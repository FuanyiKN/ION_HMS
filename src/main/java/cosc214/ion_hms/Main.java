package cosc214.ion_hms;

import cosc214.ion_hms.navigation.SceneController;
import cosc214.ion_hms.ui.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneController.initialize(primaryStage);

        // Register all scenes
        SceneController.register("welcome", WelcomeScreen::create);
        SceneController.register("login", LoginScreen::create);
        SceneController.register("signup", SignupScreen::create);
        SceneController.register("adminDashboard", AdminDashboard::create);
        SceneController.register("doctorDashboard", DoctorDashboard::create);
        SceneController.register("patientDashboard", PatientDashboard::create);
        SceneController.register("appointmentBooking", AppointmentBookingScreen::create);
        SceneController.register("profile", ProfileScreen::create);
        SceneController.register("manageUsers", ManageUsersScreen::create);
        SceneController.register("manageDoctors", ManageDoctorsScreen::create);
        SceneController.register("managePatients", ManagePatientsScreen::create);
        SceneController.register("availability", AvailabilityScreen::create);
        SceneController.register("assignedPatients", AssignedPatientsScreen::create);
        SceneController.register("doctorProfile", DoctorProfileScreen::create);
        SceneController.register("appointments", AppointmentListScreen::create); // Ensure appointments scene is registered

        // Set the initial scene
        SceneController.setScene("welcome");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
