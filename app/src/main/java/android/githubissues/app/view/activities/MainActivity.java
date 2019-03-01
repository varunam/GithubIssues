package android.githubissues.app.view.activities;

import android.githubissues.app.R;
import android.githubissues.app.view.fragments.IssuesListFragment;
import android.githubissues.app.view.fragments.UserInputFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private UserInputFragment userInputFragment;
    private IssuesListFragment issuesListFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        launchUserInputFragment();
    }
    
    private void launchUserInputFragment() {
        if (userInputFragment == null)
            userInputFragment = UserInputFragment.newInstance();
        
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container_id, userInputFragment)
                .commitAllowingStateLoss();
    }
}
