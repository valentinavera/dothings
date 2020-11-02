package edu.unicauca.main.persistence.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.persistence.managers.ModelManager;


public abstract  class Model<T>  {
    private List<T> cacheList;
    private String key;



    public Model(){
        cacheList = new ArrayList<>();
    }


    public  String getKey(){
        return this.key;
    }
    public  void setKey(String key ){
        this.key = key;
    }

}
