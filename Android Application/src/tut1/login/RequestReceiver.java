package tut1.login;

//import org.json.JSONException;
//import org.json.JSONObject;

//import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.CommunityService.EntitesUnmapped.Event;
import org.CommunityService.EntitesUnmapped.EventVolunteer;
import org.CommunityService.EntitesUnmapped.Interest;
import org.CommunityService.EntitesUnmapped.Organization;
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
		String id = null;
		String response = null;
		String responseType = intent.getStringExtra("mysqlRequest");

		if(responseType.trim().equalsIgnoreCase("mysqlRequest")){

			kind = intent.getStringExtra("kind");
			id = intent.getStringExtra("id");
			response = intent.getStringExtra("response");

			parseResponse( Integer.parseInt(kind), id, response);
		}
		else if(responseType.trim().equalsIgnoreCase("getSomethingElse")){
			//you can choose to implement another transaction here
		}

	}

	private void parseResponse(int kind, String id, String response){

		if(kind == MySQLRequest.kindVolQuery ||
				kind == MySQLRequest.kindToken) {
			
			if(response != null && !response.equals("") ) {

				//do not create more than one volunteer.
				//this code can be triggered more than once if the user fails to enter the correct username and password and another mysql request is made
				VolunteerData vol = new VolunteerData();
				VolunteerData.current = vol;	
		
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
				kind == MySQLRequest.kindBySkill ||
				kind == MySQLRequest.kindOrgEvents) {
			
			if(response != null && !response.equals("") ) {
				
				if(kind == MySQLRequest.kindEventQuery) {
					
					EventData.signedUpForString = new ArrayList<String>();
					EventData.signedUpFor = new ArrayList<EventData>();
				}

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
						ev.AddToSignedUpForList();
					
					if(kind == MySQLRequest.kindByDate)
						ev.AddToEventsByDateList();					
	
					if(kind == MySQLRequest.kindBySkill)
						ev.AddToEventsBySkillList();	

					if(kind == MySQLRequest.kindByInterest)
						ev.AddToEventsByInterestList();
					
					if(kind == MySQLRequest.kindOrgEvents)
						EventData.orgEvent.add(ev);
				}
			}
			
			if(kind == MySQLRequest.kindEventQuery)
				MainFragment.current.onEventQueryDone();
			
			if(kind == MySQLRequest.kindByDate)
				EventData.onByDateQueryDone();				

			if(kind == MySQLRequest.kindByInterest)
				EventData.onByInterestQueryDone();			
			
			if(kind == MySQLRequest.kindBySkill)
				EventData.onBySkillQueryDone();
			
			if(kind == MySQLRequest.kindOrgEvents)
				OrgEventFragment.current.onQueryDone();				
		}
		else if(kind == MySQLRequest.kindRegister) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				Register.current.onRegistration(true);
			}
			else
				Register.current.onRegistration(false);
		}
		else if(kind == MySQLRequest.kindOrgApprove) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				OrgApproveFragment.current.onQueryDone(kind, true);
			}
			else
				OrgApproveFragment.current.onQueryDone(kind, false);
		}
		else if(kind == MySQLRequest.kindOrgRemove) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				OrgApproveFragment.current.onQueryDone(kind, true);
			}
			else
				OrgApproveFragment.current.onQueryDone(kind, false);
		}
		else if(kind == MySQLRequest.kindOrgAddHours) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				OrgHoursAdapter.current.onQueryDone(true);
			}
			else
				OrgHoursAdapter.current.onQueryDone(false);
		}			
		else if(kind == MySQLRequest.kindSignUpForEvent) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				
				EventData dat = EventData.GetEventInAllEventsList(id);
				if(dat == null)
					Obj.BreakPoint();
				else
					dat.AddToSignedUpForList();
				
				if(EventAdapter.current != null)
					EventAdapter.current.onSignUpEvent(true);
			}
			else {
				if(EventAdapter.current != null)
					EventAdapter.current.onSignUpEvent(false);				
			}
		}
		else if(kind == MySQLRequest.kindEventRemove) {
			
			if(response.contains("1")) { //servlet actually returns "1/n"
				
				EventData.RemoveEventInSignedUpForList(id);
				
				if(EventAdapter.current != null)
					EventAdapter.current.OnRemoveEvent(true);
			}
			else {
				if(EventAdapter.current != null)
					EventAdapter.current.OnRemoveEvent(true);
			}
		}		
		else if(kind == MySQLRequest.kindInterests ) {
			
			if(response != null && !response.equals("") ) {

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
		}		
		else if(kind == MySQLRequest.kindSkills ) {

			if(response != null && !response.equals("") ) {
			
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
		}		
		else if(kind == MySQLRequest.kindEventVolunteer ||
				kind == MySQLRequest.kindEventVolunteerOrg ||
				kind == MySQLRequest.kindApprovedEventVolunteerOrg) {
			
			if(response != null && !response.equals("") ) {
			
				Gson gson = new Gson();
				EventVolunteer evVol = gson.fromJson(response, EventVolunteer.class);
				
				if(kind == MySQLRequest.kindEventVolunteer) {
					EventData evDat = EventData.GetEventInSignedUpForList(id);
					if(evDat != null) {
						evDat.setApproved(evVol.getApproved());
					}
				}
				else if(kind == MySQLRequest.kindEventVolunteerOrg) {
					for(VolunteerData v : VolunteerData.needsOrgApproval) {
						if(v.getVolunteerID().equals(id)) {
							v.setApproved(evVol.getApproved());
							break;
						}
					}
					
					if(OrgApproveFragment.current != null)
						OrgApproveFragment.current.onQueryDone(MySQLRequest.kindEventVolunteerOrg, true);
				}
				else if(kind == MySQLRequest.kindApprovedEventVolunteerOrg) {
					for(VolunteerData v : VolunteerData.assignHours) {
						if(v.getVolunteerID().equals(id)) {
							v.setApproved(evVol.getApproved());
							break;
						}
					}
					
					if(OrgHoursFragment.current != null)
						OrgHoursFragment.current.onQueryDone(MySQLRequest.kindApprovedEventVolunteerOrg, true);
				}				
			}
		}
		else if(kind == MySQLRequest.kindOrg ) {

			if(response != null && !response.equals("") ) {
			
				Gson gson = new Gson();
				List<Organization> list = gson.fromJson(response, new TypeToken<List<Organization>>(){}.getType());
	
				OrgData ev;
				Organization element;
				for (int i = 0; i < list.size(); i++) {
					element = list.get(i);
	
					//pass gson obj to custom class. at some point custom classes should not be used
					ev = new OrgData();
					ev.init( element.getOrgId().toString(), element.getOrgName() );
				}
			}
			
			if(OrgFragment.current != null)
				OrgFragment.current.onQueryDone();
		}
		else if(kind == MySQLRequest.kindVolunteersByEvent ||
				kind == MySQLRequest.kindApprovedVolunteersByEvent ) {
			
			if(response != null && !response.equals("") ) {
			
				Gson gson = new Gson();
				List<Volunteer> list = gson.fromJson(response, new TypeToken<List<Volunteer>>(){}.getType());
	
				VolunteerData ev;
				Volunteer element;
				for (int i = 0; i < list.size(); i++) {
					element = list.get(i);
	
					//pass gson obj to custom class. at some point custom classes should not be used
					ev = new VolunteerData();
					ev.initNonUser( element.getVolunteerId().toString(),
							element.getVolunteerName(),
							element.getPhoneNumber(),
							element.getEmailAddress(),
							element.getDescription(),
							element.getFirstName(),
							element.getLastName(),
							element.getPoints().toString(),
							element.getHoursWorked().toString(),
							element.getAvgRating().toString());
					
					if(kind == MySQLRequest.kindVolunteersByEvent)
						VolunteerData.needsOrgApproval.add(ev);
					else
						VolunteerData.assignHours.add(ev);
				}
			}
			
			if(kind == MySQLRequest.kindVolunteersByEvent && OrgApproveFragment.current != null)
				OrgApproveFragment.current.onQueryDone(MySQLRequest.kindVolunteersByEvent, true);
			
			else if(kind == MySQLRequest.kindApprovedVolunteersByEvent && OrgHoursFragment.current != null)
				OrgHoursFragment.current.onQueryDone(MySQLRequest.kindApprovedVolunteersByEvent, true);			
		}		
		
		if(kind == MySQLRequest.kindEventQuery)
			EventData.status = EventData.statusLoaded;
	}
}