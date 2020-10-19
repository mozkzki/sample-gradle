package sample.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SpringSingletonPersonHasConstructor implements InitializingBean {
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
    public SpringSingletonPersonHasConstructor(SpringDog dog) {
        this.name = null;
        this.dog = dog;
        System.out.println("[singleton has const][constructer] name=" + this.name + ", dog=" + this.dog);
    }

    public SpringSingletonPersonHasConstructor() {
        // @Autowiredをつけたコンストラクタが呼ばれるので、こっちは呼ばれない
    }

    public SpringSingletonPersonHasConstructor(SpringDog dog, int test) {
        // @Autowiredをつけたコンストラクタが呼ばれるので、こっちは呼ばれない
    }

    // nameはafterPropertiesSet内ではDIコンテナによりセット済み。
    // コンストラクタ内ではnullなので注意。
    // dogはコンストラクタ内で注入される。
    public void afterPropertiesSet() {
        System.out.println("[singleton has const][afterprop] name=" + this.name + ", dog=" + this.dog);
    }

    public String getGreeting() {
        System.out.println("[singleton has const][getgreeting] name=" + this.name + ", dog=" + this.dog);
        return "My name is " + this.name + ". I have dog. " + this.dog;
    }

}