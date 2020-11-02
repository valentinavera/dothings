package edu.unicauca.main.persistence.managers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;

public class UserModelManager extends  ModelManager<UserModel>{
    public UserModelManager(Model m ) {
        super(m);
        setEntityName("User");

    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        UserModel userModel = new UserModel((String) data.get("name"),(String) data.get("lastname"),(String) data.get("username"),(String) data.get("password"));
        userModel.setKey((String) data.get("_id"));
        return userModel;
    }


    @Override
    public Map<String, Class> getColumnTypes() {
        Map<String, Class> task = new HashMap<> ();
        task.put("description",String.class);
        task.put("name",String.class);
        task.put("key", String.class);
        task.put("date", Date.class);
        return  task;
    }
}
