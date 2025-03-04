package p2pfilesharing.server.DTO;

import java.security.PublicKey;
import java.util.Random;

public class onlinePeer {
    private String ip;
    private int port;
    private int currentDownloads;
    private int currentUploads;

    public onlinePeer(String ip) {
        this.ip = ip;
        this.currentDownloads = 0;
        this.currentUploads = 0;
        Random rand = new Random();
        this.port = 49152 + rand.nextInt(16384); // 16384 = 65535 - 49152 + 1
    }

    public int getCurrentDownloads() {
        return currentDownloads;
    }

    public void setCurrentDownloads(int currentDownloads) {
        this.currentDownloads = currentDownloads;
    }

    public int getCurrentUploads() {
        return currentUploads;
    }

    public void setCurrentUploads(int currentUploads) {
        this.currentUploads = currentUploads;
    }  

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public int getPriorityScore(){
        return this.currentDownloads - this.currentUploads;
    }

    public void increaseDownloads(){
        this.currentDownloads++;
    }
    public void increaseUploads(){
        this.currentUploads++;
    }
    public void decreaseDownloads(){
        this.currentDownloads--;
    }
    public void decreaseUploads(){
        this.currentUploads--;
    }
}

