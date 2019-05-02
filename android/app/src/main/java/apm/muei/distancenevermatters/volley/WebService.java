package apm.muei.distancenevermatters.volley;

import android.content.Context;
import android.util.Log;

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

public class WebService {

    private static String URL = "https://distance-never-matters-backend.appspot.com/";

    public static void getMaps(Context context, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "maps", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error fetch maps",  error.toString());

            }
        });
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void getModelsPreview(Context context, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "models", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error fetch models",  error.toString());

            }
        });
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void getMarkers(Context context, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, URL + "markers", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error fetch markers",  error.toString());

            }
        });
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

    public static void createGame(Context context, final CreateGameDto createGameDto, final VolleyCallback callback){
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

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("token", new GlobalVars().getInstance().getmAuth().getCurrentUser().getEmail());
                return headers;
            }
        };
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }

}
