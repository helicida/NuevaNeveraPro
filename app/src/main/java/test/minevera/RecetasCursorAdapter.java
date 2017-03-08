package test.minevera;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import test.minevera.databinding.RecetasRowBinding;

/**
 * Created by mireia on 10/01/17.
 */

public class RecetasCursorAdapter extends CupboardCursorAdapte <Receta> {

    public RecetasCursorAdapter(Context context, Class<Receta> entityClass) {
        super(context, entityClass);
    }

    @Override
    public View newView(Context context, Receta model, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        RecetasRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.recetas_row, parent, false);

        return binding.getRoot();
    }

    @Override
    public void bindView(View view, Context context, Receta receta) {

        RecetasRowBinding binding = DataBindingUtil.getBinding(view);

        binding.adapterNombreReceta.setText(receta.getNombreReceta());


        Glide.with(context).
                load(receta.getImagen()).
                into(binding.adapterImagen);
    }
}

