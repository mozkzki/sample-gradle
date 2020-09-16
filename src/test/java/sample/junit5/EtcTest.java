package sample.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EtcTest {

    @Test
    @DisplayName("失敗するテスト")
    void fail() {
        assertEquals(10, 8);
    }

    @Test
    @DisplayName("複数項目まとめてチェックするテスト")
    void AllTest() {
        assertAll("heading_string", () -> assertNotEquals("expected_value", "actual_value"),
                () -> assertNotEquals("foo", "bar"));
    }

}