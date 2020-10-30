package edu.unicauca.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import edu.unicauca.main.persistence.models.TaskModel;

import static edu.unicauca.main.R.id.contentfragment;

public class MenuActivity extends AppCompatActivity {
    private TaskModel taskModel ;
    TaskFragment taskfrag;
    DayFragment dayfragment;
    ScheduleFragment scheduleFragment;
    private TaskAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_menu);
        taskModel = new TaskModel(getApplicationContext());//(getApplicationContext());
        taskfrag = TaskFragment.newInstance(taskModel);
        getSupportFragmentManager().beginTransaction().add(contentfragment, taskfrag).commit();

    }

    public void onClick(View view) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        dayfragment =  DayFragment.newInstance(taskModel);

        scheduleFragment =  ScheduleFragment.newInstance(taskModel);

        switch (view.getId())
        {
            case R.id.carTask:
                transaction = getSupportFragmentManager().beginTransaction().replace(contentfragment,taskfrag);
                break;
            case R.id.carMyDay:
                transaction = getSupportFragmentManager().beginTransaction().replace(contentfragment, dayfragment);
                break;
            case R.id.carSchedule:
                transaction = getSupportFragmentManager().beginTransaction().replace(contentfragment, scheduleFragment);
                break;
        }transaction.commit();
    }

}