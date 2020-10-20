package edu.unicauca.main.models;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.patterns.observer.Observed;
@IgnoreExtraProperties
public  class TaskModel extends Model<TaskModel> {
    private static  TaskModel taskModelObject;
    private static String entityName = "Task";
    private  static  FirebaseConnection db = new FirebaseConnection();
    private  String name;
    private  String description;
    public  TaskModel(){
         db.linkModel(TaskModel.class,entityName,this);
    }
    public static  TaskModel getTaskConnection(){
        if(taskModelObject == null){
            taskModelObject = new TaskModel();
        }
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
        return db.push(entityName,task);
    }
}