package android.githubissues.app.view.apicalls.model;

/**
 * Created by varun.am on 01/03/19
 */
public class Issue {
    
    private IssueStatus issue_status;
    private String user;
    private String pull_request_number;
    private String title;
    private String patch_url;
    
    public IssueStatus getIssue_status() {
        return issue_status;
    }
    
    public void setIssue_status(IssueStatus issue_status) {
        this.issue_status = issue_status;
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getPull_request_number() {
        return pull_request_number;
    }
    
    public void setPull_request_number(String pull_request_number) {
        this.pull_request_number = pull_request_number;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPatch_url() {
        return patch_url;
    }
    
    public void setPatch_url(String patch_url) {
        this.patch_url = patch_url;
    }
}
