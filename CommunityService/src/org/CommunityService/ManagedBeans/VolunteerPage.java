package org.CommunityService.ManagedBeans;

import java.util.List;

import javax.faces.bean.ManagedBean;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;

@ManagedBean
public class VolunteerPage {

    String volunteerEmail = "No email provided";
    List<Volunteer> allVolunteers;
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
    public List<Volunteer> getAllVolunteers() {
	this.allVolunteers = VolunteerService.getVolunteers();
        return allVolunteers;
    }
    public void setAllVolunteers(List<Volunteer> allVolunteer) {
        this.allVolunteers = allVolunteer;
    }
    public void setVolunteerEmail(String volunteerEmail) {
        this.volunteerEmail = volunteerEmail;
    }
    
    
    
}