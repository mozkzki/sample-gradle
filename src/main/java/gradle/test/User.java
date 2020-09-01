package gradle.test;

public class User extends AbstractUser implements IUser {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getGreeting() {
        return "my name is " + this.name;
    }
}