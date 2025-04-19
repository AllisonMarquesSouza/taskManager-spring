package com.br.tasksmanager.Controllers;

import com.br.tasksmanager.dtos.users.AuthenticationDto;
import com.br.tasksmanager.dtos.users.RegisterDto;
import com.br.tasksmanager.dtos.users.TokenDto;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "404", description = "Not found ", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
})
public class AuthenticationController {
    private final AuthenticationService authService;

    @Operation(
            description = "log in",
            summary = "Type your username and password to log in",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid AuthenticationDto authDto){
        return ResponseEntity.ok(authService.login(authDto));
    }

    @Operation(
            description = "Register",
            summary = "Create your account",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Users.class))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody @Valid RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
