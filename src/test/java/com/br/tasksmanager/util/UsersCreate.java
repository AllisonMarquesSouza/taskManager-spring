package com.br.tasksmanager.util;

import com.br.tasksmanager.Enums.UserRole;
import com.br.tasksmanager.dtos.users.RegisterDto;
import com.br.tasksmanager.models.Users;

public class UsersCreate {
    public static Users createUser(){
        return Users.builder()
                .username("allison")
                .password("allison")
                .email("allison@gmail.com")
                .role(UserRole.USER)
                .build();
    }
    public static Users createUser2(){
        return Users.builder()
                .username("matheus")
                .password("matheus")
                .email("matheus@gmail.com")
                .role(UserRole.USER)
                .build();
    }

    public static Users createUserService(){
        return Users.builder()
                .id(1L)
                .username("allison")
                .password("allison")
                .email("allison@gmail.com")
                .role(UserRole.USER)
                .build();
    }
    public static RegisterDto createUserDto(){
        return RegisterDto.builder()
                .username("allison")
                .password("allison")
                .email("allison@gmail.com")
                .build();
    }

}
