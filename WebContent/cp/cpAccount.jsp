<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.account.*,com.iposb.area.*,com.iposb.consignment.*,java.util.ArrayList,java.text.*,java.util.Date,org.apache.commons.lang3.StringUtils" contentType="text/html; charset=utf-8"%>
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
	
	String year = request.getParameter("year") == null ? "" : request.getParameter("year").toString();
	String month = request.getParameter("month") == null ? "" : request.getParameter("month").toString();
	String day = request.getParameter("day") == null ? "" : request.getParameter("day").toString();
	int origin = Integer.parseInt(request.getParameter("origin") == null ? "2" : request.getParameter("origin").toString());	
	int destination = Integer.parseInt(request.getParameter("destination") == null ? "0" : request.getParameter("destination").toString());	
	String payMethod = request.getParameter("payMethod") == null ? "" : request.getParameter("payMethod").toString();	
	String filter = request.getParameter("filter") == null ? "1" : request.getParameter("filter").equals("") ? "1" : request.getParameter("filter").toString();	
	
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
	
	
	if(todayMonth.length()==1){
		todayMonth = "0" + todayMonth; //避免大意只輸入一個數字，幫加0在前面
	}
	if(month.length()==1){
		month = "0" + month; //避免大意只輸入一個數字，幫加0在前面
	} else if (month.equals("")){
		month = todayMonth;
	}
	
	
	if(todayDay.length()==1){
		todayDay = "0" + todayDay; //避免大意只輸入一個數字，幫加0在前面
	}
	if(day.length()==1){
		day = "0" + day; //避免大意只輸入一個數字，幫加0在前面
	} else if (day.equals("")){
		day = todayDay;
	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_ACCOUNT",locale)%> <%=Resource.getString("ID_LABEL_ACCOUNTSUMMARY",locale)%></title>
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
							
							<% if (!payMethod.equals("") && AccountController.isNumeric(payMethod) && Integer.parseInt(payMethod) >= 0) { //只列列該公司
									out.print("<li>"+Resource.getString("ID_LABEL_ACCOUNT",locale)+"</li> ");
									out.print("<li><a href=\"./account\">"+Resource.getString("ID_LABEL_ACCOUNTSUMMARY",locale)+"</a></li>"); 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_EDIT",locale)+"</li>"); 
							   } else {
								   out.print("<li>"+Resource.getString("ID_LABEL_ACCOUNT",locale)+"</li> ");
								   out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_ACCOUNTSUMMARY",locale)+"</li>");
							   } 
							%>
														
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
									
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CHECK",locale)%></h3>
								
									<form id="selector" name="selector" action="./account" method="get" class="form-horizontal">
										<input type="hidden" id="filter" name="filter">
									
										<div class="space-4"></div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="year"></label>
											<div class="col-sm-4">

												<div data-toggle="buttons" class="btn-group">
													<button class="btn btn-yellow" id="btn1" type="button"> Yearly </button>
													<button class="btn btn-yellow" id="btn2" type="button"> Monthly </button>
													<button class="btn btn-yellow" id="btn3" type="button"> Daily </button>
												</div>
										
											</div>
										</div>
													
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
										
										<span id="monthSpan">

										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="month"> <%=Resource.getString("ID_LABEL_MONTH",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="month" name="month">
													<option value="" <% if(month.equals("")){out.print("selected");} %>></option>
													<% 
														String numI = "";
														for(int i = 1; i <= 12; i++) { 
															numI = i<10 ? "0"+i : String.valueOf(i);
													%>
														<option value="<%=numI %>" <%if(month.equals(numI) &&(filter == "2" || filter == "3") ){out.print("selected");}%>><%=numI %></option>
													<% } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
										
										</span>
										
										<span id="daySpan">

										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="day"> <%=Resource.getString("ID_LABEL_DAY",locale)%> </label>
											<div class="col-sm-2">
												<select class="form-control" id="day" name="day">
													<option value="" <% if(day.equals("")){out.print("selected");} %>></option>
													<% 
														String numJ = "";
														for(int j = 1; j <= 31; j++) { 
															numJ = j<10 ? "0"+j : String.valueOf(j);
													%>
														<option value="<%=numJ %>" <%if(day.equals(numJ) && filter == "3"){out.print("selected");}%>><%=numJ %></option>
													<% } %>
												</select>
											</div>
										</div>
										
										<div class="space-4"></div>
										
										</span>
										
										<div class="space-24"></div>
													
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
													<option value="" <% if(payMethod.equals("")){out.print("selected");} %>> --- All --- </option>
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
												<span id="errorText" class="text-danger" style="display:none;"></span>
											</div>
										</div>

									</form>
									
									
									<% 
										if ( (!payMethod.equals("") && Integer.parseInt(payMethod) >= 0)) { //只列單一付款方式 
											double totalComm = 0.0;
											double submitTax = 0.0;
									%>
									
										<div class="space-4"></div>
										
										<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_TURNOVERDETAILS", locale)%></h3>
									
										<div class="space-6"></div>
										
										<h5>
										<%=Resource.getString("ID_LABEL_PAYMETHODTXT", locale)%><%=Resource.getString("ID_LABEL_COLON", locale)%>
										<% if(!payMethod.equals("") && Integer.parseInt(payMethod) >= 0){ 
												out.print(ConsignmentBusinessModel.parsePayMethod(accountData.getPayMethod(), locale));
											} else {
												out.print("All");
											}
										%>
										</h5>

											<div class="row">
												<div class="col-xs-12">
													<div class="table-responsive">
														<table id="overall-table" class="table table-striped table-bordered table-hover">
															<thead>
																<tr>
																	<th class="center"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_FROMTO",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%></th>
																	<th class="center"><%=Resource.getString("ID_LABEL_AMOUNT",locale)%></th>
																	<th class="center"></th>
																</tr>
															</thead>

															<tbody>
																<%
																	int totalQuantity = 0;
																	double totalAmount = 0.0;
												
																	if(data != null && data.size() > 0){
																		for(int i = 0; i < data.size(); i++){
																			accountData = (AccountDataModel)data.get(i);
																			
																			if( (accountData.getCreditArea() > 0) && (accountData.getCreditArea() != origin) ) { //如果是credit term user, 則必須根據 creditArea 作歸類，如果creditArea 和 origin 不一樣，則不 show 此筆
																			} else {
																%>
																	<tr onMouseOver="this.bgColor='#E6FFD9';" onMouseOut="this.bgColor='#FFFFFF';">
																		<td align="center"><%=accountData.getCreateDT() %></td>
																		<td align="center">
																			<a href="./consignment?actionType=searchConsignment&consignmentNo=<%=accountData.getConsignmentNo() %>" target="_blank"><%=accountData.getConsignmentNo() %></a>
																		</td>
																		<td align="center"><%=accountData.getSenderName() %></td>
																		<td><%=accountData.getSenderAreaname().equals("") ? "?" : accountData.getSenderAreaname() %> - <%=accountData.getReceiverAreaname().equals("") ? "?" : accountData.getReceiverAreaname() %></td>
																		<td align="center"><%=accountData.getQuantity() %><% totalQuantity += accountData.getQuantity(); %></td>
																		<td align="center"><%=formatter.format(accountData.getAmount()) %><% totalAmount += accountData.getAmount(); %></td>
																		<td align="center">
																			<% if (accountData.getRemark().trim().length() > 0) { %>
																				<i class="fa fa-info-circle orange" rel="tooltip" title="<%=accountData.getRemark().replaceAll("\n", "<br/> ") %>"></i>
																			<% } %>
																		</td>
																	</tr>
																<%
																			}
																		}
																	}
																%>
																
																<tr>
																	<td colspan="4" align="right" style="background:#E6FFD9"><b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></b></td>
																	<td class="center" style="background:#E6FFD9">
																		<b>
																			<%=formatter.format(totalQuantity) %>
																		</b>
																	</td>
																	<td align="center" style="background:#E6FFD9"><b><%=formatter.format(totalAmount) %></b></td>
																	<td colspan="2" align="center" style="background:#E6FFD9"></td>
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
											<a class="btn" id="backBtn">
												<i class="fa"></i>
												<%=Resource.getString("ID_LABEL_BACK",locale)%>
											</a>
											
											<% if( (priv == 99)||(priv == 9) ){ //admin或account才能修改 %>
												<a class="btn btn-primary" id="editBtn">
													<i class="fa fa-edit"></i>
													<%=Resource.getString("ID_LABEL_EDITACCOUNT",locale)%>
												</a>
											<% } %>
											
										</div>
										
										
										</div>
										
									<% } else if (payMethod.equals("")){ //帳目總覽 %>
									
									
										<div class="space-24"></div>
										<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_OVERALLACCOUNT",locale)%></h3>
										
										<div class="row">
											<div class="col-xs-12">
												<div class="table-responsive">
													<table id="overall-table" class="table table-striped table-bordered table-hover">
														<thead>
															<tr>
																<th class="center"><%=Resource.getString("ID_LABEL_PAYMETHODTXT",locale)%></th>
																<th class="center"><%=Resource.getString("ID_LABEL_TOTALCONSIGNMENT",locale)%></th>
																<th class="center"><%=Resource.getString("ID_LABEL_AMOUNT",locale)%></th>
																<th class="center"></th>
															</tr>
														</thead>

														<tbody>
															<%
																int totalBooking = 0;
																double totalTurnover = 0.0;
																double totalOutputTax = 0.0;
																double totalInputTax = 0.0;
																double totalSubmitTax = 0.0;
																double totalProfit = 0.0;
																String linkType = "";
													
																if(data != null && data.size() > 0){
																	for(int i = 0; i < data.size(); i++){
																		accountData = (AccountDataModel)data.get(i);
												
																		if( (accountData.getCreditArea() > 0) && (accountData.getCreditArea() != origin) ) { //如果是credit term user, 則必須根據 creditArea 作歸類，如果creditArea 和 origin 不一樣，則不 show 此筆
																		} else {
																			
																			totalBooking += accountData.getTotal();
																			totalTurnover += accountData.getTurnover();
																			totalProfit += accountData.getProfit();
															%>
																<tr>
																	<td align="center"><%=ConsignmentBusinessModel.parsePayMethod(accountData.getPayMethod(), locale) %></td>
																	<td align="center"><%=formatter.format(accountData.getTotal()) %></td>
																	<td align="center"><%=formatter.format(accountData.getTurnover()) %></td>
																	<td align="center">
																		<div class="visible-md visible-lg hidden-sm hidden-xs">
																			<a class="details" class="blue" href="&origin=<%=origin %>&destination=<%=destination %>&payMethod=<%=accountData.getPayMethod() %>" class="btn btn-primary no-radius">
																				<i class="icon-search"></i>
																				<%=Resource.getString("ID_LABEL_DETAIL",locale)%>
																			</a>
																		</div>
																	</td>
																</tr>
															<% }}} %>
															<tr>
																<td align="right" style="background:#E6FFD9"><b><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></b></td>
																<td align="center" style="background:#E6FFD9"><b><%=formatter.format(totalBooking) %></b></td>
																<td align="center" style="background:#E6FFD9"><b><%=formatter.format(totalTurnover) %></b></td>
																<td align="center" style="background:#E6FFD9"></td>
															</tr>
														</tbody>
													</table>
												</div><!-- /.table-responsive -->
											</div><!-- /span -->
										</div>
										
										<div class="space-4"></div>
									
										<% if( (priv == 99)||(priv == 9) ){ //admin或account才能修改 %>
											<a class="btn btn-primary" id="editBtn">
												<i class="fa fa-edit"></i>
												<%=Resource.getString("ID_LABEL_EDITACCOUNT",locale)%>
											</a>
										<% } %>

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
		
		
		<script language="javascript" type="text/javascript">

			$( document ).ready(function() {
				$('#btn<%=filter %>').addClass( "active" );
				
				if("<%=filter %>"=="1") {
					$('#monthSpan').hide();
					$('#daySpan').hide();
					$('.details').attr("href", function(i, v){
						return "./account?filter=2&year=<%=year %>" + v;
					});
				} else if("<%=filter %>"=="2") {
					$('#daySpan').hide();
					$('#month').val("<%=month %>");
					$('#filter').val("<%=filter %>");
					$('.details').attr("href", function(i, v){
						return "./account?filter=2&year=<%=year %>&month=<%=month %>" + v;
					});
				}else if("<%=filter %>"=="3") {
					$('#month').val("<%=month %>");
					$('#day').val("<%=day %>");
					$('#filter').val("<%=filter %>");
					$('.details').attr("href", function(i, v){
						return "./account?filter=3&year=<%=year %>&month=<%=month %>&day=<%=day %>" + v;
					});
				}
				
			});
			
			$(function() {
				
				$( "#btn1" ).click(function() {
					
				});
				
				$(function() {
					$('[id^=btn]').on('click', '', function() {
						
						$('#btn1').removeClass( "active" );
						$('#btn2').removeClass( "active" );
						$('#btn3').removeClass( "active" );
						
						thisId = this.id;
						num = thisId.substring(3, 4);
						
						if(num==1) {
							$("#errorText").hide();
							$('#month').val("");
							$('#day').val("");
							$('#monthSpan').hide();
							$('#daySpan').hide();
						} else if(num==2) {
							$("#errorText").hide();
							$('#day').val("");
							$('#monthSpan').show();
							$('#daySpan').hide();
							$('#month').val("<%=month %>");
						} else if(num==3) {
							$("#errorText").hide();
							$('#monthSpan').show();
							$('#daySpan').show();
							$('#month').val("<%=month %>");
							$('#day').val("<%=day %>");
						}
						
						$('#filter').val(num);
					});
				});

				$( "#checkBtn" ).click(function() { 

					if($("#btn3").hasClass("active") && ($("#month").val()== "" || $("#day").val()== "")){
						$("#errorText").show();
						$("#errorText").html("<label>Please select a month or day</label>");
						return false;
					} else if($("#btn2").hasClass("active") && $("#month").val()== ""){
						$("#errorText").show();
						$("#errorText").html("<label>Please select a month</label>");
						return false;
					} else {
						$("#errorText").hide();
						document.selector.submit(); return false;
					}
						
				});
				
				$( "#editBtn" ).click(function() { location.href="./accountMaintain?year=<%=year %>&month=<%=month %>&day=<%=day %>&payMethod=<%=payMethod %>"; return false; });

				$( "#backBtn" ).click(function() { location.href='./account?year=<%=year %>&month=<%=month %>&day=<%=day %>'; return false; });				
				
			});

		</script>
		
	</body>
</html>
