package apm.muei.distancenevermatters.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import apm.muei.distancenevermatters.R;
import apm.muei.distancenevermatters.Server.Movement;
import apm.muei.distancenevermatters.Server.ServerActions;
import apm.muei.distancenevermatters.Server.SocketUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.emitter.Emitter;

public class UnityFragment extends Fragment {

    private OnUnityFragmentInteractionListener mListener;
    private SocketUtils socketUtils;
    private Gson gson;
    //private String user = "user"; // TODO inicializar bien
    private String user = "user2";
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

        Log.d("weird", "create unity fragment");
        ViewGroup frameLayout = rootView.findViewById(R.id.unityFrameLayout);
        if (frameLayout.getChildAt(0) == null) {
            FrameLayout.LayoutParams lp =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mUnityPlayer.getView(), 0, lp);
        }

        mUnityPlayer.requestFocus();

        // Se crea el socket e inicializamos el listener para recibir los movimientos
        socketUtils = SocketUtils.getInstance();
        socketUtils.connect();
        socketUtils.getSocket().on(ServerActions.RECEIVEMOVEMENT, onNewMovement);
        //TODO pasarle el usuario y el código de partida
        socketUtils.join("user", 7777); // Mock

        gson = new GsonBuilder().create();

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
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUnityFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        Log.d("Weird", "resuming");
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
        super.onDestroy();
        this.socketUtils.disconnect();
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
    }

    // Method for Unity
    public void getGameInfo() {
        String gameobjectName = "MapTarget";
        String method = "SetGameInfo";
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
        String arg =
                "{" +
                        "\"map\": \"map\"," +
                        "\"user\": \"user2\"," +
                        "\"tracked\": [" +
                        "{ \"target\": \"Chandelure\", \"model\": \"PurpleDragon\" }" +
                        "]," +
                        "\"external\": [" +
                        "{ \"model\": \"Umbreon\", \"user\": \"user\", \"target\": \"RPGHeroHP\" }" +
                        "]" +
                        "}";
        UnityPlayer.UnitySendMessage(gameobjectName, method, arg);
    }

    public void mockUpdate(String json) {
        String gameObject = "MapTarget";
        String method = "UpdateLocations";

        UnityPlayer.UnitySendMessage(gameObject, method, json);
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

                // TODO inicializar correctamente
                Movement movement = new Movement(user, target, distance, rotation);
                Log.d("UnitySockets", movement.toString());
                socketUtils.sendMovement(movement, 7777); // TODO replace mock code

            } else {
                // TODO inicializar sin rotación
                Movement movement = new Movement(user, target, null, null);
                socketUtils.sendMovement(movement, 7777); // TODO replace mock code
            }
        }
    }

    private Emitter.Listener onNewMovement = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO reemplazar por información de verdad
                    Log.d("UnitySockets", "Movement received");
                    Log.d("UnitySockets", args[0].toString());

                    // No transmitir nuestros propios movimientos
                    JsonParser parser = new JsonParser(); // TODO pasar a atributo
                    JsonObject updateInfo = parser.parse(args[0].toString()).getAsJsonObject();
                    if (!updateInfo.get("user").getAsString().equals(user)) {
                        Log.d("UnitySockets", "Updating");
                        mUnityPlayer.UnitySendMessage("MapTarget", "UpdateLocation", args[0].toString());
                    }
//                    Movement movement = gson.fromJson(args[0].toString(), Movement.class);
//                    Map<String, Float> distance = movement.getDistance();
//                    Map<String, Float> rotation = movement.getRotation();
//
//                    if (distance != null) {
//                        float x = distance.get("x") + 1;
//                        // TODO quitar update mock
//                        String json =
//                                "{ \"Target\": \"Chandelureuser2\"," +
//                                        "\"Distance\": { \"x\":" + x + ", \"y\": " + distance.get("y") + ",\"z\": " + distance.get("z") + "}," +
//                                        "\"Rotation\": { \"x\":" + rotation.get("x") + ", \"y\": " + rotation.get("y") + ",\"z\": " + rotation.get("z") + ",\"w\": " + rotation.get("w") + "}" +
//                                        "}";
//                        mUnityPlayer.UnitySendMessage("MapTarget", "UpdateLocation", json);
//                    }
                }
            });
        }
    };
}
