package edu.unicauca.main.persistence.models;

import android.content.Context;

import com.google.firebase.database.IgnoreExtraProperties;

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

    private  String name;
    private  String description;
    private long timeDate;
    private   static ModelManager objects ;
    private String state;
    public  TaskModel(){
        //db.linkModel(entityName,this);
        if(this.objects==null) {
            // taskModelObject.db  = new SqliteConnection(context);

            //IConnection c = new SqliteConnection(context);
            objects = new TaskModelManager(TaskModel.class);
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
            objects = new TaskModelManager(TaskModel.class);
            objects.createConnectionWithDB(context);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }
    public  TaskModel(String name, String description,long date, String state){
        //db.linkModel(entityName,this);
        //this();
        this.name = name;
        this.description = description;
        this.timeDate = date;
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", date='" + timeDate + '\'' +
                '}';
    }



    public boolean  save() {
        Map<String, Object> task = new HashMap<>();
        task.put("name",name);
        task.put("description", description);
        task.put("state",state);
        if(timeDate != 0)task.put("time",timeDate);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String des){ this.description = des;}

    public Date getDate() {
            return new Date(timeDate);

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimeDate() {
        return timeDate;
    }

    public void setTime(long timeDate) {
        this.timeDate = timeDate;
    }

    public ModelManager getManager(){
        return objects;
    }
}