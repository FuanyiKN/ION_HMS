package cosc214.ion_hms.dao;

import cosc214.ion_hms.models.Admin;
import cosc214.ion_hms.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private final Connection conn = DatabaseUtil.getConnection();

    public void addAdmin(Admin admin) {
        String sql = "INSERT INTO Admins (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getId());
            stmt.setString(2, admin.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
        }
    }

    public Admin getAdminById(String id) {
        String sql = "SELECT * FROM Admins WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getString("id"),
                        rs.getString("name")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching admin by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM Admins";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                admins.add(new Admin(
                        rs.getString("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all admins: " + e.getMessage());
        }
        return admins;
    }

    public void updateAdmin(Admin admin) {
        String sql = "UPDATE Admins SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
        }
    }

    public void deleteAdmin(String id) {
        String sql = "DELETE FROM Admins WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting admin: " + e.getMessage());
        }
    }
}
