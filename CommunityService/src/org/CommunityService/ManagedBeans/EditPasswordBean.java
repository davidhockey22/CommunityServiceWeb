package org.CommunityService.ManagedBeans;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path = "/editPassword", to = "/Web/EditPassword.xhtml")
public class EditPasswordBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	String oldPassword = new String(), password = new String();
	String oldPasswordError = new String("");

	public void check() {
	}
	public String update() {
		
		String hashed = null;
		
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
}
