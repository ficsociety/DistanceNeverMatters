package apm.muei.distancenevermatters.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import apm.muei.distancenevermatters.LocaleManager.LocaleHelper;
import apm.muei.distancenevermatters.entities.Marker;
import apm.muei.distancenevermatters.entities.Model;
import apm.muei.distancenevermatters.fragments.CreateGameFragment;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.fragments.GameCreatedFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGameActivity extends AppCompatActivity{

    private Map<Marker, Model> markerModel = new HashMap<>();

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

    public Map<Marker, Model> getMarkerModel() {
        return markerModel;
    }

    public void putMarkerModel(Marker marker, Model model){
        this.markerModel.put(marker, model);
    }

    public void removeMarkerModel(Marker marker){
        this.markerModel.keySet().remove(marker);
    }

    @Override
    public void onBackPressed() {

    }

    public void onBack() {
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}
