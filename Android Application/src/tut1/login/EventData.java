package tut1.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventData extends Obj{
	
	public static final int statusNotLoaded = 0, statusLoading = 1, statusLoaded = 2;
	public static int status = statusNotLoaded;
	
	public static boolean doByDateQuery = true;	
	public static boolean doByInterestQuery = true;
	public static boolean doBySkillQuery = true;
	
    private static List<String> allEventsString = new ArrayList<String>();
    private static List<EventData> allEvents = new ArrayList<EventData>();
    
    public static List<String> signedUpForString = new ArrayList<String>();
    public static List<EventData> signedUpFor = new ArrayList<EventData>();
	
    private static List<String> findListString = null;
    private static List<EventData> findList = null;
    
    private static List<String> byDateString = new ArrayList<String>();
    private static List<EventData> byDate = new ArrayList<EventData>();    

    private static List<String> bySkillString = new ArrayList<String>();
    private static List<EventData> bySkill = new ArrayList<EventData>();    

    private static List<String> byInterestString = new ArrayList<String>();
    private static List<EventData> byInterest = new ArrayList<EventData>();
    
    public static List<EventData> orgEvent = new ArrayList<EventData>();
    
    private List<String> interestIDList = new ArrayList<String>();
    
    public static final int childDataTotal = 3;
	
	private String eventID;

	private String eventName;
	private String description;
	private String location;
	
	private String beginTime;
	private String endTime;
	
	private boolean approved;

	public EventData() {
    }

    public void init(String EventID, String EventName, String Description, String Location, String BeginTime, String EndTime) {
    	
    	eventID = EventID;
    	eventName = EventName;
    	description = Description;
    	location = Location;
    	beginTime = BeginTime;
    	endTime = EndTime;
    	
    	approved = false;
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
    public static EventData GetEventInAllEventsList(String id) {
    	
    	for(EventData e : allEvents) {
    		if(e.getEventID().equals(id))
    			return e;
    	}
    	
    	return null;	
    }    
    public void AddToSignedUpForList() {
    	
    	if( signedUpForString.contains(eventID) )
    		return;
    	
    	signedUpForString.add(eventID);
    	signedUpFor.add(this);
    }
    static public void RemoveEventInSignedUpForList(String id) {
    	
    	int loc = -1;
    	for(EventData e : signedUpFor) {
    		loc++;
    		if(e.getEventID().equals(id))
    			break;
    	}
    	if(loc > -1) signedUpFor.remove(loc);
    	
    	loc = -1;
    	for(String e : signedUpForString) {
    		loc++;
    		if(e.equals(id))
    			break;
    	}
    	if(loc > -1) signedUpForString.remove(loc);    	
    }    
    static public boolean IsInSignedUpForList(String id) {
    	
    	for(EventData e : signedUpFor) {
    		if(e.getEventID() == id)
    			return true;
    	}
    	
    	return false;
    }
    static List<EventData> GetSignedUpForList() {
    	
    	return signedUpFor;
    }     
    static public EventData GetEventInSignedUpForList(String id) {
    	
    	for(EventData e : signedUpFor) {
    		if(e.getEventID().equals(id))
    			return e;
    	}
    	
    	return null;
    }    
    public void AddToEventsByDateList() {
    	
    	if( signedUpForString.contains(eventID) )
    		return;
    	
    	byDateString.add(eventID);
    	byDate.add(this);
    }
    public void AddToEventsByInterestList() {
    	
    	if( signedUpForString.contains(eventID) )
    		return;
    	
    	byInterestString.add(eventID);
    	byInterest.add(this);
    }    
    public void AddToEventsBySkillList() {
    	
    	if( signedUpForString.contains(eventID) )
    		return;
    	
    	bySkillString.add(eventID);
    	bySkill.add(this);
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
    public static EventData GetSignedUpForLocation(int Location) {
    	
    	return signedUpFor.get(Location);
    }
    public static int GetSignedUpForSize() {
    	
    	return signedUpFor.size();
    }
    public static void SetFindList(int EventAdapterKind) {
    	    	
		if(EventAdapterKind == EventAdapter.kindSignedUp){

			//copy list (elements might be deleted from signedUpFor but should not be deleted from findList)
	        findListString = new ArrayList<String>(signedUpForString);
	        findList = new ArrayList<EventData>(signedUpFor);
			return;
		}
		else if(EventAdapterKind == EventAdapter.kindFindByDate){
			
			if(doByDateQuery) {				
				doByDateQuery = false;
				
		        findListString = new ArrayList<String>();
		        findList = new ArrayList<EventData>();				

		        byDateString = new ArrayList<String>();
		        byDate = new ArrayList<EventData>();				
		        
				//do another query
		        FindFragment.status = FindFragment.statusQuery;
		        MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindByDate), "0");
				return;
			}
			else {
				doByDateQuery = true;
				
				findListString = byDateString;
				findList = byDate;
				return;
			}
		}   	
		else if(EventAdapterKind == EventAdapter.kindFindByInterest){
						
			if(doByInterestQuery) {
				doByInterestQuery = false;
			
		        findListString = new ArrayList<String>();
		        findList = new ArrayList<EventData>();
				
		        byInterestString = new ArrayList<String>();
		        byInterest = new ArrayList<EventData>();
		        
				//do another query
		        FindFragment.status = FindFragment.statusQuery;
				MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindByInterest), EventAdapter.findByInterestID);
				return;
			}
			else {
				doByInterestQuery = true;
				
				findListString = byInterestString;
				findList = byInterest;
				return;
			}
		}
		else if(EventAdapterKind == EventAdapter.kindFindBySkill){
						
			if(doBySkillQuery) {
				doBySkillQuery = false;
			
		        findListString = new ArrayList<String>();
		        findList = new ArrayList<EventData>();				
				
		        bySkillString = new ArrayList<String>();
		        bySkill = new ArrayList<EventData>();
		        
				//do another query
		        FindFragment.status = FindFragment.statusQuery;
				MySQLRequest.Create( MainActivity.current, Integer.toString(MySQLRequest.kindBySkill), EventAdapter.findBySkillID);
				return;
			}
			else {
				doBySkillQuery = true;
				
				findListString = bySkillString;
				findList = bySkill;
				return;
			}
		}
		else
			Obj.BreakPoint();
    	    	
    }
    public static EventData GetEventInFindList(String id) {
    	
    	for(EventData e : findList) {
    		if(e.getEventID().equals(id))
    			return e;
    	}
    	
    	return null;	
    }    
    public static EventData GetFindListLocation(int Location) {
    	
    	return findList.get(Location);
    }
    public static int GetFindListSize() {
    	
    	if(findList == null) return 0;
    	
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
    public static void onByDateQueryDone() {
    	
    	if(FindFragment.current != null)
    		FindFragment.current.refresh();
    }     
    public static void onByInterestQueryDone() {
    	
    	if(FindFragment.current != null)
    		FindFragment.current.refresh();
    }  
    public static void onBySkillQueryDone() {
    	
    	if(FindFragment.current != null)
    		FindFragment.current.refresh();
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
	
    public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}	
}