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

public class AvailabilityScreen {

    public static Scene create() {
        Image logo = new Image(AvailabilityScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Manage Availability");
        title.setFill(Color.PURPLE);

        TextField hourField = new TextField();
        hourField.setPromptText("Add Available Hour (e.g., 09:00-11:00)");

        Button addBtn = new Button("Add");
        Button removeBtn = new Button("Remove");
        Button backBtn = new Button("Back");

        VBox vbox = new VBox(15, logoView, title, hourField, addBtn, removeBtn, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 600, 500);

        title.styleProperty().bind(
                scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        hourField.prefWidthProperty().bind(scene.widthProperty().divide(2.5));
        for (Button btn : new Button[]{addBtn, removeBtn, backBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(3));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        }

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        addBtn.setOnAction(e -> {
            String hour = hourField.getText().trim();
            if (hour.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter an available hour.").show();
                return;
            }
            // TODO: Add logic to update availability in the backend
            new Alert(Alert.AlertType.INFORMATION, "Availability added successfully.").show();
        });

        removeBtn.setOnAction(e -> {
            String hour = hourField.getText().trim();
            if (hour.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please enter an hour to remove.").show();
                return;
            }
            // TODO: Add logic to remove availability in the backend
            new Alert(Alert.AlertType.INFORMATION, "Availability removed successfully.").show();
        });

        backBtn.setOnAction(e -> SceneController.setScene("doctorDashboard"));

        return scene;
    }
}
