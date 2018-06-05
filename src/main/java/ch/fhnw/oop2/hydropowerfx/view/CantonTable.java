package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.CantonPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CantonTable extends HBox implements ViewMixin {

    private final RootPM root;

    private TableView<CantonPM> table;

    public CantonTable(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("table");
    }

    @Override
    public void initializeControls() {
        table = initializeTable();
        table.setMaxHeight(190);
    }

    @Override
    public void layoutControls() {
        setPrefHeight(300);
        setHgrow(table, Priority.ALWAYS);
        getChildren().addAll(table);
    }

    private TableView<CantonPM> initializeTable() {
        TableView<CantonPM> tableView = new TableView<>(root.getAllCantons());

        TableColumn<CantonPM, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().cantonNameProperty());//Werte für die col liefern (gemiendeNamen in col 1)

        TableColumn<CantonPM, Number> sumOfpowerplantsCol = new TableColumn<>("Anzahl Kraftwerke");
        sumOfpowerplantsCol.setCellValueFactory(cell -> cell.getValue().hydropowersPerCantonProperty());

        TableColumn<CantonPM, Number> sumOfpowerCol = new TableColumn<>("Gesamtleistung (MW)");
        sumOfpowerCol.setCellValueFactory(cell -> cell.getValue().powerPerCantonProperty());

        nameCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        sumOfpowerCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        sumOfpowerplantsCol.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));

        tableView.getColumns().addAll(nameCol, sumOfpowerplantsCol, sumOfpowerCol);

        return tableView;


    }

    @Override
    public void setupBindings() {


    }

    @Override
    public void setupValueChangedListeners() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            root.setSelectedPowerplantId(newValue.getHydropowersPerCanton());
        });


    }


}