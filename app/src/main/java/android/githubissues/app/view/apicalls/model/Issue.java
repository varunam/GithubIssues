package android.githubissues.app.view.apicalls.model;

import android.githubissues.app.view.utils.Constants;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by varun.am on 01/03/19
 */
public class Issue implements Parcelable {
    
    private IssueStatus issue_status;
    private String user;
    private String pull_request_number;
    private String title;
    private String patch_url;
    
    public Issue(IssueStatus issue_status) {
        this.issue_status = issue_status;
        if (issue_status == IssueStatus.OPEN) {
            this.patch_url = Constants.NOT_AVAILABLE;
            this.pull_request_number = Constants.NOT_AVAILABLE;
        }
    }
    
    protected Issue(Parcel in) {
        user = in.readString();
        pull_request_number = in.readString();
        title = in.readString();
        patch_url = in.readString();
    }
    
    public static final Creator<Issue> CREATOR = new Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel in) {
            return new Issue(in);
        }
        
        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
    
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
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        
        parcel.writeString(user);
        parcel.writeString(pull_request_number);
        parcel.writeString(title);
        parcel.writeString(patch_url);
    }
}
