package edu.unicauca.main.persistence.managers;

import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;

public class TaskModelManager extends  ModelManager<TaskModel>{
    public TaskModelManager(Model m, IConnection db) {
        super(m, db);
    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        TaskModel taskModel = new TaskModel((String) data.get("name"),
                (String) data.get("description"),
                (String) data.get("state"));
        taskModel.setKey((String) data.get("_id"));
        return taskModel;
    }

}
