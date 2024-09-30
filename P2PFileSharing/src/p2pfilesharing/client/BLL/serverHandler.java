package p2pfilesharing.client.BLL;

import p2pfilesharing.client.GUI.AppForm;
import p2pfilesharing.client.GUI.LoginForm;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class serverHandler extends Thread {
    private Socket socket;
    private String myPort;
    private BufferedReader in;
    private PrintWriter out;
    LoginForm loginForm = new LoginForm();
    AppForm appForm = new AppForm();

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
        loginForm.setVisible(true);
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
                    appForm.setVisible(true);
                    loginForm.dispose();
                    try {
                        PeerListener peerListener = new PeerListener(Integer.parseInt(parts[1]));
                        peerListener.start();
                        serverConnection.getInstance().sendRefreshRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "LOGIN_FAIL":
                    JOptionPane.showMessageDialog(loginForm, "Error: Wrong account or password!", "Login error", JOptionPane.ERROR_MESSAGE);
                    break;

                case "PARALLEL_LOGIN":
                    JOptionPane.showMessageDialog(loginForm, "Error: Your account is being accessed from another location!", "Login error", JOptionPane.ERROR_MESSAGE);
                    break;

                case "EXISTED_USERNAME":
                    JOptionPane.showMessageDialog(loginForm, "Error: Username already exists", "Register error", JOptionPane.ERROR_MESSAGE);
                    break;

                case "REGISTER_SUCCESS":
                    JOptionPane.showMessageDialog(loginForm, "Register successful", "Register successful", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case "UPLOAD_SUCCESS":
                    JOptionPane.showMessageDialog(appForm, "Upload successful", "Upload successful", JOptionPane.INFORMATION_MESSAGE);
                    try {
                        serverConnection.getInstance().sendRefreshRequest();
                    } catch (IOException e) { System.out.println("Lỗi khi cập nhật:" +e.getMessage()); }
                    break;

                case "FILE_LIST":
                    String fileData = response.substring(response.indexOf("|") + 1);
                    updateFileTable(fileData);
                    break;

                case "CHECK_EXISTING_FILE":
                    String yourFiles = response.substring(response.indexOf("|") + 1);
                    checkExistingFiles(yourFiles);
                    break;

                case "NO_UPLOADER_ONLINE":
                    JOptionPane.showMessageDialog(loginForm, "No peer owns the file" + parts[1] + " online!", "Download error", JOptionPane.ERROR_MESSAGE);
                    break;

                case "UPLOADER_INFORMATION":
                    handleDownload(Integer.parseInt(parts[1]), parts[2], parts[3], Integer.parseInt(parts[4]), parts[5]);
                    break;
                
                default:
                    System.out.println("Phản hồi từ server: " + response);
                    break;
            }
        }
    }

    private void updateFileTable(String fileData) {
        String[] fileEntries = fileData.split(";");

        for (String entry : fileEntries) {
            // Mỗi file có định dạng id|name|size
            String[] fileDetails = entry.split("\\|");
            if (fileDetails.length == 3) {
                String id = fileDetails[0];
                String name = fileDetails[1];
                String size = fileDetails[2];

                // Cập nhật bảng ở MainForm
                appForm.addFileToTable(id, name, size);
            }
        }
    }

    private void checkExistingFiles(String yourFiles) {
        String[] fileEntries = yourFiles.split(";");

        StringBuilder response = new StringBuilder();
        response.append("LOST_FILES|");
        for (String entry : fileEntries) {
            String[] file = entry.split("\\|");
            if (file.length == 2) {
                String path = file[1];
                File f = new File(path);
                if (!f.exists()) response.append(file[0]).append("|"); // trả về fileId nếu file đã bị mất
            }
        }

        //Nếu có fileid được add vào thì bỏ kí tự cuối cùng và gửi phản hồi cho server
        if (!response.toString().equals("LOST_FILES|")) {
            response.deleteCharAt(response.length() - 1);
            sendRequest(response.toString());
        }
    }

    private void handleDownload(int fileId, String fileName, String Ip, int port, String path) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose file storage location for: " +  fileName);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(appForm);
        if (result == JFileChooser.APPROVE_OPTION) {
            String savePath = chooser.getSelectedFile().getAbsolutePath();
            PeerDownloader peerDownloader = new PeerDownloader(fileId, path, Ip, port, savePath);
            peerDownloader.start();
        }
    }
}
