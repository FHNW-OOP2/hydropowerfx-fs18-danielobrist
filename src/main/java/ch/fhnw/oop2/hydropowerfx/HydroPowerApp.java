package ch.fhnw.oop2.hydropowerfx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.*;

import javafx.stage.WindowEvent;

import java.util.Optional;

import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import ch.fhnw.oop2.hydropowerfx.view.RootPanel;


public class HydroPowerApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(confirmCloseEventHandler);

        RootPM model = new RootPM();
        Parent rootPanel = new RootPanel(model);

        Scene scene = new Scene(rootPanel);

        primaryStage.titleProperty().bind(model.applicationTitleProperty());
        primaryStage.setScene(scene);

        primaryStage.setResizable(true);
        primaryStage.setWidth(1200);

        primaryStage.show();

        model.setSelectedPowerplantId(100100);


    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure?"
        );
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("");
        closeConfirmation.setTitle("");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(primaryStage);

        closeConfirmation.setX(primaryStage.getWidth() / 2);
        closeConfirmation.setY(primaryStage.getHeight() / 2);

        Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(closeResponse.get())) {
            event.consume();
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

}
