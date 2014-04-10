package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.Services.OrganizationService;
import org.hibernate.HibernateException;

@ManagedBean
@SessionScoped
public class EditOrganizationBean {

	boolean renderError;
	boolean renderPage;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private Organization currentOrg = null;

	private String oldEmail = null;

	public void init() {

		renderError = true;
		renderPage = false;

		if (currentVolunteer.getVolunteer() == null) {
			return;
		}

		//do query
		//currentOrg = OrganizationService.getOrganizationByVolunteerId(currentVolunteer.getVolunteer().getVolunteerId());
		
		if(currentOrg == null)
			return;

		renderError = false;
		renderPage = true;
	}

	public void saveEmail() {

		oldEmail = new String(currentVolunteer.getVolunteer().getEmailAddress());
	}

	public String updateProfile() {

		try {

			// if email changed, clear activation date
			if (oldEmail != null) {

				String newEmail = currentVolunteer.getVolunteer().getEmailAddress();

				if (!newEmail.equals(oldEmail)) {

					// there is no activation date for organization
				}
			}

			// test
			OrganizationService.updateOrganization(currentOrg);

		} catch (HibernateException e) {
			e.printStackTrace();
			return "Error";
		}

		return null;
	}

	public boolean isRenderError() {
		return renderError;
	}

	public void setRenderError(boolean renderError) {
		this.renderError = renderError;
	}

	public boolean isRenderPage() {
		return renderPage;
	}

	public void setRenderPage(boolean renderPage) {
		this.renderPage = renderPage;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public Organization getCurrentOrg() {
		return currentOrg;
	}

	public void setCurrentOrg(Organization currentOrg) {
		this.currentOrg = currentOrg;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
}
