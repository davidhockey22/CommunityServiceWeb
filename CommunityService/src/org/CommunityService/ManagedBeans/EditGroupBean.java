package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path="/group/{groupId}", to="/Web/EditGroup.xhtml")
public class EditGroupBean {
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;	

	List<Volunteer> members;
	List<Volunteer> mods;
	
	private Group group;
	private String groupId;
	private boolean admin = false;
	
	public void fetchGroup() {
		try {
			this.group = GroupService.getGroupById(Integer.parseInt(groupId));
			
			if(this.group != null) {
				
				mods = new ArrayList<Volunteer>();
				members = new ArrayList<Volunteer>();
				
				GroupMember eagerMember;
				for(GroupMember m : this.group.getGroupMembers()) {
					
					eagerMember = GroupService.getGroupMemberById(m.getGroupMemberId());

					if(eagerMember.getAdmin() == true) {	
						
						//if current volunteer is the admin						
						if(eagerMember.getVolunteer().getVolunteerId() == currentVolunteer.getVolunteer().getVolunteerId())						
								admin = true;
							
							continue; //skip admins
					}
				
					if(eagerMember.getMod())
						mods.add(eagerMember.getVolunteer());
					else
						members.add(eagerMember.getVolunteer());
				}
			}
			
		} catch (NumberFormatException e) {
			this.group = null;
		}
	}
	
	public void remove() {
		
		int test = 0;
		test++;
	}
	
	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}	
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public List<Volunteer> getMembers() {
		return members;
	}

	public void setMembers(List<Volunteer> members) {
		this.members = members;
	}
	public boolean getAdmin() {
		return this.admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public List<Volunteer> getMods() {
		return mods;
	}

	public void setMods(List<Volunteer> mods) {
		this.mods = mods;
	}	
}
