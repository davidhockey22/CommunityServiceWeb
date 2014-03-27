package org.CommunityService.ManagedBeans;

import hibernate.HibernateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.Services.GroupService;
import org.CommunityService.Services.VolunteerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
			
			Group group = new Group(name);
			group.setCreationDate(new Date());
			group.setAvgRatingOfVolunteers(0.0f);
			group.setHoursWorked(0);
			group.setPoints(0.0f);
			Set<GroupMember> members = new HashSet<GroupMember>();
			GroupMember founder = new GroupMember();
			founder.setAdmin(true);
			founder.setGroup(group);
			founder.setJoinedDate(new Date());
			founder.setMod(true);
			founder.setVolunteer(VolunteerService.getVolunteerById(currentVolunteer.getVolunteer().getVolunteerId()));
			members.add(founder);
			group.setGroupMembers(members);
			
			GroupService.addGroup(group);
			
			/*
			Group group = new Group(name);
			
			GroupMember founding = new GroupMember(group, currentVolunteer.getVolunteer(), new Date(), true, true);
			group.getGroupMembers().add(founding);					
			
			currentVolunteer.getVolunteer().getGroupMembers().add(founding);
			VolunteerService.updateVolunteer(currentVolunteer.getVolunteer()); //hibernate bad grammar error

			GroupService.addGroup(group); //hibernate detached entity error: currentVolunteer.getVolunteer()
			*/

		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}

		return "LandingPage";
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
