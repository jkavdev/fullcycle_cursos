package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.controllers;

import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update.UpdateCastMemberCommand;
import br.com.jkavdev.fullcycle.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api.CastMemberAPI;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.UpdateCastMemberRequest;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.presenters.CastMemberPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CastMemberController implements CastMemberAPI {

    private final CreateCastMemberUseCase createCastMemberUseCase;

    private final GetCastMemberByIdUseCase getCastMemberByIdUseCase;

    private final UpdateCastMemberUseCase updateCastMemberUseCase;

    public CastMemberController(
            final CreateCastMemberUseCase createCastMemberUseCase,
            final GetCastMemberByIdUseCase getCastMemberByIdUseCase,
            final UpdateCastMemberUseCase updateCastMemberUseCase
    ) {
        this.createCastMemberUseCase = Objects.requireNonNull(createCastMemberUseCase);
        this.getCastMemberByIdUseCase = Objects.requireNonNull(getCastMemberByIdUseCase);
        this.updateCastMemberUseCase = Objects.requireNonNull(updateCastMemberUseCase);
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

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCastMemberRequest anInput) {
        final var aCommand =
                UpdateCastMemberCommand.with(id, anInput.name(), anInput.type());

        final var output = updateCastMemberUseCase.execute(aCommand);

        return ResponseEntity.ok((output));
    }
}
