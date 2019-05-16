function login(){
	//var XMLHTTPRequest = new XMLHTTPRequest();
var username = document.getElementById("username").value;
var password = document.getElementById("password").value;

 if(username == null || username.length ==0){
     return;
 }else if(password == null || password.length == 0){
     return;
 }
 var requestData = {"email" : username,"password" : password};
 var data = JSON.stringify(requestData);
 verifyIfUserLoggedIn();
 $.ajax({
     type: "POST",
     url: "/MessagingApp/login",
     contentType: "application/json",
     data:data,
     async: false, //add this
   }).done(function ( data ) {
         Success = true;
         if(data.data == undefined || data.statusMessage == "USER_NOT_AVAILABLE"){
        	alert("Invalid User");
         }else{
        	 console.log("Success"+ JSON.stringify(data));
             
             var userData = data.data;
             console.log("userData"+userData.extId);
             
             setCookie("extid",userData.extId,1);
             setCookie("name",userData.name,1);
             setCookie("email",userData.email,1);
             window.location.href = './postmessage.html';
             console.log("naviagate");
         }
         
 }).fail(function ( data ) {
        Success = false;
        console.log("data");
 });

}
	
function verifyIfUserLoggedIn() {
	if(getCookie("extid") != null && getCookie("extid").length > 0) {
	     window.location.href = './postmessage.html';
	     return;
	 }
}
function setCookie(cname,cvalue,exdays) {
	  var d = new Date();
	  d.setTime(d.getTime() + (exdays*24*60*60*1000));
	  var expires = "expires=" + d.toGMTString();
	  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	}

function getCookie(cname) {
	  var name = cname + "=";
	  var decodedCookie = decodeURIComponent(document.cookie);
	  var ca = decodedCookie.split(';');
	  for(var i = 0; i < ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}

window.onload = verifyIfUserLoggedIn;