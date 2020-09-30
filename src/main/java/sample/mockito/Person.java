package sample.mockito;

public class Person {
    private Home home;

    public Person() {
        System.out.println("Person / called constructor. arg count: 0");
        this.home = new Home();

    }

    public Person(String address) {
        System.out.println("Person / called constructor. arg count: 1");
        this.home = new Home(address);
    }

    public Person(String address, String subAddress) {
        System.out.println("Person / called constructor. arg count: 2");
        this.home = new Home(address, subAddress);
    }

    public void show() {
        System.out.println(home.getAddress());
    }

}
