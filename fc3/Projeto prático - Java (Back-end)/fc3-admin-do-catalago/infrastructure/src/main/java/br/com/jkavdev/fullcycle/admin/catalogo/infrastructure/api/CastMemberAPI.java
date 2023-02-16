package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.api;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;
import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.castmember.models.CreateCastMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("cast_members")
@Tag(name = "Cast Members")
public interface CastMemberAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new cast member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    ResponseEntity<?> create(@RequestBody final CreateCastMemberRequest anInput);

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a cast member by it's identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cast member found"),
            @ApiResponse(responseCode = "404", description = "Cast member was not found"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    CastMemberResponse getById(@PathVariable(name = "id") final String id);
}
