package edu.unicauca.main.models;

import android.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.patterns.observer.Observed;

public abstract  class Model<T> extends Observed {
    private List<T> cacheList;
    public Model(){
        cacheList = new ArrayList<>();
    }
    //public abstract boolean create();
    public   void addToCache(T e){
        cacheList.add(e);
    }
    public void clearCache(){
        cacheList.clear();
    }
    public  List<T> getAll(){
        return cacheList;
    }
    public abstract Model createModel(Map<String, Object> data);
}
