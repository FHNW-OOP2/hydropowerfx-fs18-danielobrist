package ch.fhnw.oop2.hydropowerfx.view;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM model;

    private CantonTable cantons;
    private HydroForm form;
    private HydroTable table;
    private HydroHeader navbar;
    private final SplitPane splitpane;

    public RootPanel(RootPM model) {
        this.model = model;
        this.splitpane = new SplitPane();
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("splitpane.css");
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {
        cantons = new CantonTable(model);
        form = new HydroForm(model);
        table = new HydroTable(model);
        navbar = new HydroHeader(model);

    }

    @Override
    public void layoutControls() {

        setTop(navbar);
        setCenter(splitpane);
        setBottom(cantons);

        splitpane.getItems().add(0, table);
        splitpane.getItems().add(1, form);
    }
}
