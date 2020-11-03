package edu.unicauca.main.persistence.managers;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.persistence.connections.FirebaseConnection;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.connections.SqliteConnection;
import edu.unicauca.main.persistence.models.Model;

public  abstract class ModelManager<T> extends Observed {
    private List<T> cacheList;
    protected   static IConnection db ;
    private  Class modelClass;
    private String entityName;


    public ModelManager(Class modelClass){

        cacheList = new ArrayList<>();
        this.modelClass = modelClass;
      //  db.linkModelManager(this);
    }
    public boolean createConnectionWithDB(){
        boolean result = false;
        if(db == null){
            result = true;
            db = new FirebaseConnection();
        }
        return result;
    }
    public boolean createConnectionWithDB(Context c){
        boolean result = false;
        if(db == null){
            result = true;
            db = new SqliteConnection(c);
        }
        return  result;
    }
    //public abstract boolean create();
    public   void addToCache(T e){
        cacheList.add(e);

    }
    public void clearCache(){
        cacheList.clear();

    }
    public  void link(){
        db.linkModelManager(this);
    }
    public  List<T> getAll(){
        return cacheList;
    }
    public  IConnection getDb(){
        return db;
    }
    public  IConnection setDb(){return db;}
    public abstract Model makeModel(Map<String, Object> data);

    public  Class getModelClass(){
        return modelClass;
    }

    public  boolean update(Map<String, Object> data){
        return db.update (this, data);
    }

    public  boolean create(Map<String, Object> data){
        return db.create(this, data);
    }


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    public abstract Map<String, Class> getColumnTypes();
}
