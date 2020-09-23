package sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Spring コンストラクタインジェクション.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpringPersonHasConstructor {
    private final SpringDog dog;

    /**
     * コンストラクタ.
     * 
     * このSpringDogはDIコンテナによって挿入される。 呼び出し元はテストクラスを参照。
     */
    @Autowired
    public SpringPersonHasConstructor(SpringDog dog) {
        this.dog = dog;
    }

    public String getGreeting() {
        return "I have " + this.dog.getName();
    }

}