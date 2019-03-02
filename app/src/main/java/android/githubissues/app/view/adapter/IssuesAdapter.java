package android.githubissues.app.view.adapter;

import android.githubissues.app.R;
import android.githubissues.app.view.apicalls.model.Issue;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.ViewHolder> {
    
    private static final String TAG = IssuesAdapter.class.getSimpleName();
    private ArrayList<Issue> issues;
    
    public IssuesAdapter() {
        issues = new ArrayList<>();
    }
    
    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_issue_item, viewGroup, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Issue issue = issues.get(position);
        
        viewHolder.issue_status.setText(issue.getIssue_status().name());
        viewHolder.pull_request_number.setText(issue.getPull_request_number());
        viewHolder.patch_url.setText(issue.getPatch_url());
        viewHolder.user.setText(issue.getUser());
        viewHolder.title.setText(issue.getTitle());
        Log.d(TAG, "Issue status: " + issue.getIssue_status().name());
        
    }
    
    @Override
    public int getItemCount() {
        Log.d(TAG,"issues size: " + issues.size());
        if (issues != null && issues.size() > 0)
            return issues.size();
        else
            return 0;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        private TextView title, user, patch_url, pull_request_number, issue_status;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            title = itemView.findViewById(R.id.item_title_id);
            user = itemView.findViewById(R.id.item_user_id);
            patch_url = itemView.findViewById(R.id.item_patch_url_id);
            pull_request_number = itemView.findViewById(R.id.item_pull_request_id);
            issue_status = itemView.findViewById(R.id.item_status_id);
        }
    }
}
