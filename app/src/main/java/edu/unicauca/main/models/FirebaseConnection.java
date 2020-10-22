package edu.unicauca.main.models;


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

import edu.unicauca.main.patterns.observer.Observed;

public  class FirebaseConnection implements  IConnection{
    private static DatabaseReference db;
    FirebaseConnection(){
        connect();
    }
    public   void connect(){
        if(db == null){
            db =  FirebaseDatabase.getInstance().getReference();
        }
    }
    public    boolean push(String entity, Map<String, Object> data){
        connect();
        try {
            db.child(entity).push().setValue(data);
            return true;
        }catch (Exception e){
            Log.e("Push "+entity+": ",e.toString());
            return false;
        }
    }
    public void linkModel( final String entity, final Model model) {
        connect();
        db.child(entity).addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren ()) {
                    //Object o = snapshot.getValue(T);
                    Object o  =ds.getValue(model.getClass());
                    model.addToCache(o);

                }
                model.notify_observers();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}