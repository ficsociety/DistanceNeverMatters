package apm.muei.distancenevermatters.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import apm.muei.distancenevermatters.adapters.GameRecyclerAdapter;
import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.User;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.fragments.GameDetailsFragment;
import apm.muei.distancenevermatters.fragments.MainFragment;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements GameRecyclerAdapter.OnGameDetailsListener {

    private List<GameDetailsDto> gameDtoList = new ArrayList<>();

    MainFragment firstFragment = null;

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @BindView(R.id.mainFrameLFragContainer)
    FrameLayout fragmentContainer;

    @BindView(R.id.mainProgressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // Be sure to ALWAYS set up the support action bar, or else getSupportActionBar could return null
        setSupportActionBar(toolbar);
        System.out.println("activity first call");

        if (fragmentContainer != null) {
            // If we are being restored, return or else we could end up
            if (savedInstanceState != null) {
                return;
            }
            // Add the first fragment to the fragment container in the layout
            firstFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFrameLFragContainer, firstFragment).commit();
        }

        WebService.getGames(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                // TODO Eliminar estos datos mockeados
                User user1 = new User("roi");
                User user2 = new User("jordiENP");
                gameDtoList.add(new GameDetailsDto("Prueba 1", "descripcion", user1, GameState.PLAYING));
                gameDtoList.add(new GameDetailsDto("Prueba 2", "descripcion", user1, GameState.PLAYING));
                gameDtoList.add(new GameDetailsDto("Prueba 3", "descripcion", user2, GameState.PLAYING));
                gameDtoList.add(new GameDetailsDto("Prueba 4", "descripcion", user1, GameState.PAUSED));
                gameDtoList.add(new GameDetailsDto("Prueba 5", "descripcion", user1, GameState.PAUSED));
                gameDtoList.add(new GameDetailsDto("Prueba 6", "descripcion", user2, GameState.PAUSED));
                gameDtoList.add(new GameDetailsDto("Prueba 7", "descripcion", user2, GameState.PAUSED));
                gameDtoList.add(new GameDetailsDto("Prueba 8", "descripcion", user1, GameState.PAUSED));
                if (fragmentContainer != null) {
                    // If we are being restored, return or else we could end up
                    // Add the first fragment to the fragment container in the layout
                    MainFragment nextFragment = new MainFragment();
                    getSupportFragmentManager().beginTransaction().detach(firstFragment)
                            .add(R.id.mainFrameLFragContainer, nextFragment).commit();

                }
                progressBar.setVisibility(View.GONE);
            }
        });
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

    public List<GameDetailsDto> getGameDtoList() {
        return gameDtoList;
    }

    /*
    TODO Comportamiento back en main fragment vs details fragment

    @Override
    public void onBackPressed() {
    }
    */
}
