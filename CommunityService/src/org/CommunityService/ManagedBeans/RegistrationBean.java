package org.CommunityService.ManagedBeans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path="/register", to="/Web/NewVolunteer.xhtml")
public class RegistrationBean {

	private String username;
	private String password;
	private String phoneNumber;
	private String email;
	private String confirmPassword;

	public String Register() {
		try {
			password = PasswordHash.getHash(password, email);
			Volunteer v = new Volunteer(username, password, phoneNumber, email, new Date(), 0, 0, username, username);
			VolunteerService.addVolunteer(v);
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		return "Home";
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
