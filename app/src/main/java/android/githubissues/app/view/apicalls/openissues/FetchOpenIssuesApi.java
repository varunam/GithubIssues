package android.githubissues.app.view.apicalls.openissues;

/**
 * Created by varun.am on 01/03/19
 */
public class FetchOpenIssuesApi {
    
    private static FetchOpenIssuesApi fetchOpenIssuesApi;
    private String organisationName, repositoryName;
    
    public static FetchOpenIssuesApi getInstance() {
        if (fetchOpenIssuesApi == null)
            fetchOpenIssuesApi = new FetchOpenIssuesApi();
        return fetchOpenIssuesApi;
    }
    
    public void fetchOpenIssues(String organisationName, String repositoryName) {
        this.repositoryName = repositoryName;
        this.organisationName = organisationName;
    }
    
   /* private StringRequest fetchOpenIssuesRequest = new StringRequest(
            Request.Method.GET,
            
            )*/
}
