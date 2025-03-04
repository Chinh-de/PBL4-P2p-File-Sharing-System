package p2pfilesharing.server.BLL;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private static final int SERVER_PORT = 6969;
    private ServerSocket serverSocket;
    private boolean isRunning;

    // Mở server
    public void startServer() {
        try {
            //Lưu nhật ký server khởi động
            logBLL.getInstance().saveLog(null,"Start sever");
            serverSocket = new ServerSocket(SERVER_PORT);
            isRunning = true;
            System.out.println("Server is listening on IP: " + InetAddress.getLocalHost().getHostAddress() + " and port: " + SERVER_PORT);


            while (isRunning) {

                // Chờ đợi kết nối từ client
                Socket clientSocket = serverSocket.accept();
                //Vì accept blocking, phải chờ server xử lí từng kết nối 1, xong mới tới kết nối khác.
                // Khởi tạo một thread mới để xử lý client
                clientHandler clientHandler = new clientHandler(clientSocket);
                clientHandler.start();
                
                System.out.println("New client connected");
            }
        } catch (IOException e) {
        }
    }

    //Dừng server
    public void stopServer() {
        try {
            logBLL.getInstance().saveLog(null,"Close sever");
            isRunning = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
