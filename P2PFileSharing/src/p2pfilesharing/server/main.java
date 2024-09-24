package p2pfilesharing.server;

import p2pfilesharing.server.BLL.clientHandler;
import p2pfilesharing.server.BLL.server;

public class main {
    public static void main(String[] args) {
        server server = new server();
        server.startServer();
    }
}
