package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.Services.OrganizationService;
import org.CommunityService.util.CalculatedEvent;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@ManagedBean
@ViewScoped
@Join(path = "/manageOrganization/{orgId}", to = "/Web/EditOrganization.xhtml")
public class EditOrganizationBean {
	// logged in volunteer information
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;
	private OrganizationFollower currentOrganizationFollower;

	// passed from view parameter
	private String orgIdViewParameter;

	// initially set for view creation in fetchOrganization()
	private Organization org = null;

	// index for main tab set
	private String tabIndex;

	// used for Manage Events tab
	private String eventTabIndex;
	private List<CalculatedEvent> selectedEvents;

	// used for Manage Members tab
	private List<OrganizationFollower> orgFollowers;

	// used for Manage Groups tab
	private List<Group> orgGroups;

	// used for Schedule tab
	private ScheduleModel scheduleModel;
	// event selected by clicking on event in calendar
	private ScheduleEvent selectedScheduleEvent = new DefaultScheduleEvent("", new Date(), new Date(), null);

	public void initialize() throws IOException {
		//
		this.refreshOrganization();

		// set up a LazyScheduleModel to fetch the events in the date range being viewed
		scheduleModel = new LazyScheduleModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void loadEvents(Date start, Date end) {
				OrganizationService.refreshOrganizationEventsBetweenDates(org, start, end);
				for (Event event : org.getEvents()) {
					DefaultScheduleEvent scheduleEvent = new DefaultScheduleEvent();
					scheduleEvent.setData(event);
					scheduleEvent.setStartDate(event.getBeginTime());
					scheduleEvent.setEndDate(event.getEndTime());
					scheduleEvent.setTitle(event.getEventName().replaceAll("'", "\\'"));
					scheduleEvent.setAllDay(false);
					scheduleEvent.setEditable(false);
					this.addEvent(scheduleEvent);
				}
			}
		};
	}

	public String updateOrganization() {
		OrganizationService.updateOrganization(org);
		return null;
	}

	public void fetchMyEvents() {
		OrganizationService.refreshOrganizationEventsForFollower(org, currentVolunteer.getVolunteer());
	}

	public void fetchPastEvents() {
		// Creating "Midnight this morning" date object for event management
		Calendar now = (new GregorianCalendar());
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date today = now.getTime();
		OrganizationService.refreshOrganizationEventsBeforeOrAfterDate(org, today, true);
	}

	public void fetchFutureEvents() {
		// Creating "Midnight this morning" date object for event management
		Calendar now = (new GregorianCalendar());
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);

		Date today = now.getTime();
		OrganizationService.refreshOrganizationEventsBeforeOrAfterDate(org, today, false);
	}

	public void fetchAllEvents() {
		OrganizationService.refreshOrganizationEvents(org);
	}

	public void refreshOrganization() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		// Throw an HTTP Response Error - Missing Credentials in case user is not logged in
		if (currentVolunteer.getVolunteer() == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
			context.responseComplete();
			return;
		}

		// Load only entities used in the default view, other views should refresh data before display
		try {
			this.org = OrganizationService
					.getOrganizationByIdWithAttachedEntities(Integer.parseInt(orgIdViewParameter));
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
			return;
		}

		// Throw an HTTP Response Error - Page Not Found in case org cannot be found from provided id
		if (this.org == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
					"Organization with id " + orgIdViewParameter + " not found.");
			context.responseComplete();
			return;
		}

		// authorize
		for (OrganizationFollower organizationFollower : currentVolunteer.getVolunteer().getOrganizationFollowers()) {
			if (organizationFollower.getOrganization().getOrgId() == this.org.getOrgId()) {
				this.currentOrganizationFollower = organizationFollower;
				break;
			}
		}
		// Throw an HTTP Response Error - Forbidden access in case user is not allowed view
		if (!(this.currentOrganizationFollower.getAdmin() || this.currentOrganizationFollower.getMod())) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN,
					"Not authorized to view page");
			context.responseComplete();
			return;
		}
	}

	public void refreshMembers() throws IOException {
		OrganizationService.refreshOrganizationMembers(this.org);
		FacesContext context = FacesContext.getCurrentInstance();
		// Throw an HTTP Response Error - Page Not Found in case org was deleted before refresh
		if (this.org == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
					"Organization with id " + orgIdViewParameter + " not found.");
			context.responseComplete();
			return;
		}
		this.orgFollowers = new ArrayList<OrganizationFollower>(this.org.getOrganizationFollowers());
	}

	public void refreshGroups() throws IOException {
		OrganizationService.refreshOrganizationGroups(this.org);
		FacesContext context = FacesContext.getCurrentInstance();
		// Throw an HTTP Response Error - Page Not Found in case org was deleted before refresh
		if (this.org == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
					"Organization with id " + orgIdViewParameter + " not found.");
			context.responseComplete();
			return;
		}
		this.orgGroups = new ArrayList<Group>(this.org.getGroups());
	}

	private static final Map<String, Method> eventTabChangeMethodCall;
	static {
		Map<String, Method> map = new HashMap<String, Method>();
		try {
			Class<EditOrganizationBean> bean = EditOrganizationBean.class;
			map.put("myEvents", bean.getMethod("fetchMyEvents"));
			map.put("pastEvents", bean.getMethod("fetchPastEvents"));
			map.put("futureEvents", bean.getMethod("fetchFutureEvents"));
			map.put("allEvents", bean.getMethod("fetchAllEvents"));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		eventTabChangeMethodCall = Collections.unmodifiableMap(map);
	}

	// Quirk of Primefaces 4.0 with nested tabs
	// see https://code.google.com/p/primefaces/issues/detail?id=3876 for more info
	public void onEventsTabChange(AjaxBehaviorEvent aEvent) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		try {
			onEventsTabChange((TabChangeEvent) aEvent);
		} catch (ClassCastException e) {
		}
	}

	public void onEventsTabChange(TabChangeEvent event) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		Method fetch = eventTabChangeMethodCall.get(event.getTab().getId());
		if (fetch != null) {
			fetch.invoke(this);
		} else {
			org.setEvents(Collections.<Event> emptySet());
		}

		FacesContext context = FacesContext.getCurrentInstance();
		// Throw an HTTP Response Error - Page Not Found in case org was deleted before refresh
		if (this.org == null) {
			context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
					"Organization with id " + orgIdViewParameter + " not found.");
			context.responseComplete();
			return;
		}

		// convert set to list for display in Primefaces
		this.selectedEvents = new ArrayList<CalculatedEvent>();
		if (!org.getEvents().isEmpty()) {
			for (Event e : org.getEvents()) {
				this.selectedEvents.add(new CalculatedEvent(e));
			}
		}
	}

	private static final Map<String, Method> tabChangeMethodCall;
	static {
		Map<String, Method> map = new HashMap<String, Method>();
		try {
			Class<EditOrganizationBean> bean = EditOrganizationBean.class;
			map.put("editView", bean.getMethod("refreshOrganization"));
			map.put("permissions", bean.getMethod("refreshMembers"));
			map.put("groups", bean.getMethod("refreshGroups"));
			map.put("events", bean.getMethod("fetchMyEvents"));
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		tabChangeMethodCall = Collections.unmodifiableMap(map);
	}

	public void onTabChange(TabChangeEvent event) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method refresh = tabChangeMethodCall.get(event.getTab().getId());
		if (refresh != null) {
			refresh.invoke(this);
		}
	}

	public List<CalculatedEvent> getSelectedEvents() {
		return selectedEvents;
	}

	public List<Group> getOrgGroups() {
		return orgGroups;
	}

	public List<OrganizationFollower> getOrgFollowers() {
		return orgFollowers;
	}

	public String getOrgId() {
		return orgIdViewParameter;
	}

	public Organization getOrg() {
		return org;
	}

	public OrganizationFollower getCurrentOrganizationFollower() {
		return currentOrganizationFollower;
	}

	public ScheduleModel getScheduleModel() {
		return scheduleModel;
	}

	public Event getSelectedEvent() {
		return (Event) selectedScheduleEvent.getData();
	}

	public ScheduleEvent getSelectedScheduleEvent() {
		return selectedScheduleEvent;
	}

	public void onEventSelect(SelectEvent selectEvent) {
		selectedScheduleEvent = (ScheduleEvent) selectEvent.getObject();
	}

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getEventTabIndex() {
		return eventTabIndex;
	}

	public void setEventTabIndex(String eventTabIndex) {
		this.eventTabIndex = eventTabIndex;
	}

	public void setOrgId(String orgId) {
		this.orgIdViewParameter = orgId;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
}
