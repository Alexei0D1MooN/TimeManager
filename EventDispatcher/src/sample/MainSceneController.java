package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.*;

public class MainSceneController {

    public Main mainApplication;

    @FXML
    AnchorPane mainAnchorPane;

    @FXML
    ListView listView;

    @FXML
    ContextMenu contextMenu;

    @FXML
    MenuItem clearButton;

    @FXML
    MenuItem addButton;

    @FXML
    MenuItem removeButton;

    @FXML
    MenuItem editButton;

    @FXML
    DatePicker datePicker;


    public class TempClass
    {
        String Time;
        String Description;

        TempClass(String time, String description){
            Time = time;
            Description = description;
        }

    }

    HashMap<Object, TempClass> itemMap = new HashMap<Object, TempClass>();

    void AddItem(String time, String description){
        listView.getItems().add("[" + time + "] - " + description);
        Object item = listView.getItems().get(listView.getItems().size() - 1);
        itemMap.put(item, new TempClass(time, description));
    }

    void InsertItem(String time, String description){
        AddItem(time, description);
        listView.getItems().sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((String)o1).compareTo((String) o2);
            }
        });
    }

    @FXML
    void initialize(){

        //
        // Defines for event list
        //
        removeButton.setDisable(true);
        editButton.setDisable(true);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                // Selected
                if (listView.getSelectionModel().getSelectedItem() == null) {
                    removeButton.setDisable(true);
                    editButton.setDisable(true);
                } else {
                    removeButton.setDisable(false);
                    editButton.setDisable(false);
                }

                //
                if (listView.getItems().size() != 0){
                    clearButton.setDisable(false);
                }
                else
                {
                    clearButton.setDisable(true);
                }
            }
        }
        );

        //
        // Defines for date picker
        //
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                listView.getItems().clear();

                List<EventController.EventInfo> list = EventController._eventMap.get(datePicker.getValue());
                if (list == null) {
                    // No items
                } else {

                    // Sorting by time
                    TreeMap<String, String> sortedMap = new TreeMap<String, String>();
                    for (EventController.EventInfo info : list) {
                        sortedMap.put(info.Time, info.Description);
                    }

                    for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                        mainApplication.addEvent(entry.getKey(), entry.getValue());
                    }
                }

            }
        });

        //
        // Defines for clear button
        //
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<EventController.EventInfo> list = EventController._eventMap.get(datePicker.getValue());
                if (list == null) {
                    // No items
                }
                else
                {
                    listView.getItems().clear();
                    list.clear();
                }
            }
        });

        //
        // Defines for add button
        //
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainApplication.displayAdd();
            }
        });

        //
        // Defines for remove button
        //
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Not null
                TempClass selectedTemp = itemMap.get(listView.getSelectionModel().getSelectedItem());
                if(listView.getSelectionModel().getSelectedItem() != null){
                    listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
                    EventController.RemoveEvent(datePicker.getValue(),
                            selectedTemp.Time,
                            selectedTemp.Description);
                }
            }
        });

        //
        // Defines for edit button
        //
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Not null
                Object selectedItem = listView.getSelectionModel().getSelectedItem();
                if(selectedItem != null){
                    TempClass temp = itemMap.get(selectedItem);
                    mainApplication.displayEdit(datePicker.getValue(), temp.Time, temp.Description, selectedItem);
                }
            }
        });

    }


}
