package sample.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FieldInjectionTest {
    @InjectMocks
    SimplePersonHasField cut;

    @Mock
    SimpleHome home;

    @BeforeEach
    void setup() {
        when(home.getAddress()).thenReturn("oosaka");
    }

    // フィールドインジェクション → される
    // cutのコンストラクタでhomeをsetする/しないに関わらず
    // テスト対象メソッドではhomeのmockが利用可能。
    // また、mockのメンバーへのset関数がある/なしに関わらずmock可能。
    // ただし、cutがデフォルトコンストラクタを持っていないと
    // テスト時にエラーとなる。
    @Test
    void test() {
        assertEquals("oosaka", cut.getAddress());
    }
}
