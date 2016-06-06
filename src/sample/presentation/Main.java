package sample.presentation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import sample.data.repository.RepositoryImpl;
import sample.domain.utils.InteractorsFactory;
import sample.domain.interfaces.Repository;
import sample.presentation.utils.ScreenFactory;
import sample.presentation.navigator.ScreenNavigator;


public class Main extends Application {

    private static ScreenNavigator sScreenNavigator;
    private static ScreenFactory sScreenFactory;
    private static InteractorsFactory sInteractorsFactory;
    private static Repository sRepository;

    @Override
    public void start(Stage primaryStage) throws Exception {
        sScreenFactory = new ScreenFactory();
        sScreenNavigator.showScreen(sScreenFactory.getScreen(ScreenFactory.Screens.MAIN));
    }


    public static void main(String[] args) {
        Platform.setImplicitExit(false);
        sScreenNavigator = new ScreenNavigator();
        sRepository = new RepositoryImpl();
        sInteractorsFactory = new InteractorsFactory();
        launch(args);
    }

    public static ScreenNavigator getScreenNavigator() {
        return sScreenNavigator;
    }

    public static ScreenFactory getScreenFactory() {
        return sScreenFactory;
    }

    public static InteractorsFactory getInteractorsFactory() {
        return sInteractorsFactory;
    }

    public static Repository getRepository() {
        return sRepository;
    }
}
