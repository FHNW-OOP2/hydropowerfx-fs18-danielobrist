package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.*;

//Kanton;Kürzel;Kantonsnummer;Standesstimme;Beitritt;Hauptort;Einwohner;Ausländer;Fläche;Einwohnerdichte;Gemeinden;Amtssprache
public class CantonPM {
    private final StringProperty cantonName = new SimpleStringProperty();
    private final StringProperty cantonShort = new SimpleStringProperty();
    public IntegerProperty hydropowersPerCanton = new SimpleIntegerProperty();
    public DoubleProperty powerPerCanton = new SimpleDoubleProperty();

    //alle anderen attribute nicht benötigt im moment

    public CantonPM(){
    }

    public CantonPM(String[] line, int totalPowerplantsPerCanton, double totalPowerPerCanton){
        setCantonName(line[0]);
        setCantonShort(line[1]);
        setHydropowersPerCanton(totalPowerplantsPerCanton);
        setPowerPerCanton(totalPowerPerCanton);
    }

    //getter and setter


    public double getPowerPerCanton() {
        return powerPerCanton.get();
    }

    public DoubleProperty powerPerCantonProperty() {
        return powerPerCanton;
    }

    public void setPowerPerCanton(double powerPerCanton) {
        this.powerPerCanton.set(powerPerCanton);
    }

    public int getHydropowersPerCanton() {
        return hydropowersPerCanton.get();
    }

    public IntegerProperty hydropowersPerCantonProperty() {
        return hydropowersPerCanton;
    }

    public void setHydropowersPerCanton(int hydropowersPerCanton) {
        this.hydropowersPerCanton.set(hydropowersPerCanton);
    }

    public String getCantonName() {
        return cantonName.get();
    }

    public StringProperty cantonNameProperty() {
        return cantonName;
    }

    public void setCantonName(String cantonName) {
        this.cantonName.set(cantonName);
    }

    public String getCantonShort() {
        return cantonShort.get();
    }

    public StringProperty cantonShortProperty() {
        return cantonShort;
    }

    public void setCantonShort(String cantonShort) {
        this.cantonShort.set(cantonShort);
    }
}

