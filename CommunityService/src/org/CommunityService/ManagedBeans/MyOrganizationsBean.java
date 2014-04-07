package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.util.MemberLevel;
import org.ocpsoft.rewrite.annotation.Join;

@Join(path = "/organizations", to = "/Web/MyOrganizations.xhtml")
@ManagedBean
@RequestScoped
public class MyOrganizationsBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private List<MemberLevel<OrganizationFollower>> levels;
	
	@PostConstruct
	public void postConstructor() {
		List<OrganizationFollower> adminOrgs = new ArrayList<OrganizationFollower>();
		List<OrganizationFollower> memberOrgs = new ArrayList<OrganizationFollower>();
		List<OrganizationFollower> followerOrgs = new ArrayList<OrganizationFollower>();

		currentVolunteer.refreshOrganizations();
		Volunteer volunteer = currentVolunteer.getVolunteer();
		Set<OrganizationFollower> organizationFollowers = volunteer.getOrganizationFollowers();
		
		for (Iterator<OrganizationFollower> iterator = organizationFollowers.iterator(); iterator.hasNext();) {
			OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
			if (organizationFollower.getAdmin()) {
				adminOrgs.add(organizationFollower);
			} else if (organizationFollower.getMod()) {
				memberOrgs.add(organizationFollower);
			} else {
				followerOrgs.add(organizationFollower);
			}
		}
		
		levels = new ArrayList<MemberLevel<OrganizationFollower>>();
		levels.add(new MemberLevel<OrganizationFollower>("Administrator", adminOrgs));
		levels.add(new MemberLevel<OrganizationFollower>("Member", memberOrgs));
		levels.add(new MemberLevel<OrganizationFollower>("Follower", followerOrgs));
	}
	
	public List<MemberLevel<OrganizationFollower>> getLevels() {
		return levels;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
