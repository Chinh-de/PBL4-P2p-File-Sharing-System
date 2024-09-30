package p2pfilesharing.client.BLL;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class PeerListener extends Thread{
    int port;
    ServerSocket peerListener;
    boolean running = true;
    public PeerListener(int port) { this.port = port; }

    @Override
    public void run() {
        try {
            peerListener = new ServerSocket(this.port);


            while (running)
            {
                Socket socket = peerListener.accept();
                new Thread(new PeerHandler(socket)).start();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                peerListener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopListener() {
        running = false;
        try {
            peerListener.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
