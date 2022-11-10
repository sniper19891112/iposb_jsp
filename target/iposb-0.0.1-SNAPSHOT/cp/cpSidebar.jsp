<%@page import="com.iposb.privilege.*"%>
<%
	//有分檔案：各服務有各自的檔案
	String uri = request.getRequestURI().replace(".jsp", "");
	String pageName = uri.substring(uri.lastIndexOf("/")+1);
	
%>
<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<button class="btn btn-purple btn-block" onClick="location.href='./cp'"><i class="ace-icon fa fa-barcode"></i> <%=Resource.getString("ID_LABEL_SCANBARCODE",locale)%></button>
	</div><!-- #sidebar-shortcuts -->
	
	<ul class="nav nav-list">
		<%
			String itemname = "";
			String link = "";
			ArrayList<PrivilegeDataModel> privData = PrivilegeBusinessModel.getItemsByPrivilege(priv);
			PrivilegeDataModel sidebarData = new PrivilegeDataModel();
			if(privData != null && privData.size() > 0){							
				for (int xx = 0; xx < privData.size(); xx++) {
					sidebarData = (PrivilegeDataModel)privData.get(xx);
					itemname = sidebarData.getName_enUS();
					link = sidebarData.getLink();
					if(locale.equals("zh_CN")){
						itemname = sidebarData.getName_zhCN();
					} else if(locale.equals("zh_TW")){
						itemname = sidebarData.getName_zhTW();
					}
					
						if(sidebarData.getIid() == 2) { //account
						
							String open = "";
							if(pageName.equals("cpAccount")||pageName.equals("cpAccountMaintain")||pageName.equals("cpAccountSettle")||pageName.equals("cpAccountInvoice")||pageName.equals("cpAccountPosState")){
								open = "class=\"active open\"";
							}
						
					%>

						<li <%=open %>>
							<a href="#" class="dropdown-toggle">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=itemname %> </span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li class="<%=pageName.equals("cpAccount")||pageName.equals("cpAccountMaintain") ? "active" : "" %>">
									<a href="./account">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_ACCOUNTSUMMARY",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpAccountSettle") ? "active" : "" %>">
									<a href="./settlement">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_SETTLEMENT",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpAccountInvoice") ? "active" : "" %>">
									<a href="./invoice">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_INVOICE",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpAccountPosState") ? "active" : "" %>">
									<a href="./posStatement">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_POSSTATEMENT",locale)%>
									</a>
								</li>
																
							</ul>
						</li>

				<%
					}
					
					else if(sidebarData.getIid() == 5) { //station
						
						String open = "";
						if(pageName.equals("cpStation")||pageName.equals("cpJoinconsignment")||pageName.equals("cpPickupRequest")){
							open = "class=\"active open\"";
						}
					
				%>

					<li <%=open %>>
						<a href="#" class="dropdown-toggle">
							<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
							<span class="menu-text"> <%=Resource.getString("ID_LABEL_STATIONWORK",locale)%> </span>

							<b class="arrow icon-angle-down"></b>
						</a>

						<ul class="submenu">
							<li class="<%=pageName.equals("cpStation") ? "active" : "" %>">
								<a href="./station">
									<i class="icon-double-angle-right"></i> &nbsp;
									<%=Resource.getString("ID_LABEL_MISC",locale)%>
								</a>
							</li>
							
							<li class="<%=pageName.equals("cpJoinconsignment") ? "active" : "" %>">
								<a href="./joinconsignment">
									<i class="icon-double-angle-right"></i> &nbsp;
									<%=Resource.getString("ID_LABEL_JOINCONSIGNMENT",locale)%>
								</a>
							</li>
							
							<li class="<%=pageName.equals("cpPickupRequest") ? "active" : "" %>">
								<a href="./cpPickup">
									<i class="icon-double-angle-right"></i> &nbsp;
									<%=Resource.getString("ID_LABEL_PICKUPREQUESTS",locale)%>
								</a>
							</li>
															
						</ul>
					</li>
				
				
				<%
					
					} else if(sidebarData.getIid() == 19) { //report
						
						String open = "";
						if(pageName.equals("cpRunsheetReport")||pageName.equals("cpAccountReport")||pageName.equals("cpPartnerReport")||pageName.equals("cpMemberReport")){
							open = "class=\"active open\"";
						}
					
				%>

					<li <%=open %>>
						<a href="#" class="dropdown-toggle">
							<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
							<span class="menu-text"> <%=itemname %> </span>

							<b class="arrow icon-angle-down"></b>
						</a>

						<ul class="submenu">
						
							<li class="<%=pageName.equals("cpRunsheetReport") ? "active" : "" %>">
								<a href="./runsheetreport">
									<i class="icon-double-angle-right"></i> &nbsp;
									<%=Resource.getString("ID_LABEL_DAILYDELIVERYRUNSHEET",locale)%>
								</a>
							</li>

							<li class="<%=pageName.equals("cpPartnerReport") ? "active" : "" %>">
								<a href="./partnerreport">
									<i class="icon-double-angle-right"></i> &nbsp;
									<%=Resource.getString("ID_LABEL_PARTNERSHIPMENT",locale)%>
								</a>
							</li>
							
							<li class="<%=pageName.equals("cpAccountReport") ? "active" : "" %>">
								<a href="./salesreport">
									<i class="icon-double-angle-right"></i> &nbsp;
									<%=Resource.getString("ID_LABEL_SALESREPORT",locale)%>
								</a>
							</li>
							
							<li class="<%=pageName.equals("cpMemberReport") ? "active" : "" %>">
								<a href="./memberreport">
									<i class="icon-double-angle-right"></i> &nbsp;
									Member Report
								</a>
							</li>
						
						</ul>
					</li>
					
					<%
						}
						
						else if(sidebarData.getIid() == 4) { //member list
							
							String open = "";
							if(pageName.equals("cpStaff")||pageName.equals("cpPartner")||pageName.equals("cpMember")){
								open = "class=\"active open\"";
							}
						
					%>
	
						<li <%=open %>>
							<a href="#" class="dropdown-toggle">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=Resource.getString("ID_LABEL_MEMBERS",locale)%></span>
	
								<b class="arrow icon-angle-down"></b>
							</a>
	
							<ul class="submenu">
								<li class="<%=pageName.equals("cpStaff") ? "active" : "" %>">
									<a href="./cpStaff">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_STAFFLIST",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpPartner") ? "active" : "" %>">
									<a href="./cpPartner">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_PARTNERLIST",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpMember") ? "active" : "" %>">
									<a href="./cpMember">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_MEMBERLIST",locale)%>
									</a>
								</li>
																
							</ul>
						</li>
					
					<%
						} else if(sidebarData.getIid() == 20) { //pricing
						
							String open = "";
							if(pageName.equals("cpPricing_partner")||pageName.equals("cpPricing_normal_parcel")||pageName.equals("cpPricing_normal_document")||pageName.equals("cpPricing_credit_parcel")||pageName.equals("cpPricing_credit_document")){
								open = "class=\"active open\"";
						}
						
					%>

						<li <%=open %>>
							<a href="#" class="dropdown-toggle">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=itemname %> </span>

								<b class="arrow icon-angle-down"></b>
							</a>

							<ul class="submenu">
								<li class="<%=pageName.equals("cpPricing_partner") ? "active" : "" %>">
									<a href="./cpPricing_partner">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_PARTNER",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpPricing_normal_parcel")||pageName.equals("cpPricing_normal_document") ? "active open" : "" %>">
									<a href="invoice" class="dropdown-toggle">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_NORMALUSER",locale)%>
										<b class="arrow icon-angle-down"></b>
									</a>
									
									<ul class="submenu">
										<li class="<%=pageName.equals("cpPricing_normal_parcel") ? "active" : "" %>">
											<a href="cpPricing_normal_parcel">
												 &nbsp; <i class="fa fa-angle-right"></i> &nbsp;
												<%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%>
											</a>
										</li>
										<li class="<%=pageName.equals("cpPricing_normal_document") ? "active" : "" %>">
											<a href="cpPricing_normal_document">
												 &nbsp; <i class="fa fa-angle-right"></i> &nbsp;
												<%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale)%>
											</a>
										</li>
									</ul>
									
								</li>
								
								<li class="<%=pageName.equals("cpPricing_credit_parcel")||pageName.equals("cpPricing_credit_document") ? "active open" : "" %>">
									<a href="invoice" class="dropdown-toggle">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_CREDITTERMUSER",locale)%>
										<b class="arrow icon-angle-down"></b>
									</a>
									
									<ul class="submenu">
										<li class="<%=pageName.equals("cpPricing_credit_parcel") ? "active" : "" %>">
											<a href="cpPricing_credit_parcel">
												 &nbsp; <i class="fa fa-angle-right"></i> &nbsp;
												<%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%>
											</a>
										</li>
										<li class="<%=pageName.equals("cpPricing_credit_document") ? "active" : "" %>">
											<a href="cpPricing_credit_document">
												 &nbsp; <i class="fa fa-angle-right"></i> &nbsp;
												<%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale)%>
											</a>
										</li>
									</ul>
									
								</li>
																
							</ul>
						</li>


				<%
						}
						
						else if(sidebarData.getIid() == 9) { //area��
							
							String open = "";
							if(pageName.equals("cpArea")||pageName.equals("cpZone")){
								open = "class=\"active open\"";
							}
						
					%>
	
						<li <%=open %>>
							<a href="#" class="dropdown-toggle">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=Resource.getString("ID_LABEL_AREAZONE",locale)%> </span>
	
								<b class="arrow icon-angle-down"></b>
							</a>
	
							<ul class="submenu">
								<li class="<%=pageName.equals("cpArea") ? "active" : "" %>">
									<a href="./area">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_AREA",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpZone") ? "active" : "" %>">
									<a href="./zone">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_ZONE",locale)%>
									</a>
								</li>
																
							</ul>
						</li>
	
					<%
						}
						
						else if(sidebarData.getIid() == 13) { //system log
							
							String open = "";
							if(pageName.equals("log")||pageName.equals("cpNotification")||pageName.equals("log_changeamount")){
								open = "class=\"active open\"";
							}
						
					%>
	
						<li <%=open %>>
							<a href="#" class="dropdown-toggle">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=Resource.getString("ID_LABEL_LOGS",locale)%></span>
	
								<b class="arrow icon-angle-down"></b>
							</a>
	
							<ul class="submenu">
								<li class="<%=pageName.equals("log") ? "active" : "" %>">
									<a href="./log">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_SERVERLOG",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpNotification") ? "active" : "" %>">
									<a href="./notification">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_NOTIFICATION",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("log_changeamount") ? "active" : "" %>">
									<a href="./amountLog">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_CHANGEAMOUNTLOG",locale)%>
									</a>
								</li>
																
							</ul>
						</li>
					
					<%
						}
						
						else if(sidebarData.getIid() == 21) { //Our Service
							
							String open = "";
							if(pageName.equals("cpBulletin")||pageName.equals("cpFaq")||pageName.equals("cpAbout")||pageName.equals("cpTerm")||pageName.equals("cpHowitworks")||pageName.equals("cpService")){
								open = "class=\"active open\"";
							}
						
					%>
	
						<li <%=open %>>
							<a href="#" class="dropdown-toggle">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=Resource.getString("ID_LABEL_OTHERS",locale)%></span>
	
								<b class="arrow icon-angle-down"></b>
							</a>
	
							<ul class="submenu">
								<li class="<%=pageName.equals("cpBulletin") ? "active" : "" %>">
									<a href="./cpBulletin">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_BULLETIN",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpFaq") ? "active" : "" %>">
									<a href="./cpFaq">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_FAQ",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpTerm") ? "active" : "" %>">
									<a href="./cpTerm">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_SERVICETERMS",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpHowitworks") ? "active" : "" %>">
									<a href="./cpHowitworks">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_HOWITWORKS",locale)%>
									</a>
								</li>
								
								<li class="<%=pageName.equals("cpService") ? "active" : "" %>">
									<a href="./cpService">
										<i class="icon-double-angle-right"></i> &nbsp;
										<%=Resource.getString("ID_LABEL_OURSERVICES",locale)%>
									</a>
								</li>
																
							</ul>
						</li>
							
					<%
						} else {	%>
						<li class="<%=pageName.equals(link) ? "active" : "" %>">
							<a href="./<%=link %>">
								<i class="<%=sidebarData.getIcon() %>"></i> &nbsp;
								<span class="menu-text"> <%=itemname %> </span>
							</a>
						</li>
					<% } 
				}
			}
		%>

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