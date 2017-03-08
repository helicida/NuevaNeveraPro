package test.minevera;

/**
 * Created by mireia on 8/01/17.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

import nl.littlerobots.cupboard.tools.provider.UriHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class DataManager {

    private static UriHelper URI_HELPER = UriHelper.with(ContentProvider.AUTHORITY);
    private static Uri RECETA_URI = URI_HELPER.getUri(Receta.class);


    public static void guardarRecetas(Receta recetas, Context context) {
       cupboard().withContext(context).put(RECETA_URI, Receta.class, recetas);
    }

    public static void borrarReceta(Context context) {
        cupboard().withContext(context).delete(RECETA_URI, "_id > ?", "0");
    }

    public static CursorLoader getCursorLoader(Context context) {
        return new CursorLoader(context, RECETA_URI, null, null, null, null);
    }

}