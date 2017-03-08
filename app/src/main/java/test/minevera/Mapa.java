package test.minevera;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

//Imports para el Overlise
import android.util.DisplayMetrics;
import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class Mapa extends Fragment {

    private MapView map;
    private View view;
    //Variables para el overlise
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;


    public Mapa() {
        // Required empty public constructor
    }

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

        //map.invalidate();





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
