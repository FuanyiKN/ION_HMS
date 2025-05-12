package cosc214.ion_hms.ui;

import cosc214.ion_hms.dao.AppointmentDao;
import cosc214.ion_hms.models.Appointment;
import cosc214.ion_hms.navigation.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class AppointmentListScreen {

    // Default no-argument create method for SceneController registration
    public static Scene create() {
        return create(true); // Default to doctor view
    }

    // Overloaded create method with parameter for doctor or patient view
    public static Scene create(boolean isDoctorView) {
        TableView<Appointment> table = new TableView<>();
        table.setPlaceholder(new Label("No appointments available."));

        TableColumn<Appointment, Integer> idCol = new TableColumn<>("Appointment ID");
        TableColumn<Appointment, String> patientCol = new TableColumn<>("Patient ID");
        TableColumn<Appointment, String> doctorCol = new TableColumn<>("Doctor ID");
        TableColumn<Appointment, String> timeCol = new TableColumn<>("Time");
        TableColumn<Appointment, Boolean> emergencyCol = new TableColumn<>("Emergency");
        TableColumn<Appointment, Integer> severityCol = new TableColumn<>("Severity");

        table.getColumns().addAll(idCol, patientCol, doctorCol, timeCol, emergencyCol, severityCol);

        // Load data from the database
        AppointmentDao appointmentDao = new AppointmentDao();
        List<Appointment> appointments = appointmentDao.getAllAppointments(); // Replace with filtered logic
        table.getItems().addAll(appointments);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> {
            if (isDoctorView) {
                SceneController.setScene("doctorDashboard");
            } else {
                SceneController.setScene("patientDashboard");
            }
        });

        VBox vbox = new VBox(15, table, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 800, 600);

        return scene;
    }
}
