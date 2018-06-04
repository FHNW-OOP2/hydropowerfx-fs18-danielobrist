package ch.fhnw.oop2.hydropowerfx.view;

import java.util.Locale;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.converter.NumberStringConverter;


public class HydroForm extends GridPane implements ViewMixin {

    private final RootPM root;

    private Label idIndex;
    private Label nameIndex;
    private Label maxPowerIndex;
    private Label siteIndex;
    private Label operationstartFirstIndex;
    private Label cantonIndex;
    private Label siteCantonIndex;


    private Label nameLabel;
    private TextField nameField;
    private Label typeLabel;
    private TextField typeField;
    private Label maxPowerLabel;
    private TextField maxPowerField;
    private Label cantonLabel;
    private TextField cantonField;
    private Label siteLabel;
    private TextField siteField;
    private Label maxWaterLabel;
    private TextField maxWaterField;
    private Label startFirstLabel;
    private TextField startFirstField;
    private Label startLastLabel;
    private TextField startLastField;
    private Label longitudeLabel;
    private TextField longitudeField;
    private Label latitudeLabel;
    private TextField latitudeField;
    private Label statusLabel;
    private TextField statusField;
    private Label waterbodiesLabel;
    private TextField waterbodiesField;
    private Label imageLabel;
    private TextField imageField;

    private WaterTankControl watertankcontrol;

    public HydroForm(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("form");
    }

    @Override
    public void initializeControls() {
        idIndex = new Label();
        nameIndex = new Label();
        nameIndex.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        maxPowerIndex = new Label();
        siteIndex = new Label();
        operationstartFirstIndex = new Label();
        cantonIndex = new Label();
        siteCantonIndex = new Label();

        nameLabel = new Label("Name");
        nameField = new TextField(" ");

        typeLabel = new Label("Type");
        typeField = new TextField();

        siteLabel = new Label("Standort");
        siteField = new TextField();

        cantonLabel = new Label("Kanton");
        cantonField = new TextField();

        maxPowerLabel = new Label("Max Power"); //unicode character für hochgestellte Zahlen
        maxPowerField = new TextField();

        maxWaterLabel = new Label("Max Water");
        maxWaterField = new TextField();

        startFirstLabel = new Label("Inbetriebnahme");
        startFirstField = new TextField();

        startLastLabel = new Label("Letzte Inbetriebnahme");
        startLastField = new TextField();

        longitudeLabel = new Label("Longitude");
        longitudeField = new TextField();

        latitudeLabel = new Label("Latitude");
        latitudeField = new TextField();

        statusLabel = new Label("Status");
        statusField = new TextField();

        waterbodiesLabel = new Label("Waterbodies");
        waterbodiesField = new TextField();

        imageLabel = new Label("Bild-URL");
        imageField = new TextField();

        watertankcontrol = new WaterTankControl(root);
    }

    @Override
    public void layoutControls() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc);

        RowConstraints gc = new RowConstraints();
        gc.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll(gc, gc, gc, gc, gc, gc, gc, gc);

        add(nameIndex, 0, 0, 2, 1);
        add(siteCantonIndex, 0, 1);
        add(maxPowerIndex, 0, 2);
        add(operationstartFirstIndex, 0, 3);

        add(nameLabel, 0, 5);
        add(nameField, 0, 6);
        add(cantonLabel, 1, 5);
        add(cantonField, 1, 6);
        add(typeLabel, 0, 7);
        add(typeField, 0, 8);
        add(siteLabel, 1, 7);
        add(siteField, 1, 8);
        add(maxWaterLabel, 0, 9);
        add(maxWaterField, 0, 10);
        add(maxPowerLabel, 1, 9);
        add(maxPowerField, 1, 10);
        add(startFirstLabel, 0, 11);
        add(startFirstField, 0, 12);
        add(startLastLabel, 1, 11);
        add(startLastField, 1, 12);
        add(latitudeLabel, 0, 13);
        add(latitudeField, 0, 14);
        add(longitudeLabel, 1, 13);
        add(longitudeField, 1, 14);
        add(statusLabel, 0, 15);
        add(statusField, 0, 16);
        add(waterbodiesLabel, 1, 15);
        add(waterbodiesField, 1, 16);
        add(imageLabel, 0, 17);
        add(imageField, 0, 18, 2, 1);

        add(watertankcontrol, 1, 3);
    }

    @Override
    public void setupEventHandlers() {
    }

    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();

        idIndex.textProperty().bind(proxy.powerplantIDProperty().asString());
        nameIndex.textProperty().bind(proxy.powerplantNameProperty());
        maxPowerIndex.textProperty().bind(proxy.powerplantMaxPowerProperty().asString("%.2f MW"));
        siteIndex.textProperty().bind(proxy.powerplantSiteProperty());
        cantonIndex.textProperty().bind(proxy.powerplantCantonProperty());
        operationstartFirstIndex.textProperty().bind(proxy.powerplantStartFirstProperty().asString());
        siteCantonIndex.textProperty().bind(proxy.powerplantSiteProperty().concat(", ").concat(proxy.powerplantCantonProperty()));

        nameField.textProperty().bindBidirectional(proxy.powerplantNameProperty());
        cantonField.textProperty().bindBidirectional(proxy.powerplantCantonProperty());
        typeField.textProperty().bindBidirectional(proxy.powerplantTypeProperty());
        siteField.textProperty().bindBidirectional(proxy.powerplantSiteProperty());
        maxWaterField.textProperty().bindBidirectional(proxy.powerplantMaxVolumeProperty(), new NumberStringConverter(new Locale("de", "CH")));
        maxPowerField.textProperty().bindBidirectional(proxy.powerplantMaxPowerProperty(), new NumberStringConverter(new Locale("de", "CH")));
        startFirstField.textProperty().bindBidirectional(proxy.powerplantStartFirstProperty(), new NumberStringConverter(new Locale("de", "CH")));
        startLastField.textProperty().bindBidirectional(proxy.powerplantStartLastProperty(), new NumberStringConverter(new Locale("de", "CH")));
        longitudeField.textProperty().bindBidirectional(proxy.powerplantLongitudeProperty(), new NumberStringConverter(new Locale("de", "CH")));
        latitudeField.textProperty().bindBidirectional(proxy.powerplantLatitudeProperty(), new NumberStringConverter(new Locale("de", "CH")));
        statusField.textProperty().bindBidirectional(proxy.powerplantStatusProperty());
        waterbodiesField.textProperty().bindBidirectional(proxy.powerplantWaterbodiesProperty());
        imageField.textProperty().bindBidirectional(proxy.powerplantImageURLProperty());

    }

    @Override
    public void setupValueChangedListeners() {
        // force textfield to be numeric only
        startFirstField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                startFirstField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        startLastField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                startLastField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        maxPowerField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                root.updateCantonTable();
            }
        });


    }


}
