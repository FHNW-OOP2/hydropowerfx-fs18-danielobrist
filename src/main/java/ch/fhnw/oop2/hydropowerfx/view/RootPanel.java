package ch.fhnw.oop2.hydropowerfx.view;

import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM model;

    private HydroHeader header;
    private HydroForm form;
    private HydroTable table;

    public RootPanel(RootPM model) {
        this.model = model;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        header = new HydroHeader(model);
        form = new HydroForm(model);
        table = new HydroTable(model);

    }

    @Override
    public void layoutControls() {
        setTop(header);
        setRight(form);
        setLeft(table);

    }
}
