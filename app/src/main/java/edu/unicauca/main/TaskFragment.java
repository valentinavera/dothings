package edu.unicauca.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.unicauca.main.models.Task;
import edu.unicauca.main.models.TaskAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements DialogTaskClass.DialogListener {
    private TextView TextViewCreateTask;
    private FloatingActionButton fabSendTask;
    private String tarea;
    private DatabaseReference mDataBase;
    private TaskAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Task> mTaskList = new ArrayList<> ();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(String param1, String param2) {
        TaskFragment fragment = new TaskFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if (getArguments () != null) {
            mParam1 = getArguments ().getString (ARG_PARAM1);
            mParam2 = getArguments ().getString (ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            final View vista = inflater.inflate (R.layout.fragment_task, container, false);
            mTaskList.clear ();
            TextViewCreateTask = vista.findViewById (R.id.viewTask);
            mDataBase = FirebaseDatabase.getInstance ().getReference ().child ("ListaTareas");
            fabSendTask = vista.findViewById (R.id.floating_button_Dialog);
            mRecyclerView = (RecyclerView) vista.findViewById (R.id.recyclerViewTareas);
            mRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
            getTaskFromFirebase ();

            fabSendTask.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                mTaskList.clear ();
                openDialog ();
                          }

            });
            // Inflate the layout for this fragment

        return vista;
    }

    private void openDialog() {
        mTaskList.clear ();
        DialogTaskClass dialogTaskClass= new DialogTaskClass ();
        dialogTaskClass.setTargetFragment (this, 0);
        dialogTaskClass.show (this.getActivity ().getSupportFragmentManager ().beginTransaction (), "Task dialog");


    }

    @Override
    public void applyText(String task) {
        //TextViewCreateTask.setText (task);
    }
    private  void getTaskFromFirebase(){
        mDataBase.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren ()) {
                    Task task = ds.getValue (Task.class);
                    mTaskList.add (task);
                }
                mAdapter = new TaskAdapter (mTaskList, R.layout.tareas_view);
                mRecyclerView.setAdapter (mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    }




