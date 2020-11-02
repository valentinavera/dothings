package edu.unicauca.main.persistence.managers;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;

public class TaskModelManager extends  ModelManager<TaskModel>{
    public TaskModelManager(Model m ) {
        super(m);
        setEntityName("Task1");

    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        TaskModel taskModel = new TaskModel((String) data.get("name"),(String) data.get("description"),(Date)data.get("date"));
        taskModel.setKey((String) data.get("_id"));
        return taskModel;
    }


    @Override
    public Map<String, Class> getColumnTypes() {
        Map<String, Class> task = new HashMap<>();
        task.put("description",String.class);
        task.put("name",String.class);
        task.put("key", String.class);
        task.put("date", Date.class);
        return  task;
    }
}
