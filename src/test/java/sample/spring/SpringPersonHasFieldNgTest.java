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
public class SpringPersonHasFieldNgTest {
    @Autowired
    private SpringPersonHasFieldNg cut;

    /**
     * Autowiredで生成。メンバー(SpringDog)はDIコンテナが注入してくれるはずだが、 cut(person)自体の生成に失敗する。
     * 理由はgetBeanの場合と同じ。
     */
    @Test
    void test1() {
        System.out.println(cut.getGreeting());
    }

    /**
     * getBeanで生成する。メンバー(SpringDog)はDIコンテナが注入してくれるはずだが、 getBeanでのperson自体の生成に失敗する。
     * テスト対象クラスが、primitive型の引数付きコンストラクタしか持っていない場合、
     * (コンテナにコンストラクタインジェクションで注入すべきオブジェクトが見つからない場合) 生成に失敗する。
     */
    @Test
    void test2() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            SpringPersonHasFieldNg person = context.getBean(SpringPersonHasFieldNg.class);
            System.out.println(person.getGreeting());
        }
    }

    /**
     * 普通にnewするとDIコンテナによるメンバー(SpringDog)の注入がされないのでエラーになる。
     */
    @Test
    void test3() {
        SpringPersonHasFieldNg person = new SpringPersonHasFieldNg("test");
        System.out.println(person.getGreeting());
    }
}
