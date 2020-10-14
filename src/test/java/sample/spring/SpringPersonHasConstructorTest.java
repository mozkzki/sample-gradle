package sample.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * SpringPersonHasConstructorは、自身のメンバー(SpringDog)をコンストラクターインジェクションしているクラス。
 * SpringPersonHasConstructorを生成すると、メンバー(SpringDog)がDIコンテナにより挿入される。
 */
@SpringJUnitConfig(classes = AppConfig.class) // 忘れやすいので注意
public class SpringPersonHasConstructorTest {
    @Autowired
    private SpringPersonHasConstructor cut;

    /**
     * Autowiredで生成。コンストラクタに渡すSpringDogはDIコンテナが注入してくれる。
     */
    @Test
    void test1() {
        System.out.println(cut.getGreeting());

    }

    /**
     * getBeanで生成する。コンストラクタに渡すSpringDogはDIコンテナが注入してくれる。
     */
    @Test
    void test2() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // コンストラクタインジェクションなら、getBeanで引数(SpringDog)を渡す必要はない
            SpringPersonHasConstructor person = context.getBean(SpringPersonHasConstructor.class);
            System.out.println(person.getGreeting());
        }
    }

    /**
     * もちろん、普通にnewしても動く。
     */
    @Test
    void test3() {
        SpringDog dog = new SpringDog();
        SpringPersonHasConstructor person = new SpringPersonHasConstructor(dog);
        System.out.println(person.getGreeting());
    }
}
