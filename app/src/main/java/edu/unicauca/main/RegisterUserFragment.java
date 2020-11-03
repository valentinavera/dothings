package edu.unicauca.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.unicauca.main.patterns.observer.Observed;
import edu.unicauca.main.patterns.observer.Observer;
import edu.unicauca.main.persistence.models.TaskModel;
import edu.unicauca.main.persistence.models.UserModel;
import edu.unicauca.main.session.SimpleSessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment implements Observer {
    private UserModel userModel;
    private EditText etName;
    private EditText etLastname;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterUserFragment newInstance(String param1, String param2) {
        RegisterUserFragment fragment = new RegisterUserFragment ();
        Bundle args = new Bundle ();
        args.putString (ARG_PARAM1, param1);
        args.putString (ARG_PARAM2, param2);
        fragment.setArguments (args);
        return fragment;
    }
    public static  RegisterUserFragment newInstance(UserModel um){
        RegisterUserFragment fragment = new RegisterUserFragment ();
        fragment.userModel =um;
        um.getManager().addObserver(fragment);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista = inflater.inflate (R.layout.fragment_register_user, container, false);
        etName= vista.findViewById (R.id.editTextName);
        etLastname= vista.findViewById (R.id.editTextLastname);
        etUsername= vista.findViewById (R.id.editTextUsername);
        etPassword= vista.findViewById (R.id.editTextPassword);
        btnRegister= vista.findViewById (R.id.buttonRegister);
        btnRegister.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name=  etName.getText().toString();
                String lastname = etLastname.getText ().toString ();
                String username = etUsername.getText ().toString ();
                String password = etPassword.getText ().toString ();
                userModel = new UserModel(name,lastname,username,password);
                userModel.save();
                Toast.makeText (getContext (),"Registro exitoso",Toast.LENGTH_LONG).show ();


                SimpleSessionManager.createUser(name, lastname,username,password, new Observer() {
                    @Override
                    public void notify(Object succesfull) {
                        if((boolean)succesfull){
                            Toast.makeText(getActivity().getApplicationContext(),"Usuario registrado",Toast.LENGTH_LONG);
                        }
                    }
                });
                //Cerrar Sesion
                //SimpleSessionManager.logout();
            }
        });
        return  vista;
    }

    @Override
    public void notify(Object observed) {

    }
}