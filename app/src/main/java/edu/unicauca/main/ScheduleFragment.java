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

import com.google.firebase.database.core.view.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import sun.bob.mcalendarview.MCalendarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements Observer {
    private TaskModel taskModel ;
    private long date;
    private Context mContext;
      //private DatabaseReference mDataBase;
    private CalendarView mCalendarView;
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
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        List<Calendar> calendars = new ArrayList<>();
        calendars.add (calendar);
        calendars.add (calendar1);
        calendar.set(2020, 10, 5);
        calendar1.set(2020, 10, 26);
        mCalendarView = (CalendarView) vista.findViewById (R.id.calendar);
        date = calendar.getTimeInMillis ();
        mCalendarView.setDate(calendar.getTimeInMillis ());
        mCalendarView.setDate(calendar1.getTimeInMillis ());
        mCalendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String date = (year) + "/" + (month+1) +"/" + dayOfMonth;
                Log.d(TAG, "onSelectedDayChange: date " +date);
            }
        });
        this.notify(null);
        return vista;
    }



    @Override
    public void notify(Object observed) {

    }
}