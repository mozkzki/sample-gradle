package sample.mockito.threepattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sample.mockito.SimpleHome;
import sample.mockito.SimplePerson;

// コンストラクタインジェクションの例
@ExtendWith(MockitoExtension.class)
public class ConstructerInjectionTest {
    @InjectMocks
    SimplePerson cut;

    @Mock
    SimpleHome home;

    @BeforeEach
    void setup() {
        when(home.getAddress()).thenReturn("osaka");
    }

    // コンストラクタインジェクション → される
    // コンストラクタで渡したmock対象のオブジェクトを
    // テスト対象クラスがメンバーとして保持するかどうかは無関係にmockできる。
    // cutのコンストラクタに、mock化されたhomeが渡る(が、when等が効いてない)
    // cutのコンストラクタは実際のものが呼ばれるが、
    // homeのコンストラクタは本物が呼ばれるわけではないので注意(mockなので)
    @Test
    void test() {
        assertEquals("osaka", cut.getAddress(home));
        // このテストの出力は下記となる
        // > null
        // > osaka
        // cutのコンストラクタ中ではhomeのmockはまだ機能しないようだ
    }

}
