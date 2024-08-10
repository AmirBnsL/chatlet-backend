package com.chatlet.chatlet.repositories;

import com.chatlet.chatlet.data.entities.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByEmail(String email);

    Optional<Auth> findByUsername(String username);

    Optional<Auth> findByUsernameOrEmail(String username, String email);


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);


}
