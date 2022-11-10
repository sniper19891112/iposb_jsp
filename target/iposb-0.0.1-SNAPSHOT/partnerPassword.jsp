<%@page import="com.iposb.i18n.*,com.iposb.partner.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(PartnerController.OBJECTDATA);
	PartnerDataModel userData = new PartnerDataModel();
	if(data != null && data.size() > 0){
		userData = (PartnerDataModel)data.get(0);
	}
	
	if(!currentUser.isAuthenticated()){
		String url = "./partnerlogin?returnUrl=./changepass";	
		response.sendRedirect(url);
        return;
	}
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_CHANGEPASSWORD",locale)%> - IPOSB Logistic</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->
			

    <div class="inner-page padd">
			
		<div class="container">

			<div class="row">
				
				<div class="single-item single-item-content">
					<div class="col-md-3">                
		                <ul class="list-group sidebar-nav-v1" id="sidebar-nav">
		               		<li class="list-group-item"><a href="./companyProfile"><i class="fa fa-user fa-lg blue"></i> <%=Resource.getString("ID_LABEL_COMPANYPROFILE",locale)%></a></li>
							<li class="list-group-item active"><a href="./changepass"><i class="fa fa-lock fa-lg blue"></i> <%=Resource.getString("ID_LABEL_CHANGEPASSWORD",locale)%></a></li>
		                </ul>
		            </div>
		        </div>
				
				<div class="col-md-9">
				
					<h3><%=Resource.getString("ID_LABEL_CHANGEPASSWORD",locale) %></h3>
					
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
					
					
					<form id="passwordForm" name="passwordForm" method="post" action="./partner" class="sky-form">
						<input type="hidden" id="actionType" name="actionType" value="updatepass">
						
						<fieldset>
	                        
							<section>
								<label class="label"><%=Resource.getString("ID_LABEL_OLDPASSWORD",locale)%> <font color="red">*</font></label>
	                            <label for="passwdOld" class="input">
	                                <input type="password" id="passwdOld" name="passwdOld" maxlength="12">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_NEWPASSWORD",locale)%> <font color="red">*</font></label>
	                            <label for="password" class="input">
	                                <input type="password" id=passwd name="passwd" maxlength="12">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_RETYPENEWPASSWORD",locale)%> <font color="red">*</font></label>
	                            <label for="passwd2" class="input">
	                                <input type="password" id="passwd2" name="passwd2" maxlength="12">
	                            </label>
	                        </section>
	                        
	                    </fieldset>
	                    
						<footer>
						
							<div id="validateInput" class="alert alert-danger fade in alert-dismissable" style="display:none"></div>
						
							<p>&nbsp;</p>
							<button id="passwordBtn" name="passwordBtn" class="btn btn-orange" type="button"><%=Resource.getString("ID_LABEL_UPDATE",locale) %></button>	
							<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
	                    </footer>
						
					</form>


				</div>
				
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript" src="./assets/js/profilepic.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			
			if("<%=userData.getErrmsg() %>" != ""){
				if("<%=userData.getErrmsg() %>" == "updateSuccess"){
					$( "#successAlert" ).show();
					document.getElementById("successMsg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "accountUpdated"){
					$( "#successAlert" ).show();
					document.getElementById("successMsg").innerHTML = "<%=Resource.getString("ID_MSG_ACCOUNTUPDATED",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "updateFailed"){
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATEFAILED",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "wrongPassword"){
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=Resource.getString("ID_MSG_OLDPASSWORDNOTMATCH",locale)%>";
				}
				else {
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=userData.getErrmsg() %>";
				}
			}

		});
		
		$(function() {
			
			var target = $( "#validateInput" );
			
			$( "#passwordBtn" ).click(function() { 

				var strPasswdOld = document.getElementById("passwdOld");
				var strPasswdNew = document.getElementById("passwd");

				if(strPasswdOld != null && strPasswdNew != null) {
				
					if(trim(strPasswdOld.value).length < 1){
						var text = "<%=Resource.getString("ID_MSG_PLSINPUTCURRENTPASSWORD",locale)%>";
						showHintText(target, text);
						return false;
					}
					
					if(trim(strPasswdNew.value).length > 0 && trim(strPasswdNew.value).length < 4){
						var text = "<%=Resource.getString("ID_MSG_PASSWORDREQUIREMENT",locale)%>";
						showHintText(target, text);
						return false;
					}
				}
				
				
				var strPasswd1 = document.getElementById("passwd");
				var strPasswd2 = document.getElementById("passwd2");
				
				if(strPasswd1 != null && strPasswd2 != null) {
				
					if(trim(strPasswd1.value) != trim(strPasswd2.value)){
						var text = "<%=Resource.getString("ID_MSG_PLSVERIFYPASSWORD",locale)%>";
						showHintText(target, text);
						return false;
					}
					
					if((trim(strPasswd1.value).length < 4) || (trim(strPasswd2.value).length < 4)){
						var text = "<%=Resource.getString("ID_MSG_PASSWORDREQUIREMENT",locale)%>";
						showHintText(target, text);
						return false;
					}
				
				}
				
				
				$( "#loading").html("<i class=\"fa fa-circle-o-notch fa-spin\"></i> <%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
				
				document.passwordForm.submit();
				return false; 
				
			});
		});

	</script>
</g:compress>	
	
</body>

</html>