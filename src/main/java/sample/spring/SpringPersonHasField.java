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
public class SpringPersonHasField {
    // このSpringDogはDIコンテナによって挿入される
    @Autowired
    private SpringDog dog;

    /**
     * コンストラクタ.
     */
    public SpringPersonHasField() {
    }

    public String getGreeting() {
        return "I have dog. " + this.dog;
    }

}