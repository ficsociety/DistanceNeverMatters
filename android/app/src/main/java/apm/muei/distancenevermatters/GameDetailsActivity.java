package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle("Partida 1");
        setContentView(R.layout.activity_details_game);

        Button button1 = (Button) findViewById(R.id.buttonInvit);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(),
                        "Invitar usuarios a partida.", Toast.LENGTH_LONG).show();

            }
        });
        Button button2 = (Button) findViewById(R.id.buttonDelGame);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(),
                        "Eliminar la partida.", Toast.LENGTH_LONG).show();

            }
        });

    }
}
