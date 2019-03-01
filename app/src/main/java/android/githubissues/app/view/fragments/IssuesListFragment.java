package android.githubissues.app.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by varun.am on 01/03/19
 */
public class IssuesListFragment extends Fragment {
    
    public static IssuesListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        IssuesListFragment fragment = new IssuesListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
