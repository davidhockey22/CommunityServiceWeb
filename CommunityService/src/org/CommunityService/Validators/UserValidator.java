package org.CommunityService.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.CommunityService.EntitiesMapped.Volunteer;
import org.CommunityService.Services.VolunteerService;



@FacesValidator("org.CommunityService.Validators.UserValidator")
public class UserValidator implements Validator {

	public UserValidator() {
	}

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String username = value.toString();
		Volunteer v = null;
		try {
			v = VolunteerService.getVolunteerByName(username);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage("Name Validation Failed", "Username field is invalid.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		if(v!=null){
			FacesMessage msg = new FacesMessage("Name Validation Failed", "This username has already been taken.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}
}