package org.CommunityService.ManagedBeans;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;
import org.CommunityService.util.Gravatar;
import org.ocpsoft.rewrite.annotation.Join;

@ManagedBean
@RequestScoped
@Join(path = "/volunteer/{volunteerId}", to = "/Web/ViewVolunteer.xhtml")
public class ViewVolunteerBean {
	@ManagedProperty(value = "#{param.volunteerId}")
	private String volunteerId;

	private Volunteer volunteer;

	public void fetchVolunteer() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			this.volunteer = VolunteerService.getVolunteerByIdWithAttachedEntities(Integer.parseInt(this.volunteerId),
					"GroupMembers", "VolunteerInterests", "OrganizationFollowers", "VolunteerSkills");
			if (this.volunteer == null) {
				// Throw an HTTP Response Error - Page Not Found in case volunteer cannot be found from provided id
				context.getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND,
						"Volunteer with id " + this.volunteerId + " does not exist");
				context.responseComplete();
			}
		} catch (NumberFormatException e) {
			// Throw an HTTP Response Error - Invalid Syntax in case invalid volunteerId was supplied
			context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid volunteer id");
			context.responseComplete();
		}
	}

	public String getGravatarURL() {
		String email = (this.volunteer != null ? this.volunteer.getEmailAddress() : null);
		return Gravatar.getGravatarImage(email);
	}

	// Getters and Setters
	public Volunteer getVolunteer() {
		return volunteer;
	}

	public void setVolunteerId(String volunteerId) {
		this.volunteerId = volunteerId;
	}

	public String getVolunteerId() {
		return this.volunteerId;
	}
}