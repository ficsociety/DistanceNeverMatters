package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);


        Button btnCreateGame = (Button) findViewById(R.id.btn_create_game);
        btnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(),
                        "Crear Partida.", Toast.LENGTH_LONG).show();

            }
        });

        Button btnAddMarker = (Button) findViewById(R.id.btn_add_marker_model);
        btnAddMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(),
                        "Añadir marcador-modelo.", Toast.LENGTH_LONG).show();

            }
        });


    }
}
