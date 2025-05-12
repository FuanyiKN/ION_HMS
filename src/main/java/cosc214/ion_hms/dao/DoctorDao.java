package cosc214.ion_hms.dao;

import cosc214.ion_hms.models.Doctor;
import cosc214.ion_hms.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DoctorDao {
    private final Connection conn = DatabaseUtil.getConnection();

    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO Doctors (id, name, specialization, available_hours, assigned_patients, last_updated_by, last_updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getId());
            stmt.setString(2, doctor.getName());
            stmt.setString(3, String.join("|", doctor.getSpecialization())); // Convert LinkedList to string
            stmt.setString(4, String.join("|", doctor.getAvailableHours())); // Convert LinkedList to string
            stmt.setString(5, String.join("|", doctor.getAssignedPatients())); // Convert LinkedList to string
            stmt.setString(6, doctor.getLastUpdatedBy());
            stmt.setTimestamp(7, Timestamp.valueOf(doctor.getLastUpdatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
        }
    }

    public Doctor getDoctorById(String id) {
        String sql = "SELECT * FROM Doctors WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        new LinkedList<>(Arrays.asList(rs.getString("specialization").split("\\|"))), // Convert string to LinkedList
                        new LinkedList<>(Arrays.asList(rs.getString("available_hours").split("\\|"))), // Convert string to LinkedList
                        new LinkedList<>(Arrays.asList(rs.getString("assigned_patients").split("\\|"))), // Convert string to LinkedList
                        rs.getString("last_updated_by"),
                        rs.getTimestamp("last_updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching doctor by ID: " + e.getMessage());
        }
        return null;
    }

    public void updateDoctor(Doctor doctor) {
        String sql = "UPDATE Doctors SET name = ?, specialization = ?, available_hours = ?, assigned_patients = ?, last_updated_by = ?, last_updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, String.join("|", doctor.getSpecialization())); // Convert LinkedList to string
            stmt.setString(3, String.join("|", doctor.getAvailableHours())); // Convert LinkedList to string
            stmt.setString(4, String.join("|", doctor.getAssignedPatients())); // Convert LinkedList to string
            stmt.setString(5, doctor.getLastUpdatedBy());
            stmt.setTimestamp(6, Timestamp.valueOf(doctor.getLastUpdatedAt()));
            stmt.setString(7, doctor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating doctor: " + e.getMessage());
        }
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        new LinkedList<>(Arrays.asList(rs.getString("specialization").split("\\|"))), // Convert string to LinkedList
                        new LinkedList<>(Arrays.asList(rs.getString("available_hours").split("\\|"))), // Convert string to LinkedList
                        new LinkedList<>(Arrays.asList(rs.getString("assigned_patients").split("\\|"))), // Convert string to LinkedList
                        rs.getString("last_updated_by"),
                        rs.getTimestamp("last_updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all doctors: " + e.getMessage());
        }
        return doctors;
    }
}
