package com.example.gropypucp.Profesor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gropypucp.Adapter.ListaClasesProfesorAdapter;
import com.example.gropypucp.Entity.Clase;
import com.example.gropypucp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListaClasesProfesorActivity extends AppCompatActivity {

    RecyclerView mRecycler;
    ListaClasesProfesorAdapter mListaAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clases_profesor);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mRecycler=findViewById(R.id.recyclerLista);
        mRecycler.setLayoutManager(new LinearLayoutManager(ListaClasesProfesorActivity.this));
        Query query = firestore.collection("clase").whereEqualTo("uidProfesor",uid);
        FirestoreRecyclerOptions<Clase> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Clase>().setQuery(query,Clase.class).build();
        mListaAdapter=new ListaClasesProfesorAdapter(firestoreRecyclerOptions,ListaClasesProfesorActivity.this);
        mListaAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mListaAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecycler.getRecycledViewPool().clear();
        mListaAdapter.notifyDataSetChanged();
        mListaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mListaAdapter.stopListening();
    }


}