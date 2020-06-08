package Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    public static Connection Koneksi() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/kasir", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
