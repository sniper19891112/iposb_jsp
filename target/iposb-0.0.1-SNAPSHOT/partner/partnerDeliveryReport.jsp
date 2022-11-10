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
	
	ArrayList <PartnerDataModel> partnerData = (ArrayList)request.getAttribute("partner");
	PartnerDataModel pData = new PartnerDataModel();

	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	
	String createDT = request.getParameter("createDT") == null ? "" : request.getParameter("createDT").toString();
	int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").equals("") ? "0" : request.getParameter("station").toString());	
	int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").equals("") ? "0" : request.getParameter("partnerId").toString());	
	int stage = Integer.parseInt(request.getParameter("stage") == null ? "0" : request.getParameter("stage").equals("") ? "0" : request.getParameter("stage").toString());	
	String payMethod = request.getParameter("payMethod") == null ? "" : request.getParameter("payMethod").toString();	
	
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
	if(createDT.equals("")) {
		checkDT = todayYear + "-" + todayMonth + "-" + todayDay;
	} else {
		checkDT = createDT;
	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_DELIVERYREPORT",locale)%></title>
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
							<li><a href="./partnerCP"><%=Resource.getString("ID_LABEL_DASHBOARD",locale)%></a></li>
							<li><%=Resource.getString("ID_LABEL_DELIVERYREPORT",locale)%></li>					
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./partnerDeliveryReport" method="get" class="form-horizontal">
									
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="createDT"> <%=Resource.getString("ID_LABEL_DATE",locale)%> </label>
											<div class="col-sm-2">
												<div class="input-group">
													<input class="form-control date-picker" id="createDT" name="createDT" data-date-format="yyyy-mm-dd" type="text" value="<%=checkDT %>">
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
												<select class="form-control" id="station" name="station">
													<option value="0" <% if(station==0){out.print("selected");} %>></option>
													<% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(station==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_STAGE",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="stage" name="stage">
					                                <option value="0" <% if(stage==0){out.print("selected");} %>><%=Resource.getString("ID_LABEL_ALL",locale) %></option>
					                                <option value="4" <% if(stage==4){out.print("selected");} %>><%=Resource.getString("ID_LABEL_STAGE4",locale) %></option>
					                                <option value="5" <% if(stage==5){out.print("selected");} %>><%=Resource.getString("ID_LABEL_STAGE5",locale) %></option>
					                                <option value="6" <% if(stage==6){out.print("selected");} %>><%=Resource.getString("ID_LABEL_STAGE6",locale) %></option>
					                                <option value="7" <% if(stage==7){out.print("selected");} %>><%=Resource.getString("ID_LABEL_STAGE7",locale) %></option>
					                                <option value="9" <% if(stage==9){out.print("selected");} %>><%=Resource.getString("ID_LABEL_STAGE9",locale) %></option>
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
																	<th class="center"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_PARTNERCONSIGNMENT",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_RECEIVERNAME",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_STATUS",locale)%></th>
																</tr>
															</thead>

															<tbody>
																<%
																	if(data != null && data.size() > 0){
																		for(int i = 0; i < data.size(); i++){
																			consignmentData = (ConsignmentDataModel)data.get(i);
																%>
																	<tr onMouseOver="this.bgColor='#E6FFD9';" onMouseOut="this.bgColor='#FFFFFF';">
																		<td align="center"><%=(i+1) %></td>
																		<td align="center">
																			<a href="./partner?actionType=searchPartnerConsignment&consignmentNo=<%=consignmentData.getConsignmentNo() %>" target="_blank"><%=consignmentData.getConsignmentNo() %></a>
																		</td>
																		<td align="center"><%=consignmentData.getGeneralCargoNo() %></td>
																		<td align="center"><%=consignmentData.getSenderName() %></td>
																		<td align="center"><%=consignmentData.getReceiverName() %></td>
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
									
										<div class="center">
											
											<% if( (priv == 4) && ((data != null && data.size() > 0)) ){ //有資料，然後身份是partner才能產生 %>
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
				
					var createDT = $("#createDT").val();
					var station = $("#station").val();
					
					if(createDT == "") {
						alert ("Please select date");
						return false;
					}
					
					document.selector.submit(); 
					return false; 
				});
				
		
				
			});
			
			
			$(function() {
				$( "#generateReport" ).click(function() {
					
					var createDT = $( "#createDT" ).val();
					var station = $( "#station" ).val();
					var stage = $( "#stage" ).val();

					$( "#loadingReport" ).html("<br/><br/><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i><br/><br/><br/>");

					$(function() {
						$.ajax({
							url: './report',        
							data: "actionType=genDeliveryReport&createDT="+createDT+"&station="+station+"&stage="+stage,
							success: function(response) {

								if(response.indexOf(".pdf")!=-1) {
									$( "#loadingReport" ).html("<br/><br/><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/partnerDeliveryReport/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a><br/><br/><br/>");
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
