package p2pfilesharing.client.BLL;

import java.io.*;
import java.net.*;

public class serverConnection {
    private static serverConnection instance;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    // Dùng IP và port cố định
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6969;

    // Private constructor để ngăn tạo instance từ bên ngoài
    private serverConnection() throws IOException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
    }

    // Phương thức để lấy instance duy nhất của lớp
    public static serverConnection getInstance() throws IOException {
        try {
            if (instance == null) {
                instance = new serverConnection();
            }
            return instance;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Gửi yêu cầu đăng nhập tới server
    public void sendLoginRequest(String username, String hashPassword) {
        String request = "LOGIN|" + username + "|" + hashPassword;
        output.println(request);
    }

    public void sendRegisterRequest(String username, String hashPassword) {
        String request = "REGISTER|" + username + "|" + hashPassword;
        output.println(request);
    }

    // Nhận phản hồi từ server
    public String receiveResponse() throws IOException {
        return input.readLine(); // Đọc phản hồi từ server
    }

    // Gửi các yêu cầu khác tới server
    public void sendRequest(String request) {
        output.println(request); // Gửi bất kỳ yêu cầu nào tới server
    }

    // Đóng kết nối với server
    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
