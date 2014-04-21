package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventSkill;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.EntitiesMapped.VolunteerInterest;
import org.CommunityService.EntitiesMapped.VolunteerSkill;
import org.CommunityService.Services.EventService;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.OrganizationService;
import org.CommunityService.Services.SkillService;
import org.CommunityService.Services.VolunteerService;
import org.hibernate.HibernateException;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.model.DualListModel;

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

	private DualListModel<String> interestModel = new DualListModel<String>();
	private DualListModel<String> skillModel = new DualListModel<String>();

	List<Interest> allInterests = new ArrayList<Interest>();
	List<Skill> allSkills = new ArrayList<Skill>();

	/**
	 * Checks if the URL is valid and user is authorized to create an event.
	 * Initializes local variables on success. Sends an HTTP Error code and
	 * returns on failure. Should be called only when generating the view for
	 * the first time.
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		// check if user is authorized to create an event
		if (!currentVolunteer.isLoggedIn()) {
			// Throw an HTTP Response Error - Missing Credentials in case user
			// is not logged in
			context.getExternalContext().responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Not logged in");
			context.responseComplete();
			return;
		}

		Integer orgId;
		try {
			orgId = Integer.parseInt(this.orgId);
			this.currentVolunteer.refreshOrganizations();
			if (!currentVolunteer.isMemberOfOrganizatoin(orgId)) {
				// Throw an HTTP Response Error - Forbidden access in case user
				// is not logged in
				context.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to view page");
				context.responseComplete();
				return;
			}
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid
			// orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
			return;
		}

		// if eventId viewParam present, assume new event was created
		if (eventId != null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
					"Event created, click the \"View Event\" button view it now"));
		}

		setSelectedOrgId(this.orgId);
		// get cached organizations from currentVolunteer
		organiztions = new TreeMap<String, String>();
		for (OrganizationFollower organizationFollower : this.currentVolunteer.getVolunteer().getOrganizationFollowers()) {
			if (organizationFollower.getAdmin() || organizationFollower.getMod()) {
				organiztions.put(organizationFollower.getOrganization().getOrgName(), organizationFollower.getOrganization().getOrgId().toString());
			}
		}

		initInterestAndSkills();
	}

	void initInterestAndSkills() {

		// interests
		List<Interest> interests = InterestService.getInterests();
		allInterests = new ArrayList<Interest>();
		for (Interest i : interests)
			allInterests.add(i);

		List<String> intStr = new ArrayList<String>();
		for (Interest i : interests)
			intStr.add(i.getName() + " - " + i.getDescription());

		interestModel.setSource(intStr);

		// skills
		List<Skill> skills = null;
		try {
			skills = SkillService.getSkills();
			allSkills = new ArrayList<Skill>();
			for (Skill i : skills)
				allSkills.add(i);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> skStr = new ArrayList<String>();
		for (Skill i : skills)
			skStr.add(i.getSkillName() + " - " + i.getDescription());

		skillModel.setSource(skStr);
	}

	public String Create() throws HibernateException {
		// last check that user has permission, as organizationfollower info
		// could be stale
		OrganizationFollower orgF = OrganizationService.getOrganizationFollowerBy(Integer.parseInt(this.selectedOrgId), this.currentVolunteer.getVolunteer()
				.getVolunteerId());

		// create the event if member has permissions
		if (orgF.getAdmin() || orgF.getMod()) {
			Event event = new Event(eventname, begintime, endtime);
			event.setDescription(description);
			event.setLocation(location);
			event.setOrganization(orgF.getOrganization());
			event.setVolunteer(this.currentVolunteer.getVolunteer());
			addInterestsAndSkills(event);

			EventService.addEvent(event);
			this.eventId = event.getEventId().toString();
		}

		// redirect to same view either way, as the view recreation will trigger
		// an orgfollower data refresh for the
		// logged in user
		return "/NewEvent.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public void addInterestsAndSkills(Event ev) {

		Set<Interest> interestSet = new HashSet<Interest>();
		String str = null;
		for (String s : interestModel.getTarget()) {
			for (Interest i : allInterests) {
				str = new String(i.getName() + " - " + i.getDescription());
				if (s.equals(str)) {
					interestSet.add(i);
				}
			}
		}
		ev.setInterests(interestSet);

		Set<EventSkill> skillSet = new HashSet<EventSkill>();
		for (String s : skillModel.getTarget()) {
			for (Skill i : allSkills) {
				str = new String(i.getSkillName() + " - " + i.getDescription());
				if (s.equals(str)) {
					skillSet.add(new EventSkill(ev, i, false));
				}
			}
		}
		ev.setEventSkills(skillSet);
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

	public List<Interest> getAllInterests() {
		return allInterests;
	}

	public void setAllInterests(List<Interest> allInterests) {
		this.allInterests = allInterests;
	}

	public List<Skill> getAllSkills() {
		return allSkills;
	}

	public void setAllSkills(List<Skill> allSkills) {
		this.allSkills = allSkills;
	}
}
