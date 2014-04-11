package org.CommunityService.ManagedBeans;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/editPassword", to = "/Web/EditPassword.xhtml")
public class EditPasswordBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	String confirmPassword = new String();
	String oldPassword = new String();
	String oldPasswordError = new String("");
	String password = new String("");
	String submit = new String("");

	public void check() {
	}
	public String update() {
		
		String hashed = null;
		
		if(getOldPasswordError().equals("")){}
		else
			return null;
		
		try {
			hashed = PasswordHash.getHash(password, currentVolunteer.getVolunteer().getSalt());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		currentVolunteer.getVolunteer().setVolunteerPassword(hashed);

		VolunteerService.updateVolunteer(currentVolunteer.getVolunteer());
		
		//clear form
		confirmPassword = new String();
		oldPassword = new String();
		oldPasswordError = new String();
		password = new String();
		submit = new String("Password changed.");

		return null;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getOldPasswordError() {
		
		if(oldPassword.equals("")) //old password has not been entered
			return "";
		
		oldPasswordError = new String("");
		
		String hashed = null;
		try {
			hashed = PasswordHash.getHash(oldPassword, currentVolunteer.getVolunteer().getSalt());
			
			if( hashed.equals(currentVolunteer.getVolunteer().getVolunteerPassword() ) == false )
				oldPasswordError = new String("Old Password is wrong.");
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return oldPasswordError;
	}
	public void setOldPasswordError(String oldPasswordError) {
		this.oldPasswordError = oldPasswordError;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}	
}
