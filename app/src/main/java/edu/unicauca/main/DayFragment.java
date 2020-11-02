package edu.unicauca.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.TaskModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment implements Observer {
    private TaskModel taskModel ;
    private TaskAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<TaskModel> mTaskList;
    private int position = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public static  DayFragment newInstance(TaskModel tm){
        DayFragment fragment = new DayFragment();
        fragment.taskModel  =tm;
        tm.getManager().addObserver(fragment);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
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
        View rootView =  inflater.inflate(R.layout.fragment_day, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById (R.id.vistaRecycler);
        mRecyclerView.setLayoutManager (new LinearLayoutManager(getContext ()));
        mRecyclerView.setHasFixedSize (true);
        this.notify(null);

        return rootView;
    }

    @Override
    public void notify(Observed observed) {
        mTaskList = taskModel.getManager().getAll();
        mAdapter = new TaskAdapter (mTaskList, R.layout.tareas_view);
        /*mAdapter.setOnClickListen(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = mRecyclerView.getChildAdapterPosition(v);
                openModalEdit();
            }
        });*/
        try {
            mRecyclerView.setAdapter (mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openModalEdit() {
        TaskModel objTaskModel = null;
        if(position != -1){
            objTaskModel = this.mTaskList.get(position);
        }
        SheetDialogTaskClass sheetDialogTaskClass = new SheetDialogTaskClass(objTaskModel);
        sheetDialogTaskClass.show(getActivity().getSupportFragmentManager(),"task modal");
    }
}

