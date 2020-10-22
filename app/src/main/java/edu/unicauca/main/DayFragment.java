package edu.unicauca.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import edu.unicauca.main.models.TaskModel;

import static android.R.layout.simple_list_item_1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment  implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View rootView ;
    private TaskModel taskModel = TaskModel.getTaskConnection(null);
    DatabaseReference db;
    EditText mEditTextName,mEditTextDescription;
    ListView lista;
    Button btnSubmit;
    final ArrayList<String> todoItems = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList =new ArrayList<>();

    public DayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayFragment newInstance(String param1, String param2) {
        DayFragment fragment = new DayFragment ();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView =  inflater.inflate(R.layout.fragment_day, container, false);
        btnSubmit =rootView.findViewById(R.id.btnSubmitUser);
        btnSubmit.setOnClickListener(this);
        mEditTextName = rootView.findViewById(R.id.etNameTask);
        mEditTextDescription = rootView.findViewById(R.id.etDescriptionTask);

       // loadTasks();
        return this.rootView;
    }

    @Override
    public void onClick(View v) {
        String name = mEditTextName.getText().toString();
        String description = mEditTextDescription.getText().toString();
        taskModel.create(name,description);
    }
    private  void loadTasks(){
        List<TaskModel> tasks = taskModel.getAll();
        for (TaskModel task: tasks) {
            Log.e("task: ",task.getName());

        }
    }



}

