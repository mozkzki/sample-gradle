package sample.mockito;

public class Home {
    private String address;

    public Home() {
        System.out.println("Home / called constructor. arg count: 0");
        this.address = "default";
    }

    public Home(String address) {
        System.out.println("Home / called constructor. arg count: 1");
        this.address = address;
    }

    public Home(String address, String subAddress) {
        System.out.println("Home / called constructor. arg count: 2");
        this.address = address + " " + subAddress;
    }

    public String getAddress() {
        return this.address;
    }

}
