package com.br.tasksmanager.Controllers;

import com.br.tasksmanager.dtos.users.AuthenticationDto;
import com.br.tasksmanager.dtos.users.RegisterDto;
import com.br.tasksmanager.dtos.users.TokenDto;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.services.AuthenticationService;
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
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid AuthenticationDto authDto){
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody @Valid RegisterDto registerDto){
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
