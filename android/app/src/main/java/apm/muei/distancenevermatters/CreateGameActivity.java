package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CreateGameActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        if (findViewById(R.id.create_game_fragments) != null){

            if (savedInstanceState !=  null) {
                return;
            }

            CreateGameFragment createGameFragment = new CreateGameFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.create_game_fragments, createGameFragment).commit();
        }
    }


}
