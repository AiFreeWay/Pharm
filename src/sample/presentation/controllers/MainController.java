package sample.presentation.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sample.domain.models.Record;
import sample.domain.utils.InteractorsFactory;
import sample.domain.interactors.GetData;
import sample.presentation.Main;
import sample.presentation.utils.ScreenFactory;
import sample.presentation.views.Screen;

import java.util.List;


public class MainController {

    private static MainController sMainController;
    private static int sPage = 1;

    private final Action1<List<Record>> ActionShowRecords = this::showRecords;

    private final Action1<List<Record>> ActionShowRecordsNext = records -> {
        if (records.size() > 0) {
            showRecords(records);
            sPage +=1;
            updatePageCounter();
        }
    };

    private final Action1<List<Record>> ActionShowRecordsPre = records -> {
        showRecords(records);
        sPage -=1;
        updatePageCounter();
    };

    @FXML
    private Button mainBtnSearch;
    @FXML
    private Button mainBtnCheck;
    @FXML
    private Button mainBtnHelp;
    @FXML
    private Button mainBtnLoad;
    @FXML
    private Button mainBtnNextPage;
    @FXML
    private Button mainBtnPrePage;
    @FXML
    private ListView<Record> mainLvRecords;
    @FXML
    private Label mainLblPage;
    @FXML
    private ChoiceBox<Integer> mainChbRecordsCount;

    private GetData mGetData;

    private ObservableList<Record> mRecords = FXCollections.observableArrayList();
    private final ObservableList<Integer> mRecordsCounts = FXCollections.observableArrayList(30, 50, 100);

    public static void updateRecords() {
        sMainController.loadData(sPage, sMainController.ActionShowRecords);
    }

    @FXML
    private void initialize() {
        sMainController = this;
        mGetData = (GetData) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.GET_DATA);
        initClickListeners();
        mainChbRecordsCount.setItems(mRecordsCounts);
        mainChbRecordsCount.setValue(mRecordsCounts.get(0));
        loadData(sPage, ActionShowRecords);
    }

    private void initClickListeners() {
        mainBtnSearch.setOnMouseClicked(mouseEvent -> {
            Screen screen = Main.getScreenFactory().getScreen(ScreenFactory.Screens.SEARCH);
            Main.getScreenNavigator().showScreen(screen);
        });

        mainBtnCheck.setOnMouseClicked(mouseEvent -> {
            Screen screen = Main.getScreenFactory().getScreen(ScreenFactory.Screens.CHECK);
            Main.getScreenNavigator().showScreen(screen);
        });

        mainBtnHelp.setOnMouseClicked(mouseEvent -> {
            Screen screen = Main.getScreenFactory().getScreen(ScreenFactory.Screens.HELP);
            Main.getScreenNavigator().showScreen(screen);
        });

        mainBtnLoad.setOnMouseClicked(mouseEvent -> {
            Screen screen = Main.getScreenFactory().getScreen(ScreenFactory.Screens.LOAD);
            Main.getScreenNavigator().showScreen(screen);
        });

        mainBtnNextPage.setOnMouseClicked(mouseEvent -> loadData(sPage+1, ActionShowRecordsNext));

        mainBtnPrePage.setOnMouseClicked(mouseEvent -> {
            if (sPage > 1)
                loadData(sPage-1, ActionShowRecordsPre);
        });
    }

    private void loadData(int page, Action1<List<Record>> action1) {
        mGetData.execute(page, mainChbRecordsCount.getValue())
                .subscribeOn(Schedulers.newThread())
                .subscribe(action1, Throwable::printStackTrace);
    }

    private void showRecords(final List<Record> records) {
        Platform.runLater(() -> {
            mRecords.clear();
            appearRecords(records);
        });
    }

    private void appearRecords(final List<Record> records) {
        Platform.runLater(() -> {
            mRecords.addAll(records);
            mainLvRecords.setItems(mRecords);
        });
    }

    private void updatePageCounter() {
        Platform.runLater(() -> mainLblPage.setText("Страница "+ sPage));
    }
}
