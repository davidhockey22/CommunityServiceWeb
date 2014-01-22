package tut1.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventData extends Obj{
	
    private static List<String> allEventsString = new ArrayList<String>();
    private static List<EventData> allEvents = new ArrayList<EventData>();
    
    private static List<String> eventsSignedUpForString = new ArrayList<String>();
    private static List<EventData> eventsSignedUpFor = new ArrayList<EventData>();
	
    private static List<String> findListString = null;
    private static List<EventData> findList = null;

    private List<String> interestIDList = new ArrayList<String>();
    
    public static final int childDataTotal = 3;
	
	private String eventID;

	private String eventName;
	private String description;
	private String location;
	
	private String beginTime;
	private String endTime;
	
    public EventData() {
    }

    public void init(String EventID, String EventName, String Description, String Location, String BeginTime, String EndTime) {
    	
    	eventID = EventID;
    	eventName = EventName;
    	description = Description;
    	location = Location;
    	beginTime = BeginTime;
    	endTime = EndTime;
    }	
    boolean initFromMySQLString(String S) {
    	
    	init(null, null, null, null, null, null);

    	return Obj.parseMySQLObj(this, S);
    }	
    void OnParseMySQLData(String Data, int Index){
    	
    	if(Index == 0) eventID = Data;
    	else if(Index == 1) eventName = Data;
    	else if(Index == 2) description = Data;
    	else if(Index == 3) location = Data;
    	else if(Index == 4) beginTime = Data;
    	else if(Index == 5) endTime = Data;
    	else Obj.BreakPoint();
    }
    public void AddToAllEventsList() {
    	
    	if( allEventsString.contains(eventID) )
    		return;
    	
    	allEvents.add(this);
    	allEventsString.add(eventID); 	
    }
    public void AddToEventsSignedUpForList() {
    	
    	if( eventsSignedUpForString.contains(eventID) )
    		return;
    	
    	eventsSignedUpForString.add(eventID);
    	eventsSignedUpFor.add(this);
    }    
    public static void AddToInterestList(String EventID, String InterestID) {
    	
    	EventData ev = GetFromList(EventID);
    	if( ev == null) {
    		Obj.BreakPoint();
    		return;
    	}
    	
    	if( ev.interestIDList.contains(InterestID))
    		return;
    	
    	ev.interestIDList.add(InterestID);
    }
    public static EventData GetEventsSignedUpForLocation(int Location) {
    	
    	return eventsSignedUpFor.get(Location);
    }
    public static int GetEventsSignedUpForSize() {
    	
    	return eventsSignedUpFor.size();
    }
    public static void SetFindList(int EventAdapterKind) {
    	
        findListString = new ArrayList<String>();
        findList = new ArrayList<EventData>();
    	
    	Iterator<String> stI = allEventsString.iterator();
    	Iterator<EventData> evI = allEvents.iterator();
    	
    	String st = null;
    	EventData ev = null;
    	
    	while(stI.hasNext() && evI.hasNext()) {
    		st = stI.next();
    		ev = evI.next();
    		
    		if( eventsSignedUpForString.contains(st) ) {} //if already signed up for event, do not put in "find result list"
    		else {
    			
    			//if find by distance
    			if(EventAdapterKind == EventAdapter.kindFindByDistance) {
    				
    				findListString.add(st);
    				findList.add(ev);
    			}
    			
    			//if find by interest
    			else if(EventAdapterKind == EventAdapter.kindFindByInterest){
    				
    				//and event interest matches selected "find by interest"
    				if( ev.interestIDList.contains(EventAdapter.findByInterestID) ) {
    					
        				findListString.add(st);
        				findList.add(ev);    					
    				}
    				
    			}
    			else
    				Obj.BreakPoint();
    		}
    	}    	
    }
    public static EventData GetFindListLocation(int Location) {
    	
    	return findList.get(Location);
    }
    public static int GetFindListSize() {
    	
    	return findList.size();
    }
    private static EventData GetFromList(String EventID) {
    	
    	Iterator<EventData> it = allEvents.iterator();
    	EventData node = null;
    	
    	while(it.hasNext() ) {
    		node = it.next();
    		
    		if(node.getEventID().equals(EventID))
    			return node;
    	}
    	
    	return null;
    }
    static public void MakeEventInterestQueries() {
    	
    	Iterator<EventData> it = allEvents.iterator();
    	EventData node = null;
    	
    	while(it.hasNext() ) {
    		node = it.next();
    		
			MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindEventInterestQuery), node.getEventID());
    	}
    	
    }
	public String getEventID() {
		return eventID;
	}

	public void setEventId(String eventID) {
		this.eventID = eventID;
	}
	
    public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}