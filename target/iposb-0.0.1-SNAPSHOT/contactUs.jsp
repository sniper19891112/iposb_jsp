<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel userData = new LogonDataModel();
	if(data != null && data.size() > 0){
		userData = (LogonDataModel)data.get(0);
	}

%>

<head>
    <title><%=Resource.getString("ID_LABEL_CONTACTUS",locale)%> - iPosb Logistic</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->
    
    <div class="banner padd">
		<div class="container">
			<!-- Image -->
			<img class="img-responsive" src="assets/img/crown-white.png" alt="" />
			<!-- Heading -->
			<h2 class="white"><%=Resource.getString("ID_LABEL_CONTACTUS",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_CONTACTUS",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
			

    <div class="inner-page padd">
			
		<!-- Contact Us Start -->
		
		<div class="contactus">
			<div class="container">
				<div class="row">
					<div class="col-md-4">
						<!-- Contact Us content -->
						<div class="row">
							<div>
								<div class="contact-details">
									<h4>Address</h4>
									<i class="fa fa-map-marker br-red"></i> <span> Shop No.31, Block B4,<br /> Jalan BU2/1, Bandar Utama,<br /> 90000 Sandakan, Sabah.</span>
									<div class="clearfix"></div>
								</div>
								<div class="contact-details">
									<h4>Call</h4>
									<i class="fa fa-phone br-green"></i> <span> +60 89-271 863</span>
									<div class="clearfix"></div>
								</div>
								<div class="contact-details">
									<h4>Email</h4>
									<i class="fa fa-envelope-o br-lblue"></i> <span><a href="mailto:ask@iposb.com"> ask@iposb.com</a></span>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						
						<!-- Contact form -->
						<!-- 
						<div class="contact-form">
							<h3>Contact Form</h3>
							
							<form role="form">
								<div class="form-group">
									<input class="form-control" type="text" placeholder="<%=Resource.getString("ID_LABEL_NAME",locale)%>" />
								</div>
								<div class="form-group">
									<input class="form-control" type="email" placeholder="<%=Resource.getString("ID_LABEL_EMAIL",locale)%>" />
								</div>
								<div class="form-group">
									<textarea class="form-control" rows="3" placeholder="<%=Resource.getString("ID_LABEL_YOURMSG",locale)%>"></textarea>
								</div>
								<button class="btn btn-orange" type="submit"><%=Resource.getString("ID_LABEL_SENDENQUIRY",locale)%></button>
							</form>
						</div>
						-->
					</div>
					<div class="col-md-8">
						<!-- Map holder -->
						<div class="map-container">
							<!-- Google Map -->
							<iframe	src="https://maps.google.com/?ie=UTF8&amp;ll=5.865397,118.057316&amp;spn=0.815042,1.352692&amp;t=m&amp;z=15&amp;output=embed"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Contact Us End -->
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			if("<%=result %>" == "loginFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_LOGINFAILED",locale)%>");
			} else if("<%=result %>" == "logoutSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_LOGOUTSUCCESS",locale)%>");
			}
		});

		
		$(function() {
			$( "#loginBtn" ).click(function() { document.loginPageForm.submit(); return false; });
		});
		
	</script>
</g:compress>	
	
</body>

</html>