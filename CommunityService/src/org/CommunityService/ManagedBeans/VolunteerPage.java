package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;

@ManagedBean
public class VolunteerPage {

    String volunteerEmail = "No email provided";

    public String getVolunteerEmail() {
	try {
	    Volunteer v = VolunteerService.getVolunteerByName("David");
	    volunteerEmail = v.getEmailAddress();
	} catch (Exception e) {
	    e.printStackTrace();
	    volunteerEmail = "Exception thrown";
	}
	return volunteerEmail;
    }
    
    
    
}