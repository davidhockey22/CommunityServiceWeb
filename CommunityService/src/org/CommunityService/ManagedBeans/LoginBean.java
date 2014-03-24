package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.Gravatar;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.SkillService;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
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
					volunteer = VolunteerService.getVolunteerById(v.getVolunteerId(), true, true);
					return "?faces-redirect=true";
				} else {
					MessageController.addError("Login credentials didn't match.");
					return "?faces-redirect=true";
				}
			} catch (Exception e) {
				// TODO Need to decide whether to redirect to an error page or
				// just reload the current one
				e.printStackTrace();
				return "error?faces-redirect=true";
			}
		} else {
			MessageController.addInfo("User already logged in. Please log out to log in again.");
			return "?faces-redirect=true";
		}
	}

	public void attachSkills() {
		if (volunteer != null && volunteer.getVolunteerSkills() == null) {
			volunteer.setVolunteerSkills(SkillService.getVolunteerSkillsByVolunteerId(volunteer.getVolunteerId()));
		}
	}

	public void attachInterests() {
		if (volunteer != null && volunteer.getVolunteerInterests() == null) {
			volunteer.setVolunteerInterests(InterestService.getVolunteerInterestsByVolunteerId(volunteer.getVolunteerId()));
		}
	}

	public void attachOrgs() {

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
