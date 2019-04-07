package apm.muei.distancenevermatters.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import apm.muei.distancenevermatters.fragments.CreateGameFragment;
import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGameActivity extends AppCompatActivity {


    @BindView(R.id.createGameToolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        ButterKnife.bind(this);

        // Be sure to ALWAYS set up the support action bar, or else getSupportActionBar could return null
        setSupportActionBar(toolbar);

        if (findViewById(R.id.create_game_fragments) != null){

            if (savedInstanceState !=  null) {
                return;
            }

            CreateGameFragment createGameFragment = new CreateGameFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.create_game_fragments, createGameFragment).commit();
        }
    }


}