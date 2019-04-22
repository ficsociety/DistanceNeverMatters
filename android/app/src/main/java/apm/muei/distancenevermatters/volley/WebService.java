package apm.muei.distancenevermatters.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


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

        StringRequest request;
        // TODO Cambiar path
        request = new StringRequest(Request.Method.GET, URL.concat("maps"), getOnSuccessCallback(callback), getOnErrorCallback());
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }
}
