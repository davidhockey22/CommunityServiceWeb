package org.CommunityService.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.Services.OrganizationService;

@ManagedBean
@RequestScoped
public class OrganizationPage {
	private Organization organization;
	private String orgId;
	
	public void fetchOrganization() {
		try {
			this.organization = OrganizationService.getOrganizationById(Integer.parseInt(orgId));
		} catch (NumberFormatException e) {
			this.organization = null;
		}
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Organization getOrganization() {
		return organization;
	}
}
