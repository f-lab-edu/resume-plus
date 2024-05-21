package com.ddoddii.resume.service;

import com.ddoddii.resume.dto.JwtTokenDTO;
import com.ddoddii.resume.dto.UserLoginRequestDTO;
import com.ddoddii.resume.dto.UserLoginResponseDTO;
import com.ddoddii.resume.dto.UserSignUpRequestDTO;
import com.ddoddii.resume.dto.UserSignUpResponseDTO;
import com.ddoddii.resume.error.errorcode.UserErrorCode;
import com.ddoddii.resume.error.exception.BadCredentialsException;
import com.ddoddii.resume.error.exception.DuplicateIdException;
import com.ddoddii.resume.error.exception.NotExistIdException;
import com.ddoddii.resume.model.RefreshToken;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.model.eunm.RoleType;
import com.ddoddii.resume.repository.RefreshTokenRepository;
import com.ddoddii.resume.repository.UserRepository;
import com.ddoddii.resume.security.CustomUserDetails;
import com.ddoddii.resume.security.TokenProvider;
import com.ddoddii.resume.util.PasswordEncrypter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
사용자의 회원가입, 로그인을 담당하는 서비스 레이어
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    // 사용자 회원가입시 사용자 정보와 로그인 토큰도 함께 반환
    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userRepository.existsByEmail(userSignUpRequestDTO.getEmail())) {
            throw new DuplicateIdException(UserErrorCode.DUPLICATE_USER);
        }

        User encryptedUser = encryptUser(userSignUpRequestDTO);

        userRepository.save(encryptedUser);

        return UserSignUpResponseDTO.builder()
                .name(userSignUpRequestDTO.getName())
                .email(userSignUpRequestDTO.getEmail())
                .pictureUrl(userSignUpRequestDTO.getPictureUrl())
                .build();
    }

    // 사용자 로그인
    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        // 이메일로 데이터베이스에서 사용자 찾기
        User user = userRepository.findByEmail(userLoginRequestDTO.getEmail())
                .orElseThrow(() -> new BadCredentialsException(UserErrorCode.BAD_CREDENTIALS));
        // 비밀번호 일치하는지 검증
        if (!PasswordEncrypter.isMatch(userLoginRequestDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(UserErrorCode.BAD_CREDENTIALS);
        }
        // GrantedAuthority 를 반환하는 메서드를 가진 CustomUserDetails 만들기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // Authorities 를 가지는 Authentication 객체
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginRequestDTO.getEmail(),
                userLoginRequestDTO.getPassword(),
                customUserDetails.getAuthorities()
        );

        JwtTokenDTO loginToken = tokenProvider.createToken(authenticationToken);

        //refresh token 저장
        refreshTokenService.saveRefreshToken(user.getEmail(), loginToken.getRefreshToken());

        return UserLoginResponseDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .pictureUrl(user.getPictureUrl())
                .token(loginToken)
                .build();
    }

    // 사용자 삭제
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistIdException(UserErrorCode.NOT_EXIST_USER));
        userRepository.delete(user);
    }

    // 사용자 비밀번호 변경
    public void changeUserPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistIdException(UserErrorCode.NOT_EXIST_USER));
        String newEncryptedPassword = PasswordEncrypter.encrypt(newPassword);
        user.setPassword(newEncryptedPassword);
        userRepository.save(user);
    }

    // refreshToken 기반 accessToken 재발급
    public JwtTokenDTO generateNewAccessToken(String token) {
        RefreshToken refreshToken = refreshTokenService.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh Token not found"));
        User user = refreshToken.getUser();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken newAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword(),
                customUserDetails.getAuthorities()
        );
        JwtTokenDTO newToken = tokenProvider.createToken(newAuthenticationToken);
        refreshTokenService.saveRefreshToken(user.getEmail(), newToken.getRefreshToken());
        return newToken;
    }

    // 현재 로그인한 유저 정보 반환
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // 비밀번호 보안 적용한 사용자 반환
    private User encryptUser(UserSignUpRequestDTO userSignUpRequestDTO) {
        String encryptedPassword = PasswordEncrypter.encrypt(userSignUpRequestDTO.getPassword());
        User user = new User();
        user.setPassword(encryptedPassword);
        user.setName(userSignUpRequestDTO.getName());
        user.setEmail(userSignUpRequestDTO.getEmail());
        user.setRole(RoleType.ROLE_USER);
        user.setPictureUrl(userSignUpRequestDTO.getPictureUrl());
        return user;
    }

}
