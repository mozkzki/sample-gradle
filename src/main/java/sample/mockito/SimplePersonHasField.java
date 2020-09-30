package sample.mockito;

public class SimplePersonHasField {
    // コンストラクタではセットしないフィールド
    private SimpleHome home;

    // homeをmockするために、デフォルトコンストラクタが存在する必要がある
    public SimplePersonHasField() {
    }

    // このコンストラクタ「のみ」が定義されている場合、テスト実行エラー
    public SimplePersonHasField(int test) {
    }

    // setterは無しでもmock可能

    // public void setHome(SimpleHome home) {
    // this.home = home;
    // }

    public String getAddress() {
        System.out.println(home.getAddress());
        return home.getAddress();
    }
}
