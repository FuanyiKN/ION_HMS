package cosc214.ion_hms.ui;

import cosc214.ion_hms.dao.PatientDao;
import cosc214.ion_hms.models.Patient;
import cosc214.ion_hms.navigation.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class ManagePatientsScreen {

    public static Scene create() {
        Image logo = new Image(ManagePatientsScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Manage Patients");
        title.setFill(Color.PURPLE);

        TableView<Patient> patientTable = new TableView<>();
        patientTable.setPlaceholder(new Label("No patient records found."));

        TableColumn<Patient, String> idCol = new TableColumn<>("Patient ID");
        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        TableColumn<Patient, Integer> ageCol = new TableColumn<>("Age");
        TableColumn<Patient, String> diagnosisCol = new TableColumn<>("Diagnosis");
        TableColumn<Patient, Integer> severityCol = new TableColumn<>("Severity");
        patientTable.getColumns().addAll(idCol, nameCol, ageCol, diagnosisCol, severityCol);

        // Load data from the database
        PatientDao patientDao = new PatientDao();
        List<Patient> patients = patientDao.getAllPatients();
        patientTable.getItems().addAll(patients);

        Button editBtn = new Button("Edit Patient");
        Button deleteBtn = new Button("Delete Patient");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10, editBtn, deleteBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(15, logoView, title, patientTable, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 800, 600);

        title.styleProperty().bind(scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        patientTable.prefWidthProperty().bind(scene.widthProperty().divide(1.2));
        patientTable.prefHeightProperty().bind(scene.heightProperty().divide(2));

        for (Button btn : new Button[]{editBtn, deleteBtn, backBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(4));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        }

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        backBtn.setOnAction(e -> SceneController.setScene("adminDashboard"));

        return scene;
    }
}
