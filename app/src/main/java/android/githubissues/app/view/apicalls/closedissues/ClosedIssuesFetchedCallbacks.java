package android.githubissues.app.view.apicalls.closedissues;

import android.githubissues.app.view.apicalls.model.Issue;

import com.android.volley.VolleyError;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public interface ClosedIssuesFetchedCallbacks {
    public void onClosedIssuesFetchSuccessful(ArrayList<Issue> closedIssues);
    public void onClosedIssuesFetchFailure(VolleyError volleyError);
}
