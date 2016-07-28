package sample.presentation.views;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sample.domain.models.Record;

import java.io.IOException;

public class CellViewShort extends ListCell<Record> {

    private static final String DATE_TITLE = "Дата загрузки";

    @FXML
    private Pane v_cell_shortPnMain;
    @FXML
    private TextField v_cell_shortTfId;
    @FXML
    private TextField v_cell_shortTfTitle;
    @FXML
    private TextField v_cell_shortTfProvider;
    @FXML
    private TextField v_cell_shortTfLoadDate;

    public CellViewShort() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res/v_cell_short.fxml"));
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
            setGraphic(v_cell_shortPnMain);
            loadData(item);
        } else
            setGraphic(null);
    }

    private void loadData(Record item) {
        v_cell_shortTfId.setText(item.getSeries());
        v_cell_shortTfTitle.setText(item.getTitle());
        v_cell_shortTfProvider.setText(item.getProvider());
        v_cell_shortTfLoadDate.setText(DATE_TITLE+" "+item.getLoadDate());
    }
}
