package sample.spring;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

public class SprintPersonTest {

    @SpringJUnitConfig(classes = AppConfig.class)
    @Nested
    class GetBeanTest {
        @Autowired
        ApplicationContext ctx;

        @Test
        void testGetGreeting() {
            // 引数付きコンストラクタしかないのでgetBean
            SpringPrototypePerson person = ctx.getBean(SpringPrototypePerson.class, "aaaaaaa");
            System.out.println(person.getGreeting());
            SpringDog dog = ctx.getBean(SpringDog.class);
            System.out.println("dog=" + dog);
        }
    }

    @SpringJUnitConfig(classes = AppConfig.class)
    @Nested
    class AutowiredTest {
        // 引数付きコンストラクタしかないので@Autowiredが使えない
        @Autowired
        SpringPrototypePerson person;

        @Test
        void testGetGreeting() {

            // 引数付きコンストラクタしかないのでgetBean
            // SpringPrototypePerson person = ctx.getBean(SpringPrototypePerson.class,
            // "aaaaaaa");
            System.out.println(person.getGreeting());
        }
    }

    @SpringJUnitConfig(classes = AppConfig.class) // 必須, @Nestedごとに付与が必要
    @Nested
    class AutowiredSingletonBeanTest {
        // DIコンテナで生成されたBEANが注入される
        @Autowired
        SpringSingletonPerson person;

        // DIコンテナで生成されたBEANが注入される
        @Autowired
        SpringSingletonPersonHasConstructor personHasConst;

        @Test
        void testGetGreeting() {
            System.out.println(">>>>>>> start");
            person.getGreeting();
            personHasConst.getGreeting();
            System.out.println(">>>>>>> end");
        }
    }

    @SpringJUnitConfig(classes = AppConfig.class) // 必須, @Nestedごとに付与が必要
    @Nested
    class GetSingletonBeanTest {
        @Autowired
        ApplicationContext ctx;

        // singletonのBEANは、DIコンテナ起動時に生成されるので、
        // getBeanしてもコンストラクタは呼び出されない(生成済みのインスタンスが返されるのみ)。
        @Test
        void testGetBean() {
            System.out.println(">>>>>>> start");

            // ここでコンストラクタが呼ばれることはない
            SpringSingletonPerson singletonPerson = ctx.getBean(SpringSingletonPerson.class);
            singletonPerson.getGreeting();

            // ここでコンストラクタが呼ばれることはない
            SpringSingletonPersonHasConstructor singletonPersonHasConst = ctx
                    .getBean(SpringSingletonPersonHasConstructor.class);
            singletonPersonHasConst.getGreeting();

            System.out.println(">>>>>>> end");
        }

        // singletonのBEANは、DIコンテナ起動時に生成されるので、
        // getBeanしてもコンストラクタは呼び出されない(生成済みのインスタンスが返されるのみ)。
        // パラメータ付きでgetBeanした場合も同じ。
        @Test
        void testGetBeanWithParam() {
            System.out.println(">>>>>>> start");

            // ここでコンストラクタが呼ばれることはない
            SpringSingletonPerson singletonPerson = ctx.getBean(SpringSingletonPerson.class, "hoge");
            singletonPerson.getGreeting();

            // ここでコンストラクタが呼ばれることはない
            SpringSingletonPersonHasConstructor singletonPersonHasConst = ctx
                    .getBean(SpringSingletonPersonHasConstructor.class, "hoge");
            singletonPersonHasConst.getGreeting();

            System.out.println(">>>>>>> end");
        }
    }

}