package gradle.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HogeTest {
    @Mock
    Lib lib;

    @InjectMocks
    Hoge hoge;

    @Test
    public void test() {
        when(lib.getGreeting()).thenReturn("hoge");
        assertEquals("hoge", hoge.getGreeting());
    }
}