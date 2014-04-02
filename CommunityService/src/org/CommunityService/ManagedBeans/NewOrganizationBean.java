package org.CommunityService.ManagedBeans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.Services.OrganizationService;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@SessionScoped
@Join(path = "/createOrganization", to = "/Web/NewOrganization.xhtml")
public class NewOrganizationBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String name;
	private String address;
	private String phoneNumber;
	private String email;
	private String description;

	public String Register() {
		try {
			boolean orgExists = false;
			if (OrganizationService.getOrganizationByName(name) != null) {
				MessageController.addInfo("An organization with this name already exists, please enter a different name.");
				orgExists = true;
			}
			if (OrganizationService.getOrganizationByAddress(address) != null) {
				MessageController.addInfo("An organization with this address already exists, please enter a different address.");
				orgExists = true;
			}
			if (!orgExists) {
				Organization org = new Organization(name, address, phoneNumber, email);
				org.setCreatedOn(new Date());
				org.setDescription(description);
				
				Set<OrganizationFollower> orgFollowers = new HashSet<OrganizationFollower>();
				OrganizationFollower follower = new OrganizationFollower(org, currentVolunteer.getVolunteer(), true, true);
				orgFollowers.add(follower);
				
				org.setOrganizationFollowers(orgFollowers);
				
				OrganizationService.addOrganization(org);
				return "/EditOrganization.xhtml?faces-redirect=true&orgId=" + org.getOrgId();
			} else {
				return "";
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			return "Error.xhtml?faces-redirect=true";
		}
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
