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

public class PatientDashboard {

    public static Scene create() {
        Image logo = new Image(PatientDashboard.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);

        Text title = new Text("Patient Dashboard");
        title.setFill(Color.PURPLE);

        Button bookBtn = new Button("Book Appointment");
        Button appointmentsBtn = new Button("Manage Appointments");
        Button editBtn = new Button("Edit Profile");
        Button logoutBtn = new Button("Logout");

        VBox vbox = new VBox(20, logoView, title, bookBtn, appointmentsBtn, editBtn, logoutBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 800, 600);

        title.styleProperty().bind(scene.widthProperty().divide(18).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        for (Button btn : new Button[]{bookBtn, appointmentsBtn, editBtn, logoutBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(2));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        }

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        bookBtn.setOnAction(e -> SceneController.setScene("appointmentBooking"));
        appointmentsBtn.setOnAction(e -> SceneController.setScene("appointments")); // Fixed navigation
        editBtn.setOnAction(e -> SceneController.setScene("profile"));
        logoutBtn.setOnAction(e -> SceneController.setScene("welcome"));

        return scene;
    }
}
