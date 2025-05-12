package cosc214.ion_hms.ui;

import cosc214.ion_hms.navigation.SceneController;
import cosc214.ion_hms.models.Patient;
import cosc214.ion_hms.dao.PatientDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class AssignedPatientsScreen {

    public static Scene create() {
        Image logo = new Image(AssignedPatientsScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Assigned Patients");
        title.setFill(Color.PURPLE);

        TableView<Patient> table = new TableView<>();
        table.setPlaceholder(new Label("No assigned patients."));

        TableColumn<Patient, String> idCol = new TableColumn<>("Patient ID");
        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        TableColumn<Patient, String> diagnosisCol = new TableColumn<>("Diagnosis");
        TableColumn<Patient, String> severityCol = new TableColumn<>("Severity");
        table.getColumns().addAll(idCol, nameCol, diagnosisCol, severityCol);

        Button backBtn = new Button("Back");

        VBox vbox = new VBox(15, logoView, title, table, backBtn);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 800, 600);

        title.styleProperty().bind(
                scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        table.prefWidthProperty().bind(scene.widthProperty().divide(1.2));
        table.prefHeightProperty().bind(scene.heightProperty().divide(2));

        backBtn.prefWidthProperty().bind(scene.widthProperty().divide(3));
        backBtn.prefHeightProperty().bind(scene.heightProperty().divide(15));
        backBtn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        // Load data from the database
        PatientDao patientDao = new PatientDao();
        List<Patient> patients = patientDao.getAllPatients(); // Replace with logic to fetch assigned patients
        table.getItems().addAll(patients);

        backBtn.setOnAction(e -> SceneController.setScene("doctorDashboard"));

        return scene;
    }
}
