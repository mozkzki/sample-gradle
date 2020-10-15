package sample.etc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import sample.spring.AppConfig;

@SpringJUnitConfig(classes = AppConfig.class)
public class RetryUtilTest {
    @Autowired
    RetryUtil<String> retry;

    SampleDao dao;

    class SampleDao {
        public String get() {
            return "dummy";
        }
    }

    @BeforeEach
    void setup() {
        dao = mock(SampleDao.class);
        when(dao.get()). //
                thenThrow(new IllegalStateException("bad state.")). //
                thenThrow(new NumberFormatException("bad number")). //
                thenThrow(new IllegalStateException("bad state."));

    }

    // リトライ数を数えるテスト

    // 数回失敗するメソッド

    // 全部失敗するメソッド
    private void targetMethod(String str) {
        System.out.println("----------> " + str);
        // わざと失敗
        // throw new IllegalStateException("test");
        dao.get();
    }

    @Test
    void test() throws Exception {
        retry.call(this::targetMethod, "foobar");
    }
}
