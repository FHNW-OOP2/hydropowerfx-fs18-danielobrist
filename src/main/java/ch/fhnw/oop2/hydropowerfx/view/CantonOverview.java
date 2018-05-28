package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class CantonOverview extends VBox implements ViewMixin {

    private final RootPM root;

    public CantonOverview(RootPM root) {
        this.root = root;
        init();
    }

    @Override
    public void initializeSelf() {
        getStyleClass().add("cantons");
    }

    @Override
    public void initializeControls() {

    }

    @Override
    public void layoutControls() {
    }


    @Override
    public void setupBindings() {

    }
}