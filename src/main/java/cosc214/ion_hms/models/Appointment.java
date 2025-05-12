package cosc214.ion_hms.models;

import java.time.LocalDateTime;

public class Appointment {
    private final int id; // Made immutable
    private String patientId;
    private String doctorId;
    private LocalDateTime time;
    private boolean isEmergency;
    private int severityLevel;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    public Appointment(int id, String patientId, String doctorId,
                       LocalDateTime time, boolean isEmergency, int severityLevel,
                       String lastUpdatedBy, LocalDateTime lastUpdatedAt) {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be null or empty.");
        }
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID cannot be null or empty.");
        }
        if (severityLevel < 0 || severityLevel > 10) {
            throw new IllegalArgumentException("Severity level must be between 0 and 10.");
        }
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.time = time;
        this.isEmergency = isEmergency;
        this.severityLevel = severityLevel;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public int getId() {
        return id;
    }

    // Removed setter for id to make it immutable

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        if (patientId == null || patientId.isEmpty()) {
            throw new IllegalArgumentException("Patient ID cannot be null or empty.");
        }
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        if (doctorId == null || doctorId.isEmpty()) {
            throw new IllegalArgumentException("Doctor ID cannot be null or empty.");
        }
        this.doctorId = doctorId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
    }

    public int getSeverityLevel() {
        return severityLevel;
    }

    public void setSeverityLevel(int severityLevel) {
        if (severityLevel < 0 || severityLevel > 10) {
            throw new IllegalArgumentException("Severity level must be between 0 and 10.");
        }
        this.severityLevel = severityLevel;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", time=" + time +
                ", isEmergency=" + isEmergency +
                ", severityLevel=" + severityLevel +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
