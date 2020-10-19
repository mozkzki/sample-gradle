package sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Spring フィールドインジェクション.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpringPersonHasFieldNg {
    // このSpringDogはDIコンテナによって挿入される
    @Autowired
    private SpringDog dog;

    private String name;

    /**
     * コンストラクタ.
     * 
     * このコンストラクタのみが定義されている場合、DIコンテナから注入すべきオブジェクト(String)が見つからず
     * Springによる本クラスの生成は失敗する。
     */
    public SpringPersonHasFieldNg(String name) {
        this.name = name;
    }

    // 下記が定義されていれば、Springによる本クラスの生成は成功する

    // public SpringPersonHasFieldNg() {
    // this.name = "default";
    // }

    public String getGreeting() {
        return "I am " + name + ". I have dog. " + this.dog;
    }

}