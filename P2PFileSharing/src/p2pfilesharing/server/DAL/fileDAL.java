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

    public int createFile(String name, long size) {
        String sql = "INSERT INTO file (name, size) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setLong(2, size);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public file getFile(int id) {
        file file = null;
        String sql = "SELECT * FROM file WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                file = new file();
                file.setId(rs.getInt("id"));
                file.setSize(rs.getLong("size"));
                file.setName(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return file;
    }

    public void updateFile(file file) {
        String sql = "UPDATE file SET size = ?, name = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, file.getSize());
            pstmt.setString(2, file.getName());
            pstmt.setInt(3, file.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteFile(int id) {
        String sql = "DELETE FROM file WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<file> getAllFiles() {
        List<file> files = new ArrayList<>();
        String sql = "SELECT * FROM file";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                file file = new file();
                file.setId(rs.getInt("id"));
                file.setSize(rs.getLong("size"));
                file.setName(rs.getString("name"));
                files.add(file);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return files;
    }
}
