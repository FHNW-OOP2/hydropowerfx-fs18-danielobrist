package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Form extends GridPane implements ViewMixin {

    private final RootPM rootpm;

    private Label idLabel;
    private Label     idField;
    private Label     nameLabel;
    private TextField nameField;
    private Label     areaLabel;
    private TextField areaField;

    public Form(RootPM rootpm){
        this.rootpm = rootpm;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("form");
    }

    @Override
    public void initializeControls() {
        idLabel = new Label("Id");
        idField = new Label();

        nameLabel = new Label("Name");
        nameField = new TextField();

        areaLabel = new Label("Fläche in km\u00B2"); //unicode character verwenden
        areaField = new TextField();
    }

    @Override
    public void layoutControls() {
        ColumnConstraints grow = new ColumnConstraints();
        grow.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(new ColumnConstraints(), grow);

        addRow(0, idLabel  , idField);
        addRow(1, nameLabel, nameField);
        addRow(2, areaLabel, areaField);
    }

    @Override
    public void setupValueChangedListeners() {

    }

}
