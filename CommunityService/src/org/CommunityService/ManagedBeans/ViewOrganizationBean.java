package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.Services.OrganizationService;
import org.CommunityService.util.Gravatar;
import org.CommunityService.util.MemberLevel;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/organization/{orgId}", to = "/Web/ViewOrganization.xhtml")
public class ViewOrganizationBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String orgId;

	private Organization organization = null;

	private List<MemberLevel<OrganizationFollower>> levels = new ArrayList<MemberLevel<OrganizationFollower>>();

	private String isMember = null;
	private String admin = null;
	private String following = null;

	public String follow() {
		OrganizationService.addFollower(currentVolunteer.getVolunteer(), organization);
		return "?faces-redirect=true";

	}

	public String unFollow() {

		return "?faces-redirect=true";
	}

	public void fetchOrganization() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.organization = OrganizationService.getOrganizationByIdWithAttachedEntities(Integer.parseInt(this.orgId), "OrganizationFollowers", "Groups");
			List<OrganizationFollower> admins = new ArrayList<OrganizationFollower>();
			List<OrganizationFollower> moderators = new ArrayList<OrganizationFollower>();
			List<OrganizationFollower> members = new ArrayList<OrganizationFollower>();

			for (Iterator<OrganizationFollower> iterator = this.organization.getOrganizationFollowers().iterator(); iterator.hasNext();) {
				OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
				if (organizationFollower.getAdmin()) {
					admins.add(organizationFollower);
				} else if (organizationFollower.getMod()) {
					moderators.add(organizationFollower);
				} else {
					members.add(organizationFollower);
				}
			}

			// add the collections in the order in which they are to be
			// displayed
			levels.add(new MemberLevel<OrganizationFollower>("Administrators", admins));
			levels.add(new MemberLevel<OrganizationFollower>("Members", moderators));
			levels.add(new MemberLevel<OrganizationFollower>("Followers", members));
			if (organization == null) {
				// Throw an HTTP Response Error - Page Not Found in case org
				// cannot be found from provided id
				context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Org with id " + orgId + " does not exist");
				context.responseComplete();
			}
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid
			// orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
		}
	}

	public String getGravatarURL() {
		String email = (this.getOrganization() != null ? this.getOrganization().getEmailAddress() : null);
		return Gravatar.getGravatarImage(email);
	}

	public boolean isAdmin() {
		if (admin != null) {
			if (admin.equals("true")) {
				return true;
			} else {
				return false;
			}
		}
		if (currentVolunteer.getVolunteer() != null) {
			for (Iterator<OrganizationFollower> iterator = currentVolunteer.getVolunteer().getOrganizationFollowers().iterator(); iterator.hasNext();) {
				OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
				if (organizationFollower.getOrganization().getOrgId() == this.organization.getOrgId() && organizationFollower.getAdmin()) {
					admin = "true";
					return true;
				}
			}
		}
		admin = "false";
		return false;

	}

	public boolean isMember() {
		if (isMember != null) {
			if (isMember.equals("true")) {
				return true;
			} else {
				return false;
			}
		}
		if (currentVolunteer.getVolunteer() != null) {
			for (Iterator<OrganizationFollower> iterator = currentVolunteer.getVolunteer().getOrganizationFollowers().iterator(); iterator.hasNext();) {
				OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
				if (organizationFollower.getOrganization().getOrgId() == this.organization.getOrgId() && organizationFollower.getMod()) {
					isMember = "true";
					return true;
				}
			}
		}
		isMember = "false";
		return false;

	}

	public boolean isFollowing() {
		if (following != null) {
			if (following.equals("true")) {
				return true;
			} else {
				return false;
			}
		}
		if (currentVolunteer.getVolunteer() != null) {
			for (Iterator<OrganizationFollower> iterator = currentVolunteer.getVolunteer().getOrganizationFollowers().iterator(); iterator.hasNext();) {
				OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
				if (organizationFollower.getOrganization().getOrgId() == this.organization.getOrgId()) {
					following = "true";
					return true;
				}
			}
		}
		following = "false";
		return false;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgId() {
		return orgId;
	}

	public Organization getOrganization() {
		return organization;
	}

	public List<MemberLevel<OrganizationFollower>> getLevels() {
		return levels;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}