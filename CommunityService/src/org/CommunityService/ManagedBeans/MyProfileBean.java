package org.CommunityService.ManagedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.VolunteerInterest;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.model.DualListModel;

@ManagedBean
@SessionScoped
@Join(path = "/editProfile", to = "/Web/MyProfile.xhtml")
public class MyProfileBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String oldEmail = null;

	private DualListModel<Interest> interestModel = new DualListModel<Interest>();

	public void saveEmail() {
		oldEmail = new String(currentVolunteer.getVolunteer().getEmailAddress());
	}

	public void check() {
		if (currentVolunteer.getVolunteer() != null) {
			currentVolunteer.refreshInterests();
			currentVolunteer.refreshSkills();
			List<Interest> interests = InterestService.getInterests();
			Set<VolunteerInterest> vInt = currentVolunteer.getVolunteer().getVolunteerInterests();
			int x = vInt.size();
			VolunteerInterest[] temp = new VolunteerInterest[x];
			temp = vInt.toArray(temp);
			List<Interest> myInterests = new ArrayList<Interest>();
			for (int i = 0; i < temp.length; i++) {
				myInterests.add(temp[i].getInterest());
			}
			interests.removeAll(myInterests);
			interestModel.setSource(interests);
			interestModel.setTarget(myInterests);
		}
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

	public DualListModel<Interest> getInterestModel() {
		return interestModel;
	}

	public void setInterestModel(DualListModel<Interest> interestModel) {
		this.interestModel = interestModel;
	}
}
