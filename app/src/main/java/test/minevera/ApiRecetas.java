package test.minevera;
import android.net.Uri;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mireia on 30/10/16.
 */

public class ApiRecetas {

        private static String url = "http://www.themealdb.com/api/json/v1/1/randomselection.php";
        private static String urlBusca = "http://www.themealdb.com/api/json/v1/1/search.php?s=";
        private static ArrayList<Receta> recetas = new ArrayList<>();

    //Esta clase se encarga de bajar la información de la Api



    public static ArrayList<Receta> getMeals() {

        recetas.clear();

        //Aqui pasa los parametros de configuración categoria y nombre de receta
        Uri builtUri = Uri.parse(url)
                .buildUpon()
                .build();

        String urls = builtUri.toString();

        try {
            String JsonResponse = HttpUtils.get(urls);

            JSONObject data = new JSONObject(JsonResponse);
            JSONArray jsonRecetas = data.getJSONArray("meals");

            //se cargan todos los parametros elejidos
            for (int i = 0; i < jsonRecetas.length(); i++) {
                Receta receta = new Receta();
                JSONObject object = jsonRecetas.getJSONObject(i);

                receta.setNombreReceta(object.getString("strMeal"));
                receta.setArea(object.getString("strArea"));

                if (object.has("strCategory"))
                    receta.setCategoria(object.getString("strCategory"));

                if (object.has("strInstructions")) {
                    receta.setTextoReceta(object.getString("strInstructions"));
                }
                if (object.has("strMealThumb")) {
                    receta.setImagen(object.getString("strMealThumb"));
                }

                boolean ingredientesCargados = false;   // Determina si ya se han listado todos los ingredientes
                int numeroIngredientes = 0;             // Pues eso, el numero de ingredientes

                while (ingredientesCargados == false) {
                    numeroIngredientes++;
                    String etiquetaIngrediente = "strIngredient" + numeroIngredientes;
                    String nombreIngrediente = "";

                    if (object.has(etiquetaIngrediente)) {
                        nombreIngrediente = object.getString(etiquetaIngrediente);

                        if(nombreIngrediente != null && !nombreIngrediente.equalsIgnoreCase("null") && !nombreIngrediente.equalsIgnoreCase("")){
                            receta.setIngredientes(nombreIngrediente + ", ");
                        }
                    }



                    if(!object.has("strIngredient" + (numeroIngredientes + 1))){
                        ingredientesCargados = true;
                    }
                }

                recetas.add(receta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recetas;
    }

    public static ArrayList<Receta> getMealsSearch(String name) {

        recetas.clear();

        //Aqui pasa los parametros de configuración categoria y nombre de receta
        String urls = urlBusca.concat(name.replace(" ", "%20"));

        try {
            String JsonResponse = HttpUtils.get(urls);

            JSONObject data = new JSONObject(JsonResponse);
            JSONArray jsonRecetas = data.getJSONArray("meals");

            //se cargan todos los parametros elegidos
            for (int i = 0; i < jsonRecetas.length(); i++) {
                Receta receta = new Receta();
                JSONObject object = jsonRecetas.getJSONObject(i);

                receta.setNombreReceta(object.getString("strMeal"));
                receta.setArea(object.getString("strArea"));

                if (object.has("strCategory"))
                    receta.setCategoria(object.getString("strCategory"));

                if (object.has("strInstructions")) {
                    receta.setTextoReceta(object.getString("strInstructions"));
                }
                if (object.has("strMealThumb")) {
                    receta.setImagen(object.getString("strMealThumb"));
                }

                // Determina si ya se han listado todos los ingredientes
                boolean ingredientesCargados = false;
                int numeroIngredientes = 0;
                while (ingredientesCargados == false) {
                    numeroIngredientes++;
                    String etiquetaIngrediente = "strIngredient" + numeroIngredientes;
                    String nombreIngrediente = "";

                    if (object.has(etiquetaIngrediente)) {
                        nombreIngrediente = object.getString(etiquetaIngrediente);

                        if(nombreIngrediente != null && !nombreIngrediente.equalsIgnoreCase("null") && !nombreIngrediente.equalsIgnoreCase("")){
                            receta.setIngredientes(nombreIngrediente + ", ");
                        }
                    }

                    if(!object.has("strIngredient" + (numeroIngredientes + 1))){
                        ingredientesCargados = true;
                    }
                }

                recetas.add(receta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recetas;
    }
}


