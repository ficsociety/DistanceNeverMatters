package apm.muei.distancenevermatters.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
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
import apm.muei.distancenevermatters.services.GameStateService;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements GameRecyclerAdapter.OnGameDetailsListener {

    private List<GameDetailsDto> gameDtoList = new ArrayList<>();
    private GameDetailsDto gameDetails;
    private Gson gson;

    MainFragment fragment = null;

    @BindView(R.id.mainToolbar)
    Toolbar toolbar;

    @BindView(R.id.mainFrameLFragContainer)
    FrameLayout fragmentContainer;

    @BindView(R.id.mainProgressBar)
    ProgressBar progressBar;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gson = new GsonBuilder().create();


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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    @Override
    public void fetchGames(final SwipeRefreshLayout swipeRefresh) {
        WebService.getGames(getApplicationContext(), new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                gameDtoList = Arrays.asList(gson.fromJson(result, GameDetailsDto[].class));
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

    public void setGameDtoList(List<GameDetailsDto> gameDtoList) {
        this.gameDtoList = gameDtoList;
    }

    public GameDetailsDto getGameDetails() { return gameDetails; }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent(this, GameStateService.class);
        startService(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i = new Intent(this, GameStateService.class);
        stopService(i);
    }

    /*
    TODO Comportamiento back en main fragment vs details fragment

    @Override
    public void onBackPressed() {
    }
    */
}
