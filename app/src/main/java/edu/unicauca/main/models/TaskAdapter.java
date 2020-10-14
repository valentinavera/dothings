package edu.unicauca.main.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.unicauca.main.R;

public class TaskAdapter extends RecyclerView.Adapter <TaskAdapter.ViewHolder> {
    private int resource;
    private String[] mDataset;
    private ArrayList<Task> taskList;
    public TaskAdapter(ArrayList<Task> taskList, int resource){
        this.taskList=taskList;
        this.resource = resource;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext ()).inflate (resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Task objTask = taskList.get (position);
      holder.textViewTarea.setText (objTask.getNameTask ());
    }

    @Override
    public int getItemCount() {
        return taskList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTarea;
        public View view;

        public ViewHolder(View view){
            super(view);
            this.view=view;
            this.textViewTarea= (TextView) view.findViewById (R.id.textViewTareas);
        }
    }

}
