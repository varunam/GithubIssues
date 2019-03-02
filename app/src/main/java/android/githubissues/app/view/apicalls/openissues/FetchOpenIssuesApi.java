package android.githubissues.app.view.apicalls.openissues;

import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.apicalls.model.IssueStatus;
import android.githubissues.app.view.utils.Constants;
import android.githubissues.app.view.volley.VolleyRequestQueue;
import android.githubissues.app.view.volley.VolleyStringRequest;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class FetchOpenIssuesApi {
    
    private static final String TAG = FetchOpenIssuesApi.class.getSimpleName();
    private static FetchOpenIssuesApi fetchOpenIssuesApi;
    private String URL;
    private StringRequest fetchOpenIssuesRequest;
    private OpenIssuesFetchedCallbacks openIssuesFetchedCallbacks;
    
    public static FetchOpenIssuesApi getInstance() {
        if (fetchOpenIssuesApi == null)
            fetchOpenIssuesApi = new FetchOpenIssuesApi();
        return fetchOpenIssuesApi;
    }
    
    public void fetchOpenIssues(@NonNull String organisationName, @NonNull String repositoryName, OpenIssuesFetchedCallbacks openIssuesFetchedCallbacks) {
        URL = Constants.ApiConstants.BASE_URL + organisationName + "/" + repositoryName +
                Constants.ApiConstants.ISSUE_STATE_OPEN;
        Log.d(TAG, "Open Issues URL: " + URL);
        fetchOpenIssuesRequest = new VolleyStringRequest(
                Request.Method.GET,
                URL,
                fetchOpenIssuesSuccessListener,
                fetchOpenIssuesFailureListener
        );
        this.openIssuesFetchedCallbacks = openIssuesFetchedCallbacks;
        VolleyRequestQueue.getInstance().addToRequestQueue(fetchOpenIssuesRequest);
    }
    
    private Response.Listener<String> fetchOpenIssuesSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "fetchOpenIssues success response received: " + response);
            
            ArrayList<Issue> openIssues = parseResponse(response);
            openIssuesFetchedCallbacks.onOpenIssuesFetchSuccessful(openIssues);
        }
    };
    
    private ArrayList<Issue> parseResponse(String response) {
        JsonArray jsonArray = (JsonArray) new JsonParser().parse(response);
        ArrayList<Issue> openIssues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            Issue issue = new Issue(IssueStatus.OPEN);
            if (jsonObject.has(Constants.ApiResponseConstants.TITLE) && jsonObject.has(Constants.ApiResponseConstants.USER)) {
                issue.setTitle(jsonObject.get(Constants.ApiResponseConstants.TITLE).getAsString());
                JsonObject userObject = (JsonObject) jsonObject.get(Constants.ApiResponseConstants.USER);
                if (userObject.has(Constants.ApiResponseConstants.LOGIN)) {
                    issue.setUser(userObject.get(Constants.ApiResponseConstants.LOGIN).getAsString());
                }
                openIssues.add(issue);
            } else {
                Log.e(TAG, "Unable to parse openIssues from response: " + response);
            }
        }
        Log.d(TAG, "returning " + openIssues.size() + " open issues");
        return openIssues;
    }
    
    private Response.ErrorListener fetchOpenIssuesFailureListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "fetchOpenIssues failure response received: " + error.getMessage());
            error.printStackTrace();
            openIssuesFetchedCallbacks.onOpenIssuesFetchFailure(error.getMessage());
        }
    };
}
