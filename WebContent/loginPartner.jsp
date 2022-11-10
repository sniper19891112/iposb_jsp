<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String sessionMsg = session.getAttribute("sessionMsg") == null ? "" : session.getAttribute("sessionMsg").toString();

	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel userData = new LogonDataModel();
	if(data != null && data.size() > 0){
		userData = (LogonDataModel)data.get(0);
	}
	
	if(currentUser.isAuthenticated()){ //已經login
		String url = "./partnerCP";
	
		if(priv >= 5){
			url = "./cp";
		}else if(priv >= 0 && priv <= 2){
			url = "./my"; 
		}
		
		response.sendRedirect(url);
        return;
	}else if(currentUser.isRemembered()){
		if(priv != 3 || priv != 4){
			String url = "./";
			
			if(priv >= 5){
				url = "./stafflogin";
			}else if(priv >= 0 && priv <= 2){
				url = "./login"; 
			}
			
			response.sendRedirect(url);
	        return;
		}
	}
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_PARTNERLOGIN",locale)%></title>

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
			<h2 class="white"><%=Resource.getString("ID_LABEL_PARTNERLOGIN",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_PARTNERLOGIN",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
	
    <!--=== Content Part ===-->

    <div class="inner-page padd">	
    	<div class="row">
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">	
					
				<div class="booking-form" style="margin-top:48px;margin-bottom:28px">
					<h3><%=Resource.getString("ID_LABEL_LOGINTOACCOUNT",locale)%></h3>
					<p><%=Resource.getString("ID_LABEL_INTERESTJOINUS",locale)%></p>	
				</div>
					
                <form id="loginPageForm" class="form-horizontal" name="loginPageForm" method="post" action="./partner">
					<input type="hidden" id="actionType" name="actionType" value="partnerlogin" />
					<input type="hidden" id="returnUrl" name="returnUrl" value="<%=returnUrl %>" />
					
					<%if(currentUser.isRemembered()){ %>
						<div class="alert alert-danger fade in alert-dismissable">
							<span>You are not authenticated, please login again.</span>
						</div>
					<%} %>
					
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
					
                    
					<div class="form-group">
						<label for="userId" class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_EMAIL",locale)%></label>
						<div class="col-md-8">
							<input type="text" class="form-control" id="userId" name="userId" placeholder="<%=Resource.getString("ID_LABEL_EMAIL",locale)%>">
						</div>
					</div>            
					<div class="form-group">
						<label for="password" class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_PASSWORD",locale)%></label>
						<div class="col-md-8">
							<input type="password" class="form-control" id="passwd" name="passwd" placeholder="<%=Resource.getString("ID_LABEL_PASSWORD",locale)%>">
						</div>
					</div>          

                    <div class="row">
                        <div class="col-md-4">
                            
                        </div>
                        <div class="col-md-8">
                        	<label class="checkbox" style="margin-left: 22px"><input type="checkbox" id="stayloggedin" name="stayloggedin"> <%=Resource.getString("ID_LABEL_STAYLOGGEDIN",locale)%></label>
                            <button class="btn btn-orange pull-right" id="loginBtn"><%=Resource.getString("ID_LABEL_LOGIN",locale)%></button>                        
                        </div>
                    </div>

                    <hr>

                    <h4><%=Resource.getString("ID_LABEL_FORGOTPASSWORD",locale)%></h4>
                    <p><%=Resource.getString("ID_LABEL_RESETPASSWORDTXT",locale)%></p>
					<div class="modal fade" id="resetPass" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-sm">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel"><%=Resource.getString("ID_LABEL_RESETPASSWORD",locale) %></h4>
								</div>
								<div class="modal-body">
									<div>
										<div class="margin-bottom-20"><%=Resource.getString("ID_LABEL_FORGOTPASSWORDTXT",locale) %></div>
										<%=Resource.getString("ID_LABEL_EMAIL",locale) %><br />
										<input class="form-control" type="text" id="myEmail" name="myEmail" size="20" maxlength="100">
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" id="thinkAgainBtn" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
									<button type="button" class="btn btn-warning" id="confirmResetBtn" onclick="resendPassword()"><%=Resource.getString("ID_LABEL_SENDNEWPASSWORD",locale) %></button>
									<span id="loading"></span>
									<button type="button" class="btn btn-default pull-right" id="closeBtn" data-dismiss="modal" style="display:none"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
								</div>
							</div>
						</div>
					</div>
                </form>            
            </div>
        </div><!--/row-->
    </div><!--/container-->	
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			if("<%=sessionMsg %>" == "loginFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_LOGINFAILED",locale)%>");
			} else if("<%=sessionMsg %>" == "logoutSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_LOGOUTSUCCESS",locale)%>");
			}
			
			<% session.removeAttribute("sessionMsg"); %>
		});

		
		$(function() {
			$( "#loginBtn" ).click(function() { document.loginPageForm.submit(); return false; });
		});
		
		function resendPassword(){
			validRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i;
			var email = $("#myEmail").val();
			
			if(trim(email).search(validRegExp) != -1){
				$("#thinkAgainBtn").hide();
				$("#confirmResetBtn").hide();
				$("#loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
				
				$.ajax({
					type: 'POST',
					url: './partner',
					data:{    
						actionType:"sendNewPassword",
						role: "partner",
						email: email,
						lang: "<%=locale %>"
			   		},  
			   		
					success: function(response) {
						if(response == "OK"){
							$("#loading").html("<span class=\"text-highlights green\"><%=Resource.getString("ID_LABEL_PASSWDSEND",locale)%></span>&nbsp;&nbsp;&nbsp;");
							$("#closeBtn").show();
						} else {
							$("#loading").html("<span class=\"text-highlights\"><%=Resource.getString("ID_LABEL_PASSWDSENDFAILED",locale)%></span>&nbsp;&nbsp;&nbsp;");
						}
					}
			   		
				});
				
			}
		};
		
		
	</script>
</g:compress>	

</body>

</html>