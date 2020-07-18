package com.inovoseltsev.lightdev.model.repository;

import com.inovoseltsev.lightdev.model.entity.AppUser;
import com.inovoseltsev.lightdev.model.role.Role;
import com.inovoseltsev.lightdev.model.state.State;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class AppUserRepositoryTest {

    public AppUserRepositoryTest() {
    }

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppUserRepository appUserRepository;

    private static AppUser initialUser;

    private static AppUser foundUser;

    @BeforeAll
    public static void setInitialUser() {
        initialUser = new AppUser("John", "Jackson", "johnJackson", "1234",
                "johnJackson@gmail.com", Role.USER, State.ACTIVE);
    }

    @Test
    void findAppUserByLogin() {
        entityManager.persist(initialUser);
        entityManager.flush();
        foundUser = appUserRepository.findAppUserByLogin(initialUser.getLogin());
        assertEquals(initialUser.getLogin(), foundUser.getLogin());
        foundUser = appUserRepository.findAppUserByLogin("notExists");
        assertNull(foundUser);
    }

    @Test
    void findAppUserByEmail() {
        entityManager.merge(initialUser);
        entityManager.flush();
        foundUser = appUserRepository.findAppUserByEmail(initialUser.getEmail());
        assertEquals(foundUser.getEmail(), initialUser.getEmail());
        foundUser = appUserRepository.findAppUserByEmail("notExists");
        assertNull(foundUser);
    }
}