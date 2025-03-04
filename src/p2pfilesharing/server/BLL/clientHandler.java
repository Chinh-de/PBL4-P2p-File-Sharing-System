package p2pfilesharing.server.BLL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore.Entry.Attribute;
import java.util.List;

import p2pfilesharing.server.DAL.*;
import p2pfilesharing.server.DTO.*;
import p2pfilesharing.server.BLL.*;

public class clientHandler extends Thread {
    private Socket clientSocket;
    private String username = "";

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
        } catch (IOException e) {
            if (username.isEmpty())
                System.err.println("A User is not logged in offline: " + e.getMessage());
            else
            {
                logBLL.getInstance().saveLog(username ,"Offline");
                System.err.println("User "+ username +" offline: " + e.getMessage());
            }
        } finally {
            closeConnection();
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
                    handleUpload(parts, this.username);
                    break;

                case "REFRESH":
                    handleRefresh();
                    break;

                case "LOST_FILES":
                    handleLostFiles(parts);
                    break;

                case "DOWNLOAD":
                    handleDownload(Integer.parseInt(parts[1]));
                    break;

                case "DOWNLOAD_SUCCESSFUL":
                    int fileId = Integer.parseInt(parts[1]);
                    String path = parts[2];
                    onlinePeerManage.getInstance().getOnlinePeer(username).decreaseDownloads();
                    logBLL.getInstance().saveLog(username,"Download file ID: " + fileId + " successful");
                    // nếu người dùng chưa sở hữu file thì thêm họ làm owner của file vừa tải
                    if(file_peerDAL.getInstance().getfile_peer(fileId, this.username) == null){
                        file_peerDAL.getInstance().createfile_peer(fileId, username, path);
                    }
                    break;

                case "DOWNLOAD_FAILED":
                    onlinePeerManage.getInstance().getOnlinePeer(username).decreaseDownloads();
                    logBLL.getInstance().saveLog(username,"Download file ID: " + parts[1] + " failed");
                    break;

                case "UPLOAD_SUCCESSFUL":
                    onlinePeerManage.getInstance().getOnlinePeer(username).decreaseUploads();
                    logBLL.getInstance().saveLog(username,"Send file ID: " + parts[1] + " successful");
                    break;

                case "UPLOAD_FAILED":
                    onlinePeerManage.getInstance().getOnlinePeer(username).decreaseUploads();
                    logBLL.getInstance().saveLog(username,"Send file ID: " + parts[1] + " failed");
                    break;

                case "GET_HISTORY":
                    handleGetHistory(this.username);
                    break;

                default:
                System.out.println("Not handle: " + message);
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
                    //Kiểm tra có đang online không
                    if (onlinePeerManage.getInstance().isOnline(username))
                        sendResponseToClient("PARALLEL_LOGIN");
                    else{
                        this.username = username;
                        //thêm vào data online peer
                        onlinePeerManage.getInstance().add(username, clientSocket.getInetAddress().getHostAddress());
                        int peerPort = onlinePeerManage.getInstance().getOnlinePeer(username).getPort();

                        sendRequestForCheckingExistingFiles(username);
                        sendResponseToClient("LOGIN_SUCCESS|" + peerPort + "|" + username);
                        logBLL.getInstance().saveLog(username,"Login");
                    }

                } else sendResponseToClient("LOGIN_FAIL");
            }
            else sendResponseToClient("LOGIN_FAIL");
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
                logBLL.getInstance().saveLog(username,"Register");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleUpload(String[] parts, String username)
    {
    	StringBuilder response = new StringBuilder();
        response.append("UPLOAD_SUCCESS|");
    	for (int i =1; i<parts.length; i++) {
    		String[] attributes = parts[i].split(";");
    		String filename = attributes[0];
    		long size =  Long.parseLong( attributes[1]);
    		String path = attributes[2];
    		int fileId = fileDAL.getInstance().createFile(filename,size);
            file_peerDAL.getInstance().createfile_peer(fileId, username, path);
            logBLL.getInstance().saveLog(username,"Upload file ID: " + fileId);
            response.append(filename);
            response.append(" ;");
    	}
    	sendResponseToClient(response.toString());
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
                    .append(fileBLL.convertSize(f.getSize()))
                    .append(";");
        }
        if (!response.toString().equals("FILE_LIST|")) {
            response.deleteCharAt(response.length() - 1);
        }
        sendResponseToClient(response.toString());
    }

    private void sendRequestForCheckingExistingFiles(String username)
    {
        List<file_peer> listFP =file_peerDAL.getInstance().getAllfile_peersByPeerUsername(username);
        StringBuilder response = new StringBuilder();
        response.append("CHECK_EXISTING_FILE|");
        for (file_peer fp: listFP)
        {
            response.append(fp.getFileid())
                    .append("|")
                    .append(fp.getPath())
                    .append(";");
        }
        if (!response.toString().equals("CHECK_EXISTING_FILE|")) {
            response.deleteCharAt(response.length() - 1);
        }
        sendResponseToClient(response.toString());
    }

    private void handleLostFiles(String[] lostFile) {
        //bỏ qua type, lấy các fileId
        for(int i = 1; i < lostFile.length; i++) {
            int fileId = Integer.parseInt(lostFile[i]);
            file_peerDAL.getInstance().deletefile_peer(fileId, this.username);
            logBLL.getInstance().saveLog(username,"Lost file ID: " + fileId);
        }
    }

    private void handleDownload(int fileId) {
        List<file_peer> owners = file_peerDAL.getInstance().getAllfile_peersByFileId(fileId);
        //Nếu peer offline thì loại khỏi danh sách
        owners.removeIf(fp -> !onlinePeerManage.getInstance().isOnline(fp.getPeerUsername()));
        //Nếu không có client nào sở hữu file online thì phản hồi cho client
        if (owners.isEmpty()) {
            String response = ("NO_UPLOADER_ONLINE|" + fileDAL.getInstance().getFile(fileId).getName());
            sendResponseToClient(response);
        }
        else {
            //sắp xếp giảm dần theo điểm ưu tiên
            owners.sort((o1, o2) -> Integer.compare(
                    onlinePeerManage.getInstance().getOnlinePeer(o2.getPeerUsername()).getPriorityScore(),
                    onlinePeerManage.getInstance().getOnlinePeer(o1.getPeerUsername()).getPriorityScore()) );
            //lấy username của client có điểm ưu tiên cao nhất
            String uploaderUsername = owners.getFirst().getPeerUsername();
            String ip = onlinePeerManage.getInstance().getOnlinePeer(uploaderUsername).getIp();
            int port = onlinePeerManage.getInstance().getOnlinePeer(uploaderUsername).getPort();
            String path = file_peerDAL.getInstance().getfile_peer(fileId, uploaderUsername).getPath();
            String fileName = fileDAL.getInstance().getFile(fileId).getName();
            //gửi thông tin về cho client
            String response = "UPLOADER_INFORMATION|" + fileId + "|" + fileName + "|" + ip + "|" + port + "|" + path;
            sendResponseToClient(response);

            //Tăng chỉ số upload, download của user tương ứng
            onlinePeerManage.getInstance().getOnlinePeer(uploaderUsername).increaseUploads();
            onlinePeerManage.getInstance().getOnlinePeer(username).increaseDownloads();
        }
    }

    private void handleGetHistory(String username) {
        List<log> logs = logDAL.getInstance().getLog(username);
        StringBuilder response = new StringBuilder();
        response.append("HISTORY|");
        for (log l : logs){
            response.append(l.getTime());
            response.append(";");
            response.append(l.getAction());
            response.append("|");
        }
        response.deleteCharAt(response.length() - 1);
        sendResponseToClient(response.toString());
    }


    public void closeConnection() {
        try{
            clientSocket.close();
            onlinePeerManage.getInstance().removePeer(username);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
    
    
