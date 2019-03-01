package android.githubissues.app.view.apicalls.closedissues;

/**
 * Created by varun.am on 01/03/19
 */
public interface ClosedIssuesFetchedCallbacks {
    public void onClosedIssuesFetchSuccessful();
    public void onClosedIssuesFetchFailure(String failureReason);
}
