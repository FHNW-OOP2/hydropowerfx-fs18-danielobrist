package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

import javax.lang.model.type.DeclaredType;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.spi.NumberFormatProvider;
import java.time.format.DecimalStyle;
import java.util.Locale;

public class HydroTable extends VBox implements ViewMixin {

    private final RootPM root;

    private TableView<PowerplantsPM> tabelle;

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
        tabelle.getSelectionModel().selectFirst();
        tabelle.requestFocus();
        tabelle.getFocusModel().focus(1);
    }

    private TableView<PowerplantsPM> initializePowerplantTabelle() {
        TableView<PowerplantsPM> tableView = new TableView<>(root.getAllPowerplants());

        TableColumn<PowerplantsPM, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> cell.getValue().powerplantNameProperty());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn()); //makes cells editable (ENTER saves Value in Table)

        TableColumn<PowerplantsPM, Number> startCol = new TableColumn<>("Inbetriebnahme");
        startCol.setCellValueFactory(cell -> cell.getValue().powerplantStartFirstProperty());
        startCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter("####")));

        TableColumn<PowerplantsPM, Number> maxpowerCol = new TableColumn<>("Leistung (MW)");
        maxpowerCol.setCellValueFactory(cell -> cell.getValue().powerplantMaxPowerProperty());
        maxpowerCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter(new Locale("de", "CH"))));


        tableView.getColumns().addAll(nameCol, maxpowerCol, startCol);

        return tableView;
    }

    @Override
    public void layoutControls() {
        setVgrow(tabelle, Priority.ALWAYS);

        getChildren().addAll(tabelle);

    }

    @Override
    public void setupBindings() {

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
                //get position
                TablePosition pos = tabelle.getFocusModel().getFocusedCell();

                //clear current selection
                tabelle.getSelectionModel().clearSelection();

                //go to last row
                int row = tabelle.getItems().size() - 1;
                tabelle.getSelectionModel().select(row, pos.getTableColumn());
                tabelle.scrollTo(c.getFrom());
                root.setSelectedPowerplantId(root.newHighestID());
            }
        });

    }


}
