package br.com.jkavdev.fullcycle.admin.catalogo.application.video.media.get;

import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCaseTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.Fixture;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.MediaResourceGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.video.VideoID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class GetMediaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetMediaUseCase useCase;

    @Mock
    private MediaResourceGateway mediaResourceGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(mediaResourceGateway);
    }

    @Test
    public void givenVideoIdAndType_whenIsValidCmd_shouldReturnResource() {
        // given
        final var expectedId = VideoID.unique();
        final var expectedType = Fixture.Videos.mediaType();
        final var expectedResource = Fixture.Videos.resource(expectedType);

        Mockito.when(mediaResourceGateway.getResource(expectedId, expectedType))
                .thenReturn(Optional.of(expectedResource));

        final var aCmd = GetMediaCommand.with(expectedId.getValue(), expectedType.name());

        // when
        final var actualResult = this.useCase.execute(aCmd);

        // then
        Assertions.assertEquals(expectedResource.name(), actualResult.name());
        Assertions.assertEquals(expectedResource.contentType(), actualResult.contentType());
        Assertions.assertEquals(expectedResource.content(), actualResult.content());
    }

    @Test
    public void givenVideoIdAndType_whenIsNotFound_shouldReturnNotFoundException() {
        // given
        final var expectedId = VideoID.unique();
        final var expectedType = Fixture.Videos.mediaType();
        final var expectedErrorMessage =
                "resource %s not found for video %s".formatted(expectedType.name(), expectedId.getValue());

        Mockito.when(mediaResourceGateway.getResource(expectedId, expectedType))
                .thenReturn(Optional.empty());

        final var aCmd = GetMediaCommand.with(expectedId.getValue(), expectedType.name());

        // when
        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> this.useCase.execute(aCmd));

        // then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenVideoIdAndType_whenTypeNotFound_shouldReturnNotFoundException() {
        // given
        final var expectedId = VideoID.unique();
        final var expectedErrorMessage = "media type QUALQUER doesn't exists";

        final var aCmd = GetMediaCommand.with(expectedId.getValue(), "QUALQUER");

        // when
        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> this.useCase.execute(aCmd));

        // then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
