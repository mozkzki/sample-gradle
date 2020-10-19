package sample.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// SCOPE_SINGLETONの場合、DIコンテナ起動時にインスタンス化できる必要がある。
//
// プリミティブ型やString型の引数を取るコンストラクタ「のみ」しか
// 持っていない場合などはDIコンテナ起動時にエラーとなる。
//
// <詳細>
// このBEANを明示的に使っている・いないに関わらず
// DIコンテナ起動時のBEAN検索で、全BEAN (@Component等ついてるもの)の初期化が行われる。
// その時、プリミティブ型やString型の引数を取るコンストラクタ「のみ」しか
// 定義されていないと、それらを注入できずエラーになる。
// (コンストラクタインジェクションしようとして、String型のBEANを作るのに失敗する)
//
// <方針>
// SCOPE_SINGLETONの場合、プリミティブ型やString型の引数を取るコンストラクタは
// 作らずに＠Value等ですべてのパラメータを外部から注入すべき。
// 
// 下記２パターンにすべき
// ・デフォルトコンストラクタのみ
// ・コンストラクタインジェクション
// 
// また、SCOPE_SINGLETONの場合、getBeanではコンストラクタが呼び出されることはない
// (DIコンテナ起動時に生成済みのインスタンスが返されるだけな)ので、それを前提にしないこと。
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SpringSingletonPerson implements InitializingBean {
    @Autowired
    private SpringDog dog;

    @Value("${sample.name:foo}")
    private String name;

    // SCOPE_SINGLETONの場合、デフォルトコンストラクタを作るか、
    // コンストラクタインジェクション(BEANを引数に取るコンストラクタを用意)にすればOK
    // → SpringSingletonPersonHasConstructor 参照
    public SpringSingletonPerson() {
        this.name = null;
        System.out.println("[singleton][default constructer] name=" + this.name + ", dog=" + this.dog);
    }

    // SCOPE_SINGLETONの場合、下記のコンストラクタ「のみ」しか定義されていないと
    // DIコンテナ初期化時にエラーになる。
    public SpringSingletonPerson(String name) {
        this.name = name;
        System.out.println("[singleton][constructer] name=" + this.name + ", dog=" + this.dog);
    }

    // nameとdogは、afterPropertiesSet内ではDIコンテナによりセット済み。
    // コンストラクタ内ではnullなので注意。
    public void afterPropertiesSet() {
        System.out.println("[singleton][afterprop] name=" + this.name + ", dog=" + this.dog);
    }

    public String getGreeting() {
        System.out.println("[singleton][getgreeting] name=" + this.name + ", dog=" + this.dog);
        return "My name is " + this.name + ". I have dog. " + this.dog;
    }

}