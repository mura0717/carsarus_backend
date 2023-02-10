package dat3.experiments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleSanitizerTest {

    @Test
    void simpleSanitize() {
        String result = SimpleSanitizer.simpleSanitize("Hello <script>alert('Hacked')</script>");
        assertEquals("Hello World",result);

    }
}