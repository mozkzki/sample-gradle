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

public class Sample {

    @Test
    @Description("失敗するテスト")
    void fail() {
        assertEquals(10, 8);
    }

    static class StaticClass {
        @Test
        void fail() {
            assertEquals(10, 8);
        }
    }

    static class StaticTest {
        @Test
        void fail() {
            assertEquals(10, 8);
        }
    }

    class InnerTest {
        @Test
        void fail() {
            assertEquals(10, 8);
        }
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before ALL.");
    }

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

    @Test
    void employeeTest() {
        assertAll("name", () -> assertEquals("kato", "kato"), () -> assertEquals("yutaka", "yutaka"));
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