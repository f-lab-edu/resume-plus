package com.ddoddii.resume.service;

import com.ddoddii.resume.dto.UserDTO;
import com.ddoddii.resume.error.errorcode.UserErrorCode;
import com.ddoddii.resume.error.exception.DuplicateIdException;
import com.ddoddii.resume.error.exception.NotExistIdException;
import com.ddoddii.resume.model.RoleType;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.repository.UserRepository;
import com.ddoddii.resume.util.PasswordEncrypter;
import java.time.LocalDateTime;
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

    public void signUp(UserDTO userDTO) {
        if (userRepository.existsByUserId(userDTO.getUserId())) {
            throw new DuplicateIdException(UserErrorCode.DUPLICATE_USER);
        }

        User encryptedUser = encryptUser(userDTO);

        userRepository.save(encryptedUser);
    }

    private User encryptUser(UserDTO userDTO) {
        String encryptedPassword = PasswordEncrypter.encrypt(userDTO.getPassword());
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setPassword(encryptedPassword);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(RoleType.USER);
        user.setCreatedAt(LocalDateTime.now());
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

    public Optional<UserDTO> findUserByIdAndPassword(String id, String password) {
        return userRepository.findByUserId(id)
                .filter(user -> PasswordEncrypter.isMatch(password, user.getPassword()))
                .map(this::convertToDto);
    }

    private UserDTO convertToDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
