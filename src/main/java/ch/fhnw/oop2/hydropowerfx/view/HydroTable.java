package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

public class HydroTable extends HBox implements ViewMixin {

    private final RootPM root;

    private Slider slider;
    private TextField inputField;
    private ComboBox<PowerplantsPM> comboBox;

    public HydroTable(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("selectorbar");
    }

    @Override
    public void initializeControls() {
        slider = new Slider();
        slider.setMin(0.0);
        slider.setMax(root.allPowerplants().size() - 1);

        inputField = new TextField();
        comboBox = new ComboBox<>(root.allPowerplants());
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(slider, inputField, comboBox);
    }

    @Override
    public void setupBindings() {
        slider.valueProperty()   .bindBidirectional(root.selectedPowerplantIdProperty());
        inputField.textProperty().bindBidirectional(root.selectedPowerplantIdProperty(), new NumberStringConverter());
    }

    @Override
    public void setupValueChangedListeners() {
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> root.setSelectedPowerplantId(newValue.getId()));

        root.selectedPowerplantIdProperty().addListener((observable, oldValue, newValue) -> comboBox.getSelectionModel().select((int) newValue));
    }

}
