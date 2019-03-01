package android.githubissues.app.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by varun.am on 01/03/19
 */
public class UserInputFragment extends Fragment {
    
    public static UserInputFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UserInputFragment fragment = new UserInputFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
