package tut1.login;

public class EventInterestData extends Obj{
	
	private String eventInterestID;
	private String eventID;
	private String interestID;

    public EventInterestData() {
    }

    public void init(String ID, String EventID, String InterestID) {
    	
    	eventInterestID = ID;
    	eventID = EventID;
    	interestID = InterestID;
    }
    boolean initFromMySQLString(String S) {
    	
    	init(null, null, null);
    	
    	return Obj.parseMySQLObj(this, S);
    }
    void OnParseMySQLData(String Data, int Index){
    	
    	if(Index == 0) eventInterestID = Data;
    	else if(Index == 1) eventID = Data;
    	else if(Index == 2) {
    		
    		interestID = Data;
    		
    		//put interestID in eventData.interestIDList
    		EventData.AddToInterestList(eventID, interestID);
    	}
    	else Obj.BreakPoint();
    }
    
	public String getEventInterestID() {
		return eventInterestID;
	}

	public void setEventInterestID(String eventInterestID) {
		this.eventInterestID = eventInterestID;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getInterestID() {
		return interestID;
	}

	public void setInterestID(String interestID) {
		this.interestID = interestID;
	}    
}