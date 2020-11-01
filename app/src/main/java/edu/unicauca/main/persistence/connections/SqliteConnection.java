package edu.unicauca.main.persistence.connections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import edu.unicauca.main.persistence.managers.ModelManager;
import edu.unicauca.main.persistence.models.Model;

class  SqliteConnectionHelper extends SQLiteOpenHelper {

    public SqliteConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
        //tasks
        String ddlTask = "create table if not EXISTS Task ( _id integer primary key autoincrement, name varchar(20),description varchar(100), state integer)";
        //String ddlTask = "create table Task ( _id integer primary key autoincrement, name varchar(20),description varchar(100))";
        db.execSQL(ddlTask);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public  int insert(String entity, ContentValues values){
        try {
            SQLiteDatabase wdb = this.getWritableDatabase();
            long insert = wdb.insert(entity, null, values);
            wdb.close();
            return (int) insert;

        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }
    public  void update(String entity, String key,ContentValues values){
        try {
            String whereClausule = "_id = ?";
            SQLiteDatabase wdb = this.getWritableDatabase();
            wdb.update(entity,values,whereClausule,new String[]{key});
            wdb.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public Cursor getAllData(String entity){

        SQLiteDatabase rdb = getReadableDatabase();
        Cursor cursor = rdb.rawQuery("SELECT * FROM " + entity, null);
        //rdb.close();

        return cursor;
    }
}

public  class SqliteConnection implements IConnection {

    //private   SQLiteDatabase db;
    private  SqliteConnectionHelper db;
    private  Context context;
    public SqliteConnection(Context c) {
        this.context = c;
    }
    @Override
    public void connect() {
        if(db == null) {
            try {
                db = new SqliteConnectionHelper(this.context,"dothings",null,1);

               // db = SQLiteDatabase.openOrCreateDatabase("dothings.db", null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean create(ModelManager manager, Map<String, Object> data) {
        connect();
        String entity = manager.getModel().getEntityName();
        ContentValues values = new ContentValues( );
        for (Map.Entry<String,Object> i:data.entrySet()) {
            values.put(i.getKey(),i.getValue().toString());

        }
        int id = db.insert(entity, values);
        if(id == -1)
            return false;
        //m.setKey(String.valueOf(id));
        this.linkModelManager(manager);
        return true;

    }

    @Override
    public boolean update(ModelManager manager, Map<String, Object> data) {
        connect();
        String entity = manager.getModel().getEntityName();
        String key = (String) data.remove("key");
        ContentValues values = new ContentValues( );
        for (Map.Entry<String,Object> i:data.entrySet()) {
            values.put(i.getKey(),i.getValue().toString());

        }
        db.update(entity,key,values);
        this.linkModelManager(manager);
        return true;

    }

    @Override
    public void linkModelManager( ModelManager manager) {
        connect();
        manager.clearCache();
        String entity = manager.getModel().getEntityName();
        Cursor cursor = db.getAllData(entity);
        Map<String, Object> data = new HashMap<>();
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            manager.notify_observers();
            return;
        }
        do {
            int i = 0;
            String[] columnNames = cursor.getColumnNames();
            for(String column: columnNames) {
                String field = cursor.getString(i);
                data.put(column,field);
                i++;
            }
            manager.addToCache(manager.makeModel(data));
        } while (cursor.moveToNext());



        manager.notify_observers();
    }
}