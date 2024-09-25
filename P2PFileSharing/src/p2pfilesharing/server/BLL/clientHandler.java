package p2pfilesharing.server.BLL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import p2pfilesharing.server.DAL.fileDAL;
import p2pfilesharing.server.DAL.file_peerDAL;
import p2pfilesharing.server.DAL.peerDAL;
import p2pfilesharing.server.DTO.peer;
import p2pfilesharing.server.DTO.file;

public class clientHandler extends Thread {
    private Socket clientSocket;
    private String username;

    public clientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        //Xử lí client
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            
            while((message = in.readLine()) != null)
            {
                handleMessage(message);
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void handleMessage(String message) {
        // Xử lý thông điệp từ client
        //cho định dạng message là: request|thông tin 1|thông tin 2|...
        String[] parts = message.split("\\|");
        if (parts.length > 0) {
            String type = parts[0]; //lấy type of request
            
            switch (type) {

                case "LOGIN":
                    handleLogin(parts[1], parts[2]);
                    break;

                case "REGISTER":
                    handleRegister(parts[1], parts[2]);
                    break;

                case "UPLOAD":
                    handleUpload(parts[1], parts[2], Long.parseLong(parts[3]), this.username);
                    break;

                case "REFRESH":
                    handleRefresh();
                default:
                System.out.println("Thông điệp không hợp lệ: " + message);
                break;
            }
        }
    }
    
    private void sendResponseToClient(String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);  // Gửi phản hồi về phía client
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //xử lí đăng nhập
    private void handleLogin(String username, String hashPassword) {
        try {
            peer tempPeer = peerDAL.getInstance().getPeer(username);
            if(tempPeer != null)
            {
                if(hashPassword.compareTo(tempPeer.getHashPassword()) == 0) {
                    sendResponseToClient("LOGIN_SUCCESS");
                    this.username = username;
                } else sendResponseToClient("LOGIN_FAILED");
            }
            else sendResponseToClient("LOGIN_FAILED");
        } catch (Exception e) { }
    }

    //xử lí đăng ký
    private void handleRegister(String username, String hashPassword) {
        try {
            peer tempPeer = peerDAL.getInstance().getPeer(username);
            if(tempPeer != null)
                sendResponseToClient("EXISTED_USERNAME");
            else
            {
                tempPeer = new peer();
                tempPeer.setUsername(username);
                tempPeer.setHashPassword(hashPassword);
                peerDAL.getInstance().addPeer(tempPeer);
                sendResponseToClient("REGISTER_SUCCESS");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleUpload(String filename, String path, long size, String username)
    {
        int fileId = fileDAL.getInstance().createFile(filename,size);
        file_peerDAL.getInstance().createfile_peer(fileId, username, path);
        sendResponseToClient("UPLOAD_SUCCESS");
    }

    private void handleRefresh() {
        StringBuilder response = new StringBuilder();
        response.append("FILE_LIST|");
        for (file f : fileDAL.getInstance().getAllFiles())
        {
            response.append(f.getId())
                    .append("|")
                    .append(f.getName())
                    .append("|")
                    .append(f.getSize())
                    .append(";");
        }
        if (!response.isEmpty()) {
            response.deleteCharAt(response.length() - 1);
        }
        sendResponseToClient(response.toString());
    }
    public String getUsername() {
        return username;
    }
}
    
    
