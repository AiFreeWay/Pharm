package sample.presentation.views;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelpScreen implements Screen {

    private Stage mStage;

    public HelpScreen(Stage stage) {
        mStage = stage;
    }

    @Override
    public void show() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("res/help.fxml"));
            mStage.setTitle(getTitle());
            mStage.setScene(new Scene(root, 600, 400));
            mStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return "Помощь";
    }
}
