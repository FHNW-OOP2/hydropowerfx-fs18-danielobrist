package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class HydroTable extends VBox implements ViewMixin {

    private final RootPM root;

    private TableView<PowerplantsPM> tabelle;   //Tabellenansicht mit Kraftwerken auf jeder Zeile
    private Label HYDROLABEL;

    public HydroTable(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("table");
    }

    @Override
    public void initializeControls() {
        tabelle = initializePowerplantTabelle();
        HYDROLABEL = new Label();
    }

    private TableView<PowerplantsPM> initializePowerplantTabelle() {
        TableView<PowerplantsPM> tableView = new TableView<>(root.allPowerplants());

        TableColumn<PowerplantsPM, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().powerplantNameProperty());//Werte für die col liefern (gemiendeNamen in col 1)

        TableColumn<PowerplantsPM, Number> idCol = new TableColumn<>("ID"); //ACHTUNG: Integer geht nicht, man muss Number nehmen als Typparameter!
        idCol.setCellValueFactory(cell ->  cell.getValue().powerplantIDProperty());

        TableColumn<PowerplantsPM, Number> maxpowerCol = new TableColumn<>("Max Power");
        maxpowerCol.setCellValueFactory(cell -> cell.getValue().powerplantMaxPowerProperty());

        tableView.getColumns().addAll(nameCol, idCol, maxpowerCol);

        return tableView;
    }


    @Override
    public void layoutControls() {
        setVgrow(tabelle, Priority.ALWAYS);

        getChildren().addAll(tabelle, HYDROLABEL);

    }

    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();


    }

    @Override
    public void setupValueChangedListeners() {

    }


}
