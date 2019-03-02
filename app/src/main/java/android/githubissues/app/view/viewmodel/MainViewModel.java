package android.githubissues.app.view.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.githubissues.app.view.apicalls.model.Issue;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class MainViewModel extends AndroidViewModel {
    
    private MutableLiveData<ArrayList<Issue>> closedIssues = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Issue>> openIssues = new MutableLiveData<>();
    private MutableLiveData<String> orgName = new MutableLiveData<>();
    private MutableLiveData<String> repoName = new MutableLiveData<>();
    
    
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    
    public void setClosedIssues(ArrayList<Issue> closedIssues) {
        this.closedIssues.postValue(closedIssues);
    }
    
    public void setOpenIssues(ArrayList<Issue> openIssues) {
        this.openIssues.postValue(openIssues);
    }
    
    public void setOrgName(String orgName){
        this.orgName.postValue(orgName);
    }
    
    public MutableLiveData<String> getOrgName(){
        return orgName;
    }
    
    public void setRepoName(String repoName){
        this.repoName.postValue(repoName);
    }
    
    public MutableLiveData<String> getRepoName(){
        return repoName;
    }
    
    public MutableLiveData<ArrayList<Issue>> getClosedIssues() {
        return closedIssues;
    }
    
    public MutableLiveData<ArrayList<Issue>> getOpenIssues() {
        return openIssues;
    }
}
