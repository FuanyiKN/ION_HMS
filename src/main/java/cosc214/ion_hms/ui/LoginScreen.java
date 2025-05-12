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

public class LoginScreen {

    public static Scene create() {
        // Logo
        Image logo = new Image(LoginScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Login to ionHMS");
        title.setFill(Color.PURPLE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button signupBtn = new Button("Sign Up");

        VBox vbox = new VBox(12, logoView, title, usernameField, passwordField, loginBtn, signupBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 500);

        title.styleProperty().bind(
                scene.widthProperty().divide(22).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (Button btn : new Button[]{loginBtn, signupBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(3));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
        }

        loginBtn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        signupBtn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");

        // Navigation
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter both username and password.");
                alert.showAndWait();
                return;
            }

            UserDao userDao = new UserDao();
            if (userDao.login(username, password)) {
                User user = userDao.getUserById(username);
                if (user != null) {
                    switch (user.getRole()) {
                        case DOCTOR:
                            SceneController.setScene("doctorDashboard");
                            break;
                        case PATIENT:
                            SceneController.setScene("patientDashboard");
                            break;
                        default:
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid role. Only Doctors and Patients can log in.");
                            alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: User not found.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
                alert.showAndWait();
            }
        });

        signupBtn.setOnAction(e -> SceneController.setScene("signup"));

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        return scene;
    }
}
