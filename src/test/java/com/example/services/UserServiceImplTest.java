package com.example.services;

import com.example.dtos.RegisteredUserDTO;
import com.example.dtos.UserDTO;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.security.RedbayUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceImplTest {
    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    RedbayUserDetailsService redbayUserDetailsService;

    MapperService mapperService;

    UserServiceImpl userServiceImpl;

    BeanFactory beanFactory;

    @Autowired
    UserServiceImplTest(BeanFactory beanFactory) {
        userRepository = Mockito.mock(UserRepository.class);
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        mapperService = Mockito.mock(MapperService.class);
        redbayUserDetailsService = Mockito.mock(RedbayUserDetailsService.class);
        userServiceImpl = new UserServiceImpl(userRepository, bCryptPasswordEncoder, mapperService, redbayUserDetailsService);
        this.beanFactory = beanFactory;
    }

    @Test
    void addNewUser_ReturnsRegisteredUserDTO() {
        UserDTO fakeUserDTO = beanFactory.getBean("fakeUserDTO", UserDTO.class);
        User fakeUser = beanFactory.getBean("fakeUserWithIds", User.class);
        RegisteredUserDTO registeredUserDTO = new RegisteredUserDTO(fakeUserDTO.getName(), fakeUserDTO.getUsername(), fakeUserDTO.getEmail());

        Mockito.when(mapperService.convertUserDTOToUser(fakeUserDTO)).thenReturn(beanFactory.getBean("fakeUserWithIds", User.class));
        Mockito.when(userRepository.save(fakeUser)).thenReturn(fakeUser);
        Mockito.when(userServiceImpl.addNewUser(fakeUserDTO)).thenReturn(registeredUserDTO);

        RegisteredUserDTO returnOfAddNewUser = userServiceImpl.addNewUser(fakeUserDTO);

        Assertions.assertEquals(fakeUserDTO.getUsername(), returnOfAddNewUser.getUsername());
        Assertions.assertEquals(fakeUserDTO.getEmail(), returnOfAddNewUser.getEmail());
    }

    @Test
    void addNewUser_WhenUserExists_ReturnsTrue() {
        User fakeUser = beanFactory.getBean("fakeUserWithIds", User.class);
        Mockito.when(userRepository.findUserByUsername(fakeUser.getUsername())).thenReturn(Optional.of(fakeUser));

        Assertions.assertTrue(userServiceImpl.isUserRegistered(fakeUser.getUsername()));
    }

    @Test
    void addNewUser_WhenUserDoesNotExist_ReturnsFalse() {
        String userName = "notExistingUser";
        Mockito.when(userRepository.findUserByUsername(userName)).thenReturn(Optional.empty());
        boolean doesUserExist = userServiceImpl.isUserRegistered(userName);

        Assertions.assertFalse(doesUserExist);
    }
}
