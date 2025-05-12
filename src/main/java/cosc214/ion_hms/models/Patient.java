package cosc214.ion_hms.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class Patient {
    private final String id; // Made immutable
    private String name;
    private int age;
    private String diagnosis;
    private LinkedList<String> history; // Changed to LinkedList
    private LinkedList<String> symptoms; // Changed to LinkedList
    private int severityLevel;
    private boolean overrideEmergency;
    private String lastUpdatedBy;
    private LocalDateTime lastUpdatedAt;
    private Stack<String> undoHistoryStack; // Added for undo functionality

    // Symptom severity mapping
    private static final Map<String, Integer> SYMPTOM_SEVERITY_MAP = new HashMap<>();

    static {
        SYMPTOM_SEVERITY_MAP.put("Fever", 2);
        SYMPTOM_SEVERITY_MAP.put("Cough", 1);
        SYMPTOM_SEVERITY_MAP.put("Chest Pain", 5);
        SYMPTOM_SEVERITY_MAP.put("Shortness of Breath", 7);
        SYMPTOM_SEVERITY_MAP.put("Unconsciousness", 10);
        SYMPTOM_SEVERITY_MAP.put("Headache", 3);
        SYMPTOM_SEVERITY_MAP.put("Nausea", 2);
        SYMPTOM_SEVERITY_MAP.put("Vomiting", 3);
        SYMPTOM_SEVERITY_MAP.put("Dizziness", 4);
        SYMPTOM_SEVERITY_MAP.put("Abdominal Pain", 4);
        SYMPTOM_SEVERITY_MAP.put("Diarrhea", 3);
        SYMPTOM_SEVERITY_MAP.put("Fatigue", 2);
        SYMPTOM_SEVERITY_MAP.put("Bleeding", 8);
        SYMPTOM_SEVERITY_MAP.put("Seizures", 9);
        SYMPTOM_SEVERITY_MAP.put("Swelling", 3);
    }

    public Patient(String id, String name, int age, String diagnosis,
                   LinkedList<String> history, LinkedList<String> symptoms,
                   int severityLevel, boolean overrideEmergency,
                   String lastUpdatedBy, LocalDateTime lastUpdatedAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.history = history != null ? history : new LinkedList<>();
        this.symptoms = symptoms != null ? symptoms : new LinkedList<>();
        this.severityLevel = severityLevel;
        this.overrideEmergency = overrideEmergency;
        this.lastUpdatedBy = lastUpdatedBy;
        this.lastUpdatedAt = lastUpdatedAt;
        this.undoHistoryStack = new Stack<>();
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    public LinkedList<String> getHistory() {
        return history;
    }
    public void setHistory(LinkedList<String> history) {
        this.history = history;
    }
    public LinkedList<String> getSymptoms() {
        return symptoms;
    }
    public void setSymptoms(LinkedList<String> symptoms) {
        this.symptoms = symptoms;
        calculateSeverityLevel(); // Recalculate severity level whenever symptoms are updated
    }
    public int getSeverityLevel() {
        return severityLevel;
    }
    public void setSeverityLevel(int severityLevel) {
        this.severityLevel = severityLevel;
    }
    public boolean isOverrideEmergency() {
        return overrideEmergency;
    }
    public void setOverrideEmergency(boolean overrideEmergency) {
        this.overrideEmergency = overrideEmergency;
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

    public void addHistory(String entry) {
        undoHistoryStack.push("Removed: " + history.peekLast()); // Save undo state
        history.add(entry);
    }

    public void undoLastHistoryChange() {
        if (!undoHistoryStack.isEmpty()) {
            String lastUndo = undoHistoryStack.pop();
            System.out.println("Undoing: " + lastUndo);
            history.removeLast();
        } else {
            System.out.println("No history changes to undo.");
        }
    }

    public void addSymptom(String symptom) {
        symptoms.add(symptom);
    }

    public void removeSymptom(String symptom) {
        symptoms.remove(symptom);
    }

    public String getSummary() {
        return name + ", Age: " + age + ", Diagnosis: " + diagnosis;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", diagnosis='" + diagnosis + '\'' +
                ", history=" + history +
                ", symptoms=" + symptoms +
                ", severityLevel=" + severityLevel +
                ", overrideEmergency=" + overrideEmergency +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }

    // Private method to calculate severity level based on symptoms
    private void calculateSeverityLevel() {
        int totalSeverity = symptoms.stream()
                .mapToInt(symptom -> SYMPTOM_SEVERITY_MAP.getOrDefault(symptom, 0))
                .sum();
        int symptomCount = symptoms.size();
        this.severityLevel = symptomCount > 0 ? totalSeverity / symptomCount : 0; // Weighted average
    }
}
