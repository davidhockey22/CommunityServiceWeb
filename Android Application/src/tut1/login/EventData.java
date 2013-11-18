package tut1.login;

import java.util.Date;

public class EventData {
	
	public static final int childDataTotal = 3; 
	
    public String eventName;
    public String description;
    public String location;
    public Date beginTime;
    public Date endTime;

    public EventData() {
    }

    public EventData(String EventName, String Description, String Location, Date BeginTime, Date EndTime) {
    	
    	eventName = EventName;
    	description = Description;
    	location = Location;
    	beginTime = BeginTime;
    	endTime = EndTime;
    }
}