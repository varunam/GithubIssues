package android.githubissues.app.view.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.githubissues.app.R;
import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.fragments.IssuesListFragment;
import android.githubissues.app.view.fragments.UserInputFragment;
import android.githubissues.app.view.viewmodel.MainViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = MainActivity.class.getSimpleName();
    private UserInputFragment userInputFragment;
    private IssuesListFragment issuesListFragment;
    private MainViewModel mainViewModel;
    private ArrayList<Issue> openIssues, closedIssues, allIssues;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
        launchUserInputFragment();
    }
    
    private void init() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getOpenIssues().observe(this, openIssuesObserver);
        mainViewModel.getClosedIssues().observe(this, closedIssuesObserver);
        //mainViewModel.getUserInputReceived().observe(this, userInputReceivedObserver);
    }
    
    private void launchUserInputFragment() {
        if (userInputFragment == null)
            userInputFragment = UserInputFragment.newInstance();
        
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container_id, userInputFragment)
                .commitAllowingStateLoss();
    }
    
    private Observer<ArrayList<Issue>> openIssuesObserver = new Observer<ArrayList<Issue>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Issue> issues) {
            if (issues != null) {
                Log.d(TAG, "received openIssues: " + issues.size());
                openIssues = issues;
                if (allIssues == null) {
                    allIssues = openIssues;
                    Log.d(TAG, "assigning openIssues to allIssues");
                } else {
                    allIssues.addAll(openIssues);
                    Log.d(TAG, "appending openIssues to allIssues");
                    launchIssuesListFragment(allIssues);
                }
            }
        }
    };
    
    private Observer<ArrayList<Issue>> closedIssuesObserver = new Observer<ArrayList<Issue>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Issue> issues) {
            if (issues != null) {
                Log.d(TAG, "received closedIssues: " + issues.size());
                closedIssues = issues;
                if (allIssues == null) {
                    allIssues = closedIssues;
                    Log.d(TAG, "assigning closedIssues to allIssues");
                } else {
                    allIssues.addAll(closedIssues);
                    Log.d(TAG, "appending closed Issues to allIssues");
                    launchIssuesListFragment(allIssues);
                }
            }
        }
    };
    
    /*private Observer<Boolean> userInputReceivedObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean aBoolean) {
            launchIssuesListFragment();
        }
    };*/
    
    private void launchIssuesListFragment(ArrayList<Issue> allIssues) {
        Log.d(TAG, "launching fragment with " + allIssues.size() + " issues");
        issuesListFragment = IssuesListFragment.newInstance(allIssues);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container_id, issuesListFragment)
                .commitAllowingStateLoss();
    }
}
