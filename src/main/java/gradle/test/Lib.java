package gradle.test;

public class Lib {
    private final String name;

    public Lib(String name) {
        this.name = name;
    }

    public String getGreeting() {
        return this.name;
    }
}