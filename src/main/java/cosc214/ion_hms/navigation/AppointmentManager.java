package cosc214.ion_hms.navigation;

import cosc214.ion_hms.models.Appointment;

import java.util.PriorityQueue;
import java.util.Comparator;

public class AppointmentManager {
    private final PriorityQueue<Appointment> appointmentQueue;

    public AppointmentManager() {
        // Priority queue with a comparator to prioritize higher severity levels
        this.appointmentQueue = new PriorityQueue<>(Comparator.comparingInt(Appointment::getSeverityLevel).reversed());
    }

    // Add an appointment to the queue
    public void addAppointment(Appointment appointment) {
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment cannot be null.");
        }
        appointmentQueue.add(appointment);
    }

    // Retrieve and remove the highest-priority appointment
    public Appointment getNextAppointment() {
        return appointmentQueue.poll(); // Retrieves and removes the head of the queue
    }

    // Peek at the highest-priority appointment without removing it
    public Appointment peekNextAppointment() {
        return appointmentQueue.peek(); // Retrieves but does not remove the head of the queue
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return appointmentQueue.isEmpty();
    }

    // Get the total number of appointments in the queue
    public int getQueueSize() {
        return appointmentQueue.size();
    }

    // Clear all appointments from the queue
    public void clearAppointments() {
        appointmentQueue.clear();
    }
}
