package android.githubissues.app.view.apicalls.openissues;

import android.githubissues.app.view.apicalls.VolleyRequestQueue;
import android.githubissues.app.view.utils.Constants;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by varun.am on 01/03/19
 */
public class FetchOpenIssuesApi {
    
    private static final String TAG = FetchOpenIssuesApi.class.getSimpleName();
    private static FetchOpenIssuesApi fetchOpenIssuesApi;
    private String URL;
    private StringRequest fetchOpenIssuesRequest;
    private OpenIssuesFetchedCallbacks openIssuesFetchedCallbacks;
    
    private FetchOpenIssuesApi(@NonNull String organisationName, @NonNull String repositoryName) {
        URL = Constants.ApiConstants.BASE_URL + organisationName + "/" + repositoryName +
                Constants.ApiConstants.ISSUE_STATE_OPEN;
        fetchOpenIssuesRequest = new StringRequest(
                Request.Method.GET,
                URL,
                fetchOpenIssuesSuccessListener,
                fetchOpenIssuesFailureListener
        );
    }
    
    public static FetchOpenIssuesApi getInstance(@NonNull String organisationName, @NonNull String repositoryName) {
        if (fetchOpenIssuesApi == null)
            fetchOpenIssuesApi = new FetchOpenIssuesApi(organisationName, repositoryName);
        return fetchOpenIssuesApi;
    }
    
    public void fetchOpenIssues(OpenIssuesFetchedCallbacks openIssuesFetchedCallbacks) {
        Log.d(TAG, URL);
        this.openIssuesFetchedCallbacks = openIssuesFetchedCallbacks;
        VolleyRequestQueue.getInstance().addToRequestQueue(fetchOpenIssuesRequest);
    }
    
    private Response.Listener<String> fetchOpenIssuesSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "fetchOpenIssues success response received: " + response);
            openIssuesFetchedCallbacks.onOpenIssuesFetchSuccessful();
        }
    };
    
    private Response.ErrorListener fetchOpenIssuesFailureListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "fetchOpenIssues failure response received: " + error.getMessage());
            error.printStackTrace();
            openIssuesFetchedCallbacks.onOpenIssuesFetchFailure(error.getMessage());
        }
    };
}
