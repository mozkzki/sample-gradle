package sample.spring;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

public class SprintPersonTest {

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

        @SpringJUnitConfig(classes = AppConfig.class) // 必須, @Nestedごとに付与が必要
        @Nested
        class AnotherAutowiredSingletonBeanTest {
            // DIコンテナで生成されたBEANが注入される
            // AutowiredSingletonBeanTestで使ったものと同じインスタンス
            @Autowired
            SpringSingletonPerson person;

            // DIコンテナで生成されたBEANが注入される
            // AutowiredSingletonBeanTestで使ったものと同じインスタンス
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

    // このテストクラスのみを実行しただけで、singletonのBEANの
    // コンストラクタが呼ばれる。singletonのBEANは
    // DIコンテナによってstaticに用意されるようだ。
    // prototypeのBEANは@Autowiredしたテストクラスでのみ生成される。
    @SpringJUnitConfig(classes = AppConfig.class) // 必須, @Nestedごとに付与が必要
    @Nested
    class AutowiredPrototypeBeanTest {
        // DIコンテナで生成されたBEANが注入される
        @Autowired
        SpringPrototypePerson person;

        // DIコンテナで生成されたBEANが注入される
        @Autowired
        SpringPrototypePersonHasConstructor personHasConst;

        @Test
        void testGetGreeting() {
            System.out.println(">>>>>>> start");
            person.getGreeting();
            personHasConst.getGreeting();
            System.out.println(">>>>>>> end");
        }

        @SpringJUnitConfig(classes = AppConfig.class) // 必須, @Nestedごとに付与が必要
        @Nested
        class AnotherAutowiredPrototypeBeanTest {
            // DIコンテナで生成されたBEANが注入される
            // AutowiredPrototypeBeanTestで使われたものとは別のインスタンス
            // @Autowiredを書くごとに生成される
            @Autowired
            SpringPrototypePerson person;

            // DIコンテナで生成されたBEANが注入される
            // AutowiredPrototypeBeanTestで使われたものとは別のインスタンス
            // @Autowiredを書くごとに生成される
            @Autowired
            SpringPrototypePersonHasConstructor personHasConst;

            @Test
            void testGetGreeting() {
                System.out.println(">>>>>>> start");
                person.getGreeting();
                personHasConst.getGreeting();
                System.out.println(">>>>>>> end");
            }
        }
    }

    @SpringJUnitConfig(classes = AppConfig.class)
    @Nested
    class GetPrototypeBeanTest {
        @Autowired
        ApplicationContext ctx;

        // prototypeのBEANは、DIコンテナ起動時ではなくgetBean呼び出し時に生成される。
        @Test
        void testGetGreeting() {
            System.out.println(">>>>>>> start");

            // ここでコンストラクタが呼ばれる
            SpringPrototypePerson prototypePerson = ctx.getBean(SpringPrototypePerson.class);
            prototypePerson.getGreeting();

            // ここでコンストラクタが呼ばれる
            SpringPrototypePersonHasConstructor prototypePersonHasConst = ctx
                    .getBean(SpringPrototypePersonHasConstructor.class);
            prototypePersonHasConst.getGreeting();

            System.out.println(">>>>>>> end");
        }

        // prototypeのBEANは、DIコンテナ起動時ではなくgetBean呼び出し時に生成される。
        @Test
        void testGetBeanWithParam() {
            System.out.println(">>>>>>> start");

            // ここでコンストラクタが呼ばれる
            SpringPrototypePerson prototypePerson = ctx.getBean(SpringPrototypePerson.class, "hoge");
            prototypePerson.getGreeting();

            // ここでコンストラクタが呼ばれる
            SpringPrototypePersonHasConstructor prototypePersonHasConst = ctx
                    .getBean(SpringPrototypePersonHasConstructor.class, "hoge");
            prototypePersonHasConst.getGreeting();

            System.out.println(">>>>>>> end");
        }
    }
}