package test.minevera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import layout.FragmentBuscador;
import layout.FragmentMisRecetas;
import layout.FragmentRecetasImpl;
import layout.RecetaPerson;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer; // Drawer
    Fragment fragment = null;
    FloatingActionButton fab;

    @Override
    protected void onStart() {
        super.onStart();

        // Recuperamos los valores de las settings
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String color = sp.getString("listaColores", "Cyan");

        if(color.equalsIgnoreCase("black")){
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }
        else if(color.equalsIgnoreCase("gray")){
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        }
        else if(color.equalsIgnoreCase("cyan")){
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Permisos (solo Marshmallow y superior)
        pedirPermisoSD();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Aqui Puedes Crear tu receta Personalizada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fragment = new RecetaPerson();
                boolean transaccion = true;
                String tag = null;
                if(transaccion){

                    getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.content_main, fragment, tag)
                            .commit();
                }



            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Pondremos el Fragment que utilizaremos al iniciar la aplicación

        fragment = new FragmentRecetasImpl();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent a = new Intent(this, SettingsActivity.class);
            startActivity(a);
            return true;
        }
        if (id == R.id.deleteDB){
            DataManager.borrarReceta(getApplicationContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean transaccion = false; // Cuadno el boleano sea true, se cambiará a otro fragment
        String tag = null;

        if (id == R.id.Recetas_del_dia) {
            fragment = new FragmentRecetasImpl();
            transaccion = true;
        }
        if (id == R.id.Buscador) {
            fragment = new FragmentBuscador();
            transaccion = true;
        }
        if (id == R.id.Mis_Recetas_de_cocina) {
            fragment = new FragmentMisRecetas();
            transaccion = true;
        }
        if (id == R.id.CrearRecetaPropia){
            fragment = new RecetaPerson();
            transaccion = true;
        }

        if (id == R.id.MapadeRecetas){
            fragment = new Mapa();
            transaccion = true;
        }


        // Si el boleano es true llamamos al nuevo fragment
        if(transaccion){

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.content_main, fragment, tag)
                    .commit();

            item.setChecked(true);
        }

        drawer.closeDrawers();
        return true;
    }

    // Puede que se use al almacenar en caché algunas imagenes o la BBDD
    public void pedirPermisoSD(){

        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (!(permissionCheck2 == PackageManager.PERMISSION_GRANTED)) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {} else {
                // do request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 8);
            }
        }
    }
}