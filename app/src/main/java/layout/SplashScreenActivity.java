package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import test.minevera.MainActivity;

/**
 * Created by mireia on 3/01/17.
 */

public class SplashScreenActivity extends AppCompatActivity {

        FrameLayout FrameLayout;

        /**
         *  En este activity mostramos la pantalla de bienvenida
         *  Solo se muestra mientras el resto de la APP carga
         */

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
}
