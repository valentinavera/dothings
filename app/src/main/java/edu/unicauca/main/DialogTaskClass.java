package edu.unicauca.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.unicauca.main.models.TaskModel;

public class DialogTaskClass extends DialogFragment {
    //Task objTask = new Task ();
    DialogListener listener;
    private EditText createTask;
    private TaskModel taskModel ;
     public DialogTaskClass(){}
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.newtaskdialog, null);
        createTask = (EditText) view.findViewById(R.id.createTask);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton ("save", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String task=  createTask.getText().toString();
                taskModel.create(task,"asdasd");
            }
        });
        taskModel = TaskModel.getTaskConnection(null,null);

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
