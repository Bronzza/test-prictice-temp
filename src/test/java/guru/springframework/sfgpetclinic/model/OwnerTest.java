package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


class OwnerTest implements ModelTests {

    private Owner owner;

    @BeforeEach
    public void setUp() {
        owner = new Owner(1L, "Joe", "Buck");
    }

    @Test
    void dependentAssertions() {
        owner.setCity("Key West");
        owner.setTelephone("123123123123");

        assertAll("Properties test",
                () -> {
                    assertAll("Person Properties",
                            () -> assertEquals("Joe", owner.getFirstName(), "First name failed"),
                            () -> assertEquals("Buck", owner.getLastName(), "Last name failed"));
                },
                () -> {
                    assertAll("Owner Properties",
                            () -> assertEquals("Key West", owner.getCity(), "City property failed"),
                            () -> assertEquals("123123123123", owner.getTelephone(), "Phone property failed"));
                });

    }

    @ValueSource(strings = {"Spring", "Test", "With", "Params"})
    @ParameterizedTest
    void testValueSource(String val) {
        System.out.println(val);
    }

    @DisplayName("Enum source parametrized test")
    @EnumSource(OwnerType.class)
    @ParameterizedTest
    void testValueSourceEnum(OwnerType val) {
        System.out.println(val);
    }

    @DisplayName("CSV input test")
    @ParameterizedTest
    @CsvSource({
            "FL, 1, 1, 1",
            "OH, 2, 2, 2",
            "MI, 3, 3, 3",
    })
    void csvInputTest(String state, int val1, int val2, int val3) {
        System.out.println(state + " " + val1 + " " + val2 + " " + val3);

    }

    @DisplayName("CSV input from file test")
    @ParameterizedTest
    @CsvFileSource(resources = "/file.csv", numLinesToSkip = 1)
    void csvInputTestFile(String state, int val1, int val2, int val3) {
        System.out.println(state + " " + val1 + " " + val2 + " " + val3);

    }

}