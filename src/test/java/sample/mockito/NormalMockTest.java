package sample.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// コンストラクタインジェクションの例
@ExtendWith(MockitoExtension.class)
public class NormalMockTest {
    @InjectMocks
    NormalPerson cut;

    @Mock
    NormalHome home;

    @BeforeEach
    public void setup() {
        when(home.getAddress()).thenReturn("osaka");
    }

    // 通常パターンではコンストラクタインジェクションになる
    // →テスト対象クラスにデフォルトコンストラクタは必須ではない
    //
    // cutのコンストラクタ中ではhomeのmockはまだ機能しない
    // コンストラクタ以外のメソッド中ではhomeのmockを使用できる
    @Test
    public void test() {
        assertEquals("osaka", cut.getAddress());
    }

}
