var messagesArray = new Array();

function getMessages() {
	console.log("onLoad"+getCookie("extid"));
	var textSearch = document.getElementById("textSearch").value;
	$("#profilename").text(getCookie("name"));
	$("#profilemail").text(getCookie("email"));
	 $.ajax({
	     type: "GET",
	     url: "/MessagingApp/message?extId="+getCookie("extid")+"&"+"searchStr="+textSearch,
	     contentType: "application/json",
	     async: false, //add this
	   }).done(function ( data ) {
	         Success = true;
	         if(data.statusCode == 200 && (undefined == data.messages || data.messages.length != 0)){
	        	 dynamicDivCreation(data.data);
	         }
	         
	 }).fail(function ( data ) {
	        Success = false;
	        console.log("data");
	 });
}

window.onload = getMessages;

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

function dynamicDivCreation(data) {
	messagesArray = [];
        var BLOCKS_PER_CHART = 2;
                console.log("hi");
                
            var blockDiv, textSpan;  // used in the for loop
			 var userExtId = getCookie("extid");
           var reqData = data.messages;
           var length = reqData.length;
           var parentDiv = document.createElement("div");
           parentDiv.id = "chartdata _parent";
           document.getElementById("chartdata").appendChild(parentDiv);
            for(var i = 0; i < reqData.length; i++) {
            	var messageId = reqData[i].id;
            	var message = {"msgid" : messageId,"msginfo":reqData[i].info,"isPublic" : reqData[i].isPublic};
				var msgOwner = reqData[i].msgOwner;
            	messagesArray[i] = {"messageId" : message};
            	var container = document.createElement("div");
                 container.className = "card gedf-card";
                 container.id = "chartdata"+i;
                document.getElementById("chartdata").appendChild(container);
                blockDiv = document.createElement("div");
                blockDiv.className = "card-header";
                textSpan = document.createElement("p");
                textSpan.className="d-flex justify-content-between align-items-center h7 text-muted dropdown";
                textSpan.append(reqData[i].userName);  // see note about browser compatibility
                blockDiv.append(textSpan);
                container.append(blockDiv);
                blockDiv = document.createElement("div");
                blockDiv.className = "card-body";
                mytabVContent = document.createElement("div");
                mytabVContent.className = "tab-content";
                mytabVContent.id="myTabContent1";
                blockDiv.append(mytabVContent)
                textSpan = document.createElement("p");
                textSpan.append(reqData[i].info);  // see note about browser compatibility
                mytabVContent.append(textSpan);
                mytabVContent = document.createElement("div");
                mytabVContent.className = "btn-toolbar justify-content-between";
                blockDiv.append(mytabVContent);
                
                if(null != userExtId && null != msgOwner && userExtId == msgOwner ){
                innerDiv = document.createElement("div");
                innerDiv.className = "btn-group";
                mytabVContent.append(innerDiv);
                
                innerDiv = document.createElement("div");
                innerDiv.className = "custom-control custom-checkbox";
                mytabVContent.append(innerDiv);
                
                button = document.createElement("button");
                button.className = "btn btn-outline-primary";
                innerDiv.append(button);  // see note about browser compatibility
                
                itag = document.createElement("i");
                itag.className = "fa fa-edit";
                itag.id = messageId;
                itag.onclick = function(){
                	updatemessage(this.id);
                  };
                button.append(itag);
                
                button = document.createElement("button");
                button.className = "btn btn-outline-primary";
                innerDiv.append(button);  // see note about browser compatibility
                
                itag = document.createElement("i");
                itag.className = "fa fa-trash";
                itag.id = messageId;
                itag.onclick = function(){
                	showdeletePopup(this.id);
                  };
                button.append(itag);
				}
                
                container.append(blockDiv);
                parentDiv.append(container);
            }
}

function showdeletePopup(messageId){
	document.getElementById("myModal1").style.display = "block";
	document.getElementById("delete_id").value = messageId;
}

function deleteMessage(){
	document.getElementById("myModal1").style.display = "none";
	var messageid = document.getElementById("delete_id").value;
	document.getElementById("delete_id").value = "";
	deletepost(messageid);
	
}

function removeDivs() {
	  var elem = document.getElementById('chartdata _parent');
	  if(null != elem){
		  elem.parentNode.removeChild(elem);
	  }
	  
	  
	}

function searchPost(){
	removeDivs();
	getMessages(textSearch);
}
            

function deletepost(messageid){
	 $.ajax({
	     type: "DELETE",
	     url: "/MessagingApp/message?extId="+getCookie("extid")+"&"+"msgId="+messageid,
	     contentType: "application/json",
	     async: false, //add this
	   }).done(function ( data ) {
	         Success = true;
	         if(data.statusCode == 200 ){
	        	 document.getElementById('textSearch').value = '';
	        	 searchPost();   
	         }
	         
	 }).fail(function ( data ) {
	        Success = false;
	        console.log("data");
	 });
}

function updatemessage(messageid){
	console.log("messagesArray"+JSON.stringify(messagesArray))
	document.getElementById("myModal").style.display = "block";
	for(var i = 0; i < messagesArray.length; i++) {
		var obj = messagesArray[i].messageId;
		if(obj.msgid == messageid){
			document.getElementById("messageShow").value = obj.msginfo;
			document.getElementById("modalcheckbox").checked = obj.isPublic;
			document.getElementById("messageid_id").value = obj.msgid;
			
			
		}
	}
}
  

window.onclick = function(event) {
	  if (event.target == document.getElementById("myModal")) {
	    modal.style.display = "none";
	  }
}
	  
function closemodal(){
	document.getElementById("myModal").style.display = "none";
}

 function createPost(){
	 console.log("createpost");
	 var ispublic = false;
	 var checkbox = document.getElementById("defaultUnchecked").checked;
	 var messageInfo = document.getElementById("message").value;
	 if(checkbox != undefined && checkbox){
		 ispublic = true;
	 }
	 var requestData = {"info" : messageInfo,"msgOwner":getCookie("extid"),"isPublic" : ispublic};
	 var data = JSON.stringify(requestData);
	 createorUpdatepost(data);
	
	 
 }
            
var modalclose = false;
  function updatedpost(){
	  var msginfo = document.getElementById("messageShow").value;
	  var ispublic = document.getElementById("modalcheckbox").checked;
	  var msgid = document.getElementById("messageid_id").value;
	  var requestData = {"id":msgid ,"info" : msginfo,"msgOwner":getCookie("extid"),"isPublic" : ispublic};
	  var data = JSON.stringify(requestData);
	  modalclose = true;
	  createorUpdatepost(data);
  }
  
  function createorUpdatepost(data){
	  $.ajax({
		     type: "POST",
		     url: "/MessagingApp/message",
		     contentType: "application/json",
		     data:data,
		     async: false, //add this
		   }).done(function ( data ) {
		         Success = true;
		         if(undefined == data|| data.statusCode != 200){
		        	
		         }else{
		        	 console.log("Success"+ JSON.stringify(data));
		        	 document.getElementById("message").value = '';
		        	 document.getElementById('textSearch').value = '';
		        	 if(modalclose){
		        		 closemodal();
		        	 }
		        	 searchPost();   
		         }
		         
		 }).fail(function ( data ) {
		        Success = false;
		        console.log("data");
		 });
  }
  
  function logout() {
	  setCookie("extid",null,0);
      setCookie("name",null,0);
      setCookie("email",null,0);
      window.location.href = './index.html';
  }
  

  function setCookie(cname,cvalue,exdays) {
  	  var d = new Date();
  	  d.setTime(d.getTime() + (exdays*24*60*60*1000));
  	  var expires = "expires=" + d.toGMTString();
  	  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
  	}