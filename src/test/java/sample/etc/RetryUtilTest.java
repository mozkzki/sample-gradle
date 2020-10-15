package sample.etc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import sample.spring.AppConfig;

@SpringJUnitConfig(classes = AppConfig.class)
public class RetryUtilTest {
    @Autowired
    RetryUtil retry;

    // リトライ数を数えるテスト

    // 数回失敗するメソッド

    // 全部失敗するメソッド
    private void targetMethod(String str) {
        System.out.println("----------> " + str);
        // わざと失敗
        throw new IllegalStateException("test");
    }

    @Test
    void test() throws Exception {
        retry.call(this::targetMethod, "foobar");
    }
}
