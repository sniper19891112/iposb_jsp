<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.report.*,com.iposb.account.*,com.iposb.area.*,com.iposb.consignment.*,java.util.ArrayList,java.text.*,java.util.Date,org.apache.commons.lang3.StringUtils" contentType="text/html; charset=utf-8"%>
<%@include file="../include.jsp" %>

<%	
	
	String errmsg = "";
	AccountDataModel accountData = new AccountDataModel();
	ArrayList data = (ArrayList)request.getAttribute(AccountController.OBJECTDATA);	
	if(data != null && data.size() > 0){
		accountData = (AccountDataModel)data.get(0);
		errmsg = accountData.getErrmsg();
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();

	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	
	String accDT = request.getParameter("accDT") == null ? "" : request.getParameter("accDT").toString();
	int origin = Integer.parseInt(request.getParameter("origin") == null ? "0" : request.getParameter("origin").toString());	
	int destination = Integer.parseInt(request.getParameter("destination") == null ? "0" : request.getParameter("destination").toString());	
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
	
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_SALESREPORT",locale)%></title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="cpMeta.jsp" %>
		<link rel="stylesheet" href="./assets/css/daterangepicker.css" />
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
							<li><%=Resource.getString("ID_LABEL_SALESREPORT",locale)%></li>							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./salesreport" method="get" class="form-horizontal">
									
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="accDT"> <%=Resource.getString("ID_LABEL_DATE",locale)%> </label>
											<div class="col-sm-3">
												<div class="input-group">
													<input class="form-control" id="accDT" name="accDT" type="text" value="<%=accDT %>">
													<span class="input-group-addon">
														<i class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
										</div>

										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="origin"> <%=Resource.getString("ID_LABEL_ORIGIN",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="origin" name="origin">
													<% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(origin==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="destination"> <%=Resource.getString("ID_LABEL_DESTINATION",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="destination" name="destination">
													<option value="0" <% if(destination==0){out.print("selected");} %>></option>
													<% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(destination==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_PAYMETHODTXT",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="payMethod" name="payMethod">
													<option value="-1" <% if(payMethod.equals("-1")){out.print("selected");} %>> --- <%=Resource.getString("ID_LABEL_ALL",locale)%> --- </option>
				                                	<option value="0" <% if(payMethod.equals("0")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_UNKNOWN",locale)%></option>
				                                	<option value="1" <% if(payMethod.equals("1")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCASH",locale)%></option>
													<option value="2" <% if(payMethod.equals("2")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYONLINE",locale)%></option>
													<option value="3" <% if(payMethod.equals("3")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYBANKIN",locale)%></option>
													<option value="4" <% if(payMethod.equals("4")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale)%></option>
													<option value="5" <% if(payMethod.equals("5")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCOD",locale)%></option>
													<option value="6" <% if(payMethod.equals("6")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_FREIGHTCHARGES",locale)%></option>
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
																	<th class="center"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_PARTNERCONSIGNMENT",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_FROMTO",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_AMOUNT",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_PAYMETHODTXT",locale)%></th>
																</tr>
															</thead>

															<tbody>
																<%
																	int totalQuantity = 0;
																	double totalAmount = 0.0;
												
																	if( (data != null && data.size() > 0) && (origin > 0) ){
																		for(int i = 0; i < data.size(); i++){
																			accountData = (AccountDataModel)data.get(i);
																			
																			if( (accountData.getCreditArea() > 0) && (accountData.getCreditArea() != origin) ) { //如果是credit term user, 則必須根據 creditArea 作歸類，如果creditArea 和 origin 不一樣，則不 show 此筆
																			} else {
																%>
																	<tr onMouseOver="this.bgColor='#E6FFD9';" onMouseOut="this.bgColor='#FFFFFF';">
																		<td align="center"><%=accountData.getDispatchDT() %></td>
																		<td align="center">
																			<a href="./consignment?actionType=searchConsignment&consignmentNo=<%=accountData.getConsignmentNo() %>" target="_blank"><%=accountData.getConsignmentNo() %></a>
																		</td>
																		<td align="center"><%=accountData.getGeneralCargoNo() %></td>
																		<td align="center"><%=accountData.getSenderName() %></td>
																		<td><%=accountData.getSenderAreaname().equals("") ? "?" : accountData.getSenderAreaname() %> - <%=accountData.getReceiverAreaname().equals("") ? "?" : accountData.getReceiverAreaname() %><input type="hidden" id="stationCode" value="<%=AreaBusinessModel.parseAreaCode(accountData.getSenderArea()) %>"></td>
																		<td align="center"><%=accountData.getQuantity() %><% totalQuantity += accountData.getQuantity(); %></td>
																		<td align="center"><%=formatter.format(accountData.getAmount()) %><% totalAmount += accountData.getAmount(); %></td>
																		<td align="center"><%=ConsignmentBusinessModel.parsePayMethod(accountData.getPayMethod(), locale) %></td>
																	</tr>
																<%
																			}
																		}
																	} else {
																%>
																	<tr><td class="text-center red" colspan="8" style="line-height:50px"><%=Resource.getString("ID_LABEL_NORECORD",locale)%></td></tr>
																
																<% } %>
																
																<tr>
																	<td colspan="5" align="right" style="background:#E6FFD9"><b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></b></td>
																	<td class="center" style="background:#E6FFD9">
																		<b>
																			<%=formatter.format(totalQuantity) %>
																		</b>
																	</td>
																	<td align="center" style="background:#E6FFD9"><b><%=formatter.format(totalAmount) %></b></td>
																	<td align="center" style="background:#E6FFD9"></td>
																</tr>
															</tbody>
														</table>
													</div><!-- /.table-responsive -->
												</div><!-- /span -->
											</div>

										
										<div class="space-4"></div>
									
										<b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%><%=accountData.getTotal() %></b>
										
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
		
		<script src="./assets/js/moment.min.js"></script>
		<script src="./assets/js/daterangepicker.js"></script>
		
		<script language="javascript" type="text/javascript">

			$( document ).ready(function() {

			});
			
			$(function() {

				$( "#checkBtn" ).click(function() { 
				
					var station = $("#station").val();
					if(station == "0") {
						alert ("Please select Station");
						return false;
					}
					
					document.selector.submit(); 
					return false; 
				});
				
		
				
			});
			
			
			$(function() {
				$( "#generateReport" ).click(function() {
					
					var accDT = $( "#accDT" ).val();
					var origin = $( "#origin" ).val();
					var destination = $( "#destination" ).val();
					var payMethod = $( "#payMethod" ).val();

					$( "#loadingReport" ).html("<br/><br/><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i><br/><br/><br/>");

					$(function() {
						$.ajax({
							url: './report',        
							data: "actionType=genSalesReport&accDT="+accDT+"&origin="+origin+"&destination="+destination+"&payMethod="+payMethod,
							success: function(response) {

								if(response.indexOf(".pdf")!=-1) {
									$( "#loadingReport" ).html("<br/><br/><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/salesReport/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a><br/><br/><br/>");
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
			
			
			//date-range-picker plugin		
			
			$('input[name="accDT"]').daterangepicker({
				autoUpdateInput: false,
		    	locale: {
		        	cancelLabel: 'Clear'
		      	},
		    	ranges: {
		            'Today': [moment(), moment()],
		            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
		            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
		            'Last 14 Days': [moment().subtract(13, 'days'), moment()],
		            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
		            'This Month': [moment().startOf('month'), moment().endOf('month')],
		            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
		        }
			});

			$('input[name="accDT"]').on('apply.daterangepicker', function(ev, picker) {
				$(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
			});

			$('input[name="accDT"]').on('cancel.daterangepicker', function(ev, picker) {
			    $(this).val('');
			});


		</script>
		
	</body>
</html>
