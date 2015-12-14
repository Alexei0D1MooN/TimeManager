package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    AnchorPane mainPane;
    AnchorPane addPane;
    AnchorPane errorPane;

    MainSceneController mainController;
    AddEventSceneController addController;
    ErrorSceneController errorController;

    Stage mainStage;
    Stage addStage;
    Stage errorStage;

    // Display error
    public void closeError(){
        errorStage.hide();
    }
    public void displayError(String text){
        errorController.setText(text);
        errorStage.show();
        errorStage.setAlwaysOnTop(true);
    }

    // Display add
    public void closeAdd(){
        addStage.close();
    }
    public void displayAdd(){
        addController.datePicker.setValue(mainController.datePicker.getValue());
        addController.descTextArea.setText("");
        addController.timeTextField.setText("");
        addController.applyButton.setText("Add");
        addController.setMode(0, null, null, null, null);
        addStage.show();
        addStage.setAlwaysOnTop(true);
    }
    public void addEvent(String time, String description){
        mainController.AddItem(time, description);
    }
    public void insertEvent(String time, String description){
        mainController.InsertItem(time, description);
    }
    public void editEvent(Object item, String prevTime, String time, String description){

    }

    public void displayEdit(LocalDate date, String time, String description, Object item){
        addController.datePicker.setValue(date);
        addController.descTextArea.setText(description);
        addController.timeTextField.setText(time);
        addController.setMode(1, time, description, date, item);
        addController.applyButton.setText("Apply");
        addStage.show();
        addStage.setAlwaysOnTop(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        mainStage = primaryStage;

        EventController.LoadBase();

        InitFXML();

        InitStages();

    }

    private void InitFXML() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainScene.fxml"));
        mainPane = (AnchorPane) loader.load();
        mainController = loader.getController();
        mainController.mainApplication = this;

        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("addEventScene.fxml"));
        addPane = (AnchorPane) loader2.load();
        addController = loader2.getController();
        addController.mainApplication = this;

        FXMLLoader loader3 = new FXMLLoader();
        loader3.setLocation(getClass().getResource("errorScene.fxml"));
        errorPane = (AnchorPane) loader3.load();
        errorController = loader3.getController();
        errorController.mainApplication = this;

    }

    private void InitStages(){

        // Add stage
        addStage = new Stage();
        addStage.setTitle("Add event");
        addStage.setScene(new Scene(addPane));
        addStage.getIcons().add(new Image("resources/mainIcon.png"));
        addStage.initStyle(StageStyle.UTILITY);
        addStage.setResizable(false);

        // Error stage
        errorStage = new Stage();
        errorStage.setTitle("Sad face :(");
        errorStage.setScene(new Scene(errorPane));
        errorStage.getIcons().add(new Image("resources/mainIcon.png"));
        errorStage.initStyle(StageStyle.UTILITY);
        errorStage.setResizable(false);

        // Main stage
        mainStage.setTitle("Event dispatcher");
        mainStage.setScene(new Scene(mainPane));
        mainStage.initStyle(StageStyle.UTILITY);
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image("resources/mainIcon.png"));
        mainStage.show();

        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                EventController.SaveBase();
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
