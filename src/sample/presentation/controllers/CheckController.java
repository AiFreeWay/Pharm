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
import sample.domain.interactors.CheckFile;
import sample.domain.interactors.Download;
import sample.domain.models.Record;
import sample.domain.utils.InteractorsFactory;
import sample.presentation.Main;
import sample.presentation.views.CheckScreen;

import java.io.File;
import java.util.List;

public class CheckController {

    private final String ERROR_MESSAGE = "Не удалось загрузить файл";
    private final String RESULT_MESSAGE = "Количество совпадений: ";
    private final String PATH_TO_FILE_TITLE = "Путь к файлу";
    private final String DOWNLOAD_ERROR = "Не удалось сохранить в файл";
    private final String EMPTY_LINE = "";

    private ObservableList<Record> mRecords = FXCollections.observableArrayList();

    @FXML
    private TextField checkTfFilePath;
    @FXML
    private Button checkBtnCheck;
    @FXML
    private Button checkBtnDownload;
    @FXML
    private Button checkBtnSelectFile;
    @FXML
    private Label checkLblMsg;
    @FXML
    private ListView<Record> checkLvRecords;

    private CheckFile mCheckFile;
    private Download mDownload;

    @FXML
    private void initialize() {
        mCheckFile = (CheckFile) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.CHECK_FILE);
        mDownload = (Download) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.DOWNLOAD);
        initClickListeners();
    }

    private void initClickListeners() {
        checkTfFilePath.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (checkTfFilePath.getText().isEmpty())
                    checkTfFilePath.setText(PATH_TO_FILE_TITLE);
            }
        });

        checkTfFilePath.setOnMouseClicked(mouseEvent -> {
            if (checkTfFilePath.getText().equals(PATH_TO_FILE_TITLE))
                checkTfFilePath.setText(EMPTY_LINE);
            checkLblMsg.setText(EMPTY_LINE);
        });

        checkBtnCheck.setOnMouseClicked(mouseEvent -> {
            if (!checkTfFilePath.getText().equals(PATH_TO_FILE_TITLE))
                checkFile(checkTfFilePath.getText());
        });

        checkBtnSelectFile.setOnMouseClicked(mouseEvent -> checkTfFilePath.setText(showFilechooser(CheckScreen.CHOOSER_OPEN).getAbsolutePath()));

        checkBtnDownload.setOnMouseClicked(mouseEvent -> {
            if (mRecords.size()>0)
                mDownload.execute(mRecords, showFilechooser(CheckScreen.CHOOSER_SAVE))
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(aVoid -> {

                        }, throwable -> {
                            showMessage(DOWNLOAD_ERROR);
                        });
        });
    }

    private void checkFile(String path) {
        mCheckFile.execute(path)
                .subscribeOn(Schedulers.newThread())
                .subscribe(records -> {
                    showRecords(records);
                    showMessage(RESULT_MESSAGE+records.size());
                }, throwable -> {
                    if (!(throwable instanceof IllegalStateException))
                        showMessage(ERROR_MESSAGE);
                    throwable.printStackTrace();
                });
    }

    private void showRecords(final List<Record> records) {
        Platform.runLater(() -> {
            mRecords.clear();
            mRecords.addAll(records);
            checkLvRecords.setItems(mRecords);
        });
    }

    private void showMessage(final String message) {
        Platform.runLater(() -> checkLblMsg.setText(message));
    }

    private File showFilechooser(int chooserType) {
        return  CheckScreen.showFileChooser(chooserType);
    }
}
