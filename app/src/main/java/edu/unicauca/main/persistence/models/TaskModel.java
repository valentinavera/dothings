package edu.unicauca.main.persistence.models;

import android.content.Context;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.FirebaseConnection;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.connections.SqliteConnection;
import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.managers.TaskModelManager;

@IgnoreExtraProperties
public  class TaskModel extends Model<TaskModel> {
    private   static ModelManager objects ;
    private  String name;
    private  String description;
    private Date dateTask;
    private Date timeTask;
    private String state;
    private ArrayList<String> dates = new ArrayList();

    public  TaskModel(){
        //db.linkModel(entityName,this);
        if(this.objects==null) {
            // taskModelObject.db  = new SqliteConnection(context);

            //IConnection c = new SqliteConnection(context);
            objects = new TaskModelManager(this);
            objects.createConnectionWithDB();
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }
    public  TaskModel(Context context){
        //db.linkModel(entityName,this);
        if(this.objects==null) {
            // taskModelObject.db  = new SqliteConnection(context);
            //IConnection c = new FirebaseConnection();
            objects = new TaskModelManager(this);
            objects.createConnectionWithDB(context);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }
    public  TaskModel(String name, String description, Date timeTask, Date date, String state){
        //db.linkModel(entityName,this);
        this();
        this.name = name;
        this.description = description;
        this.timeTask= timeTask;
        this.dateTask = date;
        this.state = state;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {return this.description; }
    public String getState(){ return this.state; }
    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time='" + timeTask + '\'' +
                ", date='" + dateTask + '\'' +
                ", state='" + state + '\'' +

                '}';
    }
    public boolean  save() {
        Map<String, Object> task = new HashMap<>();
        task.put("name",name);
        task.put("description", description);
        task.put ("time",timeTask);
        task.put ("date",dateTask);
        task.put("state", state);
        //añadir a arrayList
        long pDateTask;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        pDateTask = dateTask.getTime ();
        String dateString = sdf.format(pDateTask);
        dates.add (dateString);
        boolean result;
        if(this.getKey() == null) {// save
            result = objects.create( task);
        }
        else {
            task.put("key", getKey ());
            result = objects.update(task);
        }


        return result;
    }
/*
    @Override
    public Map<String, Class> getColumtypes() {
        Map<String, Class> task = new HashMap<>();
        task.put("description",String.class);
        task.put("name",String.class);
        task.put("key", String.class);
        return  task;

    }
*/
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String des){ this.description = des;}
    public Date getDateTask() {
        return dateTask;
    }
    public void setDateTask(Date dateTask) {
        this.dateTask = dateTask;
    }
    public void setState(String state) { this.state = state; }
    public Date getTimeTask() {
        return timeTask;
    }
    public void setTimeTask(Date timeTask) {
        this.timeTask = timeTask;
    }
    public ArrayList<String> getDates() {
        return dates;
    }
    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
    public ModelManager getManager(){
        return objects;
    }


}