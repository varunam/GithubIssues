package android.githubissues.app.view.fragments;

import android.githubissues.app.R;
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

/**
 * Created by varun.am on 01/03/19
 */
public class UserInputFragment extends Fragment implements View.OnClickListener{
    
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
                toast("clicked");
                break;
            default:
                break;
        }
    }
    
    private void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
