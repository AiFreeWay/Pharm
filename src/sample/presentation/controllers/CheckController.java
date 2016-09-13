package sample.presentation.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rx.schedulers.Schedulers;
import sample.domain.interactors.*;
import sample.domain.models.CheckCollections;
import sample.domain.models.Record;
import sample.domain.utils.InteractorsFactory;
import sample.presentation.Main;
import sample.presentation.views.CellViewFull;
import sample.presentation.views.CheckScreen;

import java.io.File;

public class CheckController {

    private final String ERROR_MESSAGE = "Не удалось загрузить файл";
    private final String RESULT_MESSAGE = "Количество совпадений: ";
    private final String PATH_TO_FILE_TITLE = "Путь к файлу";
    private final String DOWNLOAD_ERROR = "Не удалось сохранить в файл";
    private final String EMPTY_LINE = "";
    private final String COUNT_TITLE = "Проверено ";
    private final String REMEMBER_FILE_TAG = "rmbfltgcheck";

    private ObservableList<Record> mRecordsFromDB = FXCollections.observableArrayList();
    private ObservableList<Record> mRecordsFromFile = FXCollections.observableArrayList();

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
    private CheckBox checkChbRememberPath;
    @FXML
    private ListView<Record> checkLvRecordsFromDB;
    @FXML
    private ListView<Record> checkLvRecordsFromFile;

    private CheckFile mCheckFile;
    private Download mDownload;
    private PutPreferense mPutPreferense;
    private DeletePreferese mDeletePreferese;
    private GetPreferense mGetPreferense;
    private int mCheckedRecordsCount = 0;

    @FXML
    private void initialize() {
        mCheckFile = (CheckFile) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.CHECK_FILE);
        mDownload = (Download) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.DOWNLOAD);
        mPutPreferense = (PutPreferense) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.PUT_PREFERENSE);
        mDeletePreferese = (DeletePreferese) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.DELETE_PREFERENSE);
        mGetPreferense = (GetPreferense) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.GET_PREFERENSE);
        checkLvRecordsFromDB.setCellFactory(param -> new CellViewFull());
        checkLvRecordsFromFile.setCellFactory(param -> new CellViewFull());
        mGetPreferense.execute(REMEMBER_FILE_TAG)
                .subscribeOn(Schedulers.newThread())
                .subscribe(this::loadRememberPath, Throwable::printStackTrace);
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
            if (mRecordsFromDB.size()>0)
                mDownload.execute(mRecordsFromDB, showFilechooser(CheckScreen.CHOOSER_SAVE))
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(aVoid -> {

                        }, throwable -> {
                            showMessage(DOWNLOAD_ERROR);
                        });
        });

        checkChbRememberPath.setOnMouseClicked(mouseEvent -> {
            if (!checkTfFilePath.getText().equals(PATH_TO_FILE_TITLE) && checkChbRememberPath.isSelected())
                mPutPreferense.execute(REMEMBER_FILE_TAG, checkTfFilePath.getText())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
            else
                mDeletePreferese.execute(REMEMBER_FILE_TAG)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
        });
    }

    private void checkFile(String path) {
        mCheckFile.execute(path, () -> {
            ++mCheckedRecordsCount;
            showMessage(getLoadMessage());})
                .subscribeOn(Schedulers.newThread())
                .subscribe(checkCollections -> {
                    showRecords(checkCollections);
                    showMessage(RESULT_MESSAGE+checkCollections.getRecordsFromFile().size());
                }, throwable -> {
                    if (!(throwable instanceof IllegalStateException))
                        showMessage(ERROR_MESSAGE);
                    throwable.printStackTrace();
                });
    }

    private void showRecords(final CheckCollections checkCollections) {
        Platform.runLater(() -> {
            mRecordsFromDB.clear();
            mRecordsFromDB.addAll(checkCollections.getRecordsFromDB());
            checkLvRecordsFromDB.setItems(mRecordsFromDB);

            mRecordsFromFile.clear();
            mRecordsFromFile.addAll(checkCollections.getRecordsFromFile());
            checkLvRecordsFromFile.setItems(mRecordsFromFile);
        });
    }


    private void showMessage(final String message) {
        Platform.runLater(() -> checkLblMsg.setText(message));
    }

    private File showFilechooser(int chooserType) {
        return  CheckScreen.showFileChooser(chooserType);
    }

    private void loadRememberPath(final String path) {
        Platform.runLater(() -> {
            checkChbRememberPath.setSelected(true);
            checkTfFilePath.setText(path);
        });
    }

    private String getLoadMessage() {
        return COUNT_TITLE+" "+mCheckedRecordsCount;
    }
}
