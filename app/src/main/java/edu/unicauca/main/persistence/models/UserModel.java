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
    private String uuid;
    private int isAuthenticated;//1= true, 2= false

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
    public  String getUuid(){
        return uuid;
    }
    public  void setUuid(String d){
        uuid = d;
    }
    private boolean save(int DATABAS_MODE){
        Map<String, Object> user= new HashMap<> ();
        if(name != null)user.put("name",name);
        if(lastname != null)user.put("lastname", lastname);
        if(email != null)user.put("email", email);
        if(password != null)user.put("password", password);
        if(uuid!=null) user.put("uuid", uuid);
        if(isAuthenticated==1) user.put("isAuthenticated", 1); else user.put("isAuthenticated", 2);

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

    public boolean saveLocal(boolean update) {
        Map<String, Object> user= new HashMap<> ();
        user.put("name",name);
        user.put("lastname", lastname);
        user.put("email", email);
        user.put("password", password);
        user.put("isAuthenticated", 1);
        user.put("uuid", uuid);


        boolean result;
        if(!update) {// save
            result = objects.create( user, ModelManager.LOCAL_MODE);
        }
        else {
            user.put("key", getKey ());
            result = objects.update(user,ModelManager.LOCAL_MODE);
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
        return isAuthenticated==1;

    }
    public  void  authenticate(){
        isAuthenticated= 1;
    }


    public void unauthenticate() {
        isAuthenticated= 2;
        Map<String, Object> user= new HashMap<> ();
        user.put("isAuthenticated", 2);
        user.put("key", getKey());
        objects.update(user,ModelManager.LOCAL_MODE);
    }

    @Override
    public boolean validate(Map<String, Object> fitlerFields) {
        for(Map.Entry<String,Object> filter: fitlerFields.entrySet()){
            Object value = filter.getValue();
            switch (filter.getKey()){
                case "name":
                    if(!value.equals(name))
                        return false;
                    break;
                case "lastname":
                    if(!value.equals(lastname))
                        return false;
                    break;
                case "email":
                    if(!value.equals(email))
                        return false;
                    break;
                case "password":
                    if(!value.equals(password))
                        return false;
                    break;
                case "isAuthenticated":
                    if(!value.equals(isAuthenticated))
                        return false;
                    break;
                case "uuid":
                    if(!value.equals(uuid))
                        return false;
                default:
                    break;
            }

        }
        return  true;
    }


}
