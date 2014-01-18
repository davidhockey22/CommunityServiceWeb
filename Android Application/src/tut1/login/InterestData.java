package tut1.login;

import java.util.ArrayList;
import java.util.List;

public class InterestData extends Obj{

	public static final int statusNotLoaded = 0, statusLoading = 1, statusLoaded = 2;
	public static int status = statusNotLoaded;
		
    private static List<String> allString = new ArrayList<String>();
    private static List<InterestData> all = new ArrayList<InterestData>();
	
	private String interestID;
	private String name;
	private String description;

    public InterestData() {
    }

    public void init(String ID, String Name, String Description) {
    	
    	interestID = ID;
    	name = Name;
    	description = Description;
    }
    boolean initFromMySQLString(String S) {
    	
    	init(null, null, null);
    	
    	return Obj.parseMySQLObj(this, S);
    }
    void OnParseMySQLData(String Data, int Index){
    	
    	if(Index == 0) interestID = Data;
    	else if(Index == 1) name = Data;
    	else if(Index == 2) description = Data;
    	else Obj.BreakPoint();
    }
    
    public void AddToAllList() {
    	
    	if( allString.contains(interestID) )
    		return;
    	
    	all.add(this);
    	allString.add(interestID);   	
    } 
    public static InterestData GetListLocation(int Location) {
    	
    	return all.get(Location);
    }
    public static int GetListSize() {
    	
    	return all.size();
    }    
	public String getInterestID() {
		return interestID;
	}

	public void setInterestID(String interestID) {
		this.interestID = interestID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}    
}