package com.example.ripetizioni_android.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ripetizioni_android.R;
import com.example.ripetizioni_android.model.Prenotazione;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<RecyclerViewSingleRow> listPren;
    private onItemClickListener itemClickListener;
    Context context;

    public RecyclerViewAdapter(List<RecyclerViewSingleRow> list, Context context) {
        this.listPren = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.btnItem.setText(listPren.get(position).getTitle());
        holder.description.setText(listPren.get(position).getBody());

        holder.itemView.setOnClickListener(view -> itemClickListener.onClick(view, position, listPren.get(position)));
    }

    @Override
    public int getItemCount() {
        return listPren.size();
    }

    public void setOnItemClickListener(RecyclerViewAdapter.onItemClickListener onItemClickListener){
        this.itemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onClick(View view, int position, RecyclerViewSingleRow prenotazione);
    }

    public void add(RecyclerViewSingleRow p) {
        listPren.add(p);
        notifyItemInserted(getItemCount() - 1);
    }

    public void insert(int position, RecyclerViewSingleRow p) {
        listPren.add(position, p);
        notifyItemInserted(position);
    }

    public void remove(RecyclerViewSingleRow p) {
        int position = listPren.indexOf(p);
        listPren.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView btnItem; //TODO: rename
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            btnItem = (TextView) itemView.findViewById(R.id.textView1);
            description = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
}
