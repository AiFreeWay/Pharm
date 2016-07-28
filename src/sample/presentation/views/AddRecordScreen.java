package sample.presentation.views;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class AddRecordScreen implements Screen {

    private Stage mStage;

    public AddRecordScreen(Stage stage) {
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
            Parent root = FXMLLoader.load(getClass().getResource("res/s_add_record.fxml"));
            mStage.setTitle(getTitle());
            mStage.setScene(new Scene(root, 600, 520));
            mStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return "Добавление исключительной записи";
    }
}
