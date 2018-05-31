package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class HydroTable extends VBox implements ViewMixin {

    private final RootPM root;
    private final HydroHeader header;

    private TableView<PowerplantsPM> tabelle;   //Tabellenansicht mit Kraftwerken auf jeder Zeile

    private int searchStart = 0;

    public HydroTable(RootPM root, HydroHeader header) {
        this.root = root;
        this.header = header;
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
        Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>(){

            @Override
            public TableCell call(TableColumn p){
                return new EditingCell();
            }
        };

    }

    private TableView<PowerplantsPM> initializePowerplantTabelle() {
        TableView<PowerplantsPM> tableView = new TableView<>(root.getAllPowerplants());

        TableColumn<PowerplantsPM, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().powerplantNameProperty());//Werte für die col liefern (gemiendeNamen in col 1)
        /*nameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<PowerplantsPM, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<PowerplantsPM, String> event) {
                ((PowerplantsPM) event.getTableView().getItems().get(
                        event.getTablePosition().getRow()
                ).setName(event.getNewValue());
            }
        });*/

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

        //scroll to newly created row and select it
        tabelle.scrollTo(powerplant);
    }

    public void deleteSelectedRows() {
        tabelle.getItems().removeAll(tabelle.getSelectionModel().getSelectedItems());
        //clears selection, because table selects by index
        tabelle.getSelectionModel().clearSelection();
    }

    /*
    public void search(){
        String searchText = header.getSearchString().toLowerCase();
        for(int i = searchStart; i < tabelle.getItems().size(); i++){
            if(tabelle.getItems().get(i).getPowerplantName().toLowerCase().contains(searchText)){
                PowerplantsPM searchPowerplant = root.getAllPowerplants().get(i);
                tabelle.scrollTo(searchPowerplant);
                searchStart = i+1;
                break;
            }if(!tabelle.getItems().get(i).getPowerplantName().toLowerCase().contains(searchText)){
                searchStart = 0;
            }
        }
    }
    */

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
        //add scroll down
        root.getAllPowerplants().addListener((ListChangeListener<PowerplantsPM>) c -> {
            while (c.next()) {
                tabelle.scrollTo(c.getFrom());
            }
        });

    }


}
