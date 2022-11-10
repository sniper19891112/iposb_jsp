<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.consignment.*,com.iposb.privilege.*,com.iposb.area.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	
	if(userId.equals("") || priv != 3){
		String url = "./";
		response.sendRedirect(url);
        return;
	}
	
	String todayDate = UtilsBusinessModel.todayDate();
	
	ArrayList <ConsignmentDataModel> data = (ArrayList)request.getAttribute(ConsignmentController.OBJECTDATA);
	ConsignmentDataModel uploadData = new ConsignmentDataModel();
	
	ArrayList <LogonDataModel> driverData = (ArrayList)request.getAttribute("driver");
	LogonDataModel dData = new LogonDataModel();
	
	ArrayList <LogonDataModel> staffData = (ArrayList)request.getAttribute("staff");
	LogonDataModel sData = new LogonDataModel();
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_STATIONWORK",locale)%></title>
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

				<%@include file="partnerCpSidebar.jsp" %>

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
							<li>
								<a href="./agentCP"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a>
							</li>
							<li class="active"><%=Resource.getString("ID_LABEL_STATIONWORK",locale)%></li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<!-- PAGE CONTENT BEGINS -->
							
							<div class="col-xs-12" id="cp">
								
									<h3><%=Resource.getString("ID_LABEL_MANIFEST",locale) %></h3>
									<div style="border-style:dashed; border-width:1px; padding:10px">
									
										<div class="row">
											
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_DATE",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %>
													<div class="input-group">
														<input class="form-control date-picker" id="dispatchDT" name="dispatchDT" maxlength="10" type="text" value="<%=todayDate %>" data-date-format="yyyy-mm-dd" />
														<span class="input-group-addon">
															<i class="fa fa-calendar bigger-110"></i>
														</span>
													</div>
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_FROMSTATION",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %>
													<select class="form-control" id="fromStation" name="fromStation">
						                                <% 
							                                if(areaData != null && !areaData.isEmpty()){
																for(int i = 0; i < areaData.size(); i ++){
																	aData = areaData.get(i);
						                                %>
															<option value="<%=aData.getAid() %>"><%=aData.getName_enUS() %> (<%=aData.getCode() %>)</option>
														<% } } %>
													</select>
												</span>
											</div>
	
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_TOSTATION",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %>
													<select class="form-control" id="toStation" name="toStation">
						                                <% 
							                                if(areaData != null && !areaData.isEmpty()){
																for(int i = 0; i < areaData.size(); i ++){
																	aData = areaData.get(i);
						                                %>
															<option value="<%=aData.getAid() %>"><%=aData.getName_enUS() %> (<%=aData.getCode() %>)</option>
														<% } } %>
													</select>
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3 text-center">
												<span>
													<button class="btn btn-lg btn-success" id="generateManifest">
														<i class="ace-icon fa fa-file-text-o"></i>
														<%=Resource.getString("ID_LABEL_GENERATEMANIFEST",locale) %>
													</button>
													<span id="loadingManifest"></span>
												</span>
											</div>
											
										</div>

									</div>
									
									<div class="space-24"></div>
									
									
									<h3><%=Resource.getString("ID_LABEL_DELIVERYRUNSHEET",locale) %></h3>
									<div style="border-style:dashed; border-width:1px; padding:10px">
									
										<div class="row">
											
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_DATE",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %>
													<div class="input-group">
														<input class="form-control date-picker" id="distributeDT" name="distributeDT" maxlength="10" type="text" value="<%=todayDate %>" data-date-format="yyyy-mm-dd" />
														<span class="input-group-addon">
															<i class="fa fa-calendar bigger-110"></i>
														</span>
													</div>
												</span>
											</div>
	
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_AREA",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %>
													<select class="form-control" id="area" name="area">
						                                <% 
							                                if(areaData != null && !areaData.isEmpty()){
																for(int i = 0; i < areaData.size(); i ++){
																	aData = areaData.get(i);
						                                %>
															<option value="<%=aData.getAid() %>"><%=aData.getName_enUS() %> (<%=aData.getCode() %>)</option>
														<% } } %>
													</select>
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_ATTENDEE",locale) %> 1<%=Resource.getString("ID_LABEL_COLON",locale) %>
													<select class="form-control" id="attendee1" name="attendee1">
														<option value=""></option>
														<% 
							                                if(staffData != null && !staffData.isEmpty()){
																for(int i = 0; i < staffData.size(); i ++){
																	sData = staffData.get(i);
																	String selected = "";
																	String options = "";
																	String staffName = sData.getCname().toString().trim().equals("") ? sData.getEname().toString() : sData.getCname().toString();
																	options = "<option value='"+sData.getUserId()+"'>" + staffName + "</option>";
																	out.println(options);
																}
															}
														%>
													</select>
												</span>
											</div>
																						
											<div class="col-xs-12 col-lg-3 text-center">

											</div>
											
										</div>
										
										<div class="row">
											
											<div class="col-xs-12 col-lg-3">
												
											</div>
											
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_DRIVER",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %>
													<select class="form-control" id="driver" name="driver">
														<% 
															/*
							                                if(driverData != null && !driverData.isEmpty()){
																for(int i = 0; i < driverData.size(); i ++){
																	dData = driverData.get(i);
																	String selected = "";
																	String options = "";
																	String driverName = dData.getCname().toString().trim().equals("") ? dData.getEname().toString() : dData.getCname().toString();
																	options = "<option value='"+dData.getUserId()+"'>" + driverName + "</option>";
																	out.println(options);
																}
															}
														*/
														if(staffData != null && !staffData.isEmpty()){
															for(int i = 0; i < staffData.size(); i ++){
																sData = staffData.get(i);
																String selected = "";
																String options = "";
																String staffName = sData.getCname().toString().trim().equals("") ? sData.getEname().toString() : sData.getCname().toString();
																options = "<option value='"+sData.getUserId()+"'>" + staffName + "</option>";
																out.println(options);
															}
														}
														%>
													</select>
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3">
												<span>
													<%=Resource.getString("ID_LABEL_ATTENDEE",locale) %> 2<%=Resource.getString("ID_LABEL_COLON",locale) %>
													<select class="form-control" id="attendee2" name="attendee2">
														<option value=""></option>
														<% 
							                                if(staffData != null && !staffData.isEmpty()){
																for(int i = 0; i < staffData.size(); i ++){
																	sData = staffData.get(i);
																	String selected = "";
																	String options = "";
																	String staffName = sData.getCname().toString().trim().equals("") ? sData.getEname().toString() : sData.getCname().toString();
																	options = "<option value='"+sData.getUserId()+"'>" + staffName + "</option>";
																	out.println(options);
																}
															}
														%>
													</select>
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3 text-center">
												<span>
													<button class="btn btn-lg btn-warning" id="generateRunsheet">
														<i class="ace-icon fa fa-file-text-o"></i>
														<%=Resource.getString("ID_LABEL_GENERATERUNSHEET",locale) %>
													</button>
													<span id="loadingRunsheet"></span>
												</span>
											</div>
											
										</div>

									</div>
									
									<div class="space-24"></div>
									
									<h3><%=Resource.getString("ID_LABEL_PICKUPRUNSHEETBLANK",locale) %></h3>
									<div style="border-style:dashed; border-width:1px; padding:10px">
									
										<div class="row">
											
											<div class="col-xs-12 col-lg-3">
												<span>
													
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3">
												<span>
													
												</span>
											</div>
	
											<div class="col-xs-12 col-lg-3">
												<span>
													
												</span>
											</div>
											
											<div class="col-xs-12 col-lg-3 text-center">
												<span>
													<a href="./etc/template_pickupRunsheet.pdf" target="_blank">
														<button class="btn btn-lg btn-default">
															<i class="ace-icon fa fa-file-text-o"></i>
															<%=Resource.getString("ID_LABEL_GENERATERUNSHEET",locale) %>
														</button>
													</a>
												</span>
											</div>
											
										</div>

									</div>
														
									<div class="space-24"></div>
									

							</div>
														
							
							
							<!-- PAGE CONTENT ENDS -->
							
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		<script src="plugins/ace/js/date-time/bootstrap-datepicker.min.js"></script>
		
		<script type="text/javascript">
		
			$(document).ready(function() {

			});
		

			$(function() {
				$( "#generateManifest" ).click(function() {
					
					var fromStation = $( "#fromStation" ).val();
					var toStation = $( "#toStation" ).val();
					var dispatchDT = $( "#dispatchDT" ).val();
					
					if(trim(dispatchDT).length != 10) {
						alert("Please select date");
						$( "#dispatchDT" ).focus();
						return false;
					}
					
					$( "#loadingManifest" ).html("<br><br><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i>");

					$(function() {
						$.ajax({
							url: './pdf',        
							data: "actionType=manifest&fromStation="+fromStation+"&toStation="+toStation+"&dispatchDT="+dispatchDT,
							success: function(response) {

								if(response.indexOf(".pdf")!=-1) {
									$( "#loadingManifest" ).html("<br><br><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/manifest/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a>");
								} else if(response=="NoConnection") {
									$( "#loadingManifest" ).html("<br><br><%=Resource.getString("ID_LABEL_NOCONNECTION",locale)%>");
								} else if(response=="noData") {
									$( "#loadingManifest" ).html("<br><br><%=Resource.getString("ID_MSG_NODATA",locale)%>");
								} else {
									$( "#loadingManifest" ).html("<br><br>"+response);
								}
								
							}
						});
					});
					
				});
				
			});
			
			$(function() {
				$( "#generateRunsheet" ).click(function() {
					
					var driver = $( "#driver" ).val();
					var attendee1 = $( "#attendee1" ).val();
					var attendee2 = $( "#attendee2" ).val();
					var area = $( "#area" ).val();
					var distributeDT = $( "#distributeDT" ).val();
					
					if(trim(distributeDT).length != 10) {
						alert("Please select date");
						$( "#distributeDT" ).focus();
						return false;
					}
					
					$( "#loadingRunsheet" ).html("<br><br><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i>");

					$(function() {
						$.ajax({
							url: './pdf',        
							data: "actionType=runsheet&area="+area+"&driver="+driver+"&attendee1="+attendee1+"&attendee2="+attendee2+"&distributeDT="+distributeDT,
							success: function(response) {

								if(response.indexOf(".pdf")!=-1) {
									$( "#loadingRunsheet" ).html("<br><br><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/runsheet/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a>");
								} else if(response=="NoConnection") {
									$( "#loadingRunsheet" ).html("<br><br><%=Resource.getString("ID_LABEL_NOCONNECTION",locale)%>");
								} else if(response=="noData") {
									$( "#loadingRunsheet" ).html("<br><br><%=Resource.getString("ID_MSG_NODATA",locale)%>");
								} else {
									$( "#loadingRunsheet" ).html("<br><br>"+response);
								}
								
							}
						});
					});
					
				});
			});
			
			
			//datepicker plugin
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			})
			//show datepicker when clicking on the icon
			.next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
			

			
		</script>
		
		<link rel="stylesheet" href="plugins/ace/css/datepicker.css" />
		
	</body>
</html>
