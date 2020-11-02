package edu.unicauca.main.persistence.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.persistence.managers.ModelManager;


public abstract  class Model<T>  {
    private List<T> cacheList;
    private  String entityName;
    private String key;
    protected   static ModelManager objects ;
    public Model(){
        cacheList = new ArrayList<>();
    }
    //public abstract boolean create();
    public  String getKey(){
        return this.key;
    }
    public  void setKey(String key ){
        this.key = key;
    }
    public String getEntityName() {
        return entityName;
    }
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    public ModelManager getManager(){
        return objects;
    }
}
