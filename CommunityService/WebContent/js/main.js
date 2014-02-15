function validateRegistration(formID, passwordID, confirmPasswordID, phoneNumberID, emailID, messageID){
	
	//clear validation message
	var mess = document.getElementById(formID + ":" + messageID);
	mess.innerHTML = "";
	
	//check password
	var password = document.getElementById(formID + ":" + passwordID);
	mess.innerHTML = checkPassword(password.value);
	if(mess.innerHTML != "") return false; //validation error
	
	//check confirm password
	var confirm = document.getElementById(formID + ":" + confirmPasswordID);
	if(confirm.value != password.value) {
		mess.innerHTML = 'The "Confirm Password" field must match the "Password" field';
		return false;
	}

	//check phone number
	var phoneNumber = document.getElementById(formID + ":" + phoneNumberID);
	var reformat = reformatPhoneNumber(phoneNumber.value);
	mess.innerHTML = checkPhoneNumber(reformat);
	if(mess.innerHTML != "") return false; //validation error
	
	//check email
	var email = document.getElementById(formID + ":" + emailID);
	mess.innerHTML = checkEmail(email.value);
	if(mess.innerHTML != "") return false; //validation error

	return false;
}

function checkPassword(password){
	
	if( password.length < 8 )
		return "Password must be at least 8 characters long";
	
	var regExp = /\d/i; //match any digit
	var result = password.match(regExp);
	
	if (result==null ||
			result=="" )
	  {
		return "Password must contain a number";
	  }
	
	regExp = /\w/i; //match any letter
	result = password.match(regExp);
	
	if (result==null ||
			result=="" )
	  {
		return "Password must contain a letter";
	  }
	
	return "";
}
function reformatPhoneNumber(phoneNumber) {
	
	//reformat and remove illegal symbols such as "-"
	var reformat = "";
	for(var i = 0; i < phoneNumber.length; i++) {
		
		if(isNaN(phoneNumber[i])){
		}
		else
			reformat = reformat + phoneNumber[i];
	}
	
	return reformat;	
}
function checkPhoneNumber(phoneNumber){
	
	if (phoneNumber==null ||
			phoneNumber=="" ||
			phoneNumber.length != 10)
	  {
		return "Phone Number must be in format XXX-XXX-XXXX";
	  }
	
	return "";
}
function checkEmail(email){

	var at = email.indexOf("@");
	var dot = email.lastIndexOf(".");
	if (at < 1 || dot < at + 2 || dot + 2 >= email.length )
		return "Invalid email format";
	
	return "";
}
function highlightField(field, string){
	$("#message").val(string);
	$(field).addClass("error");
	sendMessage();
	alert("test2");
}