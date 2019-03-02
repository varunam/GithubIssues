package android.githubissues.app.view.volley;

import android.githubissues.app.view.application.ThisApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by varun.am on 25/02/19
 */
public class VolleyRequestQueue {
    
    private RequestQueue requestQueue;
    private static VolleyRequestQueue volleyRequestQueue;
    
    public VolleyRequestQueue() {
        requestQueue = Volley.newRequestQueue(ThisApplication.getContext());
    }
    
    public static VolleyRequestQueue getInstance() {
        if (volleyRequestQueue == null)
            volleyRequestQueue = new VolleyRequestQueue();
        return volleyRequestQueue;
    }
    
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    
    public <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
