package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

/**
 * Created by ENVY on 20.11.15.
 */
public class AddEventSceneController {

    public Main mainApplication;

    @FXML
    AnchorPane mainPane;

    @FXML
    DatePicker datePicker;

    @FXML
    TextField timeTextField;

    @FXML
    TextArea descTextArea;

    @FXML
    Button applyButton;

    Object item;
    String prevTime;
    String prevDescription;
    LocalDate prevDate;
    int displayMode = 0;

    void setMode(int mode, String time, String description, LocalDate date, Object item){
        if(mode == 1){
            datePicker.setDisable(true);
        } else datePicker.setDisable(false);
        prevTime = time;
        displayMode = mode;
        prevDescription = description;
        prevDate = date;
        this.item = item;
    }

    @FXML
    void initialize(){

        //
        // Apply button defines
        //
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // Check date
                if( datePicker.getValue() == null){
                    mainApplication.displayError("I really think you should choose date first!)");
                }
                else
                {

                    // Check time field
                    if( !timeTextField.getText().matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$") ){
                        mainApplication.displayError("Time string is incorrect! Try to input it rightly :)");
                    }
                    else
                    {
                        // Add mode
                        if(displayMode == 0){
                            mainApplication.insertEvent(timeTextField.getText(), descTextArea.getText());
                            EventController.AddEvent(datePicker.getValue(), timeTextField.getText(), descTextArea.getText());
                            mainApplication.mainController.datePicker.setValue(datePicker.getValue());
                            mainApplication.closeAdd();
                        }
                        // Edit mode
                        else
                        {
                            EventController.EditEvent(prevDate, prevTime, prevDescription, datePicker.getValue(), timeTextField.getText(), descTextArea.getText());
                            mainApplication.mainController.listView.getItems().remove(item);
                            if(datePicker.getValue() == mainApplication.mainController.datePicker.getValue()){
                                mainApplication.insertEvent(timeTextField.getText(), descTextArea.getText());
                            }
                            mainApplication.mainController.datePicker.setValue(datePicker.getValue());
                            mainApplication.closeAdd();
                        }
                    }

                }
            }
        });

        //
        // Text field defines
        //
        timeTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Input validation
                switch(timeTextField.getText().length()){
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }

            }
        });

    }

}
