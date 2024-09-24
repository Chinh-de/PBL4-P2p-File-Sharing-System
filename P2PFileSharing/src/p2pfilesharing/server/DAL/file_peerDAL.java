package p2pfilesharing.server.DAL;

import p2pfilesharing.server.DTO.file_peer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class file_peerDAL {

    // Singleton instance
    private static file_peerDAL instance;

    // Private constructor to prevent instantiation
    private file_peerDAL() {}

    // Method to get the singleton instance
    public static synchronized file_peerDAL getInstance() {
        if (instance == null) {
            instance = new file_peerDAL();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Create a new file-peer relationship
    public void createfile_peer(file_peer filePeer) {
        String sql = "INSERT INTO file_peer (fileId, peerUsername, path) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filePeer.getFileid());
            pstmt.setString(2, filePeer.getPeerUsername());
            pstmt.setString(3, filePeer.getPath());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a file-peer relationship by fileId and peerUsername
    public file_peer getfile_peer(int fileId, String peerUsername) {
        file_peer filePeer = null;
        String sql = "SELECT * FROM file_peer WHERE fileId = ? AND peerUsername = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fileId);
            pstmt.setString(2, peerUsername);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                filePeer = new file_peer();
                filePeer.setFileid(rs.getInt("fileId"));
                filePeer.setPeerUsername(rs.getString("peerUsername"));
                filePeer.setPath(rs.getString("path"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filePeer;
    }

    // Update a file-peer relationship
    public void updatefile_peer(file_peer filePeer) {
        String sql = "UPDATE file_peer SET path = ? WHERE fileId = ? AND peerUsername = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, filePeer.getPath());
            pstmt.setInt(2, filePeer.getFileid());
            pstmt.setString(3, filePeer.getPeerUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a file-peer relationship
    public void deletefile_peer(int fileId, String peerUsername) {
        String sql = "DELETE FROM file_peer WHERE fileId = ? AND peerUsername = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fileId);
            pstmt.setString(2, peerUsername);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all file-peer relationships for a specific fileId
    public List<file_peer> getAllfile_peersByFileId(int fileId) {
        List<file_peer> filePeers = new ArrayList<>();
        String sql = "SELECT * FROM file_peer WHERE fileId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, fileId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                file_peer filePeer = new file_peer();
                filePeer.setFileid(rs.getInt("fileId"));
                filePeer.setPeerUsername(rs.getString("peerUsername"));
                filePeer.setPath(rs.getString("path"));
                filePeers.add(filePeer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filePeers;
    }

    // Retrieve all file-peer relationships for a specific peerUsername
    public List<file_peer> getAllfile_peersByPeerUsername(String peerUsername) {
        List<file_peer> filePeers = new ArrayList<>();
        String sql = "SELECT * FROM file_peer WHERE peerUsername = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, peerUsername);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                file_peer filePeer = new file_peer();
                filePeer.setFileid(rs.getInt("fileId"));
                filePeer.setPeerUsername(rs.getString("peerUsername"));
                filePeer.setPath(rs.getString("path"));
                filePeers.add(filePeer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filePeers;
    }
}