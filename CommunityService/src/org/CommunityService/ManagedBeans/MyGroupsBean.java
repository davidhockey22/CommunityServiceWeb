package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;

@Join(path = "/groups", to = "/Web/MyGroups.xhtml")
@ManagedBean
@RequestScoped
public class MyGroupsBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	List<GroupMember> adminGroup;
	List<GroupMember> memberGroup;

	@PostConstruct
	public void postConstructor() {
		adminGroup = new ArrayList<GroupMember>();
		memberGroup = new ArrayList<GroupMember>();

		Volunteer volunteer = currentVolunteer.getVolunteer();
		List<GroupMember> groupMembers = VolunteerService.getGroupMembersByVolunteer(volunteer);

		for (Iterator<GroupMember> iterator = groupMembers.iterator(); iterator.hasNext();) {
			GroupMember groupMember = (GroupMember) iterator.next();
			if (groupMember.getAdmin()) {
				adminGroup.add(groupMember);
			} else {
				memberGroup.add(groupMember);
			}
		}
	}

	public List<GroupMember> getAdminGroup() {
		return adminGroup;
	}

	public List<GroupMember> getMemberGroup() {
		return memberGroup;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
