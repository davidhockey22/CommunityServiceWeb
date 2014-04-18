package org.CommunityService.ManagedBeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Event;
import org.CommunityService.Services.EventService;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@ViewScoped
@Join(path = "/manageEvent/{eventId}", to = "/Web/EditEvent.xhtml")
public class EditEventBean {
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean currentVolunteer;

	private Event event;

	// passed from view parameter
	private String eventIdViewParameter;

	public void initialize() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		
		try {
			event = EventService.getEventByIdForEdit(Integer.parseInt(eventIdViewParameter), this.currentVolunteer.getVolunteer().getVolunteerId());
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid orgId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid org id");
			context.responseComplete();
			return;
		}
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

	public String getEventId() {
		return eventIdViewParameter;
	}

	public void setEventId(String eventId) {
		this.eventIdViewParameter = eventId;
	}

}
