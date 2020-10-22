package sample.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpringDog {
    @Value("${sample.name:pochi}")
    private String name;

    public SpringDog() {
        this.name = "no name";
    }

    @Override
    public String toString() {
        return "[Dog: " + this.name + "(hash=" + this.hashCode() + ")] ";
    }

    public static String getBar() {
        return "bar";
    }

}