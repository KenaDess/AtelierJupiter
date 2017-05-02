package com.norsys.service;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.norsys.User;
import com.norsys.service.exception.AuthenticationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class LoginServiceTest {

    private LoginService loginService;

    private LoadingCache<String, Integer> attemptsCache;

    @BeforeEach
    void setUp() {
        attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String user) {
                return 0;
            }
        });
        loginService = new LoginService(attemptsCache);
    }

    @Test
    @DisplayName("Succes de l'authentification")
    void loginSuccess() {
        // Comment tester ?
    }

    @Test
    @DisplayName("Echec de l'authentification")
    void loginFail() {
        Throwable exception = assertThrows(BadCredentialsException.class, () -> {
            loginService.authenticate("toto", "bidon");
        });
        assertEquals("Mauvaise identification", exception.getMessage());
    }

    @Disabled
    @TestFactory
    @DisplayName("Echecs répétés de l'authentification")
    List<DynamicTest> blockedUser(TestInfo testInfo) {
        User myUser = new User("Pach", "Cyrille");
        myUser.setUsername("robocal");
        myUser.setPassword("badpassword");

        List<DynamicTest> tests = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            tests.add(DynamicTest.dynamicTest(displayNameForBlockedUser(myUser), () -> {
                assertThrows(BadCredentialsException.class, () -> loginService.authenticate(myUser.getUsername(), myUser.getPassword()));
                assertTrue(true);
            }));
        }
        return tests;
    }

    @Disabled
    @RepeatedTest(10)
    @DisplayName("Blocage de l'utilisateur suite à plusieurs échecs")
    void blockedUsersWithRepetitions(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        User myUser = new User("Pach", "Cyrille");
        myUser.setUsername("robocal");
        myUser.setPassword("badpassword");

        assumingThat(repetitionInfo.getCurrentRepetition() <= 5, () ->
                assertThrows(BadCredentialsException.class, () -> loginService.authenticate(myUser.getUsername(), myUser.getPassword()))
        );

        assumingThat(repetitionInfo.getCurrentRepetition() > 5, () ->
                assertThrows(AuthenticationException.class, () -> loginService.authenticate(myUser.getUsername(), myUser.getPassword()))
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Test paramétré")
    void testWithValueSource(int argument) {
        assertNotNull(argument);
    }

    private String displayNameForBlockedUser(User user) {
        return "Tentative d'authentification avec " + user.getUsername() + "/" + user.getPassword();
    }
}
