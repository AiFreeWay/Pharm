package sample.presentation.navigator;


import sample.presentation.views.Screen;

import java.util.HashSet;

public class ScreenNavigator {

    public ScreenNavigator() {
    }

    public void showScreen(Screen screen) {
        screen.show();
    }
}
