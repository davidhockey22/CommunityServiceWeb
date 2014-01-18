package tut1.login;

import java.util.ArrayList;
import java.util.List;

public class EventVolunteerData extends Obj{

	public static final int statusNotLoaded = 0, statusLoading = 1, statusLoaded = 2;
	public static int status = statusNotLoaded;
	
    private static List<String> allString = new ArrayList<String>();
    private static List<EventVolunteerData> all = new ArrayList<EventVolunteerData>();
	
	private String eventVolunteerID;
	private String eventID;
	private String volunteerID;

    public EventVolunteerData() {
    }

    public void init(String ID, String EventID, String VolunteerID) {
    	
    	eventVolunteerID = ID;
    	eventID = EventID;
    	volunteerID = VolunteerID;
    }
    boolean initFromMySQLString(String S) {
    	
    	init(null, null, null);
    	
    	return Obj.parseMySQLObj(this, S);
    }
    void OnParseMySQLData(String Data, int Index){
    	
    	if(Index == 0) eventVolunteerID = Data;
    	else if(Index == 1) eventID = Data;
    	else if(Index == 2) volunteerID = Data;
    	else Obj.BreakPoint();
    }
    
    public void AddToAllList() {
    	
    	if( allString.contains(eventVolunteerID) )
    		return;
    	
    	all.add(this);
    	allString.add(eventVolunteerID);

		if(MainActivity.test == false ) {
			//get interests
    		MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindEventQuery), eventID);
		}    	
    }    
    
	public String getEventVolunteerID() {
		return eventVolunteerID;
	}

	public void setEventVolunteerID(String eventVolunteerID) {
		this.eventVolunteerID = eventVolunteerID;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getVolunteerID() {
		return volunteerID;
	}

	public void setVolunteerID(String volunteerID) {
		this.volunteerID = volunteerID;
	}    
}