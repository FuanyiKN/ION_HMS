package cosc214.ion_hms.ui;

import cosc214.ion_hms.navigation.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DoctorDashboard {

    public static Scene create() {
        // Logo
        Image logo = new Image(DoctorDashboard.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        // Title
        Text title = new Text("Doctor Dashboard");
        title.setFill(Color.PURPLE);

        // Buttons
        Button viewAppointmentsBtn = new Button("View Appointments");
        Button manageAvailabilityBtn = new Button("Manage Availability");
        Button managePatientsBtn = new Button("Manage Assigned Patients");
        Button editProfileBtn = new Button("Edit Profile");
        Button logoutBtn = new Button("Logout");

        VBox vbox = new VBox(15, logoView, title, viewAppointmentsBtn, manageAvailabilityBtn, managePatientsBtn, editProfileBtn, logoutBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 500);

        title.styleProperty().bind(
                scene.widthProperty().divide(18).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (Button btn : new Button[]{viewAppointmentsBtn, manageAvailabilityBtn, managePatientsBtn, editProfileBtn, logoutBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
        }

        String baseButtonStyle = "-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;";
        for (Button btn : new Button[]{viewAppointmentsBtn, manageAvailabilityBtn, managePatientsBtn, editProfileBtn, logoutBtn}) {
            btn.setStyle(baseButtonStyle);
        }

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double fontSize = newVal.doubleValue() / 35;
            String style = String.format("-fx-font-size: %.1fpx; %s", fontSize, baseButtonStyle);
            for (Button btn : new Button[]{viewAppointmentsBtn, manageAvailabilityBtn, managePatientsBtn, editProfileBtn, logoutBtn}) {
                btn.setStyle(style);
            }
        });

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        // Navigation actions
        viewAppointmentsBtn.setOnAction(e -> SceneController.setScene("appointments")); // Navigate to appointments scene
        manageAvailabilityBtn.setOnAction(e -> SceneController.setScene("availability"));
        managePatientsBtn.setOnAction(e -> SceneController.setScene("assignedPatients"));
        editProfileBtn.setOnAction(e -> SceneController.setScene("doctorProfile"));
        logoutBtn.setOnAction(e -> SceneController.setScene("login"));

        return scene;
    }
}
