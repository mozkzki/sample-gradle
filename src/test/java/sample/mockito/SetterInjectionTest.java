package sample.mockito;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SetterInjectionTest {
    @InjectMocks
    SimplePersonHasSetter cut;

    @Mock
    SimpleHome home;

    @BeforeEach
    void setup() {
        when(home.getAddress()).thenReturn("oosaka");
    }

    // セッターインジェクション → される
    // mockのhomeを渡しているので、当たり前といえば当たり前。
    // ただし、cutがデフォルトコンストラクタを持っていないと
    // テスト時にエラーとなる。
    @Test
    void test() {
        cut.setHome(home);
    }
}
