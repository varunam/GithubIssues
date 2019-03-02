package android.githubissues.app.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.githubissues.app.R;
import android.githubissues.app.view.adapter.IssuesAdapter;
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

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class IssuesListFragment extends Fragment {
    
    public static final String ISSUES_LIST_KEY = "issues_list_key";
    private static final String TAG = IssuesListFragment.class.getSimpleName();
    private RecyclerView issuesRecyclerView;
    private MainViewModel mainViewModel;
    private IssuesAdapter issuesAdapter;
    private ArrayList<Issue> allIssuesList;
    
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
            Log.d(TAG, "all issues set to adapter: " + allIssuesList.size());
        }
    }
    
    private void init(View view) {
        allIssuesList = new ArrayList<>();
        issuesAdapter = new IssuesAdapter();
        issuesRecyclerView = view.findViewById(R.id.issues_list_recyclerview_id);
        issuesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        issuesRecyclerView.setAdapter(issuesAdapter);
        
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }
    
   /* private Observer<ArrayList<Issue>> issuesObserver = new Observer<ArrayList<Issue>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Issue> issues) {
            if (issues != null) {
                Log.d(TAG, "received issues in fragment: " + issues.size());
                allIssuesList.addAll(issues);
                issuesAdapter.setIssues(allIssuesList);
            }
        }
    };*/
}
