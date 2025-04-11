package com.br.tasksmanager.services;

import com.br.tasksmanager.Enums.UserRole;
import com.br.tasksmanager.config.JWTService;
import com.br.tasksmanager.dtos.users.AuthenticationDto;
import com.br.tasksmanager.dtos.users.RegisterDto;
import com.br.tasksmanager.dtos.users.TokenDto;
import com.br.tasksmanager.exceptions.BadRequestException;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final ApplicationContext context;
    private final JWTService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username);
    }

    public TokenDto login(AuthenticationDto authDto){
        AuthenticationManager manager = context.getBean(AuthenticationManager.class);
        try{
            var userPassword = new UsernamePasswordAuthenticationToken(authDto.username(), authDto.password());
            Authentication authenticate = manager.authenticate(userPassword);
            String token = jwtService.generateToken((Users) authenticate.getPrincipal());
            return new TokenDto(token);
        } catch (RuntimeException e){
            throw new BadRequestException("Error in authenticating the user...");
        }
    }

    @Transactional
    public Users register(RegisterDto data){
        if(usersRepository.existsByUsername(data.username())){
            throw new BadRequestException("Username already exist, check it ");
        }
        if(usersRepository.existsByEmail(data.email())){
            throw new BadRequestException("Email already exist, check it");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users user = new Users(data.username(), encryptedPassword, data.email(), UserRole.ADMIN);
        return usersRepository.save(user);
    }
}
