package p2pfilesharing.server.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Các thuộc tính cơ sở dữ liệu
    private static final String URL = "jdbc:mysql://localhost:3307/p2pfilesharingsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    // Kết nối cơ sở dữ liệu
    private static Connection connection = null;

    // Phương thức để lấy kết nối
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Tải driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Tạo kết nối
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL Driver not found.", e);
            }
        }
        return connection;
    }

    // Phương thức để đóng kết nối
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
