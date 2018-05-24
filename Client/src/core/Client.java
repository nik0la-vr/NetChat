package core;

public class Client {
    private int port;
    private String ip;
    private String name;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getName() {
        return name;
    }

}
