package ch.fhnw.oop2.hydropowerfx.view;

import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM model;

    private CantonOverview cantons;
    private HydroForm form;
    private HydroTable table;
    private Navbar navbar;

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
        cantons = new CantonOverview(model);
        form = new HydroForm(model);
        table = new HydroTable(model);
        navbar = new Navbar(model);

    }

    @Override
    public void layoutControls() {
        setTop(navbar);
        setRight(form);
        setLeft(table);
        setBottom(cantons);
    }
}
