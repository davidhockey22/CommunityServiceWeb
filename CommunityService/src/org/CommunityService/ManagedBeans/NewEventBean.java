package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.Services.EventService;
import org.CommunityService.Services.OrganizationService;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/createEvent", to = "/Web/NewEvent.xhtml")
public class NewEventBean {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	// view parameters
	private String orgId = null;
	private String eventId = null;

	// SelectOneMenu values
	private String selectedOrgId = null;
	private Map<String, String> organiztions = null;

	// used to create new event
	private String eventname;
	private String description;
	private String location;
	private Date begintime = new Date();
	private Date endtime = new Date();
	// TODO need to set up patterns
	private int recurrance;
	// TODO Should be an admin only privilege;
	private float hoursBonus = (float) 1.00;

	/**
	 * Checks if the URL is valid and user is authorized to create an event. Initializes local variables on success.
	 * Sends an HTTP Error code and returns on failure. Should be called only when generating the view for the first
	 * time.
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		// check if user is authorized to create an event
		if (!currentVolunteer.isLoggedIn()) {
			// Throw an HTTP Response Error - Missing Credentials in case user is not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
			context.responseComplete();
			return;
		}

		Integer orgId;
		try {
			orgId = Integer.parseInt(this.orgId);
			this.currentVolunteer.refreshOrganizations();
			if (!currentVolunteer.isMemberOfOrganizatoin(orgId)) {
				// Throw an HTTP Response Error - Forbidden access in case user is not logged in
				context.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN,
						"Not authorized to view page");
				context.responseComplete();
				return;
			}
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
			return;
		}

		// if eventId viewParam present, assume new event was created
		if (eventId != null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
					"Event created, click the \"View Event\" button below the form to view it now"));
		}

		setSelectedOrgId(this.orgId);
		// get cached organizations from currentVolunteer
		organiztions = new TreeMap<String, String>();
		for (OrganizationFollower organizationFollower : this.currentVolunteer.getVolunteer()
				.getOrganizationFollowers()) {
			if (organizationFollower.getAdmin() || organizationFollower.getMod()) {
				organiztions.put(organizationFollower.getOrganization().getOrgName(), organizationFollower
						.getOrganization().getOrgId().toString());
			}
		}
	}

	public String Create() throws HibernateException {
		// last check that user has permission, as organizationfollower info could be stale
		OrganizationFollower orgF = OrganizationService.getOrganizationFollowerBy(Integer.parseInt(this.selectedOrgId),
				this.currentVolunteer.getVolunteer().getVolunteerId());

		// create the event if member has permissions
		if (orgF.getAdmin() || orgF.getMod()) {
			Event event = new Event(eventname, begintime, endtime);
			event.setDescription(description);
			event.setLocation(location);
			event.setOrganization(orgF.getOrganization());
			EventService.addEvent(event);
			this.eventId = event.getEventId().toString();
		}

		// redirect to same view either way, as the view recreation will trigger an orgfollower data refresh for the
		// logged in user
		return "/NewEvent.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSelectedOrgId() {
		return selectedOrgId;
	}

	public void setSelectedOrgId(String selectedOrgId) {
		this.selectedOrgId = selectedOrgId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public Map<String, String> getOrganizations() {
		return organiztions;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public int getRecurrance() {
		return recurrance;
	}

	public void setRecurrance(int recurrance) {
		this.recurrance = recurrance;
	}

	public float getHoursBonus() {
		return hoursBonus;
	}

	public void setHoursBonus(float hoursBonus) {
		this.hoursBonus = hoursBonus;
	}
}
