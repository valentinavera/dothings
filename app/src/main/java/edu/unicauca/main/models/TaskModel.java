package edu.unicauca.main.models;

import android.util.Log;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class TaskModel{
    private static String entityName = "Task";
    private  static  FirebaseConnection db = new FirebaseConnection();
    private  String name;
    private  String description;
    public  static boolean createTask(String name, String description){
        Map<String, Object> task = new HashMap<>();
        task.put("name",name);
        task.put("description", description);
        return db.push(entityName,task);
    }
    public static List<TaskModel>  allTasks(){
        List<TaskModel> tasks = new ArrayList<TaskModel>();
        for (Object t: db.getAllData(TaskModel.class,entityName)){
            TaskModel task = (TaskModel) t;
            tasks.add(task);
        }
        return tasks;
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
}