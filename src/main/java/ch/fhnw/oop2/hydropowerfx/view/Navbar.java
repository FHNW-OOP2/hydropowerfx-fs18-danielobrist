package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Navbar extends VBox implements ViewMixin {

        private final RootPM root;

        private Label nameLabel;
        private Button add;

        public Navbar(RootPM root) {
            this.root = root;
            init();
        }

        @Override
        public void initializeSelf() {
            getStyleClass().add("table");
        }

        @Override
        public void initializeControls() {
            nameLabel = new Label();
        }

        @Override
        public void layoutControls() {
            getChildren().addAll(nameLabel);
        }

        @Override
        public void setupBindings() {
            PowerplantsPM proxy = root.getHydroProxy();

            nameLabel.textProperty().bind(proxy.nameProperty());
        }

}
