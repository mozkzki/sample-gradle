package gradle.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * <pre>
 * 
 * mockitoでは下記のメンバーをモック出来ない。
 * 
 * - staticメンバー (staticメンバークラスも含む)
 * - finalメンバー
 * - メンバーインナークラス
 * - ローカルインナークラス
 * 
 * また、mockitoではローカル変数をモック出来ない。
 * モック出来るのは下記３パターン。
 * 
 * - コンストラクタ仮引数のモック
 * - setter仮引数のモック
 * - メンバーのモック (ただし前述の制限あり)
 * 
 * 参考：
 *   InjectMocks (Mockito 3.5.6 API)
 *   https://javadoc.io/static/org.mockito/mockito-core/3.5.6/org/mockito/InjectMocks.html
 * 
 *   Javaのインナークラスの使い方を現役エンジニアが解説【初心者向け】 | TechAcademyマガジン
 *   https://techacademy.jp/magazine/32398
 * 
 *   JAVA インナークラス・メンバークラス - Qiita
 *   https://qiita.com/icelandnono/items/d30bce978bea8939b8a1
 * 
 * </pre>
 */
@ExtendWith(MockitoExtension.class)
public class CompanyTest {
    @Mock
    User user1;

    @Mock
    User user2;

    @Mock
    User staticUser;

    @Mock
    User finalUser;

    @Mock
    User abstractUser;

    @Mock
    User interfaceUser;

    @Mock
    User localValUser;

    @Mock
    User constructUser;

    @InjectMocks
    Company company;

    /**
     * 普通のパターン.(モック可能)
     */
    @Test
    @DisplayName("getUser1Greeting__User1が挨拶する")
    public void testUser1Greeting() {
        when(user1.getGreeting()).thenReturn("mock_user_1");
        assertEquals("Hoge: mock_user_1", company.getUser1Greeting());
    }

    /**
     * 変数名で置き換わるオブジェクトが判定される.(モック可能)
     */
    @Test
    @DisplayName("getUser2Greeting__User2が挨拶する")
    public void testUser2Greeting() {
        when(user2.getGreeting()).thenReturn("mock_user_2");
        assertEquals("Hoge: mock_user_2", company.getUser2Greeting());
    }

    /**
     * mockitoは、staticのメンバ(フィールド)をモックできない.
     */
    @Test
    @DisplayName("getStaticUserGreeting__StaticUserが挨拶する")
    public void testStaticUserGreeting() {
        when(staticUser.getGreeting()).thenReturn("mock_static_user");
        // mockに置き換わらないため、下記は失敗する
        assertEquals("Hoge: mock_static_user", company.getStaticUserGreeting());
    }

    /**
     * mockitoは、finalのメンバ(フィールド)をモックできない.
     */
    @Test
    @DisplayName("getFinalUserGreeting__FinalUserが挨拶する")
    public void testFinalUserGreeting() {
        when(finalUser.getGreeting()).thenReturn("mock_final_user");
        // mockに置き換わらないため、下記は失敗する
        assertEquals("Hoge: mock_final_user", company.getFinalUserGreeting());
    }

    /**
     * mockitoは、abstractのメンバ(フィールド)をモックできる.(実体が非abstractの場合)
     */
    @Test
    @DisplayName("getAbstractUserGreeting__AbstractUserが挨拶する")
    public void testAbstractUserGreeting() {
        when(abstractUser.getGreeting()).thenReturn("mock_abstract_user");
        // abstractメンバ(実体は非abstractメンバ)がmockに置き換わる
        assertEquals("Hoge: mock_abstract_user", company.getAbstractUserGreeting());
    }

    /**
     * mockitoは、interfaceのメンバ(フィールド)をモックできる.(実体が非interfaceの場合)
     */
    @Test
    @DisplayName("getInterfaceUserGreeting__InterfaceUserが挨拶する")
    public void testInterfaceUserGreeting() {
        when(interfaceUser.getGreeting()).thenReturn("mock_interface_user");
        // interfaceメンバ(実体は非interfaceメンバ)がmockに置き換わる
        assertEquals("Hoge: mock_interface_user", company.getInterfaceUserGreeting());
    }

    /**
     * mockitoは、ローカル変数をモックできない.
     */
    @Test
    @DisplayName("getLocalValUserGreeting__LocalValUserが挨拶する")
    public void testLocalValUserGreeting() {
        when(localValUser.getGreeting()).thenReturn("mock_local_val_user");
        // mockに置き換わらないため、下記は失敗する
        assertEquals("Hoge: mock_local_val_user", company.getLocalValUserGreeting());
    }

    /**
     * 変数名で置き換わるオブジェクトが判定される.(モック可能)
     */
    @Test
    @DisplayName("getConstructUserGreeting__ConstructUserが挨拶する")
    public void testConstructUserGreeting() {
        User constUser = new User("construct-user");
        Company testCompany = new Company(constUser);
        // あえてモックに置き換えてみる
        when(constructUser.getGreeting()).thenReturn("mock_construct_user");
        assertEquals("Hoge: mock_construct_user", testCompany.getConstructUserGreeting());
    }

}