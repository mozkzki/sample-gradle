package sample.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * SpringPersonHasFieldは、自身のメンバー(SpringDog)をフィールドインジェクションしているクラス。
 * SpringPersonHasFieldを生成すると、メンバー(SpringDog)がDIコンテナにより挿入される。
 */
@SpringJUnitConfig(classes = AppConfig.class) // 忘れやすいので注意
public class SpringPersonHasFieldTest {
    @Autowired
    private SpringPersonHasField person;

    /**
     * Autowiredで生成。
     */
    @Test
    void test1() {
        System.out.println(person.getGreeting());
    }

    /**
     * getBeanで生成する。これは成功する。コンストラクタに渡すSpringDogはDIコンテナが勝手に用意してくれる。
     */
    @Test
    void test2() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            SpringPersonHasField person2 = context.getBean(SpringPersonHasField.class);
            System.out.println(person2.getGreeting());
        }
    }

    /**
     * 普通にnewするとDIコンテナによるメンバー(SpringDog)の挿入がされないのでエラーになる。
     */
    @Test
    void test3() {
        SpringPersonHasField person = new SpringPersonHasField();
        System.out.println(person.getGreeting());
    }
}
