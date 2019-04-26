package apm.muei.distancenevermatters.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import apm.muei.distancenevermatters.GlobalVars.GlobalVars;


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
        GlobalVars gVars = new GlobalVars().getInstance();
        System.out.println(gVars.getUser().getUid());
        StringRequest request;
        request = new StringRequest(Request.Method.GET, URL.concat("user/games"), getOnSuccessCallback(callback), getOnErrorCallback()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                // TODO Obtener user y meterlo en el header
                params.put("token", "roi");
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

}
