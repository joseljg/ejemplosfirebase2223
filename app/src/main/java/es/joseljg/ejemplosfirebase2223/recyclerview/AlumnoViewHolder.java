package es.joseljg.ejemplosfirebase2223.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import es.joseljg.ejemplosfirebase2223.DetallesAlumnoActivity;
import es.joseljg.ejemplosfirebase2223.MostrarAlumnos;
import es.joseljg.ejemplosfirebase2223.R;
import es.joseljg.ejemplosfirebase2223.clases.Alumno;
import es.joseljg.ejemplosfirebase2223.utilidades.ImagenesBlobBitmap;

public class AlumnoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public static final String EXTRA_ALUMNO_ITEM = "es.joseljg.alumnoviewholder.alumno";
    public static final String EXTRA_ALUMNO_IMAGEN = "es.joseljg.alumnoviewholder.imagenalumno";
    public static final String EXTRA_POSICION_CASILLA = "es.joseljg.alumnoviewholder.posicion";
    // atributos
    private TextView txt_item_nombre;
    private TextView txt_item_curso;
    private ImageView img_item_alumno;
    //-------------------------------------
    private ListaAlumnosAdapter lpa;
    private Context contexto;

    public ImageView getImg_item_alumno() {
        return img_item_alumno;
    }

    public void setImg_item_alumno(ImageView img_item_alumno) {
        this.img_item_alumno = img_item_alumno;
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }

    public AlumnoViewHolder(@NonNull View itemView, ListaAlumnosAdapter listaProductosAdapter) {
        super(itemView);
        txt_item_nombre = (TextView) itemView.findViewById(R.id.txt_item_nombre);
        txt_item_curso = (TextView) itemView.findViewById(R.id.txt_item_curso);
        img_item_alumno = (ImageView) itemView.findViewById(R.id.img_item_alumno);
       //-----------------------------------------------------------------------------
        lpa = listaProductosAdapter;
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

    public ListaAlumnosAdapter getLpa() {
        return lpa;
    }

    public void setLpa(ListaAlumnosAdapter lpa) {
        this.lpa = lpa;
    }

    @Override
    public void onClick(View view) {
        int posicion = getLayoutPosition();
        Alumno p = lpa.getAlumnos().get(posicion);
        Intent intent = new Intent(lpa.getContexto(), DetallesAlumnoActivity.class);
        intent.putExtra(EXTRA_ALUMNO_ITEM,p);
        img_item_alumno.buildDrawingCache();
        Bitmap foto_bm = img_item_alumno.getDrawingCache();
        intent.putExtra(EXTRA_ALUMNO_IMAGEN, ImagenesBlobBitmap.bitmap_to_bytes_png(foto_bm));
        intent.putExtra(EXTRA_POSICION_CASILLA, posicion);
        //  lpa.getContexto().startActivity(intent);
        Context contexto = lpa.getContexto();
        ((MostrarAlumnos) contexto).startActivityForResult(intent, MostrarAlumnos.PETICION1);
    }
}
