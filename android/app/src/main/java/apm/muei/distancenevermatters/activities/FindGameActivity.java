package apm.muei.distancenevermatters.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import apm.muei.distancenevermatters.LocaleManager.LocaleHelper;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.fragments.CreateGameFragment;
import apm.muei.distancenevermatters.fragments.FindGameFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindGameActivity extends AppCompatActivity {

    @BindView(R.id.findGameToolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (findViewById(R.id.find_game_fragments) != null){

            if (savedInstanceState !=  null) {
                return;
            }

            FindGameFragment findGameFragment = new FindGameFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.find_game_fragments, findGameFragment).commit();

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBack();
            }
        });
    }

    @Override
    public void onBackPressed(){
    }

    public void onBack(){
        super.onBackPressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}
