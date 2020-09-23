package sample.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * SpringPersonHasConstructorは、自身のメンバー(SpringDog)をコンストラクターインジェクションしているクラス。
 * SpringPersonHasConstructorを生成すると、メンバー(SpringDog)がDIコンテナにより挿入される。
 */
public class SpringPersonHasConstructorTest {
    @Autowired
    private SpringPersonHasConstructor person;

    /**
     * Autowiredで生成。 これは失敗する。personは引数付きコンストラクタしか無いので Autowiredでは生成出来ないようだ。
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
            SpringPersonHasConstructor person2 = context.getBean(SpringPersonHasConstructor.class);
            System.out.println(person2.getGreeting());
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
