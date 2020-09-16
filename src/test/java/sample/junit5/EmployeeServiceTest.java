package sample.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EmployeeServiceTest {
    EmployeeService service;

    @BeforeEach
    void setUp() {
        this.service = new EmployeeService();
    }

    @Test
    @DisplayName("IDで検索する")
    void testFindById() {
        Employee employee = service.findById(1);
        assertAll("employee", () -> assertEquals("Yutaka", employee.getFirstName()),
                () -> assertEquals("Kato", employee.getLastName()));
    }

    @Nested
    @DisplayName("名前で検索する")
    class FindByFirstNameStartingWithTest {
        @Test
        @DisplayName("firstNameが「E」から始まる")
        void startsWithE() {
            // アウタークラスのフィールドにアクセスできる
            java.util.List<Employee> employees = service.findByFirstNameStartingWith("E");
            assertEquals(employees.size(), 1);
            Employee employee = employees.get(0);
            assertAll("employee", () -> assertEquals("Eiji", employee.getFirstName()),
                    () -> assertEquals("Yoshikawa", employee.getLastName()));
        }
    }

}
