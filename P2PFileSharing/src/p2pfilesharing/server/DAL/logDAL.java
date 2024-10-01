package p2pfilesharing.server.DAL;

import p2pfilesharing.server.DTO.log;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class logDAL {

    // Singleton instance
    private static logDAL instance;

    // Private constructor to prevent instantiation
    private logDAL() {}

    // Method to get the singleton instance
    public static synchronized logDAL getInstance() {
        if (instance == null) {
            instance = new logDAL();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    // Create a new log entry
    public void createLog(log Log) {
        String sql = "INSERT INTO log (username, action, time) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Log.getUsername());
            pstmt.setString(2, Log.getAction());
            pstmt.setTimestamp(3, Timestamp.valueOf(Log.getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve a log entry by id
    public log getLog(int id) {
        log Log = null;
        String sql = "SELECT * FROM log WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Log = new log();
                Log.setId(rs.getInt("id"));
                Log.setUsername(rs.getString("username"));
                Log.setAction(rs.getString("action"));
                Log.setTime(rs.getTimestamp("time").toLocalDateTime());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Log;
    }

    // Update an existing log entry
    public void updateLog(log Log) {
        String sql = "UPDATE log SET username = ?, action = ?, time = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Log.getUsername());
            pstmt.setString(2, Log.getAction());
            pstmt.setTimestamp(3, Timestamp.valueOf(Log.getTime()));
            pstmt.setInt(4, Log.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a log entry by id
    public void deleteLog(int id) {
        String sql = "DELETE FROM log WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve all log entries
    public List<log> getAllLogs() {
        List<log> logs = new ArrayList<>();
        String sql = "SELECT * FROM log";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                log Log = new log();
                Log.setId(rs.getInt("id"));
                Log.setUsername(rs.getString("username"));
                Log.setAction(rs.getString("action"));
                Log.setTime(rs.getTimestamp("time").toLocalDateTime());
                logs.add(Log);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return logs;
    }
}
