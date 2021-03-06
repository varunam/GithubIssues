package android.githubissues.app.view.apicalls.closedissues;

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

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class FetchClosedIssuesApi {
    
    private static final String TAG = FetchClosedIssuesApi.class.getSimpleName();
    private static FetchClosedIssuesApi fetchClosedIssuesApi;
    private ClosedIssuesFetchedCallbacks closedIssuesFetchedCallbacks;
    private String organisationName, repositoryName;
    
    public static FetchClosedIssuesApi getInstance() {
        if (fetchClosedIssuesApi == null)
            fetchClosedIssuesApi = new FetchClosedIssuesApi();
        return fetchClosedIssuesApi;
    }
    
    public void fetchOpenIssues(@NonNull String organisationName, @NonNull String repositoryName, ClosedIssuesFetchedCallbacks openIssuesFetchedCallbacks) {
        this.closedIssuesFetchedCallbacks = openIssuesFetchedCallbacks;
        String URL = Constants.ApiConstants.BASE_URL + organisationName + "/" + repositoryName +
                Constants.ApiConstants.ISSUE_STATE_CLOSED;
        Log.d(TAG, "Closed Issues URL: " + URL);
        this.organisationName = organisationName;
        this.repositoryName = repositoryName;
        StringRequest fetchClosedIssuesRequest = new VolleyStringRequest(
                Request.Method.GET,
                URL,
                fetchOpenIssuesSuccessListener,
                fetchOpenIssuesFailureListener
        );
        VolleyRequestQueue.getInstance().addToRequestQueue(fetchClosedIssuesRequest);
    }
    
    private ArrayList<Issue> parseResponse(String response) {
        JsonArray jsonArray = (JsonArray) new JsonParser().parse(response);
        ArrayList<Issue> closedIssues = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            Issue issue = new Issue(IssueStatus.CLOSED);
            
            //checking if it has title
            if (jsonObject.has(Constants.ApiResponseConstants.TITLE)) {
                issue.setTitle(jsonObject.get(Constants.ApiResponseConstants.TITLE).getAsString());
            } else {
                issue.setTitle(Constants.NOT_AVAILABLE);
            }
            
            if (jsonObject.has(Constants.ApiResponseConstants.USER)) {
                JsonObject userObject = (JsonObject) jsonObject.get(Constants.ApiResponseConstants.USER);
                if (userObject.has(Constants.ApiResponseConstants.LOGIN)) {
                    issue.setUser(userObject.get(Constants.ApiResponseConstants.LOGIN).getAsString());
                } else {
                    issue.setUser(Constants.NOT_AVAILABLE);
                }
            } else {
                issue.setUser(Constants.NOT_AVAILABLE);
            }
            
            if (jsonObject.has(Constants.ApiResponseConstants.PULL_REQUEST)) {
                JsonObject pullRequestObject = (JsonObject) jsonObject.get(Constants.ApiResponseConstants.PULL_REQUEST);
                if (pullRequestObject.has(Constants.ApiResponseConstants.PATCH_URL)) {
                    issue.setPatch_url(pullRequestObject.get(Constants.ApiResponseConstants.PATCH_URL).getAsString());
                } else {
                    issue.setPatch_url(Constants.NOT_AVAILABLE);
                }
                if (pullRequestObject.has(Constants.ApiResponseConstants.URL)) {
                    String pr_number = pullRequestObject.get(Constants.ApiResponseConstants.URL).getAsString()
                            .replaceAll(Constants.ApiConstants.BASE_URL, "")
                            .replaceAll(organisationName + File.separator + repositoryName + File.separator + "pulls/", "");
                    issue.setPull_request_number(pr_number);
                } else {
                    issue.setPull_request_number(Constants.NOT_AVAILABLE);
                }
            } else {
                issue.setPull_request_number(Constants.NOT_AVAILABLE);
                issue.setPatch_url(Constants.NOT_AVAILABLE);
            }
            
            closedIssues.add(issue);
        }
        Log.d(TAG, "returning " + closedIssues.size() + " closed issues");
        return closedIssues;
    }
    
    private Response.Listener<String> fetchOpenIssuesSuccessListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "fetchClosedIssues success response received: " + response);
            ArrayList<Issue> closedIssues = parseResponse(response);
            closedIssuesFetchedCallbacks.onClosedIssuesFetchSuccessful(closedIssues);
        }
    };
    
    private Response.ErrorListener fetchOpenIssuesFailureListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "fetchClosedIssues failure response received: " + error.getMessage());
            error.printStackTrace();
            closedIssuesFetchedCallbacks.onClosedIssuesFetchFailure(error);
        }
    };
}
