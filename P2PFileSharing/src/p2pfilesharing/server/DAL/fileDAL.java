package p2pfilesharing.server.DAL;
import p2pfilesharing.server.DTO.file;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class fileDAL {

    // Singleton instance
    private static fileDAL instance;

    // Private constructor to prevent instantiation
    private fileDAL() {}

    // Method to get the singleton instance
    public static synchronized fileDAL getInstance() {
        if (instance == null) {
            instance = new fileDAL();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void createFile(file file) {
        String sql = "INSERT INTO files (size, describe) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, file.getSize());
            pstmt.setString(2, file.getDescribe());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public file getFile(int id) {
        file file = null;
        String sql = "SELECT * FROM files WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                file = new file();
                file.setId(rs.getInt("id"));
                file.setSize(rs.getLong("size"));
                file.setDescribe(rs.getString("describe"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void updateFile(file file) {
        String sql = "UPDATE files SET size = ?, describe = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, file.getSize());
            pstmt.setString(2, file.getDescribe());
            pstmt.setInt(3, file.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(int id) {
        String sql = "DELETE FROM files WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<file> getAllFiles() {
        List<file> files = new ArrayList<>();
        String sql = "SELECT * FROM files";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                file file = new file();
                file.setId(rs.getInt("id"));
                file.setSize(rs.getLong("size"));
                file.setDescribe(rs.getString("describe"));
                files.add(file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return files;
    }
}
