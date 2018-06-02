package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import java.text.DecimalFormat;
import java.util.Locale;

public class HydroTable extends VBox implements ViewMixin {

    private final RootPM root;
    private final HydroHeader header;

    private TableView<PowerplantsPM> tabelle;   //Tabellenansicht mit Kraftwerken auf jeder Zeile

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
        tabelle.getSelectionModel().selectFirst();
        tabelle.requestFocus();
        tabelle.getFocusModel().focus(1);
    }

    private TableView<PowerplantsPM> initializePowerplantTabelle() {
        TableView<PowerplantsPM> tableView = new TableView<>(root.getAllPowerplants());

        TableColumn<PowerplantsPM, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().powerplantNameProperty());//Werte für die col liefern
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn()); //makes cells editable (ENTER saves Value in Table)

        TableColumn<PowerplantsPM, Number> startCol = new TableColumn<>("Inbetriebnahme"); //ACHTUNG: Number, Integer geht nicht, man muss Number nehmen als Typparameter!
        startCol.setCellValueFactory(cell -> cell.getValue().powerplantStartFirstProperty());
        startCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

        TableColumn<PowerplantsPM, Number> maxpowerCol = new TableColumn<>("Leistung (MW)");
        maxpowerCol.setCellValueFactory(cell -> cell.getValue().powerplantMaxPowerProperty());
        maxpowerCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter(new Locale("de", "CH"))));


        tableView.getColumns().addAll(nameCol, maxpowerCol, startCol);

        return tableView;
    }

    public void addRow() {
        //get position
        TablePosition pos = tabelle.getFocusModel().getFocusedCell();

        //clear current selection
        tabelle.getSelectionModel().clearSelection();

        //create new empty powerplant and add it to model
        PowerplantsPM powerplant = new PowerplantsPM(root.newHighestID());
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


    public void search() {
        String searchText = header.getSearchString().toLowerCase();
        tabelle.getItems().stream().filter(item -> item.getName().toLowerCase() == searchText).findAny()
                .ifPresent(item -> {
                    tabelle.getSelectionModel().select(item);
                });
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
        //add scroll down
        root.getAllPowerplants().addListener((ListChangeListener<PowerplantsPM>) c -> {
            while (c.next()) {
                tabelle.scrollTo(c.getFrom());
            }
        });

    }


}
