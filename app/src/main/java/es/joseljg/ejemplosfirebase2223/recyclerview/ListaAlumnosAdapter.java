package es.joseljg.ejemplosfirebase2223.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import es.joseljg.ejemplosfirebase2223.R;
import es.joseljg.ejemplosfirebase2223.clases.Alumno;
import es.joseljg.ejemplosfirebase2223.utilidades.ImagenesFirebase;

public class ListaAlumnosAdapter extends RecyclerView.Adapter<AlumnoViewHolder> {
    // atributos
    private Context contexto = null;
    private ArrayList<Alumno> alumnos = null;
    private LayoutInflater inflate = null;

    private FirebaseAuth mAuth;

    public ListaAlumnosAdapter(Context contexto, ArrayList<Alumno> alumnos ) {
        this.contexto = contexto;
        this.alumnos = alumnos;
        inflate =  LayoutInflater.from(this.contexto);
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ArrayList<Alumno> alumnos) {
        this.alumnos = alumnos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = inflate.inflate(R.layout.item_rv_alumno,parent,false);
        AlumnoViewHolder evh = new AlumnoViewHolder(mItemView,this);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno p = this.getAlumnos().get(position);
        //        cargo la imagen desde la base de datos
        //-----------------------------------------------------------------
        String carpeta = p.getNombre();
        ImageView imagen = holder.getImg_item_alumno() ;
        ImagenesFirebase.descargarFoto(carpeta,p.getNombre(),imagen);
        ImageView imagen1 = imagen;
        holder.setImg_item_alumno(imagen1);
        //----------------------------------------------------------------------
        holder.getTxt_item_nombre().setText("nombre: " + p.getNombre());
        holder.getTxt_item_curso().setText("curso " + String.valueOf(p.getCurso()));

    }


    @Override
    public int getItemCount() {
        return this.alumnos.size();
    }

    public void addAlumno(Alumno alumnoAñadido) {
        alumnos.add(alumnoAñadido);
       notifyDataSetChanged();
    }
}
