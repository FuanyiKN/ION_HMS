package cosc214.ion_hms.navigation;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SceneController {

    private static Stage primaryStage;
    private static final Map<String, Supplier<Scene>> scenes = new HashMap<>();

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }

    public static void register(String name, Supplier<Scene> sceneSupplier) {
        scenes.put(name, sceneSupplier);
    }

    public static void setScene(String name) {
        Supplier<Scene> supplier = scenes.get(name);
        if (supplier != null && primaryStage != null) {
            Scene scene = supplier.get();
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.err.println("Scene not registered or Stage not initialized: " + name);
        }
    }

    public static Stage getStage() {
        return primaryStage;
    }
}
