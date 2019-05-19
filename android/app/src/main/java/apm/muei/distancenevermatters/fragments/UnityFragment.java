package apm.muei.distancenevermatters.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unity3d.player.UnityPlayer;

import java.util.HashMap;
import java.util.Map;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.Server.Movement;
import apm.muei.distancenevermatters.Server.ServerActions;
import apm.muei.distancenevermatters.Server.SocketUtils;
import apm.muei.distancenevermatters.SharedPreference.PreferenceManager;
import apm.muei.distancenevermatters.activities.GameActivity;
import apm.muei.distancenevermatters.activities.MainActivity;
import apm.muei.distancenevermatters.entities.Player;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import io.socket.emitter.Emitter;


public class UnityFragment extends Fragment {

    private OnUnityFragmentInteractionListener mListener;
    private SocketUtils socketUtils;
    private GameDetailsDto gameDetails;
    private String user;
    private long code;
    private JsonParser parser = new JsonParser();
    protected UnityPlayer mUnityPlayer;
    public static UnityFragment instance; // Para poder acceder desde Unity

    public static UnityFragment getInstance() {
        if (instance == null) {
            instance = new UnityFragment();
        }

        return  instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_unity, container, false);

        FloatingActionButton quitFab = getActivity().findViewById(R.id.quitFab);
        FloatingActionButton diceFab = getActivity().findViewById(R.id.gameFabBtnDice);
        diceFab.show();
        quitFab.show();

        AppBarLayout appBar = getActivity().findViewById(R.id.gameAppBar);
        appBar.setVisibility(View.GONE);
        ViewGroup frameLayout = rootView.findViewById(R.id.unityFrameLayout);
        if (frameLayout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mUnityPlayer.getView(), 0, lp);
        }

        mUnityPlayer.requestFocus();

        // Usuario y código de partida
        user = PreferenceManager.getInstance().getUserName();
        code = gameDetails.getCode();

        // Se crea el socket e inicializamos el listener para recibir los movimientos
        socketUtils = SocketUtils.getInstance();
        socketUtils.getSocket().on(ServerActions.RECEIVEMOVEMENT, onNewMovement);
        socketUtils.getSocket().on(ServerActions.RECEIVEMASTERLEAVE, onMasterLeave);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  OnUnityFragmentInteractionListener) {
            OnUnityFragmentInteractionListener listener =
                    (OnUnityFragmentInteractionListener) context;
            mListener = listener;
            mUnityPlayer = listener.getUnityPlayer();
            gameDetails = new Gson().fromJson(listener.getGameDetails(), GameDetailsDto.class);
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUnityFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        // If Unity view is not set, set it
        // This can happen when blocking and unblocking mobile while in this screen
        FrameLayout frameLayout = getView().findViewById(R.id.unityFrameLayout);
        if (frameLayout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mUnityPlayer.getView(), 0, lp);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        mUnityPlayer.quit();
        if (gameDetails.getMaster().getUid().equals(PreferenceManager.getInstance().getUserName())){
            this.socketUtils.sendMasterLeave(true, gameDetails.getCode());
        }

        this.socketUtils.disconnect();

        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mUnityPlayer.stop();
        // We need to remove the view from the FrameLayout, or else app will crash next time we open this screen
        // Not putting the Unity view in the FrameLayout after the first creation DOES NOT WORK,
        // the app doesn't crash, but Unity isn't displayed
        ViewGroup unityView = ((ViewGroup) mUnityPlayer.getView().getParent());
        unityView.removeView(mUnityPlayer.getView());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    public interface OnUnityFragmentInteractionListener {
        UnityPlayer getUnityPlayer();
        String getGameDetails();
    }

    // Method for Unity
    public void getGameInfo() {
        String gameobjectName = "MapTarget";
        String method = "SetGameInfo";
        Log.d("UnitySockets", "getGameInfo");
        // Example JSON for user
//        String arg =
//                "{" +
//                    "\"map\": \"map\"," +
//                    "\"user\": \"user\"," +
//                    "\"tracked\": [" +
//                        "{ \"target\": \"Umbreon\", \"model\": \"RPGHeroHP\" }" +
//                    "]," +
//                    "\"external\": [" +
//                        "{ \"model\": \"PurpleDragon\", \"user\": \"user2\", \"target\": \"Chandelure\" }" +
//                    "]" +
//                "}";

        // Example JSON for user2
//        String arg =
//                "{" +
//                        "\"map\": \"map\"," +
//                        "\"user\": \"user2\"," +
//                        "\"tracked\": [" +
//                        "{ \"target\": \"Chandelure\", \"model\": \"PurpleDragon\" }" +
//                        "]," +
//                        "\"external\": [" +
//                        "{ \"model\": \"RPGHeroHP\", \"user\": \"user\", \"target\": \"Umbreon\" }" +
//                        "]" +
//                        "}";
        // Create game info JSON for Unity
        JsonObject json = new JsonObject();
        json.addProperty("map", gameDetails.getMap().getName());
        json.addProperty("user", user);

        JsonArray tracked = new JsonArray();
        JsonArray external = new JsonArray();
        // Clasify pairs of marker-models in tracked and external
        for (Player player : gameDetails.getPlayers()) {
            if (player.getUser().getUid().equals(user)) {
                // Tracked model
                JsonObject trackedTarget = new JsonObject();
                trackedTarget.addProperty("target", player.getMarker().getName());
                trackedTarget.addProperty("model", player.getModel().getName());
                tracked.add(trackedTarget);
            } else {
                // External model
                JsonObject externalModel = new JsonObject();
                externalModel.addProperty("model", player.getModel().getName());
                externalModel.addProperty("user", player.getUser().getUid());
                externalModel.addProperty("target", player.getMarker().getName());
                external.add(externalModel);
            }
        }

        json.add("tracked", tracked);
        json.add("external", external);

        String arg = json.toString();

        Log.d("UnitySockets", "GameInfo: " + arg);

        UnityPlayer.UnitySendMessage(gameobjectName, method, arg);
    }

    public void log(String message) {
        Log.d("UnitySockets", message);
    }

    public void sendLocationInfo(String json) {
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();

        for (JsonElement element : jsonArray) {
            // Crear y enviar cada movimiento
            JsonObject updateInfo = element.getAsJsonObject();
            String target = updateInfo.get("target").getAsString();
            Movement movement;

            if (!(updateInfo.get("distance") instanceof JsonNull)) {
                JsonObject dist = updateInfo.get("distance").getAsJsonObject();
                Map<String, Float> distance = new HashMap<>();
                distance.put("x", dist.get("x").getAsFloat());
                distance.put("y", dist.get("y").getAsFloat());
                distance.put("z", dist.get("z").getAsFloat());

                JsonObject rot = updateInfo.get("rotation").getAsJsonObject();
                Map<String, Float> rotation = new HashMap<>();
                rotation.put("x", rot.get("x").getAsFloat());
                rotation.put("y", rot.get("y").getAsFloat());
                rotation.put("z", rot.get("z").getAsFloat());
                rotation.put("w", rot.get("w").getAsFloat());

                // Inicializar con todos los datos
                movement = new Movement(user, target, distance, rotation);
                Log.d("UnitySockets", "Movement: " + movement.toString());

            } else {
                // Inicializar sin rotación
                movement = new Movement(user, target, null, null);
            }

            // Enviar movimiento
            socketUtils.sendMovement(movement, code);
        }
    }

    private Emitter.Listener onMasterLeave = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    boolean leave = new Gson().fromJson(args[0].toString(), Boolean.class);
                    if (leave){
                        mUnityPlayer.quit();
                        socketUtils.disconnect();
                        ((GameActivity)getActivity()).leaveGame();
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMovement = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // No transmitir nuestros propios movimientos
                    JsonObject updateInfo = parser.parse(args[0].toString()).getAsJsonObject();
                    if (!updateInfo.get("user").getAsString().equals(user)) {
                        Log.d("UnitySockets", "Updating with: " + args[0].toString());
                        mUnityPlayer.UnitySendMessage("MapTarget", "UpdateLocation", args[0].toString());
                    }
                }
            });
        }
    };
}
