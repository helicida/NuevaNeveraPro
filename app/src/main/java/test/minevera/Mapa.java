package test.minevera;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

//Imports para el Overlise
import android.util.DisplayMetrics;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;

import layout.RecetasAdapter;
import test.minevera.databinding.FragmentMisRecetasBinding;

public class Mapa extends Fragment {

    private MapView map;
    private View view;
    //Variables para el overlise
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;

    // Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference referenciaListaRecetas;

    // Recetas
    private List<Receta> items = new ArrayList<>();

    public Mapa() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mapa, container, false);
        map = (MapView) view.findViewById(R.id.map);

        initializeMap();
        setZoom();
        setOverlays();
        map.invalidate();

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

                Zonas auxZonas = new Zonas();

                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).getArea() != null && items.get(i).getArea() != ""){
                        auxZonas.setZone(items.get(i).getArea());
                        GeoPoint estationpoint = new GeoPoint(auxZonas.returnLat(), auxZonas.returnLong());
                        Marker startMaker = new Marker(map);
                        startMaker.setPosition(estationpoint);
                        startMaker.setTitle(items.get(i).getNombreReceta());
                        map.getOverlays().add(startMaker);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getContext(), "Error descargando las recetas", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void initializeMap() {
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        map.setTilesScaledToDpi(true);
        map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(true);
    }

    private void setZoom() {
               //  Setteamos el zoom al mismo nivel y ajustamos la posición a un geopunto
        mapController = map.getController();
        mapController.setZoom(25);
    }


    private void setOverlays() {
        final DisplayMetrics dm = getResources().getDisplayMetrics();

        myLocationOverlay = new MyLocationNewOverlay(getContext(),new GpsMyLocationProvider(getContext()),map);

        myLocationOverlay.enableMyLocation();

        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo( myLocationOverlay.getMyLocation());
            }
        });

        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mMinimapOverlay = new MinimapOverlay(getContext(), map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);

        mCompassOverlay = new CompassOverlay(
                getContext(),
                new InternalCompassOrientationProvider(getContext()),
                map
        );
        mCompassOverlay.enableCompass();

        map.getOverlays().add(myLocationOverlay);
        map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
    }
}
