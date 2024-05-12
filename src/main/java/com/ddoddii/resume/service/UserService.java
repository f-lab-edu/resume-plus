package com.ddoddii.resume.service;

import com.ddoddii.resume.dto.UserSignUpRequestDTO;
import com.ddoddii.resume.error.errorcode.UserErrorCode;
import com.ddoddii.resume.error.exception.DuplicateIdException;
import com.ddoddii.resume.error.exception.NotExistIdException;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.model.eunm.RoleType;
import com.ddoddii.resume.repository.UserRepository;
import com.ddoddii.resume.util.PasswordEncrypter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
사용자의 회원가입, 로그인을 담당하는 서비스 레이어
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void signUp(UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userRepository.existsByUserId(userSignUpRequestDTO.getUserId())) {
            throw new DuplicateIdException(UserErrorCode.DUPLICATE_USER);
        }

        User encryptedUser = encryptUser(userSignUpRequestDTO);

        userRepository.save(encryptedUser);
    }

    private User encryptUser(UserSignUpRequestDTO userSignUpRequestDTO) {
        String encryptedPassword = PasswordEncrypter.encrypt(userSignUpRequestDTO.getPassword());
        User user = new User();
        user.setUserId(userSignUpRequestDTO.getUserId());
        user.setPassword(encryptedPassword);
        user.setName(userSignUpRequestDTO.getName());
        user.setEmail(userSignUpRequestDTO.getEmail());
        user.setRole(RoleType.USER);
        return user;
    }

    public void deleteUser(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotExistIdException(UserErrorCode.NOT_EXIST_USER));
        userRepository.delete(user);
    }

    public void changeUserPassword(String userId, String newPassword) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotExistIdException(UserErrorCode.NOT_EXIST_USER));
        String newEncryptedPassword = PasswordEncrypter.encrypt(newPassword);
        user.setPassword(newEncryptedPassword);
        userRepository.save(user);
    }

    public Optional<UserSignUpRequestDTO> findUserByIdAndPassword(String id, String password) {
        return userRepository.findByUserId(id)
                .filter(user -> PasswordEncrypter.isMatch(password, user.getPassword()))
                .map(this::convertToDto);
    }

    private UserSignUpRequestDTO convertToDto(User user) {
        return UserSignUpRequestDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
