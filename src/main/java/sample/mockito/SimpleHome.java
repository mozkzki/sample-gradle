package sample.mockito;

public class SimpleHome {
    private String address;

    public SimpleHome() {
        System.out.println("SimpleHome / called constructor. arg count: 0");
        this.address = "default";
    }

    public String getAddress() {
        return this.address;
    }

}
