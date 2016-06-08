package sample.presentation.controllers;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import rx.schedulers.Schedulers;
import sample.domain.interactors.Delete;
import sample.domain.interactors.Download;
import sample.domain.interactors.GetDataWithParams;
import sample.domain.models.Record;
import sample.domain.utils.InteractorsFactory;
import sample.presentation.Main;
import sample.presentation.models.SearchParams;
import sample.presentation.utils.SearchParamsBuilder;
import sample.presentation.views.CellViewShort;
import sample.presentation.views.SearchScreen;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class SearchController {

    public final String POSITION_TITLE = "Серия";
    public final String TITLE_TITLE = "Наименование";
    public final String PROVIDER_TITLE = "Поставщик";
    public final String DATE_FROM_TITLE = "Загрузка от (пр: 1.01.1999)";
    public final String DATE_TO_TITLE = "Загрузка по (пр: 1.01.1999)";
    public final String EMPTY_LINE = "";

    private final String LOAD_ERROR = "Не удалось загрузить данные";
    private final String DOWNLOAD_ERROR = "Не удалось сохранить в файл";
    private final String DELETE_SUCCESS = "Записи удалены";
    private final String DELETE_ERROR = "Не удалось удалить данные";
    private final String CONFIRM_DELETE = "ПОДТВЕРДИТЬ УДАЛЕНИЕ?";
    private final String DELETE_TITLE = "Удаление записи";
    private final String COUNT_TITLE = "Удалено ";

    @FXML
    private TextField searchTfId;
    @FXML
    private TextField searchTfTitle;
    @FXML
    private TextField searchTfProvider;
    @FXML
    private TextField searchTfDateFrom;
    @FXML
    private TextField searchTfDateTo;
    @FXML
    private Button searchBtnDownloadAll;
    @FXML
    private Button searchBtnDownloadSelection;
    @FXML
    private Button searchBtnSearch;
    @FXML
    private Button searchBtnDeleteAll;
    @FXML
    private Button searchBtnDeleteSelection;
    @FXML
    private ListView<Record> searchLvRecords;
    @FXML
    private Label searchLblMsg;

    private ObservableList<Record> mRecords = FXCollections.observableArrayList();
    private GetDataWithParams mGetDataWithParams;
    private Download mDownload;
    private Delete mDelete;
    private int mDeleteRecordsCount = 0;

    @FXML
    private void initialize() {
        mGetDataWithParams = (GetDataWithParams) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.GET_DATA_WITH_PARAMS);
        mDownload = (Download) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.DOWNLOAD);
        mDelete = (Delete) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.DELETE);
        searchLvRecords.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        searchLvRecords.setCellFactory(param -> new CellViewShort());
        initClickListeners();
    }

    private void initClickListeners() {
        searchTfId.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (searchTfId.getText().isEmpty())
                    searchTfId.setText(POSITION_TITLE);
            }
        });

        searchTfId.setOnMouseClicked(mouseEvent -> {
            if (searchTfId.getText().equals(POSITION_TITLE))
                searchTfId.setText(EMPTY_LINE);
        });

        searchTfTitle.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (searchTfTitle.getText().isEmpty())
                    searchTfTitle.setText(TITLE_TITLE);
            }
        });

        searchTfTitle.setOnMouseClicked(mouseEvent -> {
            if (searchTfTitle.getText().equals(TITLE_TITLE))
                searchTfTitle.setText(EMPTY_LINE);
        });

        searchTfProvider.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (searchTfProvider.getText().isEmpty())
                    searchTfProvider.setText(PROVIDER_TITLE);
            }
        });

        searchTfProvider.setOnMouseClicked(mouseEvent -> {
            if (searchTfProvider.getText().equals(PROVIDER_TITLE))
                searchTfProvider.setText(EMPTY_LINE);
        });

        searchTfDateFrom.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (searchTfDateFrom.getText().isEmpty())
                    searchTfDateFrom.setText(DATE_FROM_TITLE);
            }
        });

        searchTfDateFrom.setOnMouseClicked(mouseEvent -> {
            if (searchTfDateFrom.getText().equals(DATE_FROM_TITLE))
                searchTfDateFrom.setText(EMPTY_LINE);
        });

        searchTfDateTo.focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (!newPropertyValue) {
                if (searchTfDateTo.getText().isEmpty())
                    searchTfDateTo.setText(DATE_TO_TITLE);
            }
        });

        searchTfDateTo.setOnMouseClicked(mouseEvent -> {
            if (searchTfDateTo.getText().equals(DATE_TO_TITLE))
                searchTfDateTo.setText(EMPTY_LINE);
        });

        searchBtnSearch.setOnMouseClicked(mouseEvent -> {
            SearchParams params = SearchParamsBuilder.Build(SearchController.this);
            searchLblMsg.setText(EMPTY_LINE);
            mGetDataWithParams.execute(params)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(this::showRecords, throwable -> {
                        showMessage(LOAD_ERROR);
                    });
        });

        searchBtnDownloadAll.setOnMouseClicked(mouseEvent -> {
            if (mRecords.size()>0)
                download(mRecords);
        });

        searchBtnDownloadSelection.setOnMouseClicked(mouseEvent -> {
            List<Record> selectionItems = getSelectionItems();
            if (selectionItems.size()>0)
                download(selectionItems);
        });

        searchBtnDeleteAll.setOnMouseClicked(mouseEvent -> {
            if (mRecords.size()>0)
                delete(mRecords);
        });

        searchBtnDeleteSelection.setOnMouseClicked(mouseEvent -> {
            List<Record> selectionItems = getSelectionItems();
            if (selectionItems.size()>0)
                delete(selectionItems);
        });
    }

    private void showRecords(final List<Record> records) {
        Platform.runLater(() -> {
            mRecords.clear();
            mRecords.addAll(records);
            searchLvRecords.setItems(mRecords);
        });
    }

    private void showMessage(final String message) {
        Platform.runLater(() -> searchLblMsg.setText(message));
    }

    private File showFilechooser() {
        return  SearchScreen.showFileChooser();
    }

    private List<Record> getSelectionItems() {
        return searchLvRecords.getSelectionModel().getSelectedItems();
    }

    private void download(List<Record> records) {
        mDownload.execute(records, showFilechooser())
                .subscribeOn(Schedulers.newThread())
                .subscribe(aVoid -> {

                }, throwable -> {
                    showMessage(DOWNLOAD_ERROR);
                });
    }

    private void delete(List<Record> records) {
        if (showDeleteDialog())
            mDelete.execute(records, () -> {
                ++mDeleteRecordsCount;
                showMessage(getLoadMessage(records.size()));
            })
                    .subscribeOn(Schedulers.newThread())
                    .doOnSubscribe(() -> {
                        searchBtnDeleteSelection.setDisable(true);
                        searchBtnDeleteAll.setDisable(true);
                        mDeleteRecordsCount=0;})
                    .doOnCompleted(() -> {
                        searchBtnDeleteSelection.setDisable(false);
                        searchBtnDeleteAll.setDisable(false);
                        MainController.updateRecords();
                    })
                    .doOnCompleted(() -> {
                        removeFromListView(records);
                        showMessage(DELETE_SUCCESS);
                    })
                    .subscribe(aVoid -> {

                    }, throwable -> {
                        searchBtnDeleteSelection.setDisable(false);
                        searchBtnDeleteAll.setDisable(false);
                        showMessage(DELETE_ERROR);
                    });
    }

    private boolean showDeleteDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(DELETE_TITLE);
        alert.setHeaderText(CONFIRM_DELETE);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void removeFromListView(List<Record> records) {
        Platform.runLater(() -> {
            if (records.size() == mRecords.size())
                mRecords.clear();
            else
                mRecords.removeAll(records);
        });
    }

    private String getLoadMessage(int size) {
        return COUNT_TITLE+" "+mDeleteRecordsCount+" из "+size;
    }

    public String getIdFieldValue() {
        return searchTfId.getText();
    }

    public String getTitleFieldValue() {
        return searchTfTitle.getText();
    }

    public String getProviderFieldValue() {
        return searchTfProvider.getText();
    }

    public String getDateFromFieldValue() {
        return searchTfDateFrom.getText();
    }

    public String getDateToFieldValue() {
        return searchTfDateTo.getText();
    }

}
