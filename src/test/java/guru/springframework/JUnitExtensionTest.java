package guru.springframework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class JUnitExtensionTest {
    @Mock
    HashMap<String, Object> mockMap1;

   @Test
    void testMock() {
        mockMap1.put("Keyvalue", "foo");
        System.out.println("in test1");
    }
}
