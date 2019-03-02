package android.githubissues.app.view.apicalls.openissues;

import android.githubissues.app.view.apicalls.VolleyRequestQueue;
import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.apicalls.model.IssueStatus;
import android.githubissues.app.view.utils.Constants;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
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
    
    private FetchOpenIssuesApi(@NonNull String organisationName, @NonNull String repositoryName) {
        URL = Constants.ApiConstants.BASE_URL + organisationName + "/" + repositoryName +
                Constants.ApiConstants.ISSUE_STATE_OPEN;
        fetchOpenIssuesRequest = new StringRequest(
                Request.Method.GET,
                URL,
                fetchOpenIssuesSuccessListener,
                fetchOpenIssuesFailureListener
        ) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(jsonString, cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
        };
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
