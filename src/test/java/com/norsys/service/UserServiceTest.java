package com.norsys.service;

import com.norsys.Application;
import com.norsys.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }


    @Nested
    @DisplayName("nouvelle feature bidon")
    class NommageUser {

        @Test
        @DisplayName("test bidon de la feature bidon")
        void test() {
            assertTrue(true);
        }
    }

    @TestFactory
    @DisplayName("Tests dynamiques avec un stream")
    public Stream<DynamicTest> translateDynamicTestsFromStream() {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User("Maquet", "Fred"),
                new User("Dessingue", "Clement"),
                new User("Debavelaere", "Pauline")));

        List<String> nomsComplets = new ArrayList<>(Arrays.asList("Fred MAQUET", "Clement DESSINGUE", "Pauline DEBAVELAERE"));

        return users.stream().map(user -> DynamicTest.dynamicTest("Nom complet pour " + user.getPrenom(), () -> {
            int index = users.indexOf(user);
            assertEquals(nomsComplets.get(index), userService.getNomComplet(user));
        }));
    }

    @TestFactory
    @DisplayName("Tests dynamiques avec liste")
    public List<DynamicTest> translateDynamicTestsFromList() {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User("Maquet", "Fred"),
                new User("Dessingue", "Clement"),
                new User("Debavelaere", "Pauline")));

        List<String> nomsComplets = new ArrayList<>(Arrays.asList("Fred MAQUET", "Clement DESSINGUE", "Pauline DEBAVELAERE"));

        List<DynamicTest> tests = new ArrayList<>();

        for (User user : users) {
            int index = users.indexOf(user);
            tests.add(DynamicTest.dynamicTest("Nom complet pour  " + user.getPrenom(), () -> assertEquals(nomsComplets.get(index), userService.getNomComplet(user))));
        }
        return tests;
    }

    @Test
    @DisplayName("Vérification d'un utilisateur")
    void assertAllProperties() {
        User myUser = new User("Pach", "Cyrille");
        myUser.setUsername("robocal");

        assertAll("user",
                () -> assertEquals("Cyrille", myUser.getPrenom()),
                () -> assertEquals("Pach", myUser.getNom()),
                () -> assertEquals("robocal", myUser.getUsername())
        );
    }

    @Disabled
    @Test
    @DisplayName("Vérification d'un utilisateur - échec")
    void failedAssertAllProperties() {
        User myUser = new User("Pach", "Cyrille");
        myUser.setUsername("robocal");

        assertAll("user",
                () -> assertEquals("Cyril", myUser.getPrenom()),
                () -> assertEquals("Pache", myUser.getNom()),
                () -> assertEquals("Robocal", myUser.getUsername())
        );
    }
}
