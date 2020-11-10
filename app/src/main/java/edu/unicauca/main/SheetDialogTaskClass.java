package edu.unicauca.main;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.unicauca.main.persistence.models.TaskModel;
import static edu.unicauca.main.R.drawable.sunset;

public class SheetDialogTaskClass extends BottomSheetDialogFragment {
    private TaskModel objTask;
    private EditText editNameText;
    private Button dateTask;
    private Button hourTask;
    private EditText taskNotes;
    private Button saveUpdate;
    private Button infoList;
    private long pDateTask=0;
    private long pTimeTask=0;
    private long changeDate=0;
    private long changeTime=0;
    private ImageButton trashButton;
    Calendar calendarResult = Calendar.getInstance();

    public SheetDialogTaskClass(TaskModel objTaskModel) {
        this.objTask = objTaskModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modal_edit_task,container,false);
        editNameText = view.findViewById(R.id.editNameTask);
        taskNotes = view.findViewById (R.id.textNotes);
        dateTask= view.findViewById (R.id.EditDate);
        trashButton = view.findViewById(R.id.imageTrashButton);
        hourTask= view.findViewById (R.id.buttonReminder);
        saveUpdate = view.findViewById(R.id.saveButton);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        pDateTask = objTask.getTimeDate();
        String dateString = sdf.format(pDateTask);
        SimpleDateFormat stf=new SimpleDateFormat("HH:mm a");
        pTimeTask = objTask.getHour();
        String timeString = stf.format(pTimeTask);
        dateTask.setText (dateString);
        hourTask.setText (timeString);

        dateTask.setOnClickListener (new View.OnClickListener () {
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity (), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int mes, int dia) {
                        dateTaskDatePicker (datePicker,year,mes,dia);
                    }
                },calendarResult.get(Calendar.YEAR),calendarResult.get(Calendar.MONTH),calendarResult.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }

        });
        hourTask.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendarResult.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendarResult.set(Calendar.MINUTE, minute);
                        SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm a");
                        String formatedDate = timeformat.format(calendarResult.getTime());
                        hourTask.setText(formatedDate);
                        Date time= calendarResult.getTime ();
                        changeTime= time.getTime ();
                    }
                }, calendarResult.get(Calendar.HOUR_OF_DAY), calendarResult.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
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
                objTask.setDescription(taskNotes.getText ().toString ());

                if((changeDate==0)){
                    objTask.setTimeDate(pDateTask);

                }else{
                    objTask.setTimeDate(changeDate);

                }
                if((changeTime==0)){
                    objTask.setHour(pTimeTask);
                }else{
                    objTask.setHour(changeTime);
                }
                objTask.save();
                dismiss();
            }

        });

        trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objTask.getKey();
                objTask.setState("3");
                objTask.save();
                v = getActivity().findViewById(R.id.contentfragment);
                Snackbar.make(v,R.string.experience,Snackbar.LENGTH_SHORT).show();
                dismiss();
            }
        });


        return view;
    }

    public void dateTaskDatePicker(DatePicker datePicker, int year, int mes, int dia){

        calendarResult.set(Calendar.YEAR,year);
        calendarResult.set(Calendar.MONTH,mes);
        calendarResult.set(Calendar.DAY_OF_MONTH,dia);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = calendarResult.getTime();
        changeDate=date.getTime ();
        String fechaDeTarea= simpleDateFormat.format(date);
        dateTask.setText(fechaDeTarea);
    }

}

