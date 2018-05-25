package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Table extends VBox implements ViewMixin {

    private final RootPM rootPM;

    private TableView<Powerplants> tabelle;   //Tabellenansicht mit Kraftwerken auf jeder Zeile
    private Label anzahlGemeinden;

    public ApplicationUI(WahlPM model) {
        this.model = model;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    public void initializeControls() {
        tabelle = initializeResultatTabelle();
        anzahlGemeinden = new Label();
    }

    private TableView<Resultat> initializeResultatTabelle() {
        TableView<Resultat> tableView = new TableView<>(model.getResulate());

        TableColumn<Resultat, String> nameCol = new TableColumn<>("Name"); //Kollonen definieren
        nameCol.setCellValueFactory(cell -> cell.getValue().gemeindeNamenProperty());//Werte für die col liefern (gemiendeNamen in col 1)

        TableColumn<Resultat, Number> wahlberechtigteCol = new TableColumn<>("Wahlberechtigte"); //ACHTUNG: Integer geht nicht, man muss Number nehmen als Typparameter!
        wahlberechtigteCol.setCellValueFactory(cell ->  cell.getValue().wahlberechtigteProperty());

        TableColumn<Resultat, Number> waehlendeCol = new TableColumn<>("Anzahl Wählende");
        waehlendeCol.setCellValueFactory(cell -> cell.getValue().waehlendeProperty());

        tableView.getColumns().addAll(nameCol, wahlberechtigteCol, waehlendeCol);

        return tableView;
    }

    private void layoutControls() {
        setVgrow(tabelle, Priority.ALWAYS);

        getChildren().addAll(tabelle, anzahlGemeinden);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
        anzahlGemeinden.textProperty().bind(Bindings.size(model.getResulate())
                .asString("Anzahl der Gemeinden %d in der Schweiz"));
    }


}
