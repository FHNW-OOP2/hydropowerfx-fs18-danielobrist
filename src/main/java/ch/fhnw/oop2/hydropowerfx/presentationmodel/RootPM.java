package ch.fhnw.oop2.hydropowerfx.presentationmodel;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;

public class RootPM {
    private static final String FILE_NAME = "/data/HYDRO_POWERSTATION.csv";
    private static final String FILE_NAME_CANTONS = "/data/cantons.csv";
    private static final String DELIMITER = ";";

    private final StringProperty applicationTitle = new SimpleStringProperty("HydroPowerFX");
    private final IntegerProperty selectedPowerplantId = new SimpleIntegerProperty(-1);

    private final ObservableList<PowerplantsPM> allPowerplants = FXCollections.observableArrayList();
    private final ObservableList<CantonPM> allCantons = FXCollections.observableArrayList();

    private final PowerplantsPM hydroProxy = new PowerplantsPM();

    private final BooleanProperty focusLatitude = new SimpleBooleanProperty();
    private final BooleanProperty focusLongitude = new SimpleBooleanProperty();

    public RootPM() {
        allPowerplants.addAll(readFromFile());
        allCantons.addAll(readFromCantonFile());

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
        hydroProxy.powerplantMaxPowerProperty().addListener((observable) -> Platform.runLater(() -> updateCantonTable()));
        hydroProxy.powerplantCantonProperty().addListener((observable) -> Platform.runLater(() -> updateCantonTable()));

    }


    private List<PowerplantsPM> readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME)) {
            return stream.skip(1)                                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(line -> new PowerplantsPM(line.split(DELIMITER, 14))) // aus jeder Zeile ein Objekt machen
                    .collect(Collectors.toList());                        // alles aufsammeln
        }
    }

    private List<CantonPM> readFromCantonFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME_CANTONS)) {
            return stream.skip(1)
                    .map(line -> new CantonPM(line.split(DELIMITER, 12), hydropowersPerCanton(line.split(DELIMITER)[1]), powerPerCanton(line.split(DELIMITER)[1])))
                    .collect(Collectors.toList());                        // alles aufsammeln
        }
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME))) {
            writer.write("ENTITY_ID;NAME;TYPE;SITE;CANTON;MAX_WATER_VOLUME_M3_S;MAX_POWER_MW;START_OF_OPERATION_FIRST;START_OF_OPERATION_LAST;LATITUDE;LONGITUDE;STATUS;WATERBODIES;IMAGE_URL");
            writer.newLine();
            allPowerplants.stream()
                    .map(powerplant -> powerplant.infoAsLine(DELIMITER))
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    });
        } catch (IOException e) {
            throw new IllegalStateException("save failed");
        }
    }

    public void add() {
        allPowerplants.addAll(new PowerplantsPM(newHighestID(), ""));
        setSelectedPowerplantId(newHighestID() - 1); //sets selection to new added row
    }

    public void delete() {
        allPowerplants.remove(getPowerplant(getSelectedPowerplantId()));
    }


    public void search(String searchtext) {
        /*String searchTextSmall = searchtext.toLowerCase();
        FilteredList<PowerplantsPM> filteredList = new FilteredList<>(data, p -> true);
        filteredList.setPredicate(PowerplantsPM -> {
            if (String.valueOf(PowerplantsPM.getName().toLowerCase().contains(searchtext))) {
                return true;
            } else if (String.valueOf(PowerplantsPM.getPowerplantStartFirst()).contains(searchtext)) {
                return true;
            }
            return false;
        });*/

    }

    //kraftwerke pro kanton zählen
    public int hydropowersPerCanton(String cantonShort) {
        return (int) allPowerplants.stream()
                .filter(powerplant -> powerplant.getPowerplantCanton().equals(cantonShort))
                .count();
    }

    //alle maxpower pro kanton summieren
    public double powerPerCanton(String cantonShort) {
        return allPowerplants.stream()
                .filter(powerplant -> powerplant.getPowerplantCanton().equals(cantonShort))
                .mapToDouble(PowerplantsPM::getPowerplantMaxPower).sum();
    }

    //get highest ID + 1
    public int newHighestID() {
        Comparator<PowerplantsPM> comp = Comparator.comparingInt(PowerplantsPM::getPowerplantID);
        PowerplantsPM powerplantWithHighestId = allPowerplants.stream()
                .max(comp)
                .get();

        return powerplantWithHighestId.getPowerplantID() + 1;
    }

    public void updateCantonTable() {
        if (allCantons != null) {
            allCantons.clear();
        }
        List<String> cantonShorts = this.allPowerplants.stream()
                .map(PowerplantsPM::getPowerplantCanton)
                .distinct()
                .sorted()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        cantonShorts.forEach(canton -> {
            CantonPM c = new CantonPM();
            c.setCantonName(canton);
            for (PowerplantsPM powerplant : allPowerplants) {
                if (powerplant.getPowerplantCanton().equals(canton)) {
                    if (powerplant.getPowerplantMaxPower() == 0) {
                        powerplant.setPowerplantMaxPower(0);
                    }
                    c.powerPerCantonProperty().setValue(c.powerPerCantonProperty().getValue() + powerplant.getPowerplantMaxPower());
                    c.hydropowersPerCantonProperty()
                            .setValue(c.hydropowersPerCantonProperty()
                                    .getValue() + 1);
                }

            }
            allCantons.add(c);
        });
    }


    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(getPath(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getPath(String fileName) {
        try {
            return Paths.get(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public PowerplantsPM getHydroProxy() {
        return hydroProxy;
    }

    private void bindToProxy(PowerplantsPM powerplant) {
        hydroProxy.powerplantIDProperty().bindBidirectional(powerplant.powerplantIDProperty());
        hydroProxy.nameProperty().bindBidirectional(powerplant.nameProperty());
        hydroProxy.powerplantTypeProperty().bindBidirectional(powerplant.powerplantTypeProperty());
        hydroProxy.powerplantMaxPowerProperty().bindBidirectional(powerplant.powerplantMaxPowerProperty());
        hydroProxy.powerplantCantonProperty().bindBidirectional(powerplant.powerplantCantonProperty());
        hydroProxy.powerplantMaxVolumeProperty().bindBidirectional(powerplant.powerplantMaxVolumeProperty());
        hydroProxy.powerplantSiteProperty().bindBidirectional(powerplant.powerplantSiteProperty());
        hydroProxy.powerplantStatusProperty().bindBidirectional(powerplant.powerplantStatusProperty());
        hydroProxy.powerplantStartFirstProperty().bindBidirectional(powerplant.powerplantStartFirstProperty());
        hydroProxy.powerplantStartLastProperty().bindBidirectional(powerplant.powerplantStartLastProperty());
        hydroProxy.powerplantLatitudeProperty().bindBidirectional(powerplant.powerplantLatitudeProperty());
        hydroProxy.powerplantLongitudeProperty().bindBidirectional(powerplant.powerplantLongitudeProperty());
        hydroProxy.powerplantWaterbodiesProperty().bindBidirectional(powerplant.powerplantWaterbodiesProperty());
        hydroProxy.powerplantImageURLProperty().bindBidirectional(powerplant.powerplantImageURLProperty());
    }

    private void unbindFromProxy(PowerplantsPM powerplant) {
        hydroProxy.powerplantIDProperty().unbindBidirectional(powerplant.powerplantIDProperty());
        hydroProxy.nameProperty().unbindBidirectional(powerplant.nameProperty());
        hydroProxy.powerplantTypeProperty().unbindBidirectional(powerplant.powerplantTypeProperty());
        hydroProxy.powerplantMaxPowerProperty().unbindBidirectional(powerplant.powerplantMaxPowerProperty());
        hydroProxy.powerplantCantonProperty().unbindBidirectional(powerplant.powerplantCantonProperty());
        hydroProxy.powerplantMaxVolumeProperty().unbindBidirectional(powerplant.powerplantMaxVolumeProperty());
        hydroProxy.powerplantSiteProperty().unbindBidirectional(powerplant.powerplantSiteProperty());
        hydroProxy.powerplantStatusProperty().unbindBidirectional(powerplant.powerplantStatusProperty());
        hydroProxy.powerplantStartFirstProperty().unbindBidirectional(powerplant.powerplantStartFirstProperty());
        hydroProxy.powerplantStartLastProperty().unbindBidirectional(powerplant.powerplantStartLastProperty());
        hydroProxy.powerplantLatitudeProperty().unbindBidirectional(powerplant.powerplantLatitudeProperty());
        hydroProxy.powerplantLongitudeProperty().unbindBidirectional(powerplant.powerplantLongitudeProperty());
        hydroProxy.powerplantWaterbodiesProperty().unbindBidirectional(powerplant.powerplantWaterbodiesProperty());
        hydroProxy.powerplantImageURLProperty().unbindBidirectional(powerplant.powerplantImageURLProperty());

    }

    public PowerplantsPM getPowerplant(int id) {
        return allPowerplants.stream()
                .filter(powerplantsPM -> powerplantsPM.getPowerplantID() == id)
                .findAny()
                .orElse(null);
    }

    public ObservableList<PowerplantsPM> getAllPowerplants() {
        return allPowerplants;
    }

    public ObservableList<CantonPM> getAllCantons() {
        return allCantons;
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
    public boolean isFocusLatitude() {
        return focusLatitude.get();
    }

    public BooleanProperty focusLatitudeProperty() {
        return focusLatitude;
    }

    public void setFocusLatitude(boolean focusLatitude) {
        this.focusLatitude.set(focusLatitude);
    }

    public boolean getFocusLongitude() {
        return focusLongitude.get();
    }

    public BooleanProperty focusLongitudeProperty() {
        return focusLongitude;
    }

    public void setFocusLongitude(boolean focusLongitude) {
        this.focusLongitude.set(focusLongitude);
    }



}
