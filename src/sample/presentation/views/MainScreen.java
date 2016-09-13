package sample.presentation.views;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainScreen implements Screen {

    private Stage mStage;

    public MainScreen(Stage stage) {
        mStage = stage;
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    @Override
    public void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("res/s_main.fxml"));
            mStage.setTitle(getTitle());
            mStage.setScene(new Scene(root, 600, 520));
            mStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return "Учёт лекарственных средств";
    }
}
