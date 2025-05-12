package cosc214.ion_hms;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            System.out.println("✅ Database connection successful!");
        } catch (Exception e) {
            System.err.println("❌ Database connection failed:");
            e.printStackTrace();
        }
    }
}

