package apm.muei.distancenevermatters.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.unity3d.player.UnityPlayer;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.Server.Movement;
import apm.muei.distancenevermatters.Server.ServerActions;
import apm.muei.distancenevermatters.Server.SocketUtils;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.fragments.DiceFragment;
import apm.muei.distancenevermatters.fragments.UnityFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

public class GameActivity extends AppCompatActivity
        implements UnityFragment.OnUnityFragmentInteractionListener {

    private UnityPlayer unityPlayer;
    private String gameDetails;
    //private SocketUtils socketUtils;


    @BindView(R.id.gameFragmentContainer)
    FrameLayout frameLayout;

    @BindView(R.id.quitFab)
    FloatingActionButton quitFab;

    @BindView(R.id.gameFabBtnDice)
    FloatingActionButton diceFab;

    @BindView(R.id.gameToolbar)
    Toolbar gameToolbar;

    @BindView(R.id.gameAppBar)
    AppBarLayout gameAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setSupportActionBar(gameToolbar);
        Intent intent = getIntent();

        gameDetails = intent.getStringExtra("gameDetails");
        //GameDetailsDto gameDetailsDto = new Gson().fromJson(gameDetails, GameDetailsDto.class);

        // Create the UnityPlayer
        unityPlayer = new UnityPlayer(this);
        int glesMode = unityPlayer.getSettings().getInt("gles_mode", 1);
        boolean trueColor888 = false;
        unityPlayer.init(glesMode, trueColor888);

        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        // TODO change

        FloatingActionButton quitFab = findViewById(R.id.quitFab);
        quitFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(GameActivity.this, MainActivity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
                onBack();
            }
        });

        //se crea el socket e inicializamos el listener para recibir los movimientos
//        socketUtils = SocketUtils.getInstance();
//        socketUtils.connect();
//        socketUtils.getSocket().on(ServerActions.RECEIVEMOVEMENT, onNewMovement);
        //TODO pasarle el usuario y el código de partida
        //socketUtils.join(user, código partida);

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


//    private Emitter.Listener onNewMovement = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    //TODO Aqui recibes args[0], que viene siendo el movimiento como string (json)
//                }
//            });
//        }
//    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (unityPlayer != null) {
            unityPlayer.windowFocusChanged(hasFocus);
        }
    }

    @OnClick(R.id.gameFabBtnDice)
    public void goToDice() {
        DiceFragment newFragment = new DiceFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.gameFragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public UnityPlayer getUnityPlayer() {
        return unityPlayer;
    }

    @Override
    public String getGameDetails() {
        return gameDetails;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        //this.socketUtils.disconnect();
    }

    @Override
    public void onBackPressed() {

    }

    public void onBack() {
        super.onBackPressed();
    }
}
