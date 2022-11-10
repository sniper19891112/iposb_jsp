<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.privilege.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%
	if(priv < 5){
		String url = "./";
		response.sendRedirect(url);
	    return;
	}
	
	LogonDataModel logonData = new LogonDataModel();
	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);	
	if(data != null && data.size() > 0){
		logonData = (LogonDataModel)data.get(0);
	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></title>
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
							<li class="active"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<!-- PAGE CONTENT BEGINS -->
							
							<div class="col-xs-12" id="cp">
									<div class="col-xs-12 center" style="margin: 10px">
										<font size="36px"><%=Resource.getString("ID_LABEL_WELCOMETOCP",locale)%></font>
									</div>
									
									
									<div class="col-xs-12 center"></div>
									
									<div class="col-xs-12 center">
										<form id="searchRecord" name="searchRecord" method="get" action="./consignment">
											<div class="row">
												<div class="col-xs-4">
													
												</div>
												<div class="col-xs-4">
													<div class="input-group">
														<input type="hidden" id="actionType" name="actionType" value="">
														<input type="text" style="min-width:150px" class="form-control search-query text-uppercase" id="consignmentNo" name="consignmentNo" maxlength="50" placeholder="<%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%>" />
														
														<span class="input-group-btn">
															<button id="searchBtn" class="btn btn-purple btn-sm">
																<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
															</button>
														</span>
													</div>
												</div>
												<div class="col-xs-4">
													
												</div>
											</div>
										</form>
									</div>
									
									<div class="col-xs-4 center"></div>
									
									<div class="col-xs-12 center" style="margin-top:80px">
									
										<button class="btn btn-app btn-info" onClick="worksheet(1)" <% if(logonData.getStage1()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-cube bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNPICKUP",locale)%>
										</button>
										
										<button class="btn btn-app btn-success" onClick="worksheet(2)" <% if(logonData.getStage2()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-home bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNSTATION",locale)%>
										</button>
										
										<button class="btn btn-app btn-danger" onClick="worksheet(3)" <% if(logonData.getStage3()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-upload bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNLOAD",locale)%>
										</button>
										
										<button class="btn btn-app btn-warning" onClick="worksheet(4)" <% if(logonData.getStage4()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-refresh bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNEXCHANGE",locale)%>
										</button>
										
										<button class="btn btn-app btn-pink" onClick="worksheet(5)" <% if(logonData.getStage5()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-download bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNUNLOAD",locale)%>
										</button>
										
										<button class="btn btn-app btn-primary" onClick="worksheet(6)" <% if(logonData.getStage6()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-truck bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNDISTRIBUTE",locale)%>
										</button>
										
										<button class="btn btn-app btn-purple" onClick="worksheet(7)" <% if(logonData.getStage7()!=1){out.print("disabled");} %>>
											<i class="ace-icon fa fa-check-square bigger-230"></i>
											<%=Resource.getString("ID_LABEL_BTNDELIVER",locale)%>
										</button>
									</div>
							</div>
							
							
							
							<div class="col-xs-12" id="stageSheet1" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNPICKUP",locale)%></h1>
								<form id="formStage1" class="form-horizontal" role="form">
																							
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="weight"> <%=Resource.getString("ID_LABEL_WEIGHT",locale)%> (KG) </label>

										<div class="col-sm-9">
											<input type="text" id="weight" placeholder="" class="col-xs-10 col-sm-1" onKeyPress="return validatenumber(event);" />
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="length"> <%=Resource.getString("ID_LABEL_LENGTH",locale)%> (M) </label>

										<div class="col-sm-9">
											<input type="text" id="length" placeholder="" class="col-xs-10 col-sm-1" onKeyPress="return validatenumber(event);" />
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="width"> <%=Resource.getString("ID_LABEL_WIDTH",locale)%> (M) </label>

										<div class="col-sm-9">
											<input type="text" id="width" placeholder="" class="col-xs-10 col-sm-1" onKeyPress="return validatenumber(event);" />
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="height"> <%=Resource.getString("ID_LABEL_HEIGHT",locale)%> (M) </label>

										<div class="col-sm-9">
											<input type="text" id="height" placeholder="" class="col-xs-10 col-sm-1" onKeyPress="return validatenumber(event);" />
										</div>
									</div>

									<div class="space-4"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="quantity"> <%=Resource.getString("ID_LABEL_QUANTITY",locale)%> (PCS) </label>

										<div class="col-sm-9">
											<input type="text" id="quantity" placeholder="" class="col-xs-10 col-sm-1" onKeyPress="return validatenumber(event);" />
										</div>
									</div>

									<div class="space-4"></div>
									
								</form>
								
							</div>
							
							<div class="col-xs-12" id="stageSheet2" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNSTATION",locale)%></h1>
								
							</div>
							
							<div class="col-xs-12" id="stageSheet3" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNLOAD",locale)%></h1>
								
							</div>
							
							<div class="col-xs-12" id="stageSheet4" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNEXCHANGE",locale)%></h1>
								
							</div>
							
							<div class="col-xs-12" id="stageSheet5" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNUNLOAD",locale)%></h1>
								
							</div>
							
							<div class="col-xs-12" id="stageSheet6" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNDISTRIBUTE",locale)%></h1>
								
							</div>
							
							<div class="col-xs-12" id="stageSheet7" style="display:none">
								<h1><%=Resource.getString("ID_LABEL_BTNDELIVER",locale)%></h1>
								
							</div>
							
							
							<div class="col-xs-12 sheetCP form-horizontal" id="sheetCP" style="display:none; margin-top:28px;">
							
								<div class="form-group">
									<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> <%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%> </label>

									<div class="col-sm-9">
										<div class="input-group col-xs-10 col-sm-5">
											<input type="text" style="min-width:100px" class="form-control search-query text-uppercase" id="consignmentScan" name="consignmentScan" placeholder="" />
											
											<span class="input-group-btn">
												<button class="btn btn-purple btn-sm" onClick="sendScan()">
													<span id="loading"><i class="ace-icon fa fa-barcode icon-on-right bigger-110"></i> <%=Resource.getString("ID_LABEL_SCAN",locale)%></span>
												</button>
											</span>
										</div>
										<div class="col-md-12 red" id="checker" style="display:none"><small><%=Resource.getString("ID_MSG_PLEASEINPUTCONSIGNMENT",locale)%></small></div>
									</div>
								</div>
								
								<div class="col-xs-12 center">
									
									<div class="row">
										<div class="col-xs-12">
											<span id="visualChecklist"></span>
										</div>
									</div>
								</div>
								
								<div class="col-xs-4 center"></div>
								
								<div class="col-xs-12 alert alert-block alert-success" id="successAlert" style="display:none; margin-top:28px">
									<!--
									<button type="button" class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
									-->
	
									<p align="center">
										<strong>
											<i class="icon-ok"></i>
											<span id="resultSuccess"></span>
										</strong>
									</p>
								</div>
								
								<div class="col-xs-12 alert alert-block alert-danger" id="errorAlert" style="display:none;">
									<!--
									<button type="button" class="close" data-dismiss="alert">
										<i class="icon-remove"></i>
									</button>
									-->
	
									<p align="center">
										<strong>
											<i class="icon-remove"></i>
											<span id="resultError"></span>
										</strong>
									</p>
								</div>
								
								<hr>
								
								<div class="col-xs-12 center">		
									<button class="btn backCP">
										<i class="icon-arrow-left"></i>
										<%=Resource.getString("ID_LABEL_BACK",locale)%>
									</button>
								</div>
							</div>
									
							
							<input type="hidden" id="currentWorksheet" name="currentWorksheet">
							
							<!-- PAGE CONTENT ENDS -->
							
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		<script type="text/javascript">
		
			$(document).ready(function() {

				$( "#consignmentNo" ).focus();
				$( "#weight" ).val("");
				$( "#length" ).val("");
				$( "#width" ).val("");
				$( "#height" ).val("");
				$( "#quantity" ).val("");
				$( "#consignmentScan" ).val("");
				$( "#checker" ).hide();
				$( "#successAlert" ).hide();
				$( "#errorAlert" ).hide();
				$( "#visualChecklist" ).html("");
				
				/*
				$('input[type="checkbox"]').click(function(){
		            if($(this).is(":checked")){
		            	$( "#isCreated" ).val("1");
		            	$( "#loading" ).html("<i class=\"ace-icon fa fa-barcode icon-on-right bigger-110\"> <%=Resource.getString("ID_LABEL_SCAN",locale)%></i>");
		            	$( "#consignmentScan" ).prop('readonly', false);
		            	$( "#consignmentScan" ).val("");
		            }
		            else if($(this).is(":not(:checked)")){
		            	$( "#isCreated" ).val("0");
		            	$( "#loading" ).html("<%=Resource.getString("ID_LABEL_CREATECONSIGNMENT",locale)%>");
		            	$( "#consignmentScan" ).prop('readonly', true);
		            	$( "#consignmentScan" ).val("<%=Resource.getString("ID_LABEL_AUTOGENERATE",locale)%>");
		            }
		        });
				*/
				
			});
			
			$("#searchBtn").click(function() { 
				var consignmentNo = $("#consignmentNo").val();
				if(trim(consignmentNo)=="") {
					alert("Please input Consignment Number");
					return false;
				} else {
					$("#actionType").val("searchConsignment");
					$("form#searchRecord").submit();	
				}
			});
		
			function worksheet(num){
				$( "#cp" ).hide();
				$( "#currentWorksheet" ).val(num);
				$( "#actionType" ).val("stage"+num);
				$( "#sheetCP" ).show();
				$( "#stageSheet"+num ).show();
				$( "#checkbox1").prop('checked', true);
				$( "#weight" ).val("");
				$( "#length" ).val("");
				$( "#width" ).val("");
				$( "#height" ).val("");
				$( "#quantity" ).val("");
				$( "#consignmentScan" ).focus();
				$( "#loading" ).html("<i class=\"ace-icon fa fa-barcode icon-on-right bigger-110\"></i> <%=Resource.getString("ID_LABEL_SCAN",locale)%>");
				$( "#consignmentScan" ).prop('readonly', false);
				$( "#checker" ).hide();
				$( "#successAlert" ).hide();
				$( "#errorAlert" ).hide();
				$( "#visualChecklist" ).html("");
			};

			$(function() {
				$( ".backCP" ).click(function() { 
					
					var num = $( "#currentWorksheet" ).val();
					$( "#consignmentScan" ).val("");
					$('#result').html("");
					$( "#cp" ).show();
					$( "#checker" ).hide();
					$( "#sheetCP" ).hide();
					$( "#stageSheet"+num ).hide();
					$( "#visualChecklist" ).html("");
				});
			});
			
			function sendScan(){

				$( "#checker" ).hide();
			    $( "#successAlert" ).hide();
				$( "#errorAlert" ).hide();
				
				if(trim($("#consignmentScan").val()).length == 0) {
					$( "#checker" ).show();
					$( "#consignmentScan" ).focus();
					return false;
				}
				
				$( "#consignmentScan" ).prop('readonly', true);
				$( "#loading" ).html("<i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i>");
				
				var currentstage = $( "#currentWorksheet" ).val();
				
				var extra = "";
				if(currentstage==1) {
					var wt = $( "#weight" ).val();
					var lh = $( "#length" ).val();
					var wh = $( "#width" ).val();
					var ht = $( "#height" ).val();
					var qt = $( "#quantity" ).val();
					extra = "&weight="+wt+"&length="+lh+"&width="+wh+"&height="+ht+"&quantity="+qt;
				}
				
				var cnNo = $( "#consignmentScan" ).val();
				$(function() {
					$.ajax({
						url: './consignment',        
						data: "actionType=handlingStage&currentstage="+currentstage+"&consignmentNo="+cnNo+extra,
						success: function(response) {

							$( "#consignmentScan" ).prop('readonly', false);
							$( "#loading" ).html("<i class=\"ace-icon fa fa-barcode icon-on-right bigger-110\"></i> <%=Resource.getString("ID_LABEL_SCAN",locale)%>");
							
							if(response=="OK" || response.substring(0,2)=="OK") {
								$( "#successAlert" ).show();
								$( "#resultSuccess" ).html("<%=Resource.getString("ID_LABEL_SCANSUCCESSFULLY",locale)%>");
							} else if(response=="RescanOK") {
								$( "#successAlert" ).show();
								$( "#resultSuccess").html("<%=Resource.getString("ID_LABEL_RESCANSUCCESSFULLY",locale)%>");
							} else if(response=="Created") {
								$( "#successAlert" ).show();
								$( "#resultSuccess").html("<%=Resource.getString("ID_LABEL_CREATEDSUCCESSFULLY",locale)%>");
							} else if(response=="Closed") {
								$( "#errorAlert" ).show();
								$( "#resultError").html("<%=Resource.getString("ID_LABEL_CONSIGNMENTHASCLOSED",locale)%>");
							} else if(response=="PlsScanBarcodeWithSerialNumber") {
								$( "#errorAlert" ).show();
								$( "#resultError" ).html("<%=Resource.getString("ID_MSG_PLSSCANBARCODEWITHSERIAL",locale)%>");
							} else if(response=="InvalidScan") {
								$( "#errorAlert" ).show();
								$( "#resultError" ).html("<%=Resource.getString("ID_MSG_INVALIDSCAN",locale)%>");
								return false;
							} else if(response=="NoConnection") {
								$( "#errorAlert" ).show();
								$( "#resultError" ).html("<%=Resource.getString("ID_LABEL_NOCONNECTION",locale)%>");
								return false;
							} else if(response=="NoSuchConsignment") {
								$( "#errorAlert" ).show();
								$( "#resultError" ).html("<%=Resource.getString("ID_LABEL_NOSUCHCONSIGNMENT",locale)%>");
								return false;
							} else {
								$( "#errorAlert" ).show();
								$( "#resultError" ).html(response);
								return false;
							}
														
							if( (currentstage==3)||(currentstage==4)||(currentstage==5) ) { //load, in transit, unload
								visualCheck();
							}
							
						}
					});
				});


			};
			
			function visualCheck(){
				
				$( "#visualChecklist" ).html("<i><%=Resource.getString("ID_LABEL_VISUALIZING",locale)%></i> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i>");
				
				var currentstage = $( "#currentWorksheet" ).val();
				var visualIcon = "";
				
				var cnNo = $( "#consignmentScan" ).val();
				$(function() {
					$.ajax({
						url: './consignment',        
						data: "actionType=visualCheck&currentstage="+currentstage+"&consignmentNo="+cnNo,
						success: function(response) {
							temp = response.replace("[","").replace("]","").split(",");//split string to array

							var i = 1;
							for (a in temp ) {
								if(temp[a] == 0) {
									visualIcon += "<i class=\"ace-icon fa fa-cube fa-lg grey\"></i>"+i+" &nbsp;";
								} else if(temp[a] == 1) {
									visualIcon += "<i class=\"ace-icon fa fa-cube fa-lg orange\"></i>"+i+" &nbsp;";
								}
								i++;
							}
							
							$( "#visualChecklist" ).html(visualIcon);
						}
					});
				});


			};
			
			
			function validatenumber(event) {
				var key = window.event ? event.keyCode : event.which;

				if (key == 8 || key == 9 || key == 46) {
				    return true;
				}
				else if ( key > 57 || key < 48) {
				    return false;
				}
				else 
					return true;
			}

			
		</script>
		
	</body>
</html>
