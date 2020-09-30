package sample.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 結論： 複数コンストラクタを持つクラスをmock, injectmocksするテストは要注意
 * 
 * 複数コンストラクタを持つクラスを、@InjectMocksや@Mockするときの注意。 動かした感じだと、引数の多いコンストラクタが呼ばれるようだ。
 * 引数なしのコンストラクタがあっても、それが呼ばれるわけではないので要注意。
 */
@ExtendWith(MockitoExtension.class)
public class PersonTest {
    // 引数２つのPersonコンストラクタが呼ばれる
    // Person自体のインスタンス化は成功する
    @InjectMocks
    Person cut;

    // 引数２つのHomeコンストラクタが呼ばれる
    // 引数付きのコンストラクタはMock化出来ないようで、
    // Mock生成に失敗する
    @Mock
    Home home;

    // 結果、cut自体は生成されるが、
    // メンバーのhomeはnullのままとなる(nullのMockに差し替えられている？)
    @Test
    void test() {
        cut.show();
    }

}
