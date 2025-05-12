package cosc214.ion_hms.dao;

import cosc214.ion_hms.models.Patient;
import cosc214.ion_hms.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PatientDao {
    private final Connection conn = DatabaseUtil.getConnection();

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO Patients (id, name, age, diagnosis, history, symptoms, severity_level, override_emergency, last_updated_by, last_updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getId());
            stmt.setString(2, patient.getName());
            stmt.setInt(3, patient.getAge());
            stmt.setString(4, patient.getDiagnosis());
            stmt.setString(5, String.join("|", patient.getHistory())); // Convert LinkedList to string
            stmt.setString(6, String.join("|", patient.getSymptoms())); // Convert LinkedList to string
            stmt.setInt(7, patient.getSeverityLevel());
            stmt.setBoolean(8, patient.isOverrideEmergency());
            stmt.setString(9, patient.getLastUpdatedBy());
            stmt.setTimestamp(10, Timestamp.valueOf(patient.getLastUpdatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
        }
    }

    public Patient getPatientById(String id) {
        String sql = "SELECT * FROM Patients WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("diagnosis"),
                        new LinkedList<>(Arrays.asList(rs.getString("history").split("\\|"))), // Convert string to LinkedList
                        new LinkedList<>(Arrays.asList(rs.getString("symptoms").split("\\|"))), // Convert string to LinkedList
                        rs.getInt("severity_level"),
                        rs.getBoolean("override_emergency"),
                        rs.getString("last_updated_by"),
                        rs.getTimestamp("last_updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching patient by ID: " + e.getMessage());
        }
        return null;
    }

    public void updatePatient(Patient patient) {
        String sql = "UPDATE Patients SET name = ?, age = ?, diagnosis = ?, history = ?, symptoms = ?, severity_level = ?, override_emergency = ?, last_updated_by = ?, last_updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getDiagnosis());
            stmt.setString(4, String.join("|", patient.getHistory())); // Convert LinkedList to string
            stmt.setString(5, String.join("|", patient.getSymptoms())); // Convert LinkedList to string
            stmt.setInt(6, patient.getSeverityLevel());
            stmt.setBoolean(7, patient.isOverrideEmergency());
            stmt.setString(8, patient.getLastUpdatedBy());
            stmt.setTimestamp(9, Timestamp.valueOf(patient.getLastUpdatedAt()));
            stmt.setString(10, patient.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
        }
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("diagnosis"),
                        new LinkedList<>(Arrays.asList(rs.getString("history").split("\\|"))), // Convert string to LinkedList
                        new LinkedList<>(Arrays.asList(rs.getString("symptoms").split("\\|"))), // Convert string to LinkedList
                        rs.getInt("severity_level"),
                        rs.getBoolean("override_emergency"),
                        rs.getString("last_updated_by"),
                        rs.getTimestamp("last_updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all patients: " + e.getMessage());
        }
        return patients;
    }
}
