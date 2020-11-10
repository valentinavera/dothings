package edu.unicauca.main;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.core.view.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements Observer {
    private TaskModel taskModel ;
    private long date;
    private TextView mTask;
    private TextView mDescription;
    private Context mContext;
    private List<TaskModel> tasks;
    private UserModel userModel;
    //private DatabaseReference mDataBase;
    //private CalendarView mCalendarView;
    private static final String TAG = "CalendarActivity";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }
    public static  ScheduleFragment newInstance(TaskModel tm){
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.taskModel  =tm;
        tm.getManager().addObserver(fragment);
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
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
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate (R.layout.fragment_schedule, container, false);
        MCalendarView calendarView = (MCalendarView) vista.findViewById (R.id.calendar);
        mTask= vista.findViewById (R.id.textViewTks);
        mDescription= vista.findViewById (R.id.textView);
        Calendar calendar = Calendar.getInstance ();
        tasks = taskModel.getManager().getAll ();
        userModel = SimpleSessionManager.getLoginUser();
        for(int i=0; i<tasks.size ();i++){

            if(tasks.get (i).getState ().compareTo ("3")!=0){
            if(userModel.isAuthenticated ()) {
                if(userModel.getUuid ().compareTo (tasks.get (i).getUserid ())==0){
                calendar.setTimeInMillis (tasks.get (i).getTimeDate ());
                int year = calendar.get (Calendar.YEAR);
                int mes = calendar.get (Calendar.MONTH);
                int dia = calendar.get (Calendar.DAY_OF_MONTH);
                calendarView.markDate (year, mes + 1, dia);}
            }else{
            calendar.setTimeInMillis (tasks.get (i).getTimeDate ());
            int year = calendar.get (Calendar.YEAR);
            int mes = calendar.get (Calendar.MONTH);
            int dia = calendar.get (Calendar.DAY_OF_MONTH);
            calendarView.markDate (year, mes + 1, dia);
            }
            }
        }


        calendarView.setOnDateClickListener (new OnDateClickListener () {
            @Override
            public void onDateClick(View view, DateData date) {

                for(int i=0; i<tasks.size ();i++){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String fechaDeTarea= simpleDateFormat.format(tasks.get (i).getTimeDate ());
                    String p = date.getDayString ()+"/"+date.getMonthString ()+"/"+date.getYear ();


                    if((fechaDeTarea.compareTo (p)==0)){
                        Toast.makeText(getActivity (), "TAREA : " + tasks.get (i).getName (), Toast.LENGTH_LONG).show();
                        mTask.setText (tasks.get (i).getName ());
                        mDescription.setText (tasks.get (i).getDescription ());
                    }

                }

            }
        });


        this.notify(null);
        return vista;
    }

    @Override
    public void notify(Object observed) {

    }
}