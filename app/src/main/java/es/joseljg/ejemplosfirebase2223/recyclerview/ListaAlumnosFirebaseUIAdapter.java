package es.joseljg.ejemplosfirebase2223.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import es.joseljg.ejemplosfirebase2223.R;
import es.joseljg.ejemplosfirebase2223.clases.Alumno;

public class ListaAlumnosFirebaseUIAdapter extends FirebaseRecyclerAdapter<Alumno, AlumnoFirebaseUIViewHolder> {

    private Context contexto = null;
    public ListaAlumnosFirebaseUIAdapter(@NonNull FirebaseRecyclerOptions<Alumno> options, Context contexto) {
        super(options);
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AlumnoFirebaseUIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_alumno, parent, false);
        AlumnoFirebaseUIViewHolder evh = new AlumnoFirebaseUIViewHolder(mItemView, contexto);
        return evh;
    }

    @Override
    protected void onBindViewHolder(@NonNull AlumnoFirebaseUIViewHolder holder, int position, @NonNull Alumno model) {
        Alumno p = (Alumno) model;
        holder.getTxt_item_nombre().setText( p.getNombre());
        holder.getTxt_item_curso().setText(String.valueOf(p.getCurso()));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }



}
