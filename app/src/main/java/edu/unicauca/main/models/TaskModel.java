package edu.unicauca.main.models;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;

@IgnoreExtraProperties
public  class TaskModel extends Model<TaskModel> {
    private static  TaskModel taskModelObject;
    private static String entityName = "Task";
    private    IConnection db ;
    private  String name;
    private  String description;
    public  TaskModel(){
        //db.linkModel(entityName,this);
    }


    public static  TaskModel getTaskConnection(Observer o, Context context){
        if(taskModelObject == null){
            taskModelObject = new TaskModel();
            if(o != null)  taskModelObject.addObserver(o);
           // taskModelObject.db  = new FirebaseConnection();
            taskModelObject.db  = new SqliteConnection(context);
            //taskModelObject.db  = new MongoDBConnection();
            taskModelObject.db.linkModel(entityName,taskModelObject);
        }else  if(o != null)  taskModelObject.addObserver(o);
        //taskModelObject.notify_observers();
            return taskModelObject;
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


    public boolean  create(String name, String description) {
        Map<String, Object> task = new HashMap<>();
        task.put("name",name);
        task.put("description", description);
        boolean push = db.push(this,entityName, task);
        if(push){
            this.notify_observers();
        }
        return push;
    }
    @Override
    public Model createModel(Map<String, Object> data) {
        TaskModel taskModel = new TaskModel();
        taskModel.description =(String) data.get("description");
        taskModel.name =(String) data.get("name");
        return taskModel;
    }
    @Override
    public Map<String, Class> getColumtypes() {
        Map<String, Class> task = new HashMap<>();
        task.put("name",String.class);
        task.put("description", String.class);
        return  task;

    }

}