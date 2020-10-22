package edu.unicauca.main.models;

import com.google.android.gms.common.util.BiConsumer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DBCollectionCountOptions;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

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
    public boolean push(String entity, Map<String, Object> data) {
        connect();
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
    public void linkModel(String entity, Model model) {
        connect();
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
}