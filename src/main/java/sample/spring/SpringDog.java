package sample.spring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpringDog {
    private String name;

    public SpringDog() {
        this.name = "dog1";
    }

    public String getName() {
        return this.name + "(" + this.hashCode() + ")";
    }

}