<%@page import="com.iposb.privilege.*"%>
<%
	//different service has different file
	String uri = request.getRequestURI().replace(".jsp", "");
	String pageName = uri.substring(uri.lastIndexOf("/")+1);
	
%>
<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>
	
	<ul class="nav nav-list">
		
		<% if(priv == 3){ %>
		
		
			<li class="<%=pageName.equals("agentCpConsignment") ? "active" : "" %>">
				<a href="./agentCpConsignment">
					<i class="fa fa-file-text fa-1g"></i> &nbsp;
					<span class="menu-text"> <%=Resource.getString("ID_LABEL_CONSIGNMENT",locale)%> </span>
				</a>
			</li>

			<%
				String open = "";
				if(pageName.equals("cpStation")||pageName.equals("cpJoinconsignment")||pageName.equals("cpPickupRequest")){
					open = "class=\"active open\"";
				}
			%>
			<li <%=open %>>
				<a href="#" class="dropdown-toggle">
					<i class="fa fa-home"></i> &nbsp;
					<span class="menu-text"> <%=Resource.getString("ID_LABEL_STATIONWORK",locale)%> </span>

					<b class="arrow icon-angle-down"></b>
				</a>

				<ul class="submenu">
					<li class="<%=pageName.equals("cpStation") ? "active" : "" %>">
						<a href="./agentStation">
							<i class="icon-double-angle-right"></i> &nbsp;
							<%=Resource.getString("ID_LABEL_MISC",locale)%>
						</a>
					</li>
					
					<li class="<%=pageName.equals("cpJoinconsignment") ? "active" : "" %>">
						<a href="./agentJoinConsignment">
							<i class="icon-double-angle-right"></i> &nbsp;
							<%=Resource.getString("ID_LABEL_JOINCONSIGNMENT",locale)%>
						</a>
					</li>
													
				</ul>
			</li>
		<% } %>
		
		<% if(priv == 4){ %>
			<li class="<%=pageName.equals("partnerCpConsignment") ? "active" : "" %>">
				<a href="./partnerCpConsignment">
					<i class="fa fa-file-text fa-1g"></i> &nbsp;
					<span class="menu-text"> <%=Resource.getString("ID_LABEL_CONSIGNMENT",locale)%> </span>
				</a>
			</li>
		
			<li class="<%=pageName.equals("partnerDeliveryReport") ? "active" : "" %>">
				<a href="./partnerDeliveryReport">
					<i class="fa fa-file-text-o fa-1g"></i> &nbsp;
					<span class="menu-text"> <%=Resource.getString("ID_LABEL_DELIVERYREPORT",locale)%> </span>
				</a>
			</li>
		<% } %>

	</ul><!-- /.nav-list -->
	
	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
	</div>

<g:compress>	
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}			
	</script>
</g:compress>
</div>