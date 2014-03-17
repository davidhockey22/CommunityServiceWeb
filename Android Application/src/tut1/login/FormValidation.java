package tut1.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {

	static String confirmPassword(String password, String confirm) {

		if(password == null || confirm == null || confirm.equals(password) == false) {
			
			return "The 'Confirm Password' field must match the 'Password' field.\n\n";			
		}
		
		return "";
	}
	static String checkPassword(String password){
		
		String mess = "Password must be at least 8 characters long.\n\n";
		
		if(password == null)
			return mess;
		
		boolean valid = password.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$");
		
		if( valid && password.length() >= 8 ) {}
		else
			return mess;
		
		return "";
	}
	static String checkPhoneNumber(String phoneNumber){
			
		if (phoneNumber == null ||
				phoneNumber.length() != 10 ) {
			
			return "Phone Number must be 10 digits and not contain dashes.\n\n";
		  }
		
		return "";
	}
	static String checkEmail(String email){

		String mess = "Invalid email format.";
		
		if(email == null) return mess;
		
		int at = email.indexOf("@");
		int dot = email.indexOf(".");
		
		if (at < 1 || dot < at + 2 || dot + 2 >= email.length() ) {
			
			return mess;
		}
				
		return "";
	}	
}
