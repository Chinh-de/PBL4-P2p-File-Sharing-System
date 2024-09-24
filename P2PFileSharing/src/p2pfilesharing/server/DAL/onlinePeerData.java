package p2pfilesharing.server.DAL;

import p2pfilesharing.server.DTO.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class onlinePeerData {
    // Thực thể duy nhất của lớp (Singleton)
    private static onlinePeerData instance;
    
    // Danh sách lưu trữ các peer trực tuyến
    private static List<onlinePeer> onlinePeers = new ArrayList<>();
    
    // Constructor riêng tư để ngăn tạo thể hiện mới từ bên ngoài
    private onlinePeerData() {
        onlinePeers = new ArrayList<>();
    }
    
    // Phương thức để lấy thể hiện duy nhất của lớp
    public static synchronized onlinePeerData getInstance() {
        if (instance == null) {
            instance = new onlinePeerData();
        }
        return instance;
    }
    
    // Thêm một peer vào danh sách
    public void addPeer(onlinePeer peer) {
        // Kiểm tra nếu peer đã tồn tại trong danh sách không
        Optional<onlinePeer> existingPeer = onlinePeers.stream()
                .filter(p -> p.getUsername().equals(peer.getUsername()))
                .findFirst();
        if (!existingPeer.isPresent()) {
            onlinePeers.add(peer);
        }
    }
    
    // Xóa một peer khỏi danh sách
    public void removePeer(String username) {
        onlinePeers.removeIf(p -> p.getUsername().equals(username));
    }
    
    // Lấy danh sách tất cả các peer
    public List<onlinePeer> getAllPeers() {
        return new ArrayList<>(onlinePeers);
    }
    
    // Cập nhật thông tin của một peer
    public void updatePeer(onlinePeer updatedPeer) {
        for (int i = 0; i < onlinePeers.size(); i++) {
            onlinePeer peer = onlinePeers.get(i);
            if (peer.getUsername().equals(updatedPeer.getUsername())) {
                onlinePeers.set(i, updatedPeer);
                return;
            }
        }
    }
}
