package com.example.cumn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> preguntas = new ArrayList<>();
    private ArrayList<String> respuestas = new ArrayList<>();
    

    public RecyclerViewAdapter(ArrayList<String> preguntas, ArrayList<String> respuestas) {
        this.preguntas = preguntas;
        this.respuestas = respuestas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view, true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.pregunta.setText(preguntas.get(position));
       holder.respuesta.setText(respuestas.get(position));
    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView pregunta;
        TextView respuesta;
        TextView username;
        TextView score;
        TextView position;

        public ViewHolder(@NonNull View itemView, boolean election) {
            super(itemView);
            if(election){
                pregunta = itemView.findViewById(R.id.preguntaR);
                respuesta = itemView.findViewById(R.id.respuestaR);
            }
            else{
                username = itemView.findViewById(R.id.NombreR);
                score = itemView.findViewById(R.id.PuntuacionR);
                position = itemView.findViewById(R.id.RankingR);
            }

            
        }
    }
}
