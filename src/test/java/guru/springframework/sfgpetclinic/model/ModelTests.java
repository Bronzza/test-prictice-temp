package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.*;

@Tag("Model")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface ModelTests {

    @BeforeAll
    default void beforeEach(TestInfo testInfo) {
        System.out.println("Before all from interface model: " + testInfo.getDisplayName());
    }
}
