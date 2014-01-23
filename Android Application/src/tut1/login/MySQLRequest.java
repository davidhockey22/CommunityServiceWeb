package tut1.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
 
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
public class MySQLRequest extends IntentService{
	
    public static final int kindVolQuery = 1, //id = name
    		kindEventVolQuery = 2, //id = volunteer id
    		kindEventQuery = 3, //id = event id
    		
    		kindFindQuery = 4, //id = null (returns all events)
    		kindInterestQuery = 5, //id = null (returns all interests)
    		kindEventInterestQuery = 6; //id = event id
 
    private String inMessage;
    
    public static final String IN_MSG = "requestType";
    public static final String OUT_MSG = "outputMessage";
 
    public MySQLRequest() {
        super("JSONRequest");
    }
    
    public static boolean Create(Context context, String kind, String id) {
    	
    	int kindInt = Integer.parseInt(kind);
    	
    	//make sure there is an internet connection 
    	boolean internet = MainActivity.isNetworkAvailable(context);        
    	if(internet == false) return false;   	
    	
    	//DO NOT run certain queries more than once
    	if(kindInt == kindEventVolQuery) {
    		
        	if(EventVolunteerData.status == EventVolunteerData.statusLoading ||
        			EventVolunteerData.status == EventVolunteerData.statusLoaded)
        		return false;	
    		EventVolunteerData.status = EventVolunteerData.statusLoading;
    	}
    	else if(kindInt == kindFindQuery) {
    		
        	if(FindData.status == FindData.statusLoading ||
        			FindData.status == FindData.statusLoaded)
        		return false;
        	FindData.status = FindData.statusLoading;
    	}    	
    	else if(kindInt == kindInterestQuery) {
    		
        	if(InterestData.status == InterestData.statusLoading ||
        			InterestData.status == InterestData.statusLoaded)
        		return false;
        	InterestData.status = InterestData.statusLoading;
    	}
    	
        Intent msgIntent = new Intent(context, MySQLRequest.class);
        msgIntent.putExtra(MySQLRequest.IN_MSG, "MySQLRequest");
        msgIntent.putExtra("kind", kind.trim());        
        msgIntent.putExtra("id", id.trim());
        context.startService(msgIntent);
        
        return true;
    }    
 
    @Override
    protected void onHandleIntent(Intent intent) {
 
        //Get Intent extras that were passed
        inMessage = intent.getStringExtra(IN_MSG);
        if(inMessage.trim().equalsIgnoreCase("MySQLRequest")){
        	
        	String kind = intent.getStringExtra("kind");
            String id = intent.getStringExtra("id");
            contactWebService(kind, id);
        }
        else if(inMessage.trim().equalsIgnoreCase("getSomethingElse")){
            //you can choose to implement another transaction here
        }
    }
 
    private void contactWebService(String kind, String id) {
    	
    	int kindInt = Integer.parseInt(kind);
 
        String url = "http://54.200.107.187:8080/CommunityService/Android";
                
        //add name value pair for the country code
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("kind", kind));
        nameValuePairs.add(new BasicNameValuePair("id", id));
    	
        String response = null;
        
        if(MainActivity.test) {

        	if(kindInt == kindVolQuery) {
        		response = "1``user``~~7``h``s``386-631-1085``user@gmail.com``~~";
        	}
        	else if(kindInt == kindEventVolQuery) {
        		response = "2``7``~~0``1``7``~~1``2``7``~~";
        	}
        	else if(kindInt == kindEventQuery) {
       			response = "3``0``~~0``humane society``walk pitbulls``orlando``monday``friday``~~";
        	}
        	else if(kindInt == kindFindQuery) {
    			response = "4``0``~~2``homeless helper``help those on the street``orlando``monday``friday``~~";
        	}
        	else if(kindInt == kindInterestQuery) {
    			response = "5``0``~~2``Animals``I love animals``~~";
        	}
        	else if(kindInt == kindEventInterestQuery) {
    			response = "6``0``~~1``0``2``~~";
        	}
        	else
        		Obj.BreakPoint();
        }
        else
            response = sendHttpRequest(url,nameValuePairs);        	
        
        //broadcast message that we have received the response 
        //from the WEB Service
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(IN_MSG, inMessage);
        broadcastIntent.putExtra(OUT_MSG, response);
        sendBroadcast(broadcastIntent);
    }
 
    private String sendHttpRequest(String url, List<NameValuePair> nameValuePairs) {
 
        int REGISTRATION_TIMEOUT = 15 * 1000;
        int WAIT_TIMEOUT = 60 * 1000;
        HttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        HttpResponse response;
        String content =  "";
        
        try {
 
            //http request parameters
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);
 
            //http POST
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
 
            //send the request and receive the response
            response = httpclient.execute(httpPost);
 
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                content = out.toString();
            }
 
            else{
                //Closes the connection.
                Log.w("HTTP1:",statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
 
        } catch (ClientProtocolException e) {
            Log.w("HTTP2:",e );
            Obj.BreakPoint();
        } catch (IOException e) {
            Log.w("HTTP3:",e );
            Obj.BreakPoint();
        }catch (Exception e) {
            Log.w("HTTP4:",e );
            Obj.BreakPoint();
        }
 
        //send back the response String
        return content;
    }
}