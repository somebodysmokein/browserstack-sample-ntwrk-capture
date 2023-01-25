package browserstack;

import java.util.HashMap;

public class NetworkLogTracker {
    private static HashMap<String,String> sessions=new HashMap<>();
    private static NetworkLogTracker _instance=null;
    private NetworkLogTracker()
    {
    }

    public static NetworkLogTracker getInstance()
    {
        if(_instance==null)
        {
            return new NetworkLogTracker();
        }
        return _instance;
    }

    public void setSession(String buildId,String sessionId)
    {
        sessions.put(sessionId,buildId);
    }

    public HashMap<String,String> getSessionDetails()
    {
        return sessions;
    }
}