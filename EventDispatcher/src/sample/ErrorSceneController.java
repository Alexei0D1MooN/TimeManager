package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by ENVY on 20.11.15.
 */
public class ErrorSceneController {

    public Main mainApplication;

    public void setText(String text){
        errorField.setText(text);
    }

    @FXML
    Label errorField;

    @FXML
    Button betterButton;

    @FXML
    void initialize(){


        betterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainApplication.closeError();
            }
        });

    }

}
