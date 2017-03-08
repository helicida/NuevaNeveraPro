package layout;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import test.minevera.DetallesRecetas;
import test.minevera.R;
import test.minevera.Receta;
import test.minevera.databinding.FragmentMisRecetasBinding;

public class FragmentMisRecetas extends Fragment {

    // Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenciaListaRecetas;

    // Adapter
    private FragmentMisRecetasBinding binding;
    private RecetasAdapter adapter;
    GridView gridRecetas;

    // Recetas
    private List <Receta> items = new ArrayList<>();

    public FragmentMisRecetas() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Conectamos la vista con el layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mis_recetas, container, false);
        View view = binding.getRoot();

        //Instanciamos la list view
        gridRecetas = (GridView) view.findViewById(R.id.gvMisRecetas);

        adapter = new RecetasAdapter (
                getContext(),
                0,
                items);

        referenciaListaRecetas = database.getReference();

        // Read from the database
        referenciaListaRecetas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                items.clear();

                for( DataSnapshot i : dataSnapshot.getChildren()) {
                    Receta receta1 = i.getValue(Receta.class);
                    items.add(receta1);
                }

                gridRecetas.setAdapter(adapter);

                Toast.makeText(getContext(), "Se han bajado " + items.size() + " recetas", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Error descargando las recetas", Toast.LENGTH_SHORT).show();
            }
        });


        // Al pulsar en una posicion del listView se ejecuta el onClick
        binding.gvMisRecetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Receta receta = (Receta) parent.getItemAtPosition(position);
                Intent details = new Intent(getContext(), DetallesRecetas.class);
                details.putExtra("receta", receta);
                startActivity(details);
            }
        });

        return view;
    }
}
