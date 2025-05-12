package cosc214.ion_hms.dao;

import cosc214.ion_hms.models.User;
import cosc214.ion_hms.DatabaseUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Random;

public class UserDao {
    private final Connection conn = DatabaseUtil.getConnection();

    public void registerUser(User user) {
        String userSql = "INSERT INTO Users (id, password, role, email, contact_number) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
            String generatedId = generateUniqueId(user.getName());
            userStmt.setString(1, generatedId);
            userStmt.setString(2, hashPassword(user.getHashedPassword())); // Ensure password is hashed
            userStmt.setString(3, user.getRole().name());
            userStmt.setString(4, user.getEmail());
            userStmt.setString(5, user.getContactNumber());
            userStmt.executeUpdate();

            // Populate Doctor or Patient table based on role
            if (user.getRole() == User.Role.DOCTOR) {
                addDoctor(generatedId, user.getName());
            } else if (user.getRole() == User.Role.PATIENT) {
                addPatient(generatedId, user.getName());
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
        }
    }

    private void addDoctor(String id, String name) {
        String doctorSql = "INSERT INTO Doctors (id, name) VALUES (?, ?)";
        try (PreparedStatement doctorStmt = conn.prepareStatement(doctorSql)) {
            doctorStmt.setString(1, id);
            doctorStmt.setString(2, name);
            doctorStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
        }
    }

    private void addPatient(String id, String name) {
        String patientSql = "INSERT INTO Patients (id, name) VALUES (?, ?)";
        try (PreparedStatement patientStmt = conn.prepareStatement(patientSql)) {
            patientStmt.setString(1, id);
            patientStmt.setString(2, name);
            patientStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
        }
    }

    private String generateUniqueId(String name) {
        String initials = name.replaceAll("[^A-Za-z]", "").toUpperCase().substring(0, Math.min(3, name.length()));
        int randomNumber = new Random().nextInt(900) + 100; // Generate a 3-digit random number
        return initials + randomNumber;
    }

    private String hashPassword(String rawPassword) {
        if (BCrypt.checkpw(rawPassword, rawPassword)) {
            return rawPassword; // Already hashed
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean login(String id, String inputPassword) {
        String sql = "SELECT password FROM Users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                return BCrypt.checkpw(inputPassword, hashedPassword); // Verify hashed password
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
        return false;
    }

    public User getUserById(String id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("id"),
                        rs.getString("name"), // Added name field
                        rs.getString("password"),
                        User.Role.valueOf(rs.getString("role").toUpperCase()), // Ensure role is mapped correctly
                        rs.getString("email"),
                        rs.getString("contact_number"),
                        true
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user by ID: " + e.getMessage());
        }
        return null;
    }

    public void deleteUser(String id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }
}
