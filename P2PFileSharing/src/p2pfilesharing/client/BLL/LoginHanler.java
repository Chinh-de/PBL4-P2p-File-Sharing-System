package p2pfilesharing.client.BLL;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginHanler {

    // Hàm băm mật khẩu
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            // Chuyển đổi byte[] thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean ExamineLogin(String username, String password) {
        if (username.length() < 6|| password.length() < 6) {
            return false;
        }
        //Kiểm tra kí tự đặc biệt
        if (!(username.matches("[a-zA-Z0-9]+") && password.matches("[a-zA-Z0-9]+") ) ) {
            return false;
        }
        return true;
    }
}
