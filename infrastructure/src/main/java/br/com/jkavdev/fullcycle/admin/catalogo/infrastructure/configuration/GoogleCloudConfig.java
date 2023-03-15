package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.properties.GoogleCloudProperties;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.properties.GoogleStorageProperties;
import com.google.api.gax.retrying.RetrySettings;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.threeten.bp.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Configuration
@Profile({"production", "development"})
public class GoogleCloudConfig {

    @Bean
    @ConfigurationProperties("google.cloud")
    public GoogleCloudProperties googleCloudProperties() {
        return new GoogleCloudProperties();
    }

    @Bean
    @ConfigurationProperties("google.cloud.storage.catalogo-videos")
    public GoogleStorageProperties googleStorageProperties() {
        return new GoogleStorageProperties();
    }

    @Bean
    public Credentials credentials(final GoogleCloudProperties props) {
        final var jsonContent =
                Base64.getDecoder().decode(props.getCredentials());

        try (final var stream = new ByteArrayInputStream(jsonContent)) {
            return GoogleCredentials.fromStream(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public Storage storage(
            final Credentials credentials,
            final GoogleCloudProperties props,
            final GoogleStorageProperties storageProps
    ) {

        final var transportOptions = HttpTransportOptions.newBuilder()
                .setConnectTimeout(storageProps.getConnectTimeout())
                .setReadTimeout(storageProps.getReadTimeout())
                .build();

        final var retrySettings = RetrySettings.newBuilder()
                .setInitialRetryDelay(Duration.ofMillis(storageProps.getRetryDelay()))
                .setMaxRetryDelay(Duration.ofMillis(storageProps.getRetryMaxDelay()))
                .setMaxAttempts(storageProps.getRetryMaxAttempts())
                .setRetryDelayMultiplier(storageProps.getRetryMultiplier())
                .build();

        final var options = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setTransportOptions(transportOptions)
                .setRetrySettings(retrySettings)
                .build();


        return options.getService();
    }
}
