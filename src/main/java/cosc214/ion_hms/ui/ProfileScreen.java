package cosc214.ion_hms.ui;

import cosc214.ion_hms.navigation.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProfileScreen {

    public static Scene create() {
        // Logo
        Image logo = new Image(ProfileScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Edit Profile");
        title.setFill(Color.PURPLE);

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField contactField = new TextField();
        contactField.setPromptText("Contact Number");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button saveBtn = new Button("Save Changes");
        Button backBtn = new Button("Back");

        VBox vbox = new VBox(12, logoView, title, nameField, ageField, contactField, emailField, saveBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 600);

        title.styleProperty().bind(
                scene.widthProperty().divide(22).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (TextField field : new TextField[]{nameField, ageField, contactField, emailField}) {
            field.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        }
        saveBtn.prefWidthProperty().bind(scene.widthProperty().divide(3));
        backBtn.prefWidthProperty().bind(scene.widthProperty().divide(3));

        String baseButtonStyle = "-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;";
        saveBtn.setStyle(baseButtonStyle);
        backBtn.setStyle(baseButtonStyle);

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        backBtn.setOnAction(e -> SceneController.setScene("patientDashboard"));

        saveBtn.setOnAction(e -> {
            // TODO: Save logic and validation
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully.");
            alert.showAndWait();
        });

        return scene;
    }
}
