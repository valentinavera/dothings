package edu.unicauca.main.persistence.models;

import android.content.Context;

import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.connections.FirebaseConnection;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.connections.SqliteConnection;
import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.managers.TaskModelManager;
import edu.unicauca.main.persistence.managers.UserModelManager;

public class UserModel extends Model<UserModel> {
    private   static ModelManager objects ;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private boolean isAuthenticated;

    public  UserModel(Context context){
        //db.linkModel(entityName,this);

        if(this.objects==null) {
            // taskModelObject.db  = new SqliteConnection(context);
            //IConnection c = new FirebaseConnection();
            objects = new UserModelManager();
            objects.createConnectionWithDB(context);
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();

        }
    }

    public UserModel(String name, String lastname, String username, String password) {
        this.name = name;
        this.lastname = lastname;
        this.email = username;
        this.password = password;
    }

    public UserModel(String username, String password) {

        this.email = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    private boolean save(int DATABAS_MODE){
        Map<String, Object> user= new HashMap<> ();
        user.put("name",name);
        user.put("lastname", lastname);
        user.put("email", email);
        user.put("password", password);
        boolean result;
        if(this.getKey() == null) {// save
            result = objects.create( user, DATABAS_MODE);
        }
        else {
            user.put("key", getKey ());
            result = objects.update(user,DATABAS_MODE);
        }


        return result;
    }
    public boolean  save() {
      return save(ModelManager.REMOTE_MODE);
    }

    public boolean saveLocal() {
        return save(ModelManager.LOCAL_MODE);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public ModelManager getManager(){
        return objects;
    }
    public  boolean isAuthenticated(){
        return isAuthenticated;

    }
    public  void  authenticate(){
        isAuthenticated= true;
    }


    public void unauthenticate() {
        isAuthenticated= false;
    }

    @Override
    public boolean validate(Map<String, Object> fitlerFields) {
        for(Map.Entry<String,Object> filter: fitlerFields.entrySet()){
            Object value = filter.getValue();
            switch (filter.getKey()){
                case "name":
                    if(value.equals(name))
                        return false;
                    break;
                case "lastname":
                    if(value.equals(lastname))
                        return false;
                    break;
                case "email":
                    if(value.equals(email))
                        return false;
                    break;
                case "password":
                    if(value.equals(password))
                        return false;
                    break;
                default:
                    break;
            }

        }
        return  true;
    }
}
