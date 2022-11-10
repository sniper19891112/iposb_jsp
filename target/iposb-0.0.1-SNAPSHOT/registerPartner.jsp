<%@page import="com.iposb.i18n.*,com.iposb.partner.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String errmsg = "";
	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	ArrayList data = (ArrayList)request.getAttribute(PartnerController.OBJECTDATA);
	PartnerDataModel userData = new PartnerDataModel();
	if(data != null && data.size() > 0){
		userData = (PartnerDataModel)data.get(0);
		errmsg = userData.getErrmsg();
	}
	
	if(!userId.trim().equals("")){ //already login
		String url = "./cp";
		response.sendRedirect(url);
        return;
	}
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_REGISTERPARTNERACCOUNT",locale)%></title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

</head>	

<body>

	<jsp:include page="header.jsp" />
	
	<div class="banner padd">
		<div class="container">
			<!-- Image -->
			<img class="img-responsive" src="assets/img/crown-white.png" alt="" />
			<!-- Heading -->
			<h2 class="white"><%=Resource.getString("ID_LABEL_REGISTER",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_REGISTERPARTNERACCOUNT",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
	
    <!--=== Content Part ===-->

    <div class="inner-page padd">	
    	<div class="row">
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">	
					
				<div class="booking-form">
					<h3><%=Resource.getString("ID_LABEL_REGISTERPARTNERACCOUNT",locale)%></h3>
					<p><%=Resource.getString("ID_LABEL_ALREADYJOIN",locale)%></p>
					<hr>
					
					<div id="result"></div>

					<form id="registerForm" name="registerForm" method="post" action="./partner" role="form">
						<input type="hidden" id="actionType" name="actionType" value="register">
						<input type="hidden" id="formValues" name="formValues">
						<input type="hidden" id="captchatoken" name="captchatoken">
						<!-- 
						<input type="hidden" id="email_Exist" name="email_Exist">
						<input type="hidden" id="email_Current" name="email_Current" value="<%=userData.getEmail_Current() %>">
						 -->
					
						<label><%=Resource.getString("ID_LABEL_EMAIL",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="email" id="email" name="email" maxlength="120" value="<%=userData.getEmail() %>" />
						</div>

						<label><%=Resource.getString("ID_LABEL_PASSWORD",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="password" id="passwd" name="passwd" maxlength="12" />
						</div>
						
						<hr>

						<label><%=Resource.getString("ID_LABEL_OFFICIALNAME",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="text" id="ename" name="ename" maxlength="50" value="<%=userData.getEname() %>" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_ABBREVIATION",locale)%></label>
						<div class="form-group">
							<input class="form-control" type="text" id="cname" name="cname" maxlength="15" value="<%=userData.getCname() %>" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_CONTACTPERSON",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="text" id="contactPerson" name="contactPerson" maxlength="30" value="<%=userData.getContactPerson() %>" />
						</div>

						<label><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="text" id="phone" name="phone" maxlength="30" value="<%=userData.getPhone() %>" />
						</div>

						<label><%=Resource.getString("ID_LABEL_WEBSITE",locale)%> </label>
						<div class="form-group">
							<input class="form-control" type="text" id="website" name="website" maxlength="100" value="<%=userData.getWebsite() %>" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_OFFICIALEMAIL",locale)%> </label>
						<div class="form-group">
							<input class="form-control" type="text" id="officialEmail" name="officialEmail" maxlength="120" value="<%=userData.getOfficialEmail() %>" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_ADDRESS",locale)%> </label>
						<div class="form-group">
							<input class="form-control" type="text" id="address" name="address" maxlength="100" value="<%=userData.getAddress() %>" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_COMPANYLICENSE",locale)%> </label>
						<div class="form-group">
							<input class="form-control" type="text" id="companyLicense" name="companyLicense" maxlength="20" value="<%=userData.getCompanyLicense() %>" />
						</div>
						
						<!--
						<label><%=Resource.getString("ID_LABEL_GSTNO",locale)%> </label>
						<div class="form-group">
							<input class="form-control" type="text" id="gst" name="gst" maxlength="15" value="<%=userData.getGst() %>" />
						</div>
						-->
						
						<hr>
						
						<label></label>
						<div class="form-group">
							<input type="checkbox" id="agree" name="agree"> <%=Resource.getString("ID_LABEL_READANDAGREED",locale)%>
						</div>
						
						<div id="validateInput" class="alert alert-danger fade in alert-dismissable" style="display:none"></div>

						<button id="registerBtn" class="btn btn-orange" onclick="return false"><%=Resource.getString("ID_LABEL_REGISTER",locale)%></button>&nbsp;
						
						<p>&nbsp;</p>
						
					</form>
				</div>
        
            </div>
        </div><!--/row-->
    </div><!--/container-->	
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
	<script src="https://www.google.com/recaptcha/api.js?render=6Le5xeIZAAAAAFwZ4n2_rtC84IRfjDAYtUVftS5G"></script>
	
<g:compress>
	
	<script type="text/javascript" src="./assets/js/registerPartner.js"></script>
	<script type="text/javascript">
	
		jQuery(document).ready(function() {
			
			<%
				if(errmsg.length() > 0){
					if(errmsg.equals("registerSuccess")){
						out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_JOINSUCCESS",locale) + "</p></div>\");");
						out.println("$( \".registerForm\" ).hide();");
					} else if(errmsg.equals("noData")){
						out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NODATA",locale) + "</p></div>\");");
					} else if(errmsg.equals("userIdEmailExist")){
						out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_USERIDEMAILEXIST",locale) + "</p></div>\");");
					} else if(errmsg.equals("noConnection")){
						out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
					} else if(errmsg.equals("secureCodeNull")){
						out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_INVALIDSECURECODE",locale) + "</p></div>\");");
					} else {
						out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
					}
				}
			%>
			
		});
		
		$(function() {
			$( "#registerBtn" ).click(function() {
		        grecaptcha.ready(function() {
		          grecaptcha.execute('6Le5xeIZAAAAAFwZ4n2_rtC84IRfjDAYtUVftS5G', {action: 'submit'}).then(function(token) {
						$('#captchatoken').val(token);
						submitCheck('register'); 
						return false; 
		          });
		        });
			});
		});
		
	</script>
</g:compress>	
	
</body>

</html>