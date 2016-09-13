package sample.presentation.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rx.schedulers.Schedulers;
import sample.domain.interactors.DeletePreferese;
import sample.domain.interactors.GetPreferense;
import sample.domain.interactors.LoadFile;
import sample.domain.interactors.PutPreferense;
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

    private final String REMEMBER_FILE_TAG = "rmbfltg";

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
    private CheckBox loadChbRememberPath;
    @FXML
    private ListView<Record> loadLvRecords;

    private LoadFile mLoadFile;
    private GetPreferense mGetPreferense;
    private PutPreferense mPutPreferense;
    private DeletePreferese mDeletePreferese;
    private int mLoadRecordsCount = 0;

    @FXML
    private void initialize() {
        mLoadFile = (LoadFile) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.LOAD_FILE);
        mGetPreferense = (GetPreferense) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.GET_PREFERENSE);
        mPutPreferense = (PutPreferense) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.PUT_PREFERENSE);
        mDeletePreferese = (DeletePreferese) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.DELETE_PREFERENSE);
        loadLvRecords.setCellFactory(param -> new CellViewFull());
        mGetPreferense.execute(REMEMBER_FILE_TAG)
                .subscribeOn(Schedulers.newThread())
                .subscribe(this::loadRememberPath, Throwable::printStackTrace);
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

        loadChbRememberPath.setOnMouseClicked(mouseEvent -> {
            if (!loadTfFilePath.getText().equals(PATH_TO_FILE_TITLE) && loadChbRememberPath.isSelected())
                mPutPreferense.execute(REMEMBER_FILE_TAG, loadTfFilePath.getText())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe();
            else
                mDeletePreferese.execute(REMEMBER_FILE_TAG)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe();
        });
    }

    private void loadFile(String path) {
        mLoadFile.execute(path, () -> {
            ++mLoadRecordsCount;
            showMessage(getLoadMessage());})
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

    private void loadRememberPath(final String path) {
        Platform.runLater(() -> {
            loadChbRememberPath.setSelected(true);
            loadTfFilePath.setText(path);
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
