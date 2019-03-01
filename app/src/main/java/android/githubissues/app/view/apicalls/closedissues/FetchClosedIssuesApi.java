package android.githubissues.app.view.apicalls.closedissues;

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
public class FetchClosedIssuesApi {
    
    private static final String TAG = FetchClosedIssuesApi.class.getSimpleName();
    private static FetchClosedIssuesApi fetchClosedIssuesApi;
    private String URL;
    private StringRequest fetchClosedIssuesRequest;
    private ClosedIssuesFetchedCallbacks closedIssuesFetchedCallbacks;
    
    private FetchClosedIssuesApi(@NonNull String organisationName, @NonNull String repositoryName) {
        URL = Constants.ApiConstants.BASE_URL + organisationName + "/" + repositoryName +
                Constants.ApiConstants.ISSUE_STATE_CLOSED;
        fetchClosedIssuesRequest = new StringRequest(
                Request.Method.GET,
                URL,
                fetchOpenIssuesSuccessListener,
                fetchOpenIssuesFailureListener
        );
    }
    
    public static FetchClosedIssuesApi getInstance(@NonNull String organisationName, @NonNull String repositoryName) {
        if (fetchClosedIssuesApi == null)
            fetchClosedIssuesApi = new FetchClosedIssuesApi(organisationName, repositoryName);
        return fetchClosedIssuesApi;
    }
    
    public void fetchOpenIssues(ClosedIssuesFetchedCallbacks openIssuesFetchedCallbacks) {
        Log.d(TAG, URL);
        this.closedIssuesFetchedCallbacks = openIssuesFetchedCallbacks;
        VolleyRequestQueue.getInstance().addToRequestQueue(fetchClosedIssuesRequest);
    }
    
    private Response.Listener<String> fetchOpenIssuesSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "fetchClosedIssues success response received: " + response);
            closedIssuesFetchedCallbacks.onClosedIssuesFetchSuccessful();
        }
    };
    
    private Response.ErrorListener fetchOpenIssuesFailureListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "fetchClosedIssues failure response received: " + error.getMessage());
            error.printStackTrace();
            closedIssuesFetchedCallbacks.onClosedIssuesFetchFailure(error.getMessage());
        }
    };
}
