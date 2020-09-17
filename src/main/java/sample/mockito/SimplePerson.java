package sample.mockito;

public class SimplePerson {

    public SimplePerson(SimpleHome home) {
        System.out.println(home.getAddress());
        // homeはメンバーとして保持しない
    }

    public String getAddress(SimpleHome home) {
        System.out.println(home.getAddress());
        return home.getAddress();
    }
}
