package gradle.test;

public class Hoge {
    private User lib;

    public Hoge() {
        this.lib = new User("hoge");
    }

    public String getGreeting() {
        return this.lib.getGreeting();
    }

}