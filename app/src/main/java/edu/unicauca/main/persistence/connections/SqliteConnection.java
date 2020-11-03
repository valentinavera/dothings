package edu.unicauca.main.persistence.connections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        String ddlTask = "create table if not EXISTS Task ( _id integer primary key autoincrement, name varchar(20),description varchar(100),time integer )";
          String ddlUser = "create table if not EXISTS User ( _id integer primary key autoincrement, name varchar(20),lastname varchar(20), username varchar(20), password varchar(10) )";

        //String ddlTask = "create table Task ( _id integer primary key autoincrement, name varchar(20),description varchar(100))";
        db.execSQL(ddlTask);
        db.execSQL (ddlUser);
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
        Cursor cursor = null;
        try {
             cursor = rdb.rawQuery("SELECT * FROM " + entity, null);
        }catch (Exception e){
            e.printStackTrace();;
        }
        //rdb.close();

        return cursor;
    }
}

public  class SqliteConnection implements IConnection {

    //private   SQLiteDatabase db;
    private  SqliteConnectionHelper db;
    private SimpleDateFormat dateFormatter=new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
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
        String entity = manager.getEntityName();
        ContentValues values = new ContentValues( );
        for (Map.Entry<String,Object> i:data.entrySet()) {
            values.put(i.getKey(),objectToString(i.getValue()));

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
        String entity = manager.getEntityName();
        String key = (String) data.remove("key");
        ContentValues values = new ContentValues( );
        for (Map.Entry<String,Object> i:data.entrySet()) {
            values.put(i.getKey(),objectToString(i.getValue()));

        }
        db.update(entity,key,values);
        this.linkModelManager(manager);
        return true;

    }

    @Override
    public void linkModelManager( ModelManager manager) {
        connect();
        manager.clearCache();
        String entity = manager.getEntityName();
        Cursor cursor = db.getAllData(entity);
        Map columnTypes = manager.getColumnTypes();
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
                Class classField = (Class) columnTypes.get(column);

                data.put(column,parseString(field,classField));
                i++;
            }
            manager.addToCache(manager.makeModel(data));
        } while (cursor.moveToNext());



        manager.notify_observers();
    }

    private Object parseString(String field, Class classField) {
        if(classField == Date.class){
            try {
                return dateFormatter.parse(field);
            } catch (ParseException e) {
                e.printStackTrace();

            }
        }else   if(classField == int.class ){
            return Integer.parseInt(field);
        }else if( classField == long.class){
            return Long.parseLong(field);
        }
        return field;


    }
    private String objectToString(Object o){
        if(o.getClass() == Date.class){
            return dateFormatter.format(o);
        }
        return String.valueOf(o);
    }
}