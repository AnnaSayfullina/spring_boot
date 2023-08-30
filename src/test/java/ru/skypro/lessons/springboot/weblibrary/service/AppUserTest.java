package ru.skypro.lessons.springboot.weblibrary.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.lessons.springboot.weblibrary.security.AppUser;
import ru.skypro.lessons.springboot.weblibrary.security.Role;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppUserTest {

    private static Stream<Arguments> argumentsForCorrectUsername() {
        return Stream.of(
                Arguments.of("Anna", "Anna"),
                Arguments.of("anna", "Anna"),
                Arguments.of("ANna", "Anna"),
                Arguments.of("ANNA", "Anna")
        );
    }

    @DisplayName("Корректное отображение имени")
    @ParameterizedTest
    @MethodSource("argumentsForCorrectUsername")
    void ShouldReturnCorrectName_OK(String name, String expectedName) {

        AppUser newAppUser = new AppUser(name,"password", Role.ADMIN);
        String actualName = StringUtils.capitalize(newAppUser.getUsername().toLowerCase());
        assertEquals(expectedName,actualName);
    }
}
