package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Header extends HBox implements ViewMixin {

    private final RootPM hydroMangament;

    private Label nameLabel;
    private Label areaLabel;

    public Header(RootPM hydroManagement) {
        this.hydroMangament = hydroManagement;
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
    public void setupValueChangedListeners() {

    }
}
