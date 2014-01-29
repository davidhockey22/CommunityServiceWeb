package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;

@ManagedBean
@RequestScoped
public class LoginBean {

	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;
	
	private String username;
	private String password;

	public String Login() {
		if (currentVolunteer.getVolunteer() == null) {
			try {
				Volunteer v = VolunteerService.getVolunteerByName(username);
				if (v != null && v.getVolunteerPassword().equals(password)) {
					currentVolunteer.setVolunteer(v);
					return "?faces-redirect=true";
				} else {
					MessageController.addError("Login credentials didn't match.");
					return "?faces-redirect=true";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error?faces-redirect=true";
			}
		} else {
			MessageController.addInfo("User already logged in.");
			return "?faces-redirect=true";
		}
	}
	
	public String Logout() {
		//invalidate current session and redirect to clear POST data
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "?faces-redirect=true";
	}

	// Getters and Setters
	// ---------------------------------------------------------------------------------------------------
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CurrentVolunteerBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(CurrentVolunteerBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

}
