package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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

        if (findViewById(R.id.create_game_fragments) != null){

            if (savedInstanceState !=  null) {
                return;
            }

            setSupportActionBar(toolbar);

            CreateGameFragment createGameFragment = new CreateGameFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.create_game_fragments, createGameFragment).commit();
        }
    }


}
