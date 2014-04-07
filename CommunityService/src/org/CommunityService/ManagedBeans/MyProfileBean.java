package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.EntitiesMapped.VolunteerInterest;
import org.CommunityService.EntitiesMapped.VolunteerSkill;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.SkillService;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@SessionScoped
@Join(path = "/editProfile", to = "/Web/MyProfile.xhtml")
public class MyProfileBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String oldEmail = null;

	private DualListModel<String> interestModel = new DualListModel<String>();
	private DualListModel<String> skillModel = new DualListModel<String>();

	List<Interest> allInterests = new ArrayList<Interest>();
	List<Skill> allSkills = new ArrayList<Skill>();
	
	public void saveEmail() {
		oldEmail = new String(currentVolunteer.getVolunteer().getEmailAddress());
	}

	public void check() {
		if (currentVolunteer.getVolunteer() != null) {
			currentVolunteer.refreshInterests();
			currentVolunteer.refreshSkills();
			List<Interest> interests = InterestService.getInterests();
			allInterests = new ArrayList<Interest>();
			for(Interest i : interests)
				allInterests.add(i);
			Set<VolunteerInterest> vInt = currentVolunteer.getVolunteer().getVolunteerInterests();
			int x = vInt.size();
			VolunteerInterest[] temp = new VolunteerInterest[x];
			temp = vInt.toArray(temp);
			List<Interest> myInterests = new ArrayList<Interest>();
			for (int i = 0; i < temp.length; i++) {
				myInterests.add(temp[i].getInterest());
			}
			interests.removeAll(myInterests);
			
			List<String> intStr = new ArrayList<String>();
			for(Interest i : interests)
				intStr.add(i.getName() + " - " + i.getDescription());
			
			List<String> myIntStr = new ArrayList<String>();
			for(Interest i : myInterests)
				myIntStr.add(i.getName() + " - " + i.getDescription());			
			
			interestModel.setSource(intStr);
			interestModel.setTarget(myIntStr);
			
			//skills
			List<Skill> skills = null;
			try {
				skills = SkillService.getSkills();
				allSkills = new ArrayList<Skill>();
				for(Skill i : skills)
					allSkills.add(i);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Set<VolunteerSkill> vSkill = currentVolunteer.getVolunteer().getVolunteerSkills();
			int size = vSkill.size();
			VolunteerSkill[] tempArray = new VolunteerSkill[size];
			tempArray = vSkill.toArray(tempArray);
			List<Skill> mySkills = new ArrayList<Skill>();
			for (int i = 0; i < tempArray.length; i++) {
				mySkills.add(tempArray[i].getSkill());
			}
			skills.removeAll(mySkills);

			List<String> skStr = new ArrayList<String>();
			for(Skill i : skills)
				skStr.add(i.getSkillName() + " - " + i.getDescription());
			
			List<String> mySkStr = new ArrayList<String>();
			for(Skill i : mySkills)
				mySkStr.add(i.getSkillName() + " - " + i.getDescription());			
			
			skillModel.setSource(skStr);
			skillModel.setTarget(mySkStr);			
		}
	}
	
	public String updateInterestsAndSkills() {
		
		Set<VolunteerInterest> interestSet = new HashSet<VolunteerInterest>();
		String str = null;
		for(String s : interestModel.getTarget()) {
			for(Interest i : allInterests) {
				
				str = new String( i.getName() + " - " + i.getDescription() );
				
				if( s.equals( str ) ) {
					
					interestSet.add( new VolunteerInterest( i, currentVolunteer.getVolunteer() ) );
				}
			}
		}
		currentVolunteer.getVolunteer().setVolunteerInterests(interestSet);
		
		Set<VolunteerSkill> skillSet = new HashSet<VolunteerSkill>();
		for(String s : skillModel.getTarget()) {
			for(Skill i : allSkills) {
				
				str = new String( i.getSkillName() + " - " + i.getDescription() );
				
				if( s.equals( str ) ) {
					
					skillSet.add( new VolunteerSkill( i, currentVolunteer.getVolunteer() ) );
				}
			}
		}
		currentVolunteer.getVolunteer().setVolunteerSkills(skillSet);		
		
		try {
			VolunteerService.updateVolunteer(currentVolunteer.getVolunteer());
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}		
		
		return null;
	}

	public String updateProfile() {

		try {

			// if email changed, clear activation date
			if (oldEmail != null) {

				String newEmail = currentVolunteer.getVolunteer().getEmailAddress();

				if (!newEmail.equals(oldEmail)) {

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

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public DualListModel<String> getInterestModel() {
		return interestModel;
	}

	public void setInterestModel(DualListModel<String> interestModel) {
		this.interestModel = interestModel;
	}
	public DualListModel<String> getSkillModel() {
		return skillModel;
	}

	public void setSkillModel(DualListModel<String> skillModel) {
		this.skillModel = skillModel;
	}
}
