package edu.unicauca.main.persistence.managers;
import android.view.Display;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;

public class TaskModelManager extends  ModelManager<TaskModel>{
    public TaskModelManager( ) {
        super(TaskModel.class);
        setEntityName("Task");

    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        try {
            TaskModel taskModel = new TaskModel((String) data.get("name"), (String) data.get("description"), (String) data.get("state"));
            if (data.containsKey("key")) taskModel.setKey((String) data.get("key"));
            else if (data.containsKey("_id")) taskModel.setKey((String) data.get("_id"));
            if (data.containsKey("sync")) taskModel.setSync((int) data.get("sync"));
            if(data.containsKey("hour")) taskModel.setHour((long) data.get("hour"));
            if(data.containsKey("time")) taskModel.setTimeDate((long) data.get("time"));
            if(data.containsKey("userid")) taskModel.setUser((String) data.get("userid"));
            return taskModel;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new TaskModel(null);
    }


    @Override
    public Map<String, Class> getColumnTypes() {
        Map<String, Class> task = new HashMap<>();
        task.put("description",String.class);
        task.put("name",String.class);
        task.put("key", String.class);
        task.put("time", long.class);
        task.put("hour", long.class);
        task.put("state", String.class);
        task.put("sync", int.class);
        task.put("userid", String.class);
        return  task;
    }
/*
    @Override
    public List<TaskModel> getAll(){
        Map<String,Object> fitlerFields = new HashMap<>();
        UserModel u = SimpleSessionManager.getLoginUser();
        if(u.isAuthenticated()) {
            fitlerFields.put("userid", u.getUuid());

            List<TaskModel> result =new ArrayList<>();
            for(TaskModel m :super.getAll() ){
                if(m.isFromUser()) {
                    result.add((TaskModel) m);
                }
            }
            return result;
        }
        return super.getAll();
    }

*/
}
