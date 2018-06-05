package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * ToDo: CustomControl kurz beschreiben
 *Wir haben ein CustomControl entwickelt, welches eine water und dessen Ladestand anzeigt.
 *Der User hat die Möglichkeit den Batteriestand am Slider oder direkt an der water am Ende des grünen Bereichs zu verändern.
 *
 * ToDo: Autoren ergänzen / ersetzen
 * @author Dieter Holz
 * @author Céline Albrecher
 * @author Norina Steiner
 */

public class WaterTankControl extends Region{
    // Zeichenflaeche
    private static final double ARTBOARD_WIDTH  = 100;
    private static final double ARTBOARD_HEIGHT = 100;



    // Attribute für das rezizing Verhalten
    private static final double ASPECT_RATIO = ARTBOARD_WIDTH / ARTBOARD_HEIGHT;
    private static final double MINIMUM_WIDTH  = 25;
    private static final double MINIMUM_HEIGHT = MINIMUM_WIDTH / ASPECT_RATIO;
    private static final double MAXIMUM_WIDTH = 100;
    private Pane drawingPane;

    // Prozentangabe
    private Text   display;
    //Wasserfuellung
    private Rectangle water;
    //Wassertank
    private Rectangle tank;



    private final DoubleProperty value = new SimpleDoubleProperty();
    private DoubleProperty valuePercentage = new SimpleDoubleProperty();;

    private final RootPM rootPM; // TODO: 04.06.18 Einfügen PM bei oop2-Person






    public WaterTankControl(RootPM rootPM) {
        this.rootPM = rootPM;
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandlers();
        setupValueChangeListeners();
        setupBinding();

    }

    private void initializeSelf() {
//        String fonts = getClass().getResource("/fonts/fonts.css").toExternalForm();
//        getStylesheets().add(fonts);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        getStyleClass().add("watertank-control");
    }

    private void initializeParts() {
        water = new Rectangle(20,95,50, 2);
        water.getStyleClass().add("water");
        tank = new Rectangle (15, 0,60,110);
        tank.getStyleClass().add("tank");
        display = createCenteredText("display");




    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.getStyleClass().add("drawing-pane");
        drawingPane.setMaxSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setMinSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
        drawingPane.setPrefSize(ARTBOARD_WIDTH, ARTBOARD_HEIGHT);
    }

    private void layoutParts() {

        drawingPane.getChildren().addAll(tank, water, display );
        getChildren().add(drawingPane);
    }

    private void setupEventHandlers() {

        water.setOnMouseDragged(event -> {
            double newValue = mousePositionToValue(event.getY(),1500,0);
            setValue(Math.max(0, Math.min(1500, newValue)));
        });

        tank.setOnMouseDragged(event -> {
            double newValue = mousePositionToValue(event.getY(),1500,0);
            setValue(Math.max(0, Math.min(1500, newValue)));
        });

        display.setOnMouseDragged(event -> {
            double newValue = mousePositionToValue(event.getY(),1500,0);
            setValue(Math.max(0, Math.min(1500, newValue)));
        });

    }

    private void maxLoad(){

    }



    private void setupValueChangeListeners() {
        valueProperty().addListener((observable, value, newValue) -> {
            double val = ((double)newValue/1500)*100;
            double position = mousePositionToValue(val, 0, 100);
            water.setY(ARTBOARD_HEIGHT-position);
            water.setHeight(position);
            valuePercentage.setValue(val);

            if(position > 100){
                water.setHeight(100);

            }

            if(position < 0){
                water.setHeight(0);

            }
        });



    }

    private void setupBinding() {
        display.textProperty().bind(valuePercentage.asString("%.2f").concat(" %"));
        value.bindBidirectional(rootPM.getHydroProxy().powerplantMaxVolumeProperty());

    }

    //resize by scaling
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }



    private void resize() {
        Insets padding         = getPadding();
        double availableWidth  = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() + padding.getTop() + padding.getBottom();

        double width = Math.max(Math.min(Math.min(availableWidth, availableHeight * ASPECT_RATIO), MAXIMUM_WIDTH), MINIMUM_WIDTH);

        double scalingFactor = width / ARTBOARD_WIDTH;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - ARTBOARD_WIDTH) * 0.5, (getHeight() - ARTBOARD_HEIGHT) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }


    private double percentageToValue(double percentage, double minValue, double maxValue){
        return ((maxValue - minValue) * percentage) + minValue;
    }

    private double valueToPercentage(double value, double minValue, double maxValue) {
        return (value - minValue) / (maxValue - minValue);
    }


    private double mousePositionToValue(double mouseY, double minValue, double maxValue){
        double percentage = mouseY/100;

        return percentageToValue(percentage, minValue, maxValue);
    }

    private double msPositionToValue(double mouseY, double minValue, double maxValue){
        double percentage = mouseY/100;

        return percentageToValue(percentage, minValue, maxValue);
    }




    private Text createCenteredText(String styleClass) {
        return createCenteredText(ARTBOARD_WIDTH * 0.5, ARTBOARD_HEIGHT * 0.5, styleClass);
    }


    private Text createCenteredText(double cx, double cy, String styleClass) {
        Text text = new Text();
        text.getStyleClass().add(styleClass);
        text.setTextOrigin(VPos.CENTER);
        text.setTextAlignment(TextAlignment.CENTER);
        double width = cx > ARTBOARD_WIDTH * 2 ? ((ARTBOARD_WIDTH - cx) * 0.5) : cx * 2.0;
        text.setWrappingWidth(width);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setY(cy);
        text.setX(cx - (width / 2.0));
        text.setText(String.valueOf(valuePercentage));

        return text;
    }

    // compute sizes

    @Override
    protected double computeMinWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return MINIMUM_WIDTH + horizontalPadding;
    }

    @Override
    protected double computeMinHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return MINIMUM_HEIGHT + verticalPadding;
    }

    @Override
    protected double computePrefWidth(double height) {
        Insets padding           = getPadding();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        return ARTBOARD_WIDTH + horizontalPadding;
    }

    @Override
    protected double computePrefHeight(double width) {
        Insets padding         = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();

        return ARTBOARD_HEIGHT + verticalPadding;
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

}
