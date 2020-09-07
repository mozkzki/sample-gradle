package gradle.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

public class BeforeAndAfter {

    // 最初に一度実行される
    @BeforeAll
    static void beforeAll() {
        System.out.println("before ALL.");
    }

    // それぞれのテストメソッドの前に実行される
    @BeforeEach
    void beforeEach() {
        System.out.println("before EACH.");
    }

    @Test
    @DisplayName("test1のテスト")
    void test1() {
        System.out.println("    test1()");
    }

    @Test
    @DisplayName("test2のテスト")
    void test2() {
        System.out.println("    test2()");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after EACH.");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after ALL.");
    }
}