package sample.presentation.views;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadScreen implements Screen {

    private static LoadScreen sScreen;
    private static final String FILECHOOSER_TITLE = "Выберети файл";
    private Stage mStage;

    public LoadScreen(Stage stage) {
        mStage = stage;
        sScreen = this;
    }

    @Override
    public void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../res/load.fxml"));
            mStage.setTitle(getTitle());
            mStage.setScene(new Scene(root, 579, 448));
            mStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return "Загрузить данные";
    }

    public static File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILECHOOSER_TITLE);
        return fileChooser.showOpenDialog(sScreen.mStage);
    }
}
