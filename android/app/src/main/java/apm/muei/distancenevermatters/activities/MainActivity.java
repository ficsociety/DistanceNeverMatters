package apm.muei.distancenevermatters.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
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
    private GameDetailsDto gameDetails;

    MainFragment fragment = null;

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

        if (fragmentContainer != null) {
            // If we are being restored, return or else we could end up
            if (savedInstanceState != null) {
                return;
            }
            // Add the first fragment to the fragment container in the layout
            fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFrameLFragContainer, fragment).commit();
        }

        fetchGames(null);
    }

    @Override
    public void fetchGames(final SwipeRefreshLayout swipeRefresh) {
        WebService.getGames(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                // TODO Eliminar estos datos mockeados
                User user1 = new User("roi");
                User user2 = new User("morenito84");
                List<User> users = new ArrayList<>();
                users.add(new User("user1"));
                users.add(new User("user2"));
                users.add(new User("user3"));
                gameDtoList = new ArrayList<>();
                gameDtoList.add(new GameDetailsDto("Prueba 1", "descripcion 1", user1, new Date(), 1L, GameState.PLAYING, users));
                gameDtoList.add(new GameDetailsDto("Prueba 2", "descripcion 2", user1, new Date(), 2L, GameState.PLAYING, users));
                gameDtoList.add(new GameDetailsDto("Prueba 3", "descripcion 3", user2, new Date(), 3L, GameState.PLAYING, users));
                gameDtoList.add(new GameDetailsDto("Prueba 4", "descripcion 4", user1, new Date(), 4L, GameState.PAUSED, users));
                gameDtoList.add(new GameDetailsDto("Prueba 5", "descripcion 5", user1, new Date(), 5L, GameState.PAUSED, users));
                gameDtoList.add(new GameDetailsDto("Prueba 6", "descripcion 6", user2, new Date(), 6L, GameState.PAUSED, users));
                gameDtoList.add(new GameDetailsDto("Prueba 7", "descripcion 7", user2, new Date(), 7L, GameState.PAUSED, users));
                gameDtoList.add(new GameDetailsDto("Prueba 8", "descripcion 8", user1, new Date(), 8L, GameState.PAUSED, users));

                if (fragmentContainer != null) {
                    // If we are being restored, return or else we could end up
                    // Add the first fragment to the fragment container in the layout
                    MainFragment nextFragment = new MainFragment();
                    getSupportFragmentManager().beginTransaction().detach(fragment)
                            .add(R.id.mainFrameLFragContainer, nextFragment).commit();
                    fragment = nextFragment;
                }
                progressBar.setVisibility(View.GONE);

                if (swipeRefresh != null) {
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onGameSelected(GameDetailsDto gameDetails) {
        // Swap fragments
        this.gameDetails = gameDetails;
        GameDetailsFragment newFragment = new GameDetailsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLFragContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public List<GameDetailsDto> getGameDtoList() {
        return gameDtoList;
    }

    public GameDetailsDto getGameDetails() { return gameDetails; }

    /*
    TODO Comportamiento back en main fragment vs details fragment

    @Override
    public void onBackPressed() {
    }
    */
}
