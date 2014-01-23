package tut1.login;

//import org.json.JSONException;
//import org.json.JSONObject;

//import com.google.gson.Gson;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//broadcast receiver to receive messages sent from the JSON IntentService
public class RequestReceiver extends BroadcastReceiver{

  public static final String PROCESS_RESPONSE = "com.as400samplecode.intent.action.PROCESS_RESPONSE";

  @Override
  public void onReceive(Context context, Intent intent) {

      String response = null;
      String responseType = intent.getStringExtra(MySQLRequest.IN_MSG);
      
      if(responseType.trim().equalsIgnoreCase("MySQLRequest")){
          response = intent.getStringExtra(MySQLRequest.OUT_MSG);
          parseResponse(response);
      }
      else if(responseType.trim().equalsIgnoreCase("getSomethingElse")){
          //you can choose to implement another transaction here
      }

  }
  
  private void parseResponse(String response){
	  
	  if(response == null) return;
	  
      int kind = Integer.parseInt( Obj.parseMySQLKind(response) );
      Obj.parseMySQLID(response);
            
      //make sure there is more to parse
      int prevParseIndex = Obj.parseIndex;
      String anotherObjStr = Obj.parseMySQLData(response);
      Obj.parseIndex = prevParseIndex;
      
      if(anotherObjStr == null) {}
      else {
    	  boolean anotherObj = true;
    	  if(anotherObj) {

    		  if(kind == MySQLRequest.kindVolQuery) {

    			  VolunteerData vol;
    			  int count = 0;

    			  while(anotherObj) {

    				  count++;
    				  if(count > 1)
    					  Obj.BreakPoint(); //if more than one volunteer from the mysql database has the same username, this is an ERROR! 

    				  //do not create more than one volunteer.
    				  //this code can be triggered more than once if the user fails to enter the correct username and password and another mysql request is made
    				  if(VolunteerData.current == null) vol = new VolunteerData();
    				  else vol = VolunteerData.current;

    				  anotherObj = vol.initFromMySQLString(response);

    				  if(Login.current != null)
    					  Login.current.OnMySQLRequest(); //notify login screen
    			  }
    		  }
    		  else if(kind == MySQLRequest.kindEventVolQuery) {

    			  EventVolunteerData evVol; 
    			  while(anotherObj) {

    				  evVol = new EventVolunteerData();
    				  anotherObj = evVol.initFromMySQLString(response);
    				  
    				  evVol.AddToAllList();
    			  }
    		  }
    		  else if(kind == MySQLRequest.kindEventQuery ||
    				  kind == MySQLRequest.kindFindQuery ) {

    			  EventData ev; 
    			  while(anotherObj) {

    				  ev = new EventData();
    				  anotherObj = ev.initFromMySQLString(response);
    				  
    				  ev.AddToAllEventsList();
    				  
    				  if(kind == MySQLRequest.kindEventQuery)
    					  ev.AddToEventsSignedUpForList();
    			  } 
    			  
    			  if(kind == MySQLRequest.kindFindQuery)
    				  MainFragment.current.onFindQueryDone();    			  
    		  }
    		  else if(kind == MySQLRequest.kindInterestQuery) {

    			  InterestData dat = null; 
    			  while(anotherObj) {

    				  dat = new InterestData();
    				  anotherObj = dat.initFromMySQLString(response);
    				  
    				  dat.AddToAllList();
    			  }
    		  }
    		  else if(kind == MySQLRequest.kindEventInterestQuery) {

    			  EventInterestData dat; 
    			  while(anotherObj) {

    				  dat = new EventInterestData();
    				  anotherObj = dat.initFromMySQLString(response);
    			  }
    		  }    		  
    	  }
      }
      
	  if(kind == MySQLRequest.kindEventVolQuery)
		  EventVolunteerData.status = EventVolunteerData.statusLoaded;      
      
//	  JSONObject responseObj = null;
//         
//      try {
//          //create JSON object from JSON string
//          responseObj = new JSONObject(response); 
//          //get the success property
//          boolean success = responseObj.getBoolean("success");
//          if(success){
//              
//              Gson gson = new Gson();
//              //get the country information property
//              String eventInfo = responseObj.getString("eventInfo");
//              //create java object from the JSON object
//              EventData ev = gson.fromJson(eventInfo, EventData.class); 
//          }
//          else {
//              
//        	  //error
//          }
//      } catch (JSONException e) {
//          e.printStackTrace();
//      }
  }
}