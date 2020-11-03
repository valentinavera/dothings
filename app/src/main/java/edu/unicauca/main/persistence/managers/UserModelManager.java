package edu.unicauca.main.persistence.managers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;

public class UserModelManager extends  ModelManager<UserModel>{

    public UserModelManager( ) {
        super(UserModel.class);
        setEntityName("User");

    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        int isAutheticated = 0 ;

        UserModel userModel = new UserModel((String) data.get("name"),(String) data.get("lastname"),(String) data.get("email"),(String) data.get("password"));
        if(data.containsKey("_id"))  userModel.setKey((String) data.get("_id"));
        else if(data.containsKey("key"))  userModel.setKey((String) data.get("key"));
        if(data.containsKey("uuid"))  userModel.setUuid((String) data.get("uuid"));
        if(data.containsKey("isAuthenticated")){
            try {
                isAutheticated = (int) data.get("isAuthenticated");
            }catch (Exception e ){
                Long l = (Long) data.get("isAuthenticated");
                isAutheticated = l.intValue();
                e.printStackTrace();
            }
            if(isAutheticated==1) userModel.authenticate();
        }




        return userModel;
    }


    @Override
    public Map<String, Class> getColumnTypes() {
        Map<String, Class> task = new HashMap<> ();
        task.put("name",String.class);
        task.put("lastname",String.class);
        task.put("email",String.class);
        task.put("password",String.class);
        task.put("key", String.class);
        task.put("isAuthenticated", int.class);
        task.put("uuid", String.class);
        return  task;
    }

}
