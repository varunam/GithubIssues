package android.githubissues.app.view.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by varun.am on 01/03/19
 */
public class ThisApplication extends Application {
    
    private static Context context;
    
    public static Context getContext() {
        return context;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
