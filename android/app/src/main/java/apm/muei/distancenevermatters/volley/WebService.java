package apm.muei.distancenevermatters.volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;
import apm.muei.distancenevermatters.entities.dto.CreateGameDto;
import apm.muei.distancenevermatters.entities.dto.GameDetailsDto;
import apm.muei.distancenevermatters.entities.dto.JoinGameDto;
import apm.muei.distancenevermatters.entities.dto.UpdateStateDto;


public class WebService {

    private static String URL = "https://distance-never-matters-backend.appspot.com/";

    private static Response.Listener<String> getOnSuccessCallback(final VolleyCallback callback) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        };
    }

    private static Response.ErrorListener getOnErrorCallback() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error calling API",  error.toString());
            }
        };
    }

    public static void getMaps(Context context, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URL.concat("maps"), getOnSuccessCallback(callback), getOnErrorCallback());
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void getGames(Context context, final VolleyCallback callback) {
        final GlobalVars gVars = GlobalVars.getInstance();
        StringRequest request = new StringRequest(Request.Method.GET, URL.concat("games"), getOnSuccessCallback(callback), getOnErrorCallback())
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", gVars.getmAuth().getCurrentUser().getEmail());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void getModelsPreview(Context context, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "models", getOnSuccessCallback(callback), getOnErrorCallback());
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void getMarkers(Context context, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "markers", getOnSuccessCallback(callback), getOnErrorCallback());
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void createGame(Context context, final CreateGameDto createGameDto, final VolleyCallback callback){

        final GlobalVars gVars = GlobalVars.getInstance();
        StringRequest request = new StringRequest(Request.Method.POST, URL + "games", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error post Game",  error.toString());

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new GsonBuilder().create();
                return gson.toJson(createGameDto).getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("token", gVars.getmAuth().getCurrentUser().getEmail());
                return headers;
            }
        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }


    public static void joinGame(Context context, final JoinGameDto joinGameDto, final VolleyCallback callback){
        final String email = GlobalVars.getInstance().getmAuth().getCurrentUser().getEmail();
        StringRequest request = new StringRequest(Request.Method.POST, URL + "game/join", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error join Game",  error.toString());

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new GsonBuilder().create();
                return gson.toJson(joinGameDto).getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("token", email);
                return headers;
            }
        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void findGameByCode (Context context, String code, final VolleyCallback callback) {

        StringRequest request;
        request = new StringRequest(Request.Method.GET, URL.concat("/game/").concat(code), getOnSuccessCallback(callback), getOnErrorCode(context)) {

        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void deleteGame(Context context, String code, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.DELETE, URL.concat("/game/").concat(code), getOnSuccessCallback(callback), getOnErrorCallback());
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }


    private static Response.ErrorListener getOnErrorCode(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        "Partida no encontrada", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public static void changeGameState(Context context, final UpdateStateDto stateDto, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.POST, URL + "game/state", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error change game state",  error.toString());

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new GsonBuilder().create();
                return gson.toJson(stateDto).getBytes();
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void updateGame(Context context, final String gameDetailsDto, final VolleyCallback callback){

        final GlobalVars gVars = GlobalVars.getInstance();
        StringRequest request = new StringRequest(Request.Method.POST, URL + "game", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error post Game",  error.toString());

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                //Gson gson = new GsonBuilder().create();
                return gameDetailsDto.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }



}
