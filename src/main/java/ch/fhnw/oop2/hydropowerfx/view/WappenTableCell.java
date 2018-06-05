package ch.fhnw.oop2.hydropowerfx.view;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerplantsPM;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class WappenTableCell extends TableCell<PowerplantsPM, String> {
    private static final Map<String, Image> WAPPEN = new HashMap<>();

    private static final Insets INSETS = new Insets(1, 8, 1, 5);

    @Override
    protected void updateItem(String item, boolean empty) {
        setText("");
        setGraphic(null);
        if (!empty) {
            Image img = WAPPEN.get(item);
            if (img == null) {
                img = new Image(getClass().getResource("/wappen_klein/" + item + ".png")
                        .toExternalForm(), 18, 18, true, true, true);
                WAPPEN.put(item, img);
            }

            ImageView imageView = new ImageView(img);

            setGraphic(imageView);
            setTooltip(new Tooltip(item));
            setAlignment(Pos.CENTER);
            setPadding(INSETS);
        }

    }
}
