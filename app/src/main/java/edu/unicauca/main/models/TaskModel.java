package edu.unicauca.main.models;

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


    public static  TaskModel getTaskConnection(Observer o){
        if(taskModelObject == null){
            taskModelObject = new TaskModel();
            if(o != null)  taskModelObject.addObserver(o);
            //taskModelObject.db  = new FirebaseConnection();
            taskModelObject.db  = new MongoDBConnection();
            taskModelObject.create("asdasd","adasdas");
            taskModelObject.db.linkModel(entityName,taskModelObject);
        }else  if(o != null)  taskModelObject.addObserver(o);
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
    @Override
    public Model createModel(Map<String, Object> data) {
        TaskModel taskModel = new TaskModel();
        taskModel.description =(String) data.get("description");
        taskModel.name =(String) data.get("name");
        return taskModel;
    }

}