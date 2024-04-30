package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
