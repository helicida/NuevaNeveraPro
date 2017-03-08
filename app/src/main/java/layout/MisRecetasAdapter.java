package layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import test.minevera.R;
import test.minevera.Receta;

/**
 * Created by mireia on 8/01/17.
 */


public class MisRecetasAdapter extends ArrayAdapter<Receta> implements Serializable {

    public MisRecetasAdapter(Context context, int resource, List<Receta> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        // Obtenim l'objecte en la possició corresponent
        Receta receta = getItem(position);;

        // Mirem a veure si la View s'està reusant, si no es així "inflem" la View
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_mis_recetas_row, parent, false);
        }

        // Unim el codi en les Views del Layout
        TextView nombreCarta = (TextView) convertView.findViewById(R.id.adapterNombreReceta);
        ImageView imagenReceta = (ImageView) convertView.findViewById(R.id.adapterImagen);

        // Fiquem les dades dels objectes (provinents del JSON) en el layout
        nombreCarta.setText(receta.getNombreReceta());

        Glide.with(getContext()).
                load(receta.getImagen()).
                into(imagenReceta);

        // Retornem la View replena per a mostrarla
        return convertView;
    }
}
