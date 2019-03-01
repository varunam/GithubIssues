package android.githubissues.app.view.adapter;

import android.githubissues.app.view.apicalls.model.Issue;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by varun.am on 01/03/19
 */
public class IssuesListAdapter extends RecyclerView.Adapter<IssuesListAdapter.ViewHolder> {
    
    private ArrayList<Issue> issues;
    
    public IssuesListAdapter(){
        issues = new ArrayList<>();
    }
    
    public void setIssues(ArrayList<Issue> issues){
        this.issues = issues;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    
    }
    
    @Override
    public int getItemCount() {
        return 0;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
    
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
