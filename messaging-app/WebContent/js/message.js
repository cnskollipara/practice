function getMessages() {
	alert("onLoad")
	var textSearch = document.getElementById("textSearch").value;
	 $.ajax({
	     type: "GET",
	     url: "/messaging-app/message?extId="+getCookie("extId")+"&"+"searchStr="+textSearch,
	     contentType: "application/json",
	     data:data,
	     async: false, //add this
	   }).done(function ( data ) {
	         Success = true;
	         
	         
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

function dynamicDivCreation() {
        var BLOCKS_PER_CHART = 2;
            function search(){
                console.log("hi");
                var container = document.createElement("div");
            var text = "Mounika!";
            var blockDiv, textSpan;  // used in the for loop

           

            for(var i = 0; i < BLOCKS_PER_CHART; i++) {
                 container.className = "card gedf-card";
                document.getElementById("chartdata").appendChild(container);
                blockDiv = document.createElement("div");
                blockDiv.className = "card-header";
                textSpan = document.createElement("p");
                textSpan.className="d-flex justify-content-between align-items-center h7 text-muted dropdown";
                textSpan.append(text);  // see note about browser compatibility
                blockDiv.append(textSpan);
                container.append(blockDiv);
                blockDiv = document.createElement("div");
                blockDiv.className = "card-body";
                textSpan = document.createElement("textarea");
                textSpan.append("Heloo how are you");  // see note about browser compatibility
                blockDiv.append(textSpan);
                container.append(blockDiv);
            }
            }

 }  