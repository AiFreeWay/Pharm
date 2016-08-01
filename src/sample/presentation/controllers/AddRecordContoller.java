package sample.presentation.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rx.schedulers.Schedulers;
import sample.domain.interactors.AddRecord;
import sample.domain.interfaces.Interactor1;
import sample.domain.utils.InteractorsFactory;
import sample.domain.models.Record;
import sample.presentation.Main;



public class AddRecordContoller {

    private final String EMPTY_LINE = "";
    private final String WARNING = "Серия обязательное поле";
    private final String SUCCES = "Запись добавлена";

    @FXML
    TextField addRecordTfTitle;
    @FXML
    TextField addRecordTfSeries;
    @FXML
    TextField addRecordTfProvider;
    @FXML
    TextField addRecordTfSertificate;
    @FXML
    TextField addRecordTfDate;
    @FXML
    TextField addRecordTfDescription;
    @FXML
    Label addRecordLblWarning;
    @FXML
    Button addRecordBtnAdd;

    private Interactor1<Void, Record> mAddRecord;

    @FXML
    private void initialize() {
        mAddRecord = (AddRecord) Main.getInteractorsFactory().getInteractor(InteractorsFactory.Interactors.ADD_RECORD);

        addRecordBtnAdd.setOnMouseClicked(mouseEvent -> {
            addRecordLblWarning.setText(EMPTY_LINE);
            if (checkFields())
                mAddRecord.execute(getRecord())
                        .subscribeOn(Schedulers.newThread())
                        .doOnCompleted(this::showMessageSucces)
                        .subscribe(aVoid -> {}, Throwable::printStackTrace);
        });
    }

    private void showMessageSucces() {
        Platform.runLater(() -> {
            addRecordLblWarning.setText(SUCCES);
        });
    }

    private Record getRecord() {
        return new Record(
                addRecordTfSeries.getText().hashCode(),
                addRecordTfSeries.getText(),
                addRecordTfTitle.getText(),
                addRecordTfProvider.getText(),
                addRecordTfSertificate.getText(),
                addRecordTfDate.getText(),
                addRecordTfDescription.getText());
    }

    private boolean checkFields() {
        if (addRecordTfSeries.getText().isEmpty()) {
            addRecordLblWarning.setText(WARNING);
            return false;
        }
        return true;
    }
}
