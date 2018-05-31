package ch.fhnw.oop2.hydropowerfx.view;

import javafx.event.EventHandler;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditingCell extends TableCell<XYChart.Data, Number>{
    private TextField editField;

    public EditingCell() {}

    @Override
    public void startEdit() {
        super.startEdit();

        if (editField == null) {
            createEditField();
        }

        setGraphic(editField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        editField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void updateItem(Number item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (editField != null) {
                    editField.setText(getString());
                }
                setGraphic(editField);
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createEditField() {
        editField = new TextField(getString());
        editField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        editField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    commitEdit(Integer.parseInt(editField.getText()));
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
