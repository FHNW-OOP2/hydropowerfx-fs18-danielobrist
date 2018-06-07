package ch.fhnw.oop2.hydropowerfx.view;

import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class PowerControlCursor {

    Circle circle;
    //Polygon peak;
    Circle peak;
    Line body;

    public PowerControlCursor(double cx, double cy, double radius, double value, double sizeFactor) {

        //Shadow & Effects
        DropShadow MY_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.color(0, 0, 0, 0.25), 10d * (sizeFactor), 0, 4, 4);

        InnerShadow MY_INNER_SHADOW = new InnerShadow();
        MY_INNER_SHADOW.setOffsetX(4);
        MY_INNER_SHADOW.setOffsetY(4);
        MY_INNER_SHADOW.setColor(Color.web("0x3b596d"));

        circle = new Circle(cx, cy, radius / 12, Color.DARKGREY);
        circle.setEffect(MY_INNER_SHADOW);
        double endX = cx - (radius / 1.5 * (Math.sin(Math.PI / 2 + Math.toRadians(value / 5))));
        double endY = cy + (radius / 1.5 * (Math.cos(Math.PI / 2 + Math.toRadians(value / 5))));

        body = new Line();
        body.setStartX(cx);
        body.setStartY(cy);
        body.setEndX(endX);
        body.setEndY(endY);
        body.setStroke(Color.DARKRED);
        body.setStrokeWidth(radius / 20);

        peak = new Circle(endX, endY, radius / 24, Color.DARKGREY);
        peak.setEffect(MY_INNER_SHADOW);
		/*
		double endX2= cx - (radius*(Math.sin(Math.PI/2+Math.toRadians(value*1.8))));
		double endY2 =cy + (radius*(Math.cos(Math.PI/2+Math.toRadians(value*1.8))));
		
		peak = new Polygon();
		peak.getPoints().setAll(
				endX2,endY2,
			 endX+radius/10, endY+radius/10,
			 endX-radius/10, endY-radius/10
				);
		   peak.setStrokeWidth(radius/20);
//     y0 + r*sin(t)//+radius*Math.cos(currentValueProperty.get()*1.8)
	      */
    }

    Group getElements() {
        Group group = new Group();
        group.getChildren().addAll(body, circle, peak);
        return group;
    }
}
