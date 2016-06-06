package sample.presentation.utils;


import javafx.stage.Stage;
import sample.presentation.views.*;


public class ScreenFactory {

    private Screen[] mScreens = new Screen[Screens.values().length];

    public ScreenFactory() {
        mScreens[Screens.HELP.id] = new HelpScreen(new Stage());
        mScreens[Screens.MAIN.id] = new MainScreen(new Stage());
        mScreens[Screens.SEARCH.id] = new SearchScreen(new Stage());
        mScreens[Screens.CHECK.id] = new CheckScreen(new Stage());
        mScreens[Screens.LOAD.id] = new LoadScreen(new Stage());
    }

    public Screen getScreen(Screens screenType) {
        return mScreens[screenType.id];
    }

    public enum Screens {
        MAIN(0),
        HELP(1),
        SEARCH(2),
        CHECK(3),
        LOAD(4);

        int id;

        Screens(int id) {
            this.id = id;
        }
    }
}
