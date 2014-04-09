function showMessage(formID, messageID, message) {
	
	document.getElementById(formID + ":" + messageID).innerHTML = message;
	
	return true;
}

function validateEditPassword(){
	
	document.getElementById("passwordForm:submitValidation").innerHTML = "";
	var returnVal = true;

	if( document.getElementById("passwordForm:passwordValidation").innerHTML != "" )
		returnVal = false;

	if( document.getElementById("passwordForm:confirmValidation").innerHTML != "" )
		returnVal = false;
	
	if( document.getElementById("passwordForm:oldPasswordValidation").innerHTML != "" )
		returnVal = false;	
	
	if( returnVal == false )
		document.getElementById("passwordForm:submitValidation").innerHTML = "Please correct errors";

	return returnVal;
}
function validateRegistration(){
	
	document.getElementById("registerForm:submitValidation").innerHTML = "";
	var returnVal = true;

	if( document.getElementById("registerForm:passwordValidation").innerHTML != "" )
		returnVal = false;

	if( document.getElementById("registerForm:confirmValidation").innerHTML != "" )
		returnVal = false;
	
	if( document.getElementById("registerForm:phoneValidation").innerHTML != "" )
		returnVal = false;
	
	if( document.getElementById("registerForm:emailValidation").innerHTML != "" )
		returnVal = false;
	
	if( returnVal == false )
		document.getElementById("registerForm:submitValidation").innerHTML = "Please correct errors";

	return returnVal;
}
function confirmPassword(confirm, passwordID, formID, messageID) {
	
	//validation message
	var mess = null;
	if(messageID != null)
		mess = document.getElementById(formID + ":" + messageID);
	
	//check confirm password
	var password = document.getElementById(formID + ":" + passwordID);
	if(confirm != password.value) {
		
		if(mess != null)
			mess.innerHTML = 'The "Confirm Password" field must match the "Password" field';
		
		return 'The "Confirm Password" field must match the "Password" field';
	}
	
	if(mess != null)
		mess.innerHTML = "";
	
	return "";
}
function checkPassword(password, formID, messageID){
	
	//validation message
	var mess = null;
	if(messageID != null)
		mess = document.getElementById(formID + ":" + messageID);
	
	if( password.length < 8 ) {
		if(mess != null)
			mess.innerHTML = "Password must be at least 8 characters long";
		
		return "Password must be at least 8 characters long";
	}
	
	var regExp = /\d/i; //match any digit
	var result = password.match(regExp);
	
	if (result==null ||
			result=="" )
	  {
		if(mess != null)
			mess.innerHTML = "Password must contain a number";
		
		return "Password must contain a number";
	  }
	
	regExp = /\w/i; //match any letter
	result = password.match(regExp);
	
	if (result==null ||
			result=="" )
	  {
		if(mess != null)
			mess.innerHTML = "Password must contain a letter";
		
		return "Password must contain a letter";
	  }
	
	if(mess != null)
		mess.innerHTML = "";
	
	return "";
}
function myFunction() {
	
	alert("Test");
	return;
}
function checkPhoneNumber(phoneNumber, formID, messageID){
		
	//validation message
	var mess = null;
	if(messageID != null)
		mess = document.getElementById(formID + ":" + messageID);
	
	if (phoneNumber==null ||
			phoneNumber=="" ||
			phoneNumber.length != 10)
	  {
		if(mess != null)
			mess.innerHTML = "Phone Number must be 10 digits and not contain dashes";
		
		return "Phone Number must be 10 digits and not contain dashes";
	  }
	
	if(mess != null)
		mess.innerHTML = "";
	
	return "";
}
function checkEmail(email, formID, messageID){

	//validation message
	var mess = null;
	if(messageID != null)
		mess = document.getElementById(formID + ":" + messageID);
	
	var at = email.indexOf("@");
	var dot = email.lastIndexOf(".");
	if (at < 1 || dot < at + 2 || dot + 2 >= email.length ) {
		
		if(mess != null)
			mess.innerHTML = "Invalid email format";
		
		return "Invalid email format";
	}
	
	if(mess != null)
		mess.innerHTML = "";
	
	return "";
}
function highlightField(field, string){
	$("#message").val(string);
	$(field).addClass("error");
	sendMessage();
	alert("test2");
}