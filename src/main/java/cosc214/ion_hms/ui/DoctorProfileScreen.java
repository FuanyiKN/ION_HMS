package cosc214.ion_hms.ui;

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

public class DoctorProfileScreen {

    public static Scene create() {
        // Logo
        Image logo = new Image(DoctorProfileScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Doctor Profile");
        title.setFill(Color.PURPLE);

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField contactField = new TextField();
        contactField.setPromptText("Contact Number");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextArea specializationsArea = new TextArea();
        specializationsArea.setPromptText("Specializations (comma-separated)");
        specializationsArea.setWrapText(true);
        specializationsArea.setPrefRowCount(2);

        Button updateBtn = new Button("Update Profile");
        Button backBtn = new Button("Back");

        VBox vbox = new VBox(12, logoView, title, nameField, contactField, emailField, specializationsArea, updateBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 600);

        title.styleProperty().bind(
                scene.widthProperty().divide(22).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        nameField.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        contactField.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        emailField.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        specializationsArea.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        updateBtn.prefWidthProperty().bind(scene.widthProperty().divide(3));
        backBtn.prefWidthProperty().bind(scene.widthProperty().divide(3));

        String baseButtonStyle = "-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;";
        updateBtn.setStyle(baseButtonStyle);
        backBtn.setStyle(baseButtonStyle);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double fontSize = newVal.doubleValue() / 35;
            String style = String.format("-fx-font-size: %.1fpx; %s", fontSize, baseButtonStyle);
            updateBtn.setStyle(style);
            backBtn.setStyle(style);
        });

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        backBtn.setOnAction(e -> SceneController.setScene("doctorDashboard"));

        updateBtn.setOnAction(e -> {
            // Placeholder for backend integration
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String email = emailField.getText().trim();
            String specializations = specializationsArea.getText().trim();

            if (name.isEmpty() || contact.isEmpty() || email.isEmpty() || specializations.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled.");
                alert.showAndWait();
            } else {
                // TODO: Update doctor profile in the backend
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully.");
                alert.showAndWait();
            }
        });

        return scene;
    }
}
