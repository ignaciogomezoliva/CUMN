package com.example.cumn;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestas = new ArrayList<>();


    public RankingAdapter(ArrayList<String> preguntas, ArrayList<String> respuestas) {
        this.preguntas = preguntas;
        this.respuestas = respuestas;
    }

    @NonNull
    @Override
    public ViewHolderRanking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolderRanking holder = new ViewHolderRanking(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRanking holder, int position) {
        holder.pregunta.setText(preguntas.get(position));
        holder.respuesta.setText(respuestas.get(position));
    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    public class ViewHolderRanking extends RecyclerView.ViewHolder{

        TextView pregunta;
        TextView respuesta;


        public ViewHolderRanking(@NonNull View itemView) {
            super(itemView);
            pregunta = itemView.findViewById(R.id.preguntaR);
            respuesta = itemView.findViewById(R.id.respuestaR);

        }
    }
}
