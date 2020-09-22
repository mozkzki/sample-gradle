package sample.mockito;

public class NormalHome {
    private String address;

    public NormalHome() {
        System.out.println("NormalHome / called constructor. arg count: 0");
        this.address = "default";
    }

    public String getAddress() {
        return this.address;
    }

}
