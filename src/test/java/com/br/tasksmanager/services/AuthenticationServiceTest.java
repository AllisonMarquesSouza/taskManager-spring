package com.br.tasksmanager.services;

import com.br.tasksmanager.config.JWTService;
import com.br.tasksmanager.dtos.users.AuthenticationDto;
import com.br.tasksmanager.dtos.users.TokenDto;
import com.br.tasksmanager.exceptions.BadRequestException;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.repositories.UsersRepository;
import com.br.tasksmanager.util.UsersCreate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private ApplicationContext context;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void loadUserByUsername_ValidUsername_ReturnsUser() {
        Users userExpected = UsersCreate.createUser();

        when(usersRepository.findByUsername(userExpected.getUsername()))
                .thenReturn(userExpected);

        UserDetails userLoad = authenticationService.loadUserByUsername(userExpected.getUsername());

        assertNotNull(userLoad);
        assertEquals(userExpected, userLoad);

    }

    @Test
    void loadUserByUsername_InvalidUsername_ThrowUsernameNotFoundException() {
        Users userExpected = UsersCreate.createUser();

        when(usersRepository.findByUsername(userExpected.getUsername()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        UsernameNotFoundException exception = assertThrows
                (UsernameNotFoundException.class,
                        () -> authenticationService.loadUserByUsername(userExpected.getUsername()));

        assertEquals("User not found", exception.getMessage());

    }

    @Test
    void login_ValidAuthentication_ReturnsUser() {
        AuthenticationDto authDto = new AuthenticationDto("allison", "allison");
        Users user = UsersCreate.createUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        when(context.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        TokenDto result = authenticationService.login(authDto);

        assertNotNull(result);
        assertEquals("jwt-token", result.token());
    }


    @Test
    void login_invalidAuthentication_ThrowBadRequestException() {
        AuthenticationDto authDto = new AuthenticationDto("invaliduser", "wrongpassword");

        when(context.getBean(AuthenticationManager.class)).thenReturn(authenticationManager);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            authenticationService.login(authDto);
        });

        assertEquals("Error in authenticating the user...", exception.getMessage());
    }


    @Test
    void register_ValidDto_ReturnsUser() {
        Users userExpected = UsersCreate.createUser();

        when(usersRepository.existsByUsername("allison"))
                .thenReturn(false);
        when(usersRepository.existsByEmail("allison@gmail.com"))
                .thenReturn(false);
        when(usersRepository.save(Mockito.any(Users.class)))
                .thenReturn(userExpected);

        Users userToBeSaved = authenticationService.register(UsersCreate.createUserDto());

        assertNotNull(userToBeSaved);
        assertEquals(userExpected, userToBeSaved);
    }
}