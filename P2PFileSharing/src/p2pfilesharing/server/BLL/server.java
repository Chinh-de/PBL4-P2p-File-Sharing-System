package p2pfilesharing.server.BLL;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    private static final int SERVER_PORT = 6969;
    private ServerSocket serverSocket;
    private boolean isRunning;

    // Mở server
    public void startServer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            isRunning = true;
            System.out.println("Server is listening on port: " + SERVER_PORT);

            // Gọi phương thức lắng nghe kết nối từ client
            listenForClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức lắng nghe các kết nối từ client
    private void listenForClients() {
        while (isRunning) {
            try {
                // Chờ đợi kết nối từ client
                Socket clientSocket = serverSocket.accept();
                //Vì accept blocking, phải chờ server xử lí từng kết nối 1, xong mới tới kết nối khác.
                // Khởi tạo một thread mới để xử lý client
                clientHandler clientHandler = new clientHandler(clientSocket);
                clientHandler.start();

                System.out.println("New client connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức dừng server
    public void stopServer() {
        try {
            isRunning = false;
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
