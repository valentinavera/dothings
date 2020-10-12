package edu.unicauca.main.models;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  class FirebaseConnection{
    private static DatabaseReference db;
    FirebaseConnection(){
        FirebaseConnection.connect();
    }
    public static  void connect(){
        if(db == null){
            db =  FirebaseDatabase.getInstance().getReference();
        }
    }
    public  static  boolean push(String entity, Map<String, Object> data){
        connect();
        try {
            db.child(entity).push().setValue(data);
            return true;
        }catch (Exception e){
            Log.e("Push "+entity+": ",e.toString());
            return false;
        }
    }
    public List<Object> getAllData(final Class<?> T , final String entity){
        connect();
        final List<Object> objects = new ArrayList<>();
        db.child(entity).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    db.child(entity);
                    db.child(snapshot.getKey());
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Object  o  =snapshot.getValue(T);
                            Log.e("object",o.toString());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return objects;
    }

}