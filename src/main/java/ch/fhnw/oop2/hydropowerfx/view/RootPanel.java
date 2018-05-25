package ch.fhnw.oop2.hydropowerfx.view;


import javafx.scene.layout.BorderPane;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;


public class RootPanel extends BorderPane implements ViewMixin {
    private final RootPM rootPM;

    private Header header;
    private Form form;
    private Table table;

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
        form = new Form(rootPM);
        table = new Table(rootPM);

    }

    @Override
    public void layoutControls() {
        setTop(header);
        setRight(form);
        setLeft(table);

    }

    @Override
    public void setupBindings() {
    }
}
