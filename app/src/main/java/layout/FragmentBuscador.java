package layout;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import test.minevera.ApiRecetas;
import test.minevera.DetallesRecetas;
import test.minevera.MainActivity;
import test.minevera.R;
import test.minevera.Receta;
import test.minevera.databinding.FragmentBuscadorBinding;

public class FragmentBuscador extends Fragment {

    private FragmentBuscadorBinding binding;

    private List<Receta> items;
    private RecetasAdapter adapter;
    public GridView gvRecetas;
    public TextView noEncontrado;

    public FragmentBuscador() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        setHasOptionsMenu(true);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Conectamos la vista con el layout
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_buscador,container,false);
        View view = binding.getRoot();

        //Instanciamos la list view
        gvRecetas = (GridView) view.findViewById(R.id.gridBuscador);
        noEncontrado = (TextView) view.findViewById(R.id.noEncontrado);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){ //Afegim una opcio "Refresh" al menu del fragment
        super.onCreateOptionsMenu(menu, inflater);

        // Limpiamos el menú
        menu.clear();

        // Y creamos el suyo propio con un buscador
        inflater.inflate(R.menu.buscador, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView buscador = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        buscador.setMaxWidth(Integer.MAX_VALUE);
        buscador.setQueryHint("Example: Roasted Chicken");
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, buscador);
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                descargarRecetas(query);
                comprobarResultados();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                descargarRecetas(newText);
                comprobarResultados();
                return false;
            }
        });
    }

    public void comprobarResultados(){

        if(items.size() > 0){
            noEncontrado.setVisibility(View.INVISIBLE);
        }
        else{
            noEncontrado.setVisibility(View.VISIBLE);
        }
    }

    //Esto descargara las recetas de la api de internet
    private void descargarRecetas(String textoABuscar) {

        RefreshAsyncTask refreshAsyncTask = new RefreshAsyncTask();
        refreshAsyncTask.textoABuscar = textoABuscar;
        refreshAsyncTask.execute();
    }

    class RefreshAsyncTask extends AsyncTask<Void, Void, ArrayList<Receta>> {

        String textoABuscar = "";

        @Override
        protected ArrayList<Receta> doInBackground(Void... voids) {

            ApiRecetas api = new ApiRecetas();
            ArrayList<Receta> recetas;

            if(textoABuscar.equalsIgnoreCase("")){
                recetas = api.getMeals();
            }
            else{
                recetas = api.getMealsSearch(textoABuscar);
            }

            //Log.d("DEBUG", recetas != null ? recetas.toString() : null);
            return recetas;
        }

        //CLase que sirve para aplicar los ajustes
        @Override
        protected void onPostExecute(ArrayList<Receta> recetas) {
            super.onPostExecute(recetas);

            adapter.clear();
            items.clear();

            for (int i = 0; i < recetas.size(); ++i) {
                adapter.add(recetas.get(i));
            }

            adapter.notifyDataSetChanged();
        }
    }
}
