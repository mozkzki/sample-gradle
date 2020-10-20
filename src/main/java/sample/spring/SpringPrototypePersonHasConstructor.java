package sample.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpringPrototypePersonHasConstructor implements InitializingBean {
    private SpringDog dog; // @Autowiredしない

    @Value("${sample.name:foo}")
    private String name;

    // BEANを引数に取るコンストラクタ。
    // DIコンテナにより「コンストラクタインジェクション」される。
    // コンストラクタ内で、注入されたパラメータが使える。
    // (デフォルトコンストラクタ形式だとafterPropertiesSetで処理しないといけない)
    //
    // 要注意
    //
    // ・コンストラクタに@Autowiredをつけない場合、下記の動作になるようだ
    // ・デフォルトコンストラクタが優先して呼ばれる
    // ・複数のコンストラクタがあるとエラーになる
    //
    // なので、使用したいコンストラクタに@Autowiredをつけたほうが良い
    //
    @Autowired
    public SpringPrototypePersonHasConstructor(SpringDog dog) {
        this.name = null;
        this.dog = dog;
        System.out.println(this + "[prototype][constructer]");
    }

    public SpringPrototypePersonHasConstructor() {
        // @Autowiredをつけたコンストラクタが呼ばれるので、こっちは呼ばれない
    }

    public SpringPrototypePersonHasConstructor(SpringDog dog, int test) {
        // @Autowiredをつけたコンストラクタが呼ばれるので、こっちは呼ばれない
    }

    // nameはafterPropertiesSet内ではDIコンテナによりセット済み。
    // コンストラクタ内ではnullなので注意。
    // dogはコンストラクタ内で注入される。
    public void afterPropertiesSet() {
        System.out.println(this + "[prototype][afterprop]");
    }

    public String getGreeting() {
        System.out.println(this + "[prototype][getgreet]");
        return "My name is " + this.name + ". I have dog. " + this.dog;
    }

    @Override
    public String toString() {
        return "[Person: " + this.name + "(hash=" + this.hashCode() + ")] " + this.dog;
    }

}