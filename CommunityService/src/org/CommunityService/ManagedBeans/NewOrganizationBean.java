package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.EntitiesMapped.VolunteerInterest;
import org.CommunityService.EntitiesMapped.VolunteerSkill;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.OrganizationService;
import org.CommunityService.Services.SkillService;
import org.CommunityService.Services.VolunteerService;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.model.DualListModel;

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

	private DualListModel<String> interestModel = new DualListModel<String>();
	List<Interest> allInterests = new ArrayList<Interest>();	
	
	public void check() {
		if (currentVolunteer.getVolunteer() != null) {
			List<Interest> interests = InterestService.getInterests();
			allInterests = new ArrayList<Interest>();
			for(Interest i : interests)
				allInterests.add(i);
			
			List<String> intStr = new ArrayList<String>();
			for(Interest i : interests)
				intStr.add(i.getName() + " - " + i.getDescription());
						
			interestModel.setSource(intStr);
		}
	}	

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
				
				
				Set<Interest> interestSet = new HashSet<Interest>();
				String str = null;
				for(String s : interestModel.getTarget()) {
					for(Interest i : allInterests) {
						str = new String( i.getName() + " - " + i.getDescription() );
						if( s.equals( str ) ) {
							interestSet.add( i );
						}
					}
				}
				org.setInterests(interestSet);
								
				OrganizationService.addOrganization(org);
				
				//update currentVolunteer
				boolean found = false;
				for (OrganizationFollower organizationFollower : currentVolunteer.getVolunteer().getOrganizationFollowers()) {
					if (organizationFollower.getOrganization().getOrgId() == org.getOrgId()) {
						found = true;
						break;
					}
				}
				if(!found) {
					
					//if new follower is not added, redirection to EditOrganization.xhtml will crash.
					//new follower is not added automatically by hibernate
					currentVolunteer.getVolunteer().getOrganizationFollowers().add(follower);
				}

				
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
	public DualListModel<String> getInterestModel() {
		return interestModel;
	}

	public void setInterestModel(DualListModel<String> interestModel) {
		this.interestModel = interestModel;
	}

	public List<Interest> getAllInterests() {
		return allInterests;
	}

	public void setAllInterests(List<Interest> allInterests) {
		this.allInterests = allInterests;
	}
}
