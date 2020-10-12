package edu.unicauca.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
    DatabaseReference db;
    EditText mEditTextName,mEditTextUserName,mEditTextPassword;
    Button btnSubmit;
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
        DayFragment fragment = new DayFragment();
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
        mEditTextName = rootView.findViewById(R.id.etName);
        mEditTextUserName = rootView.findViewById(R.id.etUsername);
        mEditTextPassword = rootView.findViewById(R.id.etPassword);
        db =  FirebaseDatabase.getInstance().getReference();
        return this.rootView;
        loadTasks();
    }

    @Override
    public void onClick(View v) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", mEditTextName.getText().toString());
        user.put("username",  mEditTextUserName.getText().toString());
        user.put("password",  mEditTextPassword.getText().toString());
        try { 
            Task<Void> user1 = db.child("User").push().setValue(user);
        }catch (Exception e) {
            Log.println(1,"tag", e.getMessage());

        }



    }
}