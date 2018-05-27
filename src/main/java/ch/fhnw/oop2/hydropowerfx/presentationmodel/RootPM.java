package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import java.util.Arrays;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RootPM {
    private final StringProperty applicationTitle = new SimpleStringProperty("HydroPowerFX");
    private final IntegerProperty selectedPowerplantId = new SimpleIntegerProperty(-1);

    private final ObservableList<PowerplantsPM> allPowerplants = FXCollections.observableArrayList();

    private final PowerplantsPM hydroProxy = new PowerplantsPM();

    public RootPM() {
        this(createAllPowerplants());
    }

    public RootPM(PowerplantsPM... powerplants) {
        this(Arrays.asList(powerplants));
    }

    private RootPM(List<PowerplantsPM> hydroList) {
        allPowerplants.addAll(hydroList);

        selectedPowerplantIdProperty().addListener((observable, oldValue, newValue) -> {
                    PowerplantsPM oldSelection = getPowerplant(oldValue.intValue());
                    PowerplantsPM newSelection = getPowerplant(newValue.intValue());

                    if (oldSelection != null) {
                        unbindFromProxy(oldSelection);
                    }

                    if (newSelection != null) {
                        bindToProxy(newSelection);
                    }
                }
        );
    }

    public PowerplantsPM getHydroProxy() {
        return hydroProxy;
    }

    private void bindToProxy(PowerplantsPM powerplant) {
        hydroProxy.idProperty()  .bindBidirectional(powerplant.idProperty());
        hydroProxy.nameProperty().bindBidirectional(powerplant.nameProperty());
        hydroProxy.areaProperty().bindBidirectional(powerplant.areaProperty());
    }

    private void unbindFromProxy(PowerplantsPM powerplant) {
        hydroProxy.idProperty()  .unbindBidirectional(powerplant.idProperty());
        hydroProxy.nameProperty().unbindBidirectional(powerplant.nameProperty());
        hydroProxy.areaProperty().unbindBidirectional(powerplant.areaProperty());
    }

    public PowerplantsPM getPowerplant(int id) {
        return allPowerplants.stream()
                .filter(powerplantsPM -> powerplantsPM.getId() == id)
                .findAny()
                .orElse(null);
    }

    private static List<PowerplantsPM> createAllPowerplants() {
        return List.of(new PowerplantsPM(0, "Schweiz"    , 41_285.00),
                new PowerplantsPM(1, "Deutschland", 357_121.41),
                new PowerplantsPM(2, "Frankreich" , 668_763.00),
                new PowerplantsPM(3, "Italien"    , 301_338.00),
                new PowerplantsPM(4, "Oesterreich",  83_878.99));
    }

    public ObservableList<PowerplantsPM> allPowerplants() {
        return allPowerplants;
    }

    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public int getSelectedPowerplantId() {
        return selectedPowerplantId.get();
    }

    public IntegerProperty selectedPowerplantIdProperty() {
        return selectedPowerplantId;
    }

    public void setSelectedPowerplantId(int selectedPowerplantId) {
        this.selectedPowerplantId.set(selectedPowerplantId);
    }

}
