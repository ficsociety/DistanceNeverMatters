package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameCreatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle("Partida 1");
        setContentView(R.layout.activity_game_created);

        Button button1 = (Button) findViewById(R.id.gcreaFABshare);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Toast.makeText(getApplicationContext(),
                        "Compartiendo código.", Toast.LENGTH_LONG).show();

            }
        });


    }
}
