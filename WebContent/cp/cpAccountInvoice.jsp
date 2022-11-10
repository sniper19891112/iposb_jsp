<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.account.*,com.iposb.partner.*,java.util.ArrayList,java.text.*,java.util.Date,org.apache.commons.lang3.StringUtils" contentType="text/html; charset=utf-8"%>
<%@include file="../include.jsp" %>

<%	
	
	String errmsg = "";
	double firstXkg = 0.0; 
	double addRM = 0.0;
	AccountDataModel accountData = new AccountDataModel();
	ArrayList data = (ArrayList)request.getAttribute(AccountController.OBJECTDATA);	
	if(data != null && data.size() > 0){
		accountData = (AccountDataModel)data.get(0);
		firstXkg = accountData.getFirst();
		addRM = accountData.getAdditionPrice();
		errmsg = accountData.getErrmsg();
	}
	
	ArrayList <PartnerDataModel> partnerData = (ArrayList)request.getAttribute("partner");
	PartnerDataModel pData = new PartnerDataModel();

	NumberFormat formatter = new DecimalFormat("#,###,##0.00");

	int partnerId = Integer.parseInt(request.getParameter("partnerId") == null ? "0" : request.getParameter("partnerId").equals("") ? "0" : request.getParameter("partnerId").toString());	
	String invoiceDateRange = request.getParameter("invoiceDateRange") == null ? "" : request.getParameter("invoiceDateRange").toString();
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_INVOICE",locale)%></title>
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
							<li><%=Resource.getString("ID_LABEL_ACCOUNT",locale)%></li>		
							<li><%=Resource.getString("ID_LABEL_INVOICE",locale)%></li>							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./invoice" method="get" class="form-horizontal">
									
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="invoiceDateRange"> <%=Resource.getString("ID_LABEL_DATE",locale)%> </label>
											<div class="col-sm-3">
												<div class="input-group">
													<input class="form-control" id="invoiceDateRange" name="invoiceDateRange" type="text" value="<%=invoiceDateRange %>">
													<span class="input-group-addon">
														<i class="icon-calendar bigger-110"></i>
													</span>
												</div>
											</div>
										</div>
										
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="partnerId"> <%=Resource.getString("ID_LABEL_NETWORKPARTNER",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="partnerId" name="partnerId">
					                                <% 
						                                if(partnerData != null && !partnerData.isEmpty()){
															for(int i = 0; i < partnerData.size(); i ++){
																pData = partnerData.get(i);
					                                %>
														<option value="<%=pData.getPid() %>" <% if(partnerId==pData.getPid()){out.print("selected");} %>><%=pData.getCname() %></option>
													<% } } %>
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
															<tr>
															    <td rowspan="2" style="text-align:center; font-weight:700"><%=Resource.getString("ID_LABEL_NUM",locale)%></td>
															    <td rowspan="2" style="text-align:center; font-weight:700"><%=Resource.getString("ID_LABEL_DATE",locale)%></td>
															    <td rowspan="2" style="text-align:center; font-weight:700; width:12%"><%=Resource.getString("ID_LABEL_GENERALCARGOCNNUMBER",locale)%></td>
															    <td rowspan="2" style="text-align:center; font-weight:700"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%></td>
															    <td rowspan="2" style="text-align:center; font-weight:700; width:12%"><%=Resource.getString("ID_LABEL_WEIGHT",locale)%> (KG)</td>
															    <td rowspan="2" style="text-align:center; font-weight:700; width:12%"><%=Resource.getString("ID_LABEL_TC020",locale)%> (RM)</td>
															    <td colspan="2" style="text-align:center; font-weight:700; width:24%"><%=Resource.getString("ID_LABEL_DELIVERYCHARGE",locale)%></td>
															    <td rowspan="2" style="text-align:center; font-weight:700; width:12%"><%=Resource.getString("ID_LABEL_COLLECTIONCOD",locale)%> (RM)</td>
															    <td rowspan="2" style="text-align:center; font-weight:700; width:12%"><%=Resource.getString("ID_LABEL_TOTAL",locale)%> (RM)</td>
															</tr>
															<tr>
															    <td style="text-align:center; font-weight:700"><%=Resource.getString("ID_LABEL_FIRSTXKG", String.valueOf(firstXkg), locale)%> (RM)</td>
															    <td style="text-align:center; font-weight:700"><%=Resource.getString("ID_LABEL_ADDPERKG", String.valueOf(addRM), locale)%> (RM)</td>
															</tr>

															<tbody>
																<%
																	int num = 0;
																	String numTxt = "";
																	String thisDT = "";
																	String dateTxt = "";
																	int totalQty = 0;
																	double totalWeight = 0.0;
																	double totalTc = 0.0;
																	double totalFirstPrice = 0.0;
																	double totalAdditionPrice = 0.0;
																	double totalCod = 0.0;
																	double totalAmt = 0.0;
																	if( (data != null && data.size() > 0) && (invoiceDateRange.length() > 0) ){
																		for(int i = 0; i < data.size(); i++){
																			accountData = (AccountDataModel)data.get(i);
																			double subtotal = 0.0;
																			
																			if(!thisDT.equals(accountData.getCreateDT())){
																				num++;
																				numTxt = String.valueOf(num);
																				dateTxt = accountData.getCreateDT();
																			} else {
																				numTxt = "";
																				dateTxt = "";
																			}
																%>
																	<tr>
																		<td align="center"><%=numTxt %></td>
																		<td align="center"><%=dateTxt %></td>
																		<td align="center"><a href="./consignment?actionType=searchConsignment&consignmentNo=<%=accountData.getConsignmentNo() %>" target="_blank"><%=accountData.getGeneralCargoNo() %></a></td>
																		<td align="center"><%=accountData.getQuantity() %><% totalQty += accountData.getQuantity(); %></td>
																		<td align="right"><%=formatter.format(accountData.getWeight()) %><% totalWeight += accountData.getWeight(); %></td>
																		<td align="right"><%=formatter.format(accountData.getWeight()*0.2) %><% subtotal += accountData.getWeight()*0.2; %><% totalTc += accountData.getWeight()*0.2; %></td>
																		<td align="right"><%=formatter.format(accountData.getFirstPrice()) %><% subtotal += accountData.getFirstPrice(); %><% totalFirstPrice += accountData.getFirstPrice(); %></td>
																		<td align="right">
																			<%
																				double additionWeight = accountData.getWeight() - accountData.getFirst();
																				double additionPrice = accountData.getAdditionPrice();
																				if(additionWeight > 0) {
																					subtotal += additionWeight * additionPrice;
																					totalAdditionPrice += additionWeight * additionPrice;
																					out.print(formatter.format(additionWeight * additionPrice));
																				} else {
																					out.print("-");
																				}
																			%>
																		</td>
																		<td align="right">
																			<%
																				if( (accountData.getTerms().equals("CC")) || (accountData.getPayMethod()==5) || (accountData.getPayMethod()==6) ) {
																					subtotal += accountData.getCodPrice();
																					totalCod += accountData.getCodPrice();
																					out.print(accountData.getCodPrice());
																				} else {
																					out.print("-");
																				}
																			
																			%>
																		</td>
																		<td align="right"><b><%=formatter.format(subtotal) %><% totalAmt += subtotal; %></b></td>
																	</tr>
																<% 
																			thisDT = accountData.getCreateDT();//為了下一筆資料的比對
																		}
																%>
																		
																	<tr>
																		<td colspan="3" align="right" style="background:#E6FFD9"><b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></b></td>
																		<td class="center" style="background:#E6FFD9"><%=totalQty %></td>
																		<td align="right" style="background:#E6FFD9"><%=formatter.format(totalWeight) %></td>
																		<td align="right" style="background:#E6FFD9"><%=formatter.format(totalTc) %></td>
																		<td align="right" style="background:#E6FFD9"><%=formatter.format(totalFirstPrice) %></td>
																		<td align="right" style="background:#E6FFD9"><%=formatter.format(totalAdditionPrice) %></td>
																		<td align="right" style="background:#E6FFD9"><%=formatter.format(totalCod) %></td>
																		<td align="right" style="background:#E6FFD9"><b><%=formatter.format(totalAmt) %></b></td>
																	</tr>
																	
																<%
																	} else { //沒有資料
																%>
																	<tr><td class="text-center red" colspan="10" style="line-height:50px"><%=Resource.getString("ID_LABEL_NORECORD",locale)%></td></tr>
																
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
											
											<% if( ((priv == 99)||(priv == 9)) && ((data != null && data.size() > 0)) ){ //有資料，然後是admin或account才能產生 %>
												<span>
													<button class="btn btn-lg btn-success" id="generateInvoice">
														<i class="ace-icon fa fa-file-excel-o"></i>
														<%=Resource.getString("ID_LABEL_GENERATEINVOICE",locale) %>
													</button>
													<span id="loadingInvoice"></span>
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
				
					var invoiceDateRange = $("#invoiceDateRange").val();
					var station = $("#station").val();
					
					if(invoiceDateRange == "") {
						alert ("Please select a date range");
						return false;
					}
					
					document.selector.submit(); 
					return false; 
				});
				
		
				
			});
			
			
			$(function() {
				$( "#generateInvoice" ).click(function() {
					
					var invoiceDateRange = $( "#invoiceDateRange" ).val();
					var partnerId = $( "#partnerId" ).val();

					$( "#loadingInvoice" ).html("<br/><br/><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i><br/><br/><br/>");

					$(function() {
						$.ajax({
							url: './account',        
							data: "actionType=genPartnerInvoice&invoiceDateRange="+invoiceDateRange+"&partnerId="+partnerId,
							success: function(response) {

								if(response.indexOf(".xlsx")!=-1) {
									$( "#loadingInvoice" ).html("<br/><br/><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/partnerInvoice/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDEXCEL",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/excel.png' border='0' /></a><br/><br/><br/>");
								} else if(response=="NoConnection") {
									$( "#loadingInvoice" ).html("<br/><%=Resource.getString("ID_LABEL_NOCONNECTION",locale)%>");
								} else if(response=="noData") {
									$( "#loadingInvoice" ).html("<br/><%=Resource.getString("ID_MSG_NODATA",locale)%>");
								} else {
									$( "#loadingInvoice" ).html("<br/>"+response);
								}
								
							}
						});
					});
					
				});
			});
			
			
			//date-range-picker plugin		
			
			$('input[name="invoiceDateRange"]').daterangepicker({
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

			$('input[name="invoiceDateRange"]').on('apply.daterangepicker', function(ev, picker) {
				$(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
			});

			$('input[name="invoiceDateRange"]').on('cancel.daterangepicker', function(ev, picker) {
			    $(this).val('');
			});


		</script>
		
		
		
	</body>
</html>
