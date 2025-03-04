package p2pfilesharing.server.DTO;

import java.time.LocalDateTime;

public class log {
    private int id;
    private String username;
    private String action;
    private LocalDateTime time;

    public log(String username, String action) {
        this.username = username;
        this.action = action;
        this.time = LocalDateTime.now();
    }
    public log() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    public void setCurrentTime() {
        this.time = LocalDateTime.now();
    }
}
