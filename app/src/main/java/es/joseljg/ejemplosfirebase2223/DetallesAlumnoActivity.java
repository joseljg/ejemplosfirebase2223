package es.joseljg.ejemplosfirebase2223;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import es.joseljg.ejemplosfirebase2223.MainActivity;
import es.joseljg.ejemplosfirebase2223.R;
import es.joseljg.ejemplosfirebase2223.clases.Alumno;
import es.joseljg.ejemplosfirebase2223.recyclerview.AlumnoViewHolder;
import es.joseljg.ejemplosfirebase2223.utilidades.ImagenesBlobBitmap;
import es.joseljg.ejemplosfirebase2223.utilidades.ImagenesFirebase;

public class DetallesAlumnoActivity extends AppCompatActivity {

    public static final String EXTRA_POSICION_DEVUELTA =  "es.joseljg.detallesalumnoactivity.posicion";
    public static final String EXTRA_TIPO = "es.joseljg.detallesalumnoactivity.tipo";
    EditText edt_detalles_nombre = null;
    EditText edt_detalles_curso = null;
    String id_antiguo ="";
    int posicion = -1;
    public static final int NUEVA_IMAGEN = 1;
    Uri imagen_seleccionada = null;
    ImageView img_detalles_foto_alumno = null;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_alumno);
        edt_detalles_nombre = (EditText) findViewById(R.id.edt_detalles_nombre);
       // edt_detalles_nombre.setEnabled(false);
        edt_detalles_curso = (EditText) findViewById(R.id.edt_detalles_curso);
        img_detalles_foto_alumno = (ImageView) findViewById(R.id.img_detalles_foto_alumno);
        //--------------------------------------------------------------------------
        Intent intent = getIntent();
        if(intent != null)
        {
            Alumno p = (Alumno)intent.getSerializableExtra(AlumnoViewHolder.EXTRA_ALUMNO_ITEM);
            edt_detalles_nombre.setText(p.getNombre());
            edt_detalles_curso.setText(p.getCurso());
            id_antiguo = p.getNombre();
            //cargo la foto
            byte[] fotobinaria = (byte[]) intent.getByteArrayExtra(AlumnoViewHolder.EXTRA_ALUMNO_IMAGEN);
            Bitmap fotobitmap = ImagenesBlobBitmap.bytes_to_bitmap(fotobinaria, 200,200);
            img_detalles_foto_alumno.setImageBitmap(fotobitmap);
            // obtengo la posicion
            posicion = intent.getIntExtra(AlumnoViewHolder.EXTRA_POSICION_CASILLA,-1);
        }

    }

    //--------------------------------------------------------------------------
    //--------CODIGO PARA CAMBIAR LA IMAGEN----------------
    public void cambiar_imagen(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUEVA_IMAGEN && resultCode == Activity.RESULT_OK) {
            imagen_seleccionada = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagen_seleccionada);
                img_detalles_foto_alumno.setImageBitmap(bitmap);

                //---------------------------------------------

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //-----------------------------------------------------------------------
    public void detalles_borrar_alumno(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        String nombre = String.valueOf(edt_detalles_nombre.getText());
        String curso = String.valueOf(edt_detalles_curso.getText());
        Alumno a = new Alumno(nombre, curso);
        //--------------------------------------------
        if(id_antiguo.equalsIgnoreCase(nombre))
        {
            myRef.child("alumnoshashmap").child(id_antiguo).removeValue();
            Toast.makeText(this,"alumno borrado correctamente",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"no se pudo borrar el alumno",Toast.LENGTH_LONG).show();
        }
        //-------------------------------------------
        // borramos la imagen del firebase store
        String carpeta = a.getNombre();
        ImagenesFirebase.borrarFoto(carpeta,a.getNombre());
        // cerramos la ventana y volvemos al recyclerview
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_POSICION_DEVUELTA, posicion);
        replyIntent.putExtra(EXTRA_TIPO, "borrado");
        setResult(RESULT_OK, replyIntent);
               finish();
    }
    public void detalles_editar_alumno(View view) {
        String nombre = String.valueOf(edt_detalles_nombre.getText());
        String curso = String.valueOf(edt_detalles_curso.getText());
        Alumno a = new Alumno(nombre, curso);
        //--------------------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("alumnoshashmap").child(id_antiguo).removeValue();
        myRef.child("alumnoshashmap").child(a.getNombre()).setValue(a);
        Toast.makeText(this,"alumno editado correctamente",Toast.LENGTH_LONG).show();
       //--------------------------------------------------
        if(imagen_seleccionada != null || !id_antiguo.equalsIgnoreCase(a.getNombre())) {
            String carpeta = a.getNombre();
            ImagenesFirebase.borrarFoto(id_antiguo,id_antiguo);
            ImagenesFirebase.subirFoto(carpeta,a.getNombre(), img_detalles_foto_alumno);
        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_POSICION_DEVUELTA, posicion);
        replyIntent.putExtra(EXTRA_TIPO, "edicion");
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    public void cambiar_imagen_detalles(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Selecciona una imagen");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, NUEVA_IMAGEN);
    }


}