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

public class AdminDashboard {

    public static Scene create() {
        // Logo
        Image logo = new Image(AdminDashboard.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        // Title
        Text title = new Text("Admin Dashboard");
        title.setFill(Color.PURPLE);

        // Buttons
        Button manageUsersBtn = new Button("Manage Users");
        Button manageDoctorsBtn = new Button("Manage Doctors");
        Button managePatientsBtn = new Button("Manage Patients");
        Button manageAppointmentsBtn = new Button("Manage Appointments");
        Button logoutBtn = new Button("Logout");

        VBox vbox = new VBox(15, logoView, title, manageUsersBtn, manageDoctorsBtn, managePatientsBtn, manageAppointmentsBtn, logoutBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 500);

        title.styleProperty().bind(
                scene.widthProperty().divide(18).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (Button btn : new Button[]{manageUsersBtn, manageDoctorsBtn, managePatientsBtn, manageAppointmentsBtn, logoutBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
        }

        String baseButtonStyle = "-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;";
        for (Button btn : new Button[]{manageUsersBtn, manageDoctorsBtn, managePatientsBtn, manageAppointmentsBtn, logoutBtn}) {
            btn.setStyle(baseButtonStyle);
        }

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double fontSize = newVal.doubleValue() / 35;
            String style = String.format("-fx-font-size: %.1fpx; %s", fontSize, baseButtonStyle);
            for (Button btn : new Button[]{manageUsersBtn, manageDoctorsBtn, managePatientsBtn, manageAppointmentsBtn, logoutBtn}) {
                btn.setStyle(style);
            }
        });

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        // Navigation
        manageUsersBtn.setOnAction(e -> SceneController.setScene("manageUsers"));
        manageDoctorsBtn.setOnAction(e -> SceneController.setScene("manageDoctors"));
        managePatientsBtn.setOnAction(e -> SceneController.setScene("managePatients"));
        manageAppointmentsBtn.setOnAction(e -> SceneController.setScene("appointments"));
        logoutBtn.setOnAction(e -> SceneController.setScene("welcome")); // Added logout functionality

        return scene;
    }
}
