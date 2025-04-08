package com.br.tasksmanager.repositories;

import com.br.tasksmanager.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    UserDetails findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

    Optional<Users> findUsersByUsername(String username);
}
