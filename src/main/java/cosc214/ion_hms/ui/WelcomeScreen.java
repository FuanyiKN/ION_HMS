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

public class WelcomeScreen {

    public static Scene create() {
        // Logo
        Image logo = new Image(WelcomeScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(120);

        Text welcomeText = new Text("Welcome to ionHMS");
        welcomeText.setFill(Color.PURPLE);

        Button loginBtn = new Button("Login");
        Button signupBtn = new Button("Sign Up");

        VBox vbox = new VBox(15, logoView, welcomeText, loginBtn, signupBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 500);

        welcomeText.styleProperty().bind(
                scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        for (Button btn : new Button[]{loginBtn, signupBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(3));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        }

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        loginBtn.setOnAction(e -> SceneController.setScene("login"));
        signupBtn.setOnAction(e -> SceneController.setScene("signup"));

        return scene;
    }
}
