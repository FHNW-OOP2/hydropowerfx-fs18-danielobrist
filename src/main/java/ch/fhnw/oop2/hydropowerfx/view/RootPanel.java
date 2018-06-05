package ch.fhnw.oop2.hydropowerfx.view;

import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM model;

    private CantonTable cantons;
    private HydroForm form;
    private HydroTable table;
    private HydroHeader header;
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
        header = new HydroHeader(model);

    }

    @Override
    public void layoutControls() {

        setBottom(cantons);

        setTop(header);
        setCenter(splitpane);

        splitpane.getItems().add(0, table);
        splitpane.getItems().add(1, form);
        Platform.runLater(() -> {
            splitpane.setDividerPosition(0, 0.37);
        });

    }

}
