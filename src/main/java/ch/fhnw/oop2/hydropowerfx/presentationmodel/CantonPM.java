package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Kanton;Kürzel;Kantonsnummer;Standesstimme;Beitritt;Hauptort;Einwohner;Ausländer;Fläche;Einwohnerdichte;Gemeinden;Amtssprache
public class CantonPM {
    private final StringProperty cantonName = new SimpleStringProperty();
    private final StringProperty cantonShort = new SimpleStringProperty();
    //alle anderen attribute adden

    public CantonPM(String[] line, int totalPowerplantsPerCanton, double totalPowerPerCanton){
        setCantonName(line[0]);
        setCantonShort(line[1]);
        //andere setter
    }

    //getter and setter

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

