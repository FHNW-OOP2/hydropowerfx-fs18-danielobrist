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
    private Label maxPowerLabel;
    private TextField maxPowerField;

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

        maxPowerLabel = new Label("Max Power"); //unicode character für hochgestellte Zahlen
        maxPowerField = new TextField();
    }

    @Override
    public void layoutControls() {
        ColumnConstraints grow = new ColumnConstraints();
        grow.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(new ColumnConstraints(), grow);

        addRow(0, idLabel  , idField);
        addRow(1, nameLabel, nameField);
        addRow(2, maxPowerLabel, maxPowerField);
    }
    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();

        idField.textProperty()  .bind             (proxy.powerplantIDProperty().asString());
        nameField.textProperty().bindBidirectional(proxy.powerplantNameProperty());
        maxPowerField.textProperty().bindBidirectional(proxy.powerplantMaxPowerProperty(), new NumberStringConverter(new Locale("de", "CH")));
    }


}
