package cosc214.ion_hms.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Doctor {
    private final String id; // Made immutable
    private String name;
    private Set<String> specialization; // Changed to Set to ensure uniqueness
    private Set<String> availableHours; // Changed to Set to ensure uniqueness
    private LinkedList<String> assignedPatients; // Changed to LinkedList
    private Stack<String> undoAssignedPatientsStack; // Added for undo functionality
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;

    public Doctor(String id, String name, List<String> specialization,
                  List<String> availableHours, LinkedList<String> assignedPatients,
                  String lastUpdatedBy, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.name = name;
        this.specialization = new HashSet<>(specialization); // Ensure uniqueness
        setAvailableHours(availableHours); // Validate and set available hours
        this.assignedPatients = assignedPatients != null ? assignedPatients : new LinkedList<>();
        this.undoAssignedPatientsStack = new Stack<>();
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    // Removed setter for id to make it immutable

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<String> specialization) {
        this.specialization = new HashSet<>(specialization); // Ensure uniqueness
    }

    public void addSpecialization(String specialization) {
        if (specialization == null || specialization.isEmpty()) {
            throw new IllegalArgumentException("Specialization cannot be null or empty.");
        }
        this.specialization.add(specialization);
    }

    public void removeSpecialization(String specialization) {
        this.specialization.remove(specialization);
    }

    public Set<String> getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(List<String> availableHours) {
        this.availableHours = new HashSet<>();
        for (String hour : availableHours) {
            addAvailableHour(hour); // Validate each hour
        }
    }

    public void addAvailableHour(String hour) {
        if (!isValidTimeFormat(hour)) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:mm.");
        }
        this.availableHours.add(hour);
    }

    public void removeAvailableHour(String hour) {
        this.availableHours.remove(hour);
    }

    public LinkedList<String> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(List<String> assignedPatients) {
        this.assignedPatients = new LinkedList<>(assignedPatients); // Ensure uniqueness
    }

    public void assignPatient(String patientId) {
        undoAssignedPatientsStack.push("Removed: " + patientId); // Save undo state
        assignedPatients.add(patientId);
    }

    public void undoLastPatientAssignment() {
        if (!undoAssignedPatientsStack.isEmpty()) {
            String lastUndo = undoAssignedPatientsStack.pop();
            System.out.println("Undoing: " + lastUndo);
            assignedPatients.removeLast();
        } else {
            System.out.println("No patient assignments to undo.");
        }
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

    // Method to check if a doctor is available at a specific hour
    public boolean isAvailable(String hour) {
        return availableHours.contains(hour);
    }

    // Helper method to validate time format
    private boolean isValidTimeFormat(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            formatter.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialization=" + specialization +
                ", availableHours=" + availableHours +
                ", assignedPatients=" + assignedPatients +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
