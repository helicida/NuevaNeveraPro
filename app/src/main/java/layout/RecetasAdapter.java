package layout;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import test.minevera.R;
import test.minevera.Receta;
import test.minevera.databinding.RecetasRowBinding;

/**
 * Created by mireia on 3/01/17.
 */
public class RecetasAdapter extends ArrayAdapter<Receta> implements Serializable {

    public RecetasAdapter(Context context, int resource, List<Receta> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Obtenim l'objecte en la possició corresponent
        Receta receta = getItem(position);
        RecetasRowBinding binding;
        binding = null;

        // Mirem a veure si la View s'està reusant, si no es així "inflem" la View
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            binding = DataBindingUtil.inflate(inflater,R.layout.recetas_row,parent,false);

            }else binding = DataBindingUtil.getBinding(convertView);

        // Fiquem les dades dels objectes (provinents del JSON) en el layout

        binding.adapterNombreReceta.setText(receta.getNombreReceta());


        Glide.with(getContext()).
                load(receta.getImagen()).
                into(binding.adapterImagen);

        // Retornem la View replena per a mostrarla
        return binding.getRoot();
    }



}

