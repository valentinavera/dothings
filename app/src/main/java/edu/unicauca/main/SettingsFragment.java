package edu.unicauca.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements Observer {
    private TaskModel taskModel;
    private UserModel userModel;
    private Button btnManage;
    private Button btnSychonize;
    private Button btnNotifications;
    private Button btnHelp;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }
    public static  SettingsFragment newInstance(UserModel us){
        SettingsFragment fragment = new SettingsFragment ();
        fragment.userModel=us;
        us.getManager().addObserver(fragment);
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
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment ();
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
        // Inflate the layout for this fragment
        final View vista = inflater.inflate (R.layout.fragment_settings, container, false);
        btnManage = vista.findViewById (R.id.buttonManage);
        btnSychonize = vista.findViewById (R.id.buttonSynchronize);
        btnNotifications = vista.findViewById (R.id.buttonNotify);
        btnHelp = vista.findViewById (R.id.buttonHelp);
        btnManage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent manage = new Intent(getActivity (), UserActivity.class);
                startActivity (manage);
            }
        });
        return vista;
    }

    @Override
    public void notify(Object observed) {

    }
}