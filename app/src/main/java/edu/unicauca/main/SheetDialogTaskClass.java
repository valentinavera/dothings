package edu.unicauca.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.unicauca.main.persistence.models.TaskModel;

public class SheetDialogTaskClass extends BottomSheetDialogFragment implements View.OnClickListener {
    private TaskModel objTask;
    private EditText editNameText;
    private EditText descriptionText;
    private EditText dateTask;
    private Button saveUpdate;

    public SheetDialogTaskClass(TaskModel objTaskModel) {
        this.objTask = objTaskModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modal_edit_task,container,false);
        editNameText = view.findViewById(R.id.editNameTask);
        descriptionText = view.findViewById (R.id.textView3);
        dateTask= view.findViewById (R.id.EditDate);
        saveUpdate = view.findViewById(R.id.saveButton);
        editNameText.setText(objTask.getName());
        //dateTask.setText (objTask.getDateTask ().toString ());
        descriptionText.setText (objTask.getDescription ());
        dateTask.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showDatePickerDialog ();
            }
        });
        saveUpdate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        objTask.getKey ();
        objTask.setName(editNameText.getText().toString());
        objTask.setDescription(descriptionText.getText ().toString ());
       // objTask.setDateTask (dateTask.getText ());
        objTask.save();
        this.dismiss();
    }
    public void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                dateTask.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
