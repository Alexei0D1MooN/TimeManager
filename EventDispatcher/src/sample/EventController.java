package sample;

import javafx.util.Pair;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ENVY on 20.11.15.
 */
public class EventController {


    public static class EventInfo implements Serializable
    {

        public EventInfo(String Time, String Description){
            this.Time = Time;
            this.Description = Description;
        }

        public void WriteToStream(RandomAccessFile file){
            try
            {
                file.writeBytes(Time);
                file.writeBytes(Description);
            }
            catch(Exception e){}
        }

        public boolean Equal(EventInfo info){
            return Time == info.Time && Description == info.Description;
        }

        public String Time;
        public String Description;
    }

    // Map stores current event state
    public static HashMap<LocalDate, List<EventInfo> > _eventMap = new HashMap<LocalDate, List<EventInfo>>();

    public static void LoadBase() {
        //
        try
        {

            FileInputStream stream = new FileInputStream("eventDate.bsd");
            ObjectInputStream objStream = new ObjectInputStream(stream);
            _eventMap = (HashMap<LocalDate, List<EventInfo> >)objStream.readObject();
            stream.close();

        }
        catch (Exception e){}

    }

    public static void SaveBase() {
        //
        try
        {

            FileOutputStream stream = new FileOutputStream("eventDate.bsd");
            ObjectOutputStream objStream = new ObjectOutputStream(stream);
            objStream.writeObject(_eventMap);
            stream.close();

        }
        catch (Exception e){

        }
    }

    public static void AddEvent(LocalDate date, String time, String description){
        //
        List<EventInfo> list = _eventMap.get(date);
        // No items
        if( list == null ){
            list = new LinkedList<EventInfo>();
            list.add(new EventInfo(time, description));
             _eventMap.put(date, list);

        }
        else
        {
            list.add(new EventInfo(time, description));
        }
    }

    public static void EditEvent(LocalDate prevDate, String prevTime, String prevDescription, LocalDate date, String time, String description){
        //
        RemoveEvent(prevDate, prevTime, prevDescription);
        AddEvent(date, time, description);
    }

    public static void RemoveEvent(LocalDate date, String time, String description){
        //
        List<EventInfo> list = _eventMap.get(date);
        // No items
        if( list == null ){
            // No such item in the map
        }
        else
        {
           // list.remove(new EventInfo(time, description));
            for(int i = 0; i < list.size(); i++){
                if(list.get(i).Equal(new EventInfo(time, description))){
                    list.remove(i);
                    return;
                }
            }
        }
    }

}
