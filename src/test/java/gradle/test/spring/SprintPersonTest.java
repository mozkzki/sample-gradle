package gradle.test.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = TestConfig.class)
public class SprintPersonTest {
    @Autowired
    ApplicationContext ctx;

    // 引数付きコンストラクタしかないので@Autowiredが使えない
    // @Autowired
    // SpringPerson person;

    @Value("${test_param:foo}")
    private String testParam;

    @Test
    void testGetGreeting() {
        // 引数付きコンストラクタしかないのでgetBean
        SpringPerson person = ctx.getBean(SpringPerson.class, "aaaaaaa");

        System.out.println(testParam);
        person.getGreeting();
    }

}