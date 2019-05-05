package apm.muei.distancenevermatters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.fragments.UnityFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity
        implements UnityFragment.OnUnityFragmentInteractionListener {

    private UnityPlayer unityPlayer;
    private boolean unityScreen = true;

    @BindView(R.id.gameFragmentContainer)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // Create the UnityPlayer
        Log.d("Weird", "Creating UnityPlayer");
        unityPlayer = new UnityPlayer(this);
        int glesMode = unityPlayer.getSettings().getInt("gles_mode", 1);
        boolean trueColor888 = false;
        unityPlayer.init(glesMode, trueColor888);

        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        // TODO change
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton quitFab = findViewById(R.id.quitFab);
        quitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
            }
        });

        if (frameLayout != null) {

            if (savedInstanceState != null) {
                    return;
            }

            UnityFragment unityFragment = UnityFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                    .add(R.id.gameFragmentContainer, unityFragment);
            transaction.commit();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (unityPlayer != null) {
            unityPlayer.windowFocusChanged(hasFocus);
        }
    }

    @Override
    public UnityPlayer getUnityPlayer() {
        return unityPlayer;
    }
}
