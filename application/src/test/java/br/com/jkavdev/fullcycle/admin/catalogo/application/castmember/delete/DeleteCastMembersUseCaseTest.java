package br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.delete;

import br.com.jkavdev.fullcycle.admin.catalogo.domain.Fixture;
import br.com.jkavdev.fullcycle.admin.catalogo.application.UseCaseTest;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMember;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteCastMembersUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        doNothing()
                .when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        // given
        final var expectedId = CastMemberID.from("123");

        doNothing()
                .when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenCallsDeleteCastMemberAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));
    }
}
