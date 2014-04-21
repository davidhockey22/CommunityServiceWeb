package tut1.login;

import java.util.ArrayList;
import java.util.List;

public class OrgData extends Obj{
		
    public static List<OrgData> all = new ArrayList<OrgData>();

	private String orgID;
	private String name;

    public OrgData() {
    }

    public void init(String ID, String Name) {
    	
    	orgID = ID;
    	name = Name;
    	
    	boolean found = false;
    	for(OrgData o : all) {
    		
    		if(o.getOrgID().equals(ID)) {
    			found = true;
    			break;
    		}
    	}
    	
    	if(!found)
    		all.add(this);
    }
    
	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}    
}