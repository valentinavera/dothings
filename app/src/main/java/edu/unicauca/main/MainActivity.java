package edu.unicauca.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        SimpleSessionManager.init(this);
        setContentView(R.layout.activity_main);
    }

    public void nextView(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}