package com.ddoddii.resume;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ddoddii.resume.dto.UserDTO;
import com.ddoddii.resume.error.exception.DuplicateIdException;
import com.ddoddii.resume.error.exception.NotExistIdException;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.repository.UserRepository;
import com.ddoddii.resume.service.UserService;
import com.ddoddii.resume.util.PasswordEncrypter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    UserDTO user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void makeUser() {
        user = UserDTO.builder()
                .userId("user")
                .password(PasswordEncrypter.encrypt("test123"))
                .email("test@email.com")
                .name("testUser")
                .build();
    }

    @Test
    @DisplayName("회원가입에 성공합니다")
    void 유저_회원가입_성공() throws Exception {
        //When
        when(userRepository.existsByUserId(any(String.class))).thenReturn(false);

        userService.signUp(user);
        //Then
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입에 실패합니다 : 중복된 아이디")
    void 유저_회원가입_실패_중복_아이디() {
        //When
        when(userRepository.existsByUserId(user.getUserId())).thenReturn(true);
        //Then
        assertThatThrownBy(() -> userService.signUp(user))
                .isInstanceOf(DuplicateIdException.class);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("유저 삭제에 성공합니다")
    void 유저_삭제_성공() {
        //Given
        User mockUser = new User();
        mockUser.setUserId(user.getUserId());
        mockUser.setEmail(user.getEmail());
        mockUser.setName(user.getName());
        mockUser.setPassword(user.getPassword());
        //When
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(mockUser));
        userService.deleteUser(user.getUserId());
        //Then
        verify(userRepository).delete(mockUser);
    }

    @Test
    @DisplayName("유저 삭제에 실패합니다 : 삭제할 아이디 존재하지 않음")
    void 유저_삭제_실패() {
        //Given
        final String userId = user.getUserId();
        //When
        when(userRepository.findByUserId(userId)).thenReturn(Optional.empty());
        //Then
        assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(NotExistIdException.class);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    @DisplayName("유저 비밀번호를 변경합니다")
    void 유저_비밀번호_변경_성공() {
        //Given
        User mockUser = new User();
        mockUser.setUserId(user.getUserId());
        mockUser.setEmail(user.getEmail());
        mockUser.setName(user.getName());
        mockUser.setPassword(user.getPassword());
        final String changedPassword = "change123";
        //When
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(mockUser));
        userService.changeUserPassword(mockUser.getUserId(), changedPassword);
        //Then
        assertTrue(PasswordEncrypter.isMatch(changedPassword, mockUser.getPassword()));
    }


}
