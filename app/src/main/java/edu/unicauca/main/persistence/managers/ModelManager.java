package edu.unicauca.main.persistence.managers;


import android.content.Context;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.persistence.connections.FirebaseConnection;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.connections.SqliteConnection;
import edu.unicauca.main.persistence.models.Model;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;

public  abstract class ModelManager<T> extends Observed {
    public   static int REMOTE_MODE =1 ;
    public   static int LOCAL_MODE = 0  ;
    private List<T> cacheList;
    protected   static IConnection localDb ;
    protected   static IConnection remoteDb ;
    private  Class modelClass;
    private String entityName;


    public ModelManager(Class modelClass){

        cacheList = new ArrayList<>();
        this.modelClass = modelClass;
      //  db.linkModelManager(this);
    }

    public boolean createConnectionWithDB(Context c){
        boolean result = false;
        if(remoteDb == null){
            result = true;
            remoteDb = new FirebaseConnection();
        }
        if(localDb == null){
            result = true;
            localDb = new SqliteConnection(c);
        }
        return  result;
    }
    private  IConnection selectDatabase(){
        UserModel u = SimpleSessionManager.getLoginUser();
        if (u.isAuthenticated()) {
            return remoteDb;
        } else {
            return localDb;
        }
    }
    private  IConnection selectDatabase(int opc){
        if (opc == REMOTE_MODE) {
            return remoteDb;
        } else {
            return localDb;
        }
    }
    public   void addToCache(T e){
        cacheList.add(e);

    }
    public void clearCache(){
        cacheList.clear();

    }
    public  void link(int database_mode){
        IConnection db = selectDatabase(database_mode);
        db.linkModelManager(this);
    }
    public  void link(){
        IConnection db = selectDatabase();
        db.linkModelManager(this);
    }
    public  List<T> getAll(){
        return cacheList;
    }
    public  IConnection getLocalDb(){
        return localDb;
    }
    public  IConnection getRemoteDb(){
        return remoteDb;
    }

    public abstract Model makeModel(Map<String, Object> data);

    public  Class getModelClass(){
        return modelClass;
    }

    public  boolean update(Map<String, Object> data,int database_mode) {
        IConnection db = selectDatabase(database_mode);
        return db.update(this, data);
    }
    public  boolean create(Map<String, Object> data,int database_mode){
        IConnection db = selectDatabase(database_mode);
        return db.create(this, data);
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    public abstract Map<String, Class> getColumnTypes();



    public List<Model> filter(Map<String, Object> fitlerFields,int database_option) {
        IConnection db = selectDatabase(database_option);
        return db.filter(this,fitlerFields);
    }
    public List<Model> filter(Map<String, Object> fitlerFields) {
        IConnection db = selectDatabase();
        return db.filter(this,fitlerFields);
    }
}
