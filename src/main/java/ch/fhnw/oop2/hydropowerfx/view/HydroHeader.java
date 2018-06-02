package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;


public class HydroHeader extends HBox implements ViewMixin {

    private final RootPM root;

    private Button addButton;
    private Button deleteButton;
    private Button saveButton;
    private TextField searchField;
    private StringProperty searchString = new SimpleStringProperty();


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
        searchField = new TextField();
        searchField.setPromptText("Suche");

        Tooltip addTooltip = new Tooltip("Erstellt eine neue leere Zeile.");
        addButton.setTooltip(addTooltip);
        Tooltip deleteTooltip = new Tooltip("Löscht die ausgewählte Zeile.");
        deleteButton.setTooltip(deleteTooltip);
        Tooltip saveTooltip = new Tooltip("Speichert alle Änderungen ab.");
        saveButton.setTooltip(saveTooltip);
    }

    @Override
    public void layoutControls() {
        getChildren().addAll(addButton, deleteButton, saveButton, searchField);

    }

    @Override
    public void setupEventHandlers() {
        saveButton.setOnAction(event -> root.save());

        // addButton.setOnAction(event -> table.addRow());
        addButton.setOnAction(event -> root.add());
        addButton.setFocusTraversable(false);

        // deleteButton.setOnAction(event -> table.deleteSelectedRows());
        deleteButton.setOnAction(event -> root.delete());
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
