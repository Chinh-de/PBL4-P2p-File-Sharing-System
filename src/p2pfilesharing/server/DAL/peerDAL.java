package p2pfilesharing.server.DAL;

import p2pfilesharing.server.DTO.peer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class peerDAL {
    
    // Singleton instance
    private static peerDAL instance;

    // Private constructor to prevent instantiation
    private peerDAL() {}

    // Method to get the singleton instance
    public static synchronized peerDAL getInstance() {
        if (instance == null) {
            instance = new peerDAL();
        }
        return instance;
    }

    // Thêm một peer mới vào cơ sở dữ liệu
    public void addPeer(peer peer) throws SQLException {
        String sql = "INSERT INTO peer (username, hashPassword) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, peer.getUsername());
            stmt.setString(2, peer.getHashPassword());
            stmt.executeUpdate();
        }
    }

    // Xóa một peer dựa trên username
    public void deletePeer(String username) throws SQLException {
        String sql = "DELETE FROM peer WHERE username = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }

    // Cập nhật thông tin của một peer
    public void updatePeer(peer peer, String oldUsername) throws SQLException {
        String sql = "UPDATE peer SET username = ?, hashPassword = ? WHERE username = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, peer.getUsername());
            stmt.setString(2, peer.getHashPassword());
            stmt.setString(3, oldUsername);
            stmt.executeUpdate();
        }
    }

    // Lấy thông tin của một peer dựa trên username
    public peer getPeer(String username) throws SQLException {
        String sql = "SELECT * FROM peer WHERE username = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                peer peer = new peer();
                peer.setUsername(rs.getString("username"));
                peer.setHashPassword(rs.getString("hashPassword"));
                return peer;
            }            
            return null;
        }
    }

    // Lấy danh sách tất cả các peer
    public List<peer> getAllPeers() throws SQLException {
        List<peer> peers = new ArrayList<>();
        String sql = "SELECT * FROM peer";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                peer peer = new peer();
                peer.setUsername(rs.getString("username"));
                peer.setHashPassword(rs.getString("hashPassword"));
                peers.add(peer);
            }
        }
        return peers;
    }
}