package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class PersonTest implements ModelTests{
    @DisplayName("Demo grouped assertions")
    @Test
    void groupedAssertions() {
        //given
        Person person = new Person(1L, "Joe", "Buck");

        //then
        assertAll("Test props Set",
                () -> assertEquals("Joe", person.getFirstName(), "First name failed"),
                () -> assertEquals("Buck", person.getLastName(), "Last name failed")
        );
    }

    @DisplayName("Demo of repeated test")
    @RepeatedTest(value = 10)
    void repeatedTest() {
        System.out.println(1);
    }

    @DisplayName("Demo of repeated test with param injection")
    @RepeatedTest(value = 5, name = "{displayName}: repetition {currentRepetition} of {totalRepetitions}")
    void repeatedTestWithDi(TestInfo testInfo, RepetitionInfo repInfo) {
        System.out.println(testInfo.getDisplayName() + " : " + repInfo.getCurrentRepetition() );
    }
}