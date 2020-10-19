package sample.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// 引数付きコンストラクタしか持たないクラスの場合、prototypeにする必要がある
// prototypeでないとgetBean(StringPerson.class, "hoge")でエラーになる
// @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SpringPrototypePerson implements InitializingBean {

    @Autowired
    private SpringDog dog;

    @Value("${sample.name:foo}")
    private String name;

    public SpringPrototypePerson() {
        this.name = null;
        System.out.println("[prototype][default constructer] name=" + this.name + ", dog=" + this.dog);
    }

    public SpringPrototypePerson(String name) {
        this.name = name;
        System.out.println("[prototype][constructer] name=" + this.name + ", dog=" + this.dog);
    }

    public void afterPropertiesSet() {
        System.out.println("[prototype][afterprop] name=" + this.name + ", dog=" + this.dog);
    }

    public String getGreeting() {
        System.out.println("[prototype][getgreet] name=" + this.name + ", dog=" + this.dog);
        return "my name is " + this.name + ". i have dog. " + this.dog;
    }

}