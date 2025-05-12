package cosc214.ion_hms.ui;

import cosc214.ion_hms.dao.DoctorDao;
import cosc214.ion_hms.models.Doctor;
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

public class ManageDoctorsScreen {

    public static Scene create() {
        Image logo = new Image(ManageDoctorsScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Manage Doctors");
        title.setFill(Color.PURPLE);

        TableView<Doctor> doctorTable = new TableView<>();
        doctorTable.setPlaceholder(new Label("No doctors available."));

        TableColumn<Doctor, String> idCol = new TableColumn<>("Doctor ID");
        TableColumn<Doctor, String> nameCol = new TableColumn<>("Name");
        TableColumn<Doctor, String> specializationCol = new TableColumn<>("Specialization");
        TableColumn<Doctor, String> assignedCol = new TableColumn<>("Assigned Patients");
        doctorTable.getColumns().addAll(idCol, nameCol, specializationCol, assignedCol);

        // Load data from the database
        DoctorDao doctorDao = new DoctorDao();
        List<Doctor> doctors = doctorDao.getAllDoctors();
        doctorTable.getItems().addAll(doctors);

        Button assignPatientBtn = new Button("Assign Patient");
        Button removeDoctorBtn = new Button("Remove Doctor");
        Button backBtn = new Button("Back");

        HBox buttonBox = new HBox(10, assignPatientBtn, removeDoctorBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(15, logoView, title, doctorTable, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 800, 600);

        title.styleProperty().bind(scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;"));

        doctorTable.prefWidthProperty().bind(scene.widthProperty().divide(1.2));
        doctorTable.prefHeightProperty().bind(scene.heightProperty().divide(2));

        for (Button btn : new Button[]{assignPatientBtn, removeDoctorBtn, backBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(4));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        }

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        backBtn.setOnAction(e -> SceneController.setScene("adminDashboard"));

        return scene;
    }
}
