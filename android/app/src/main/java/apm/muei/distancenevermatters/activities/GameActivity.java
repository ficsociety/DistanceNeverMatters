package apm.muei.distancenevermatters.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unity3d.player.UnityPlayer;

import apm.muei.distancenevermatters.LocaleManager.LocaleHelper;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.Server.DiceResult;
import apm.muei.distancenevermatters.Server.Movement;
import apm.muei.distancenevermatters.Server.ServerActions;
import apm.muei.distancenevermatters.Server.SocketUtils;
import apm.muei.distancenevermatters.SharedPreference.PreferenceManager;
import apm.muei.distancenevermatters.entities.GameState;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.entities.dto.UpdateStateDto;
import apm.muei.distancenevermatters.fragments.DiceFragment;
import apm.muei.distancenevermatters.fragments.DiceHistoricFragment;
import apm.muei.distancenevermatters.fragments.UnityFragment;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.socket.emitter.Emitter;

import com.google.common.collect.EvictingQueue;

import java.util.Queue;

public class GameActivity extends AppCompatActivity
        implements UnityFragment.OnUnityFragmentInteractionListener {

    private UnityPlayer unityPlayer;
    private String gameDetails;
    private GameDetailsDto gameDetailsDto;
    //private SocketUtils socketUtils;
    private Gson gson = new GsonBuilder().create();

    private String user;
    private long code;
    private SocketUtils socketUtils;

    private Queue<DiceResult> fifo = EvictingQueue.create(20);


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
        gameDetailsDto = gson.fromJson(gameDetails, GameDetailsDto.class);

        user = PreferenceManager.getInstance().getUserName();
        code = gameDetailsDto.getCode();

        // Se crea el socket e inicializamos el listener para recibir los movimientos
        socketUtils = SocketUtils.getInstance();
        socketUtils.connect();
        socketUtils.join(code);
        socketUtils.getSocket().on(ServerActions.RECEIVEDICE, onDiceResult);

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
                leaveGame();

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

    @OnClick(R.id.gameFabBtnDice)
    public void goToDice() {
        DiceFragment newFragment = new DiceFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.gameFragmentContainer, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void goToDiceHistoric() {
        DiceHistoricFragment newFragment = new DiceHistoricFragment();

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
        this.socketUtils.disconnect();
    }

    @Override
    public void onBackPressed() {

    }

    public void leaveGame() {
        String userName = PreferenceManager.getInstance().getUserName();
        if ((gameDetailsDto.getMaster().getUid().equals(userName))) {
            UpdateStateDto stateDto = new UpdateStateDto(GameState.PAUSED, gameDetailsDto.getCode());
            WebService.changeGameState(getApplicationContext(), stateDto, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                }
            });
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBack() {
        super.onBackPressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public Queue<DiceResult> getFifo() {
        return fifo;
    }

    private Emitter.Listener onDiceResult = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DiceResult diceResult = gson.fromJson(args[0].toString(), DiceResult.class);
                    getFifo().add(diceResult);
                    if (!diceResult.getUser().equals(user)) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.eljugador) + " '" + diceResult.getUser() + "' " + getResources().getString(R.string.halanzado), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    public SocketUtils getSocketUtils() {
        return socketUtils;
    }
}
