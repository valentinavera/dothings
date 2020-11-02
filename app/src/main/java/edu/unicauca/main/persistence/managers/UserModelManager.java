package edu.unicauca.main.persistence.managers;

import java.util.Map;

import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.UserModel;

public class UserModelManager extends ModelManager<UserModel> {
    public UserModelManager(Model m, IConnection db) {
        super(m, db);
    }
    @Override
    public Model makeModel(Map<String, Object> data) {
        UserModel userModel = new UserModel((String) data.get("name"),
                (String) data.get("lastname"),
                (String) data.get("username"),
                (String) data.get("password"));
        userModel.setKey((String) data.get("_id"));
        return userModel;
    }
}
