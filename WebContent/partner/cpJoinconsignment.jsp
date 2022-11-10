<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.consignment.*,com.iposb.area.*,com.iposb.partner.*,java.util.HashMap,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%		
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();
	String source = request.getParameter("source") == null ? "" : request.getParameter("source").toString();
	
	HashMap joinData = (HashMap)request.getAttribute(ConsignmentController.OBJECTDATA2);
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	ArrayList <PartnerDataModel> partnerData = (ArrayList)request.getAttribute("partner");
	PartnerDataModel pData = new PartnerDataModel();
	
	String generalCargoNo = "";
	String amount = "0";
	String weight = "0";
	String flightNum = "";
	String airwaybillNum = "";
	String natureGood = "";
	String receiptNum = "";
	String stationRemark = "";
	String senderName = "";
	String senderAddress1 = "";
	String senderAddress2 = "";
	String senderAddress3 = "";
	String senderPostcode = "";
	int senderZone = 0;
	String senderPhone = "";
	int senderArea = 0;
	String senderIC = "";
	String receiverName = "";
	String receiverAttn = "";
	String receiverAddress1 = "";
	String receiverAddress2 = "";
	String receiverAddress3 = "";
	String receiverPostcode = "";
	int receiverZone = 0;
	String receiverPhone = "";
	int receiverArea = 0;
	String helps = "";
	int shipmentType = 0;
	int quantity = 1;
	int payMethod = 0;
	String useLocale = "";
	String verify = "";
	String errmsg = "";
	
	if(source.equals("edit")) {
		generalCargoNo = joinData.get("generalCargoNo") == null ? "" : joinData.get("generalCargoNo").toString();
		amount = joinData.get("amount") == null ? "0.00" : joinData.get("amount").equals("") ? "0.00" : joinData.get("amount").toString();
		weight = joinData.get("weight") == null ? "0.00" : joinData.get("weight").equals("") ? "0.00" : joinData.get("weight").toString();
		
		flightNum = joinData.get("flightNum") == null ? "" : joinData.get("flightNum").toString();
		airwaybillNum = joinData.get("airwaybillNum") == null ? "" : joinData.get("airwaybillNum").toString();
		natureGood = joinData.get("natureGood") == null ? "" : joinData.get("natureGood").toString();
		receiptNum = joinData.get("receiptNum") == null ? "" : joinData.get("receiptNum").toString();
		stationRemark = joinData.get("stationRemark") == null ? "" : joinData.get("stationRemark").toString();
		
		senderName = joinData.get("senderName") == null ? "" : joinData.get("senderName").toString();
		senderAddress1 = joinData.get("senderAddress1") == null ? "" : joinData.get("senderAddress1").toString();
		senderAddress2 = joinData.get("senderAddress2") == null ? "" : joinData.get("senderAddress2").toString();
		senderAddress3 = joinData.get("senderAddress3") == null ? "" : joinData.get("senderAddress3").toString();
		senderPostcode = joinData.get("senderPostcode") == null ? "" : joinData.get("senderPostcode").toString();
		senderZone = Integer.parseInt(joinData.get("senderZone") == null ? "0" : joinData.get("senderZone").equals("") ? "0" : joinData.get("senderZone").toString());
		senderPhone = joinData.get("senderPhone") == null ? "" : joinData.get("senderPhone").toString();
		senderArea = Integer.parseInt(joinData.get("senderArea") == null ? "0" : joinData.get("senderArea").equals("") ? "0" : joinData.get("senderArea").toString());
		receiverAttn = joinData.get("receiverAttn") == null ? "" : joinData.get("receiverAttn").toString();
		senderIC = joinData.get("senderIC") == null ? "" : joinData.get("senderIC").toString();
		receiverName = joinData.get("receiverName") == null ? "" : joinData.get("receiverName").toString();
		receiverAddress1 = joinData.get("receiverAddress1") == null ? "" : joinData.get("receiverAddress1").toString();
		receiverAddress2 = joinData.get("receiverAddress2") == null ? "" : joinData.get("receiverAddress2").toString();
		receiverAddress3 = joinData.get("receiverAddress3") == null ? "" : joinData.get("receiverAddress3").toString();
		receiverPostcode = joinData.get("receiverPostcode") == null ? "" : joinData.get("receiverPostcode").toString();
		receiverZone = Integer.parseInt(joinData.get("receiverZone") == null ? "0" : joinData.get("receiverZone").equals("") ? "0" : joinData.get("receiverZone").toString());
		receiverPhone = joinData.get("receiverPhone") == null ? "" : joinData.get("receiverPhone").toString();
		receiverArea = Integer.parseInt(joinData.get("receiverArea") == null ? "0" : joinData.get("receiverArea").equals("") ? "0" : joinData.get("receiverArea").toString());
		helps = joinData.get("helps") == null ? "" : joinData.get("helps").toString();
		shipmentType = Integer.parseInt(joinData.get("shipmentType") == null ? "0" : joinData.get("shipmentType").equals("") ? "0" : joinData.get("shipmentType").toString());
		quantity = Integer.parseInt(joinData.get("quantity") == null ? "0" : joinData.get("quantity").equals("") ? "0" : joinData.get("quantity").toString());
		payMethod = Integer.parseInt(joinData.get("payMethod") == null ? "0" : joinData.get("payMethod").equals("") ? "0" : joinData.get("payMethod").toString());
		useLocale = joinData.get("useLocale") == null ? "" : joinData.get("useLocale").toString();
		verify = joinData.get("verify") == null ? "" : joinData.get("verify").toString();
		errmsg = joinData.get("errmsg") == null ? "" : joinData.get("errmsg").toString();
	}

%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_JOINCONSIGNMENT",locale)%></title>
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
							<li><a href="./agentCP"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li>
							<li class="active"><%=Resource.getString("ID_LABEL_JOINCONSIGNMENT",locale) %></li> 

						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
										<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
										<span id="errorMsg"></span>
									</div>
									
									<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
										<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
										<span id="successMsg"></span>
									</div>
									
									<form id="createForm" name="createForm" method="post" action="./consignment" class="form-horizontal">
										<input type="hidden" id="actionType" name="actionType" value="agentCreateJoinConsignment">
										<input type="hidden" id="useLocale" name="useLocale" value="<%=locale %>">
										<input type="hidden" id="verify" name="verify" value="<%=verify %>">
										<input type="hidden" id="formValues" name="formValues">
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_3RDPARTYCN",locale)%> <span class="red">*</span> </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="generalCargoNo" name="generalCargoNo" value="<%=generalCargoNo %>" maxlength="50">
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%> </label>
	
											<div class="col-sm-8">
												<select class="col-xs-5" id="shipmentType" name="shipmentType">
													<option value="0"></option>
													<option value="1" <% if(shipmentType==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale)%></option>
													<option value="2" <% if(shipmentType==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%></option>
													<option value="3" <% if(shipmentType==3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_CHARTERTRANSPORT",locale)%></option>
												</select>
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_AMOUNT",locale)%> (RM) </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="amount" name="amount" value="<%=amount %>" maxlength="13" onKeyPress="return validatenumber(event);">
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_QUANTITY",locale)%> (PCS) </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="quantity" name="quantity" value="<%=quantity %>" maxlength="6" onKeyPress="return validatenumber(event);">
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_WEIGHT",locale)%> (KG) </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="weight" name="weight" value="<%=weight %>" maxlength="13" onKeyPress="return validatenumber(event);">
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_PAYMETHOD",locale)%> </label>
	
											<div class="col-sm-8">
												<select class="col-xs-5" id="payMethod" name="payMethod">
													<option value="0"></option>
				                                	<option value="1" <% if(payMethod==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCASH",locale)%></option>
													<option value="2" <% if(payMethod==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYONLINE",locale)%></option>
													<option value="3" <% if(payMethod==3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYBANKIN",locale)%></option>
													<option value="4" <% if(payMethod==4){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale)%></option>
													<option value="5" <% if(payMethod==5){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCOD",locale)%></option>
													<option value="6" <% if(payMethod==6 || payMethod==0){out.print("selected");} %>><%=Resource.getString("ID_LABEL_FREIGHTCHARGES",locale)%></option>
													<option value="7" <% if(payMethod==7){out.print("selected");} %>><%=Resource.getString("ID_LABEL_COMPANYMAIL",locale)%></option>
												</select>
											</div>
										</div>
										
					                    <div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_FLIGHTNUM",locale)%> </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="flightNum" name="flightNum" value="<%=flightNum %>" maxlength="20">
											</div>
										</div>
										
					                    <div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_AIRWAYBILLNUM",locale)%> </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="airwaybillNum" name="airwaybillNum" value="<%=airwaybillNum %>" maxlength="50">
											</div>
										</div>
										
					                    <div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_NATUREOFGOOD",locale)%> </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="natureGood" name="natureGood" value="<%=natureGood %>" maxlength="20">
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIPTNUM",locale)%> </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="receiptNum" name="receiptNum" value="<%=receiptNum %>" maxlength="20">
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-4 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_STATIONREMARK",locale)%> </label>
	
											<div class="col-sm-8">
												<input class="col-xs-5" type="text" id="stationRemark" name="stationRemark" value="<%=stationRemark %>" maxlength="50">
											</div>
										</div>
										
				                        <div class="row">
								
											<div class="col-md-6">
											
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SENDERNAME",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="senderName" name="senderName" value="<%=senderName %>">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SENDERIC",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="senderIC" name="senderIC" value="<%=senderIC %>">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SENDERADDRESS",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="senderAddress1" name="senderAddress1" value="<%=senderAddress1 %>" maxlength="40">
														<input class="col-xs-10" type="text" id="senderAddress2" name="senderAddress2" value="<%=senderAddress2 %>" maxlength="40">
														<input class="col-xs-10" type="text" id="senderAddress3" name="senderAddress3" value="<%=senderAddress3 %>" maxlength="40">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SENDERPOSTCODE",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="senderPostcode" name="senderPostcode" maxlength="10" value="<%=senderPostcode %>">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SENDERAREA",locale)%> </label>
			
													<div class="col-sm-9">
														<select class="col-xs-10" id="senderArea" name="senderArea" onChange="loadZone('sender')">
															<option value="0"></option>
							                                <% 
								                                if(areaData != null && !areaData.isEmpty()){
																	for(int i = 0; i < areaData.size(); i ++){
																		aData = areaData.get(i);
							                                %>
																<option value="<%=aData.getAid() %>" <% if(senderArea==(i+1)){out.print("selected");} %>><%=aData.getName_enUS() %> (<%=aData.getCode() %>)</option>
															<% } } %>
														</select>
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_COLLECTPOINT",locale)%> </label>
			
													<div class="col-sm-9">
														<select class="col-xs-10" id="senderZone" name="senderZone"></select>
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_SENDERPHONE",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="senderPhone" name="senderPhone" value="<%=senderPhone %>">
													</div>
												</div>

											</div>
											
											<div class="col-md-6">
											
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIVERNAME",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="receiverName" name="receiverName" value="<%=receiverName %>">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIVERATTN",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="receiverAttn" name="receiverAttn" value="<%=receiverAttn %>">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIVERADDRESS",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="receiverAddress1" name="receiverAddress1" value="<%=receiverAddress1 %>" maxlength="40">
														<input class="col-xs-10" type="text" id="receiverAddress2" name="receiverAddress2" value="<%=receiverAddress2 %>" maxlength="40">
														<input class="col-xs-10" type="text" id="receiverAddress3" name="receiverAddress3" value="<%=receiverAddress3 %>" maxlength="40">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIVERPOSTCODE",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="receiverPostcode" name="receiverPostcode" maxlength="10" value="<%=receiverPostcode %>">
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIVERAREA",locale)%> </label>
			
													<div class="col-sm-9">
														<select class="col-xs-10" id="receiverArea" name="receiverArea" onChange="loadZone('receiver')">
															<option value="0"></option>
							                                <% 
								                                if(areaData != null && !areaData.isEmpty()){
																	for(int i = 0; i < areaData.size(); i ++){
																		aData = areaData.get(i);
							                                %>
																<option value="<%=aData.getAid() %>" <% if(receiverArea==(i+1)){out.print("selected");} %>><%=aData.getName_enUS() %> (<%=aData.getCode() %>)</option>
															<% } } %>
														</select>
													</div>
												</div>
												
												<div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_DROPOFFPOINT",locale)%> </label>
			
													<div class="col-sm-9">
														<select class="col-xs-10" id="receiverZone" name="receiverZone"></select>
													</div>
												</div>
											
						                        <div class="form-group">
													<label class="col-sm-3 control-label no-padding-right"> <%=Resource.getString("ID_LABEL_RECEIVERPHONE",locale)%> </label>
			
													<div class="col-sm-9">
														<input class="col-xs-10" type="text" id="receiverPhone" name="receiverPhone" value="<%=receiverPhone %>">
													</div>
												</div>
						                        
											</div>
											
										</div>
			
										<p class="text-center">
											<button id="submitBtn" name="submitBtn" class="btn btn-orange" type="button" onClick="submitCheck()"><%=Resource.getString("ID_LABEL_CONFIRMCREATE",locale)%></button>	
											<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
					                    </p>
										
									</form>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		

<g:compress>
		<script type="text/javascript">
			
			$( document ).ready(function() {
				
				if("<%=result %>" == "registerSuccess"){
					$( "#successAlert" ).show();
					$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_REGISTERSUCCESS",locale)%>");
				} else if("<%=result %>" == "createSuccess"){
					$( "#successAlert" ).show();
					$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_CREATESUCCESS",locale)%>");
				} else if("<%=result %>" == "noConnection"){
					$( "#errorAlert" ).show();
					$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_NOCONNECTION",locale)%>");
				}
				
				$( "#generalCargoNo" ).focus();
				
				loadZone("sender");
				loadZone("receiver");

			});
			
			function submitCheck() {
				var gCN = $( "#generalCargoNo" ).val();
				if(gCN.length > 0) {
					$( "#successAlert" ).hide();
					$( "#errorAlert" ).hide();
					$( "#submitBtn" ).button( 'option', 'disabled', true );
					$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
					$( "#createForm" ).submit();
				}
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
			};
			
			
			function loadZone(obj) {
				
				var areaId = "";
				
				if(obj=="sender") {
					$( "#senderZone" ).prop( "disabled", true );
					$( "#senderZone" ).empty();
					areaId = $( "#senderArea" ).val();
				} else if(obj=="receiver") {
					$( "#receiverZone" ).prop( "disabled", true );
					$( "#receiverZone" ).empty();
					areaId = $( "#receiverArea" ).val();
				}
				
				$.ajax({
					type: 'POST',
					url: './area',
					data:{    
						lang: "<%=locale %>",
						actionType:"ajaxGetZone",
						aid: areaId
			   		},  
			   		
					success: function(response) {
						
						if(obj=="sender") {
							
							if(response != ""){
								$( "#senderZone" ).append(response);
							}
							$( "#senderZone" ).prop( "disabled", false );
							
						} else if(obj=="receiver") {

							if(response != ""){
								$( "#receiverZone" ).append(response);
							}
							$( "#receiverZone" ).prop( "disabled", false );
							
						}
						
					} 
				});
				
			};

			
		</script>
</g:compress>

	</body>
</html>
