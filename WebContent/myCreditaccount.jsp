<%@page import="com.iposb.i18n.*,com.iposb.my.*,com.iposb.area.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	//String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(MyController.OBJECTDATA);
	MyDataModel myData = new MyDataModel();
	if(data != null && data.size() > 0){
		myData = (MyDataModel)data.get(0);
		errmsg = myData.getErrmsg();
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	if(priv < 1){
		String url = "./login?returnUrl=./creditaccount";
		response.sendRedirect(url);
        return;
	}
	
	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("insertcreditaccount") || actionType.equals("updatecreditaccount"))){
		addEdit = true;
		list = false;
	} else {
		addEdit = false;
		list = true;
	}
%>

<head>
    <title><%=Resource.getString("ID_LABEL_CREDITACCOUNT",locale)%> - iPosb Logistic</title>

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
				
				<jsp:include page="mySidebar.jsp?tab=creditaccount" />
				
				<div class="col-md-9">
				
					<% 
						String btnText = "";
						if(actionType.equals("insertcreditaccount")){
							out.println("<h3>"+Resource.getString("ID_LABEL_ADDCREDITACCOUNT",locale)+"</h3>"); 
							btnText = Resource.getString("ID_LABEL_CONFIRMCREATE",locale);
						} else if(actionType.equals("updatecreditaccount")){
							out.println("<h3>"+Resource.getString("ID_LABEL_EDITCREDITACCOUNT",locale)+"</h3>");
							btnText = Resource.getString("ID_LABEL_UPDATE",locale);
						} else {
							out.println("<h3>"+Resource.getString("ID_LABEL_CREDITACCOUNT",locale)+"</h3>");
						}
					%>
					
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
					
					<%
						if(addEdit){
					%>
					
						<form id="createForm" name="createForm" method="post" action="./my" class="sky-form">
							<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
							<input type="hidden" id="verify" name="verify" value="<%=myData.getVerify() %>">
							<input type="hidden" id="useLocale" name="useLocale" value="<%=locale %>">
							
							<fieldset>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_AREA",locale)%> </label>
		                            <label for="aid" class="input">
		                                <select class="form-control" id="aid" name="aid">
			                                <% 
				                                if(areaData != null && !areaData.isEmpty()){
													for(int i = 0; i < areaData.size(); i ++){
														aData = areaData.get(i);
			                                %>
												<option value="<%=aData.getAid() %>" <% if(myData.getAid()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
											<% } } %>
										</select>
		                            </label>
		                        </section>
		                        
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_CONTACTNAME",locale)%> </label>
		                            <label for="contactName" class="input">
		                                <input type="text" id="contactName" name="contactName" value="<%=myData.getContactName() %>" maxlength="255">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_PHONE",locale)%> </label>
		                            <label for="contactPhone" class="input">
		                                <input type="text" id="contactPhone" name="contactPhone" value="<%=myData.getContactPhone() %>" maxlength="50">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_ADDRESS",locale)%> </label>
		                            <label for="contactAddress" class="input">
		                                <input type="text" id="contactAddress" name="contactAddress" value="<%=myData.getContactAddress() %>" maxlength="255">
		                            </label>
		                        </section>

		                    </fieldset>

							<footer>
							
								<p>&nbsp;</p>
								<button id="favBtn" name="favBtn" class="btn btn-orange" type="submit"><%=btnText %></button>	
								<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
		                    </footer>
							
						</form>
						
						
					<% } else if(list){ %>
					
						<div class="row">
							<div class="col-md-6 col-sm-6"></div>
							<div class="col-md-6 col-sm-6">
								<div class="pull-right"><a href="./addCreditaccount"><button id="addCreditaccount" class="btn btn-primary" type="button"><%=Resource.getString("ID_LABEL_ADDCREDITACCOUNT",locale)%></button></a></div>
							</div>
						</div>
					
						<div class="single-item single-item-content">
					
							<div class="item-details">
								<ul class="list-unstyled">
								
									<% 
										if(data != null && data.size() > 0){
											for(int i = 0; i < data.size(); i++) {
												myData = (MyDataModel)data.get(i);
									%>
										<li>
											<%=(i+1) %>. <a href="./editCreditaccount-<%=myData.getVerify() %>"><%=myData.getContactName() %></a> <span class="pull-right"> <a href="./editCreditaccount-<%=myData.getVerify() %>"><button id="edit" class="btn btn-warning" type="button"><%=Resource.getString("ID_LABEL_EDIT",locale)%></button></a></span>
											<div class="clearfix"></div>
										</li>
										
									<% } } else { %>
									
										<li>no record found<div class="clearfix"></div></li>
										
									<% }  %>
								</ul>
							</div>
						
						</div>
					
					<% } %>
					
					


				</div>
				
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript">
	
		jQuery(document).ready(function() {
			if("<%=result %>" == "updatecreditaccountSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_SAVESUCCESS",locale)%>");
			} else if("<%=result %>" == "createcreditaccountSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_CREATESUCCESS",locale)%>");
			} else if ("<%=result %>" == "updatecreditaccountFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_SAVEFAILED",locale)%>");
			} else if ("<%=result %>" == "createcreditaccountFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_CREATEFAILED",locale)%>");
			}
			
		});
	</script>
</g:compress>	
	
</body>

</html>