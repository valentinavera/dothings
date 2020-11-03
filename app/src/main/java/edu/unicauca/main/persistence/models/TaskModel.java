package edu.unicauca.main.persistence.models;

import android.content.Context;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.managers.TaskModelManager;
import edu.unicauca.main.session.SimpleSessionManager;

@IgnoreExtraProperties
public  class TaskModel extends Model<TaskModel> {

    private  String name;
    private  String description;
    private long time;
    private   static ModelManager objects;
    private String state;
    private int sync=2;

    public  TaskModel(Context context){
        //db.linkModel(entityName,this);
        if(this.objects==null) {

            objects = new TaskModelManager();
            objects.createConnectionWithDB(context);
            objects.link();


        }
    }
    public  TaskModel(String name, String description,long date){
        //db.linkModel(entityName,this);
        //this();
        this.name = name;
        this.description = description;
        this.time = date;


    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }



    public boolean  save() {
        Map<String, Object> task = new HashMap<>();
        int DATA_BASE_MODE ;
        UserModel u = SimpleSessionManager.getLoginUser();
        if(u.isAuthenticated()) {//seleccionar base
            DATA_BASE_MODE = ModelManager.REMOTE_MODE;
            task.put("userid", u.getUuid());

        }
        else{
            DATA_BASE_MODE = ModelManager.LOCAL_MODE;

        }

        task.put("name",name);
        task.put("description", description);
        if(time != 0)task.put("time",time);
        boolean result;
        if(this.getKey() == null) {// save
                result = objects.create( task,DATA_BASE_MODE);
        }
        else {
                task.put("key", getKey ());
                result = objects.update(task,DATA_BASE_MODE);
        }


        return result;
    }
    public boolean saveLocal() {
        Map<String, Object> task = new HashMap<>();
        int DATA_BASE_MODE= ModelManager.LOCAL_MODE ;

        task.put("name",name);
        task.put("description", description);
        task.put("sync",sync);
        if(time != 0)task.put("time",time);
        boolean result;
        if(this.getKey() == null) {// save
            result = objects.create( task,DATA_BASE_MODE);
        }
        else {
            task.put("key", getKey ());
            result = objects.update(task,DATA_BASE_MODE);
        }


        return result;
    }
    public boolean saveRemote(String uid) {
        Map<String, Object> task = new HashMap<>();
        int DATA_BASE_MODE ;
        UserModel u = SimpleSessionManager.getLoginUser();
        task.put("userid", uid);
        if(u.isAuthenticated()) {//seleccionar base
            DATA_BASE_MODE = ModelManager.REMOTE_MODE;
        }
        else{
            DATA_BASE_MODE = ModelManager.LOCAL_MODE;
        }

        task.put("name",name);
        task.put("description", description);
        if(time != 0)task.put("time",time);
        boolean result;

        //task.put("key", getKey ());
        result = objects.create(task,DATA_BASE_MODE);



        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String des){ this.description = des;}

    public Date getDate() {
            return new Date(time);

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public ModelManager getManager(){
        return objects;
    }

    @Override
    public boolean validate(Map<String, Object> fitlerFields) {
        return false;
    }
    public void setSync(int sync) {
        this.sync = sync;

    }
    public  boolean isSynchronized(){
        return sync ==1;
    }


}