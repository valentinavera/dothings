package edu.unicauca.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.unicauca.main.models.TaskModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextView(View view){
        TaskModel.getTaskConnection(null,this);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}