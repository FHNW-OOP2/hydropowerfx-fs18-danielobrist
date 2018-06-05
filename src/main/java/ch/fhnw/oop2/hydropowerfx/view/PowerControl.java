package ch.fhnw.oop2.hydropowerfx.view;

import javafx.beans.property.DoubleProperty;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;

/**
 * @author Bastady C�line
 */
public class PowerControl extends Region {


    /**
     * Simple 20bar meter
     *
     * @author Jasper Potts
     */

    private static final double ARTBOARD_SIZE = 200;//figma frame_width

    private static final double PREFERRED_SIZE = ARTBOARD_SIZE;
    private static final double MINIMUM_SIZE = 14;
    private static final double MAXIMUM_SIZE = 800;

    //part of my graphic (see figma)
    private Arc arc;
    //Sub-Zones
    private Arc redZone;
    private Arc orangeZone;
    private Arc yellowZone;
    private Arc greenZone;
    private Cursor cursor;


    //all properties
    private final DoubleProperty currentValue = new SimpleDoubleProperty();

    //blurType, color,radius,spread, offsetX, offsetY
    private InnerShadow MY_INNER_SHADOW = new InnerShadow();


    private static DropShadow MY_SHADOW;
    private static final double radius = 100.0f;
    private double cx;
    private double cy;
    //needed for resizing
    private Pane drawingPane;

    private static double sizeFactor() {
        return (PREFERRED_SIZE / ARTBOARD_SIZE);
    }

    public PowerControl() {
        initializeSizes();
        initializeSelf();
        initializeParts();
        initializeDrawingPane();
        layoutParts();
        setupEventHandler();
        setupValueChangeListener();
        setupBinding();
        setCurrentValue(50);
        initializeShadow();

    }

    private void initializeShadow() {
        MY_INNER_SHADOW.setOffsetX(4);
        MY_INNER_SHADOW.setOffsetY(4);
        MY_INNER_SHADOW.setColor(Color.web("0x3b596d"));

        MY_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.color(0, 0, 0, 0.25), 10d * (sizeFactor()), 0, 4, 4);
    }

    private void initializeSizes() {
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();
        double horizontalPadding = padding.getLeft() + padding.getRight();

        setMinSize(MINIMUM_SIZE + horizontalPadding, MINIMUM_SIZE + verticalPadding);
        setPrefSize(PREFERRED_SIZE + horizontalPadding, PREFERRED_SIZE + verticalPadding);
        setMaxSize(MAXIMUM_SIZE + horizontalPadding, MAXIMUM_SIZE + verticalPadding);
    }

    private void initializeSelf() {
       /* String fonts = getClass().getResource("fonts.css").toExternalForm();
        getStylesheets().add(fonts);*/


        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);

        // initialize resizing constraints
        Insets padding = getPadding();
        double verticalPadding = padding.getTop() + padding.getBottom();
        double horizontalPadding = padding.getLeft() + padding.getRight();
        setMinSize(MINIMUM_SIZE + horizontalPadding, MINIMUM_SIZE + verticalPadding);
        setPrefSize(ARTBOARD_SIZE + horizontalPadding, ARTBOARD_SIZE + verticalPadding);
        setMaxSize(MAXIMUM_SIZE + horizontalPadding, MAXIMUM_SIZE + verticalPadding);
    }

    // farbe wählen für zonen
    private Arc createZone(double cx, double cy, double radius2, float startAngle, float length, Color color, ArcType type) {

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.DARKGREY);
        ds1.setOffsetY(1.0);

        Arc arc = new Arc();
        arc.setCenterX(cx);
        arc.setCenterY(cy);
        arc.setRadiusX(radius2 * PowerControl.sizeFactor());
        arc.setRadiusY(radius2 * PowerControl.sizeFactor());
        arc.setStartAngle(startAngle);
        arc.setLength(length);
        arc.setFill(color);
        arc.setType(type);
        arc.setEffect(ds1);
        return arc;

    }


    private void initializeParts() {

        //Eigenschaften von FIGMA , redundanz vermeiden
        cx = 100 * sizeFactor();
        cy = 100 * sizeFactor();


        //aiguille
        updateCursor();

        //sub-zone initialisation
        arc = createZone(cx, cy, radius, 0.0f, 180.0f, Color.WHITE, ArcType.CHORD);
        arc.setEffect(MY_INNER_SHADOW);                                     //Anfangspunkt  Zonengrösse
        redZone = createZone(cx, cy, radius - (radius / 10), 0.0f, 20.0f, Color.RED, ArcType.ROUND);
        orangeZone = createZone(cx, cy, radius - (radius / 10), 20.0f, 70.0f, Color.ORANGE, ArcType.ROUND);
        greenZone = createZone(cx, cy, radius - (radius / 10), 90.0f, 70.0f, Color.GREENYELLOW, ArcType.ROUND);
        yellowZone = createZone(cx, cy, radius - (radius / 10), 160.0f, 20.0f, Color.YELLOW, ArcType.ROUND);

        Stop[] stops = new Stop[]{new Stop(0, Color.GREEN), new Stop(1, Color.WHITESMOKE)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);


    }

    private void initializeDrawingPane() {
        drawingPane = new Pane();
        drawingPane.setMaxSize(PREFERRED_SIZE, PREFERRED_SIZE);
        drawingPane.setMinSize(PREFERRED_SIZE, PREFERRED_SIZE);
        drawingPane.setPrefSize(PREFERRED_SIZE, PREFERRED_SIZE);
        drawingPane.getStyleClass().add("battery");
    }

    private void layoutParts() {
        drawingPane.getChildren().addAll(arc, cursor.getElements(), redZone, orangeZone, yellowZone, greenZone);

        drawingPane.getChildren().add(createTicks(cx, cy, 21, 180, radius / 15, 5, 90, "")); //Intervall, Anzahl Ticks
        //drawingPane.getChildren().add(createTicks(cx, cy, 19,180, radius/15, 5, 90, ""));
        getChildren().add(drawingPane);
    }

    private void setupEventHandler() {
        /*arc.setOnMouseClicked(event -> {
            double newVal = getCurrentValue() + 50;
            if (newVal <= 900) setCurrentValue(newVal);
            else {
                setCurrentValue(0);
            }

        });*/
    }


    private void updateCursor() {
        cursor = new Cursor(cx, cy, radius * sizeFactor(), getCurrentValue(), sizeFactor());

    }

    private void setupValueChangeListener() {
        currentValue.addListener((observable, oldValue, newValue) -> {
                    double state = (double) newValue;
                    if (state <= 900 && state >= 0) {

                        drawingPane.getChildren().remove(cursor.getElements());
                        updateCursor();

                        drawingPane.getChildren().add(cursor.getElements());

                    }
                }
        );
    }

    private void setupBinding() {

    }


    //resize by scaling
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        resize();
    }

    private void resize() {
        Insets padding = getPadding();
        double availableWidth = getWidth() - padding.getLeft() - padding.getRight();
        double availableHeight = getHeight() - padding.getTop() - padding.getBottom();
        double size = Math.max(Math.min(Math.min(availableWidth, availableHeight), MAXIMUM_SIZE), MINIMUM_SIZE);

        double scalingFactor = size / PREFERRED_SIZE;

        if (availableWidth > 0 && availableHeight > 0) {
            drawingPane.relocate((getWidth() - PREFERRED_SIZE) * 0.5, (getHeight() - PREFERRED_SIZE) * 0.5);
            drawingPane.setScaleX(scalingFactor);
            drawingPane.setScaleY(scalingFactor);
        }
    }

    //given method from CUIE-Course
    private Group createTicks(double cx, double cy, int numberOfTicks, double overallAngle, double tickLength, double indent, double startingAngle, String styleClass) {
        Group group = new Group();

        double degreesBetweenTicks = overallAngle == 360 ?
                overallAngle / numberOfTicks :
                overallAngle / (numberOfTicks - 1);
        double outerRadius = Math.min(cx, cy) - indent;
        double innerRadius = Math.min(cx, cy) - indent - tickLength;

        for (int i = 0; i < numberOfTicks; i++) {

            double angle = 180 + startingAngle + i * degreesBetweenTicks;

            Point2D startPoint = pointOnCircle(cx, cy, outerRadius, angle);
            Point2D labelPoint = pointOnCircle(cx, cy, outerRadius - 5, angle);
            Point2D endPoint = pointOnCircle(cx, cy, innerRadius, angle);

            Line tick = new Line(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
            tick.getStyleClass().add(styleClass);
            if ((i * 5) % 20 == 0) {
                tick.setStroke(Color.RED);
            }

        /*    Text label = new Text(labelPoint.getX(), labelPoint.getY(), String.valueOf(i * 5));
            label.setFont(new Font("Helvetica", 5 * sizeFactor()));
            group.getChildren().addAll(tick, label);*/
        }

        return group;
    }

    private Point2D pointOnCircle(double cX, double cY, double radius, double angle) {
        return new Point2D(cX - (radius * Math.sin(Math.toRadians(angle - 180))),
                cY + (radius * Math.cos(Math.toRadians(angle - 180))));
    }

    // all Getter and Setter

    public double getCurrentValue() {
        return currentValue.get();
    }

    public DoubleProperty currentValueProperty() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue.set(currentValue);
    }


}