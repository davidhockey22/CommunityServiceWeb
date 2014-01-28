package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.Services.VolunteerService;

@ManagedBean
@SessionScoped
public class ProfileBean {

	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;

	private String oldEmail = null;
	
	public void saveEmail() {
		
		oldEmail = new String( currentVolunteer.getVolunteer().getEmailAddress() );
	}
	
	public String updateProfile(){
		
		try {
			
			//if email changed, clear activation date
			if( oldEmail != null ) {
												
				String newEmail = currentVolunteer.getVolunteer().getEmailAddress();
				
				if(!newEmail.equals(oldEmail)) {
					
					currentVolunteer.getVolunteer().setActivationDate(null);
				}
			}
			
			VolunteerService.updateVolunteer(currentVolunteer.getVolunteer());
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		
		return null;
	}

	public CurrentVolunteerBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(CurrentVolunteerBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
	
	public String getOldEmail() {
		return oldEmail;
	}
	
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
}
