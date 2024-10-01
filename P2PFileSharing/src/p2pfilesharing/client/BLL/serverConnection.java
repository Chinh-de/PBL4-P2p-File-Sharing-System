package p2pfilesharing.client.BLL;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class serverConnection {
    private static serverConnection instance;
    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;

    private static final int SERVER_PORT = 6969;

    // Private constructor để ngăn tạo instance từ bên ngoài
    private serverConnection() throws IOException {
        String SERVER_IP = "";
        while (SERVER_IP.isEmpty())
        {
            SERVER_IP = JOptionPane.showInputDialog(null, "Enter IP of Server:");
            if (SERVER_IP == null) {
                JOptionPane.showMessageDialog(null, "Exiting the application.", "Exit", JOptionPane.WARNING_MESSAGE);
                System.exit(0); // Thoát chương trình
            }
        }
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
            JOptionPane.showMessageDialog(null , "Error connecting to the server", "Error connecting to the server", JOptionPane.ERROR_MESSAGE);
            instance = null;
            return getInstance();
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

    public void sendUploadRequest (String filename, String path, long size)
    {
        String request = "UPLOAD|" + filename + "|" + path + "|" + size;
        output.println(request);
    }

    public void sendDownloadRequest (int fileId)
    {
        String request = "DOWNLOAD|" + fileId;
        output.println(request);
    }

    public void sendRefreshRequest ()
    {
        String request = "REFRESH|";
        output.println(request);
    }
    public void sendDownloadSuccesful(int fileId, String path){
        String request = "DOWNLOAD_SUCCESSFUL|" + fileId + "|" + path;
        output.println(request);
    }
    public void sendDownloadFailed(int fileId){
        String request = "DOWNLOAD_FAILED|" + fileId;
        output.println(request);
    }
    public void sendUploadSuccessful(int fileId){
        String request = "UPLOAD_SUCCESSFUL|" + fileId;
        output.println(request);
    }
    public void sendUploadFailed(int fileId){
        String request = "UPLOAD_FAILED|" + fileId;
        output.println(request);
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
