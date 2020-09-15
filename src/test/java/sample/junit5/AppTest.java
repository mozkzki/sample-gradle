package sample.junit5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void appHasAGreeting() {
        App cut = new App();
        assertNotNull(cut.getGreeting(), "app should have a greeting");
    }
}
