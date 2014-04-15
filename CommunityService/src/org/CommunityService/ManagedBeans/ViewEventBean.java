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

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.EntitiesMapped.EventSkill;
import org.CommunityService.EntitiesMapped.Group;
import org.CommunityService.EntitiesMapped.GroupMember;
import org.CommunityService.EntitiesMapped.Interest;
import org.CommunityService.EntitiesMapped.Skill;
import org.CommunityService.EntitiesMapped.EventVolunteer;
import org.CommunityService.Services.EventService;
import org.CommunityService.Services.GroupService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean(name = "viewEventBean")
@ViewScoped
@Join(path = "/event/{eventId}", to = "/Web/ViewEvent.xhtml")
public class ViewEventBean {
	private Event event;

	private String eventId;

	private boolean renderDropDown = false;
	private boolean renderPending = false;		
	private boolean renderSignUpGroup = false;	
	private boolean signedUp = false;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;
	
	private List<Interest> interests = new ArrayList<Interest>();
	private List<Skill> skills = new ArrayList<Skill>();;
	private String hostedOrgName;

	String selectedGroup = null;
	List<String> groups = new ArrayList<String>();
	
	public String signUp() {
		System.out.println("Signing up!");
		EventService.signUp(currentVolunteer.getVolunteer(), event);
		renderDropDown = false;
		renderPending = true;
		renderSignUpGroup = false;
		signedUp = true;
		return "?faces-redirect=true";
	}
	
	public String signUpGroup() {
		
		renderDropDown = true;
		
		return null;
	}
	public String confirmSignUpGroup() {
		
		Group selected = null;
		for(GroupMember gm : currentVolunteer.getVolunteer().getGroupMembers()) {
			if(gm.getGroup().getGroupName().equals(selectedGroup)) {
				selected = gm.getGroup();
				break;
			}
		}		
		
		//todo:
		//implement group sign up
		//use notification system?
		
		return signUp();
	}
	
	public String removeEventFromVolunteer() {
		EventService.removeEventVolunteer(Integer.parseInt(eventId), currentVolunteer.getVolunteer().getVolunteerId());
		renderDropDown = false;
		renderPending = false;
		
		if(currentVolunteer.getVolunteer().getGroupMembers() != null &&
				currentVolunteer.getVolunteer().getGroupMembers().isEmpty() == false )
			renderSignUpGroup = true;
		
		signedUp = false;
		return "?faces-redirect=true";
	}

	public void fetchEvent() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if (event == null || event.getEventId() != Integer.parseInt(eventId)) {
				this.event = EventService.getEventByIdFetch(Integer.parseInt(eventId));
				System.out.println("Sign up!");
				this.signedUp = this.signedUp();
				
				if(signedUp) {
					
					EventVolunteer ev = EventService.getEventVolunteerById(event.getEventId(), currentVolunteer.getVolunteer().getVolunteerId());
					renderPending = !ev.getApproved();
				}
				else {
					if(currentVolunteer.getVolunteer().getGroupMembers() != null &&
							currentVolunteer.getVolunteer().getGroupMembers().isEmpty() == false )
						renderSignUpGroup = true;
				}
			} else {
				// Throw an HTTP Response Error - Page Not Found in case event cannot be found from provided id
				context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
						"Event with id " + eventId + " does not exist");
				context.responseComplete();
			}
		} catch (NumberFormatException e) {
			this.event = null;
			// Throw an HTTP Response Error - Invalid Syntax in case invalid eventId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid event id");
			context.responseComplete();
			return;
		}
		
		if(event != null) {
			
			interests.addAll(event.getInterests());
			
			for(EventSkill es : event.getEventSkills()) {
				
				skills.add(es.getSkill());
			}
		}
		
		for(GroupMember gm : currentVolunteer.getVolunteer().getGroupMembers()) {
			
			groups.add(gm.getGroup().getGroupName());
		}
	}

	public boolean signedUp() {
		if (currentVolunteer.getVolunteer() != null) {
			List<Event> userEvents = (List<Event>) EventService.getEventsByVolunteer(currentVolunteer.getVolunteer()
					.getVolunteerId());
			for (Iterator<Event> iterator = userEvents.iterator(); iterator.hasNext();) {
				Event event = (Event) iterator.next();
				if (event.getEventId() == Integer.parseInt(this.eventId)) {
					System.out.println("ViewEventBean.signedUp()");
					return true;
				}
			}
		}
		return false;
	}

	public boolean isSignedUp() {

		return signedUp;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Event getEvent() {
		return event;
	}

	public LoginBean getCurrentVolunteer() {
		return currentVolunteer;
	}

	public void setCurrentVolunteer(LoginBean currentVolunteer) {
		this.currentVolunteer = currentVolunteer;
	}
	public String getHostedOrgName() {
				
		if(event == null || event.getOrganization() == null) {
			return "None";
		}
		
		return event.getOrganization().getOrgName();
	}
	public List<Interest> getInterests() {
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	public boolean isRenderPending() {
		return renderPending;
	}

	public void setRenderPending(boolean renderPending) {
		this.renderPending = renderPending;
	}	
	public boolean isRenderSignUpGroup() {
		return renderSignUpGroup;
	}

	public void setRenderSignUpGroup(boolean renderSignUpGroup) {
		this.renderSignUpGroup = renderSignUpGroup;
	}
	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
	public boolean isRenderDropDown() {
		return renderDropDown;
	}

	public void setRenderDropDown(boolean renderDropDown) {
		this.renderDropDown = renderDropDown;
	}	
}
