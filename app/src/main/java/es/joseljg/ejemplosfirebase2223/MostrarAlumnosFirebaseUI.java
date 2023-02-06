package es.joseljg.ejemplosfirebase2223;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import es.joseljg.ejemplosfirebase2223.clases.Alumno;
import es.joseljg.ejemplosfirebase2223.recyclerview.AlumnoFirebaseUIViewHolder;
import es.joseljg.ejemplosfirebase2223.recyclerview.ListaAlumnosFirebaseUIAdapter;

public class MostrarAlumnosFirebaseUI extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening(); // para que empiece a funcionar el firebaseUI
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        else{
            Toast.makeText(this, "debes autenticarte primero", Toast.LENGTH_SHORT).show();
            FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    //---------------------------------------------------------------------

    private Context contexto = null;

    private RecyclerView rv_alumnos = null;
    DatabaseReference databaseReference = null;
    FirebaseRecyclerOptions<Alumno> options = null;
    FirebaseRecyclerAdapter<Alumno, AlumnoFirebaseUIViewHolder> adapter;

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_alumnos_firebase_ui);
        //--------------------------------------------------------------------
        rv_alumnos = (RecyclerView) findViewById(R.id.rv_alumnos_firebaseUI);
        //-------------------------------------------------------------
        databaseReference = FirebaseDatabase.getInstance().getReference();
      //  Query query = databaseReference.child("alumnoshashmap").limitToLast(50);
        Query query = databaseReference.child("alumnoshashmap");
        options = new FirebaseRecyclerOptions.Builder<Alumno>().setQuery(query, Alumno.class).build();
        adapter = new ListaAlumnosFirebaseUIAdapter(options, this);

        rv_alumnos.setAdapter(adapter);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            rv_alumnos.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            // In portrait
            rv_alumnos.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void addAlumnoFirebaseUI(View view) {
        Intent intent = new Intent(this, AddAlumnoActivity.class);
        startActivity(intent);
    }

}
