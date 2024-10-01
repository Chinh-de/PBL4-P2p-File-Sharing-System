package p2pfilesharing.client.BLL;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class PeerDownloader extends Thread {
    private Socket socket;
    private String path;
    private String SenderIp;
    private int SenderPort;
    private String savePath;
    int fileid;

    public PeerDownloader(int fileId, String path, String SenderIp, int SenderPort, String savePath) {
        this.path = path;
        this.SenderIp = SenderIp;
        this.SenderPort = SenderPort;
        this.savePath = savePath;
        this.fileid = fileId;
        try {
            this.socket = new Socket(SenderIp, SenderPort);
        } catch (IOException e) {
            System.out.println("Error Can't connect to other peer: "+e.getMessage());
        }
    }
    @Override
    public void run() {

        sendDownloadRequest(path);
        receiveFile(path, savePath);
    }

    private void sendDownloadRequest(String path) {
        String message = "DOWNLOAD|" + path + "|" + fileid; // Tạo thông điệp
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            System.out.println("Error Can't send request download to peer: "+e.getMessage());
        }
    }

    private void receiveFile(String path, String savePath) {
        int lastSlashIndex = path.lastIndexOf('\\');
        String fileName = path.substring(lastSlashIndex + 1);
        String filePath = savePath + "\\" + fileName;

        if (new File(filePath).exists()) {
            JOptionPane.showMessageDialog(null, "File" + fileName + " already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try (InputStream in = socket.getInputStream();
                 FileOutputStream fileOut = new FileOutputStream(filePath);) {
                byte[] buffer = new byte[4096]; // Buffer để lưu trữ dữ liệu
                int bytesRead;
                // Đọc dữ liệu từ input stream và ghi vào file
                while ((bytesRead = in.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, bytesRead);
                }
                fileOut.flush();
                JOptionPane.showMessageDialog(null, "File " + fileName + " downloaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                String pathToFile = savePath + "\\" + fileName;
                serverConnection.getInstance().sendDownloadSuccesful(fileid, pathToFile);
                System.out.println("File " + fileName + " downloaded successfully!");
            } catch (IOException e) {
                System.out.println("Error: Downloaded failed: "+e.getMessage());
                JOptionPane.showMessageDialog(null, "File " + fileName + " Downloaded failed!", "Error", JOptionPane.ERROR_MESSAGE);
                try {
                    serverConnection.getInstance().sendDownloadFailed(fileid);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error: Can't close peer socket: "+e.getMessage());
                }
            }
        }
    }
}
