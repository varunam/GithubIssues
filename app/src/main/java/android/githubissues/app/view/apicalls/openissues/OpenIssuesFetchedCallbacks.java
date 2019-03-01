package android.githubissues.app.view.apicalls.openissues;

import android.githubissues.app.view.apicalls.model.Issue;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public interface OpenIssuesFetchedCallbacks {
    public void onOpenIssuesFetchSuccessful(ArrayList<Issue> openIssues);
    public void onOpenIssuesFetchFailure(String failureReason);
}
