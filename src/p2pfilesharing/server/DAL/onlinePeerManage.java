package p2pfilesharing.server.DAL;

import p2pfilesharing.server.DTO.*;

import java.util.*;

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

    public int getOnlinePeerCount() {
        return onlinePeers.size();
    }

    public boolean isOnline(String username) {
        return onlinePeers.containsKey(username);
    }

    public String[][] getAllOnlinePeerToArray()
    {
        int index = 0;
        String[][] allOP = new String[onlinePeers.size()][5];
        for (Map.Entry<String, onlinePeer> entry : onlinePeers.entrySet()) {
            allOP[index][0] = entry.getKey();
            onlinePeer temp = entry.getValue();
            allOP[index][1] = Integer.toString(temp.getCurrentUploads());
            allOP[index][2] = Integer.toString(temp.getCurrentDownloads());
            allOP[index][3] = temp.getIp();
            allOP[index][4] = Integer.toString(temp.getPort());
            index++;
        }
        return allOP;
    }

}
