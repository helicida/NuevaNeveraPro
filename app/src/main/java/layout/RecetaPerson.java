package layout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import test.minevera.R;
import test.minevera.Receta;

import static android.app.Activity.RESULT_OK;

public class RecetaPerson extends Fragment {

    private ImageView recetaImage;
    private Intent takePictureIntent;

    //Objeto
    Receta receta = new Receta();

    public RecetaPerson() {
    }

    private String pathFotoTemporal;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_receta_person, container, false);
        Button guardar = (Button) view.findViewById(R.id.guardarReceta);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarReceta();
            }
        });

        recetaImage = (ImageView) view.findViewById(R.id.ImagenRecetas);
        recetaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        pathFotoTemporal = "file:" + image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {

            if (requestCode == REQUEST_TAKE_PHOTO) {
                if (resultCode == RESULT_OK) {

                    Glide.with(getContext()).load(pathFotoTemporal).into(recetaImage);
                    receta.setImagen(pathFotoTemporal);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void GuardarReceta() {
        //Botones ect
        EditText NombreReceta = (EditText) view.findViewById(R.id.nombreRecetaTexto);
        EditText RecetaTexto = (EditText) view.findViewById(R.id.recetaTextore);
        EditText Ingredients = (EditText) view.findViewById(R.id.ingredientesTexto);
        EditText Categoria = (EditText) view.findViewById(R.id.categoriaTexto);
        EditText Area = (EditText) view.findViewById(R.id.areaTexto);

        //Pasamos lo introducido en los Text
        receta.setNombreReceta(NombreReceta.getText().toString());
        receta.setArea(Area.getText().toString());
        receta.setCategoria(Categoria.getText().toString());
        receta.setIngredientes(Ingredients.getText().toString());
        receta.setTextoReceta(RecetaTexto.getText().toString());
        receta.setImagen(recetaImage.toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String key = receta.getNombreReceta();
        final DatabaseReference myRef = database.getReference("nombreReceta " + key);
        myRef.setValue(receta);

    }
}
