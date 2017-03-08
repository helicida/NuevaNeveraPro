package layout;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import test.minevera.ApiRecetas;
import test.minevera.DetallesRecetas;
import test.minevera.R;
import test.minevera.Receta;
import test.minevera.databinding.FragmentRecetasBinding;

/**
 * Created by mireia on 3/01/17.
 */
public class FragmentRecetasImpl extends Fragment {

    private FragmentRecetasBinding binding;

    private List <Receta> items;
    private RecetasAdapter adapter;
    public GridView gvRecetas;

    public FragmentRecetasImpl() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        //Cada vez que se ejecuta descargamos las recetas
        descargarRecetas();

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Conectamos la vista con el layout
        //C
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recetas,container,false);
        View view = binding.getRoot();

        //Instanciamos la list view
        gvRecetas = (GridView) view.findViewById(R.id.gvRecetas);
        items = new ArrayList<>();
        adapter = new RecetasAdapter (
                getContext(),
                0,
                items);

        gvRecetas.setAdapter(adapter);

        // Al pulsar en una posicion del listView se ejecuta el onClick
        gvRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent details = new Intent(getContext(), DetallesRecetas.class);
                details.putExtra("receta", items.get(position));
                startActivity(details);
            }
        });

        return view;
    }


    //Esto descargara las recetas de la api de internet
    private void descargarRecetas() {
        RefreshAsyncTask refreshAsyncTask = new RefreshAsyncTask();
        refreshAsyncTask.execute();

    }



    class RefreshAsyncTask extends AsyncTask<Void, Void, ArrayList<Receta>> {

        @Override
        protected ArrayList<Receta> doInBackground(Void... voids) {

            ApiRecetas api = new ApiRecetas();
            ArrayList<Receta> recetas = api.getMeals();

            Log.d("DEBUG", recetas != null ? recetas.toString() : null);

            return recetas;
        }
        //CLase que sirve para aplicar los ajustes
        @Override
        protected void onPostExecute(ArrayList<Receta> recetas) {
            super.onPostExecute(recetas);
            adapter.clear();

            for (int i = 0; i < recetas.size(); ++i) {
                adapter.add(recetas.get(i));
            }
            adapter.notifyDataSetChanged();

        }
    }

}