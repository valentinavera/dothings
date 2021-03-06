package edu.unicauca.main.persistence.connections;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.Model;

 public class FirebaseConnection implements  IConnection{
    private static DatabaseReference db;
    public FirebaseConnection(){
        connect();
    }
    public   void connect(){
        if(db == null){
            db =  FirebaseDatabase.getInstance().getReference();
            db.keepSynced(true);
        }
    }
    @Override
    public    boolean create
            (ModelManager manager, Map<String, Object> data){
        connect();
        String entity = manager.getEntityName();
        try {
            db.child(entity).push().setValue(data);
            return true;
        }catch (Exception e){
            Log.e("Push "+entity+": ",e.toString());
            return false;
        }
    }

     @Override
     public boolean delete(ModelManager manager, String key) {
         connect();
         String entity = manager.getEntityName();
         try {

             db.child(entity).child(key).removeValue();

             return true;
         }catch (Exception e){
             Log.e("Push "+entity+": ",e.toString());
             return false;
         }
     }

     @Override
    public boolean update(ModelManager manager, Map<String, Object> data) {
        connect();
        String entity = manager.getEntityName();
        try {
            String key = (String) data.remove("key");
            db.child(entity).child (key).updateChildren (data);
            String valor = key;
            return true;
        }catch (Exception e){
            Log.e("Push "+entity+": ",e.toString());
            return false;
        }
    }
    @Override
    public void linkModelManager(  final ModelManager manager) {
        connect();
        String entity = manager.getEntityName();
        db.child(entity).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                manager.clearCache();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Object o = snapshot.getValue(T);

                  Map<String,Object>  data= (Map<String, Object>) ds.getValue();

                    Model o = manager.makeModel(data);
                    o.setKey(ds.getKey());
                    manager.addToCache(o);

                }
                manager.notify_observers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

     private void linkModelManager(final ModelManager manager, final boolean notify) {
         connect();
         String entity = manager.getEntityName();
         db.child(entity).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 manager.clearCache();
                 for (DataSnapshot ds : snapshot.getChildren()) {
                     //Object o = snapshot.getValue(T);

                     Map<String,Object>  data= (Map<String, Object>) ds.getValue();

                     Model o = manager.makeModel(data);
                     o.setKey(ds.getKey());
                     manager.addToCache(o);

                 }
                 if(notify) manager.notify_observers();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }

         });
     }
     @Override
     public List<Model> filter(ModelManager manager, Map<String, Object> fitlerFields) {
        // linkModelManager(manager,false);
         List<Model> filters = new ArrayList<>();
         List<Model> all = manager.getAll();
         for(Model m : all){
             if(m.validate(fitlerFields))
                 filters.add(m);

         }
         return filters;
     }


 }