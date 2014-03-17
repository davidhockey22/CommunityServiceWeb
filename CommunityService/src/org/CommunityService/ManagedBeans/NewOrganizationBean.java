package org.CommunityService.ManagedBeans;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.Services.OrganizationService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path="/createOrganization", to="/Web/NewOrganization.xhtml")
public class NewOrganizationBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;
	
	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String description;
	
	public String Register(){
		
		try {
			
			//new organization
			Organization org = new Organization(name, address, phoneNumber, email);
			org.setCreatedOn(new Date());
			org.setDescription(description);

			//add organization to mysql
			OrganizationService.addOrganization(org);
			
			//update volunteer which is now the admin of the org
//			currentVolunteer.getVolunteer().setOrganization(org);
//			VolunteerService.updateVolunteer(currentVolunteer.getVolunteer());
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
		
		return "LandingPage";
	}
	
	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
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