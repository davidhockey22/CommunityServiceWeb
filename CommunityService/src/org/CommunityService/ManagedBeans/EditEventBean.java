package org.CommunityService.ManagedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventSkill;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Organization;
import org.CommunityService.EntitiesMapped.OrganizationFollower;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.EventService;
import org.CommunityService.Services.InterestService;
import org.CommunityService.Services.SkillService;
import org.CommunityService.Services.VolunteerService;
import org.ocpsoft.rewrite.annotation.Join;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
@Join(path = "/manageEvent/{eventId}", to = "/Web/EditEvent.xhtml")
public class EditEventBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	// passed from view parameter
	private String eventIdViewParameter;

	// initialize for view before render with initialize()
	private Event event;

	private boolean authorized;

	private DualListModel<String> interestModel;
	private Map<String, Interest> interestIdMap;

	private Set<EventSkill> currentEventSkills;
	private Map<String, Skill> skillIdMap;
	private DualListModel<String> skillModel;

	private List<EditableEventVolunteer> eventVolunteers;

	public void initialize() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			event = EventService.getEventById(Integer.parseInt(eventIdViewParameter));
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
			return;
		}

		this.refreshAuthorization();
		if (authorized) {
			this.refreshEvent();
		}
	}

	public void refreshAuthorization() {
		currentVolunteer.refreshOrganizations();
		Organization org = EventService.getEventOrganiztion(event.getEventId());
		OrganizationFollower orgF = null;
		for (OrganizationFollower orgf : currentVolunteer.getVolunteer().getOrganizationFollowers()) {
			if (org.getOrgId() == orgf.getOrganization().getOrgId()) {
				orgF = orgf;
				break;
			}
		}
		if (orgF != null && (orgF.getAdmin() || orgF.getMod())) {
			authorized = true;
		} else {
			authorized = false;
		}
	}

	public String refreshEvent() {
		event = EventService.getEventByIdFetch(this.event.getEventId());

		interestIdMap = new HashMap<String, Interest>();
		for (Interest interest : this.event.getInterests()) {
			interestIdMap.put(interest.getName(), interest);
		}

		List<Interest> otherInterests = InterestService.getInterests();
		List<String> otherInterestIds = new ArrayList<String>();
		List<String> eventInterests = new ArrayList<String>();
		for (Interest interest : otherInterests) {
			Interest prior = interestIdMap.put(interest.getName(), interest);
			if (prior == null) {
				otherInterestIds.add(interest.getName());
			} else {
				eventInterests.add(interest.getName());
			}
		}
		interestModel = new DualListModel<String>(otherInterestIds, eventInterests);

		currentEventSkills = new HashSet<EventSkill>();
		skillIdMap = new HashMap<String, Skill>();
		for (EventSkill eventSkill : event.getEventSkills()) {
			currentEventSkills.add(eventSkill);
			skillIdMap.put(eventSkill.getSkill().getSkillName(), eventSkill.getSkill());
		}

		List<Skill> otherSkills = SkillService.getSkills();
		List<String> otherSkillIds = new ArrayList<String>();
		List<String> eventSkills = new ArrayList<String>();
		for (Skill skill : otherSkills) {
			if (skillIdMap.put(skill.getSkillName(), skill) == null) {
				otherSkillIds.add(skill.getSkillName());
			} else {
				eventSkills.add(skill.getSkillName());
			}
		}
		skillModel = new DualListModel<String>(otherSkillIds, eventSkills);

		eventVolunteers = new ArrayList<EditableEventVolunteer>();
		for (EventVolunteer eventVolunteer : event.getEventVolunteers()) {
			eventVolunteers.add(new EditableEventVolunteer(eventVolunteer));
		}

		return "";
	}

	public String updateEvent() {
		// update interests
		HashSet<Interest> newInterests = new HashSet<Interest>();
		for (String id : interestModel.getTarget()) {
			newInterests.add(interestIdMap.get(id));
		}

		// update skills
		event.setInterests(newInterests);

		// list of new skills
		Set<EventSkill> newSkills = new HashSet<EventSkill>();
		for (String id : skillModel.getTarget()) {
			EventSkill eventSkill = new EventSkill(this.event, skillIdMap.get(id), false);
			newSkills.add(eventSkill);
		}

		// update event eventSkills
		this.event.setEventSkills(newSkills);

		// update volunteer points
		for (EditableEventVolunteer ev : eventVolunteers) {
			if (ev.getHoursChange() != 0) {
				VolunteerService.updateVolunteerPoints(ev.getVolunteer(), ev.hoursChange, 0, 0);
			}
		}

		// update event changes in DB
		EventService.update(event);

		return "";
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public String getEventId() {
		return eventIdViewParameter;
	}

	public void setEventId(String eventId) {
		this.eventIdViewParameter = eventId;
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

	public List<EditableEventVolunteer> getEventVolunteers() {
		return eventVolunteers;
	}

	public String[] getApproveOptions() {
		return EditableEventVolunteer.approvalOptions;
	}

	public static class EditableEventVolunteer {

		private EventVolunteer eventVolunteer;

		private int hoursChange = 0;

		private static final String[] approvalOptions;
		static {
			approvalOptions = new String[2];
			approvalOptions[0] = "Yes";
			approvalOptions[1] = "No";
		}

		public EditableEventVolunteer(EventVolunteer eventVolunteer) {
			this.eventVolunteer = eventVolunteer;
		}

		public EventVolunteer getEventVolunteer() {
			return eventVolunteer;
		}

		public Volunteer getVolunteer() {
			return this.getEventVolunteer().getVolunteer();
		}

		public String getAttendedHours() {
			return this.getEventVolunteer().getAttendedHours().toString();
		}

		public void setAttendedHours(String hours) {
			int newHours = Integer.parseInt(hours);
			this.hoursChange = this.getEventVolunteer().getAttendedHours() - newHours;
			this.getEventVolunteer().setAttendedHours(newHours);
		}

		public int getHoursChange() {
			return this.hoursChange;
		}

		public String getApproved() {
			return this.eventVolunteer.getApproved() ? approvalOptions[0] : approvalOptions[1];
		}

		public String[] getApproveOptions() {
			return EditableEventVolunteer.approvalOptions;
		}

		public void setApproved(String approval) {
			if (approval.equals(approvalOptions[0])) {
				this.eventVolunteer.setApproved(true);
			} else if (approval.equals(approvalOptions[1])) {
				this.eventVolunteer.setApproved(false);
			}
		}
	}
}