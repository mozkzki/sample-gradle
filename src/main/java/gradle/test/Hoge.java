package gradle.test;

public class Hoge {
    private Lib lib;

    public Hoge() {
        this.lib = new Lib("hoge");
    }

    public String getGreeting() {
        return this.lib.getGreeting();
    }

}