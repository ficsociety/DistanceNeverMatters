package apm.muei.distancenevermatters.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
                Log.i("Error fetch maps",  error.toString());

            }
        });
        VolleySingleton.getInstance(context).addRequestQueue(request);
    }


}
