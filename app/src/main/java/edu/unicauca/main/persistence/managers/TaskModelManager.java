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
        setEntityName("Task");

    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        TaskModel taskModel = new TaskModel((String) data.get("name"),(String) data.get("description"),(Date)data.get("time"),(Date)data.get("date"),(String)data.get("state"));
        taskModel.setKey((String) data.get("_id"));
        return taskModel;
    }


    @Override
    public Map<String, Class> getColumnTypes() {
        Map<String, Class> task = new HashMap<> ();
        task.put("description",String.class);
        task.put("name",String.class);
        task.put("key", String.class);
        task.put("time", Date.class);
        task.put("date", Date.class);
        task.put("state", Date.class);
        return  task;
    }
}
