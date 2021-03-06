package edu.unicauca.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment implements Observer {
    private TaskModel taskModel;
    private TextView TextViewCreateTask;
    private FloatingActionButton fabSendTask;
    //private DatabaseReference mDataBase;
    private TaskAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<TaskModel> tasks;
    private int position;

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
    public static  TaskFragment newInstance(TaskModel tm){
        TaskFragment fragment = new TaskFragment();
        fragment.taskModel  =tm;
        tm.getManager().addObserver(fragment);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
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
        TaskFragment fragment = new TaskFragment();
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

            TextViewCreateTask = vista.findViewById (R.id.viewTask);
            fabSendTask = vista.findViewById (R.id.floating_button_Dialog);
            mRecyclerView = (RecyclerView) vista.findViewById (R.id.recyclerViewTareas);
            mRecyclerView.setLayoutManager (new LinearLayoutManager (getActivity ()));
            this.notify(null);
            fabSendTask.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) { openDialog (); }
            });

            // Inflate the layout for this fragment

        return vista;
    }

    private void openDialog() {

        DialogTaskClass dialogTaskClass= new DialogTaskClass ();
        dialogTaskClass.setTargetFragment (this, 0);
        dialogTaskClass.show (this.getActivity ().getSupportFragmentManager ().beginTransaction (), "Task dialog");
    }

    @Override
    public void notify(Object observed) {
        Map<String,Object> fitlerFields = new HashMap<>();
        fitlerFields.put("state","0");
        UserModel u = SimpleSessionManager.getLoginUser();
        if(u.isAuthenticated()) {
            fitlerFields.put("userid", u.getUuid());
            tasks = taskModel.getManager().filter(fitlerFields);
        }else{
            //tasks = taskModel.getManager().getAll();
            tasks = taskModel.getManager().filter(fitlerFields);
        }

        mAdapter = new TaskAdapter (tasks, R.layout.tareas_view);
        mAdapter.setOnClickListen(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = mRecyclerView.getChildAdapterPosition(view);
                //Toast.makeText(getActivity().getApplicationContext(),"position"+position,Toast.LENGTH_SHORT).show();
                showModal();
            }
        });
        try {
            mRecyclerView.setAdapter (mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        //Log.e("Notify:","hola");
    }

    private void showModal() {
        TaskModel objTaskModel = null;
        if(position != -1){
            objTaskModel = this.tasks.get(position);
        }
        SheetDialogTaskClass sheetDialogTaskClass = new SheetDialogTaskClass(objTaskModel);
        sheetDialogTaskClass.show(getActivity().getSupportFragmentManager(),"task modal");
    }

}




