package sample.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// 引数付きコンストラクタしか持たないクラスの場合、prototypeにする必要がある
// prototypeでないとgetBean(StringPerson.class, "hoge")でエラーになる
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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