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
import javafx.util.converter.NumberStringConverter;



public class HydroForm extends GridPane implements ViewMixin {

    private final RootPM root;

    //  ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL


    private Label idIndex;
    private Label nameIndex;
    private Label maxPowerIndex;
    private Label siteIndex;
    private Label operationstartFirstIndex;


    private Label idLabel;
    private Label     nameLabel;
    private TextField nameField;
    private Label maxPowerLabel;
    private TextField maxPowerField;
    private Label standortLabel;
    private TextField standortField;
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

    public HydroForm(RootPM root){
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
        maxPowerIndex = new Label();
        siteIndex = new Label();
        operationstartFirstIndex = new Label();

        idLabel = new Label("Id");

        nameLabel = new Label("Name");
        nameField = new TextField();

        standortLabel = new Label("Standort");
        standortField = new TextField();

        cantonLabel = new Label("Kanton");
        cantonField = new TextField();

        siteLabel = new Label("Site");
        siteField = new TextField();

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
    }

    @Override
    public void layoutControls() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc, cc, cc, cc, cc, cc, cc);

        RowConstraints gc = new RowConstraints();
        gc.setVgrow(Priority.ALWAYS);
        getRowConstraints().addAll(gc, gc, gc, gc, gc, gc, gc, gc);

        add(nameIndex, 0, 0);
        add(siteIndex, 0, 1);

        add(nameLabel, 0,5);
        add(nameField, 0, 6);
    }
    @Override
    public void setupBindings() {
        PowerplantsPM proxy = root.getHydroProxy();

        idIndex.textProperty()  .bind             (proxy.powerplantIDProperty().asString());
        nameIndex.textProperty().bind(proxy.powerplantNameProperty());
        maxPowerIndex.textProperty().bindBidirectional(proxy.powerplantMaxPowerProperty(), new NumberStringConverter(new Locale("de", "CH")));


        nameField.textProperty().bindBidirectional(proxy.powerplantNameProperty());
        maxPowerField.textProperty().bindBidirectional(proxy.powerplantMaxPowerProperty(), new NumberStringConverter(new Locale("de", "CH")));
    }


}
