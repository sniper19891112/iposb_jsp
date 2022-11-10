<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.privilege.*,com.iposb.area.*,com.iposb.consignment.*,java.util.ArrayList,java.util.Date,java.text.*"%>
<%@include file="../include.jsp" %>

<%		
	if(priv < 9){
    	String url = "./stafflogin?returnUrl=./calendar";
		response.sendRedirect(url);
        return;
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();

	Date date = new Date();
	SimpleDateFormat sdf1, sdf2, sdf3;
	String todayYear, todayMonth, todayDay;
	sdf1 = new SimpleDateFormat("yyyy");
	todayYear = sdf1.format(date);
	sdf2 = new SimpleDateFormat("MM");
	todayMonth = sdf2.format(date);
	sdf3 = new SimpleDateFormat("dd");
	todayDay = sdf3.format(date);
	
	String year = request.getParameter("year") == null ? "" : request.getParameter("year").toString();
	String month = request.getParameter("month") == null ? "" : request.getParameter("month").toString();
	int station = Integer.parseInt(request.getParameter("station") == null ? "2" : request.getParameter("station").toString());	
	
	if(year.equals("") || month.equals("")){
		year = todayYear;
		month = todayMonth;
	}
	
	if(month.length()==1){
		month = "0" + month;
	}
	String eventString = ConsignmentBusinessModel.getConsignmentString4Calendar(year, month, station);
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_CONSIGNMENT",locale)%> (<%=Resource.getString("ID_LABEL_CALENDARVIEW",locale)%>)</title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="cpMeta.jsp" %>
		
		<style>
			#month td
			{
				width:160px;
				height:100px;
				vertical-align:top;
				overflow:auto;
				padding:1px;
			}

			td .day
			{
				width:180px;
				height:150px;
				overflow:auto;
				margin-top:0;
			}

			#month2 td
			{
				width:88px;
				height:48px;
				vertical-align:top;
				overflow:auto;
				padding:1px;
			}
			
			#month th,#month2 th
			{
				font-size:11px;
				height:28px;
				text-align: center;
			}

			#month,#month2
			{
				border-collapse:collapse;
				margin:2em auto;
			}

			#month th,#month td,#month2 th,#month2 td
			{
				border:1px solid #ddd;
			}

			#month thead,#month2 thead
			{
				background-color:#9cf;
			}

			#month tbody .weekend,#month2 tbody .weekend2
			{
				background-color:#EBF9FF;
			}

			#month tbody .next,#month tbody .previous,#month2 tbody .next2,#month2 tbody .previous2
			{
				background-color:#eee;
			}

			#month tbody .today,#month2 tbody .today2
			{
				background-color:#A7FF93;
			}

		</style>
		
		<script type="text/javascript" src="./assets/js/calgen_consignment.js"></script>

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
							<li><a href="./consignment"><%=Resource.getString("ID_LABEL_CONSIGNMENT",locale)%></a></li>
							<li class="active"><%=Resource.getString("ID_LABEL_CALENDARVIEW",locale)%></li>

						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<div align="center">
										<div>
											<form class="form-inline" id="genCalForm" name="genCalForm">
												<input type="hidden" id="todayYear" name="todayYear" value="<%=todayYear %>" />
												<input type="hidden" id="todayMonth" name="todayMonth" value="<%=todayMonth %>" />
												<input type="hidden" id="todayDay" name="todayDay" value="<%=todayDay %>" />
											
												<select id="year" name="year" style="width:68px;">
													<option value="2015" <% if(year.equals("2015")){out.print("selected");}%>>2015</option>
													<option value="2016" <% if(year.equals("2016")){out.print("selected");}%>>2016</option>
													<option value="2017" <% if(year.equals("2017")){out.print("selected");}%>>2017</option>
													<option value="2018" <% if(year.equals("2018")){out.print("selected");}%>>2018</option>
													<option value="2019" <% if(year.equals("2019")){out.print("selected");}%>>2019</option>
													<option value="2020" <% if(year.equals("2020")){out.print("selected");}%>>2020</option>
													<option value="2021" <% if(year.equals("2021")){out.print("selected");}%>>2021</option>
												</select>
												
												<select id="month" name="month" style="width:48px;">
													<option value="01" <% if(month.equals("01") || month.equals("1")) out.print("selected"); %>>01</option>
													<option value="02" <% if(month.equals("02") || month.equals("2")) out.print("selected"); %>>02</option>
													<option value="03" <% if(month.equals("03") || month.equals("3")) out.print("selected"); %>>03</option>
													<option value="04" <% if(month.equals("04") || month.equals("4")) out.print("selected"); %>>04</option>
													<option value="05" <% if(month.equals("05") || month.equals("5")) out.print("selected"); %>>05</option>
													<option value="06" <% if(month.equals("06") || month.equals("6")) out.print("selected"); %>>06</option>
													<option value="07" <% if(month.equals("07") || month.equals("7")) out.print("selected"); %>>07</option>
													<option value="08" <% if(month.equals("08") || month.equals("8")) out.print("selected"); %>>08</option>
													<option value="09" <% if(month.equals("09") || month.equals("9")) out.print("selected"); %>>09</option>
													<option value="10" <% if(month.equals("10")) out.print("selected"); %>>10</option>
													<option value="11" <% if(month.equals("11")) out.print("selected"); %>>11</option>
													<option value="12" <% if(month.equals("12")) out.print("selected"); %>>12</option>
												</select>
												
												<select id="station" name="station" style="width:150px;">
													<% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(station==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
												
												<div style="display:none"><label for="sat"><input type="radio" name="day" id="sat" value="6"> Saturday</label> <label for="sun"><input type="radio" name="day" id="sun" value="0" checked> Sunday</label> <label for="mon"><input type="radio" name="day" id="mon" value="1" checked="checked"> Monday</label></div>

												<button id="submitBtn" class="btn btn-purple btn-sm">
													<%=Resource.getString("ID_LABEL_CHECK",locale)%>
													<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
												</button>
											</form>
										</div>
									</div>
								
									<span id="generateCalHere"><div align="center"><i class="fa fa-circle-o-notch fa-spin fa-2x"></i></div></span>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		<script type="text/javascript" src="./assets/js/jquery.form.js"></script>
		
		<!--=== Start Upload Image Modal ===-->
         <div class="modal fade" id="uploadImageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title"><%=Resource.getString("ID_LABEL_UPLOADBANKSLIP",locale) %></h4>
                        </div>
                        <div class="modal-body">
                            <form id="imageForm" method="post" enctype="multipart/form-data" action="./upload" class="form-horizontal">
								<input type="file" id="upImg" name="upImg" value="" style="width:0;top:-999;position: absolute;left: -999px;"/>

								<div class="form-group">
									<div class="col-sm-4 text-right"><h5><%=Resource.getString("ID_LABEL_DATE",locale)%></h5></div>

									<div class="col-sm-8">
										<h5><span id="thisDT"></span></h5>
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-sm-4 text-right"><h5><%=Resource.getString("ID_LABEL_AMOUNT",locale)%> (RM)</h5></div>

									<div class="col-sm-8">
										<input type="text" id="amount" name="amount">
									</div>
								</div>
								
								<div class="form-group">
									<div class="col-sm-4 text-right"><h5><%=Resource.getString("ID_LABEL_REMARK",locale)%> </h5></div>

									<div class="col-sm-8">
										<input type="text" id="remark" name="remark" maxlength="50" style="width:100%">
									</div>
								</div>

							</form>
						</div>
						<div class="modal-footer">
							<div id="uploadSuccess" class="success" style="display:none;padding:10px;background-color:#D6E9C6;margin-bottom:13px;"></div>
							<div id="uploadError"  class="error" style="display:none;padding:10px;background-color:#D15B47;margin-bottom:13px;"></div>
							<div id="loading"></div>
							<div class="margin-bottom-20"></div>
							<button type="button" class="btn btn-info" id="uploadImageBtn"><%=Resource.getString("ID_LABEL_SELECTFILE",locale) %></button>
							<button type="button" id="closeBtn" class="btn btn-default" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
						</div>
                	</div>
                </div>
            </div>
		<!--=== End Upload Image Modal ===-->


		<!-- inline scripts related to this page -->

		<script language="javascript" type="text/javascript">
		
			$(document).ready(function () {
				
				setCal(document.genCalForm, '<%=eventString %>', '<%=year %>', '<%=month %>');

			});

			function view(){
				var Y = $("#year").val();
				var M = $("#month").val();
				var S = $("#station").val();
				window.location.href="./calendar?year="+Y+"&month="+M+"&station="+S;
			};
			
			$(function() {
				$( "#submitBtn" ).click(function() { view(); return false; });
			});

		</script>
		
		
		
	</body>
</html>
