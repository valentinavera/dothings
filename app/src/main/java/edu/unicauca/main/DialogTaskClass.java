package edu.unicauca.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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

import static android.R.layout.simple_list_item_1;

public class DialogTaskClass extends DialogFragment {
    Task objTask = new Task ();
    DialogListener listener;
    private EditText createTask;
    private DatabaseReference mDataBase;
     public DialogTaskClass(){}
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mDataBase = FirebaseDatabase.getInstance ().getReference ();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.newtaskdialog, null);
        createTask = (EditText) view.findViewById(R.id.createTask);

        builder.setView(view);
        //Para que funcionará con el botón Add

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton ("save", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //mRecyclerView.setLayoutManager (new LinearLayoutManager(getActivity ()));
                String task=  createTask.getText().toString();
                mDataBase.child ("ListaTareas").push().child("nameTask").setValue (task);
                listener.applyText (task);
            }
        });

        return builder.create ();

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (DialogListener) activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó OnSetTitleListener");

        }
    }
    public interface DialogListener{
        void applyText(String task);
    }
}
