<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.report.*,com.iposb.partner.*,com.iposb.area.*,com.iposb.consignment.*,java.util.ArrayList,java.text.*,java.util.Date,org.apache.commons.lang3.StringUtils" contentType="text/html; charset=utf-8"%>
<%@include file="../include.jsp" %>

<%	
	
	String errmsg = "";
	ConsignmentDataModel consignmentData = new ConsignmentDataModel();
	ArrayList data = (ArrayList)request.getAttribute(ReportController.OBJECTDATA);	
	if(data != null && data.size() > 0){
		consignmentData = (ConsignmentDataModel)data.get(0);
		errmsg = consignmentData.getErrmsg();
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	ArrayList <LogonDataModel> staffData = (ArrayList)request.getAttribute("staff");
	LogonDataModel sData = new LogonDataModel();

	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	
	String distributeDT = request.getParameter("distributeDT") == null ? "" : request.getParameter("distributeDT").toString();
	int area = Integer.parseInt(request.getParameter("area") == null ? "0" : request.getParameter("area").equals("") ? "0" : request.getParameter("area").toString());	
	String driver = request.getParameter("driver") == null ? "" : request.getParameter("driver").toString();	
	
	Date date = new Date();
	SimpleDateFormat sdf1, sdf2, sdf3;
	String todayYear, todayMonth, todayDay;
	sdf1 = new SimpleDateFormat("yyyy");
	todayYear = sdf1.format(date);
	sdf2 = new SimpleDateFormat("MM");
	todayMonth = sdf2.format(date);
	sdf3 = new SimpleDateFormat("dd");
	todayDay = sdf3.format(date);
	
	String checkDT = "";
	if(distributeDT.equals("")) {
		checkDT = todayYear + "-" + todayMonth + "-" + todayDay;
	} else {
		checkDT = distributeDT;
	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_DAILYDELIVERYRUNSHEETREPORT",locale)%></title>
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
							<li><%=Resource.getString("ID_LABEL_REPORT",locale)%></li>		
							<li><%=Resource.getString("ID_LABEL_DAILYDELIVERYRUNSHEET",locale)%></li>							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./runsheetreport" method="get" class="form-horizontal">
									
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="distributeDT"> <%=Resource.getString("ID_LABEL_DATE",locale)%> </label>
											<div class="col-sm-2">
												<div class="input-group">
													<input class="form-control date-picker" id="distributeDT" name="distributeDT" data-date-format="yyyy-mm-dd" type="text" value="<%=checkDT %>">
													<span class="input-group-addon">
														<i class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
										</div>

										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_STATION",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="area" name="area">
													<option value="0" <% if(area==0){out.print("selected");} %>></option>
													<% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(area==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_DRIVER",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="driver" name="driver">
													<option value=""></option>
													<% 
														if(staffData != null && !staffData.isEmpty()){
															for(int i = 0; i < staffData.size(); i ++){
																sData = staffData.get(i);
																String selected = "";
																String options = "";
																String staffName = sData.getCname().toString().trim().equals("") ? sData.getEname().toString() : sData.getCname().toString();
																if(driver.replace("%40", "@").equals(sData.getUserId())) {
																	selected = "selected";
																}
																options = "<option value='"+sData.getUserId()+"' "+selected+">" + staffName + "</option>";
																out.println(options);
															}
														}
													%>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="checkBtn"></label>
											<div class="col-sm-9">
												<a class="btn btn-primary" id="checkBtn">
													<i class="fa fa-search"></i>
													<%=Resource.getString("ID_LABEL_CHECK",locale)%>
												</a>
											</div>
										</div>

									</form>
									
									
										<div class="space-4"></div>
										
										<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DETAILS", locale)%></h3>
									
										<div class="space-6"></div>

										
											<div class="row">
												<div class="col-xs-12">
													<div class="table-responsive">
														<table id="overall-table" class="table table-striped table-bordered table-hover">
															<thead>
																<tr>
																	<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
																	<th class="center col-xs-2"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
																	<th class="center col-xs-2"><%=Resource.getString("ID_LABEL_PARTNERCONSIGNMENT",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_RECEIVERNAME",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_STATUS",locale)%></th>
																	<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
																</tr>
															</thead>

															<tbody>
																<%
																	if( (data != null && data.size() > 0) && (area > 0) ){
																		for(int i = 0; i < data.size(); i++){
																			consignmentData = (ConsignmentDataModel)data.get(i);
																%>
																	<tr onMouseOver="this.bgColor='#E6FFD9';" onMouseOut="this.bgColor='#FFFFFF';">
																		<td align="center"><%=(i+1) %></td>
																		<td align="center">
																			<a href="./consignment?actionType=searchConsignment&consignmentNo=<%=consignmentData.getConsignmentNo() %>" target="_blank"><%=consignmentData.getConsignmentNo() %></a>
																		</td>
																		<td align="center"><%=consignmentData.getGeneralCargoNo() %></td>
																		<td align="center"><%=consignmentData.getSenderName() %></td>
																		<td align="center"><%=consignmentData.getReceiverName() %></td>
																		<td align="center"><%=consignmentData.getQuantity() %></td>
																		<td align="center">
																			<%
																				String stageTXT = "";
																				int thisstage = consignmentData.getStage();
																				if(thisstage == 0) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE0", "en_US");
																				} else if(thisstage == 1) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE1", "en_US");
																				} else if(thisstage == 2) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE2", "en_US");
																				} else if(thisstage == 3) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE3", "en_US");
																				} else if(thisstage == 4) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE4", "en_US");
																				} else if(thisstage == 5) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE5", "en_US");
																				} else if(thisstage == 6) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE6", "en_US");
																				} else if(thisstage == 7) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE7", "en_US");
																				} else if(thisstage == 9) {
																					stageTXT = Resource.getString("ID_LABEL_STAGE9", "en_US");
																				}
																				
																				out.print(stageTXT);
																			%>
																		</td>
																		<td align="center"><%=consignmentData.getDeliveryDT() %></td>
																	</tr>
																<%
																		}
																	} else {
																%>
																	<tr><td class="text-center red" colspan="6" style="line-height:50px"><%=Resource.getString("ID_LABEL_NORECORD",locale)%></td></tr>
																
																<% } %>
																
															</tbody>
														</table>
													</div><!-- /.table-responsive -->
												</div><!-- /span -->
											</div>

										
										<div class="space-4"></div>
									
										<b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%><%=data==null ? 0 : data.size() %></b>
										
										<div class="space-10"></div>
										
										<%
											if(!consignmentData.getAttendee1().equals("n/a")) {
												out.println(Resource.getString("ID_LABEL_ATTENDEE",locale)+"1 "+Resource.getString("ID_LABEL_COLON",locale) + consignmentData.getAttendee1());
											}
										
											if(!consignmentData.getAttendee2().equals("n/a")) {
												out.println("<br/>"+Resource.getString("ID_LABEL_ATTENDEE",locale)+"2 "+Resource.getString("ID_LABEL_COLON",locale) + consignmentData.getAttendee2());
											}
										%>
										
										<div class="space-10"></div>
									
										<div class="center">
											
											<% if( ((priv == 99)||(priv == 9)) && ((data != null && data.size() > 0)) ){ //有資料，然後是admin或account才能產生 %>
												<span>
													<button class="btn btn-lg btn-primary" id="generateReport">
														<i class="ace-icon fa fa-file-pdf-o"></i>
														<%=Resource.getString("ID_LABEL_GENERATEREPORT",locale) %>
													</button>
													<span id="loadingReport"></span>
												</span>
											<% } %>
											
										</div>

										<p>&nbsp;</p>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		<script src="plugins/ace/js/date-time/bootstrap-datepicker.min.js"></script>
		
		<script language="javascript" type="text/javascript">

			$( document ).ready(function() {

			});
			
			$(function() {

				$( "#checkBtn" ).click(function() { 
				
					var distributeDT = $("#distributeDT").val();
					var area = $("#area").val();
					
					if(distributeDT == "") {
						alert ("Please select date");
						return false;
					}
					
					if(area == "0") {
						alert ("Please select Station");
						return false;
					}
					
					document.selector.submit(); 
					return false; 
				});
				
		
				
			});
			
			
			$(function() {
				$( "#generateReport" ).click(function() {
					
					var distributeDT = $( "#distributeDT" ).val();
					var area = $( "#area" ).val();
					var driver = $( "#driver" ).val();

					$( "#loadingReport" ).html("<br/><br/><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i><br/><br/><br/>");

					$(function() {
						$.ajax({
							url: './report',        
							data: "actionType=genRunsheetReport&distributeDT="+distributeDT+"&area="+area+"&driver="+driver,
							success: function(response) {

								if(response.indexOf(".pdf")!=-1) {
									$( "#loadingReport" ).html("<br/><br/><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/runsheetReport/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a><br/><br/><br/>");
								} else if(response=="NoConnection") {
									$( "#loadingReport" ).html("<br/><%=Resource.getString("ID_LABEL_NOCONNECTION",locale)%>");
								} else if(response=="noData") {
									$( "#loadingReport" ).html("<br/><%=Resource.getString("ID_MSG_NODATA",locale)%>");
								} else {
									$( "#loadingReport" ).html("<br/>"+response);
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
