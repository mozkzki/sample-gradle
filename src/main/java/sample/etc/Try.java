package sample.etc;

public class Try {
    public static void main(String[] args) {
        Try t = new Try();
        StringBuffer i = t.tryCatch();
        System.out.println(i.toString());
    }

    public StringBuffer tryCatch() {
        System.out.println("スタート！");
        StringBuffer str = null;// nullにする
        try {
            str.toString(); // NullPointerException
            System.out.println("ほげほげ");
            return new StringBuffer("Try句からのリターンです。");
        } catch (Exception e) {
            System.out.println("ふがふが");
            // return "Catch句からのリターンです。";
            throw new IllegalStateException("Catch句からの例外です");
        } finally {
            System.out.println("finally句が実行されました。");
        }
    }
}