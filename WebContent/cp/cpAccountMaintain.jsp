<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.account.*,com.iposb.area.*,com.iposb.consignment.*,java.util.ArrayList,java.text.*,java.util.Date" contentType="text/html; charset=utf-8"%>
<%@include file="../include.jsp" %>

<%		
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();

	NumberFormat formatter = new DecimalFormat("#,###,###.00");
	
	String year = request.getParameter("year") == null ? "" : request.getParameter("year").toString();
	String month = request.getParameter("month") == null ? "" : request.getParameter("month").toString();
	String day = request.getParameter("day") == null ? "" : request.getParameter("day").toString();
	int station = Integer.parseInt(request.getParameter("station") == null ? "0" : request.getParameter("station").toString());	
	String payMethod = request.getParameter("payMethod") == null ? "0" : request.getParameter("payMethod").toString();
	
	
	Date date = new Date();
	SimpleDateFormat sdf1, sdf2, sdf3;
	String todayYear, todayMonth, todayDay;
	sdf1 = new SimpleDateFormat("yyyy");
	todayYear = sdf1.format(date);
	sdf2 = new SimpleDateFormat("MM");
	todayMonth = sdf2.format(date);
	sdf3 = new SimpleDateFormat("dd");
	todayDay = sdf3.format(date);
	
	if(year.equals("")){
		year = todayYear;
	}
	if(month.equals("")){
		month = todayMonth;
	}
	
	if(month.length()==1){
		month = "0" + month; //避免大意只輸入一個數字，幫加0在前面
	}
	
	if(day.length()==1){
		day = "0" + day; //避免大意只輸入一個數字，幫加0在前面
	}
	
	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(AccountController.OBJECTDATA);
    AccountDataModel accountData = new AccountDataModel();
	if(data != null && data.size() > 0){
		accountData = (AccountDataModel)data.get(0);
		errmsg = accountData.getErrmsg();
	}
	
	//String all_calendar = "";	
	//String all_proof = "";
	//String all_btn = "";
	double totalComm = 0.0;
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_ACCOUNT",locale)%></title>
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
							<li><%=Resource.getString("ID_LABEL_ACCOUNT",locale)%></li>
							<li><a href="./account?year=<%=year %>&month=<%=month %>&payMethod=<%=payMethod %>"><%=Resource.getString("ID_LABEL_ACCOUNTSUMMARY",locale)%></a></li>
							<li class="active"><%=Resource.getString("ID_LABEL_EDIT",locale)%></li>
							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<div id="result"></div>
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./accountMaintain" method="get" class="form-horizontal">
									
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="year"> <%=Resource.getString("ID_LABEL_YEAR",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="year" name="year">
													<option value="2015" <% if(year.equals("2015")){out.print("selected");}%>>2015</option>
													<option value="2016" <% if(year.equals("2016")){out.print("selected");}%>>2016</option>
													<option value="2017" <% if(year.equals("2017")){out.print("selected");}%>>2017</option>
													<option value="2018" <% if(year.equals("2018")){out.print("selected");}%>>2018</option>
													<option value="2019" <% if(year.equals("2019")){out.print("selected");}%>>2019</option>
													<option value="2020" <% if(year.equals("2020")){out.print("selected");}%>>2020</option>
													<option value="2021" <% if(year.equals("2021")){out.print("selected");}%>>2021</option>
												</select>
											</div>
										</div>

										<div class="space-4"></div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_MONTH",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="month" name="month">
													<% 
														String numI = "";
														for(int i = 1; i <= 12; i++) { 
															numI = i<10 ? "0"+i : String.valueOf(i);
													%>
														<option value="<%=numI %>" <%if(month.equals(numI)){out.print("selected");}%>><%=numI %></option>
													<% } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>

										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_DAY",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="day" name="day">
													<option value="" <% if(day.equals("")){out.print("selected");} %>> --- <%=Resource.getString("ID_LABEL_ALL",locale)%> --- </option>
													<% 
														String numJ = "";
														for(int j = 1; j <= 31; j++) { 
															numJ = j<10 ? "0"+j : String.valueOf(j);
													%>
														<option value="<%=numJ %>" <%if(day.equals(numJ)){out.print("selected");}%>><%=numJ %></option>
													<% } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
													
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_STATION",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="station" name="station">
													<option value="0" <% if(station==0){out.print("selected");} %>> --- <%=Resource.getString("ID_LABEL_ALL",locale)%> --- </option>
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
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_PAYMETHODTXT",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="payMethod" name="payMethod">
													<option value="" <% if(payMethod.equals("")){out.print("selected");} %>> --- <%=Resource.getString("ID_LABEL_ALL",locale)%> --- </option>
				                                	<option value="0" <% if(payMethod.equals("0")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_UNKNOWN",locale)%></option>
				                                	<option value="1" <% if(payMethod.equals("1")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCASH",locale)%></option>
													<option value="2" <% if(payMethod.equals("2")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYONLINE",locale)%></option>
													<option value="3" <% if(payMethod.equals("3")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYBANKIN",locale)%></option>
													<option value="4" <% if(payMethod.equals("4")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale)%></option>
													<option value="5" <% if(payMethod.equals("5")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCOD",locale)%></option>
													<option value="6" <% if(payMethod.equals("6")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_FREIGHTCHARGES",locale)%></option>
													<option value="7" <% if(payMethod.equals("7")){out.print("selected");} %>><%=Resource.getString("ID_LABEL_COMPANYMAIL",locale)%></option>
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
									
									<div class="space-24"></div>

									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_EDIT", locale)%> <%=Resource.getString("ID_LABEL_TURNOVERDETAILS", locale)%></h3>
										
									<div class="row">
										<div class="col-xs-12">
											<div class="table-responsive">
												<table id="tour-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_FROMTO",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_AMOUNT",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ACCOUNTDATE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_REMARK",locale)%></th>
															<th class="center"></th>
														</tr>
													</thead>

													<tbody>
														<%
															String consignmentNo = "";
															String statusTXT = "";
												
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																	accountData = (AccountDataModel)data.get(i);
																	consignmentNo = accountData.getConsignmentNo();
																	statusTXT = ConsignmentBusinessModel.parseStatus(accountData.getStatus(), locale);
																	//all_calendar += "#payDate_"+consignmentNo+"," + "#rcvDate_"+consignmentNo+"," + "#invDate_"+consignmentNo+",";
																	//all_proof += "#payProof_"+consignmentNo+"," + "#rcvProof_"+consignmentNo+",";
																	//all_btn += "#btn_"+consignmentNo+",";
																	//if(i==data.size()-1) { //last
																	//	all_calendar = all_calendar.substring(0, all_calendar.length()-1);
																	//	all_proof = all_proof.substring(0, all_proof.length()-1);
																	//	all_btn = all_btn.substring(0, all_btn.length()-1);
																	//}
														%>
															<tr>
																<td align="center"><%=accountData.getCreateDT() %></td>
																<td align="center"><%=consignmentNo %></td>
																<td align="center"><%=accountData.getSenderName() %></td>
																<td align="center"><%=accountData.getSenderAreaname().equals("") ? "?" : accountData.getSenderAreaname() %> - <%=accountData.getReceiverAreaname().equals("") ? "?" : accountData.getReceiverAreaname() %></td>
																<td align="center"><%=accountData.getAmount() %></td>
																<td align="center"><input class="form-control date-picker" id="accDT_<%=consignmentNo %>" name="accDT_<%=consignmentNo %>" value="<%=accountData.getAccDT() %>" maxlength="10" type="text" style="width:90px" data-date-format="yyyy-mm-dd" /></td>
																<td align="center"><textarea id="remark_<%=consignmentNo %>" name="remark_<%=consignmentNo %>"><%=accountData.getRemark() %></textarea></td>
																<td align="center">
																	<button id="btn_<%=consignmentNo %>" class="btn btn-app btn-warning btn-xs radius-4">
																		<i class="icon-save"></i>
																	</button>
																</td>
															</tr>
														<%
																}
															}
														%>
													</tbody>
												</table>
											</div><!-- /.table-responsive -->
										</div><!-- /span -->
									</div>
										
									
									<div class="space-4"></div>
									
									<div class="center">
										<a class="btn" id="backBtn">
											<i class="fa"></i>
											<%=Resource.getString("ID_LABEL_BACK",locale)%>
										</a>
										<a class="btn btn-primary" id="overallBtn">
											<i class="fa fa-list"></i>
											<%=Resource.getString("ID_LABEL_OVERALLSTATEMENT",locale)%>
										</a>											
									</div>
									

									</div>
								
								
									<form id="commForm" name="commForm" action="./account" method="post">
										<input type="hidden" id="actionType" name="actionType" value="updateProfit" />
										<input type="hidden" id="year" name="year" value="<%=year %>" />
										<input type="hidden" id="month" name="month" value="<%=month %>" />
										<input type="hidden" id="day" name="day" value="<%=day %>" />
										<input type="hidden" id="consignmentNo" name="consignmentNo" />										
										<input type="hidden" id="accDT" name="accDT" />
										<input type="hidden" id="station" name="station" value="<%=accountData.getAid() %>" />
										<input type="hidden" id="payMethod" name="payMethod" value="<%=accountData.getPayMethod() %>" />
										<input type="hidden" id="remark" name="remark" />
									</form>
			
								
									<div id="invalid"></div>
									
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		


		<script src="plugins/ace/js/date-time/bootstrap-datepicker.min.js"></script>

<g:compress>		
		<script language="javascript" type="text/javascript">

			$( document ).ready(function() {
				<%
					if(errmsg.length() > 0){
						if(errmsg.equals("updateSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATESUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("noConnection")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>
			});
			
			$(document).ready(function(){

			}); 
			
		
			$(function() {
				$( "#checkBtn" ).click(function() { document.selector.submit(); return false; });
			});
			
			$(function() {
				$( "#overallBtn" ).click(function() { location.href='./account?year=<%=year %>&month=<%=month %>'; return false; });
			});
			
			$(function() {
				$( "#backBtn" ).click(function() { location.href='./account?year=<%=year %>&month=<%=month %>&payMethod=<%=payMethod %>'; return false; });
			});
			
			$(function() {
				$('[id^=btn_]').on('click', '', function() {
					code = this.id;
					updateProfit(code.substring(4, code.length));
				});
			});
			
			function updateProfit(code){

				var accD = document.getElementById("accDT_"+code);				
				document.getElementById("btn_"+code).disabled = true;
				document.getElementById("btn_"+code).innerHTML = "<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>";
				document.getElementById("consignmentNo").value = code;
				document.getElementById("accDT").value = accD.value;
				document.getElementById("remark").value = document.getElementById("remark_"+code).value;
				document.getElementById("commForm").submit();
			};
			
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
</g:compress>

	</body>
</html>
