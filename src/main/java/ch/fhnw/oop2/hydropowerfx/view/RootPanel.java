package ch.fhnw.oop2.hydropowerfx.view;


import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM rootPM;

    private Header header;

    public RootPanel(RootPM model) {
        this.rootPM = model;

        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        header = new Header(rootPM);

    }

    @Override
    public void layoutControls() {
        setTop(header);

    }

    @Override
    public void setupBindings() {
    }
}
