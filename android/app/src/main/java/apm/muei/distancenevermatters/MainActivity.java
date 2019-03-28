package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity
        implements GameListFragment.OnGameDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.mainFragmentContainer) != null) {

            // If we are being restored, return or else we could end up
            // with overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            Toolbar toolbar = findViewById(R.id.mainToolbar);
            setSupportActionBar(toolbar);

            // Add the first fragment to the fragment container in the layout
            MainFragment firstFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFragmentContainer, firstFragment).commit();

        }
    }

    @Override
    public void onGameSelected() {
        // Swap fragments
        GameDetailsFragment newFragment = new GameDetailsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
