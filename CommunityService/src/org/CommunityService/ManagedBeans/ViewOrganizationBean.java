package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.Services.OrganizationService;
import org.CommunityService.util.Gravatar;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path="/organization/{orgId}", to="/Web/ViewOrganization.xhtml")
public class ViewOrganizationBean {
	private Organization organization;
	private String orgId;
	
	public void fetchOrganization() {
		try {
			this.organization = OrganizationService.getOrganizationById(Integer.parseInt(orgId));
		} catch (NumberFormatException e) {
			this.organization = null;
		}
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getGravatarURL() {
		String email = (this.getOrganization() != null ? this.getOrganization().getEmailAddress() : null);
		return Gravatar.getGravatarImage(email);
	}

	public Organization getOrganization() {
		return organization;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
