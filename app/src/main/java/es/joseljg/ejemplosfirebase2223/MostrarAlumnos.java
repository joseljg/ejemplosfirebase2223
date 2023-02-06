package es.joseljg.ejemplosfirebase2223;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

import es.joseljg.ejemplosfirebase2223.clases.Alumno;
import es.joseljg.ejemplosfirebase2223.recyclerview.ListaAlumnosAdapter;

public class MostrarAlumnos extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
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
    private RecyclerView rv_alumnos = null;
    private ListaAlumnosAdapter adaptadorAlumnos = null;
    private DatabaseReference myRefalumnos = null;
    private DatabaseReference myRefalumnos1 = null;
    private ArrayList<Alumno> alumnos;
    private EditText edt_buscar_nombre1;
    public static int PETICION1 = 1;
    //-----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_alumnos);
        rv_alumnos = (RecyclerView) findViewById(R.id.rv_alumnos);
        edt_buscar_nombre1 = (EditText) findViewById(R.id.edt_buscar_nombre2);
        //-------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        alumnos = new ArrayList<Alumno>();
        //-----------------------------------------------------------
        adaptadorAlumnos = new ListaAlumnosAdapter(this,alumnos);
        rv_alumnos.setAdapter(adaptadorAlumnos);
        myRefalumnos = FirebaseDatabase.getInstance().getReference("alumnoshashmap");
        myRefalumnos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                adaptadorAlumnos.getAlumnos().clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Alumno a = (Alumno) dataSnapshot.getValue(Alumno.class);
                    alumnos.add(a);
                    adaptadorAlumnos.setAlumnos(alumnos);
                    adaptadorAlumnos.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i( "firebase1", String.valueOf(error.toException()));
            }
        });

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            rv_alumnos.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            // In portrait
            rv_alumnos.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void addAlumno(View view) {
        Intent intent = new Intent(this, AddAlumnoActivity.class);
        startActivity(intent);
    }
    public void buscarAlumnos1(View view) {

        String texto = String.valueOf(edt_buscar_nombre1.getText());
        myRefalumnos1 = FirebaseDatabase.getInstance().getReference("alumnoshashmap");
        myRefalumnos1.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> keys1 = new ArrayList<String>();
                ArrayList<Alumno> alumnos1 = new ArrayList<Alumno>();
                for (DataSnapshot keynode : snapshot.getChildren()) {
                    keys1.add(keynode.getKey());
                    Alumno a = keynode.getValue(Alumno.class);
                    if(a.getNombre().contains(texto)) {
                        alumnos1.add(keynode.getValue(Alumno.class));
                    }
                }
                adaptadorAlumnos.setAlumnos(alumnos1);
                adaptadorAlumnos.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i( "firebase1", String.valueOf(error.toException()));
            }
        });

    }
//---------------------------------------------------------------------------------
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == PETICION1 && resultCode == Activity.RESULT_OK) {
        int posicion = data.getIntExtra(DetallesAlumnoActivity.EXTRA_POSICION_DEVUELTA,-1);
        String tipo = data.getStringExtra(DetallesAlumnoActivity.EXTRA_TIPO);
       if(tipo.equalsIgnoreCase("edicion"))
       {
           adaptadorAlumnos.notifyItemChanged(posicion);
           adaptadorAlumnos.notifyDataSetChanged();
       }
       else if(tipo.equalsIgnoreCase("borrado"))
       {
           adaptadorAlumnos.notifyItemRemoved(posicion);
           adaptadorAlumnos.notifyDataSetChanged();
       }
       else{
           adaptadorAlumnos.notifyDataSetChanged();
       }


     // this.recreate();
     //  getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
    }
}
    //------------------------------------------------------------------------------


}