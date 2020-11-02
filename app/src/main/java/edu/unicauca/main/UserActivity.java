package edu.unicauca.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;

import static edu.unicauca.main.R.id.contentfragment;

public class UserActivity extends AppCompatActivity {
    private TaskModel taskModel ;
    private UserModel userModel;
    RegisterUserFragment registerUserFragment;
    TaskFragment taskfrag;
    DayFragment dayfragment;
    ScheduleFragment scheduleFragment;
    SettingsFragment settingsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        setContentView(R.layout.activity_user);
        taskModel = new TaskModel(getApplicationContext());//(getApplicationContext());
        userModel = new UserModel(getApplicationContext());
        taskfrag = TaskFragment.newInstance(taskModel);
        registerUserFragment= RegisterUserFragment.newInstance(userModel);
        getSupportFragmentManager().beginTransaction().add(contentfragment, registerUserFragment).commit();

    }

    public void onClick(View view) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        dayfragment =  DayFragment.newInstance(taskModel);
        scheduleFragment =  ScheduleFragment.newInstance(taskModel);
        settingsFragment = SettingsFragment.newInstance (userModel);


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
            case R.id.carSettings:
                transaction = getSupportFragmentManager().beginTransaction().replace(contentfragment, settingsFragment);
                break;
        }transaction.commit();
    }

}