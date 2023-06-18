package com.project.todoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.project.todoapp.Model.DataModel;
import com.project.todoapp.R;
import com.project.todoapp.databinding.ItemTaskContainerBinding;

public class Tasks_Adapter extends ListAdapter<DataModel,Tasks_Adapter.ViewHolder> {

    public Tasks_Adapter()
    {
        super(CALLBACK);
    }
    private static final DiffUtil.ItemCallback<DataModel> CALLBACK = new DiffUtil.ItemCallback<DataModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DataModel oldItem, @NonNull DataModel newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataModel oldItem, @NonNull DataModel newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())&& oldItem.getDescription().equals(newItem.getDescription())
                    &&oldItem.getDate().equals(newItem.getDate())&&oldItem.getTime().equals(newItem.getTime());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_container,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataModel dataModel = getItem(position);
        holder.binding.title.setText(dataModel.getTitle());
        holder.binding.description.setText(dataModel.getDescription());
        holder.binding.date.setText(dataModel.getDate());
        holder.binding.time.setText(dataModel.getTime());

    }

    public DataModel getTask(int position)
    {
        return getItem(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
     {
         ItemTaskContainerBinding binding;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             binding = ItemTaskContainerBinding.bind(itemView);
         }
     }
}
