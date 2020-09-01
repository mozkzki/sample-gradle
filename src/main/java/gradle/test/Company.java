package gradle.test;

public class Company {
    private String name;
    private User user1;
    private User user2;
    private static User staticUser;
    private final User finalUser;
    private AbstractUser abstractUser;
    private IUser interfaceUser;
    private User constUser;

    public Company() {
        this.name = "Hoge";
        this.user1 = new User("private-user-1");
        this.user2 = new User("private-user-2");
        Company.staticUser = new User("private-static-user");
        this.finalUser = new User("final-user");
        this.abstractUser = new User("abstract-user");
        this.interfaceUser = new User("interface-user");
    }

    public Company(User constructUser) {
        this.constUser = constructUser;
        this.finalUser = new User("final-user");
    }

    public String getUser1Greeting() {
        return this.name + ": " + this.user1.getGreeting();
    }

    public String getUser2Greeting() {
        return this.name + ": " + this.user2.getGreeting();
    }

    public String getStaticUserGreeting() {
        return this.name + ": " + staticUser.getGreeting();
    }

    public String getFinalUserGreeting() {
        return this.name + ": " + finalUser.getGreeting();
    }

    public String getAbstractUserGreeting() {
        return this.name + ": " + abstractUser.getGreeting();
    }

    public String getInterfaceUserGreeting() {
        return this.name + ": " + interfaceUser.getGreeting();
    }

    public String getLocalValUserGreeting() {
        User localValUser = new User("local-val-user");
        return this.name + ": " + localValUser.getGreeting();
    }

    public String getConstructUserGreeting() {
        return this.name + ": " + constUser.getGreeting();
    }

}