package apm.muei.distancenevermatters.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import apm.muei.distancenevermatters.adapters.GameRecyclerAdapter;
import apm.muei.distancenevermatters.fragments.GameDetailsFragment;
import apm.muei.distancenevermatters.fragments.MainFragment;
import apm.muei.distancenevermatters.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements GameRecyclerAdapter.OnGameDetailsListener {

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @BindView(R.id.mainFrameLFragContainer)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Be sure to ALWAYS set up the support action bar, or else getSupportActionBar could return null
        setSupportActionBar(toolbar);

        if (fragmentContainer != null) {

            // If we are being restored, return or else we could end up
            // with overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            // Add the first fragment to the fragment container in the layout
            MainFragment firstFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFrameLFragContainer, firstFragment).commit();

        }
    }

    @Override
    public void onGameSelected() {
        // Swap fragments
        GameDetailsFragment newFragment = new GameDetailsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLFragContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    TODO Comportamiento back en main fragment vs details fragment

    @Override
    public void onBackPressed() {
    }
    */
}
