package com.ddoddii.resume.service;

import com.ddoddii.resume.error.errorcode.UserErrorCode;
import com.ddoddii.resume.error.exception.BadCredentialsException;
import com.ddoddii.resume.model.RefreshToken;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.repository.RefreshTokenRepository;
import com.ddoddii.resume.repository.UserRepository;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-jwt.yaml")
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidityInSeconds;


    public RefreshToken saveRefreshToken(String userEmail, String token) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BadCredentialsException(UserErrorCode.NOT_EXIST_USER));
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUser(user);

        if (existingRefreshToken.isPresent()) {
            // Update existing refresh token
            RefreshToken refreshToken = existingRefreshToken.get();
            refreshToken.setRefreshToken(token);
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenValidityInSeconds * 1000));
            refreshTokenRepository.save(refreshToken);
            return refreshToken;
        } else {
            // Create a new refresh token if it does not exist
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .refreshToken(token)
                    .expiryDate(Instant.now().plusMillis(refreshTokenValidityInSeconds * 1000))
                    .build();
            refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }
    }


    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }


    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException(refreshToken.getRefreshToken() + "Refresh token is expired");
        }
        return refreshToken;
    }
}
