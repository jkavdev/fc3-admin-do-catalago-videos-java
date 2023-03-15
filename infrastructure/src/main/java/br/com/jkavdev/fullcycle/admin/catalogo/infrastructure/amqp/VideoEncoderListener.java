package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.amqp;

import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload.UpdateMediaStatusCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.update.upload.UpdateMediaStatusUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.MediaStatus;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.Json;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoEncoderCompleted;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoEncoderError;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.video.models.VideoEncoderResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VideoEncoderListener {

    private static final Logger log = LoggerFactory.getLogger(VideoEncoderListener.class);

    static final String LISTENER_ID = "videoEncodedListener";

    private final UpdateMediaStatusUseCase updateMediaStatusUseCase;

    public VideoEncoderListener(final UpdateMediaStatusUseCase updateMediaStatusUseCase) {
        this.updateMediaStatusUseCase = Objects.requireNonNull(updateMediaStatusUseCase);
    }

    @RabbitListener(id = LISTENER_ID, queues = "${amqp.queues.video-encoded.queue}")
    public void onVideoEncodedMessage(@Payload final String message) {
        final var aResult = Json.readValue(message, VideoEncoderResult.class);

        if (aResult instanceof VideoEncoderCompleted dto) {
            log.info("[message:video.listener.income] [status:completed] [payload:{}]", message);
            final var aCmd = new UpdateMediaStatusCommand(
                    MediaStatus.COMPLETED,
                    dto.id(),
                    dto.video().resourceId(),
                    dto.video().encodedVideoFolder(),
                    dto.video().filePath()
            );

            this.updateMediaStatusUseCase.execute(aCmd);
        } else if (aResult instanceof VideoEncoderError) {
            log.error("[message:video.listener.income] [status:error] [payload:{}]", message);
        } else {
            log.error("[message:video.listener.income] [status:unknown] [payload:{}]", message);
        }
    }
}
