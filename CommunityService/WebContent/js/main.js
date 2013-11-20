function checkPassword(password){
	alert("TBD");
}
function checkPhoneNumber(password){
	alert("TBD");
}
function checkEmail(password){
	alert("TBD");
}
function highlightField(field, string){
	$("#message").val(string);
	$(field).addClass("error");
	sendMessage();
}