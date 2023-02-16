package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.CastMemberAPI;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.presenters.CastMemberPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;

    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final GetCastMemberByIdUseCase getCastMemberByIdUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.getCastMemberByIdUseCase = Objects.requireNonNull(getCastMemberByIdUseCase);
    }

    @Override
    public ResponseEntity<?> create(CreateCastMemberRequest anInput) {
        final var aCommand = CreateCastMemberCommand.with(anInput.name(), anInput.type());

        final var output = createCastMemberUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/cast_members/" + output.id())).body(output);
    }

    @Override
    public CastMemberResponse getById(String id) {
        return CastMemberPresenter.present(getCastMemberByIdUseCase.execute(id));
    }
}
