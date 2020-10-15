package sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Spring フィールドインジェクション. (prototype)
 */
@Component
public class SpringPersonNoScope {
    // このSpringDogはDIコンテナによって挿入される
    @Autowired
    private SpringDog dog;

    /**
     * コンストラクタ.
     */
    public SpringPersonNoScope() {
    }

    public String getGreeting() {
        return "HashCode: " + this.hashCode() + ", I have " + this.dog.getName();
    }

}