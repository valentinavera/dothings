package edu.unicauca.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.unicauca.main.persistence.models.TaskModel;

public class SheetDialogTaskClass extends BottomSheetDialogFragment {
    private TaskModel objTask;
    private EditText editNameText;
    private EditText taskNotes;
    private Button saveUpdate;

    public SheetDialogTaskClass(TaskModel objTaskModel) {
        this.objTask = objTaskModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modal_edit_task,container,false);
        editNameText = view.findViewById(R.id.editNameTask);
        taskNotes= view.findViewById(R.id.textNotes);
        saveUpdate = view.findViewById(R.id.saveButton);

        editNameText.setText(objTask.getName());
        taskNotes.setText(objTask.getDescription());
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objTask.getKey ();
                objTask.setName(editNameText.getText().toString());
                objTask.setDescription(taskNotes.getText().toString());
                objTask.save();
                dismiss();
            }
        });
        return view;
    }

}
