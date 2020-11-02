package edu.unicauca.main.persistence.models;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.FirebaseConnection;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.connections.SqliteConnection;
import edu.unicauca.main.persistence.managers.TaskModelManager;
import edu.unicauca.main.persistence.managers.UserModelManager;

public class UserModel extends Model<UserModel> {
    private static  UserModel userModelObject;
    private String name;
    private String lastname;
    private String username;
    private String password;
    public  UserModel(){
        //db.linkModel(entityName,this);
        if(this.objects==null) {
            this.setEntityName("User");
            // taskModelObject.db  = new SqliteConnection(context);
            IConnection c = new FirebaseConnection ();
            //IConnection c = new SqliteConnection(context);
            objects = new UserModelManager (this,c);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }
    public  UserModel(Context context){
        //db.linkModel(entityName,this);
        this.setEntityName("User");
        if(this.objects==null) {
            // taskModelObject.db  = new SqliteConnection(context);
            //IConnection c = new FirebaseConnection();
            IConnection c = new SqliteConnection (context);
            objects = new UserModelManager (this,c);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }

    public UserModel(String name, String lastname, String username, String password) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public boolean  save() {
        Map<String, Object> user= new HashMap<> ();
        user.put("name",name);
        user.put("lastname", lastname);
        user.put("username", username);
        user.put("password", password);
        boolean result;
        if(this.getKey() == null) {// save
            result = objects.create( user);
        }
        else {
            user.put("key", getKey ());
            result = objects.update(user);
        }


        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
