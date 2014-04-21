package org.CommunityService.ManagedBeans;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Notification;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.NotificationService;
import org.CommunityService.Services.SkillService;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;
import org.CommunityService.util.Gravatar;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean(name = "loginBean")
@SessionScoped
@Join(path = "/login", to = "/Web/Login.xhtml")
public class LoginBean {
	private Volunteer volunteer;

	public boolean isLoggedIn() {
		return volunteer != null;
	}

	private String username;
	private String password;

	public String Login() {
		if (volunteer == null) {
			try {
				Volunteer v = VolunteerService.getVolunteerByName(username);

				if (v != null && v.getVolunteerPassword().equals(PasswordHash.getHash(password, v.getSalt()))) {
					volunteer = v;
					this.refreshAllData();
					return "?faces-redirect=true";
				} else {
					MessageController.addError("Login credentials didn't match.");
					return "?faces-redirect=true";
				}
			} catch (HibernateException e) {
				e.printStackTrace();
				return "Error.xhtml?faces-redirect=true";
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return "Error.xhtml?faces-redirect=true";
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				return "Error.xhtml?faces-redirect=true";
			}

		} else {
			MessageController.addInfo("User already logged in. Please log out to log in again.");
			return "?faces-redirect=true";
		}
	}

	public void attachSkills() {
		if (volunteer != null && volunteer.getVolunteerSkills() == null) {
			this.refreshSkills();
		}
	}

	public void refreshSkills() {
		if (volunteer != null) {
			volunteer.setVolunteerSkills(SkillService.getVolunteerSkillsByVolunteerId(volunteer.getVolunteerId()));
		}
	}

	public void attachInterests() {
		if (volunteer != null && volunteer.getVolunteerInterests() == null) {
			this.refreshInterests();
		}
	}

	public void refreshInterests() {
		if (volunteer != null) {
			volunteer.setVolunteerInterests(InterestService.getVolunteerInterestsByVolunteerId(volunteer.getVolunteerId()));
		}
	}

	public void attachGroups() {
		if (volunteer != null && volunteer.getGroupMembers() == null) {
			this.refreshGroups();
		}
	}

	public void refreshGroups() {
		if (volunteer != null) {
			volunteer.setGroupMembers(new HashSet<GroupMember>(VolunteerService.getGroupMembersByVolunteer(volunteer)));
		}
	}

	public void attachOrganizations() {
		if (volunteer != null && volunteer.getOrganizationFollowers() == null) {
			this.refreshOrganizations();
		}
	}

	public void refreshOrganizations() {
		if (volunteer != null) {
			volunteer.setOrganizationFollowers(new HashSet<OrganizationFollower>(VolunteerService.getOrganizationFollowersByVolunteer(volunteer)));
		}
	}

	public void refreshNotifications() {
		if (volunteer != null) {
			volunteer.setNotifications(new HashSet<Notification>(NotificationService.getUserNotifications(volunteer)));
		}
	}

	public void refreshAssignedEvents(Organization org) {
		if (volunteer != null) {
			volunteer.setEvents(new HashSet<Event>(VolunteerService.getEventsByVolunteer(volunteer, org)));
		}
	}

	public void refreshAllData() {
		volunteer = VolunteerService.getVolunteerByIdWithAttachedEntities(volunteer.getVolunteerId(), "GroupMembers", "VolunteerInterests",
				"OrganizationFollowers", "VolunteerSkills", "EventVolunteers", "Notifications");
	}

	public void attachEvents() {
		if (volunteer != null && volunteer.getEventVolunteers() == null) {
			this.refreshEvents();
		}
	}

	public void refreshEvents() {
		if (volunteer != null) {
			volunteer.setEventVolunteers(new HashSet<EventVolunteer>(VolunteerService.getEventVolunteersByVolunteer(volunteer)));
		}
	}

	/**
	 * @param org
	 * @return up-to-date status of a Volunteer in a given Organization, or
	 *         false if orgId is null
	 */
	public boolean isMemberOfOrganization(Organization org) {
		return org == null ? false : isMemberOfOrganizatoin(org.getOrgId());
	}

	/**
	 * @param orgId
	 * @return cached status of a Volunteer in a given Organization, or false if
	 *         orgId is null
	 */
	public boolean isMemberOfOrganizatoin(Integer orgId) {
		if (orgId == null)
			return false;
		for (Iterator<OrganizationFollower> iterator = this.volunteer.getOrganizationFollowers().iterator(); iterator.hasNext();) {
			OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
			if (organizationFollower.getOrganization().getOrgId() == orgId) {
				return organizationFollower.getAdmin() || organizationFollower.getMod();
			}
		}
		return false;
	}

	public String Logout() {
		// invalidate current session and redirect to clear POST
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "?faces-redirect=true";
	}

	// Getters and Setters
	// ---------------------------------------------------------------------------------------------------

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGravatarURL() {
		String email = (this.getVolunteer() != null ? this.getVolunteer().getEmailAddress() : null);
		return Gravatar.getGravatarImage(email);
	}

	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteer(Volunteer volunteer) {
		this.volunteer = volunteer;
	}
}
