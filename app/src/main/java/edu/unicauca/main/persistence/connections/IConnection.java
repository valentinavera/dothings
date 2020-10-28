package edu.unicauca.main.persistence.connections;

import java.util.Map;

import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.Model;

public interface  IConnection{
    void connect();
   // boolean push(ModelManager manager, Map<String, Object> data);
    boolean update(ModelManager manager, Map<String, Object> data);
    boolean create(ModelManager manager, Map<String, Object> data);
    void linkModelManager(  final ModelManager m);


}