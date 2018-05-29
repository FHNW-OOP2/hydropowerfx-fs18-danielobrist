package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.CantonPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CantonTable extends VBox implements ViewMixin {

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
    }

    @Override
    public void layoutControls() {
        setVgrow(table, Priority.ALWAYS);
        getChildren().addAll(table);
    }

    private TableView<CantonPM> initializeTable() {
        TableView<CantonPM> tableView = new TableView<>(root.getAllCantons());

        TableColumn<CantonPM, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().cantonNameProperty());//Werte für die col liefern (gemiendeNamen in col 1)

        TableColumn<CantonPM, String> shortCol = new TableColumn<>("Abkürzung"); //Kollonen definieren
        shortCol.setCellValueFactory(cell -> cell.getValue().cantonShortProperty());//Werte für die col liefern (gemiendeNamen in col 1)

        TableColumn<CantonPM, Number> sumOfpowerplantsCol = new TableColumn<>("Anzahl Kraftwerke");
        sumOfpowerplantsCol.setCellValueFactory(cell -> cell.getValue().hydropowersPerCantonProperty());  //hier den Kanton der Zeile übergeben

        TableColumn<CantonPM, Number> sumOfpowerCol = new TableColumn<>("Gesamtleistung");
        sumOfpowerCol.setCellValueFactory(cell -> cell.getValue().powerPerCantonProperty());  //hier den Kanton der Zeile übergeben

        tableView.getColumns().addAll(nameCol, shortCol, sumOfpowerplantsCol, sumOfpowerCol);

        return tableView;
    }
    @Override
    public void setupBindings() {

    }
}