package gradle.test;

public class Company {
    private String name;
    private User user;

    public Company() {
        this.name = "Hoge";
        this.user = new User("User1");
    }

    public String getGreeting() {
        return this.name + ": " + this.user.getGreeting();
    }

}