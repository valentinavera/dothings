package edu.unicauca.main.persistence.models;

import android.content.Context;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private long timeDate;
    private static ModelManager objects ;
    private String state;
    private long hour;
    private ArrayList<String> dates = new ArrayList();
    private int sync=2;
    private  String userid;

   /* public  TaskModel() {
        //db.linkModel(entityName,this);
        if (this.objects == null) {
            // taskModelObject.db  = new SqliteConnection(context);

            //IConnection c = new SqliteConnection(context);
            objects = new TaskModelManager(TaskModel.class);
            objects.createConnectionWithDB();
            objects.link();
            //taskModelObject.db  = new MongoDBConnection();
        }
    }*/

    public  TaskModel(Context context){
        //db.linkModel(entityName,this);
        if(this.objects==null) {

            objects = new TaskModelManager();
            objects.createConnectionWithDB(context);
            objects.link();
        }
    }

    public  TaskModel(String name, String description, String state){
        //db.linkModel(entityName,this);
        //this();
        this.name = name;
        this.description = description;


        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time='" + timeDate + '\'' +
                ", hour='" + hour + '\'' +
                ", state='" + state + '\'' +
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
        task.put("state",state);
        task.put("time",timeDate);
        task.put ("hour",hour);
        //añadir a arrayList
        long pDateTask;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //pDateTask = dateTask.getTime ();
        String dateString = sdf.format(timeDate);
        dates.add (dateString);
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
        task.put("state",state);
        task.put("sync",sync);
        if(timeDate != 0)task.put("time",timeDate);
        if(hour != 0) task.put("hour", hour);
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
        task.put("state",state);
        if(timeDate != 0)task.put("time",timeDate);
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
            return new Date(timeDate);

    }

    public String getState() {
        return state;
    }

     public long getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(long timeDate) {
        this.timeDate = timeDate;
    }

    public void setState(String state) {
        this.state = state;
    }
    public long getHour() {
        return this.hour;
    }
    public void setHour(long timeTask) {
        this.hour = timeTask;
    }
    public ArrayList<String> getDates() {
        return dates;
    }
    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
    public ModelManager getManager(){
        return objects;
    }

    @Override
    public boolean validate(Map<String, Object> fitlerFields) {
        for (Map.Entry<String, Object> filter : fitlerFields.entrySet()) {
            Object value = filter.getValue();
            switch (filter.getKey()) {
                case "name":
                    if (!value.equals(name))
                        return false;
                    break;
                case "description":
                    if (!value.equals(description))
                        return false;
                    break;
                case "state":
                    if (!value.equals(state))
                        return false;
                    break;
                case "timeDate":
                    if (!value.equals(timeDate))
                        return false;
                    break;
                case "hour":
                    if (!value.equals(hour))
                        return false;
                    break;
                case "userid":
                    if (!value.equals(userid))
                        return false;
                    break;
                default:
                    break;
            }

        }
        return true;
    }
    public void setSync(int sync) {
        this.sync = sync;

    }
    public  boolean isSynchronized(){
        return sync ==1;
    }

    public boolean isFromUser() {
        try {
            return this.userid.equals(SimpleSessionManager.getLoginUser().getUuid());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public void setUser(String userid) {
        this.userid=  userid;
    }
}