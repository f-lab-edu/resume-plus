package com.ddoddii.resume;

import static org.mockito.Mockito.doThrow;

import com.ddoddii.resume.dto.UserRegisterDto;
import com.ddoddii.resume.model.User;
import com.ddoddii.resume.repository.UserRepository;
import com.ddoddii.resume.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;


    @Test
    void 유저_회원가입_성공() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("testuser");
        userRegisterDto.setEmail("test@example.com");
        userRegisterDto.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));
    }


    @Test
    void 유저_회원가입_실패_중복_사용자명() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("existinguser");
        userRegisterDto.setEmail("test@example.com");
        userRegisterDto.setPassword("password");

        doThrow(new IllegalArgumentException("Username already exists")).when(userService)
                .registerUser(ArgumentMatchers.any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Username already exists"));
    }

    @Test
    void 유저_회원가입_실패_중복_이메일() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("testuser");
        userRegisterDto.setEmail("existing@example.com");
        userRegisterDto.setPassword("password");

        doThrow(new IllegalArgumentException("Email already exists")).when(userService)
                .registerUser(ArgumentMatchers.any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email already exists"));
    }

}
