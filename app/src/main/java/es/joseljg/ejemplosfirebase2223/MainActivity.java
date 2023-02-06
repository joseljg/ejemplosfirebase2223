package es.joseljg.ejemplosfirebase2223;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.joseljg.ejemplosfirebase2223.clases.Alumno;
import es.joseljg.ejemplosfirebase2223.clases.Equipo;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText edt_login_email;
    private EditText edt_login_clave;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_login_email = (EditText) findViewById(R.id.edt_login_email);
        edt_login_clave = (EditText) findViewById(R.id.edt_login_clave);

        mAuth = FirebaseAuth.getInstance();
        // PRUEBAS EN FIREBASE REALTIME
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
  /*
        myRef.child("saludo3").setValue("Hola de nuevo");

     Equipo e1 = new Equipo("equipo1", "hp");
        myRef.child("equipos").child(e1.getIdEquipo()).setValue(e1);
      //  myRef.child("equipos").push().setValue(e1);
        Equipo e2 = new Equipo("equipo2", "hp");
        Equipo e3 = new Equipo("equipo3", "hp");
        Equipo e4 = new Equipo("equipo4", "hp");
        Equipo e5 = new Equipo("equipo5", "hp");
        Equipo e6 = new Equipo("equipo6", "hp");
       //-----------------------------------------------------------------------
        ArrayList<Equipo> arraylistequipos = new ArrayList<Equipo>();
        arraylistequipos.add(e2);
        arraylistequipos.add(e3);
        arraylistequipos.add(e4);
        arraylistequipos.add(e5);
        arraylistequipos.add(e6);
        myRef.child("arraylistequipos").setValue(arraylistequipos);
        //------------------------------------------------------------------------
        Map<String, Equipo> equiposhashmap = new HashMap<String,Equipo>();
        equiposhashmap.put(e2.getIdEquipo(),e2);
        equiposhashmap.put(e3.getIdEquipo(),e3);
        equiposhashmap.put(e4.getIdEquipo(),e4);
        equiposhashmap.put(e5.getIdEquipo(),e5);
        equiposhashmap.put(e6.getIdEquipo(),e6);
        myRef.child("hashmapequipos").setValue(equiposhashmap);

        // ejemplos para añadir nodos a la base de datos
        //   myRef.push().setValue("Hello, World!");
        //  myRef.child("saludo2").setValue("hola a todos, me han creado desde android");
          myRef.child("numero2").setValue(5);
          myRef.child("conectado").setValue(true);
        //------------------------------------------------------------
        //  Alumno a1 = new Alumno("pepe", "2dam");
        //   myRef.child("alumnos").push().setValue(a1);
*/
        Alumno a2 = new Alumno("juan", "2dam");
        Alumno a3 = new Alumno("ana", "2dam");
        myRef.child("alumnos").child("juan").setValue(a2);
        myRef.child("alumnos").child("ana").setValue(a3);
        //------------------------------------------------------------
        Alumno a4 = new Alumno("carlos", "1dam");
        Alumno a5 = new Alumno("sarai", "1dam");
        Alumno a6 = new Alumno("andres", "1dam");
        Alumno a7 = new Alumno("maria", "1dam");
        Map<String, Alumno> alumnos2 = new HashMap<String,Alumno>();
        alumnos2.put("carlos",a4 );
        alumnos2.put("sarai",a5 );
        alumnos2.put("andres",a6 );
        alumnos2.put("maria",a7 );

        myRef.child("alumnoshashmap").setValue(alumnos2);
        //---------------------------------------------------------------------------------
        ArrayList<Alumno> alumnos3 = new ArrayList<Alumno>();
        alumnos3.add(new Alumno("antonio", "1dam"));
        alumnos3.add(new Alumno("david", "2dam"));
        alumnos3.add(new Alumno("olga", "1dam"));
        myRef.child("alumnosarraylist").setValue(alumnos3);

        //-----------------borrado de datos -------------------------------------
        myRef.child("alumnos").child("juan").setValue(null);
        myRef.child("alumnoshashmap").child("andres").removeValue();
        myRef.child("hashmapequipos").child("equipo3").setValue(null);
        myRef.child("saludo3").removeValue();
        myRef.child("equipos").removeValue();
        //-------------------actualizacion de datos---------------------------------------------------------
        myRef.child("alumnoshashmap").child("carlos").child("curso").setValue("cursonuevo");
        myRef.child("hashmapequipos").child("equipo4").child("nombre").setValue("nueva marca");
        myRef.child("hashmapequipos").child("equipo4").child("idEquipo").setValue("nuevo identificador");
    //    Alumno a_maria_nuevo = new Alumno("nombre nuevo maria", "curso nuevo maria" );

        Equipo equipo4nuevo = new Equipo("id4nuevo", "marca4 nuevo");
        Map<String,Object> nuevoequipo4 = new HashMap<>();
        nuevoequipo4.put("equipo4",equipo4nuevo);
        myRef.child("hashmapequipos").updateChildren(nuevoequipo4);
        //--------------------------------------------------------------------------------------------

    //    Map<String, Object> nuevoMap = new HashMap<String,Object>();
    //    nuevoMap.put("maria",a_maria_nuevo);
     //   myRef.child("alumnoshashmap").updateChildren(nuevoMap);

        //--------------------------------------------------------------------------------------

        // LEER UN ALUMNO CON CLAVE "clave2" (HASHMAP -> alumnoshashmap )
        // Read from the database alumno key -> "clave2"
        DatabaseReference myRefalumnos1 = database.getReference("alumnoshashmap");
        myRefalumnos1.child("olga").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Alumno value = dataSnapshot.getValue(Alumno.class);
                if(value != null) {
                    System.out.println(value.toString());
                    Log.i("firebase1", value.toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i( "firebase1", String.valueOf(error.toException()));
            }
        });
        //---------------------------------------------------------------------------------------
        // LEER TODOS LOS ALUMNOS
        DatabaseReference myRefalumnos2 = database.getReference("alumnoshashmap");
        myRefalumnos2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> keys = new ArrayList<String>();
                List<Alumno> alumnos = new ArrayList<Alumno>();
                for (DataSnapshot keynode : snapshot.getChildren()) {
                    keys.add(keynode.getKey());
                    alumnos.add(keynode.getValue(Alumno.class));
                }
                for (String k : keys) {
                    System.out.println(k);
                    Log.i("firebase1", "clave leida " + k);
                }
                for (Alumno a : alumnos) {
                    System.out.println(a.toString());
                    Log.i("firebase1", "alumno leido " + a.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i( "firebase1", String.valueOf(error.toException()));
            }
        });
    }


    public void mostrar_alumnos(View view) {
        Intent intent = new Intent(this, MostrarAlumnos.class);
        startActivity(intent);
    }
    public void mostrar_alumnos_FirebaseUI(View view) {
        Intent intent = new Intent(this, MostrarAlumnosFirebaseUI.class);
        startActivity(intent);
    }

    public void loguearAlumno(View view) {
        String email = String.valueOf(edt_login_email.getText());
        String password = String.valueOf(edt_login_clave.getText());
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("firebasedb", "logueado correctamente");
                            Toast.makeText(MainActivity.this, "logueado correctamente", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent = new Intent(MainActivity.this, MostrarAlumnos.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("firebasedb", "error al loguearse", task.getException());
                            Toast.makeText(MainActivity.this, "error al loguearse", Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }

    public void registrarAlumno(View view) {
        String email = String.valueOf(edt_login_email.getText()).trim();
        String password = String.valueOf(edt_login_clave.getText());
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("firebase1", "usuario registrado correctamente");
                            Toast.makeText(MainActivity.this, "usuario registrado correctamente", Toast.LENGTH_SHORT).show();
//                            // updateUI(user);
                            Intent intent = new Intent(MainActivity.this, MostrarAlumnos.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("firebase1", "no se pudo registrar al usuario", task.getException());
                            Toast.makeText(MainActivity.this, "no se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }
                    }
                });

    }

    public void cerrarAlumno(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "se cerró la sesión correctamente", Toast.LENGTH_SHORT).show();
    }
}