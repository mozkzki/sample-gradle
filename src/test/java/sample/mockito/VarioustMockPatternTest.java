package sample.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VarioustMockPatternTest {

    private static final String KEY_YES = "yes";
    private static final String KEY_OTHER = "other";

    @InjectMocks
    VariousMockPattern cut;

    @Mock
    VariousMockPatternParam paramMock;

    @Test
    public void getParamValue__keyがyesの場合はyesValueを返す() {
        doReturn("yesValue").when(paramMock).getValue(eq(KEY_YES));
        // when(paramMock.getValue(eq(KEY_YES))).thenReturn("yesValue"); // この書き方でもOK
        String actual = cut.getParamValue(KEY_YES);
        assertEquals("yesValue", actual);
    }

    @Test
    public void checkParam__keyがyesの場合は指定の引数でparamのcheckを呼び出す() {
        // do*の書き方ならvoid返却メソッドでも同じ書式で書ける
        // checkが呼ばれなければエラーになるのでverifyは不要
        doNothing().when(paramMock).check(eq(KEY_YES));
        cut.checkParam(KEY_YES);
    }

    @Test
    public void checkParam__keyがyes以外の場合はIllegalStateExceptionを発行する() {
        // mockで例外を発行する
        doThrow(IllegalStateException.class).when(paramMock).check(eq(KEY_OTHER));
        assertThrows(IllegalStateException.class, () -> {
            cut.checkParam(KEY_OTHER);
        });
    }

    @ParameterizedTest
    @CsvSource({ //
            "-1, -1", //
            "0, 0", //
            "5, 5", //
            "100000, 100000" })
    public void parseInt__指定値に従った適切な値を返す(String value, int expected) {
        int actual = cut.parseInt(value);
        assertEquals(expected, actual);
    }
}
