package edu.unicauca.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    TaskFragment taskfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        taskfrag = new TaskFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contentfragment,taskfrag).commit();
    }

    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DayFragment dayfrag = new DayFragment();
        switch (view.getId())
        {
            case R.id.carTask:
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contentfragment, taskfrag);
                break;
            case R.id.carMyDay:
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contentfragment, dayfrag);
                break;
        }transaction.commit();
    }
}