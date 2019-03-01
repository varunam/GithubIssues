package android.githubissues.app.view.apicalls.openissues;

/**
 * Created by varun.am on 01/03/19
 */
public interface OpenIssuesFetchedCallbacks {
    public void onOpenIssuesFetchSuccessful();
    public void onOpenIssuesFetchFailure(String failureReason);
}
