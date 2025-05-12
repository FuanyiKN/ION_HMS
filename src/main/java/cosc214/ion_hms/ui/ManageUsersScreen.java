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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class ManageUsersScreen {

    public static Scene create() {
        Image logo = new Image(ManageUsersScreen.class.getResource("/cosc214/ion_hms/logo.png").toExternalForm());
        ImageView logoView = new ImageView(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(100);

        Text title = new Text("Manage Users");
        title.setFill(Color.PURPLE);

        TableView<User> userTable = new TableView<>();
        userTable.setPlaceholder(new Label("No users found."));

        TableColumn<User, String> idCol = new TableColumn<>("User ID");
        TableColumn<User, String> nameCol = new TableColumn<>("Username");
        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        TableColumn<User, String> roleCol = new TableColumn<>("Role");

        userTable.getColumns().addAll(idCol, nameCol, emailCol, roleCol);

        // Load data from the database
        UserDao userDao = new UserDao();
        List<User> users = List.of(
                userDao.getUserById("D001"), // Replace with actual DAO logic to fetch all users
                userDao.getUserById("P001")
        );
        userTable.getItems().addAll(users);

        Button promoteBtn = new Button("Promote to Admin");
        Button deleteBtn = new Button("Delete User");
        Button backBtn = new Button("Back");

        promoteBtn.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a user to promote.").show();
                return;
            }
            if (selectedUser.getRole() == User.Role.ADMIN) {
                new Alert(Alert.AlertType.ERROR, "User is already an Admin.").show();
                return;
            }
            new Alert(Alert.AlertType.INFORMATION, "Admins can only be created manually in the database.").show();
        });

        deleteBtn.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                new Alert(Alert.AlertType.WARNING, "Please select a user to delete.").show();
                return;
            }
            userDao.deleteUser(selectedUser.getId());
            userTable.getItems().remove(selectedUser);
            new Alert(Alert.AlertType.INFORMATION, "User deleted successfully.").show();
        });

        backBtn.setOnAction(e -> SceneController.setScene("adminDashboard"));

        HBox buttonBox = new HBox(10, promoteBtn, deleteBtn, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(15, logoView, title, userTable, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #f3e6ff, #dab6fc);");

        Scene scene = new Scene(vbox, 800, 600);

        title.styleProperty().bind(
                scene.widthProperty().divide(20).asString("-fx-font-size: %.0fpx; -fx-font-weight: bold;")
        );

        userTable.prefWidthProperty().bind(scene.widthProperty().divide(1.2));
        userTable.prefHeightProperty().bind(scene.heightProperty().divide(2));

        for (Button btn : new Button[]{promoteBtn, deleteBtn, backBtn}) {
            btn.prefWidthProperty().bind(scene.widthProperty().divide(4));
            btn.prefHeightProperty().bind(scene.heightProperty().divide(15));
            btn.setStyle("-fx-font-weight: bold; -fx-background-color: #a066c9; -fx-text-fill: white;");
        }

        logoView.fitWidthProperty().bind(scene.widthProperty().divide(4));

        return scene;
    }
}
