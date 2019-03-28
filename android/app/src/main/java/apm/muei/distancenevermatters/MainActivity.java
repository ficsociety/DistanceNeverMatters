package apm.muei.distancenevermatters;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements GameListFragment.OnGameDetailsListener {

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @BindView(R.id.mainFrameLFragContainer)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (fragmentContainer != null) {

            // If we are being restored, return or else we could end up
            // with overlapping fragments
            if (savedInstanceState != null) {
                return;
            }

            setSupportActionBar(toolbar);

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
}
