package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.impl;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Fixture;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.resource.Resource;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.IdUtils;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoMediaType.VIDEO;
import static com.google.cloud.storage.Storage.BlobListOption.prefix;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GCStorageServiceTest {

    private GCStorageService target;

    private Storage storage;

    private String bucket = "fc3_test";

    @BeforeEach
    public void setUp() {
        this.storage = Mockito.mock(Storage.class);
        this.target = new GCStorageService(this.bucket, this.storage);
    }

    @Test
    public void givenValidResource_whenCallsStore_shouldStoreIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VIDEO);

        final var blob = mockBlob(expectedName, expectedResource);
        doReturn(blob).when(storage).create(any(BlobInfo.class), any());

        // when
        target.store(expectedName, expectedResource);

        // then
        final var captor = ArgumentCaptor.forClass(BlobInfo.class);

        verify(storage, times(1)).create(captor.capture(), eq(expectedResource.content()));

        final var actualBlob = captor.getValue();
        Assertions.assertEquals(this.bucket, actualBlob.getBlobId().getBucket());
        Assertions.assertEquals(expectedName, actualBlob.getBlobId().getName());
        Assertions.assertEquals(expectedName, actualBlob.getName());
        Assertions.assertEquals(expectedResource.checksum(), actualBlob.getCrc32cToHexString());
        Assertions.assertEquals(expectedResource.contentType(), actualBlob.getContentType());
    }

    @Test
    public void givenValidResource_whenCallsGet_shouldRetrieveIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VIDEO);

        final var blob = mockBlob(expectedName, expectedResource);
        doReturn(blob).when(storage).get(anyString(), anyString());

        // when
        final var actualResource = target.get(expectedName).get();

        // then
        verify(storage, times(1)).get(eq(this.bucket), eq(expectedName));

        Assertions.assertEquals(expectedResource, actualResource);
    }

    @Test
    public void givenInvalidResource_whenCallsGet_shouldBeEmpty() {
        // given
        final var expectedName = IdUtils.uuid();

        doReturn(null).when(storage).get(anyString(), anyString());

        // when
        final var actualResource = target.get(expectedName);

        // then
        verify(storage, times(1)).get(eq(this.bucket), eq(expectedName));

        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    public void givenValidResource_whenCallsList_shouldRetrieveAll() {
// given
        final var expectedPrefix = "media_";

        final var expectedNameVideo = expectedPrefix + IdUtils.uuid();
        final var expectedVideo = Fixture.Videos.resource(VIDEO);

        final var expectedNameBanner = expectedPrefix + IdUtils.uuid();
        final var expectedBanner = Fixture.Videos.resource(VIDEO);

        final var blobVideo = mockBlob(expectedNameVideo, expectedVideo);
        final var blobBanner = mockBlob(expectedNameBanner, expectedBanner);

        final var expectedResources = List.of(expectedNameVideo, expectedNameBanner);

        final var page = Mockito.mock(Page.class);
        doReturn(List.of(blobVideo, blobBanner)).when(page).iterateAll();

        doReturn(page).when(storage).list(anyString(), any());

        // when
        final var actualResource = target.list(expectedPrefix);

        // then
        verify(storage, times(1)).list(eq(this.bucket), eq(prefix(expectedPrefix)));

        Assertions.assertTrue(
                expectedResources.size() == actualResource.size()
                        && expectedResources.containsAll(actualResource)
        );
    }

    @Test
    public void givenValidResource_whenCallsDelete_shouldDeleteAll() {
// given
        final var expectedPrefix = "media_";
        final var expectedNameVideo = expectedPrefix + IdUtils.uuid();
        final var expectedNameBanner = expectedPrefix + IdUtils.uuid();

        final var expectedResources = List.of(expectedNameVideo, expectedNameBanner);

        // when
        target.deleteAll(expectedResources);

        // then
        final var captor = ArgumentCaptor.forClass(List.class);

        verify(storage, times(1)).delete(captor.capture());

        final var actualResource = ((List<BlobId>) captor.getValue()).stream()
                .map(BlobId::getName)
                .toList();

        Assertions.assertTrue(
                expectedResources.size() == actualResource.size()
                        && expectedResources.containsAll(actualResource)
        );
    }

    private Blob mockBlob(final String name, final Resource resource) {
        final var blob = Mockito.mock(Blob.class);
        when(blob.getBlobId()).thenReturn(BlobId.of(this.bucket, name));
        when(blob.getCrc32cToHexString()).thenReturn(resource.checksum());
        when(blob.getContent()).thenReturn(resource.content());
        when(blob.getContentType()).thenReturn(resource.contentType());
        when(blob.getName()).thenReturn(resource.name());
        return blob;
    }

}