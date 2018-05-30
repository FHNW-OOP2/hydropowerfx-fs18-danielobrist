package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class HydroHeader extends HBox implements ViewMixin {

    private final RootPM root;

    private Button addButton;
    private Button deleteButton;
    private Button saveButton;

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
        addButton = new Button("Add");
        deleteButton = new Button("Delete");
        saveButton = new Button("Save");
    }

    @Override
    public void layoutControls() {

        getChildren().addAll(addButton, deleteButton, saveButton);
    }
    @Override
    public void setupEventHandlers() {
        // addButton.setOnAction(event -> root.addNewPowerplant());

        saveButton.setOnAction(event -> root.save());
    }

    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();


    }

}
