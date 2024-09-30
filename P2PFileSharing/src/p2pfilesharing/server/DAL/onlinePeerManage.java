package p2pfilesharing.server.DAL;

import p2pfilesharing.server.DTO.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class onlinePeerManage {
    private static onlinePeerManage instance;
    
    // Danh sách lưu trữ các peer trực tuyến
    private static HashMap<String, onlinePeer> onlinePeers;

    //Singleton pattern
    private onlinePeerManage() {
        onlinePeers = new HashMap<>();
    }

    public static synchronized onlinePeerManage getInstance() {
        if (instance == null) {
            instance = new onlinePeerManage();
        }
        return instance;
    }
    
    // thêm onlinePeer
    public void add(String username, String ip)
    {
        onlinePeer peer = new onlinePeer(ip);
        onlinePeers.put(username, peer);
    }
    
    // xóa peer offline
    public void removePeer(String username){
        onlinePeers.remove(username);
    }

    //Lấy onlinePeer theo username
    public onlinePeer getOnlinePeer(String username){
        return onlinePeers.get(username);
    }

    // Lấy tất cả các username onlinepeer
    public List<String> getAllOnlinePeers() {
        return new ArrayList<>(onlinePeers.keySet());
    }

    public boolean isOnline(String username) {
        return onlinePeers.containsKey(username);
    }


}
