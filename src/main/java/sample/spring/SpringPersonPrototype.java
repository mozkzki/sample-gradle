package sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Spring フィールドインジェクション. (prototype)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpringPersonPrototype {
    // このSpringDogはDIコンテナによって挿入される
    @Autowired
    private SpringDog dog;

    /**
     * コンストラクタ.
     */
    public SpringPersonPrototype() {
    }

    public String getGreeting() {
        return "HashCode: " + this.hashCode() + ", I have " + this.dog.getName();
    }

}