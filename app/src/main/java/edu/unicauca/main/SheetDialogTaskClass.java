package edu.unicauca.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.unicauca.main.persistence.models.TaskModel;
import static edu.unicauca.main.R.drawable.sunset;

public class SheetDialogTaskClass extends BottomSheetDialogFragment {
    private TaskModel objTask;
    private EditText editNameText;
    private EditText taskNotes;
    private Button saveUpdate;
    private Button infoList;

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
        infoList = view.findViewById(R.id.ListButton);

        editNameText.setText(objTask.getName());
        taskNotes.setText(objTask.getDescription());

        if(objTask.getState().equals("0")){
            infoList.setText(R.string.change_ToMyDayList);
            //infoList.setBackgroundResource(sunset);
        }else{
            infoList.setText(R.string.change_ToTasksLis);
            //infoList.setBackgroundResource(R.drawable.tarea);
        }

        infoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objTask.getKey();
                if(objTask.getState().equals("0")){
                    objTask.setState("1");
                    infoList.setText(R.string.change_ToTasksLis);
                }else{
                    objTask.setState("0");
                    infoList.setText(R.string.change_ToMyDayList);
                }
                objTask.save();
            }
        });

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
