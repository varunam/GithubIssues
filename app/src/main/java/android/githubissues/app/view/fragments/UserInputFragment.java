package android.githubissues.app.view.fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.githubissues.app.R;
import android.githubissues.app.view.apicalls.closedissues.ClosedIssuesFetchedCallbacks;
import android.githubissues.app.view.apicalls.closedissues.FetchClosedIssuesApi;
import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.apicalls.openissues.FetchOpenIssuesApi;
import android.githubissues.app.view.apicalls.openissues.OpenIssuesFetchedCallbacks;
import android.githubissues.app.view.viewmodel.MainViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class UserInputFragment extends Fragment implements View.OnClickListener, OpenIssuesFetchedCallbacks, ClosedIssuesFetchedCallbacks {
    
    private static final String TAG = UserInputFragment.class.getSimpleName();
    private TextInputEditText organisationNameEditText, repositoryNameEditText;
    private Button fetchIssuesButton;
    private MainViewModel mainViewModel;
    private ProgressDialog progressDialog;
    
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
        progressDialog = new ProgressDialog(getContext());
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
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
                Log.d(TAG, "organisationName: " + organisationName + "\n" +
                        "repositoryName: " + repositoryName);
                if (TextUtils.isEmpty(organisationName)) {
                    organisationNameEditText.setError("Required");
                    organisationNameEditText.requestFocus();
                } else if (TextUtils.isEmpty(repositoryName)) {
                    repositoryNameEditText.setError("Required");
                    repositoryNameEditText.requestFocus();
                } else {
                    showProgressDialog();
                    FetchOpenIssuesApi.getInstance().fetchOpenIssues(organisationName,
                            repositoryName, this);
                    FetchClosedIssuesApi.getInstance().fetchOpenIssues(organisationName,
                            repositoryName, this);
                }
                break;
            default:
                break;
        }
    }
    
    private void showProgressDialog() {
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    
    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
    
    private void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void onOpenIssuesFetchSuccessful(ArrayList<Issue> openIssues) {
        mainViewModel.setOpenIssues(openIssues);
    }
    
    @Override
    public void onOpenIssuesFetchFailure(String failureReason) {
        Log.e(TAG, "Failed to fetchOpenIssues: " + failureReason);
        hideProgressDialog();
        toast("Something went wrong\nPlease try again");
    }
    
    @Override
    public void onClosedIssuesFetchSuccessful(ArrayList<Issue> closedIssues) {
        mainViewModel.setClosedIssues(closedIssues);
    }
    
    @Override
    public void onClosedIssuesFetchFailure(String failureReason) {
        Log.e(TAG, "Failed to fetchClosedIssues: " + failureReason);
        hideProgressDialog();
        toast("Something went wrong\nPlease try again");
    }
}
