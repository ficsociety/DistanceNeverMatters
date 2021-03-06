package apm.muei.distancenevermatters.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.LocaleManager.LocaleHelper;
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
        implements GameRecyclerAdapter.OnGameDetailsListener, NavigationView.OnNavigationItemSelectedListener {

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

    private DrawerLayout mainLayout;
    private LinearLayout contentLayout;
    private GlobalVars gVars;

    ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.navigation_view);
        mainLayout = findViewById(R.id.drawer_layout);
        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_main, null);
        mainLayout.addView(contentLayout);
        ButterKnife.bind(this);
        gson = new GsonBuilder().create();
        gVars = GlobalVars.getInstance();


        // Be sure to ALWAYS set up the support action bar, or else getSupportActionBar could return null
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupMenu();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        if (fragmentContainer != null) {
            if(fragment == null){
                fragment = new MainFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.mainFrameLFragContainer, fragment).commit();
            }
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

    private void setupMenu(){
        toggle = new ActionBarDrawerToggle(
                this, mainLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mainLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLFragContainer, newFragment, "GAME_DETAIL");
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = new Intent(this, GameStateService.class);
        startService(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i = new Intent(this, GameStateService.class);
        stopService(i);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_sign_out:
                CharSequence text = "Regresando a login";
                Toast toast = Toast.makeText(this.getApplicationContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                singOut();
                return true;
            case R.id.nav_find:
                Intent intent = new Intent(this, FindGameActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                showProfile();
                return true;
            case R.id.nav_languaje:
                changeLanguage();
                return true;
            default:
                return false;
        }
    }

    private void singOut() {
        // Firebase sign out
        gVars.getmAuth().signOut();

        // Google sign out
        gVars.getSignInClient().signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void showProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void changeLanguage(){
        final String languages[] = new String[] {"es", "en"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.Seleccionar_idioma);
        builder.setItems(languages, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocaleHelper.setLocale(MainActivity.this, languages[which]);
                finish();
                startActivity(getIntent());
            }
        });
        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onBackPressed() {
        final GameDetailsFragment fragment = (GameDetailsFragment) getSupportFragmentManager().findFragmentByTag("GAME_DETAIL");

        if (fragment == null) {
            super.onBackPressed();
        }
    }

    public void onBack() {
        setupMenu();
        super.onBackPressed();
    }
}