package sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Spring フィールドインジェクション. (singleton)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SpringPersonSingleton {
    // このSpringDogはDIコンテナによって挿入される
    @Autowired
    private SpringDog dog;

    /**
     * コンストラクタ.
     */
    public SpringPersonSingleton() {
    }

    public String getGreeting() {
        return "HashCode: " + this.hashCode() + ", I have dog. " + this.dog;
    }

}