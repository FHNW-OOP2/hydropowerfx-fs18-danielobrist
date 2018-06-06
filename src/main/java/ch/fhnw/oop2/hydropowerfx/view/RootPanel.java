package ch.fhnw.oop2.hydropowerfx.view;

import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.stage.Stage;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM model;

    private HydroForm form;
    private HydroTable table;
    private HydroHeader header;
    private final SplitPane splitpane;
    private CantonTable cantons;

    public RootPanel(RootPM model) {
        this.splitpane = new SplitPane();
        this.model = model;
        init();
    }

    @Override
    public void initializeSelf() {
        addStylesheetFiles("style.css");
    }

    @Override
    public void initializeControls() {


        form = new HydroForm(model);
        table = new HydroTable(model);
        header = new HydroHeader(model);
        cantons = new CantonTable(model);

    }

    @Override
    public void layoutControls() {
        setBottom(cantons);
        setCenter(splitpane);
        splitpane.getItems().add(0, table);
        splitpane.getItems().add(1, form);
        Platform.runLater(() -> {
            splitpane.setDividerPosition(0, 0.4);
        });

        setTop(header);



    }



}
