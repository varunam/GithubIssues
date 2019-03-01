package android.githubissues.app.view.utils;

/**
 * Created by varun.am on 01/03/19
 */
public class Constants {
    
    public class ApiConstants {
        public static final String BASE_URL = "https://api.github.com/repos/";
        public static final String ISSUE_STATE_OPEN = "/issues?state=open";
        public static final String ISSUE_STATE_CLOSED = "/issues?state=closed";
    }
    
    public class ApiResponseConstants{
        public static final String USER = "user";
        public static final String TITLE = "title";
        public static final String PULL_REQUEST = "pull_request";
        public static final String PATCH_URL = "patch_url";
        public static final String LOGIN = "login";
    }
    
    public static final String NOT_AVAILABLE = "not-available";
}
