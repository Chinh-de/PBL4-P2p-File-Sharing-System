package p2pfilesharing.server.BLL;

import java.io.*;
import java.net.*;
import java.util.*;

public class CentralServer {
    private static final int SERVER_PORT = 12345;
    private static List<PeerInfo> peerList = new ArrayList<>();
    private static List<FileInfo> fileList = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("CentralServer is running on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String peerName = in.readLine();
                InetAddress peerAddress = socket.getInetAddress();
                int peerPort = Integer.parseInt(in.readLine());

                // Lưu thông tin peer
                PeerInfo peerInfo = new PeerInfo(peerName, peerAddress.getHostAddress(), peerPort);
                peerList.add(peerInfo);

                

                // Xử lý yêu cầu từ client
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equals("LIST_FILES")) {
                       
                        for (FileInfo file : fileList) {
                            out.println(file.getFilePath() + " " + file.getPeer().getIp() + ":" + file.getPeer().getPort());
                        }
                        out.println("END");
                    } else if (line.startsWith("SHARE_FILE")) {
                        String filePath = in.readLine();
                        fileList.add(new FileInfo(filePath, peerInfo));
                        out.println("File shared: " + filePath);
                    } else if (line.startsWith("GET_PEER_FOR_FILE")) {
                        String filePath = in.readLine();
                        FileInfo fileInfo = fileList.stream()
                                .filter(f -> f.getFilePath().equals(filePath))
                                .findFirst()
                                .orElse(null);
                        if (fileInfo != null) {
                            out.println(fileInfo.getPeer().getIp() + ":" + fileInfo.getPort() + " " + fileInfo.getFilePath());
                        } else {
                            out.println("FILE_NOT_FOUND");
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class PeerInfo {
        private String name;
        private String ip;
        private int port;

        public PeerInfo(String name, String ip, int port) {
            this.name = name;
            this.ip = ip;
            this.port = port;
        }

        public String getName() {
            return name;
        }

        public String getIp() {
            return ip;
        }

        public int getPort() {
            return port;
        }
    }

    private static class FileInfo {
        private String filePath;
        private PeerInfo peer;

        public FileInfo(String filePath, PeerInfo peer) {
            this.filePath = filePath;
            this.peer = peer;
        }

        public String getFilePath() {
            return filePath;
        }

        public PeerInfo getPeer() {
            return peer;
        }

        public int getPort() {
            return peer.getPort();
        }
    }
}

