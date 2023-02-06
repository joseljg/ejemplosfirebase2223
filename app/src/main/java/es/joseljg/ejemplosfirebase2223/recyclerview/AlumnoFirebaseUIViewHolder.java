package es.joseljg.ejemplosfirebase2223.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.joseljg.ejemplosfirebase2223.DetallesAlumnoActivity;
import es.joseljg.ejemplosfirebase2223.R;
import es.joseljg.ejemplosfirebase2223.clases.Alumno;

public class AlumnoFirebaseUIViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String EXTRA_ALUMNO_ITEM = "es.joseljg.productosviewholder.alumno";
    // atributos
    private TextView txt_item_nombre;
    private TextView txt_item_curso;
    //-------------------------------------
    private ListaAlumnosAdapter lpa;
    private Context contexto;


    public AlumnoFirebaseUIViewHolder(@NonNull View itemView, ListaAlumnosAdapter listaProductosAdapter) {
        super(itemView);
        txt_item_nombre = (TextView) itemView.findViewById(R.id.txt_item_nombre);
        txt_item_curso = (TextView) itemView.findViewById(R.id.txt_item_curso);
       //-----------------------------------------------------------------------------
        lpa = listaProductosAdapter;
        itemView.setOnClickListener(this);
    }
    public AlumnoFirebaseUIViewHolder(@NonNull View itemView, Context contexto) {
        super(itemView);
        txt_item_nombre = (TextView) itemView.findViewById(R.id.txt_item_nombre);
        txt_item_curso = (TextView) itemView.findViewById(R.id.txt_item_curso);
        //-----------------------------------------------------------------------------
        this.contexto = contexto;
        itemView.setOnClickListener(this);
    }
    public TextView getTxt_item_nombre() {
        return txt_item_nombre;
    }

    public void setTxt_item_nombre(TextView txt_item_nombre) {
        this.txt_item_nombre = txt_item_nombre;
    }

    public TextView getTxt_item_curso() {
        return txt_item_curso;
    }

    public void setTxt_item_curso(TextView txt_item_curso) {
        this.txt_item_curso = txt_item_curso;
    }

    @Override
    public void onClick(View view) {
        Alumno p = new Alumno(String.valueOf(txt_item_nombre.getText()),String.valueOf(txt_item_curso.getText()));
        Intent intent = new Intent(this.contexto , DetallesAlumnoActivity.class);
        intent.putExtra(EXTRA_ALUMNO_ITEM,p);
        this.contexto.startActivity(intent);
    }
}
