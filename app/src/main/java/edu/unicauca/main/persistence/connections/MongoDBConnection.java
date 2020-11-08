package edu.unicauca.main.persistence.connections;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.Model;

public  class MongoDBConnection implements IConnection{
    private  MongoClient mongo ;
    private DB db;
    @Override
    public void connect() {
        if(mongo!=null)
            return;
        try{
            //mongo = new MongoClient( "localhost:27017");
            mongo = new MongoClient( "localhost",27017);
        }catch (UnknownError e){
            e.printStackTrace();
        }
        if(mongo != null) {
            db = mongo.getDB("dothings");

        }
    }

    @Override
    public boolean create(ModelManager m, Map<String, Object> data) {
        connect();
        String entity = m.getEntityName();
        try {
            DBObject document = new BasicDBObject(data) ;
            if (!db.collectionExists(entity)){
                db.createCollection(entity,document);
            }else {
                DBCollection collection = db.getCollection(entity);
                collection.insert(document);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(ModelManager manager, String key) {
        return false;
    }

    @Override
    public boolean update(ModelManager m, Map<String, Object> data) {
        return false;
    }

    @Override
    public void linkModelManager( ModelManager model) {
        connect();
        String entity = model.getEntityName();
        if(!db.collectionExists(entity)){
            model.notify_observers();
            return;
        }
        DBCursor cursor = db.getCollection(entity).find();
        try {
            while (cursor.hasNext()) {
                Map<String, Object> data = new HashMap<>();
                DBObject doc = cursor.next();
                for( String key : doc.keySet()){
                    Object o = doc.get(key);
                    data.put(key,o);
                }
                model.addToCache(data);
            }
        } finally {
            cursor.close();
        }

        model.notify_observers();

    }

    @Override
    public List<Model> filter(ModelManager m, Map<String, Object> fitlerFields) {
        return null;
    }
}