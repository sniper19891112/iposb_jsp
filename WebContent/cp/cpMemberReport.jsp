<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.report.*,com.iposb.account.*,com.iposb.area.*,com.iposb.consignment.*,java.util.ArrayList,java.text.*,java.util.Date,org.apache.commons.lang3.StringUtils" contentType="text/html; charset=utf-8"%>
<%@include file="../include.jsp" %>

<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

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
	String memberId = request.getParameter("userId") == null ? "" : request.getParameter("userId").toString();
	
	Date date = new Date();
	SimpleDateFormat sdf1, sdf2, sdf3;
	String todayYear, todayMonth, todayDay;
	sdf1 = new SimpleDateFormat("yyyy");
	todayYear = sdf1.format(date);
	sdf2 = new SimpleDateFormat("MM");
	todayMonth = sdf2.format(date);
	sdf3 = new SimpleDateFormat("dd");
	todayDay = sdf3.format(date);
	
	
	//Paging
	String searchMember = "";
	if(memberId != "") {
		searchMember = "accDT="+accDT+"&userId="+memberId;
	}
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>Member Report</title>
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
							<li>Member Report</li>							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./memberreport" method="get" class="form-horizontal">
									
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
											<label class="col-sm-2 control-label no-padding-right" for="userId"> <%=Resource.getString("ID_LABEL_USERID",locale)%> <span class="red">*</span></label>
											<div class="col-sm-3">
												<input class="form-control" id="userId" name="userId" type="text" value="<%=memberId %>">
											</div>
										</div>
										
										<div class="space-4"></div>
											
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
																	<th class="center"><%=Resource.getString("ID_LABEL_WEIGHT",locale)%> (KG)</th>
																	<th class="center"><%=Resource.getString("ID_LABEL_AMOUNT",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_PAYMETHODTXT",locale)%></th>
																</tr>
															</thead>

															<tbody>
																<%
																	int totalQuantity = 0;
																	double totalWeight = 0.0;
																	double totalAmount = 0.0;
												
																	if( (data != null && data.size() > 0) && (memberId.length() > 0) ){
																		for(int i = 0; i < data.size(); i++){
																			accountData = (AccountDataModel)data.get(i);
																%>
																	<tr onMouseOver="this.bgColor='#E6FFD9';" onMouseOut="this.bgColor='#FFFFFF';">
																		<td align="center"><%=accountData.getCreateDT().length() > 10 ? accountData.getCreateDT().substring(0, 10) : accountData.getCreateDT() %></td>
																		<td align="center">
																			<a href="./consignment?actionType=searchConsignment&consignmentNo=<%=accountData.getConsignmentNo() %>" target="_blank"><%=accountData.getConsignmentNo() %></a>
																		</td>
																		<td align="center"><%=accountData.getGeneralCargoNo() %></td>
																		<td align="center"><%=accountData.getSenderName() %></td>
																		<td><%=accountData.getSenderAreaname().equals("") ? "?" : accountData.getSenderAreaname() %> - <%=accountData.getReceiverAreaname().equals("") ? "?" : accountData.getReceiverAreaname() %><input type="hidden" id="stationCode" value="<%=AreaBusinessModel.parseAreaCode(accountData.getSenderArea()) %>"></td>
																		<td align="center"><%=accountData.getQuantity() %><% totalQuantity += accountData.getQuantity(); %></td>
																		<td align="center"><%=accountData.getWeight() %><% totalWeight += accountData.getWeight(); %></td>
																		<td align="center"><%=formatter.format(accountData.getAmount()) %><% totalAmount += accountData.getAmount(); %></td>
																		<td align="center"><%=ConsignmentBusinessModel.parsePayMethod(accountData.getPayMethod(), locale) %></td>
																	</tr>
																<%
																		}
																	} else {
																%>
																	<tr><td class="text-center red" colspan="8" style="line-height:50px"><%=Resource.getString("ID_LABEL_NORECORD",locale)%></td></tr>
																
																<% } %>
																
																<tr>
																	<td colspan="5" align="right" style="background:#E6FFD9"><b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></b></td>
																	<td class="center" style="background:#E6FFD9">
																		<b><%=formatter.format(totalQuantity) %></b>
																	</td>
																	<td class="center" style="background:#E6FFD9">
																		<b><%=formatter.format(totalWeight) %></b>
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
										
										<% if(accountData.getTotal() <= 20) { %>
											<b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%><%=accountData.getTotal() %></b>
										<% } else { %>
										
										<div class="text-center">
												
											<%
												//分頁
												int numPerPage = 20; //每頁顯示數量
												String pnoStr = request.getParameter("page");
												Integer pno = pnoStr == null ? 1 : Integer.parseInt(pnoStr);
												pager.setPageNo(pno.intValue());
												pager.setPerLen(numPerPage);
												int total = 0;
												int thisPage = 0;
												String p = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
												
												//防止user亂輸入頁碼
												boolean isNum = ReportController.isNumeric(p);
												
												if(isNum){
													thisPage = Integer.parseInt(p);
												}
												if( !(thisPage > 0) || !(isNum) ){
													thisPage = 1;
												}
												
												
												int prev10 = 0;
												int next10 = 0;
												int this10start = 0;
												if( pno%2==0 || pno%2==1 ){//整數
												  prev10 = (int) (Math.floor( (pno-10)/10.1 ) * 10) + 1; //上10頁
												  next10 = (int) (Math.floor( (pno+10)/10.1 ) * 10) + 1;//下10頁
												  this10start = (int) (Math.floor( (pno)/10.1 ) * 10) + 1; //當天10頁的開始頁數 eg. 1, 21, 31, ...
												} else {//有小數
												  prev10 = (int) (Math.floor( (pno-10)/10 ) * 10) + 1; //上10頁
												  next10 = (int) (Math.floor( (pno+10)/10 ) * 10) + 1;//下10頁
												  this10start = (int) (Math.floor( (pno)/10 ) * 10) + 1; //當天10頁的開始頁數 eg. 1, 21, 31, ...
												}
	
											
												total = accountData.getTotal();
												pager.setData(total);//设置数据
												int []range = pager.getRange(numPerPage);//获得页码呈现的区间
												if(pager.getMaxPage()>1) {//要呈现的页面超过1页，需要分页呈现
													out.print("<ul class=\"pagination\">");
														
													//上10頁按鍵
													if(thisPage > 1){
														out.print("<li><a href=\"./memberreport?" + searchMember + "&page="+prev10+"\" rel=\"tooltip\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
													} else { //本頁
														out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
													}
													
													//中間10頁													
													for(int i=this10start; i<this10start+10; i++){
														if(i<=pager.getMaxPage()){
															if(thisPage==i){//當頁
																out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
															} else {
																out.print("<li><a href=\"./memberreport?" + searchMember + "&page="+i+"\">"+i+"</a></li>");
															}
														}
													}
													
													//下10頁按鍵
													if(thisPage < pager.getMaxPage()){
														out.print("<li><a href=\"./memberreport?" + searchMember + "&page="+(next10)+"\" rel=\"tooltip\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
													}
													
													out.print("</ul>");
											   }
											%>
												
										</div>
										
										<% } %>
										
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
				
					var userId = $("#userId").val();
					if(userId == "") {
						alert ("Please input userId");
						return false;
					}
					
					document.selector.submit(); 
					return false; 
				});
				
		
				
			});
			
			
			$(function() {
				$( "#generateReport" ).click(function() {
					
					var accDT = $( "#accDT" ).val();
					var memberId = $( "#userId" ).val();

					$( "#loadingReport" ).html("<br/><br/><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i><br/><br/><br/>");

					$(function() {
						$.ajax({
							url: './report',        
							data: "actionType=genMemberReport&accDT="+accDT+"&memberId="+memberId,
							success: function(response) {

								if(response.indexOf(".pdf")!=-1) {
									$( "#loadingReport" ).html("<br/><br/><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/memberReport/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a><br/><br/><br/>");
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
