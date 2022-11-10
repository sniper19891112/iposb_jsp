<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,com.iposb.pickup.*,java.util.HashMap,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%
	String errmsg = "";
	
	ArrayList data = (ArrayList)request.getAttribute(PickupController.OBJECTDATA);
	PickupDataModel pickupData = new PickupDataModel();
	
	String driversData = (String)request.getAttribute("driver");
	
	String partnerData = (String)request.getAttribute("partner");
	
	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
	String actionType = request.getParameter("actionType") == null ? "" : request.getParameter("actionType").toString();
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_PICKUPMAINTAINANCE",locale)%></title>
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
								<a href="/"><%=Resource.getString("ID_LABEL_HOME",locale)%></a>
							</li>
							<li><a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li>
							<% if(actionType.equals("searchRequest")){ 
									out.print("<li class=\"active\"><a href=\"./pickup\">"+Resource.getString("ID_LABEL_PICKUPMAINTAINANCE",locale)+"</a></li>");
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_SEARCH",locale)+"</li>"); 

							   } else {
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_PICKUPMAINTAINANCE",locale)+"</li>");
							   } 
							%>
							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
								<div class="row">
									<!-- 
									<div class="col-xs-12 col-sm-8">
										<a onclick="editPickup()" class="btn btn-primary" id="addPickupBtn">
											<i class="fa fa-plus"></i>
											Add New Pickup
										</a>
									</div>
									 -->
									 
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_SEARCH",locale)%></h3>
								
									
									<form id="searchForm" name="searchForm" action="./pickup" method="get" class="form-horizontal">
										<input type="hidden" name="actionType" value="searchRequest"/>
									
										<div class="space-4"></div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="consignmentNo"> <%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%> </label>
											<div class="col-sm-4">
												<div class="input-group">
													<input class="form-control" id="consignmentNo" name="consignmentNo" type="text" value="<%=consignmentNo %>" maxlength="9">
												</div>
											</div>
										</div>
																				
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="checkBtn"></label>
											<div class="col-sm-9">
												<button class="btn btn-primary" id="checkBtn" type="submit">
													<i class="fa fa-search"></i>
													<%=Resource.getString("ID_LABEL_SEARCH",locale)%>
												</button>
											</div>
										</div>

									</form>
									
									<div class="space-4"></div>
								
				           		</div>
				           		
								<div class="row">
									<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_PICKUPREQUESTS",locale)%></h3>
								
									<div class="col-xs-12">
									
										<div class="table-responsive">
											<table id="pickup-table" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center"><%=Resource.getString("ID_LABEL_NO", locale) %>.</th>
														<th class="center"><%=Resource.getString("ID_LABEL_CREATEDATE", locale) %></th>
														<th class="center"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER", locale) %></th>
														<th class="center"><%=Resource.getString("ID_LABEL_PICKUPDRIVER", locale) %></th>
														<th class="center"><%=Resource.getString("ID_LABEL_SENDERAREA", locale) %></th>
														<th class="center"><%=Resource.getString("ID_LABEL_ZONE", locale) %></th>
														<th class="center"><%=Resource.getString("ID_LABEL_STATUS", locale) %></th>
														<th class="center">Assign</th>
													</tr>
												</thead>

												<tbody>
													<% 
													if(data != null && data.size() != 0){
														String lastCN = ""; //last consignmentNo
														
														boolean showAssign = false;
														
														for(int i=0; i< data.size(); i++){
															pickupData = (PickupDataModel)data.get(i);
															String status = "";
															
															String driver = "<label class=\"grey\" style=\"font-size:85%\" >(Not assigned yet)</label>";
															if(!pickupData.getDriver().equals("")) {
																driver = pickupData.getDriver() +" ("+ pickupData.getDriverName() +")";
															}
															
															String assign = "";
															if( pickupData.getStatus() == 0 && !pickupData.getDriver().equals("") ){ // pending
																status = "<label class=\"blue\"><b>"+ Resource.getString("ID_LABEL_PENDING",locale) +"...</b></label>";
																showAssign = false;
																
															}else if(pickupData.getStatus() == 2 || ( pickupData.getStatus() == 0 && pickupData.getDriver().equals("") )){ // waiting assign or rejected
	
																if(pickupData.getStatus() == 2){
																	status = "<label class=\"red\"><b>"+ Resource.getString("ID_LABEL_REJECTED",locale) +"</b></label>";
																	
																	if( lastCN.equals(pickupData.getConsignmentNo()) ){
																		showAssign = false;
																	}else{
																		showAssign = true;
																	}
																	
																}else if( pickupData.getStatus() == 0){
																	status = "<label class=\"orange\"><b>"+ Resource.getString("ID_LABEL_AWAITINGASSIGNDRIVER",locale) +"</b></label>";

																	showAssign = true;
																}
															
																if(showAssign && pickupData.getIsMonitored() == 0){
																	showAssign = false;
																	
																	assign = 
																		"<br/><a style=\"cursor:pointer;\" onclick=\"reassign('"+i+"')\" title=\"Assign\" >" +
																			"<span class=\"green\">" +
																				"<i class=\"icon-edit bigger-120\"></i>" +
																			"</span>" +
																		"</a>";
																}
																
															}else if(pickupData.getStatus() == 1){
																status = "<label class=\"green\"><b>"+ Resource.getString("ID_LABEL_ACCEPTED",locale) +"</b></label>";
															}else if(pickupData.getStatus() == 3){
																status = "<label class=\"green\"><b>"+ Resource.getString("ID_LABEL_PICKEDUP",locale) +"</b></label>";
															}else if(pickupData.getStatus() == 4){
																status = "<label class=\"orange\"><b>"+ Resource.getString("ID_LABEL_NOTYETPICKUP",locale) +"</b></label>";
															}
															
															lastCN = pickupData.getConsignmentNo();
															
															String createDT = pickupData.getCreateDT();
													%>
														<tr>
															<td align="center"><%=(i+1) %></td>
															<td align="center"><%=createDT.length() >10 ? createDT.substring(0, 10) : createDT %></td>
															<%-- <td align="center"><%=pickupData.getPickupDT() %></td> --%>
															<td align="center"><a target="_blank" href="./consignment?actionType=searchConsignment&consignmentNo=<%=pickupData.getConsignmentNo() %>"><%=pickupData.getConsignmentNo() %></a></td>
															<td align="center">
																<%=driver%>
															</td>
															<td align="center"><%=pickupData.getAreaName_enUS() %></td>
															<td align="center"><%=pickupData.getZoneName_enUS() %></td>
															<td align="center"><%=status %></td>
															<td align="center">
																<%=assign %>
																<input type="hidden" id="consignmentNo_<%=i %>" value="<%=pickupData.getConsignmentNo() %>" />
																<input type="hidden" id="zoneNM_<%=i %>" value="<%=pickupData.getZoneName_enUS() %>" />
																<input type="hidden" id="areaNM_<%=i %>" value="<%=pickupData.getAreaName_enUS() %>" />
																<%-- <input type="hidden" id="pickupDT_<%=i %>" value="<%=pickupData.getPickupDT() %>" /> --%>
																<input type="hidden" id="driver_<%=i %>" value="<%=pickupData.getDriver() %>" />
																<input type="hidden" id="deliveryDT_<%=i %>" value="<%=pickupData.getDeliveryDT() %>" />
																<input type="hidden" id="agent_<%=i %>" value="<%=pickupData.getAgent() %>" />
															</td>
														</tr>
													<%}} else { %>
														<tr>
															<td class="text-center red" align="center" colspan="8"><%=Resource.getString("ID_LABEL_NORECORD",locale) %></td>
														</tr>
													<% } %>
												</tbody>
											</table>
				
										</div><!-- /.table-responsive -->
									</div><!-- /span -->
								</div>
								
								<div align="center">
											
										<%
											//分頁
											int numPerPage = 30; //每頁顯示數量
											String pnoStr = request.getParameter("page");
											Integer pno = pnoStr == null ? 1 : Integer.parseInt(pnoStr);
											pager.setPageNo(pno.intValue());
											pager.setPerLen(numPerPage);
											int total = 0;
											int thisPage = 0;
											String p = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
											
											//防止user亂輸入頁碼
											boolean isNum = PickupController.isNumeric(p);
											
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
											
											// Add Search parameter to paging url
											String param1 = "",param2 = "";
											if(!consignmentNo.equals("")) param1 = "consignmentNo="+consignmentNo+"&";
											if(!actionType.equals("")) param2 = "actionType="+actionType+"&";
										
											total = pickupData.getTotal();
											pager.setData(total);//设置数据
											int []range = pager.getRange(numPerPage);//获得页码呈现的区间
											if(pager.getMaxPage()>1) {//要呈现的页面超过1页，需要分页呈现
												out.print("<ul class=\"pagination\">");
													
												//上10頁按鍵
												if(thisPage > 1){
													out.print("<li><a href=\"./pickup?"+param1+param2+"page="+prev10+"\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁												
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./pickup?"+param1+param2+"page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./pickup?"+param1+param2+"page="+(next10)+"\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
												}
												
												out.print("</ul>");
										   }
										%>
										
								</div>
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
			<div class="modal fade" id="reassignDriver" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h2 class="modal-title" id="">Assign Driver</h2>
						</div>
						<div class="modal-body">
							<div class="row">
								<form id="reassignForm" name="reassignForm" class="reg-page" style="width:90%;margin:0px auto!important;" method="post" action="./pickup">
									<input type="hidden" id="actionType" name="actionType" value="reassignDriver">		
									<input type="hidden" id="mCN" name="mCN" value="">
									<input type="hidden" id="mDriverUserId" name="mDriverUserId" value="">
									<!-- <input type="hidden" id="mPickupDT" name="mPickupDT" value=""> -->
									<!--<input type="hidden" id="formValues" name="formValues">-->	
									
									
									
									<div class="form-group">
										<div class="col-xs-6">
											<label>Consignment Number:</label>
											<div>
												<p style="color:grey;margin-left: 15px;font-size:150%" id="displayCN"></p>
											</div>
										</div>
										<div class="col-xs-6">
											<label>Pickup Driver</label>
											<div>
												<p style="color:grey;margin-left: 15px;" id="mDriver"></p>
											</div>
										</div>
									</div>
									
									
									<div class="form-group">
										<div class="col-xs-12">
											<label>Zone</label>
											<div>
												<p style="color:grey;margin-left: 15px;" id="mZoneNM"></p>
											</div>
										</div>
									</div>
									
									
									<div class="form-group">
										<div class="col-xs-12">
											<label>Area</label>
											<div>
												<p style="color:grey;margin-left: 15px;" id="mAreaNM"></p>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12">
											<div class="space-8"></div>
											<label style="color:cornflowerblue;font-weight: bold;"><%=Resource.getString("ID_LABEL_DELIVERYDATE",locale) %></label>
											<div class="input-group">
												<input name="mDeliveryDT" id="mDeliveryDT" class="form-control date-picker" autocomplete="off" maxlength="10"></input>
												<span class="input-group-addon">
													<i class="fa fa-calendar bigger-110"></i>
												</span>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12">
											<div class="space-8"></div>
											<label style="color:cornflowerblue;font-weight: bold;"><%=Resource.getString("ID_LABEL_ASSIGNNEWDRIVER",locale) %></label>
											<div>
												<select name="newDriver" id="newDriver" class="form-control"></select>
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<div class="col-xs-12">
											<div class="space-8"></div>
											<label style="color:cornflowerblue;font-weight: bold;"><%=Resource.getString("ID_LABEL_ASSIGNAGENT",locale) %></label>
											<div>
												<select name="mAgent" id="mAgent" class="form-control"></select>
											</div>
										</div>
									</div>
									
				                </form>
			                </div>
						</div>
						<div class="modal-footer">
							<span id="loading"></span>
							<button type="button" class="btn btn-default" id="cancel" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
							<button type="button" class="btn btn-info" id="sendReq">Send Pickup Request</button>
							<button type="button" class="btn btn-default pull-right" id="closeBtn" data-dismiss="modal" style="display:none"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
						</div>
					</div>
				</div>
			</div>

		<!--=== Edit Pickup Modal End ===-->

		<!-- inline scripts related to this page -->

		<!-- page specific plugin scripts -->
		<!-- <script src="plugins/ace/js/date-time/bootstrap-datepicker.min.js"></script> -->
		<!-- <script src="./assets/js/jquery.dataTables.min.js"></script> -->
		<!-- <script src="./assets/js/jquery.dataTables.bootstrap.js"></script> -->
		
<g:compress>
		<script type="text/javascript">
		
			$( document ).ready(function() {
				$("#reassignDriver #sendReq").click(function(){

					var consignmentNo = $("#reassignDriver #mCN").val();
					var driver = $("#reassignDriver #mDriverUserId").val();
					var newDriver = $("#reassignDriver #newDriver").val(); // new sid (staff id)
					// var pickupDT = $("#reassignDriver #mPickupDT").val();
					var deliveryDT = $("#reassignDriver #mDeliveryDT").val();
					var agent = $("#reassignDriver #mAgent").val();
					
			   		$("#reassignDriver #loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#reassignDriver #cancel").hide();
					$("#reassignDriver #sendReq").hide();
					$.ajax({
						type: 'POST',
						url: './pickup',
						data:{    
							lang: "<%=locale %>",
							actionType:"ajaxReassign",
							oldDriverUserId: driver,
							newDriverSid: newDriver,
							//pickupDT: pickupDT,
							deliveryDT: deliveryDT,
							agent: agent,
							consignmentNo: consignmentNo
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "success"){
									$("#reassignDriver #loading").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\">Send Success</span>&nbsp;&nbsp;&nbsp;");
									$("#reassignDriver #closeBtn").show();
								} else {
									$("#reassignDriver #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: " + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#reassignDriver #closeBtn").hide();
									$("#reassignDriver #cancel").show();
									$("#reassignDriver #sendReq").show();
								}
							}
						} 
					});
				});
				
				$("#reassignDriver #closeBtn").click(function(){
					location.reload();
				});

				
				<%
					if(errmsg.length() > 0){
						if(errmsg.equals("insertSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_INSERTSUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("updateSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATESUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("noData")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_NODATA",locale) + "</p></div>\");");
						} else if(errmsg.equals("exist")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_EXIST",locale) + "</p></div>\");");
						} else if(errmsg.equals("noConnection")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>

			});
			
			function reassign(id){
				$(function(){
					var consignmentNo = $("#consignmentNo_"+id).val();
					var consignmentlink = "<a target=\"_blank\" href=\"consignment?actionType=searchConsignment&consignmentNo="+consignmentNo+"\">"+ consignmentNo +"</a>";
					$("#reassignDriver #displayCN").html( consignmentlink  );
					$("#reassignDriver #mCN").val( consignmentNo  );
					$("#reassignDriver #mDriverUserId").val( $("#driver_"+id).val() );
					$("#reassignDriver #mDriver").html( $("#driver_"+id).val() );
					$("#reassignDriver #mZoneNM").html( $("#zoneNM_"+id).val() );
					$("#reassignDriver #mAreaNM").html( $("#areaNM_"+id).val() );
					$("#reassignDriver #mDeliveryDT").val( $("#deliveryDT_"+id).val() );
					$("#reassignDriver #mAgent").val( $("#agent_"+id).val() );
					// $("#reassignDriver #mPickupDT").val( $("#pickupDT_"+id).val() );
					// $("#reassignDriver #dis_mPickupDT").html( $("#pickupDT_"+id).val() );

					$("#reassignDriver #newDriver").html("");
					$("#reassignDriver #newDriver").append("<option value=\"0\"> </option>");
					$("#reassignDriver #mAgent").html("");
					$("#reassignDriver #mAgent").append("<option value=\"0\"> </option>");
					
					var ddata = (JSON.parse('<%=driversData%>')).driversdata;
					for(var i=0;i<ddata.length;i++){
						var driverdata = ddata[i];
						$("#reassignDriver #newDriver").append("<option value=\""+driverdata.sid+"\">"+driverdata.userId+" ("+driverdata.ename+")</option>");
					}
					
					var pdata = (JSON.parse('<%=partnerData%>')).partnerdata;
					for(var i=0;i<pdata.length;i++){
						var partnerdata = pdata[i];
						$("#reassignDriver #mAgent").append("<option value=\""+partnerdata.pid+"\">"+partnerdata.userId+" ("+partnerdata.ename+")</option>");
					}
					
					$("#reassignDriver").modal();
				});
			};
			
			
			//datepicker plugin
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true,
				dateFormat: 'yy-mm-dd'
			})
			//show datepicker when clicking on the icon
			.next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
			
			
		</script>
</g:compress>

		<link rel="stylesheet" href="plugins/ace/css/datepicker.css" />
	</body>
</html>
