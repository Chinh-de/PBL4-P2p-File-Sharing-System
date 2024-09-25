package p2pfilesharing.client;

import p2pfilesharing.client.BLL.serverHandler;
import p2pfilesharing.client.GUI.LoginForm;

public class client {
    public static void main(String[] args) {
        serverHandler svHandler = new serverHandler();
        svHandler.start();

    }
}
