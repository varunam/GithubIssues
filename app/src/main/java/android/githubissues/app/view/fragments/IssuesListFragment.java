package android.githubissues.app.view.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.githubissues.app.R;
import android.githubissues.app.view.adapter.IssuesAdapter;
import android.githubissues.app.view.adapter.PatchUrlClickedCallback;
import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.viewmodel.MainViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class IssuesListFragment extends Fragment implements PatchUrlClickedCallback {
    
    public static final String ISSUES_LIST_KEY = "issues_list_key";
    private static final String TAG = IssuesListFragment.class.getSimpleName();
    private MainViewModel mainViewModel;
    private IssuesAdapter issuesAdapter;
    private ArrayList<Issue> allIssuesList;
    private TextView orgNameTextView, repoNameTextView;
    
    public static IssuesListFragment newInstance(ArrayList<Issue> issuesList) {
        
        Bundle args = new Bundle();
        args.putParcelableArrayList(ISSUES_LIST_KEY, issuesList);
        IssuesListFragment fragment = new IssuesListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_issues_list, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        init(view);
        if (getArguments() != null && getArguments().containsKey(ISSUES_LIST_KEY)) {
            allIssuesList = getArguments().getParcelableArrayList(ISSUES_LIST_KEY);
            issuesAdapter.setIssues(allIssuesList);
            Log.d(TAG, "all issues set to adapter " + allIssuesList.size());
        }
    }
    
    private void init(View view) {
        allIssuesList = new ArrayList<>();
        issuesAdapter = new IssuesAdapter(this);
        orgNameTextView = view.findViewById(R.id.issues_list_org_name_id);
        repoNameTextView = view.findViewById(R.id.issues_list_repo_name_id);
        RecyclerView issuesRecyclerView = view.findViewById(R.id.issues_list_recyclerview_id);
        issuesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        issuesRecyclerView.setAdapter(issuesAdapter);
        
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mainViewModel.getOrgName().observe(this, orgNameObserver);
        mainViewModel.getRepoName().observe(this, repoNameObserver);
    }
    
    private Observer<String> orgNameObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            if (s != null)
                orgNameTextView.setText(s.toUpperCase());
        }
    };
    
    private Observer<String> repoNameObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            if (s != null)
                repoNameTextView.setText(s.toUpperCase());
        }
    };
    
    @Override
    public void onPatchUrlClicked(String patch_url) {
        if (URLUtil.isValidUrl(patch_url))
            new FinestWebView.Builder(getActivity()).show(patch_url);
        else
            Log.e(TAG, "Ignoring invalid url: " + patch_url);
    }
}
