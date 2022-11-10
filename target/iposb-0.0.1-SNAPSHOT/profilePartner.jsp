<%@page import="com.iposb.i18n.*,com.iposb.partner.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();
	String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString();

	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(PartnerController.OBJECTDATA);
	PartnerDataModel logonData = new PartnerDataModel();
	if(data != null && data.size() > 0){
		logonData = (PartnerDataModel)data.get(0);
		errmsg = logonData.getErrmsg();
	}
	
	/* if(priv<0){
		String url = "./login?returnUrl=./profile";
		if(role.equals("staff")) {
			url = "./stafflogin?returnUrl=./staffprofile";
		}
		response.sendRedirect(url);
        return;
	} */
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_PROFILE",locale)%></title>

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
		               		<li class="list-group-item active"><a href="./companyProfile"><i class="fa fa-user fa-lg blue"></i> <%=Resource.getString("ID_LABEL_COMPANYPROFILE",locale)%></a></li>
							<li class="list-group-item"><a href="./changepass"><i class="fa fa-lock fa-lg blue"></i> <%=Resource.getString("ID_LABEL_CHANGEPASSWORD",locale)%></a></li>
		                </ul>
		            </div>
		        </div>
				
				<div class="col-md-9">
				
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="accountStatus" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="privStatus"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
							
					<form id="registerForm" name="registerForm" method="post" action="./partner" class="sky-form">
						<input type="hidden" id="actionType" name="actionType">
						<input type="hidden" id="role" name="role" value="<%=role %>">
						<input type="hidden" id="useLocale" name="useLocale" value="<%=locale %>">
						<input type="hidden" id="email_Exist" name="email_Exist">
						<input type="hidden" id="email_Current" name="email_Current" value="<%=logonData.getEmail() %>">
						
						<fieldset>
													
							<section>
								<label class="label"><%=Resource.getString("ID_LABEL_EMAIL",locale)%> <font color="red">*</font></label>
	                            <label for="email" class="input">
	                                <input type="text" id="email" name="email" value="<%=logonData.getEmail() %>" maxlength="120" <%if(priv != 0){out.print(" readonly disabled ");}%> >
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_OFFICIALNAME",locale)%> <font color="red">*</font></label>
	                            <label for="ename" class="input">
	                                <input type="text" id="ename" name="ename" value="<%=logonData.getEname() %>" maxlength="50">
	                            </label>
	                        </section>
		                    
		                    <section>
								<label class="label"><%=Resource.getString("ID_LABEL_CONTACTPERSON",locale)%> <font color="red">*</font></label>
	                            <label for="contactPerson" class="input">
	                                <input type="text" id="contactPerson" name="contactPerson" value="<%=logonData.getContactPerson() %>" maxlength="30">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%> <font color="red">*</font></label>
	                            <label for="phone" class="input">
	                                <input type="text" id="phone" name="phone" value="<%=logonData.getPhone() %>" maxlength="30">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_WEBSITE",locale)%></label>
	                            <label for="website" class="input">
	                                <input type="text" id="website" name="website" value="<%=logonData.getWebsite() %>" maxlength="100">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_OFFICIALEMAIL",locale)%></label>
	                            <label for="officialEmail" class="input">
	                                <input type="text" id="officialEmail" name="officialEmail" value="<%=logonData.getOfficialEmail() %>" maxlength="120">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_ADDRESS",locale)%></label>
	                            <label for="address" class="input">
	                                <input type="text" id="address" name="address" value="<%=logonData.getAddress() %>" maxlength="100">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_COMPANYLICENSE",locale)%></label>
	                            <label for="companyLicense" class="input">
	                                <input type="text" id="companyLicense" name="companyLicense" value="<%=logonData.getCompanyLicense() %>" maxlength="20">
	                            </label>
	                        </section>

	                        
		                    <p>&nbsp;</p>
	                        
	                    </fieldset>
	                    
						<footer>
						
							<p>&nbsp;</p>
							<button id="profileBtn" name="profileBtn" class="btn btn-orange" type="submit"><%=Resource.getString("ID_LABEL_UPDATE",locale) %></button>	
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
	<script type="text/javascript">
		jQuery(document).ready(function() {
        	
			if("<%=logonData.getErrmsg() %>" != ""){
				if("<%=logonData.getErrmsg() %>" == "accountUpdated"){
					$( "#successAlert" ).show();
					document.getElementById("successMsg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%>";
				} 
				else if("<%=logonData.getErrmsg() %>" == "updateFailed"){
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATEFAILED",locale)%>";
				}
				else if("<%=logonData.getErrmsg() %>" == "userIdEmailExist"){
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=Resource.getString("ID_MSG_USERIDEMAILEXIST",locale)%>";
				}
				else {
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=logonData.getErrmsg() %>";
				}
			}
			
			<% if(priv == 0){%>
				$( "#accountStatus" ).show();
				document.getElementById("privStatus").innerHTML = "Your account is waiting for verification. "+"<a href='./logon?actionType=sendVerifyEmail&userId=<%=userId %>'>Click here</a> to resend email.";
			<%} %>
		});
		
		$(function() {
			$( "#profileBtn" ).click(function() { submitCheck('updateProfile'); return false; });
		});
		
		function submitCheck(obj) {
			document.getElementById("actionType").value = obj;
			
			if (document.getElementById("profileBtn") != null){
				$( "#profileBtn" ).attr("disabled", true);
				$( "#profileBtn" ).prop("value", "Processing...");
			}
			
			$( "#loading" ).show();
			
			document.registerForm.submit();
			
		}
		

	</script>
</g:compress>
	
</body>

</html>