package com.apps.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.listadetarefas.R;
import com.apps.listadetarefas.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> list) {
        this.taskList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_task_adapter, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.task.setText(task.getNameTask());
    }

    @Override
    public int getItemCount() {
        return this.taskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView task;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.textTask);
        }
    }
}
