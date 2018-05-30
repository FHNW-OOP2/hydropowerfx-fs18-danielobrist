package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class HydroTable extends VBox implements ViewMixin {

    private final RootPM root;

    private TableView<PowerplantsPM> tabelle;   //Tabellenansicht mit Kraftwerken auf jeder Zeile

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
        tabelle.setEditable(true);
    }

    private TableView<PowerplantsPM> initializePowerplantTabelle() {
        TableView<PowerplantsPM> tableView = new TableView<>(root.getAllPowerplants());

        TableColumn<PowerplantsPM, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().powerplantNameProperty());//Werte für die col liefern (gemiendeNamen in col 1)

        TableColumn<PowerplantsPM, Number> idCol = new TableColumn<>("ID"); //ACHTUNG: Integer geht nicht, man muss Number nehmen als Typparameter!
        idCol.setCellValueFactory(cell -> cell.getValue().powerplantIDProperty());

        TableColumn<PowerplantsPM, Number> maxpowerCol = new TableColumn<>("Max Power");
        maxpowerCol.setCellValueFactory(cell -> cell.getValue().powerplantMaxPowerProperty());

        tableView.getColumns().addAll(nameCol, idCol, maxpowerCol);

        return tableView;
    }

    public void addRow() {
        //get position
        TablePosition pos = tabelle.getFocusModel().getFocusedCell();

        //clear current selection
        tabelle.getSelectionModel().clearSelection();

        //create new empty powerplant and add it to model
        PowerplantsPM powerplant = new PowerplantsPM();
        tabelle.getItems().add(powerplant);

        //get last row
        int row = tabelle.getItems().size() - 1;
        tabelle.getSelectionModel().select(row, pos.getTableColumn());

        //scroll to new row
        tabelle.scrollTo(powerplant);
    }

    public void deleteSelectedRows() {
        tabelle.getItems().removeAll(tabelle.getSelectionModel().getSelectedItems());
        //clears selection, because table selects by index
        tabelle.getSelectionModel().clearSelection();
    }


    @Override
    public void layoutControls() {
        setVgrow(tabelle, Priority.ALWAYS);

        getChildren().addAll(tabelle);

    }

    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();


    }

    @Override
    public void setupEventHandlers() {

    }



    @Override
    public void setupValueChangedListeners() {
        //set selected PowerplantID
        tabelle.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                root.setSelectedPowerplantId(newValue.getPowerplantID());
            }
        });
        //add
        root.getAllPowerplants().addListener((ListChangeListener<PowerplantsPM>) c -> {
            while (c.next()) {
                tabelle.scrollTo(c.getFrom());
            }
        });



        // tabelle.setOnMouseClicked(event -> {
        //    TableView source = (TableView) event.getSource();

        //    PowerplantsPM wasserwerk = (PowerplantsPM) source.getSelectionModel().getSelectedItem();
        //    root.setSelectedPowerplantId(wasserwerk.getPowerplantID());
        //  System.out.print(root.getHydroProxy().getPowerplantID());
        // });
    }


}
