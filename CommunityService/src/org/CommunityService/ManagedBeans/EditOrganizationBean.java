package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.Services.OrganizationService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@ViewScoped
@Join(path = "/manageOrganization/{orgId}", to = "/Web/EditOrganization.xhtml")
public class EditOrganizationBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private String orgId;
	private Organization org = null;

	private List<Event> orgEvents = new ArrayList<Event>();
	private List<Group> orgGroups = new ArrayList<Group>();

	private List<OrganizationFollower> orgFollowers = new ArrayList<OrganizationFollower>();

	private ScheduleModel scheduleModel = new LazyScheduleModel();
	private ScheduleEvent currentEvent = new DefaultScheduleEvent("", new Date(), new Date(), null);

	public void fetchOrganization() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		// TODO: add more attached entities when they are available
		try {
			this.org = OrganizationService.getOrganizationByIdWithAttachedEntities(Integer.parseInt(orgId), "Groups",
					"OrganizationFollowers", "Events");
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
			return;
		}

		// Throw an HTTP Response Error - Page Not Found in case org cannot be found from provided id
		if (this.org == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
					"Organization with id " + orgId + " not found.");
			context.responseComplete();
			return;
		}

		// authorize
		boolean authorized = false;
		if (currentVolunteer.getVolunteer() != null) {
			for (Iterator<OrganizationFollower> iterator = currentVolunteer.getVolunteer().getOrganizationFollowers()
					.iterator(); iterator.hasNext();) {
				OrganizationFollower organizationFollower = (OrganizationFollower) iterator.next();
				if (organizationFollower.getOrganization().getOrgId() == this.org.getOrgId()) {
					authorized = (organizationFollower.getAdmin() || organizationFollower.getMod());
					break;
				}
			}
		} else {
			// Throw an HTTP Response Error - Missing Credentials in case user is not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
			context.responseComplete();
			return;
		}
		if (!authorized) {
			// Throw an HTTP Response Error - Forbidden access in case user is not allowed view
			context.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN,
					"Not authorized to view page");
			context.responseComplete();
			return;
		}

		this.orgEvents = new ArrayList<Event>(org.getEvents());
		this.orgFollowers = new ArrayList<OrganizationFollower>(org.getOrganizationFollowers());
		this.orgGroups = new ArrayList<Group>(org.getGroups());
	}

	public void onEventSelect(SelectEvent selectEvent) {
		currentEvent = (ScheduleEvent) selectEvent.getObject();
	}

	public List<Group> getOrgGroups() {
		return orgGroups;
	}

	public List<OrganizationFollower> getOrgFollowers() {
		return orgFollowers;
	}

	public List<Event> getOrgEvents() {
		return orgEvents;
	}

	public String getOrgId() {
		return orgId;
	}

	public Organization getOrg() {
		return org;
	}

	public ScheduleModel getScheduleModel() {
		return scheduleModel;
	}

	public ScheduleEvent getCurrentEvent() {
		return currentEvent;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
