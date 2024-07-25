package guru.springframework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

public class AnnotationMockTest {

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        System.out.println("opened");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        System.out.println("closed");
    }

    @Mock
    Map<String, Object> mockMap;
    AutoCloseable autoCloseable;

    @Test
    void testMock() {
        mockMap.put("Keyvalue", "foo");
        System.out.println("in test1");
    }

    @Test
    void testMock1() {
        mockMap.put("Keyvalue", "foo");
        System.out.println("in test2");
    }
}
