package browserstack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkUtils {

    public String baseUrl="https://api.browserstack.com/automate/builds/<buildid>/sessions/<sessionid>/networklogs";

    public void getNetworkLogs(String buildId,String sessionId) throws
            Exception {

        String url=this.baseUrl.replaceAll("<sessionid>",sessionId);
        url=url.replaceAll("<buildid>",buildId);

        String userName = System.getenv("BROWSERSTACK_USERNAME");
        String passWord = System.getenv("BROWSERSTACK_ACCESS_KEY");

        HttpResponse<JsonNode> jsonResponse= null;
        Future<HttpResponse<JsonNode>> futureData = Unirest.get(url).basicAuth(userName,passWord)
                .asJsonAsync();

        try
        {

                jsonResponse = futureData.get(10000, TimeUnit.MILLISECONDS);


            if(jsonResponse.getStatus() == 200)
            {
                File file = new File("output/report/networklogs-"+ sessionId+".json");
                LocalDateTime localTime = LocalDateTime.now(ZoneId.of("GMT+05:30"));
                File dir=new File("output/report/"+buildId+"-"+localTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                dir.mkdir();
                FileWriter writer=new FileWriter(new File(dir.getAbsolutePath()+File.separator+
                        "networklogs-"+ sessionId+".json"));
                writer.write(jsonResponse.getBody().toString());
                writer.close();

            }else
            {
                throw new Exception("Not able to extract network logs");
            }
        }catch(TimeoutException e)
        {
            //e.printStackTrace();

            futureData.cancel(true);
            System.out.println("isCancelled: "+futureData.isCancelled());
            throw new Exception("Time out while downloading network logs");

        }

    }

    public static boolean isDataReady() throws UnirestException {
        NetworkLogTracker networkData= NetworkLogTracker.getInstance();
        boolean isDataReady=false;
        Iterator itr= networkData.getSessionDetails().entrySet().iterator();
        while(itr.hasNext())
        {

            Map.Entry<String,String> map=(Map.Entry<String,String>)itr.next();
            String baseUrl="https://api.browserstack.com/automate/builds/"+map.getValue()+
                    "/sessions/"+map.getKey()+"/networklogs";
            int retry=0;
            while(true) {

                retry++;
                try {

                    String userName = System.getenv("BROWSERSTACK_USERNAME");
                    String passWord = System.getenv("BROWSERSTACK_ACCESS_KEY");
                    HttpResponse<JsonNode> jsonResponse = Unirest.get(baseUrl).basicAuth(userName, passWord)
                            .asJson();
                    if (jsonResponse.getStatus() == 200) {
                        if (jsonResponse.getBody() != null) {
                            isDataReady = true;
                            break;
                        }
                    }
                } catch (UnirestException e) {
                    System.out.println("Retry "+retry+" for session =>"+ map.getKey());
                    if(retry==3) {
                        System.out.println("Max Retry "+retry+" for session =>"+ map.getKey());
                        e.printStackTrace();
                        throw e;
                    }
                }
            }

        }
        return isDataReady;
    }
}
