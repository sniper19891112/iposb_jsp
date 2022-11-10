
		
		function checkExist(obj){
			
			var newInput = document.getElementById(obj).value;
			
			if(newInput.length > 1) {
				document.getElementById("checkingSpan_"+obj).innerHTML = "";
				startCheck(obj);
			}
			
		};

		function startCheck(obj) {

			var objvalue = document.getElementById(obj).value;
				
			if(obj=="userId") {
				if(trim(obj).length < 3){
					return false;
				}
			}

			document.getElementById("checkingSpan_"+obj).innerHTML = "<i class='fa fa fa-spinner fa-spin'></i>";
			var url = encodeURI("./partner?actionType=isExist&" + obj + "=" + objvalue + "&timeStamp=" + new Date().getTime());
			ajaxHandlerExist(url, obj);

		}

		var xmlHttp;

		function createXMLHttpRequestExist(){
			if(window.XMLHttpRequest){
				xmlHttp = new XMLHttpRequest();
			}
			else if(window.ActiveXObject){
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
		};


		function ajaxHandlerExist(url, obj){
		    createXMLHttpRequestExist();
		    xmlHttp.open("POST", url);
		    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlHttp.send();
		    xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyState == 4) {
					if(xmlHttp.status == 200) {
						document.getElementById("checkingSpan_"+obj).innerHTML = "";
						document.getElementById(obj+"_Exist").value = "";
						
						var rText = trim(xmlHttp.responseText);
						if(rText == "Y"){
							document.getElementById("checkingSpan_"+obj).innerHTML = "<span style='padding: 0 1px; font-style: normal; font-size: 11px; line-height: 15px; color: #ee9393;'>It has been taken</span>";
							document.getElementById(obj+"_Exist").value = "Y";
						} else if(rText == "N") {
							document.getElementById("checkingSpan_"+obj).innerHTML = "<i class='fa fa fa-check-circle'></i>";
							document.getElementById(obj+"_Exist").value = "N";
						} else {
							document.getElementById("checkingSpan_"+obj).innerHTML = "";
						}
					}
				}
			};
			
		};
		
		function submitCheck(){
			$(function(){
				var target = $("#validateInput");
				
				//validate email
				validRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i;
				var strEmail = document.getElementById("email").value;
				
				if(trim(strEmail).search(validRegExp) == -1){
					var text = "Email: Not a valid email";
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
				
				
				//validate ename
				var ename = document.getElementById("ename").value;
				if(trim(ename).length < 1){
					var text = "Company Official Name: Please input your company official name";
					showHintText(target, text);
			    	return false;
				}
				
				//validate contactPersons
				var contactPerson = document.getElementById("contactPerson").value;
				if(trim(contactPerson).length < 1){
					var text = "Contact Person: Please input contact person";
					showHintText(target, text);
			    	return false;
				}
				
				//validate phone
				var phone = document.getElementById("phone").value;
				if(trim(phone).length < 1){
					var text = "Contact Number: Please input your phone number";
					showHintText(target, text);
			    	return false;
				}

				
				//Agreement
				var agreement = document.getElementById("agree");
				if(!agreement.checked){
					var text = "You must read and agree to the Terms of Service before continue.";
					showHintText(target, text);
					return false;
				}
				
				document.registerForm.submit();
			});
		};
		
		