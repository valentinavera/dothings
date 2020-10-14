package edu.unicauca.main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.unicauca.main.models.Task;
import edu.unicauca.main.models.TaskAdapter;

import static edu.unicauca.main.R.id.contentfragment;

public class MenuActivity extends AppCompatActivity implements DialogTaskClass.DialogListener{

    TaskFragment taskfrag;
    DayFragment dayfragment;
    ScheduleFragment scheduleFragment;
    private TaskAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        taskfrag = new TaskFragment ();
        getSupportFragmentManager().beginTransaction().add(contentfragment, taskfrag).commit();

    }

    public void onClick(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        dayfragment = new DayFragment ();
        scheduleFragment = new ScheduleFragment ();
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