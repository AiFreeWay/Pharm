package sample.presentation.views;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class CheckScreen implements Screen {

    public static final int CHOOSER_OPEN = -3;
    public static final int CHOOSER_SAVE = -5;
    private static CheckScreen sScreen;
    private static final String FILECHOOSER_TITLE = "Выберети файл";
    private Stage mStage;

    public CheckScreen(Stage stage) {
        mStage = stage;
        sScreen = this;
    }

    @Override
    public void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("res/check.fxml"));
            mStage.setTitle(getTitle());
            mStage.setScene(new Scene(root, 579, 448));
            mStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return "Проверить данные";
    }

    public static File showFileChooser(int chooserType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILECHOOSER_TITLE);
        if (chooserType == CHOOSER_OPEN)
            return fileChooser.showOpenDialog(sScreen.mStage);
        else
            return fileChooser.showSaveDialog(sScreen.mStage);
    }
}
