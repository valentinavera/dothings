package edu.unicauca.main.models;

import java.util.Map;

public interface  IConnection{
    void connect();
    boolean push(String entity, Map<String, Object> data);
    void linkModel( final String entity, final Model model);

}