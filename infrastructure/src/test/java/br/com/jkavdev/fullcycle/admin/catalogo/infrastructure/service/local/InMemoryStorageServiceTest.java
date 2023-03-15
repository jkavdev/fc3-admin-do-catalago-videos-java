package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.service.local;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Fixture;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.utils.IdUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoMediaType.VIDEO;

public class InMemoryStorageServiceTest {

    private final InMemoryStorageService target = new InMemoryStorageService();

    public void setUp() {
        this.target.reset();
    }

    @Test
    public void givenValidResource_whenCallsStore_shouldStoreIt() {
//        given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VIDEO);

//        when
        target.store(expectedName, expectedResource);

//        then
        final var actualContent = target.storage().get(expectedName);

        Assertions.assertEquals(expectedResource, actualContent);
    }

    @Test
    public void givenValidResource_whenCallsGet_shouldRetrieveIt() {
//        given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VIDEO);

        target.store(expectedName, expectedResource);

//        when
        final var actualResource = target.get(expectedName).get();

//        then
        Assertions.assertEquals(expectedResource, actualResource);
    }

    @Test
    public void givenInvalidResource_whenCallsGet_shouldBeEmpty() {
//        given
        final var expectedName = IdUtils.uuid();

        this.target.store("qualquer coisa", Fixture.Videos.resource(VIDEO));

//        when
        final var actualResource = target.get(expectedName);

//        then
        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    public void givenValidResource_whenCallsList_shouldRetrieveAll() {
//        given
        final var expectedNames = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(expectedNames);
        all.add("image_" + IdUtils.uuid());
        all.add("image_" + IdUtils.uuid());

        all.forEach(name -> target.store(name, Fixture.Videos.resource(VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

//        when
        final var actualResource = target.list("video");

//        then
        Assertions.assertTrue(
                expectedNames.size() == actualResource.size()
                        && expectedNames.containsAll(actualResource)
        );
    }

    @Test
    public void givenValidResource_whenCallsDelete_shouldDeleteAll() {
//        given
        final var videos = List.of(
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid(),
                "video_" + IdUtils.uuid()
        );

        final var expectedNames = Set.of(
                "image_" + IdUtils.uuid(),
                "image_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(videos);
        all.addAll(expectedNames);

        all.forEach(name -> target.store(name, Fixture.Videos.resource(VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

//        when
        target.deleteAll(videos);

//        then
        Assertions.assertEquals(2, target.storage().size());

        final var actualKeys = target.storage().keySet();

        Assertions.assertTrue(
                expectedNames.size() == actualKeys.size()
                        && expectedNames.containsAll(actualKeys)
        );
    }

}