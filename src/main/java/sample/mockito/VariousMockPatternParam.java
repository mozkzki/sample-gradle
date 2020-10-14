package sample.mockito;

public class VariousMockPatternParam {

    public VariousMockPatternParam() {
    }

    public String getValue(String key) {
        if (key == "yes") {
            return "yesValue";
        } else {
            return "otherValue";
        }
    }

    public void check(String key) {
        if (key == "yes") {
            System.out.println("key is yes.");
        } else {
            throw new IllegalStateException("key is invalid.");
        }
    }

}
