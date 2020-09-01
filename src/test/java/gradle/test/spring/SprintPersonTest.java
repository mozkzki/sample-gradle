package gradle.test.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class SprintPersonTest {
    @Autowired
    ApplicationContext ctx;

    @Test
    void testGetGreeting() {
        SpringPerson person = ctx.getBean(SpringPerson.class, "aaaaaaa");
        person.getGreeting();
    }

}