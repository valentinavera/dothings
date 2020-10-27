package edu.unicauca.main;

import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.unicauca.main.persistence.models.TaskModel;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements View.OnClickListener{
    private int resource;
    private List<TaskModel> taskList;
    private View.OnClickListener Listener;

    public TaskAdapter(List<TaskModel> taskList, int resource) {
        this.taskList = taskList;
        this.resource = resource;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder vista = new ViewHolder(view);
        view.setOnClickListener(this);
        return vista;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel objTask = taskList.get(position);
        holder.textViewTarea.setText(objTask.getName());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setOnClickListen(View.OnClickListener clickListener){
        this.Listener = clickListener;
    }

    @Override
    public void onClick(View v) {
        if(this.Listener!=null){
            Listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTarea;
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewTarea = (TextView) view.findViewById(R.id.textViewTareas);
        }
    }

}
