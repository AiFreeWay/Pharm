package sample.presentation.utils;


import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.presentation.views.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ScreenFactory {

    private Image mLogo;
    private Screen[] mScreens = new Screen[Screens.values().length];

    public ScreenFactory() {
        try {
            mLogo = new Image(new FileInputStream("src/sample/presentation/views/res/logo.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mScreens[Screens.HELP.id] = new HelpScreen(stageFactory());
        mScreens[Screens.MAIN.id] = new MainScreen(stageFactory());
        mScreens[Screens.SEARCH.id] = new SearchScreen(stageFactory());
        mScreens[Screens.CHECK.id] = new CheckScreen(stageFactory());
        mScreens[Screens.LOAD.id] = new LoadScreen(stageFactory());
        mScreens[Screens.ADD_RECORD.id] = new AddRecordScreen(stageFactory());
    }

    public Screen getScreen(Screens screenType) {
        return mScreens[screenType.id];
    }

    public enum Screens {
        MAIN(0),
        HELP(1),
        SEARCH(2),
        CHECK(3),
        LOAD(4),
        ADD_RECORD(5);

        int id;

        Screens(int id) {
            this.id = id;
        }
    }

    private Stage stageFactory() {
        Stage stage = new Stage();
        stage.getIcons().add(mLogo);
        return stage;
    }
}
