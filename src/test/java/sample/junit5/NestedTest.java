package sample.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Junit5のテストクラス階層化の例.
 * 
 * @Nestedをつけると階層化できるが、非static innerクラスにする必要がある。static innerクラスだとテストが実行されない。
 */
public class NestedTest {
    private int count = 8;

    // このテストはstaticなので実行されない
    @Nested
    static class StaticClass {
        @Test
        @DisplayName("static inner test (name: 'StaticClass'")
        void fail() {
            assertEquals(10, 8);
        }
    }

    // このテストはstaticなので実行されない
    @Nested
    static class StaticTest {
        @Test
        @DisplayName("static inner test (name: 'StaticTest')")
        void fail() {
            assertEquals(10, 8);
        }
    }

    // このテストは実行される（失敗する）
    @Nested
    class InnerTest {
        @Test
        @DisplayName("non static inner test (name: 'InnerTest') - 1")
        void fail() {
            // 外部クラスのメンバーを参照できる
            assertEquals(10, count);
        }
    }

    // このテストは実行される
    @Nested
    class InnerTest2 {
        private int count = 10;

        @Test
        @DisplayName("non static inner test (name: 'InnerTest') - 2")
        void success() {
            // 内部クラスのメンバーで上書き
            assertEquals(10, count);
        }
    }

}