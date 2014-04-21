package org.CommunityService.ManagedBeans;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path = "/register", to = "/Web/NewVolunteer.xhtml")
public class NewVolunteerBean {

	private String username;
	private String password;
	private String phoneNumber;
	private String email;
	private String confirmPassword;
	private String firstName;
	private String lastName;
	private String description;

	public String init() {
		return "NewVolunteer.xhtml";
	}

	public String Register() {
		try {
			password = PasswordHash.getHash(password, email);
			Volunteer v = new Volunteer(username, password, phoneNumber, email, firstName, lastName);
			v.setSalt(email);
			v.setDescription(description);
			VolunteerService.addVolunteer(v);
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
		username = "";
		return "Login.xhtml?faces-redirect=true";
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
