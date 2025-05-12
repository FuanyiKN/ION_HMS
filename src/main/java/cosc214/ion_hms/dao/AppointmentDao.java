package cosc214.ion_hms.dao;

import cosc214.ion_hms.models.Appointment;
import cosc214.ion_hms.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    private final Connection conn = DatabaseUtil.getConnection();

    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (id, patient_id, doctor_id, appointment_time, is_emergency, severity_level, last_updated_by, last_updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getId());
            stmt.setString(2, appointment.getPatientId());
            stmt.setString(3, appointment.getDoctorId());
            stmt.setTimestamp(4, Timestamp.valueOf(appointment.getTime()));
            stmt.setBoolean(5, appointment.isEmergency());
            stmt.setInt(6, appointment.getSeverityLevel());
            stmt.setString(7, appointment.getLastUpdatedBy());
            stmt.setTimestamp(8, Timestamp.valueOf(appointment.getLastUpdatedAt()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding appointment: " + e.getMessage());
        }
    }

    public Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getTimestamp("appointment_time").toLocalDateTime(),
                        rs.getBoolean("is_emergency"),
                        rs.getInt("severity_level"),
                        rs.getString("last_updated_by"),
                        rs.getTimestamp("last_updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Error fetching appointment by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getTimestamp("appointment_time").toLocalDateTime(),
                        rs.getBoolean("is_emergency"),
                        rs.getInt("severity_level"),
                        rs.getString("last_updated_by"),
                        rs.getTimestamp("last_updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all appointments: " + e.getMessage());
        }
        return appointments;
    }

    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_time = ?, is_emergency = ?, severity_level = ?, last_updated_by = ?, last_updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getPatientId());
            stmt.setString(2, appointment.getDoctorId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getTime()));
            stmt.setBoolean(4, appointment.isEmergency());
            stmt.setInt(5, appointment.getSeverityLevel());
            stmt.setString(6, appointment.getLastUpdatedBy());
            stmt.setTimestamp(7, Timestamp.valueOf(appointment.getLastUpdatedAt()));
            stmt.setInt(8, appointment.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
    }

    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting appointment: " + e.getMessage());
        }
    }
}
