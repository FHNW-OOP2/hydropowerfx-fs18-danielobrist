package ch.fhnw.oop2.hydropowerfx.view;

import java.util.Locale;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.converter.NumberStringConverter;



public class HydroForm extends GridPane implements ViewMixin {

    private final RootPM root;

    private Label idLabel;
    private Label     idField;
    private Label     nameLabel;
    private TextField nameField;
    private Label     areaLabel;
    private TextField areaField;

    public HydroForm(RootPM root){
        this.root = root;
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
        root.selectedPowerplantIdProperty().addListener((observable, oldValue, newValue) -> {
            PowerplantsPM oldSelection = root.getPowerplant(oldValue.intValue());
            PowerplantsPM newSelection = root.getPowerplant(newValue.intValue());

            if (oldSelection != null) {
                idField.textProperty()  .unbind();
                nameField.textProperty().unbindBidirectional(oldSelection.nameProperty());
                areaField.textProperty().unbindBidirectional(oldSelection.powerplantMaxPowerProperty());
            }

            if (newSelection != null) {
                idField.textProperty()  .bind             (newSelection.powerplantIDProperty().asString());
                nameField.textProperty().bindBidirectional(newSelection.nameProperty());
                areaField.textProperty().bindBidirectional(newSelection.powerplantMaxPowerProperty(), new NumberStringConverter(new Locale("de", "CH")));
            }
        });
    }


}
