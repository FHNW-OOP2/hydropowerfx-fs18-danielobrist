package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class HydroHeader extends HBox implements ViewMixin {

    private final RootPM root;
    private final HydroTable table;

    private Button addButton;
    private Button deleteButton;
    private Button saveButton;
    private TextField searchField;
    private StringProperty searchString = new SimpleStringProperty();


    public HydroHeader(RootPM root, HydroTable table) {
        this.root = root;
        this.table = table;
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
        searchField = new TextField();
        searchField.setPromptText("Nach Name suchen");
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(addButton, deleteButton, saveButton, searchField);

    }

    @Override
    public void setupEventHandlers() {
        saveButton.setOnAction(event -> root.save());

        addButton.setOnAction(event -> table.addRow());
        addButton.setFocusTraversable(false);

        deleteButton.setOnAction(event -> table.deleteSelectedRows());
        deleteButton.setFocusTraversable(false);

        // searchField.setOnKeyReleased(event -> table.search());
    }

    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();


    }

    public String getSearchString() {
        return searchField.getText();
    }

    public StringProperty searchStringProperty() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString.set(searchString);
    }
}
