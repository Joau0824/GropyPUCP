package com.example.gropypucp.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gropypucp.Entity.Clase;
import com.example.gropypucp.Profesor.ListaEstudiantesProfesorActivity;
import com.example.gropypucp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ListaClasesProfesorAdapter extends FirestoreRecyclerAdapter<Clase,ListaClasesProfesorAdapter.ViewHolder> {
    Activity activity;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListaClasesProfesorAdapter(@NonNull FirestoreRecyclerOptions<Clase> options, Activity activity) {
        super(options);
        this.activity=activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Clase clase) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();
        holder.clase.setText(clase.getClase());
        holder.detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ListaEstudiantesProfesorActivity.class);
                intent.putExtra("idClase",id);
                activity.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public ListaClasesProfesorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listaclaseprofesor,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clase;
        TextView detalles;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clase=itemView.findViewById(R.id.textView33);
            detalles=itemView.findViewById(R.id.textViewdetalles);
        }
    }
}
