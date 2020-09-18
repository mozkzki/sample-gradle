package sample.mockito;

public class SimplePersonHasSetter {
    // コンストラクタではセットしないフィールド
    private SimpleHome home;

    // homeをmockするために、デフォルトコンストラクタが存在する必要がある
    public SimplePersonHasSetter() {
    }

    // このコンストラクタ「のみ」が定義されている場合、テスト実行エラー
    public SimplePersonHasSetter(int test) {
    }

    public void setHome(SimpleHome home) {
        this.home = home;
        System.out.println("Result of getAddress: " + this.home.getAddress());
    }

}
