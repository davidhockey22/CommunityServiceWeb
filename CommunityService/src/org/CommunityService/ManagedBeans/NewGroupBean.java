package org.CommunityService.ManagedBeans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.Services.GroupService;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path = "/createGroup", to = "/Web/NewGroup.xhtml")
public class NewGroupBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String name;
	
	public String Register() {
		try {
			Group group = GroupService.getGroupByName(name);
			if (group == null) {
				group = new Group(name);
				group.setCreationDate(new Date());
				group.setAvgRatingOfVolunteers(0.0f);
				group.setHoursWorked(0);
				group.setPoints(0.0f);
				Set<GroupMember> members = new HashSet<GroupMember>();
				GroupMember founder = new GroupMember(group,
						currentVolunteer.getVolunteer(), new Date(), true, true);
				founder.setApproved(true);
				members.add(founder);
				group.setGroupMembers(members);
				GroupService.addGroup(group);
				
				//if new groupmember is not added, redirection to EditGroup.xhtml will crash.
				currentVolunteer.getVolunteer().getGroupMembers().add(founder);				
				
				return "/EditGroup.xhtml?faces-redirect=true&groupId=" + group.getGroupId();
			} else {
				MessageController.addInfo("Group already exists, please choose a different name.");
				return "";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return "Error.xhtml?faces-redirect=true";
		}
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
