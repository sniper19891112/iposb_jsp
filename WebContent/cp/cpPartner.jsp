<%@page import="com.iposb.i18n.*,com.iposb.partner.*,com.iposb.area.*,com.iposb.pricing.*,com.iposb.utils.*,java.util.HashMap,java.util.Iterator,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	

	ArrayList data = (ArrayList)request.getAttribute(PartnerController.OBJECTDATA);
	PartnerDataModel partnerData = new PartnerDataModel();
	String errMsg = "";
	
	if(data != null && data.size() > 0){
		partnerData = (PartnerDataModel)data.get(0);
		errMsg = partnerData.getErrmsg();
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();

	String searchuser = request.getParameter("searchuser")==null ? "" : new String(request.getParameter("searchuser").getBytes("ISO-8859-1"), "UTF-8");
	boolean isSearch = request.getParameter("actionType")==null ? false : request.getParameter("actionType").toString().equals("searchmember") ? true : false;
	String actionType = request.getParameter("actionType") == null ? "" : request.getParameter("actionType").toString();
	
	String displayArea = partnerData.getPrivilege() == 3 ? "block" : "none";
	
	boolean addEdit = false;
	boolean list = true;
	
	
	if(actionType.equals("cppartner_edit") || actionType.equals("cppartner_update")){
		addEdit = true;
		list = false;
	}

%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_PARTNERLIST",locale)%></title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="cpMeta.jsp" %>		

	</head>

	<body>
		<%@include file="cpHeader.jsp" %>
		

		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<%@include file="cpSidebar.jsp" %>

				<div class="main-content">
				
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a>
							</li>
							<li><a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li>
							<% 
								if (isSearch) {
									out.print("<li><a href=\"./cpPartner\">"+Resource.getString("ID_LABEL_PARTNERLIST",locale)+"</a></li>"); 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_SEARCH",locale)+ " " + searchuser +"</li>");
								} else { 
									
									if(list){ 
										out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_PARTNERLIST",locale)+"</li>"); 
									} else {
										out.print("<li><a href=\"./cpPartner\">"+Resource.getString("ID_LABEL_PARTNERLIST",locale)+"</a></li>"); 
										out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_EDIT",locale)+"</li>"); 
									}
				
								} 
							%>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<%
										if(addEdit){
									%>
										<div id="result"></div>
										
										<form id="cpPartnerForm" name="cpPartnerForm" method="post" action="./partner">
											<input type="hidden" id="actionType" name="actionType" value="cppartner_update">
											<input type="hidden" id="pid" name="pid" value="<%=partnerData.getPid() %>">
											<input type="hidden" id="userId_Current" name="userId_Current" value="<%=partnerData.getUserId_Current() %>">
											<input type="hidden" id="email_Current" name="email_Current" value="<%=partnerData.getEmail_Current() %>">
											<input type="hidden" id="creator" name="creator" value="<%=userId %>">
											<input type="hidden" id="modifier" name="modifier" value="<%=userId %>">
											
											<div>
											
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="email"> <%=Resource.getString("ID_LABEL_EMAIL",locale)%> <span class="red">*</span> </label>
													<div class="col-sm-9">
														<input type="text" id="email" name="email" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getEmail() %>" maxlength="120" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="ename"> <%=Resource.getString("ID_LABEL_OFFICIALNAME",locale)%> <span class="red">*</span> </label>
													<div class="col-sm-9">
														<input type="text" id="ename" name="ename" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getEname() %>" maxlength="100" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="cname"> <%=Resource.getString("ID_LABEL_ABBREVIATION",locale)%> <span class="red">*</span> </label>
													<div class="col-sm-9">
														<input type="text" id="cname" name="cname" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getCname() %>" maxlength="100" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="website"> <%=Resource.getString("ID_LABEL_WEBSITE",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="website" name="website" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getWebsite() %>" maxlength="100" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="officialEmail"> <%=Resource.getString("ID_LABEL_OFFICIALEMAIL",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="officialEmail" name="officialEmail" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getOfficialEmail() %>" maxlength="120" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="contactPerson"> <%=Resource.getString("ID_LABEL_CONTACTNAME",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="contactPerson" name="contactPerson" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getContactPerson() %>" maxlength="50" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="phone"> <%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="phone" name="phone" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getPhone() %>" maxlength="50" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="address"> <%=Resource.getString("ID_LABEL_ADDRESS",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="address" name="address" placeholder="" class="col-xs-10 col-sm-15" value="<%=partnerData.getAddress() %>" maxlength="100" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="companyLicense"> <%=Resource.getString("ID_LABEL_COMPANYLICENSE",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="companyLicense" name="companyLicense" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getCompanyLicense() %>" maxlength="100" />
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="gst"> <%=Resource.getString("ID_LABEL_GSTNO",locale)%> </label>
													<div class="col-sm-9">
														<input type="text" id="gst" name="gst" placeholder="" class="col-xs-10 col-sm-5" value="<%=partnerData.getGst() %>" maxlength="15" />
													</div>
												</div>
																								
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="privilege"> <%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%> <span class="red">*</span> </label>
													<div class="col-sm-9">
														<select class="form-control" id="privilege" name="privilege"  style="width:41%;">
															<option value="0" <% if (partnerData.getPrivilege() == 0){out.print("selected");} %>>&nbsp;</option>
															<option value="3" <% if (partnerData.getPrivilege() == 3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PRIV3",locale) %></option>
															<option value="4" <% if (partnerData.getPrivilege() == 4){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PRIV4",locale) %></option>
														</select>
													</div>
												</div>
																								
												<div class="agentAreaDiv" style="height:35px;display:<%=displayArea%>;"></div>
												
												<div class="form-group agentAreaDiv" style="display:<%=displayArea%>;">
													<label class="col-sm-3 control-label no-padding-right" for="aid"> <%=Resource.getString("ID_LABEL_AREA",locale)%> <span class="red">*</span> </label>
													<div class="col-sm-9">
														<select class="form-control" id="aid" name="aid"  style="width:41%;">
															<option value="0"></option>
															<% 
								                                if(areaData != null && !areaData.isEmpty()){
																	for(int i = 0; i < areaData.size(); i ++){
																		aData = areaData.get(i);
							                                %>
																<option value="<%=aData.getAid() %>" <% if(partnerData.getAid()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
															<% } } %>
														</select>
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right" for="pricingPolicy"> <%=Resource.getString("ID_LABEL_PRICINGPOLICY",locale)%> <span class="red">*</span> </label>
													<div class="col-sm-9">
														<select class="form-control" id="pricingPolicy" name="pricingPolicy"  style="width:41%;">
															<option value="0">&nbsp;</option>
															<% 
																ArrayList <PricingDataModel> pricingData = (ArrayList)request.getAttribute("pricing");
																PricingDataModel pData = new PricingDataModel();
																if(pData != null && !pricingData.isEmpty()){
																	for(int i = 0; i < pricingData.size(); i ++){
																		pData = pricingData.get(i);
																		String selected = "";
																		if(pData.getPid() == partnerData.getPricingPolicy()) {
																			selected = "selected";
																		}
																		out.println("<option value=\""+pData.getPid()+"\" "+selected+">"+pData.getTitle_enUS()+"</option>");
																	}
																}
															%>
														</select>
													</div>
												</div>
												
												<div style="height:35px;"></div>
												
											</div>
										</form>
										
										<p>
											<div class="center">
												<a class="btn" id="backBtn">
													<i class="icon-arrow-left"></i>
													<%=Resource.getString("ID_LABEL_BACK",locale)%>
												</a>
												<a class="btn btn-primary" id="submitBtns">
													<i class="icon-save"></i>
													<%=Resource.getString("ID_LABEL_SAVE",locale)%>
												</a>
											</div>
										</p>
									<% }
										else if(list){
									%>	
									
									
									<div class="row">
										<div class="col-xs-12">

											<div class="table-responsive">
												<table id="members-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ENAME",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ABBREVIATION",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_EMAIL",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_AREA",locale)%></th>
															<th class="center"></th>
														</tr>
													</thead>

													<tbody>
													<%
														partnerData = new PartnerDataModel();
														
														if(data != null && data.size() > 0){
															for(int i = 0; i < data.size(); i++){
																partnerData = (PartnerDataModel)data.get(i);
													%>
														<tr>
															<td class="center"><%=partnerData.getPid() %></td>
															<td class="center"><%=partnerData.getEname() %></td>
															<td class="center"><%=partnerData.getCname() %></td>
															<td class="center"><%=partnerData.getEmail() %></td>
															<td class="center"><%=partnerData.getPhone() %></td>
															<td id="priv_<%=partnerData.getPid() %>" class="center">
																<%
																	if (partnerData.getPrivilege() == 3){out.print(Resource.getString("ID_LABEL_PRIV3",locale));}
																	else if (partnerData.getPrivilege() == 4){out.print(Resource.getString("ID_LABEL_PRIV4",locale));}
																%>
															</td>
															<td id="areaName_<%=partnerData.getPid() %>" class="center"><%=partnerData.getAreaName() %></td>
															<td class="center">
																<a href="partner?actionType=cppartner_edit&email=<%=partnerData.getEmail() %>" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_EDIT",locale)%>">
																	<span class="green">
																		<i class="icon-edit bigger-120"></i>
																	</span>
																</a>
																<a id="userDetails" pid="<%=partnerData.getPid() %>" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_DETAILS",locale)%>">
																	<span class="blue">
																		<i class="icon-search bigger-120"></i>
																	</span>
																</a>
															</td>
														</tr>
													<% } } %>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									
									<% } %>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		<jsp:include page="cpModal.jsp?bookType=partner" />

		<!-- page specific plugin scripts -->

		<script src="./assets/js/jquery.dataTables.min.js"></script>
		<script src="./assets/js/jquery.dataTables.bootstrap.js"></script>

<g:compress>

		<script type="text/javascript">
			
			$( document ).ready(function() {
				<%
					if(errMsg.length() > 0){
						if(errMsg.equals("insertSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_INSERTSUCCESS",locale) + "</p></div>\");");
						} else if(errMsg.equals("updateSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATESUCCESS",locale) + "</p></div>\");");
						} else if(errMsg.equals("noData")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NODATA",locale) + "</p></div>\");");
						} else if(errMsg.equals("exist")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_EXIST",locale) + "</p></div>\");");
						} else if(errMsg.equals("noConnection")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>
				
				$("#privilege").change(function(){
					var privilege = this.value;
					//alert("privilege: "+privilege);
					showArea(privilege);
				});
			});
			function showArea(p){ 
				var div = $(".agentAreaDiv");
				if(p==3) div.show();
				else div.hide();
			}
			
			$(function() {
				$("#submitBtns").click(function(){
					$("#cpPartnerForm").submit();
				});
			});
			
			jQuery(function($) {
				var oTable1 = $('#members-table').dataTable( {
				"aoColumns": [
			      null, 
			      null,
			      null,
			      null, 
			      null,
			      null,
			      null,
				  { "bSortable": false }
				] } );
			
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
				
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			})
		</script>
</g:compress>
		
	</body>
</html>
