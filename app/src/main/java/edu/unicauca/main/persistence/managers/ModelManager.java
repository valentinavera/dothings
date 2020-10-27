package edu.unicauca.main.persistence.managers;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.persistence.connections.IConnection;
import edu.unicauca.main.persistence.models.Model;

public  abstract class ModelManager<T> extends Observed {
    private List<T> cacheList;
    protected   static IConnection db ;
    private  Model model;


    public ModelManager(Model m, IConnection db){

        this.db = db;
        cacheList = new ArrayList<>();
        model = m;
      //  db.linkModelManager(this);
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
    public abstract Model makeModel(Map<String, Object> data);

    public  Model getModel(){
        return model;
    }

    public  boolean update(Map<String, Object> data){
        return db.update(this, data);
    }

    public  boolean create(Map<String, Object> data){
        return db.create(this, data);
    }
}
