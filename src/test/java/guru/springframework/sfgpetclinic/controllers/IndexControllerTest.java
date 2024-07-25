package guru.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest implements ControllerTests{

    private IndexController controller;

    @BeforeAll
    void beforeAllClass() {
        System.out.println("Before all class");
    }

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Proper view returned from controller")
    @Test
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(), "Wrong view returned");
    }

    @DisplayName("Proper view returned with AssertJ")
    @Test
    void indexAssertJ() {
        assertThat(controller.index()).isEqualTo("index")
                .contains("ex")
                .startsWith("ind");
    }

    @DisplayName("Test not implemented method")
    @Test
    void oopsHandler() {
        assertTrue("notimplemented".equals(controller.oupsHandler()), () -> "This is some expensive message build");
    }

    @DisplayName("Testing exception")
    @Test
    void oopsHandler1() {
        assertThrows(ValueNotFoundException.class, () -> controller.doSmthWrongInside(), "Exception wasn't thrown");
    }

    @DisplayName("Demo test of timeout")
    @Test
    void testTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(80);
            System.out.println("I got here");
        });
    }

    @DisplayName("Demo test of pre-emptive timeout")
    @Test
    void testTimeoutPremt() {
        assertTimeoutPreemptively(Duration.ofMillis(100),
                () -> {
                    Thread.sleep(80);
                    System.out.println("I got here static");
                },
                () -> "Message ");

    }

    @DisplayName("Failed assumption test")
    @Test
    void testAssumptionFalse() {
        assumeTrue("GURU".equalsIgnoreCase(System.getenv("GURE_RUNTIME")));
    }

    @DisplayName("Succesfull assumption demo")
    @Test
    void testAssumptionTrue() {
        assumeTrue("GURU".equalsIgnoreCase("GURU"));
    }
}