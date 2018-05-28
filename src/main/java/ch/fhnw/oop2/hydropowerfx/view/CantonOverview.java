package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.CantonPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CantonOverview extends VBox implements ViewMixin {

    private final RootPM root;

    private TableView<CantonPM> table;

    public CantonOverview(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("cantons");
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

        tableView.getColumns().addAll(nameCol);

        return tableView;
    }
    @Override
    public void setupBindings() {

    }
}