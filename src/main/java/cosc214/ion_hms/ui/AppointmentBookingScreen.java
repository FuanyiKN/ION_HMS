package cosc214.ion_hms.ui;

import cosc214.ion_hms.dao.AppointmentDao;
import cosc214.ion_hms.models.Appointment;
import cosc214.ion_hms.navigation.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBookingScreen {

    public static Scene create() {
        Image logo = new Image(AppointmentBookingScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Book an Appointment");
        title.setFill(Color.PURPLE);

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");

        ComboBox<String> timeSlotBox = new ComboBox<>();
        timeSlotBox.setPromptText("Select Time Slot");
        timeSlotBox.getItems().addAll("09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM");

        Label symptomsLabel = new Label("Select Symptoms:");
        symptomsLabel.setStyle("-fx-font-weight: bold");

        List<CheckBox> symptomChecks = new ArrayList<>();
        String[] symptoms = {
                "Chest pain", "Shortness of breath", "Severe bleeding", "Loss of consciousness",
                "High fever", "Seizures", "Severe headache", "Persistent vomiting",
                "Abdominal pain", "Severe injury"
        };
        for (String symptom : symptoms) {
            CheckBox cb = new CheckBox(symptom);
            cb.setWrapText(true);
            symptomChecks.add(cb);
        }

        TextField otherSymptomsField = new TextField();
        otherSymptomsField.setPromptText("Other symptoms...");

        ComboBox<String> doctorSelector = new ComboBox<>();
        doctorSelector.setPromptText("Select Doctor (available)");
        doctorSelector.setDisable(true);

        Button submitBtn = new Button("Submit");
        Button backBtn = new Button("Back");

        VBox symptomsBox = new VBox(5);
        symptomsBox.getChildren().add(symptomsLabel);
        symptomsBox.getChildren().addAll(symptomChecks);
        symptomsBox.getChildren().add(otherSymptomsField);
        symptomsBox.setAlignment(Pos.CENTER_LEFT);

        VBox vbox = new VBox(12, logoView, title, datePicker, timeSlotBox, symptomsBox, doctorSelector, submitBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 700, 700);

        title.styleProperty().bind(
                scene.widthProperty().divide(22).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (Control ctrl : new Control[]{datePicker, timeSlotBox, otherSymptomsField, doctorSelector, submitBtn, backBtn}) {
            ctrl.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        }

        String baseButtonStyle = "-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;";
        submitBtn.setStyle(baseButtonStyle);
        backBtn.setStyle(baseButtonStyle);

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        submitBtn.setOnAction(e -> {
            int severityScore = 0;
            for (CheckBox cb : symptomChecks) {
                if (cb.isSelected()) severityScore += 2;
            }
            if (!otherSymptomsField.getText().isBlank()) severityScore += 1;

            boolean isEmergency = severityScore >= 7;
            doctorSelector.setDisable(false);
            doctorSelector.getItems().clear();
            doctorSelector.getItems().addAll(
                    isEmergency ? List.of("D001", "D002") : List.of("D003", "D004")
            );

            // Sample placeholders - in real use, you’d gather these dynamically:
            String selectedDoctor = doctorSelector.getValue();
            LocalDate date = datePicker.getValue();
            String timeStr = timeSlotBox.getValue();
            if (selectedDoctor == null || date == null || timeStr == null) {
                new Alert(Alert.AlertType.WARNING, "Please select all fields including doctor.").show();
                return;
            }

            LocalTime time = LocalTime.parse(timeStr.replace(" AM", "").replace(" PM", ""));
            LocalDateTime dateTime = LocalDateTime.of(date, time);

            Appointment appointment = new Appointment(
                    (int) (Math.random() * 10000), // temp ID generation
                    "P001",                        // placeholder patient ID
                    selectedDoctor,
                    dateTime,
                    isEmergency,
                    severityScore,
                    "P001",                        // placeholder updater
                    LocalDateTime.now()
            );

            new AppointmentDao().addAppointment(appointment);
            new Alert(Alert.AlertType.INFORMATION, "Appointment submitted successfully.").show();
            SceneController.setScene("patientDashboard");
        });

        backBtn.setOnAction(e -> SceneController.setScene("patientDashboard"));

        return scene;
    }
}
