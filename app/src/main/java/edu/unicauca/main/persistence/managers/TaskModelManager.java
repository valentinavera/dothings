package edu.unicauca.main.persistence.managers;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;

public class TaskModelManager extends  ModelManager<TaskModel>{
    public TaskModelManager( ) {
        super(TaskModel.class);
        setEntityName("Task");

    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        TaskModel taskModel = new TaskModel((String) data.get("name"),(String) data.get("description"),(long)data.get("time"));
        if(data.containsKey("key"))  taskModel.setKey((String) data.get("key"));
        else if(data.containsKey("_id")) taskModel.setKey((String) data.get("_id"));
        return taskModel;
    }


    @Override
    public Map<String, Class> getColumnTypes() {
        Map<String, Class> task = new HashMap<>();
        task.put("description",String.class);
        task.put("name",String.class);
        task.put("key", String.class);
        task.put("time", long.class);
        return  task;
    }
}
