package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path="/group/{groupId}", to="/Web/ViewGroup.xhtml")
public class ViewGroupBean {

	List<Volunteer> admins;
	List<Volunteer> members;
	
	private Group group;
	private String groupId;
	
	public void fetchGroup() {
		try {
			this.group = GroupService.getGroupById(Integer.parseInt(groupId));
			
			if(this.group != null) {
				
				admins = new ArrayList<Volunteer>();
				members = new ArrayList<Volunteer>();
				
				GroupMember eagerMember;
				for(GroupMember m : this.group.getGroupMembers()) {
					
					eagerMember = GroupService.getGroupMemberById(m.getGroupMemberId());
					
					if(eagerMember.getAdmin() || eagerMember.getMod())
						admins.add(eagerMember.getVolunteer());
					else
						members.add(eagerMember.getVolunteer());
				}
			}
			
		} catch (NumberFormatException e) {
			this.group = null;
		}
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
	
	public List<Volunteer> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Volunteer> admins) {
		this.admins = admins;
	}	
}
