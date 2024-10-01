package p2pfilesharing.client.BLL;

import java.io.*;
import java.net.Socket;

public class PeerHandler implements Runnable {
    private Socket peerSocket;
    private boolean keepAlive;

    public PeerHandler(Socket peerSocket) {
        this.peerSocket = peerSocket;
        this.keepAlive = true;
    }

    @Override
    public void run() {
        PrintWriter out = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
            out = new PrintWriter(peerSocket.getOutputStream(), true);
            String message;
            if (keepAlive) {
                while ((message = in.readLine()) != null) {
                    String[] parts = message.split("\\|");
                    if (parts.length > 0) {
                        if (parts[0].equals("DOWNLOAD")) {
                            String path = parts[1];
                            int fileId = Integer.parseInt(parts[2]);
                            handleDownload(path, fileId);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Notice: End sending file, Closed socket");
        }
    }

    private void handleDownload(String path, int fileId)
    {
        File fileToSend = new File(path);
        try {
            FileInputStream fis = new FileInputStream(fileToSend);
            OutputStream os = peerSocket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            serverConnection.getInstance().sendUploadSuccessful(fileId);

        } catch (IOException e){
            System.out.println(e.getMessage());
            try {
                serverConnection.getInstance().sendUploadFailed(fileId);
            } catch (IOException e1) {
                System.out.println("Send file fail: "+e1.getMessage());
            }
        } finally {
            try {
                this.keepAlive = false;
                peerSocket.close();
            } catch (IOException e) {
                System.out.println("Error Can't close socket: " + e.getMessage());
            }
        }
    }
}
