package tut1.login;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
import org.CommunityService.EntitesUnmapped.Event;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
 
public class MySQLRequest extends IntentService{
	
    public static final int kindVolQuery = 1, //id = name
    		kindEventQuery = 3, //id = volunteer id   		
    		kindFindQuery = 4, //id = null (returns all events)
    		kindRegister = 5,
    		kindByDate = 6,
    		kindSkills = 7,
    		kindBySkill = 8,
    		kindByInterest = 9,
    		kindInterests = 10,
    		kindToken = 11,
    		kindSignUpForEvent = 12,
    		kindEventVolunteer = 13,
    		kindEventRemove = 14,
    		kindOrg = 15,
    		kindOrgApprove = 16,
    		kindOrgRemove = 17,
    		kindOrgAddHours = 18,
    		kindOrgEvents = 19,
    		kindVolunteersByEvent = 20,
    		kindEventVolunteerOrg = 21, 
    		kindApprovedVolunteersByEvent = 22,
    		kindApprovedEventVolunteerOrg = 23;
 
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
    	if(kindInt == kindEventQuery) {
    		
        	if(EventData.status == EventData.statusLoading ||
        			EventData.status == EventData.statusLoaded)
        		return false;	
    		EventData.status = EventData.statusLoading;
    	}
    	
        Intent msgIntent = new Intent(context, MySQLRequest.class);
        msgIntent.putExtra(MySQLRequest.IN_MSG, "MySQLRequest");
        msgIntent.putExtra("kind", kind.trim());        
        if(id != null) msgIntent.putExtra("id", id.trim());
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
    	
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        String response = null;
    	int kindInt = Integer.parseInt(kind);
        
        if(MainActivity.test) {
        }
        else {
        	
        	String url = "";

        	if(kindInt == kindVolQuery) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/login";
        		//url += "?username=" + Login.current.username;
        		//url += "&password=" + Login.current.password;
        		
        		nameValuePairs.add(new BasicNameValuePair("username", Login.current.username));
        		nameValuePairs.add(new BasicNameValuePair("password", Login.current.password));

        		response = sendHttpRequest(url, nameValuePairs); 
        	}
        	else if(kindInt == kindEventQuery) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getEvents";
        		url += "?ID=" + id;

        		response = sendHttpRequest(url, nameValuePairs);        		
        	}
        	else if(kindInt == kindFindQuery) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getEvents";
        		
        		response = sendHttpRequest(url, nameValuePairs);        		
        	}
        	else if(kindInt == kindRegister) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/register";
        		//url += "?username=" + Login.current.username;
        		//url += "&password=" + Login.current.password;
        		
        		nameValuePairs.add(new BasicNameValuePair("username", Register.current.user));
        		nameValuePairs.add(new BasicNameValuePair("password", Register.current.password));
        		nameValuePairs.add(new BasicNameValuePair("firstName", Register.current.first));
        		nameValuePairs.add(new BasicNameValuePair("lastName", Register.current.last));
        		nameValuePairs.add(new BasicNameValuePair("phoneNumber", Register.current.phone));
        		nameValuePairs.add(new BasicNameValuePair("email", Register.current.email));

        		response = sendHttpRequest(url, nameValuePairs);
        	}
        	else if(kindInt == kindByDate){
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/byDate";

        		response = sendHttpRequest(url, nameValuePairs);        		
        	}
        	else if(kindInt == kindSkills){
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getSkills";

        		response = sendHttpRequest(url, nameValuePairs);        		
        	}
        	else if(kindInt == kindBySkill){
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/bySkill";
        		url += "?ID=" + id;
        		
        		response = sendHttpRequest(url, nameValuePairs);        		
        	}        	
        	else if(kindInt == kindByInterest){
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/byInterest";
        		url += "?ID=" + id;
        		
        		response = sendHttpRequest(url, nameValuePairs);        		
        	}
        	else if(kindInt == kindInterests){
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getInterests";

        		response = sendHttpRequest(url, nameValuePairs);        		
        	}
        	else if(kindInt == kindToken){
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/loginToken";

        		nameValuePairs.add(new BasicNameValuePair("token", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		
        	}   
        	else if(kindInt == kindSignUpForEvent) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/signUpForEvent";

        		nameValuePairs.add(new BasicNameValuePair("volID", VolunteerData.current.getVolunteerID()));
        		nameValuePairs.add(new BasicNameValuePair("eventID", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}
        	else if(kindInt == kindEventVolunteer ||
        			kindInt == kindEventVolunteerOrg ||
        			kindInt == kindApprovedEventVolunteerOrg) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getEventVolunteer";

        		if(kindInt == kindEventVolunteer) {
        			nameValuePairs.add(new BasicNameValuePair("volID", VolunteerData.current.getVolunteerID()));
        			nameValuePairs.add(new BasicNameValuePair("eventID", id));
        		}
        		else if(kindInt == kindEventVolunteerOrg) {
        			nameValuePairs.add(new BasicNameValuePair("volID", id));
        			nameValuePairs.add(new BasicNameValuePair("eventID", OrgApproveFragment.current.eventId));        			
        		}
        		else if(kindInt == kindApprovedEventVolunteerOrg) {
        			nameValuePairs.add(new BasicNameValuePair("volID", id));
        			nameValuePairs.add(new BasicNameValuePair("eventID", OrgHoursFragment.current.eventId));        			
        		}
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}        	
        	else if(kindInt == kindEventRemove) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/RemoveEvent";

        		nameValuePairs.add(new BasicNameValuePair("volID", VolunteerData.current.getVolunteerID()));
        		nameValuePairs.add(new BasicNameValuePair("eventID", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}
        	else if(kindInt == kindOrg) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getOrgs";

        		nameValuePairs.add(new BasicNameValuePair("ID", VolunteerData.current.getVolunteerID()));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}
        	else if(kindInt == kindOrgEvents) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/byOrg";

        		nameValuePairs.add(new BasicNameValuePair("ID", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}        	
        	else if(kindInt == kindOrgApprove) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/orgApprove";
        		
        		int i = 0;
        		for(VolunteerData v : VolunteerData.needsOrgApproval) {        			
        			if(v.isApproved()) {
        				nameValuePairs.add(new BasicNameValuePair("approve" + i, v.getVolunteerID()));
        				i++;
        			}
        		}
        		nameValuePairs.add(new BasicNameValuePair("total", Integer.toString( i )));
        		
        		nameValuePairs.add(new BasicNameValuePair("eventId", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}
        	else if(kindInt == kindOrgRemove) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/orgReject";
        		
        		int i = 0;
        		for(VolunteerData v : VolunteerData.needsOrgApproval) {        			
        			if(v.isApproved() == false) {
        				nameValuePairs.add(new BasicNameValuePair("reject" + i, v.getVolunteerID()));
        				i++;
        			}
        		}
        		nameValuePairs.add(new BasicNameValuePair("total", Integer.toString( i )));
        		
        		nameValuePairs.add(new BasicNameValuePair("eventId", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}
        	else if(kindInt == kindOrgAddHours) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/hoursWorked";
        		
        		//convert
        		String hours = Integer.toString( VolunteerData.saveVolunteer.getAddHours() * VolunteerData.addHoursFactor );
        		String rating = Integer.toString( VolunteerData.saveVolunteer.getAddRating() * VolunteerData.addRatingFactor );
        		
        		nameValuePairs.add(new BasicNameValuePair("volID", VolunteerData.saveVolunteer.getVolunteerID()));
        		nameValuePairs.add(new BasicNameValuePair("hours", hours));
        		nameValuePairs.add(new BasicNameValuePair("rating", rating));
        		nameValuePairs.add(new BasicNameValuePair("bonus", "1.0"));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}
        	else if(kindInt == kindVolunteersByEvent ||
        			kindInt == kindApprovedVolunteersByEvent) {
        		
        		url = "http://54.200.107.187:8080/CommunityService/Android/getVolunteersByEvent";

        		nameValuePairs.add(new BasicNameValuePair("ID", id));
        		
        		response = sendHttpRequest(url, nameValuePairs);        		        		
        	}           	
        	else
        		Obj.BreakPoint();
        }        	
                
        //broadcast message that we have received the response 
        //from the WEB Service
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(RequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra("mysqlRequest", "mysqlRequest");
        broadcastIntent.putExtra("kind", kind);
        broadcastIntent.putExtra("id", id);
        broadcastIntent.putExtra("response", response);
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