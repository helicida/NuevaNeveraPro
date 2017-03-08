package test.minevera;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetallesRecetas extends AppCompatActivity {

    private Intent intent ;
    private Receta receta ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_recetas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Referencias
        TextView texto = (TextView) findViewById(R.id.Instrucciones);
        TextView tipo= (TextView) findViewById(R.id.adapterCategoria);
        TextView area = (TextView) findViewById(R.id.adapterArea);
        ImageView imagen = (ImageView) findViewById(R.id.imagenToolbar);
        TextView Ingredientes = (TextView)findViewById(R.id.Ingredientes) ;

        // Recogemos el intent y cargamos la receta que hemos pasado desde Recetas del dia
        intent = this.getIntent();

        if (intent != null) {

            receta = (Receta) intent.getSerializableExtra("receta");

            if (receta!= null) {

                texto.setText("Instructions : " + receta.getTextoReceta());
                setTitle(receta.getNombreReceta());
                area.setText("Area : " + receta.getArea());
                tipo.setText("Category : "+ receta.getCategoria());
                Ingredientes.setText("Ingredients : " + receta.getIngredientes());

                Glide.with(this).
                        load(receta.getImagen()).
                        fitCenter().
                        into(imagen);
            }


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    DataManager.guardarRecetas(receta, getApplicationContext());
                }
            });

        }
        else{
            Toast.makeText(this, "Error al cargar la carta", Toast.LENGTH_SHORT).show();
        }

    }



}
