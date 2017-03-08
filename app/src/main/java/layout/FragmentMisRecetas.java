package layout;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import test.minevera.DataManager;
import test.minevera.DetallesRecetas;
import test.minevera.R;
import test.minevera.Receta;
import test.minevera.RecetasCursorAdapter;
import test.minevera.databinding.FragmentMisRecetasBinding;

public class FragmentMisRecetas extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public FragmentMisRecetas() {
    }

    private RecetasCursorAdapter adapter;
    GridView gvRecetas;
    private FragmentMisRecetasBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(0,null,this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Conectamos la vista con el layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mis_recetas, container, false);
        View view = binding.getRoot();

        //Instanciamos la list view
        gvRecetas = (GridView) view.findViewById(R.id.gvRecetas);
        adapter = new RecetasCursorAdapter(getContext(), Receta.class);
        binding.gvRecetas.setAdapter(adapter);

        // Al pulsar en una posicion del listView se ejecuta el onClick
        binding.gvRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Receta receta = (Receta) parent.getItemAtPosition(position);
                Intent details = new Intent(getContext(), DetallesRecetas.class);
                details.putExtra("receta", receta);
                startActivity(details);
            }
        });

        getLoaderManager().initLoader(0, null, this);
        return view;
    }


   /* //Esto descargara las recetas de la api de internet
    private void descargarRecetas() {
        FragmentMisRecetas.RefreshAsyncTask refreshAsyncTask = new FragmentMisRecetas.RefreshAsyncTask();
        refreshAsyncTask.execute();

    }*/



    /*class RefreshAsyncTask extends AsyncTask<Void, Void, ArrayList<Receta>> {

        @Override
        protected ArrayList<Receta> doInBackground(Void... voids) {

            ApiRecetas api = new ApiRecetas();
            ArrayList<Receta> recetas = api.getMeals();

            Log.d("DEBUG", recetas != null ? recetas.toString() : null);

            return recetas;
        }

    }*/

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return DataManager.getCursorLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
