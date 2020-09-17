package sample.mockito;

public class SimplePerson {

    public SimplePerson(SimpleHome home) {
        System.out.println(home.getAddress());
    }

    public void doSomething(SimpleHome home) {
        System.out.println(home.getAddress());
    }
}
