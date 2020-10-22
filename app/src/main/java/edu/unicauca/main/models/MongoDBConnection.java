package edu.unicauca.main.models;

import com.google.android.gms.common.util.BiConsumer;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
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
    private MongoDatabase db;
    @Override
    public void connect() {
        if(mongo!=null)
            return;
        try{
            mongo = new MongoClient("localhost",27017);
        }catch (UnknownError e){
            e.printStackTrace();
        }
        if(db == null) {
            db = mongo.getDatabase("dothings");
        
        }
    }

    @Override
    public boolean push(String entity, Map<String, Object> data) {
        connect();
        try {
            db.createCollection(entity);
            MongoCollection<Document> collection = db.getCollection(entity);
            Document document = new Document(data);
            collection.insertOne(document);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void linkModel(String entity, Model model) {
        connect();
        FindIterable<Document> documents = db.getCollection(entity).find();

        for (Document doc : documents) {
            Map<String, Object> data = new HashMap<>();
            for( String key : doc.keySet()){
                Object o = doc.get(key);
                data.put(key,o);
            }
            model.addToCache(data);
        }
        model.notify_observers();

    }
}