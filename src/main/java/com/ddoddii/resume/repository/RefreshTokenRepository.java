package com.ddoddii.resume.repository;

import com.ddoddii.resume.model.RefreshToken;
import com.ddoddii.resume.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);

    Optional<RefreshToken> findByUser(User user);
}
