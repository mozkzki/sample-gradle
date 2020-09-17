package sample.mockito;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// コンストラクタインジェクションの例
@ExtendWith(MockitoExtension.class)
public class ConstructerInjectionTest {
    @InjectMocks
    SimplePerson cut;

    @Mock
    SimpleHome home;

    @BeforeEach
    void setup() {
        when(home.getAddress()).thenReturn("oosaka");
    }

    // コンストラクタインジェクションされる
    // cutのコンストラクタに、mock化されたhomeが渡る
    // cutのコンストラクタは実際のものが呼ばれるが、
    // homeのコンストラクタは本物が呼ばれるわけではないので注意(mockなので)
    @Test
    void test() {
        cut.doSomething(home);
    }

}
