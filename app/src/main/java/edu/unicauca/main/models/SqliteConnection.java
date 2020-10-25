package edu.unicauca.main.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

class  SqliteConnectionHelper extends SQLiteOpenHelper {

    public SqliteConnectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
        //tasks
        String ddlTask = "create table if not EXISTS Task ( _id integer primary key autoincrement, name varchar(20),description varchar(100))";
        //String ddlTask = "create table Task ( _id integer primary key autoincrement, name varchar(20),description varchar(100))";
        db.execSQL(ddlTask);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public  void insert(String entity, ContentValues values){
        try {
            SQLiteDatabase wdb = this.getWritableDatabase();
            wdb.insert(entity,null,values);
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
    public boolean push(Model m ,String entity, Map<String, Object> data) {
        connect();
        ContentValues values = new ContentValues( );
        for (Map.Entry<String,Object> i:data.entrySet()) {
            values.put(i.getKey(),i.getValue().toString());

        }
        db.insert(entity,values);
        this.linkModel(entity,m);
        return true;

    }

    @Override
    public void linkModel(String entity, Model model) {
        connect();
        model.clearCache();
        //Cursor cursor = db.rawQuery("SELECT * FROM " + entity, null);
        Cursor cursor = db.getAllData(entity);
        Map<String,Class> columtypes= model.getColumtypes();
        Map<String, Object> data = new HashMap<>();
        cursor.moveToFirst();
        if(cursor.getCount() == 0){
            model.notify_observers();
            return;
        }
        do {
            int i = 1;
            for(Map.Entry<String,Class> ctype: columtypes.entrySet()) {
                String field = cursor.getString(i);
                data.put(ctype.getKey(),field);
                i++;
            }
            model.addToCache(model.createModel(data));
        } while (cursor.moveToNext());



        model.notify_observers();
    }
}