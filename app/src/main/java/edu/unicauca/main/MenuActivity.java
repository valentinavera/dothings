package edu.unicauca.main;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import edu.unicauca.main.models.TaskModel;

import static edu.unicauca.main.R.id.contentfragment;

public class MenuActivity extends AppCompatActivity implements DialogTaskClass.DialogListener{
    private TaskModel taskModel = TaskModel.getTaskConnection(null,null);
    TaskFragment taskfrag;
    DayFragment dayfragment;
    ScheduleFragment scheduleFragment;
    private TaskAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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

    @Override
    public void applyText(String task) {
        TaskFragment fragment = (TaskFragment) getSupportFragmentManager ().findFragmentById (taskfrag.getId ());
        fragment.applyText (task);
    }

}