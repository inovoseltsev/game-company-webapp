package com.inovoseltsev.lightdev.service.impl;

import com.inovoseltsev.lightdev.domain.entity.AppUser;
import com.inovoseltsev.lightdev.domain.role.Role;
import com.inovoseltsev.lightdev.domain.state.State;
import com.inovoseltsev.lightdev.repository.AppUserRepository;
import com.inovoseltsev.lightdev.service.AppUserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppUserServiceImplTest {

    @Autowired
    private AppUserService appUserService;

    @MockBean
    private AppUserRepository appUserRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    ClientRegistrationRepository clientRegistrationRepository;

    private static AppUser initialUser;

    @BeforeAll
    public static void setUp() {
        initialUser = new AppUser("John", "Jackson", "johnJackson", "1234",
                "johnJackson@gmail.com", Role.USER);
        initialUser.setState(State.ACTIVE);
        initialUser.setId(0L);
    }

    @Test
    void create() {
        Mockito.doReturn("4321")
                .when(passwordEncoder)
                .encode("1234");
        appUserService.create(initialUser);
        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode("1234");
        Mockito.verify(appUserRepository, Mockito.times(1)).save(initialUser);
        assertEquals("4321", initialUser.getPassword());
    }

    @Test
    void update() {
        appUserService.update(initialUser);
        Mockito.verify(appUserRepository, Mockito.times(1)).save(initialUser);
    }

    @Test
    void delete() {
        appUserService.delete(initialUser);
        Mockito.verify(appUserRepository, Mockito.times(1)).delete(initialUser);
    }

    @Test
    void findById() {
        Long userId = 0L;
        Mockito.doReturn(Optional.of(initialUser))
                .when(appUserRepository)
                .findById(userId);
        AppUser foundUser = appUserService.findById(userId);
        Mockito.verify(appUserRepository, Mockito.times(1)).findById(userId);
        assertEquals(initialUser.getLogin(), foundUser.getLogin());

        userId = 1L;
        Mockito.doReturn(Optional.empty())
                .when(appUserRepository)
                .findById(userId);
        foundUser = appUserService.findById(userId);
        assertNull(foundUser);
    }

    @Test
    void findByLogin() {
        String userLogin = initialUser.getLogin();
        Mockito.when(appUserRepository.findAppUserByLogin(userLogin))
                .thenReturn(initialUser);
        AppUser foundUser = appUserService.findByLogin(userLogin);
        assertEquals(foundUser.toString(), initialUser.toString());
        Mockito.verify(appUserRepository, Mockito.times(1))
                .findAppUserByLogin(userLogin);

        String falseLogin = "nothing";
        Mockito.when(appUserRepository.findAppUserByLogin(falseLogin))
                .thenReturn(null);
        assertNull(appUserService.findByLogin(falseLogin));
    }

    @Test
    void findByEmail() {
        String userEmail = initialUser.getEmail();
        Mockito.when(appUserRepository.findAppUserByEmail(userEmail))
                .thenReturn(initialUser);
        AppUser foundUser = appUserService.findByEmail(userEmail);
        assertEquals(foundUser.toString(), initialUser.toString());
        Mockito.verify(appUserRepository, Mockito.times(1))
                .findAppUserByEmail(userEmail);

        String falseEmail = "nothing";
        Mockito.when(appUserRepository.findAppUserByEmail(falseEmail))
                .thenReturn(null);
        assertNull(appUserService.findByEmail(falseEmail));
    }

    @Test
    void findAll() {
        List<AppUser> users = Collections.singletonList(initialUser);
        Mockito.when(appUserRepository.findAll())
                .thenReturn(users);
        List<AppUser> foundUsers = appUserService.findAll();
        assertEquals(foundUsers, users);
        Mockito.verify(appUserRepository, Mockito.times(1))
                .findAll();
    }
}