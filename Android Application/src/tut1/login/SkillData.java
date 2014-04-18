package tut1.login;

import java.util.ArrayList;
import java.util.List;

public class SkillData extends Obj{
		
    private static List<String> allString = new ArrayList<String>();
    private static List<SkillData> all = new ArrayList<SkillData>();
	
	private String skillID;
	private String name;
	private String description;

    public SkillData() {
    }

    public void init(String ID, String Name, String Description) {
    	
    	skillID = ID;
    	name = Name;
    	description = Description;
    }    
    public void AddToAllList() {
    	
    	if( allString.contains(skillID) )
    		return;
    	
    	all.add(this);
    	allString.add(skillID);   	
    } 
    public static SkillData GetListLocation(int Location) {
    	
    	return all.get(Location);
    }
    public static int GetListSize() {
    	
    	return all.size();
    }    
	public String getSkillID() {
		return skillID;
	}

	public void setSkillID(String interestID) {
		this.skillID = interestID;
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