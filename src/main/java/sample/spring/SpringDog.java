package sample.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringDog {
    private String name;

    public SpringDog() {
        this.name = "dog1";
    }

    public String getName() {
        return this.name;
    }

}