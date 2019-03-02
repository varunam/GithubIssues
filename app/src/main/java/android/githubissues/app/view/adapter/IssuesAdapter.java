package android.githubissues.app.view.adapter;

import android.githubissues.app.R;
import android.githubissues.app.view.apicalls.model.Issue;
import android.githubissues.app.view.apicalls.model.IssueStatus;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
    private PatchUrlClickedCallback patchUrlClickedCallback;
    private int oldPosition = 0, lastPosition = 0;
    
    public IssuesAdapter(@NonNull PatchUrlClickedCallback patchUrlClickedCallback) {
        this.patchUrlClickedCallback = patchUrlClickedCallback;
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Issue issue = issues.get(position);
        
        viewHolder.issue_status.setText(issue.getIssue_status().name());
        viewHolder.pull_request_number.setText(issue.getPull_request_number());
        viewHolder.patch_url.setText(issue.getPatch_url());
        viewHolder.user.setText(issue.getUser());
        viewHolder.title.setText(issue.getTitle());
        
        if (issue.getIssue_status() == IssueStatus.OPEN) {
            viewHolder.issue_status.setTextColor(viewHolder.divider.getContext()
                    .getResources()
                    .getColor(R.color.Color_DarkRed));
        } else {
            viewHolder.issue_status.setTextColor(viewHolder.divider.getContext()
                    .getResources()
                    .getColor(R.color.Color_DarkGreen));
        }
        
        viewHolder.patch_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patchUrlClickedCallback.onPatchUrlClicked(issue.getPatch_url());
            }
        });
        
        viewHolder.issueCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPosition = lastPosition;
                lastPosition = position;
                notifyItemChanged(oldPosition);
                notifyItemChanged(lastPosition);
            }
        });
        
        if (position == lastPosition) {
            showFullCard(viewHolder);
        } else {
            hideFullCard(viewHolder);
        }
        
    }
    
    private void hideFullCard(ViewHolder viewHolder) {
        viewHolder.divider.setVisibility(View.GONE);
        viewHolder.user.setVisibility(View.GONE);
        viewHolder.userLabel.setVisibility(View.GONE);
        viewHolder.pathUrlLabel.setVisibility(View.GONE);
        viewHolder.patch_url.setVisibility(View.GONE);
        viewHolder.prLabel.setVisibility(View.GONE);
        viewHolder.pull_request_number.setVisibility(View.GONE);
    }
    
    private void showFullCard(ViewHolder viewHolder) {
        viewHolder.divider.setVisibility(View.VISIBLE);
        viewHolder.user.setVisibility(View.VISIBLE);
        viewHolder.userLabel.setVisibility(View.VISIBLE);
        viewHolder.pathUrlLabel.setVisibility(View.VISIBLE);
        viewHolder.patch_url.setVisibility(View.VISIBLE);
        viewHolder.prLabel.setVisibility(View.VISIBLE);
        viewHolder.pull_request_number.setVisibility(View.VISIBLE);
    }
    
    @Override
    public int getItemCount() {
        if (issues != null && issues.size() > 0)
            return issues.size();
        else
            return 0;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        private TextView title, user, patch_url, pull_request_number, issue_status;
        private TextView userLabel, pathUrlLabel, prLabel;
        private View divider;
        private CardView issueCard;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            title = itemView.findViewById(R.id.item_title_id);
            user = itemView.findViewById(R.id.item_user_id);
            patch_url = itemView.findViewById(R.id.item_patch_url_id);
            pull_request_number = itemView.findViewById(R.id.item_pull_request_id);
            issue_status = itemView.findViewById(R.id.item_status_id);
            
            divider = itemView.findViewById(R.id.divider_id);
            userLabel = itemView.findViewById(R.id.item_user_label_id);
            pathUrlLabel = itemView.findViewById(R.id.item_patch_url_label_id);
            prLabel = itemView.findViewById(R.id.item_pr_number_label_id);
            issueCard = itemView.findViewById(R.id.issue_card_id);
        }
    }
}
