package sample.mockito;

public class NormalPerson {
    private NormalHome home;

    public NormalPerson(NormalHome home) {
        System.out.println("address: " + home.getAddress());
        this.home = home;
    }

    public String getAddress() {
        System.out.println(home.getAddress());
        return home.getAddress();
    }
}
