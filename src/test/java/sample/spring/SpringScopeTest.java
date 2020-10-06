package sample.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = AppConfig.class) // 忘れやすいので注意
public class SpringScopeTest {
    @Autowired
    private SpringPersonPrototype prototype1;
    @Autowired
    private SpringPersonPrototype prototype2;
    @Autowired
    private SpringPersonSingleton single1;
    @Autowired
    private SpringPersonSingleton single2;

    /**
     * Prototype指定のBeanを複数回作ると、別のインスタンスが取得される
     */
    @Test
    void testCreatePrototypeByAutowired() {
        System.out.println(prototype1.getGreeting());
        System.out.println(prototype2.getGreeting());
    }

    /**
     * Singleton指定のBeanを複数回作っても、同じインスタンスが取得される
     * 
     * 注意： メンバー(SpringDog)がprototype指定であっても、
     * 保持するクラスがsingletonであれば、当然メンバーも同じインスタンスになる
     */
    @Test
    void testCreateSingletonByAutowired() {
        System.out.println(single1.getGreeting());
        System.out.println(single2.getGreeting());
    }

    /**
     * Prototype指定のBeanを複数回作ると、別のインスタンスが取得される
     */
    @Test
    void testCreatePrototypeByGetBean() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {

            SpringPersonPrototype person = context.getBean(SpringPersonPrototype.class);
            System.out.println(person.getGreeting());

            SpringPersonPrototype person2 = context.getBean(SpringPersonPrototype.class);
            System.out.println(person2.getGreeting());

            // 結果
            // > HashCode: 2121645869, I have dog1
            // > HashCode: 945747249, I have dog1

        }
    }

    /**
     * Singleton指定のBeanを複数回作っても、同じインスタンスが取得される
     * 
     * 注意： メンバー(SpringDog)がprototype指定であっても、
     * 保持するクラスがsingletonであれば、当然メンバーも同じインスタンスになる
     */
    @Test
    void testCreateSingletonByGetBean() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {

            SpringPersonSingleton person = context.getBean(SpringPersonSingleton.class);
            System.out.println(person.getGreeting());

            SpringPersonSingleton person2 = context.getBean(SpringPersonSingleton.class);
            System.out.println(person2.getGreeting());

            // 結果
            // > HashCode: 22790969, I have dog1
            // > HashCode: 22790969, I have dog1
        }
    }

}
