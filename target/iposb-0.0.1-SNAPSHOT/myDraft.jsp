<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	ArrayList data = (ArrayList)request.getAttribute(ConsignmentController.OBJECTDATA);
	ConsignmentDataModel consignmentData = new ConsignmentDataModel();
	
	if(priv < 1){
		String url = "./login?returnUrl=./draft";
		response.sendRedirect(url);
        return;
	}
%>

<head>
    <title><%=Resource.getString("ID_LABEL_MYDRAFT",locale)%> - iPosb Logistic</title>

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
				
				<jsp:include page="mySidebar.jsp?tab=draft" />
				
				<div class="col-md-9">

					<h3><%=Resource.getString("ID_LABEL_MYDRAFT",locale)%></h3>
					
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
					
					<div class="single-item single-item-content">
					
						<div class="item-details">
							<ul class="list-unstyled">
							
								<% 
									if(data != null && data.size() > 0){
										for(int i = 0; i < data.size(); i++) {
											consignmentData = (ConsignmentDataModel)data.get(i);
								%>
									<li>
										[<%=consignmentData.getModifyDT() %>] <a href="./editDraft-<%=consignmentData.getVerify() %>"><%=consignmentData.getReceiverName() %></a> <span class="pull-right"> <a href="./editDraft-<%=consignmentData.getVerify() %>"><button id="edit" class="btn btn-warning" type="button"><%=Resource.getString("ID_LABEL_EDIT",locale)%></button></a></span>
										<div class="clearfix"></div>
									</li>
									
								<% } } else { %>
								
									<li>no draft found<div class="clearfix"></div></li>
									
								<% }  %>
							</ul>
						</div>
					
					</div>


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
			if( ("<%=result %>" == "savedraftSuccess")||("<%=result %>" == "updatedraftSuccess") ){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_SAVESUCCESS",locale)%>");
			}

		});
		
	</script>
</g:compress>	
	
</body>

</html>