package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.usecases;

import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.list.ListCastMembersUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfiguration {

    private final CastMemberGateway castMemberGateway;


    public CastMemberUseCaseConfiguration(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMembersUseCase listCastMembersUseCase() {
        return new DefaultListCastMembersUseCase(castMemberGateway);
    }
}
