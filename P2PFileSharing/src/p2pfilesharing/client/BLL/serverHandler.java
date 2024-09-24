package p2pfilesharing.client.BLL;

import p2pfilesharing.client.GUI.LoginForm;
import p2pfilesharing.client.GUI.MainForm;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class serverHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public serverHandler() {
        try {
            // Kết nối đến server
            socket = serverConnection.getInstance().getSocket();
            // Thiết lập các luồng vào/ra
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String request) {
        out.println(request);
    }

    // Lắng nghe phản hồi từ server
    @Override
    public void run() {
        try {
            String response;
            while ((response = in.readLine()) != null) {
                handleServerResponse(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // Đóng kết nối khi kết thúc
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Xử lý phản hồi từ server
    private void handleServerResponse(String response) {
        // Lấy từ khóa đầu tiên của phản hồi để so sánh
        String[] parts = response.split("\\|");
        if (parts.length > 0) {
            String type = parts[0]; //lấy type of request

            switch (type) {
                case "LOGIN_SUCCESS":
                    MainForm mainForm = new MainForm();
                    mainForm.setVisible(true);

                    break;

                case "LOGIN_FAIL":
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                    break;

                case "EXISTED_USERNAME":
                    JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại", "Lỗi đăng ký", JOptionPane.ERROR_MESSAGE);
                    break;

                case "REGISTER_SUCCESS":
                    JOptionPane.showMessageDialog(null, "Đăng ký thành công", "Đăng ký thành công", JOptionPane.OK_OPTION);
                    break;

                default:
                    System.out.println("Phản hồi từ server: " + response);
                    break;
            }
        }
    }
}
