package ch.fhnw.oop2.hydropowerfx.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

public class Powerplants {
    private final IntegerProperty powerplantID = new SimpleIntegerProperty();
    private final StringProperty powerplantName = new SimpleStringProperty();
    private final StringProperty powerplantType = new SimpleStringProperty();
    private final StringProperty powerplantSite = new SimpleStringProperty();
    private final StringProperty powerplantCanton = new SimpleStringProperty();
    private final DoubleProperty powerplantMaxVolume = new SimpleDoubleProperty();
    private final DoubleProperty powerplantMaxPower = new SimpleDoubleProperty();
    private final IntegerProperty powerplantStartFirst = new SimpleIntegerProperty();
    private final IntegerProperty powerplantStartLast = new SimpleIntegerProperty();
    private final DoubleProperty powerplantLatitude = new SimpleDoubleProperty();
    private final DoubleProperty powerplantLongitude = new SimpleDoubleProperty();
    private final StringProperty powerplantStatus = new SimpleStringProperty();
    private final StringProperty powerplantWaterbodies = new SimpleStringProperty();
    private final StringProperty powerplantImageURL = new SimpleStringProperty();



//  ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL
