package com.example.controllers;

import com.example.dtos.UserDTO;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RegistrationControllerTest {
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    BeanFactory beanFactory;

    UserRepository userRepository;

    @Autowired
    RegistrationControllerTest(MockMvc mockMvc, BeanFactory beanFactory, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
        this.beanFactory = beanFactory;
        this.userRepository = userRepository;
    }

    @Test
    void test() {
        System.out.println("test");
    }

    @Test
    @Sql("/db/test/clear_tables.sql")
    void addNewUser_AddNewUserWithGivenData_ReturnsNewUser() throws Exception {
        UserDTO fakeUserDTO = beanFactory.getBean("fakeUserDTO", UserDTO.class);
        String userDTOJson = objectMapper.writeValueAsString(fakeUserDTO);
        MockHttpServletRequestBuilder requestBuilder = post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDTOJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(fakeUserDTO.getName()))
                .andExpect(jsonPath("$.username").value(fakeUserDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(fakeUserDTO.getEmail()));
    }

    @Test
    @Sql("/db/test/clear_tables.sql")
    void addNewUser_WithExistingUsername_ReturnsExpectedErrorMessage() throws Exception {
        userRepository.save(beanFactory.getBean("fakeUser", User.class));
        UserDTO fakeUserDTO = beanFactory.getBean("fakeUserDTO", UserDTO.class);
        String userDTOJson = objectMapper.writeValueAsString(fakeUserDTO);
        MockHttpServletRequestBuilder requestBuilder = post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDTOJson);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict());
    }

    @Test
    void addNewUser_WithLessThanEightCharactersPassword_ReturnsExpectedErrorMessage() throws Exception {
        UserDTO fakeUserDTO = beanFactory.getBean("fakeUserDTO", UserDTO.class);
        fakeUserDTO.setPassword("1234567");
        String userDTOJson = objectMapper.writeValueAsString(fakeUserDTO);
        MockHttpServletRequestBuilder requestBuilder = post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDTOJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("The password must be at least 8 characters!"));
    }

    @Test
    @Sql("/db/test/clear_tables.sql")
    void addNewUser_WithoutEmail_ReturnsExpectedErrorMessage() throws Exception {
        UserDTO fakeUserDTO = beanFactory.getBean("fakeUserDTO", UserDTO.class);
        fakeUserDTO.setEmail(null);

        String userDTOJson = objectMapper.writeValueAsString(fakeUserDTO);
        MockHttpServletRequestBuilder requestBuilder = post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDTOJson);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Email is required!"));
    }
}
