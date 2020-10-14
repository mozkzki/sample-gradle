package sample.mockito;

public class VariousMockPattern {
    private VariousMockPatternParam param;

    public VariousMockPattern(VariousMockPatternParam param) {
        this.param = param;
    }

    public String getParamValue(String key) {
        return this.param.getValue(key);
    }

    public int parseInt(String value) {
        return Integer.parseInt(value);
    }

    public void checkParam(String key) {
        this.param.check(key);
    }

}
