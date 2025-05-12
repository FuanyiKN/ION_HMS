package cosc214.ion_hms.ui;

import cosc214.ion_hms.dao.UserDao;
import cosc214.ion_hms.models.User;
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

public class SignupScreen {

    public static Scene create() {
        Image logo = new Image(SignupScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Create Account");
        title.setFill(Color.PURPLE);

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField contactNumberField = new TextField();
        contactNumberField.setPromptText("Contact Number");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Doctor", "Patient"); // Restricted to Doctor and Patient

        Button signupBtn = new Button("Register");
        Button backBtn = new Button("Back to Login");

        VBox vbox = new VBox(12, logoView, title, fullNameField, usernameField, emailField, contactNumberField, passwordField, confirmPasswordField, roleComboBox, signupBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 600);

        title.styleProperty().bind(
                scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (Control ctrl : new Control[]{fullNameField, usernameField, emailField, contactNumberField, passwordField, confirmPasswordField, roleComboBox, signupBtn, backBtn}) {
            ctrl.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        }

        String baseButtonStyle = "-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;";
        signupBtn.setStyle(baseButtonStyle);
        backBtn.setStyle(baseButtonStyle);

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        signupBtn.setOnAction(e -> {
            String fullName = fullNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String contactNumber = contactNumberField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String selectedRole = roleComboBox.getValue();

            if (!password.equals(confirmPassword)) {
                new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                return;
            }

            if (username.isBlank() || password.isBlank() || fullName.isBlank() || email.isBlank() || contactNumber.isBlank() || selectedRole == null) {
                new Alert(Alert.AlertType.WARNING, "All fields are required").show();
                return;
            }

            User.Role role = User.Role.valueOf(selectedRole.toUpperCase());
            User user = new User(username, fullName, password, role, email, contactNumber); // Pass fullName to the constructor

            try {
                new UserDao().registerUser(user);
                new Alert(Alert.AlertType.INFORMATION, "Registration successful! Please login.").show();
                SceneController.setScene("login");
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Username already exists or an error occurred").show();
            }
        });

        backBtn.setOnAction(e -> SceneController.setScene("login"));

        return scene;
    }
}
