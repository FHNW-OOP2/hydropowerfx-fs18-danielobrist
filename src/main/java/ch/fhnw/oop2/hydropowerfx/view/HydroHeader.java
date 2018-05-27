package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HydroHeader extends VBox implements ViewMixin {

    private final RootPM root;

    private Label nameLabel;
    private Label areaLabel;

    public HydroHeader(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("header");
    }

    @Override
    public void initializeControls() {
        nameLabel = new Label();
        areaLabel = new Label();
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(nameLabel, areaLabel);
    }

    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();

        nameLabel.textProperty().bind(proxy.nameProperty());
        areaLabel.textProperty().bind(proxy.powerplantMaxPowerProperty().asString("%.2f km\u00B2"));
    }
}
