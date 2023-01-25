package browserstack;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;

public class AccessNetworkLogs {

    public String baseUrl="https://api-cloud.browserstack.com/automate/builds/<buildid>/sessions/<sessionid>/networklogs";
    public WebDriver driver;
    public String buildId=null;
    public AccessNetworkLogs(WebDriver driver) throws Exception {
        this.driver=driver;
        this.buildId=getBuildId();
    }




     public String getBuildId() throws Exception {
        String buildId=null;
         String url="https://api.browserstack.com/automate/builds.json?limit=5&status=running";
         HashMap<String,String> map=getCredentials();
         HttpResponse<JsonNode> jsonResponse= Unirest.get(url).basicAuth(map.get("userName"),map.get("passWord"))
                 .asJson();
         if(jsonResponse.getStatus()==200)
         {
             ObjectMapper mapper=new ObjectMapper();
             List<Build> buildItems=mapper.readValue(jsonResponse.getBody().toString(),  new TypeReference<List<Build>>(){});
             buildId = buildItems.get(0).getAutomationBuild().getHashedId();
         }else
         {
             throw new Exception("Not able to fetch build id");
         }
        return buildId;
     }

    private HashMap<String,String> getCredentials()
    {
        HashMap<String,String> cred=new HashMap<>();
        String userName = System.getenv("BROWSERSTACK_USERNAME");
        String passWord = System.getenv("BROWSERSTACK_ACCESS_KEY");
        cred.put("userName",userName);
        cred.put("passWord",passWord);
        return cred;
    }
}
