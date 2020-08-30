package gradle.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CompanyTest {
    @Mock
    User user;

    @InjectMocks
    Company company;

    @Test
    @DisplayName("getGreeting__挨拶する")
    public void testGreeting() {
        when(user.getGreeting()).thenReturn("user_x");
        assertEquals("Hoge: user_x", company.getGreeting());
    }

}