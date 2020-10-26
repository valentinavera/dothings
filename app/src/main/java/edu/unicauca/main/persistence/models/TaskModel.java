package edu.unicauca.main.persistence.models;

import android.content.Context;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.FirebaseConnection;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.connections.SqliteConnection;
import edu.unicauca.main.persistence.managers.TaskModelManager;

@IgnoreExtraProperties
public  class TaskModel extends Model<TaskModel> {
    private static  TaskModel taskModelObject;
    private  String name;
    private  String description;
    public  TaskModel(){
        //db.linkModel(entityName,this);
        if(this.objects==null) {
            this.setEntityName("Task");
            // taskModelObject.db  = new SqliteConnection(context);
            IConnection c = new FirebaseConnection();
            //IConnection c = new SqliteConnection(context);
            objects = new TaskModelManager(this,c);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }
    public  TaskModel(Context context){
        //db.linkModel(entityName,this);
        this.setEntityName("Task");
        if(this.objects==null) {
            // taskModelObject.db  = new SqliteConnection(context);
            //IConnection c = new FirebaseConnection();
            IConnection c = new SqliteConnection(context);
            objects = new TaskModelManager(this,c);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }
    public  TaskModel(String name, String description){
        //db.linkModel(entityName,this);
        this();
        this.name = name;
        this.description = description;


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
        boolean result;
        if(this.getKey() == null) {// save
                result = objects.create( task);

        }
        else {
          //  task.put("key", key);
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
}