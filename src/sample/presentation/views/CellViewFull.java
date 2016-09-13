package sample.presentation.views;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sample.domain.models.Record;

import java.io.IOException;

public class CellViewFull extends ListCell<Record> {

    @FXML
    private Pane v_cell_fullPnMain;
    @FXML
    private TextField v_cell_fullTfId;
    @FXML
    private TextField v_cell_fullTfSeries;
    @FXML
    private TextField v_cell_fullTfTitle;
    @FXML
    private TextField v_cell_fullTfProvider;
    @FXML
    private TextField v_cell_fullTfCertificate;
    @FXML
    private TextField v_cell_fullTfDate;
    @FXML
    private TextField v_cell_fullTfDescription;

    public CellViewFull() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res/v_cell_full.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Record item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            setGraphic(v_cell_fullPnMain);
            loadData(item);
        } else
            setGraphic(null);
    }

    private void loadData(Record item) {
        v_cell_fullTfId.setText(item.getId()+"");
        v_cell_fullTfSeries.setText(item.getSeries());
        v_cell_fullTfTitle.setText(item.getTitle());
        v_cell_fullTfProvider.setText(item.getProvider());
        v_cell_fullTfCertificate.setText(item.getCertificate());
        v_cell_fullTfDate.setText(item.getDate());
        v_cell_fullTfDescription.setText(item.getDescription());
    }
}
