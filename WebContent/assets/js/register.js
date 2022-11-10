function setContryCode(obj) {
	var idx  = obj.selectedIndex;
	var ccode = obj.options[idx].value;
	document.getElementById("nationality").value = ccode.substring(0, 2);
	document.getElementById("countryCode").value = ccode.substring(3, ccode.length);
}

function trimZero() {
	var phone = document.getElementById("phone").value;
	if(phone.length > 0) {
		document.getElementById("phone").value = phone.replace(/^(0+)/g, '');
	}
}


function submitCheck(obj){

	var target = $( "#validateInput" );
	
	
	//validate email
	validRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i;
	var strEmail = document.getElementById("email").value;

	if(trim(strEmail).length < 1) {
		var text = "Email: Please input email";
		showHintText(target, text);
		return false;
	}
	
	if(trim(strEmail).search(validRegExp) == -1){
		var text = "Email: Not a valid email";
		showHintText(target, text);
		return false;
	}
	
	//validate exist of email
	var exist1 = document.getElementById("email_Exist").value;
	if(exist1 == "Y"){
		var text = "This email has been taken, please try another one.";
		showHintText(target, text);
		return false;
	}
	

	//validate Password
	var strPasswd = document.getElementById("passwd");
	
	if(strPasswd != null) {
		
		if(trim(strPasswd.value).length < 4){
			var text = "Password: Please use 4 to 12 characters";
			showHintText(target, text);
			return false;
		}
	
	}

    	
	//validate Name
	var ename = document.getElementById("ename").value;
	if(trim(ename).length < 1){
		var text = "Name: Please input your name";
		showHintText(target, text);
    	return false;
	}
	
	//Nationality
	var e2 = document.getElementById("cc");
	var nation = e2.options[e2.selectedIndex].value;
	
	if (nation == 0) {
		var text = "Nationality: Please choose one";
		showHintText(target, text);
		return false;
	}
	
	if(trim(document.getElementById("nationality").value) == "") {
		var idx  = document.getElementById("cc").selectedIndex;
		var ccode = document.getElementById("cc").options[idx].value;
		document.getElementById("nationality").value = ccode.substring(0, 2);
	}
	
	//validate Contact Number
	var pho = document.getElementById("phone").value;
	
	if(trim(pho).length < 1){
		var text = "Contact Number: Please input your phone number";
		showHintText(target, text);
		return false;
	}
	
	
	//validate Gender
    var e = document.getElementById("gender");
	var gen = e.options[e.selectedIndex].value;
	
	if (gen == 0) {
		var text = "Gender: Please choose one";
		showHintText(target, text);
		return false;
	}
	
	//DOB
	var year = document.getElementById("year").value;
	var month = document.getElementById("month").value;
	var day = document.getElementById("day").value;
	document.getElementById("dob").value = year +"-"+ month +"-"+ day;

	
	//Agreement
	if (document.getElementById("agree") != null){
		var agreement = document.getElementById("agree");
		if(!agreement.checked){
			var text = "You must read and agree to the Terms of Service before continue.";
			showHintText(target, text);
			return false;
		}
	}
	
	document.getElementById("actionType").value = obj;
	
	if (document.getElementById("updateBtn") != null){
		$( "#updateBtn" ).attr("disabled", true);
		$( "#updateBtn" ).prop("value", "Processing...");
	}
	if (document.getElementById("registerBtn") != null){
		$( "#registerBtn" ).attr("disabled", true);
		$( "#registerBtn" ).prop("value", "Processing...");
	}
	
	$( "#loading" ).show();
	
	document.registerForm.submit();
	
};


function checkExist(obj){

	var current = $("#"+obj+"_Current").val();
	var newInput = $("#"+obj).val();
	$("#checkingSpan_"+obj).html("");
	
	if(trim(current).length > 0) { //profile
		if (current == newInput) {
			//do nothing
		} else {
			startCheck(obj);
		}
	} else { //register
		startCheck(obj);
	}
	
};

function startCheck(obj) {

	var objvalue = $("#"+obj).val();
		
	if(obj=="email") {
		validRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i;
		if(trim(objvalue).search(validRegExp) == -1){
			$("#checkingSpan_"+obj).html("<span style='padding: 0 1px; font-style: normal; font-size: 11px; line-height: 15px; color: #ee9393;'>Invalid format.</span>");
			return false;
		}
	}
	
	if(obj=="userId") {
		if(trim(obj).length < 3){
			return false;
		}
	}
	
	$("#checkingSpan_"+obj).html("<i class='fa fa-circle-o-notch fa-spin'></i>");
	
	$.ajax({
		type: 'POST',
		url: './logon?actionType=isExist&' + obj + '=' + objvalue + '&timeStamp=' + new Date().getTime(),
		
		success: function(response) {
			
			$("#checkingSpan_"+obj).html("");
			$("#"+obj+"_Exist").val("");
			
			if(response == "Y"){
				$("#checkingSpan_"+obj).html("<span style='padding: 0 1px; font-style: normal; font-size: 11px; line-height: 15px; color: #ee9393;'>It has been taken</span>");
				$("#"+obj+"_Exist").val("Y");
			} else if(response == "N") {
				$("#checkingSpan_"+obj).html("<i class='fa fa-check-circle green'></i>")
				$("#"+obj+"_Exist").val("N");
			} else {
				$("#checkingSpan_"+obj).html("");
			}
			
		} 
	});

}
