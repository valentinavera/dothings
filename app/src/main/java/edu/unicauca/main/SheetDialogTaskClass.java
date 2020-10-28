package edu.unicauca.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import edu.unicauca.main.persistence.models.TaskModel;

public class SheetDialogTaskClass extends BottomSheetDialogFragment {
    private TaskModel objTask;
    private EditText editNameText;

    public SheetDialogTaskClass(TaskModel objTaskModel) {
        this.objTask = objTaskModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_modal_edit_task,container,false);
        editNameText = view.findViewById(R.id.editNameTask);
        editNameText.setText(objTask.getName());
        return view;
    }



    @Override
    public void dismiss() {
        super.dismiss();
    }
}
