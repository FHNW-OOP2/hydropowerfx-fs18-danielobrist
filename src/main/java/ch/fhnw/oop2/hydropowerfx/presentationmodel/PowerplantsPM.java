package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


//  ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL

public class PowerplantsPM {
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

    public PowerplantsPM() {
    }

    public PowerplantsPM(String[] line) {
        setPowerplantID(Integer.valueOf(line[0]));
        setPowerplantName(line[1]);
        setPowerplantType(line[2]);
        setPowerplantSite(line[3]);
        setPowerplantCanton(line[4]);
        setPowerplantMaxVolume(Double.valueOf(line[5]));
        setPowerplantMaxPower(Double.valueOf(line[6]));
        setPowerplantStartFirst(Integer.valueOf(line[7]));
        setPowerplantStartLast(Integer.valueOf(line[8]));
        setPowerplantLatitude(Double.valueOf(line[9]));
        setPowerplantLongitude(Double.valueOf(line[10]));
        setPowerplantStatus(line[11]);
        setPowerplantWaterbodies(line[12]);
        setPowerplantImageURL(line[13]);
    }

    public String infoAsLine(String delimiter) {
        return String.join(delimiter,
                Integer.toString(getPowerplantID()),
                getPowerplantName(),
                getPowerplantType(),
                getPowerplantSite(),
                getPowerplantCanton(),
                Double.toString(getPowerplantMaxVolume()),
                Double.toString(getPowerplantMaxPower()),
                Integer.toString(getPowerplantStartFirst()),
                Integer.toString(getPowerplantStartLast()),
                Double.toString(getPowerplantLatitude()),
                Double.toString(getPowerplantLongitude()),
                getPowerplantStatus(),
                getPowerplantWaterbodies(),
                getPowerplantImageURL()
        );
    }

    public String getPowerplantName() {
        return powerplantName.get();
    }

    public StringProperty powerplantNameProperty() {
        return powerplantName;
    }

    public void setPowerplantName(String powerplantName) {
        this.powerplantName.set(powerplantName);
    }

    public String getPowerplantType() {
        return powerplantType.get();
    }

    public StringProperty powerplantTypeProperty() {
        return powerplantType;
    }

    public void setPowerplantType(String powerplantType) {
        this.powerplantType.set(powerplantType);
    }

    public String getPowerplantSite() {
        return powerplantSite.get();
    }

    public StringProperty powerplantSiteProperty() {
        return powerplantSite;
    }

    public void setPowerplantSite(String powerplantSite) {
        this.powerplantSite.set(powerplantSite);
    }

    public String getPowerplantCanton() {
        return powerplantCanton.get();
    }

    public StringProperty powerplantCantonProperty() {
        return powerplantCanton;
    }

    public void setPowerplantCanton(String powerplantCanton) {
        this.powerplantCanton.set(powerplantCanton);
    }

    public double getPowerplantMaxVolume() {
        return powerplantMaxVolume.get();
    }

    public DoubleProperty powerplantMaxVolumeProperty() {
        return powerplantMaxVolume;
    }

    public void setPowerplantMaxVolume(double powerplantMaxVolume) {
        this.powerplantMaxVolume.set(powerplantMaxVolume);
    }

    public int getPowerplantStartFirst() {
        return powerplantStartFirst.get();
    }

    public IntegerProperty powerplantStartFirstProperty() {
        return powerplantStartFirst;
    }

    public void setPowerplantStartFirst(int powerplantStartFirst) {
        this.powerplantStartFirst.set(powerplantStartFirst);
    }

    public int getPowerplantStartLast() {
        return powerplantStartLast.get();
    }

    public IntegerProperty powerplantStartLastProperty() {
        return powerplantStartLast;
    }

    public void setPowerplantStartLast(int powerplantStartLast) {
        this.powerplantStartLast.set(powerplantStartLast);
    }

    public double getPowerplantLatitude() {
        return powerplantLatitude.get();
    }

    public DoubleProperty powerplantLatitudeProperty() {
        return powerplantLatitude;
    }

    public void setPowerplantLatitude(double powerplantLatitude) {
        this.powerplantLatitude.set(powerplantLatitude);
    }

    public double getPowerplantLongitude() {
        return powerplantLongitude.get();
    }

    public DoubleProperty powerplantLongitudeProperty() {
        return powerplantLongitude;
    }

    public void setPowerplantLongitude(double powerplantLongitude) {
        this.powerplantLongitude.set(powerplantLongitude);
    }

    public String getPowerplantStatus() {
        return powerplantStatus.get();
    }

    public StringProperty powerplantStatusProperty() {
        return powerplantStatus;
    }

    public void setPowerplantStatus(String powerplantStatus) {
        this.powerplantStatus.set(powerplantStatus);
    }

    public String getPowerplantWaterbodies() {
        return powerplantWaterbodies.get();
    }

    public StringProperty powerplantWaterbodiesProperty() {
        return powerplantWaterbodies;
    }

    public void setPowerplantWaterbodies(String powerplantWaterbodies) {
        this.powerplantWaterbodies.set(powerplantWaterbodies);
    }

    public String getPowerplantImageURL() {
        return powerplantImageURL.get();
    }

    public StringProperty powerplantImageURLProperty() {
        return powerplantImageURL;
    }

    public void setPowerplantImageURL(String powerplantImageURL) {
        this.powerplantImageURL.set(powerplantImageURL);
    }

    @Override

    public String toString() {
        return getName();
    }

    public int getPowerplantID() {
        return powerplantID.get();
    }

    public IntegerProperty powerplantIDProperty() {
        return powerplantID;
    }

    public void setPowerplantID(int powerplantID) {
        this.powerplantID.set(powerplantID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPowerplantMaxPower() {
        return powerplantMaxPower.get();
    }

    public DoubleProperty powerplantMaxPowerProperty() {
        return powerplantMaxPower;
    }

    public void setPowerplantMaxPower(double powerplantMaxPower) {
        this.powerplantMaxPower.set(powerplantMaxPower);
    }
}
