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
    private Date date;
    private   static ModelManager objects ;
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
    public  TaskModel(String name, String description,Date date){
        //db.linkModel(entityName,this);
        //this();
        this.name = name;
        this.description = description;
        this.date = date;


    }



    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }



    public boolean  save() {
        Map<String, Object> task = new HashMap<>();
        task.put("name",name);
        task.put("description", description);
        task.put("date", date);
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
    public void setDescription(String des){ this.description = des;}

    public ModelManager getManager(){
        return objects;
    }
}