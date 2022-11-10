<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,java.util.HashMap,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%

	String email = request.getParameter("email") == null ? "" : request.getParameter("email").toString();
	String verify = request.getParameter("verify") == null ? "" : request.getParameter("verify").toString();
	String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString();
	String resend = request.getParameter("resend") == null ? "" : request.getParameter("resend").toString();

	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel userData = new LogonDataModel();
	if(data != null && data.size() > 0){
		userData = (LogonDataModel)data.get(0);
	}
	
	
	if(priv > 0){ 
		String url = "./profile";
		if(role.equals("staff")) {
			url = "./cp";
		} else if(role.equals("partner")) {
			url = "./cpPartner";
		}
		response.sendRedirect(url);
        return;
	}
	
	if(email.trim() == ""){
		email = userId;
	}
	
%>

<head>
    <title>Activate Account - iPosb Logistic</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->

    <div class="inner-page padd" style="margin-bottom:200px">
			
			<div class="container">

				<div class="row">
								
					<% if(role.equals("staff")||role.equals("partner")) {%>
						<h3>Your account is waiting for verification from our staff.</h3>
					<% }else {%>
						<h3>Verification email has been sent to <strong><%=email %></strong>.</h3>
						<p>Have not received your verification email? <a href="./logon?actionType=sendVerifyEmail&userId=<%=email %>">Click here</a> to re-send email.</p>
					<% } %>
					
					<br><br>
					<div id="resendMsg" class="alert alert-success fade in alert-dismissable" style="display:none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="msg"></span>
					</div>
				</div>
			</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->
    

	<%@ include file="footer.jsp" %>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			
			if("<%=resend %>" == "success"){
				$("#resendMsg").show();
				$("#msg").html("<%=Resource.getString("ID_MSG_RESENDSUCCESS",locale)%>");
			} else {
				$("#resendMsg").hide();
				$("#msg").html("");
			}
			
		});
	</script>
	
</body>

</html>