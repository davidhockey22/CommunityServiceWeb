package org.CommunityService.ManagedBeans;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.OrgGroup;
import org.CommunityService.EntitiesMapped.OrgMod;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.OrganizationService;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.Validators.PasswordHash;

@ManagedBean
@SessionScoped
public class RegisterOrgBean {

	@ManagedProperty(value = "#{currentVolunteerBean}")
	private CurrentVolunteerBean currentVolunteer;
	
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String description;
	
	public String Register(){
		
		try {
			
			//new organization
			Organization org = new Organization(name, address, phoneNumber, email);
			org.setDescription(description);
			
			//new moderator
			OrgMod mod = new OrgMod(currentVolunteer.getVolunteer().getVolunteerId(), org, currentVolunteer.getVolunteer());
			
			Set<OrgMod> set = new HashSet<OrgMod>();
			set.add(mod);
			
			org.setOrgMods(set); //set organization moderator
			
			//add organization to mysql
			OrganizationService.addOrganization(org);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		
		return "LandingPage";
	}
	
	public CurrentVolunteerBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(CurrentVolunteerBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
