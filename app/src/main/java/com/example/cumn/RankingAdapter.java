package com.example.cumn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<Usuario> users = new ArrayList<>();

    public RankingAdapter(ArrayList<Usuario> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_score,parent,false);
        RecyclerViewAdapter.ViewHolder holder = new RecyclerViewAdapter.ViewHolder(view, false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.username.setText(users.get(position).getUsername());
        holder.score.setText(String.format("%.0f", users.get(position).getScore()));
        position+=1;
        holder.position.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
