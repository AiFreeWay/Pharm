package sample.presentation.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import rx.schedulers.Schedulers;
import sample.domain.interactors.LoadFile;
import sample.domain.models.Record;
import sample.domain.utils.InteractorsFactory;
import sample.presentation.Main;
import sample.presentation.views.CellViewFull;
import sample.presentation.views.LoadScreen;

import java.io.File;
import java.util.List;

public class LoadController {

    private final String PATH_TO_FILE_TITLE = "Путь к файлу";
    private final String EMPTY_LINE = "";
    private final String ERROR_MESSAGE = "Не удалось загрузить файл";
    private final String COUNT_TITLE = "Загружено ";

    private ObservableList<Record> mRecords = FXCollections.observableArrayList();

    @FXML
    private TextField loadTfFilePath;
    @FXML
    private Button loadBtnLoad;
    @FXML
    private Button loadBtnSelectFile;
    @FXML
    private Label loadLblMsg;
    @FXML
    private ListView<Record> loadLvRecords;

    private LoadFile mLoadFile;
    private int mLoadRecordsCount = 0;

    @FXML
    private void initialize() {
        mLoadFile = (LoadFile) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.LOAD_FILE);
        loadLvRecords.setCellFactory(param -> new CellViewFull());
        initClickListeners();
    }

    private void initClickListeners() {
        loadTfFilePath.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (loadTfFilePath.getText().isEmpty())
                    loadTfFilePath.setText(PATH_TO_FILE_TITLE);
            }
        });

        loadTfFilePath.setOnMouseClicked(mouseEvent -> {
            if (loadTfFilePath.getText().equals(PATH_TO_FILE_TITLE))
                loadTfFilePath.setText(EMPTY_LINE);
            loadLblMsg.setText(EMPTY_LINE);
        });

        loadBtnLoad.setOnMouseClicked(mouseEvent -> {
            if (!loadTfFilePath.getText().equals(PATH_TO_FILE_TITLE))
                loadFile(loadTfFilePath.getText());
        });

        loadBtnSelectFile.setOnMouseClicked(mouseEvent -> loadTfFilePath.setText(showFilechooser().getAbsolutePath()));
    }

    private void loadFile(String path) {
        mLoadFile.execute(path, () -> {
            ++mLoadRecordsCount;
            showMessage(getLoadMessage());
        })
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(() -> {
                    loadBtnLoad.setDisable(true);
                    mLoadRecordsCount=0;})
                .doOnCompleted(() -> {
                    loadBtnLoad.setDisable(false);
                    MainController.updateRecords();
                })
                .subscribe(this::showRecords, throwable -> {
                    loadBtnLoad.setDisable(false);
                    if (!(throwable instanceof IllegalStateException))
                        showMessage(ERROR_MESSAGE);
                    throwable.printStackTrace();
                });
    }

    private void showRecords(final List<Record> records) {
        Platform.runLater(() -> {
            mRecords.clear();
            mRecords.addAll(records);
            loadLvRecords.setItems(mRecords);
        });
    }

    private void showMessage(String message) {
        Platform.runLater(() -> loadLblMsg.setText(message));
    }

    private File showFilechooser() {
        return  LoadScreen.showFileChooser();
    }

    private String getLoadMessage() {
        return COUNT_TITLE+" "+mLoadRecordsCount+" из "+mRecords.size();
    }
}
