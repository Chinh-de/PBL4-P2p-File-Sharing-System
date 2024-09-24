package p2pfilesharing.server.DTO;

public class onlinePeer {
    private String username;
    private String ip;
    private int currentDownloads;
    private int currentUploads;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}

