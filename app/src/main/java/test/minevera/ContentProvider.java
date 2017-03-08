package test.minevera;

/**
 * Created by mireia on 8/01/17.
 */


import nl.littlerobots.cupboard.tools.provider.CupboardContentProvider;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class ContentProvider extends CupboardContentProvider{

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";

    static {
        cupboard().register(Receta.class);
    }

    public  ContentProvider() {
        super(AUTHORITY, 1);
    }

}

