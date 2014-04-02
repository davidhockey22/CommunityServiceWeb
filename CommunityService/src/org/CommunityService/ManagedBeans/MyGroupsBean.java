package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.util.MembershipLevel;
import org.ocpsoft.rewrite.annotation.Join;

@Join(path = "/groups", to = "/Web/MyGroups.xhtml")
@ManagedBean
@RequestScoped
public class MyGroupsBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;
	
	private List<MembershipLevel<GroupMember>> levels = new ArrayList<MembershipLevel<GroupMember>>();
	
	@PostConstruct
	public void postConstructor() {
		List<GroupMember> adminGroup = new ArrayList<GroupMember>();
		List<GroupMember> moderatorGroup = new ArrayList<GroupMember>();
		List<GroupMember> memberGroup = new ArrayList<GroupMember>();

		Volunteer volunteer = currentVolunteer.getVolunteer();
		currentVolunteer.refreshGroups();
		Set<GroupMember> groupMembers = volunteer.getGroupMembers(); 

		for (Iterator<GroupMember> iterator = groupMembers.iterator(); iterator.hasNext();) {
			GroupMember groupMember = (GroupMember) iterator.next();
			if (groupMember.getAdmin()) {
				adminGroup.add(groupMember);
			} else if (groupMember.getMod()){
				moderatorGroup.add(groupMember);
			} else {
				memberGroup.add(groupMember);
			}
		}
		
		levels = new ArrayList<MembershipLevel<GroupMember>>();
		levels.add(new MembershipLevel<GroupMember>("Administrator", adminGroup));
		levels.add(new MembershipLevel<GroupMember>("Moderator", moderatorGroup));
		levels.add(new MembershipLevel<GroupMember>("Member", memberGroup));
	}
	
	public List<MembershipLevel<GroupMember>> getLevels() {
		return levels;
	}
	
	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
