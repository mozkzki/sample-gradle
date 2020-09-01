package gradle.test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // prototypeでないとgetBean(StringPerson.class, "hoge")でエラーになる
public class SpringPerson {

    @Autowired
    private SpringDog dog;

    private String name;

    public SpringPerson(String name) {
        this.name = name;
    }

    public String getGreeting() {
        return "my name is " + this.name + ". i have " + this.dog.getName();
    }

}