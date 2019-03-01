package android.githubissues.app.view.fragments;

import android.githubissues.app.R;
import android.githubissues.app.view.apicalls.closedissues.ClosedIssuesFetchedCallbacks;
import android.githubissues.app.view.apicalls.closedissues.FetchClosedIssuesApi;
import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.apicalls.openissues.FetchOpenIssuesApi;
import android.githubissues.app.view.apicalls.openissues.OpenIssuesFetchedCallbacks;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class UserInputFragment extends Fragment implements View.OnClickListener, OpenIssuesFetchedCallbacks, ClosedIssuesFetchedCallbacks {
    
    private EditText organisationNameEditText, repositoryNameEditText;
    private Button fetchIssuesButton;
    
    public static UserInputFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserInputFragment fragment = new UserInputFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_input, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
    }
    
    private void initViews(View view) {
        organisationNameEditText = view.findViewById(R.id.ui_organisation_name_et_id);
        repositoryNameEditText = view.findViewById(R.id.ui_repository_name_et_id);
        fetchIssuesButton = view.findViewById(R.id.ui_fetch_issues_button_id);
        fetchIssuesButton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ui_fetch_issues_button_id:
                String organisationName = organisationNameEditText.getText().toString().trim();
                String repositoryName = repositoryNameEditText.getText().toString().trim();
                FetchOpenIssuesApi.getInstance("prestodb",
                        "presto").fetchOpenIssues(this);
                FetchClosedIssuesApi.getInstance("prestodb",
                        "presto").fetchOpenIssues(this);
                break;
            default:
                break;
        }
    }
    
    private void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onOpenIssuesFetchSuccessful(ArrayList<Issue> openIssues) {
    
    }
    
    @Override
    public void onOpenIssuesFetchFailure(String failureReason) {
    
    }
    
    @Override
    public void onClosedIssuesFetchSuccessful() {
    
    }
    
    @Override
    public void onClosedIssuesFetchFailure(String failureReason) {
    
    }
}
