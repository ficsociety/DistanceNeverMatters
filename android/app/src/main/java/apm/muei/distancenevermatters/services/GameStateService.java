package apm.muei.distancenevermatters.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.fragments.GameListFragment;
import apm.muei.distancenevermatters.volley.VolleyCallback;
import apm.muei.distancenevermatters.volley.WebService;

public class GameStateService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    public Gson gson;
    List<GameDetailsDto> gameDtoList = new ArrayList<>();

    public GameStateService() {
        super();
    }

    @Override
    public void onCreate() {
        gson = new GsonBuilder().create();
        handler = new Handler();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable = new Runnable() {
            public void run() {
                WebService.getGames(getApplicationContext(), new VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        Bundle extra = new Bundle();
                        extra.putSerializable("objects", (Serializable) gameDtoList);
                        Intent broadcast = new Intent();
                        broadcast.setAction(GameListFragment.BROADCAST_ACTION);
                        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
                        broadcast.putExtra("extra", result);
                        sendBroadcast(broadcast);
                    }
                });
                handler.postDelayed(runnable, 10000);
            }
        };

        handler.postDelayed(runnable, 15000);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onStart(Intent intent, int startid) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}

