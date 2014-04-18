package tut1.login;

//import org.json.JSONException;
//import org.json.JSONObject;

//import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.CommunityService.EntitesUnmapped.Event;
import org.CommunityService.EntitesUnmapped.Interest;
import org.CommunityService.EntitesUnmapped.Skill;
import org.CommunityService.EntitesUnmapped.Volunteer;
import org.CommunityService.EntitesUnmapped.VolunteerDevice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//broadcast receiver to receive messages sent from the JSON IntentService
public class RequestReceiver extends BroadcastReceiver{

	public static final String PROCESS_RESPONSE = "com.as400samplecode.intent.action.PROCESS_RESPONSE";

	@Override
	public void onReceive(Context context, Intent intent) {

		String kind = null;
		String response = null;
		String responseType = intent.getStringExtra("mysqlRequest");

		if(responseType.trim().equalsIgnoreCase("mysqlRequest")){

			kind = intent.getStringExtra("kind");
			response = intent.getStringExtra("response");

			parseResponse( Integer.parseInt(kind), response);
		}
		else if(responseType.trim().equalsIgnoreCase("getSomethingElse")){
			//you can choose to implement another transaction here
		}

	}

	private void parseResponse(int kind, String response){

		if(kind == MySQLRequest.kindVolQuery ||
				kind == MySQLRequest.kindToken) {
			
			if(response == null || response.equals("") ) {}
			else {

				//do not create more than one volunteer.
				//this code can be triggered more than once if the user fails to enter the correct username and password and another mysql request is made
				VolunteerData vol = null;
				if(VolunteerData.current == null) vol = new VolunteerData();
				else vol = VolunteerData.current;	
		
				if(MainActivity.test) { //test
					vol.init("4", "h", "s", "3866311085", "henry@gmail.com", "");
				}
				else {
					Gson gson = new Gson();
					Volunteer v = gson.fromJson(response, Volunteer.class);
					
					if(v.getVolunteerId() != null) { //possible if wrong username / password
					
						//get token
						String token = v.getSalt();
						
//						Iterator<VolunteerDevice> i = v.getVolunteerDevices().iterator();
//						String token = null;
//						if( i.hasNext() ) {
//							token = i.next().getDeviceTokenInternal();
//						}
						
						vol.init(
								v.getVolunteerId().toString(),
								v.getFirstName(),
								"",
								v.getPhoneNumber(),
								v.getEmailAddress(),
								token);
					}
				}
			}

			if(Login.current != null)
				Login.current.OnMySQLRequest(kind); //notify login screen
		}
		else if(kind == MySQLRequest.kindEventQuery ||
				kind == MySQLRequest.kindFindQuery ||
				kind == MySQLRequest.kindByDate ||
				kind == MySQLRequest.kindByInterest ||
				kind == MySQLRequest.kindBySkill) {
			
			if(response == null || response.equals("")) {}
			else {

				Gson gson = new Gson();
				List<Event> list = gson.fromJson(response, new TypeToken<List<Event>>(){}.getType());
	
				EventData ev;
				Event element;
				for (int i = 0; i < list.size(); i++) {
					element = list.get(i);
	
					//pass gson obj to custom class. at some point custom classes should not be used
					ev = new EventData();
					ev.init(element.getEventId().toString(),
							element.getEventName(),
							element.getDescription(),
							element.getLocation(),
							element.getBeginTime().toString(),
							element.getEndTime().toString());
	
					ev.AddToAllEventsList();
	
					if(kind == MySQLRequest.kindEventQuery)
						ev.AddToEventsSignedUpForList();
					
					if(kind == MySQLRequest.kindByDate)
						ev.AddToEventsByDateList();					
	
					if(kind == MySQLRequest.kindBySkill)
						ev.AddToEventsBySkillList();	

					if(kind == MySQLRequest.kindByInterest)
						ev.AddToEventsByInterestList();
				}
			}
			
			if(kind == MySQLRequest.kindFindQuery)
				MainFragment.current.onFindQueryDone();
			
			if(kind == MySQLRequest.kindByDate)
				EventData.onByDateQueryDone();				

			if(kind == MySQLRequest.kindByInterest)
				EventData.onByInterestQueryDone();			
			
			if(kind == MySQLRequest.kindBySkill)
				EventData.onBySkillQueryDone();	
		}
		else if(kind == MySQLRequest.kindRegister) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				Register.current.onRegistration(true);
			}
			else
				Register.current.onRegistration(false);
		}
		else if(kind == MySQLRequest.kindSignUpForEvent) {
			
			Obj.BreakPoint();
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				Register.current.onRegistration(true);
			}
			else
				Register.current.onRegistration(false);
		}		
		else if(kind == MySQLRequest.kindInterests ) {
			
			if(response == null || response.equals("") )
				return;

			Gson gson = new Gson();
			List<Interest> list = gson.fromJson(response, new TypeToken<List<Interest>>(){}.getType());

			InterestData ev;
			Interest element;
			for (int i = 0; i < list.size(); i++) {
				element = list.get(i);

				//pass gson obj to custom class. at some point custom classes should not be used
				ev = new InterestData();
				ev.init(element.getInterestId().toString(),
						element.getName(),
						element.getDescription() );

				ev.AddToAllList();
			}
		}		
		else if(kind == MySQLRequest.kindSkills ) {

			if(response == null || response.equals("") )
				return;
			
			Gson gson = new Gson();
			List<Skill> list = gson.fromJson(response, new TypeToken<List<Skill>>(){}.getType());

			SkillData ev;
			Skill element;
			for (int i = 0; i < list.size(); i++) {
				element = list.get(i);

				//pass gson obj to custom class. at some point custom classes should not be used
				ev = new SkillData();
				ev.init(element.getSkillId().toString(),
						element.getSkillName(),
						element.getDescription() );

				ev.AddToAllList();
			}
		}		

		if(kind == MySQLRequest.kindEventQuery)
			EventData.status = EventData.statusLoaded;
	}
}