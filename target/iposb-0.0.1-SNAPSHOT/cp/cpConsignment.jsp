<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.consignment.*,com.iposb.area.*,com.iposb.partner.*,com.iposb.pricing.*,java.util.HashMap,java.util.Iterator,java.util.ArrayList,java.io.*,java.text.*"%>
<%@include file="../include.jsp" %>

<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%	
	String errmsg = "";
	String consignmentNoStream = "";
	String invoiceNoconsignmentNoStream = "";
	ArrayList data = (ArrayList)request.getAttribute(ConsignmentController.OBJECTDATA);	
	if(data != null && data.size() > 0){
		ConsignmentDataModel tmpData = new ConsignmentDataModel();
		for (int i = 0; i < data.size(); i++) {
			tmpData = (ConsignmentDataModel)data.get(i);
			consignmentNoStream += tmpData.getConsignmentNo()+"-";
			invoiceNoconsignmentNoStream += tmpData.getInvoiceNo() + "-" + tmpData.getConsignmentNo()+";";
			if(i==data.size()-1) { //last
				consignmentNoStream = consignmentNoStream.substring(0, consignmentNoStream.length()-1);
				invoiceNoconsignmentNoStream = invoiceNoconsignmentNoStream.substring(0, invoiceNoconsignmentNoStream.length()-1);
			}
			
			if(i==0) {//first
				errmsg = tmpData.getErrmsg();
			}
		}
	}
	
	String pageNo = request.getParameter("page")==null ? "1" : request.getParameter("page").toString();
	String pageStatus = request.getParameter("status")==null ? "0" : request.getParameter("status").toString();	
	String searchCode = request.getParameter("consignmentNo")==null ? "" : request.getParameter("consignmentNo").toString();
	String searchType = request.getParameter("searchType")==null ? "" : request.getParameter("searchType").toString();
	String actionType = request.getParameter("actionType")==null ? "" : request.getParameter("actionType").toString();
	String conType = request.getParameter("conType")==null ? "" : request.getParameter("conType").toString();
	
	String pagelink = "consignment";
	if(conType.equals("pending")) {
		pagelink = "pending";
	}
	
	String searchPartnerCN = "";
	int partnerId = Integer.parseInt(request.getParameter("partnerId")==null ? "0" : request.getParameter("partnerId").equals("") ? "0" : request.getParameter("partnerId").toString());
	if(partnerId > 0) {
		searchPartnerCN = "actionType=filterPartnerCN&partnerId="+partnerId;
	}

	NumberFormat formatter = new DecimalFormat("#,###,##0.00");
	
	ArrayList <PartnerDataModel> partnerData = (ArrayList)request.getAttribute("partner");
	PartnerDataModel pData = new PartnerDataModel();
	
	ArrayList <PartnerDataModel> agentData = (ArrayList)request.getAttribute("agent");
	PartnerDataModel aData = new PartnerDataModel();
	
	String all_amount = "";
	String all_discountReason = "";
	String all_shipmentType = "";
	String all_senderName = "";
	String all_senderAddress1 = "";
	String all_senderAddress2 = "";
	String all_senderAddress3 = "";
	String all_senderPostcode = "";
	String all_senderArea = "";
	String all_senderPhone = "";
	String all_senderIC = "";
	String all_receiverName = "";
	String all_receiverAttn = "";
	String all_receiverAddress1 = "";
	String all_receiverAddress2 = "";
	String all_receiverAddress3 = "";
	String all_receiverPostcode = "";
	String all_receiverArea = "";
	String all_receiverPhone = "";
	String all_quantity = "";
	String all_weight = "";
	String all_length = "";
	String all_width = "";
	String all_height = "";
	String all_flightNum = "";
	String all_airwaybillNum = "";
	String all_natureGood = "";
	String all_receiptNum = "";
	String all_payMethod = "";
	String all_creditArea = "";
	String all_payStatus = "";
	String all_payDT = "";
	String all_pickupDriver = "";
	String all_pickupDT = "";
	String all_deliveryDriver = "";
	String all_deliveryDT = "";
	String all_pricing = "";
	String all_terms = "";
	String all_abbreviationPartner = "";
	String all_abbreviationAgent = "";
	String all_generalCargoNo = "";
	String all_stationRemark = "";
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_CONSIGNMENT",locale)%></title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@include file="cpMeta.jsp" %>
		
		<!-- page specific plugin styles -->

		<g:compress>
			<link rel="stylesheet" href="../plugins/ace/css/jquery.gritter.css" />
			<link rel="stylesheet" href="../plugins/ace/css/select2.css" />
			<link rel="stylesheet" href="../plugins/ace/css/bootstrap-editable.css" />
		</g:compress>

		
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
							<li>
								<a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a>
							</li>
							<li class="active">
								<%
									if (actionType.equals("searchConsignment")) {
										out.print(Resource.getString("ID_LABEL_SEARCH",locale));
									} else {
										out.print(Resource.getString("ID_LABEL_CONSIGNMENTS",locale));
									}
								%>
							</li>

						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<div class="col-sm-12 infobox-container">
									
										<div class="infobox infobox-blue" style="width:300px">
											<div class="infobox-icon">
												<i class="icon-search"></i>
											</div>

											<div class="infobox-data">
												<span>
													<form id="searchRecord" name="searchRecord" method="get" action="./consignment">
														<input type="hidden" id="actionType" name="actionType" value="searchConsignment">
														<input type="text" class="text-uppercase" id="consignmentNo" name="consignmentNo" value="<%=searchCode %>" maxlength="50" style="width:138px">
														<button class="btn btn-sm btn-inverse" id="submitBtn"><%=Resource.getString("ID_LABEL_SEARCH",locale)%></button>
													</form>
												</span>
												<div class="infobox-content"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></div>
											</div>
										</div>

										<div class="infobox infobox-green" style="width:300px">
											<div class="infobox-icon">
												<i class="icon-filter"></i>
											</div>

											<div class="infobox-data">
												<span>
													<form id="filterForm" name="filterForm" method="get" action="./consignment">
														<input type="hidden" id="actionType" name="actionType" value="filterPartnerCN">
														<select id="partnerId" name="partnerId" style="width:138px">
															<option value="0"></option>
							                                <% 
								                                if(partnerData != null && !partnerData.isEmpty()){
																	for(int i = 0; i < partnerData.size(); i ++){
																		pData = partnerData.get(i);
							                                %>
																<option value="<%=pData.getPid() %>" <% if(partnerId == pData.getPid()) { out.print("selected");} %> ><%=pData.getCname().toString().trim().equals("") ? pData.getEname().toString() : pData.getCname().toString() %></option>
															<% } } %>
														</select>
														<button class="btn btn-sm btn-inverse" id="filterBtn"><%=Resource.getString("ID_LABEL_FILTER",locale)%></button>
													</form>
												</span>
												<div class="infobox-content"><%=Resource.getString("ID_LABEL_PARTNERCONSIGNMENT",locale)%></div>
											</div>
										</div>
										
										<div class="infobox" style="width:200px">
											<button class="btn btn-lg btn-warning" id="calendarViewBtn">
												<i class="fa fa-calendar"></i>
												<%=Resource.getString("ID_LABEL_CALENDARVIEW",locale)%>
											</button>
										</div>
										
										<div class="infobox" style="width:300px">
											<% if(conType.equals("pending")) { %>
												<button class="btn btn-lg btn-success" id="showAllBtn">
													<i class="fa fa-adjust"></i>
													<%=Resource.getString("ID_LABEL_SHOWALLCONSIGNMENT",locale)%>
												</button>
											<% } else { %>
												<button class="btn btn-lg btn-danger" id="showPendingBtn">
													<i class="fa fa-adjust"></i>
													<%=Resource.getString("ID_LABEL_SHOWONLYPENDINGSHIPMENT",locale)%>
												</button>
											<% } %>
										</div>

									</div>
									
									<div class="space-24"></div>
									
									
									<div style="margin-left: 10px; margin-top: 80px; width:300px">	
										
									</div>
									
									
									<div class="space-10"></div>
									
									<%				
										ConsignmentDataModel consignmentData = new ConsignmentDataModel();
										String statusTXT = "";
										String consignmentNo = "";
										String generalCargoNo = "";
										int privilege = 0;
										String accNo = "";
										int status = 0;
										String reasonPending = "";
										int payMethod = 0;
										int payStatus = -1;
										int cid = 0;
										int thisPartnerId = 0;
										int thisAgentId = 0;
										
										if(data != null && data.size() > 0){
											for(int i = 0; i < data.size(); i++){
												consignmentData = (ConsignmentDataModel)data.get(i);
												consignmentNo = consignmentData.getConsignmentNo();
												generalCargoNo = consignmentData.getGeneralCargoNo() == null ? "" : consignmentData.getGeneralCargoNo();
												
												privilege = consignmentData.getPrivilege();
												accNo = consignmentData.getAccNo();
												status = consignmentData.getStatus();
												reasonPending = consignmentData.getReasonPending();
												payMethod = consignmentData.getPayMethod();
												payStatus = consignmentData.getPayStatus();
												statusTXT = ConsignmentBusinessModel.parseStatus(status, locale);
												cid = consignmentData.getCid();
												thisPartnerId = consignmentData.getPartnerId();
												thisAgentId = consignmentData.getAgentId();
												
												all_amount += "#amount_"+consignmentNo+",";
												all_discountReason += "#discountReason_"+consignmentNo+",";
												all_shipmentType += "#shipmentType_"+consignmentNo+",";
												all_senderName += "#senderName_"+consignmentNo+",";
												all_senderAddress1 += "#senderAddress1_"+consignmentNo+",";
												all_senderAddress2 += "#senderAddress2_"+consignmentNo+",";
												all_senderAddress3 += "#senderAddress3_"+consignmentNo+",";
												all_senderPostcode += "#senderPostcode_"+consignmentNo+",";
												all_senderArea += "#senderArea_"+consignmentNo+",";
												all_senderPhone += "#senderPhone_"+consignmentNo+",";
												all_senderIC += "#senderIC_"+consignmentNo+",";
												all_receiverName += "#receiverName_"+consignmentNo+",";
												all_receiverAttn += "#receiverAttn_"+consignmentNo+",";
												all_receiverAddress1 += "#receiverAddress1_"+consignmentNo+",";
												all_receiverAddress2 += "#receiverAddress2_"+consignmentNo+",";
												all_receiverAddress3 += "#receiverAddress3_"+consignmentNo+",";
												all_receiverPostcode += "#receiverPostcode_"+consignmentNo+",";
												all_receiverArea += "#receiverArea_"+consignmentNo+",";
												all_receiverPhone += "#receiverPhone_"+consignmentNo+",";
												all_quantity += "#quantity_"+consignmentNo+",";
												all_weight += "#weight_"+consignmentNo+",";
												all_length += "#length_"+consignmentNo+",";
												all_width += "#width_"+consignmentNo+",";
												all_height += "#height_"+consignmentNo+",";
												all_flightNum += "#flightNum_"+consignmentNo+",";
												all_airwaybillNum += "#airwaybillNum_"+consignmentNo+",";
												all_natureGood += "#natureGood_"+consignmentNo+",";
												all_receiptNum += "#receiptNum_"+consignmentNo+",";
												all_payMethod += "#payMethod_"+consignmentNo+",";
												all_creditArea += "#creditArea_"+consignmentNo+",";
												all_payStatus += "#payStatus_"+consignmentNo+",";
												all_payDT += "#payDT_"+consignmentNo+",";
												all_pickupDriver += "#pickupDriver_"+consignmentNo+",";
												all_pickupDT += "#pickupDT_"+consignmentNo+",";
												all_deliveryDriver += "#deliveryDriver_"+consignmentNo+",";
												all_deliveryDT += "#deliveryDT_"+consignmentNo+",";
												all_pricing += "#pricing_"+consignmentNo+",";
												all_terms += "#terms_"+consignmentNo+",";
												all_abbreviationPartner += "#partnerId_"+consignmentNo+",";
												all_abbreviationAgent += "#agentId_"+consignmentNo+",";
												all_generalCargoNo += "#generalCargoNo_"+consignmentNo+",";
												all_stationRemark += "#stationRemark_"+consignmentNo+",";
												
												if(i==data.size()-1) { //last
													all_amount = all_amount.substring(0, all_amount.length()-1);
													all_discountReason = all_discountReason.substring(0, all_discountReason.length()-1);
													all_shipmentType = all_shipmentType.substring(0, all_shipmentType.length()-1);
													all_senderName = all_senderName.substring(0, all_senderName.length()-1);
													all_senderAddress1 = all_senderAddress1.substring(0, all_senderAddress1.length()-1);
													all_senderAddress2 = all_senderAddress2.substring(0, all_senderAddress2.length()-1);
													all_senderAddress3 = all_senderAddress3.substring(0, all_senderAddress3.length()-1);
													all_senderPostcode = all_senderPostcode.substring(0, all_senderPostcode.length()-1);
													all_senderArea = all_senderArea.substring(0, all_senderArea.length()-1);
													all_senderPhone = all_senderPhone.substring(0, all_senderPhone.length()-1);
													all_senderIC = all_senderIC.substring(0, all_senderIC.length()-1);
													all_receiverName = all_receiverName.substring(0, all_receiverName.length()-1);
													all_receiverAttn = all_receiverAttn.substring(0, all_receiverAttn.length()-1);
													all_receiverAddress1 = all_receiverAddress1.substring(0, all_receiverAddress1.length()-1);
													all_receiverAddress2 = all_receiverAddress2.substring(0, all_receiverAddress2.length()-1);
													all_receiverAddress3 = all_receiverAddress3.substring(0, all_receiverAddress3.length()-1);
													all_receiverPostcode = all_receiverPostcode.substring(0, all_receiverPostcode.length()-1);
													all_receiverArea = all_receiverArea.substring(0, all_receiverArea.length()-1);
													all_receiverPhone = all_receiverPhone.substring(0, all_receiverPhone.length()-1);
													all_quantity = all_quantity.substring(0, all_quantity.length()-1);
													all_weight = all_weight.substring(0, all_weight.length()-1);
													all_length = all_length.substring(0, all_length.length()-1);
													all_width = all_width.substring(0, all_width.length()-1);
													all_height = all_height.substring(0, all_height.length()-1);
													all_flightNum = all_flightNum.substring(0, all_flightNum.length()-1);
													all_airwaybillNum = all_airwaybillNum.substring(0, all_airwaybillNum.length()-1);
													all_natureGood = all_natureGood.substring(0, all_natureGood.length()-1);
													all_receiptNum = all_receiptNum.substring(0, all_receiptNum.length()-1);
													all_payMethod = all_payMethod.substring(0, all_payMethod.length()-1);
													all_creditArea = all_creditArea.substring(0, all_creditArea.length()-1);
													all_payStatus = all_payStatus.substring(0, all_payStatus.length()-1);
													all_payDT = all_payDT.substring(0, all_payDT.length()-1);
													all_pickupDriver = all_pickupDriver.substring(0, all_pickupDriver.length()-1);
													all_pickupDT = all_pickupDT.substring(0, all_pickupDT.length()-1);
													all_deliveryDriver = all_deliveryDriver.substring(0, all_deliveryDriver.length()-1);
													all_deliveryDT = all_deliveryDT.substring(0, all_deliveryDT.length()-1);
													all_pricing = all_pricing.substring(0, all_pricing.length()-1);
													all_terms = all_terms.substring(0, all_terms.length()-1);
													all_abbreviationPartner = all_abbreviationPartner.substring(0, all_abbreviationPartner.length()-1);
													all_abbreviationAgent = all_abbreviationAgent.substring(0, all_abbreviationAgent.length()-1);
													all_generalCargoNo = all_generalCargoNo.substring(0, all_generalCargoNo.length()-1);
													all_stationRemark = all_stationRemark.substring(0, all_stationRemark.length()-1);
												}
												
												String bgColor = ""; //預設顏色
												String dt = consignmentData.getCreateDT().length() > 10 ? consignmentData.getCreateDT().substring(0, 10) : consignmentData.getCreateDT();
												long days = -2;
												
												days = UtilsBusinessModel.compareDate(dt);
												if(status!=1 && days == 0) { //狀態不是"取消" + 當天
													//bgColor = "style='background-color:#FFBBBB'"; //淺紅
												} else if(status!=1 && days == -1) { //狀態不是"取消" + 明天
													//bgColor = "style='background-color:#CDFF82'"; //淺綠
												} else if(days >= 1) { //已過去
													//bgColor = "style='background-color:#FFFF8C'"; //淺黃
												}
												
												String fontclass = "";
												if(consignmentData.getStatus()==3) { //pending shipment
													fontclass = "red";
												}

									%>
										<div class="col-sm-12 widget-container-span table-responsive" style="padding-bottom:38px;">
											<div class="widget-box">
												<div class="widget-header <%=fontclass %>">
													
													<i class="fa fa-send"></i><span style="margin-top:8px; margin-left: 8px"><%=consignmentNo %><% if(generalCargoNo.trim().length() > 0) {out.print(" <small>("+generalCargoNo+")</small>");} %></span>

													<div class="widget-toolbar no-border">
														<ul class="nav nav-tabs" id="myTab">
															<li class="active">
																<a data-toggle="tab" href="#basic_<%=consignmentNo %>">
																	<i class="blue fa fa-info-circle"></i>
																	<%=Resource.getString("ID_LABEL_BASICINFO",locale)%>
																</a>
															</li>

															<li>
																<a data-toggle="tab" href="#detail_<%=consignmentNo %>">
																	<i class="blue fa fa-navicon"></i>
																	<%=Resource.getString("ID_LABEL_DETAILS",locale)%>
																</a>
															</li>

															<li>
																<a data-toggle="tab" href="#manage_<%=consignmentNo %>" onclick="checkDB('<%=consignmentNo %>')">
																	<i class="blue fa fa-cog"></i>
																	<%=Resource.getString("ID_LABEL_ADMINISTRATION",locale)%>
																</a>
															</li>
														</ul>
													</div>
												</div>
												<div class="widget-body">
													<div>
														<div class="tab-content">
															<div id="basic_<%=consignmentNo %>" class="tab-pane in active table-responsive">
																<table class="table table-striped table-bordered" style="margin:0px">
																	<thead>
																		<tr>
																			<th class="center col-sm-1"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_FROMTO",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_SENDER",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_RECEIVER",locale)%></th>
																			<th class="center col-sm-1"><%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%></th>
																			<th class="center col-sm-1"><%=Resource.getString("ID_LABEL_STAGE",locale)%></th>
																			<th class="center col-sm-1"><%=Resource.getString("ID_LABEL_STATUS",locale)%></th>
																		</tr>
																	</thead>

																	<tbody>
																		<tr>
																			<td class="center" <%=bgColor %>>
																				<%=dt %>
																			</td>																			
																			<td class="center" <%=bgColor %>>
																				<%=consignmentData.getConsignmentNo() %><span id="pdf_<%=consignmentData.getConsignmentNo() %>"><i class='fa fa-spinner fa-pulse btn-lg'></i></span>
																			</td>
																			<td class="center" <%=bgColor %>>
																				<%=AreaBusinessModel.parse2AreaName(consignmentData.getSenderArea(), consignmentData.getReceiverArea()) %>
																			</td>
																			<td class="center" <%=bgColor %>>
																				<%=consignmentData.getSenderName() %>
																			</td>
																			<td class="center" <%=bgColor %>>
																				<%=consignmentData.getReceiverName() %>
																			</td>
																			<td class="center" <%=bgColor %>>
																				<% 
																					int shipmentType = 0;
																					if(consignmentData.getShipmentType()==1) {
																						out.print(Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale));
																					} else if(consignmentData.getShipmentType()==2) {
																						out.print(Resource.getString("ID_LABEL_LANDDDTRUCK",locale));
																					} else if(consignmentData.getShipmentType()==3) {
																						out.print(Resource.getString("ID_LABEL_CHARTERTRANSPORT",locale));
																					} else {
																						out.print(Resource.getString("ID_LABEL_UNKNOWN",locale));
																					}
																				%>
																			</td>
																			<td class="center" <%=bgColor %>>
																				<%
																					int stage = consignmentData.getStage();
																					if(stage == 0) {
																						out.print(Resource.getString("ID_LABEL_STAGE0",locale)); //New Request
																					} else if(stage == 1) {
																						out.print(Resource.getString("ID_LABEL_STAGE1",locale)); //Picking Up
																					} else if(stage == 2) {
																						out.print(Resource.getString("ID_LABEL_STAGE2",locale)); //Station Processing
																					} else if(stage == 3) {
																						out.print(Resource.getString("ID_LABEL_STAGE3",locale)); //Loading
																					} else if(stage == 4) {
																						out.print(Resource.getString("ID_LABEL_STAGE4",locale)); //In Transit
																					} else if(stage == 5) {
																						out.print(Resource.getString("ID_LABEL_STAGE5",locale)); //Reached Area
																					} else if(stage == 6) {
																						out.print(Resource.getString("ID_LABEL_STAGE6",locale)); //Delivering
																					} else if(stage == 7) {
																						out.print(Resource.getString("ID_LABEL_STAGE7",locale)); //Reached Receiver
																					} else if(stage == 8) {
																						out.print(Resource.getString("ID_LABEL_STAGE8",locale)); //Closed
																					} else if(stage == 9) {
																						String reason = "";
																						if(consignmentData.getReasonPending().equals("MD")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONMD",locale);
																						} else if(consignmentData.getReasonPending().equals("NA")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONNA",locale);
																						} else if(consignmentData.getReasonPending().equals("NP")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONNP",locale);
																						} else if(consignmentData.getReasonPending().equals("PH")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONPH",locale);
																						} else if(consignmentData.getReasonPending().equals("RF")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONRF",locale);
																						} else if(consignmentData.getReasonPending().equals("SH")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONSH",locale);
																						} else if(consignmentData.getReasonPending().equals("ST")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONST",locale);
																						} else if(consignmentData.getReasonPending().equals("UL")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONUL",locale);
																						} else if(consignmentData.getReasonPending().equals("VB")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONVB",locale);
																						} else if(consignmentData.getReasonPending().equals("WA")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONWA",locale);
																						} else if(consignmentData.getReasonPending().equals("WR")) {
																							reason = Resource.getString("ID_LABEL_PENDINGREASONWR",locale);
																						} else if(consignmentData.getReasonPending().equals("")) {
																							reason = consignmentData.getReasonText();
																						}
																						  
																						out.print(Resource.getString("ID_LABEL_STAGE9",locale) + " <i class=\"fa fa-exclamation-triangle red\" title=\""+reason+"\" rel=\"tooltip\"></i> "); //Pending Shipment
																					}
																				%>
																			</td>
																			<td class="center" <%=bgColor %>>
																				<span id="stat_<%=consignmentNo %>"><%=statusTXT %></span>
																			</td>
																			
																		</tr>
																	</tbody>
																</table>
															</div>

															<div id="detail_<%=consignmentNo %>" class="tab-pane">
																<div class="row">
																
																	<!-- First Column  -->
																	<div class="col-xs-12 col-sm-4">
																		<div class="profile-user-info profile-user-info-striped">
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERNAME",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="senderName_<%=consignmentNo %>"><%=consignmentData.getSenderName() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERIC",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="senderIC_<%=consignmentNo %>"><%=consignmentData.getSenderIC() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERADDRESS",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="senderAddress1_<%=consignmentNo %>"><%=consignmentData.getSenderAddress1() %></span><br/>
																					<span class="editable" id="senderAddress2_<%=consignmentNo %>"><%=consignmentData.getSenderAddress2() %></span><br/>
																					<span class="editable" id="senderAddress3_<%=consignmentNo %>"><%=consignmentData.getSenderAddress3() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERPOSTCODE",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="senderPostcode_<%=consignmentNo %>"><%=consignmentData.getSenderPostcode() %></span>
																				</div>
																			</div>																			
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERAREA",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="senderArea_<%=consignmentNo %>" data-value="<%=consignmentData.getSenderArea() %>"></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_ZONE",locale)%> </div>

																				<div class="profile-info-value">
																					<label id="senderzoneEdit_<%=consignmentNo %>" data-source="<%=AreaBusinessModel.getEditableOption(String.valueOf(consignmentData.getSenderArea())) %>"><span id="senderzoneDIV_<%=consignmentNo %>"><%=consignmentData.getSenderZonename() %></span></label>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERPHONE",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="senderPhone_<%=consignmentNo %>"><%=consignmentData.getSenderPhone() %></span>
																				</div>
																			</div>
																			
																		</div>
																	</div>
																	
																	<!-- Second Column  -->
																	<div class="col-xs-12 col-sm-4">
																		<div class="profile-user-info profile-user-info-striped">
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERNAME",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="receiverName_<%=consignmentNo %>"><%=consignmentData.getReceiverName() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERATTN",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="receiverAttn_<%=consignmentNo %>"><%=consignmentData.getReceiverAttn() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERADDRESS",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="receiverAddress1_<%=consignmentNo %>"><%=consignmentData.getReceiverAddress1() %></span><br/>
																					<span id="receiverAddress2_<%=consignmentNo %>"><%=consignmentData.getReceiverAddress2() %></span><br/>
																					<span id="receiverAddress3_<%=consignmentNo %>"><%=consignmentData.getReceiverAddress3() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERPOSTCODE",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="receiverPostcode_<%=consignmentNo %>"><%=consignmentData.getReceiverPostcode() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERAREA",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="receiverArea_<%=consignmentNo %>" data-value="<%=consignmentData.getReceiverArea() %>"></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_ZONE",locale)%> </div>

																				<div class="profile-info-value">
																					<label id="receiverzoneEdit_<%=consignmentNo %>" data-source="<%=AreaBusinessModel.getEditableOption(String.valueOf(consignmentData.getReceiverArea())) %>"><span id="receiverzoneDIV_<%=consignmentNo %>"><%=consignmentData.getReceiverZonename() %></span></label>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERPHONE",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="receiverPhone_<%=consignmentNo %>"><%=consignmentData.getReceiverPhone() %></span>
																				</div>
																			</div>
																			
																			
																			<!-- 
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_USELOCALE",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="locale_<%=consignmentNo %>" data-value="<%=consignmentData.getUseLocale() %>">
																						<%
																							if(consignmentData.getUseLocale().equals("zh_CN")) {
																								out.println(Resource.getString("ID_LABEL_SCHINESE",locale));
																							} else if(consignmentData.getUseLocale().equals("zh_TW")) {
																								out.println(Resource.getString("ID_LABEL_TCHINESE",locale));
																							} else {
																								out.println(Resource.getString("ID_LABEL_ENGLISH",locale));
																							}
																						%>
																					</span>
																				</div>
																			</div>
																			
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_GUESTMSG",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="helps_<%=consignmentNo %>"><font color="blue"><%=consignmentData.getHelps().length() > 0 ? consignmentData.getHelps().replaceAll("\n", "<br/> ") : "&nbsp;" %></font></span>
																				</div>
																			</div>
																			
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_TAXINVOICE",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="invoice_<%=consignmentNo %>"><i class='fa fa-circle-o-notch fa-spin btn-lg'></i></span>
																				</div>
																			</div>
																			
																			-->
																			
																			<% if(consignmentData.getStatus() == 1){ //cancelled %>
																				<div class="profile-info-row">
																					<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_CANCELDATE",locale)%> </div>
	
																					<div class="profile-info-value">
																						
																					</div>
																				</div>
																				<div class="profile-info-row">
																					<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_WAYTOCANCEL",locale)%> </div>
	
																					<div class="profile-info-value">
																						<% 
																							if (consignmentData.getCancelBy() == 1) {
																								out.println(Resource.getString("ID_LABEL_CANCELBYADMIN",locale));
																							} else if (consignmentData.getCancelBy() == 2) {
																								out.println(Resource.getString("ID_LABEL_CANCELBYUSER",locale));
																							} else if (consignmentData.getCancelBy() == 3) {
																								out.println(Resource.getString("ID_LABEL_CANCELBYEMAIL",locale));
																							} else if (consignmentData.getCancelBy() == 4) {
																								out.println(Resource.getString("ID_LABEL_CANCELBYSYSTEM",locale));
																							}
																						%>
																					</div>
																				</div>
																				<div class="profile-info-row">
																					<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_CANCELREASON",locale)%> </div>
	
																					<div class="profile-info-value">
																						
																					</div>
																				</div>
																			<% } %>
																			
																		</div>
																	</div>
																	
																	
																	<!-- Third Column  -->
																	<div class="col-xs-12 col-sm-4">
																		<div class="profile-user-info profile-user-info-striped">
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="shipmentType_<%=consignmentNo %>" data-value="<%=consignmentData.getShipmentType() %>"></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_ITEMREMARK",locale)%> </div>

																				<div class="profile-info-value">
																					<%
																						String tickvalue = consignmentData.getTickItem();
																						if(!tickvalue.equals("") && tickvalue != null){
																							String tickSplit[] = tickvalue.split("\\,");
																							for(int x = 0; x < tickSplit.length; x++){
																								String tmp = tickSplit[x];
																								
																								if(tmp.equals("tick1")) {
																									out.print("<img src='./etc/tick1.png' title='"+Resource.getString("ID_LABEL_PERISHABLE",locale)+"' /> &nbsp; ");
																								}
																								if(tmp.equals("tick2")) {
																									out.print("<img src='./etc/tick2.png' title='"+Resource.getString("ID_LABEL_FOOD",locale)+"' /> &nbsp; ");
																								}
																								if(tmp.equals("tick3")) {
																									out.print("<img src='./etc/tick3.png' title='"+Resource.getString("ID_LABEL_FRAGILE",locale)+"' /> &nbsp; ");
																								}
																								if(tmp.equals("tick4")) {
																									out.print("<img src='./etc/tick4.png' title='"+Resource.getString("ID_LABEL_EQUIPMENT",locale)+"' />");
																								}
																							}
																						} else {
																							out.print("&nbsp;");
																						}
																					%>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SPECIALHELP",locale)%> </div>

																				<div class="profile-info-value">
																					<font color="blue"><%=consignmentData.getHelps().trim().length() > 0 ? consignmentData.getHelps() : "&nbsp;" %></font>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_CREATEDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="createDT_<%=consignmentNo %>"><%=consignmentData.getCreateDT() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PAYMETHOD",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="payMethod_<%=consignmentNo %>" data-value="<%=payMethod %>">
																						<%=ConsignmentBusinessModel.parsePayMethod(payMethod, locale) %>
																					</span>
																				</div>
																			</div>
																			
																			<% if(payMethod==4) { //credit term %>
																				<div class="profile-info-row">
																					<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_CREDITACCOUNT",locale)%> </div>
	
																					<div class="profile-info-value">
																						<span class="editable" id="creditArea_<%=consignmentNo %>" data-value="<%=consignmentData.getCreditArea() %>"></span>
																					</div>
																				</div>
																			<% } %>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PAYSTATUS",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="payStatus_<%=consignmentNo %>" data-value="<%=payStatus %>">
																						<%=ConsignmentBusinessModel.parsePayStatus(payStatus, locale) %>
																					</span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PAYDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="payDT_<%=consignmentNo %>" data-value="<%=consignmentData.getPayDT() %>">
																						<%=consignmentData.getPayDT().equals("") ? "&nbsp;" : consignmentData.getPayDT() %>
																					</span>
																				</div>
																			</div>
																			
																		</div>
																	</div>
																	
																</div>
																
																<div class="row">
																
																	<!-- Forth Column  -->
																	<div class="col-xs-12 col-sm-4" style="margin-top:30px">
																		<div class="profile-user-info profile-user-info-striped">
																		
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PICKUPDRIVER",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="pickupDriver_<%=consignmentNo %>" data-value="<%=consignmentData.getPickupDriver() %>"></span>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PICKUPDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="pickupDT_<%=consignmentNo %>"><%=consignmentData.getPickupDT().equals("") ? "&nbsp;" : consignmentData.getPickupDT() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_DELIVERYDRIVER",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="deliveryDriver_<%=consignmentNo %>" data-value="<%=consignmentData.getDeliveryDriver() %>"></span>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_DELIVERYDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="deliveryDT_<%=consignmentNo %>"><%=consignmentData.getDeliveryDT().equals("") ? "&nbsp;" : consignmentData.getDeliveryDT() %></span>
																				</div>
																			</div>
																			
																			<!-- 
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_REMARKINEMAIL",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="remkEmail_<%=consignmentNo %>"><%=consignmentData.getRemarkEmail().length() > 0 ? consignmentData.getRemarkEmail() : "" %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_REMARKINRECEIPT",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="remk_<%=consignmentNo %>"><%=consignmentData.getRemark().length() > 0 ? consignmentData.getRemark() : "" %></span>
																				</div>
																			</div>
																			-->
																			
																			
																		</div>
																	</div>
																	
																	<!-- Fifth Column  -->
																	<div class="col-xs-12 col-sm-4" style="margin-top:30px">
																		<div class="profile-user-info profile-user-info-striped">
																																																																																												
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_WEIGHT",locale)%> (KG) </div>

																				<div class="profile-info-value">
																					<span id="weight_<%=consignmentNo %>"><%=consignmentData.getWeight() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_LENGTH",locale)%> (M) </div>

																				<div class="profile-info-value">
																					<span id="length_<%=consignmentNo %>"><%=consignmentData.getLength() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_WIDTH",locale)%> (M) </div>

																				<div class="profile-info-value">
																					<span id="width_<%=consignmentNo %>"><%=consignmentData.getWidth() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_HEIGHT",locale)%> (M) </div>

																				<div class="profile-info-value">
																					<span id="height_<%=consignmentNo %>"><%=consignmentData.getHeight() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_QUANTITY",locale)%> (PCS) </div>

																				<div class="profile-info-value">
																					<span id="quantity_<%=consignmentNo %>"><%=consignmentData.getQuantity() %></span>
																				</div>
																			</div>
																																						
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_AMOUNT",locale)%> (RM) </div>

																				<div class="profile-info-value">
																					<span id="amount_<%=consignmentNo %>"><%=consignmentData.getAmount() %></span>
																					
																					<%if(priv==9 || priv==99){ %>
																						<a id="amountModal" onclick="editAmount('<%=consignmentNo %>')" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="Change Amount">
																							<span class="blue">
																								<i class="icon-edit bigger-120"></i>
																							</span>
																						</a>
																					<%} %>
																					<a id="discountModal" onclick="editDiscount('<%=consignmentNo %>')" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="Discount">
																						<span class="green">
																							<i class="icon-edit bigger-120"></i>
																						</span>
																					</a>
																					<input type="hidden" id="discountReasonModal_<%=consignmentNo %>" name="discountReasonModal_<%=consignmentNo %>" value="<%=consignmentData.getDiscountReason() %>" />
																					<input type="hidden" id="amountModal_<%=consignmentNo %>" name="amountModal_<%=consignmentNo %>" value="<%=consignmentData.getAmount() %>" />
																				</div>
																				
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_STATIONREMARK",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="stationRemark_<%=consignmentNo %>"><%=consignmentData.getStationRemark() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row" style="display:<%=generalCargoNo != "" ? "block" : "none" %>;" >
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_FLIGHTNUM",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="flightNum_<%=consignmentNo %>"><%=consignmentData.getFlightNum() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row" style="display:<%=generalCargoNo != "" ? "block" : "none" %>;" >
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_AIRWAYBILLNUM",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="airwaybillNum_<%=consignmentNo %>"><%=consignmentData.getAirwaybillNum() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row" style="display:<%=generalCargoNo != "" ? "block" : "none" %>;" >
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_NATUREOFGOOD",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="natureGood_<%=consignmentNo %>"><%=consignmentData.getNatureGood() %></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row" style="display:<%=generalCargoNo != "" ? "block" : "none" %>;" >
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIPTNUM",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="receiptNum_<%=consignmentNo %>"><%=consignmentData.getReceiptNum() %></span>
																				</div>
																			</div>
																			
																			
																		</div>
																	</div>
																	
																	<!-- Sixth Column  -->
																	<div class="col-xs-12 col-sm-4" style="margin-top:30px">
																		<div class="profile-user-info profile-user-info-striped">
																		
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_NETWORKPARTNER",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="partnerId_<%=consignmentNo %>" data-value="<%=thisPartnerId %>">
																						<%=consignmentData.getAbbreviationPartner() %>
																					</span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_GENERALCARGOCNNUMBER",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="generalCargoNo_<%=consignmentNo %>" data-value="<%=consignmentData.getGeneralCargoNo() %>"></span>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_TERMS",locale)%> </div>

																				<div class="profile-info-value">
																					<span id="terms_<%=consignmentNo %>" data-value="<%=consignmentData.getTerms() %>"></span>
																				</div>
																			</div>

																		</div>
																	</div>
																	
																	<!-- Seventh Column  -->
																	<div class="col-xs-12 col-sm-4" style="margin-top:30px">
																		<div class="profile-user-info profile-user-info-striped">
																		
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_AGENT",locale)%> </div>

																				<div class="profile-info-value">
																					<span class="editable" id="agentId_<%=consignmentNo %>" data-value="<%=thisAgentId %>">
																						<%=consignmentData.getAbbreviationAgent() %>
																					</span>
																				</div>
																			</div>
																			

																		</div>
																	</div>
																	
																	
																</div>
																
															</div>

															<div id="manage_<%=consignmentNo %>" class="tab-pane">
																<div class="tabs">
																	<ul class="nav nav-tabs" id="myTab">
																		<li><a href="#tabs-<%=consignmentNo %>"><%=Resource.getString("ID_LABEL_MANAGE",locale) %></a></li>
																		<li>
																			<a href="./log?actionType=cpconsignmentinternalnote&consignmentNo=<%=consignmentNo %>">
																				<%=Resource.getString("ID_LABEL_INTERNALNOTE", locale) %>
																				<span class="badge badge-danger" id="count_<%=consignmentNo %>"></span>
																			</a>
																		</li>
																		<li><a href="./log?actionType=cpconsignmentlog&consignmentNo=<%=consignmentNo %>"><%=Resource.getString("ID_LABEL_MAINTENANCELOG",locale) %></a></li>
																		<li><a href="./log?actionType=cpstagelog&consignmentNo=<%=consignmentNo %>"><%=Resource.getString("ID_LABEL_STAGELOG",locale) %></a></li>
																		<li><a href="./consignment?actionType=misc&consignmentNo=<%=consignmentNo %>"><%=Resource.getString("ID_LABEL_MISC",locale) %></a></li>
																	</ul>
																	<div id="tabs-<%=consignmentNo %>">
																	
																		<div class="row">
																
																			<div class="col-xs-12 col-sm-6">
																			
																				<div class="profile-user-info profile-user-info-striped">
																				
																					<% if(consignmentData.getStaffCreate()==1) { %>
																						<div class="profile-info-row">
																							<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_USERID",locale)%> </div>
	
																							<div class="profile-info-value">
																								<span id="userid_<%=consignmentNo %>"><%=consignmentData.getUserId() %></span>
																							</div>
																						</div>
																					
																					<% } else { %>
																						<div class="profile-info-row">
																							<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_ENAME",locale)%> </div>
	
																							<div class="profile-info-value">
																								<span id="ename">
																									<% 
																										if(consignmentData.getGender()==1) { 
																											out.print("Mr. ");
																										} else if(consignmentData.getGender()==2) {
																											out.print("Ms. ");
																										} 
																									%>
																									<%=consignmentData.getEname() %>
																									<%
																										if(consignmentData.getCname().trim().length() > 0) {
																											out.print("("+consignmentData.getCname()+")");	
																										}
																									%>
																								</span>
																							</div>
																						</div>
																						
																						<div class="profile-info-row">
																							<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_USERID",locale)%> </div>
	
																							<div class="profile-info-value">
																								<span id="userid_<%=consignmentNo %>"><a id="userDetails" userId="<%=consignmentData.getUserId() %>" style="cursor:pointer;"><%=consignmentData.getUserId() %></a></span>
																							</div>
																						</div>
																						
																						<%
																							String accountNo = "none";
																							if(privilege == 2 && accNo != ""){ //Account Number
																								accountNo = "block";
																							}
																						%>
																						
																						
																						<div class="profile-info-row"  style="display:<%=accountNo %>;">
																							<div class="profile-info-name"> Account Number </div>
	
																							<div class="profile-info-value">
																								<span id="accNo"><%=accNo %></span>
																							</div>
																						</div>
																						
																						
																						<div class="profile-info-row">
																							<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_EMAIL",locale)%> </div>
	
																							<div class="profile-info-value">
																								<span id="email"><%=consignmentData.getEmail() %></span>
																							</div>
																						</div>
																						
																						<div class="profile-info-row">
																							<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_TOTALCONSIGNMENT",locale)%> </div>
	
																							<div class="profile-info-value">
																								<span id="totalconsignment"><a href="consignment?actionType=adminCheckUser&check=<%=consignmentData.getUserId() %>" target="_blank"><b><span id="countTotalConsignment_<%=consignmentNo %>"></span> <%=Resource.getString("ID_LABEL_XCONSIGNMENT", locale) %></b></a></span>
																							</div>
																						</div>
																					<% } %>
																					
																				</div>
																			</div>
																			
																			<div class="col-xs-12 col-sm-6">
																				<div class="profile-user-info profile-user-info-striped">
																					<div class="profile-info-row">
																						<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PROCESSSTATUS",locale)%> </div>

																						<div class="profile-info-value">
																							<select id="status_<%=consignmentNo %>" name="status_<%=consignmentNo %>" onchange="changeStatusCheck(this)">
																								<option value="0" <% if(status==0) out.print("selected"); %>><%=Resource.getString("ID_LABEL_STATUS0",locale) %></option>
																								<option value="1" <% if(status==1) out.print("selected"); %>><%=Resource.getString("ID_LABEL_STATUS1",locale) %></option>
																								<option value="2" <% if(status==2) out.print("selected"); %>><%=Resource.getString("ID_LABEL_STATUS2",locale) %></option>
																								<option value="3" <% if(status==3) out.print("selected"); %>><%=Resource.getString("ID_LABEL_STATUS3",locale) %></option>
																								<option value="4" <% if(status==4) out.print("selected"); %>><%=Resource.getString("ID_LABEL_STATUS4",locale) %></option>
																							</select>
																							<br /><font color="red"><span id="sendEmailHint_<%=consignmentNo %>"></span></font>
																						</div>
																					</div>
																					
																					<%
																						String NDisplay = "none";
																						if(status == 3){ //Pending Shipment
																							NDisplay = "block";
																						}
																					%>
																					
																					<span id="remarkNoti_<%=consignmentNo %>" style="display:<%=NDisplay %>;">
																						<div class="profile-info-row">
																							<div class="profile-info-name" style="border-top: 0px none;"> <%=Resource.getString("ID_LABEL_REASON",locale)%> </div>
	
																							<div class="profile-info-value" style="border-top: 0px none;">
																								<select name="reasonPending_<%=consignmentNo %>" id="reasonPending_<%=consignmentNo %>" onchange="changeReasonCheck(this)">
																							      <option value="MD" <% if(consignmentData.getReasonPending().equals("MD") || consignmentData.getReasonPending().equals("") && consignmentData.getReasonText().equals("")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONMD",locale)%></option>
																							      <option value="NA" <% if(consignmentData.getReasonPending().equals("NA")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONNA",locale)%></option>
																							      <option value="NP" <% if(consignmentData.getReasonPending().equals("NP")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONNP",locale)%></option>
																								  <option value="PH" <% if(consignmentData.getReasonPending().equals("PH")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONPH",locale)%></option>
																								  <option value="RF" <% if(consignmentData.getReasonPending().equals("RF")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONRF",locale)%></option>
																								  <option value="SH" <% if(consignmentData.getReasonPending().equals("SH")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONSH",locale)%></option>
																								  <option value="ST" <% if(consignmentData.getReasonPending().equals("ST")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONST",locale)%></option>
																								  <option value="UL" <% if(consignmentData.getReasonPending().equals("UL")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONUL",locale)%></option>
																								  <option value="VB" <% if(consignmentData.getReasonPending().equals("VB")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONVB",locale)%></option>
																								  <option value="WA" <% if(consignmentData.getReasonPending().equals("WA")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONWA",locale)%></option>
																								  <option value="WR" <% if(consignmentData.getReasonPending().equals("WR")){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONWR",locale)%></option>
																								  <option value="" <% if(consignmentData.getReasonPending().equals("") && consignmentData.getReasonText() != ""){ out.print("selected"); } %>><%=Resource.getString("ID_LABEL_PENDINGREASONOTHERS",locale)%></option>
																						        </select>
																							</div>
																						</div>
																					</span>
																					
																					<%
																						String rtext = "none";
																						if(status == 3 && reasonPending == ""){ //Others
																							rtext = "block";
																						}
																					%>
																					
																					<span id="otherReasons_<%=consignmentNo %>" style="display:<%=rtext %>;">
																						<div class="profile-info-row">
																							<div class="profile-info-name" style="border-top: 0px none;"></div>
																							
																							<div class="profile-info-value">
																								<input type="text" name="reasonText_<%=consignmentNo %>" id="reasonText_<%=consignmentNo %>" placeholder="<%=Resource.getString("ID_LABEL_PENDINGREASONOTHERSTXT",locale)%>" maxlength="100" value="<%=consignmentData.getReasonText() %>">		
																							</div>
																						</div>
																					</span>
																																										
																					<div class="profile-info-row">
																						<div class="profile-info-name"> </div>
																						<div class="profile-info-value">
																							<a class="btn btn-primary" id="goBtn_<%=consignmentNo %>" onclick="changeStatus('<%=consignmentNo %>')">
																								<i class="fa fa-check-circle"></i>
																								<%=Resource.getString("ID_LABEL_SUBMIT",locale)%>
																							</a>
																							
																							<span id="loadingBtn_<%=consignmentNo %>" style="display:none; padding:10px;"><%=Resource.getString("ID_LABEL_PROCESSING",locale)%> <i class='fa fa-circle-o-notch fa-spin'></i></span>
																							<div id="success_<%=consignmentNo %>" class="text-success" style="display:none;"></div>
																							<div id="noReason_<%=consignmentNo %>" class="text-danger" style="display:none;"></div>
																							<div id="error_<%=consignmentNo %>"  class="text-danger" style="display:none;"></div>
																							
																						</div>
																					</div>
																					
																				</div>
																			</div>																			
																			
																		</div>
																		
																	</div>
																</div>
																
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									
									<% } } else { out.println("<div class='space-50'></div><div class=\"text-center\">*** " +Resource.getString("ID_MSG_NODATA",locale)+ " ***</div>");} %>
									
									<% if(actionType.equals("fastSearch")||actionType.equals("searchConsignmentByDay")) { %>
										<div class="text-center"><h4><%=Resource.getString("ID_LABEL_TOTAL",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%><%=consignmentData.getTotal() %></h4></div>
									<% } else { %>
									
									<div class="text-center">
											
										<%
											//分頁
											int numPerPage = 5; //每頁顯示數量
											String pnoStr = request.getParameter("page");
											Integer pno = pnoStr == null ? 1 : Integer.parseInt(pnoStr);
											pager.setPageNo(pno.intValue());
											pager.setPerLen(numPerPage);
											int total = 0;
											int thisPage = 0;
											String p = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
											
											//防止user亂輸入頁碼
											boolean isNum = ConsignmentController.isNumeric(p);
											
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

										
											total = consignmentData.getTotal();
											pager.setData(total);//设置数据
											int []range = pager.getRange(numPerPage);//获得页码呈现的区间
											if(pager.getMaxPage()>1) {//要呈现的页面超过1页，需要分页呈现
												out.print("<ul class=\"pagination\">");
													
												//上10頁按鍵
												if(thisPage > 1){
													out.print("<li><a href=\"./"+pagelink+"?" + searchPartnerCN + "&page="+prev10+"\" rel=\"tooltip\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁													
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./"+pagelink+"?" + searchPartnerCN + "&page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./"+pagelink+"?" + searchPartnerCN + "&page="+(next10)+"\" rel=\"tooltip\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
												}
												
												out.print("</ul>");
										   }
										%>
											
									</div>
									
									<% } %>
										
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<!--=== Edit Discount Modal Start ===-->
		<div class="modal fade" id="editDiscount" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h2 class="modal-title" id=""> Discount </h2>
					</div>
					<div class="modal-body">
						
						<form id="discountForm" name="discountForm" class="reg-page" style="width:90%;margin:0px auto!important;" method="post" action="./consignment">
							<input type="hidden" id="actionType" name="actionType" value="submitDiscount">
							<input type="hidden" id="consignmentNo" name="consignmentNo" value="">
							<input type="hidden" id="discounted" name="discounted" value="">
							
							<div class="margin-bottom-20"></div>
		
							<label>Amount (RM)</label>
							<input id="amount" name="amount" type="text" class="form-control" readonly />
							
							<div class="space-4"></div>
							
							<label>Discount Rate</label>
							<select name="discount" id="discount" class="form-control">
								<option value=""></option>
								<option value="5">5%</option>
								<option value="10">10%</option>
								<option value="20">20%</option>
								<option value="30">30%</option>
							</select>
							
							<div class="space-4"></div>
							
							<label>Discount Reason</label>
							<textarea id="discountReason" name="discountReason" type="text" style="resize:vertical" class="form-control" maxlength="200"></textarea>
							
							<div class="space-24"></div>
							
		                </form>
					</div>
					<div class="modal-footer">
						<span id="loading"></span>
						<button type="button" class="btn btn-default" id="cancel" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
						<button type="button" class="btn btn-info" id="submitDiscount"><%=Resource.getString("ID_LABEL_SUBMIT",locale) %></button>
						<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal" style="display:none;"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
					</div>
				</div>
			</div>
		</div>
		<!--=== Edit Discount Modal End ===-->
		
		<!--=== Edit Amount Modal Start ===-->
		<div class="modal fade" id="editAmount" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h2 class="modal-title" id=""> Edit Amount </h2>
					</div>
					<div class="modal-body">
						
						<form id="discountForm" name="discountForm" class="reg-page" style="width:90%;margin:0px auto!important;" method="post" action="./consignment">
							<input type="hidden" id="actionType" name="actionType" value="submitAmount">
							<input type="hidden" id="consignmentNo" name="consignmentNo" value="">
							
							<div class="margin-bottom-20"></div>
		
							<label>Amount (RM)</label>
							<input id="amount" name="amount" type="text" class="form-control" />
							
							<div class="space-24"></div>
							
		                </form>
					</div>
					<div class="modal-footer">
						<span id="loading1"></span>
						<button type="button" class="btn btn-default" id="cancel" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
						<button type="button" class="btn btn-info" id="submitAmount"><%=Resource.getString("ID_LABEL_SUBMIT",locale) %></button>
						<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal" style="display:none;"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
					</div>
				</div>
			</div>
		</div>
		<!--=== Edit Amount Modal End ===-->
		
		<%@include file="cpFooter.jsp" %>
		
		<jsp:include page="cpModal.jsp" />
		
		<%@include file="cpExtJs.jsp" %>

		<script type="text/javascript">
			var _$ = $.noConflict(true);
        </script>
		<script type="text/javascript">
			$(document).ready(function() {
				
				<% 
					if(!errmsg.trim().equals("")) {
						out.print("alert('"+errmsg+"')");
					}
				%>
				
				//check Consignment Note PDF files
				$.ajax({
					url: './resource',        
					data: 'actionType=checkConsignmentnote&consignmentNoStream=<%=consignmentNoStream %>',
					dataType: 'json',
					success: function(response) {
					
					<%
						if(data != null && data.size() > 0){
							for (int i = 0; i < data.size(); i++) {
								ConsignmentDataModel tmp = (ConsignmentDataModel)data.get(i);
								
								String cd = tmp.getConsignmentNo();
					%>
								var cd_<%=cd %> = response.cd_<%=cd %>;
								if (cd_<%=cd %> == 1) {
									var iconLink = " <a href='https://s3-ap-southeast-1.amazonaws.com/iposb/pdf/<%=cd %>-IPOSB.pdf' target='_blank'><img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a>";
									$('#pdf_<%=cd %>').html(iconLink);
								} else {
									$('#pdf_<%=cd %>').html("");
								}
					<%	
						}}
					%>
					}
				});
				
				
				//check Tax Invoice PDF files
				$.ajax({
					url: './resource',        
					data: 'actionType=checkInvoice&invoiceNoconsignmentNoStream=<%=invoiceNoconsignmentNoStream %>',
					dataType: 'json',
					success: function(response) {
					
					<%
						if(data != null && data.size() > 0){
							for (int i = 0; i < data.size(); i++) {
								ConsignmentDataModel tmp = (ConsignmentDataModel)data.get(i);
								String cd = tmp.getConsignmentNo();
					%>
								if (response.cd_<%=cd %> == 1) {
									var iconLink = "<a href='https://s3-ap-southeast-1.amazonaws.com/iposb/invoice/<%=tmp.getInvoiceNo() %>-<%=cd %>-IPOSB.pdf' target='_blank'><img src='<%=resPath %>/resources/common/assets/img/pdf.gif' border='0' /></a>";
									$('#invoice_<%=cd %>').html(iconLink);
								} else {
									$('#invoice_<%=cd %>').html("&nbsp;");
								}
					<%	
						}}
					%>
					}
				});
				
				
				//toggle `popup` / `inline` mode
				$.fn.editable.defaults.url = './consignment?actionType=inlineUpdate'; 
				$.fn.editable.defaults.mode = 'inline';  
				$.fn.editable.defaults.send = 'always';
				
				if(<%=priv%> >= 7){
					$('#discountModal').show();
					
//					$('<%=all_amount %>').editable({
//						type: 'text',
//					    params: function (params) {
//							params.field = "amount";
//							params.dataType = "num";
//							params.oriAmount = parseFloat($('<%=all_amount %>').html()).toFixed(2);
//							return params;
//						}
//				    });
					
				}else if(<%=priv%> < 7){
					$('#discountModal').hide();
				};
				
				
				$('<%=all_senderArea %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
							AreaDataModel arData = new AreaDataModel();
							if(areaData != null && !areaData.isEmpty()){
								for(int i = 0; i < areaData.size(); i ++){
									arData = areaData.get(i);
									String selected = "";
									String options = "";
									options = "{value: '"+arData.getAid()+"', text: '" + arData.getName_enUS() +" ("+ arData.getCode() + ")'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "senderArea";
						params.dataType = "num";
						return params;
					}
					
				});

				
				$('<%=all_receiverArea %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							arData = new AreaDataModel();
							if(areaData != null && !areaData.isEmpty()){
								for(int i = 0; i < areaData.size(); i ++){
									arData = areaData.get(i);
									String selected = "";
									String options = "";
									options = "{value: '"+arData.getAid()+"', text: '" + arData.getName_enUS() +" ("+ arData.getCode() + ")'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "receiverArea";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_shipmentType %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						{value: 0, text: '<%=Resource.getString("ID_LABEL_UNKNOWN",locale) %>'},
						{value: 1, text: '<%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale) %>'},
						{value: 2, text: '<%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale) %>'},
						{value: 3, text: '<%=Resource.getString("ID_LABEL_CHARTERTRANSPORT",locale) %>'}
					],
					params: function (params) {
						params.field = "shipmentType";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_senderName %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderName";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_senderAddress1 %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderAddress1";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_senderAddress2 %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderAddress2";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_senderAddress3 %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderAddress3";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_senderPostcode %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderPostcode";
						params.dataType = "txt";
						return params;
					}
			    });

				$('<%=all_senderPhone %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderPhone";
						params.dataType = "txt";
						return params;
					}
			    });
				
				
				$('<%=all_senderIC %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "senderIC";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverName %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverName";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverAttn %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverAttn";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverAddress1 %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverAddress1";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverAddress2 %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverAddress2";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverAddress3 %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverAddress3";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverPostcode %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverPostcode";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiverPhone %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiverPhone";
						params.dataType = "txt";
						return params;
					}
			    });
								
				$('<%=all_pricing %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "pricing";
						params.dataType = "num";
						return params;
					}
			    });
				
				$('<%=all_quantity %>').editable({
					type: 'spinner',
					spinner : {
						min : 1, max:9999, step:1, touch_spinner: true
					},
				    params: function (params) {
						params.field = "quantity";
						params.dataType = "num";
						return params;
					}
			    });
				
				$('<%=all_weight %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "weight";
						params.dataType = "num";
						return params;
					}
			    });
				
				$('<%=all_length %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "length";
						params.dataType = "num";
						return params;
					}
			    });
				
				$('<%=all_width %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "width";
						params.dataType = "num";
						return params;
					}
			    });
				
				$('<%=all_height %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "height";
						params.dataType = "num";
						return params;
					}
			    });
				
				$('<%=all_flightNum %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "flightNum";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_airwaybillNum %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "airwaybillNum";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_natureGood %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "natureGood";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_receiptNum %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "receiptNum";
						params.dataType = "txt";
						return params;
					}
			    });
			
				$('<%=all_payMethod %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						{value: 0, text: '<%=Resource.getString("ID_LABEL_UNKNOWN",locale) %>'},
						{value: 1, text: '<%=Resource.getString("ID_LABEL_PAYBYCASH",locale) %>'},
						{value: 2, text: '<%=Resource.getString("ID_LABEL_PAYBYCREDITCARD",locale) %>'},
						{value: 3, text: '<%=Resource.getString("ID_LABEL_PAYBYBANKIN",locale) %>'},
						{value: 4, text: '<%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale) %>'},
						{value: 5, text: '<%=Resource.getString("ID_LABEL_PAYBYCOD",locale) %>'},
						{value: 6, text: '<%=Resource.getString("ID_LABEL_FREIGHTCHARGES",locale) %>'},
						{value: 7, text: '<%=Resource.getString("ID_LABEL_COMPANYMAIL",locale) %>'}
					],
					params: function (params) {
						params.field = "payMethod";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_creditArea %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							if(areaData != null && !areaData.isEmpty()){
								for(int i = 0; i < areaData.size(); i ++){
									arData = areaData.get(i);
									String selected = "";
									String options = "";
									options = "{value: '"+arData.getAid()+"', text: '" + arData.getName_enUS() +" ("+ arData.getCode() + ")'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "creditArea";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_payStatus %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						{value: 0, text: '<%=Resource.getString("ID_LABEL_NOTPAID",locale) %>'},
						{value: 1, text: '<%=Resource.getString("ID_LABEL_PAID100",locale) %>'},
						{value: 9, text: '<%=Resource.getString("ID_LABEL_FREE",locale) %>'}
					],
					params: function (params) {
						params.field = "payStatus";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_payDT %>').editable({
					type: 'date',
					params: function (params) {
						params.field = "payDT";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_pickupDriver %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							ArrayList <LogonDataModel> driverData = (ArrayList)request.getAttribute("driver");
							LogonDataModel dData = new LogonDataModel();
							if(driverData != null && !driverData.isEmpty()){
								for(int i = 0; i < driverData.size(); i ++){
									String selected = "";
									String options = "";
									dData = driverData.get(i);
									String driverName = dData.getCname().toString().trim().equals("") ? dData.getEname().toString() : dData.getCname().toString();
									options = "{value: '"+dData.getUserId()+"', text: '" + driverName + "'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "pickupDriver";
						params.dataType = "txt";
						return params;
					}
					
				});
				
				
				$('<%=all_pickupDT %>').editable({
					type: 'date',
					params: function (params) {
						params.field = "pickupDT";
						params.dataType = "txt";
						return params;
					}
			    });
				
				
				$('<%=all_deliveryDriver %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							dData = new LogonDataModel();
							if(driverData != null && !driverData.isEmpty()){
								for(int i = 0; i < driverData.size(); i ++){
									String selected = "";
									String options = "";
									dData = driverData.get(i);
									String driverName = dData.getCname().toString().trim().equals("") ? dData.getEname().toString() : dData.getCname().toString();
									options = "{value: '"+dData.getUserId()+"', text: '" + driverName + "'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "deliveryDriver";
						params.dataType = "txt";
						return params;
					}
					
				});
				
				$('<%=all_abbreviationPartner %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							if(partnerData != null && !partnerData.isEmpty()){
								for(int i = 0; i < partnerData.size(); i ++){
									pData = partnerData.get(i);
									String selected = "";
									String options = "";
									String partnerName = pData.getCname().toString().trim().equals("") ? pData.getEname().toString() : pData.getCname().toString();
									options = "{value: '"+pData.getPid()+"', text: '" + partnerName + "'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "partnerId";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_abbreviationAgent %>').editable({
					type: 'select',
					placement: 'right',
					source: [
						<% 
							if(agentData != null && !agentData.isEmpty()){
								for(int i = 0; i < agentData.size(); i ++){
									aData = agentData.get(i);
									String selected = "";
									String options = "";
									String agentName = aData.getCname().toString().trim().equals("") ? aData.getEname().toString() : aData.getCname().toString();
									options = "{value: '"+aData.getPid()+"', text: '" + agentName + "'},";
									out.println(options);
								}
							}
						%>
					],
					params: function (params) {
						params.field = "agentId";
						params.dataType = "num";
						return params;
					}
					
				});
				
				$('<%=all_deliveryDT %>').editable({
					type: 'date',
					params: function (params) {
						params.field = "deliveryDT";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_terms %>').editable({
					type: 'text',
					params: function (params) {
						params.field = "terms";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_generalCargoNo %>').editable({
					type: 'text',
					params: function (params) {
						params.field = "generalCargoNo";
						params.dataType = "txt";
						return params;
					}
			    });
				
				$('<%=all_stationRemark %>').editable({
					type: 'text',
				    params: function (params) {
						params.field = "stationRemark";
						params.dataType = "txt";
						return params;
					}
			    });
				
				
				$("label[id *= 'senderzoneEdit_']").editable({
					type: "select", 
					send: "never",
					display: function(value, sourceData) {
						var consignmentNo = $(this).attr("id").substring($(this).attr("id").indexOf("_") + 1, $(this).attr("id").length);

				        var $el = $('#senderzoneDIV_' + consignmentNo), checked, html = '';
				        if(!value) {
				            return;
				        }            
				        
				        checked = $.grep(sourceData, function(o){
				              return $.grep(value, function(v){ 
				                   return v == o.value; 
				              }).length;
				        });

				        $.each(checked, function(i, zone) { 
				        	var zoneName =  $.fn.editableutils.escape(zone.text);
				        	$el.html(zoneName);
				        });
				
				        updateData(consignmentNo, "senderZone", value);
				    }
				  	
			    });
				
				
				$("label[id *= 'receiverzoneEdit_']").editable({
					type: "select", 
					send: "never",
					display: function(value, sourceData) {
						var consignmentNo = $(this).attr("id").substring($(this).attr("id").indexOf("_") + 1, $(this).attr("id").length);

				        var $el = $('#receiverzoneDIV_' + consignmentNo), checked, html = '';
				        if(!value) {
				            return;
				        }            
				        
				        checked = $.grep(sourceData, function(o){
				              return $.grep(value, function(v){ 
				                   return v == o.value; 
				              }).length;
				        });

				        $.each(checked, function(i, zone) { 
				        	var zoneName =  $.fn.editableutils.escape(zone.text);
				        	$el.html(zoneName);
				        });
				
				        updateData(consignmentNo, "receiverZone", value);
				    }
				    
				  	
			    });
				
				$("#editDiscount #submitDiscount").click(function(){
					
					var amount = $("#editDiscount #amount").val();
					var discount = $("#editDiscount #discount").val();
					var discountReason = $("#editDiscount #discountReason").val();
					var consignmentNo = $("#editDiscount #consignmentNo").val();
					var discounted = 0.0;
					
					if (discount == 0) {
						$("#editDiscount #loading").show();
						$("#editDiscount #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Please select a discount rate</span>&nbsp;&nbsp;&nbsp;");
						return false;
					}else if (discount != 0 && discountReason == "") {
						$("#editDiscount #loading").show();
						$("#editDiscount #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\"><%=Resource.getString("ID_MSG_PLSINPUTREASON",locale)%></span>&nbsp;&nbsp;&nbsp;");
						return false;
					}
					
			   		$("#editDiscount #loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#editDiscount #cancel").hide();
					$("#editDiscount #submitDiscount").hide();
					$.ajax({
						type: 'POST',
						url: './consignment',
						data:{    
							lang: "<%=locale %>",
							actionType:"submitDiscount",
							amount: amount,
							discount: discount,
							discountReason: discountReason,
							consignmentNo: consignmentNo
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "OK"){
									$("#editDiscount #loading").show();
									$("#editDiscount #loading").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\"><%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%></span>&nbsp;&nbsp;&nbsp;");
									
									if(discount ==  5){
										discounted = amount * 0.95;
									} else if(discount == 10){
										discounted = amount * 0.9;
									} else if(discount == 20){
										discounted = amount * 0.8;
									} else if(discount == 30){
										discounted = amount * 0.7;
									}
										
									discounted = parseFloat(discounted).toFixed(2);
									$('#editDiscount #discounted').val(discounted);
									$('#amount_'+ consignmentNo).html(discounted);
									$("#amountModal_" + consignmentNo).val(discounted);
									$("#editDiscount #amount").val(discounted);
									$("#discountReasonModal_" + consignmentNo).val(discountReason);
									
									$("#editDiscount #closeBtn").show();
								} else {
									$("#editDiscount #loading").show();
									$("#editDiscount #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\"><%=Resource.getString("ID_MSG_UPDATEFAILED",locale) + Resource.getString("ID_LABEL_COLON",locale) %>" + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#editDiscount #closeBtn").hide();
									$("#editDiscount #cancel").show();
									$("#editDiscount #submitDiscount").show();
								}
							}
						} 
					});
					
				});
				
				$("#editAmount #submitAmount").click(function(){
					
					var amount = $("#editAmount #amount").val();
					var consignmentNo = $("#editAmount #consignmentNo").val();
					
			   		$("#editAmount #loading1").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#editAmount #cancel").hide();
					$("#editAmount #submitAmount").hide();
					$.ajax({
						type: 'POST',
						url: './consignment',
						data:{    
							lang: "<%=locale %>",
							actionType:"changeAmount",
							amount: amount,
							consignmentNo: consignmentNo
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "OK"){
									$("#editAmount #loading1").show();
									$("#editAmount #loading1").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\"><%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%></span>&nbsp;&nbsp;&nbsp;");
									
									$('#amount_'+ consignmentNo).html(amount);
									$("#amountModal_" + consignmentNo).val(amount);
									
									$("#editAmount #closeBtn").show();
									$("#editAmount #submitAmount").show();
								} else {
									$("#editAmount #loading1").show();
									$("#editAmount #loading1").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\"><%=Resource.getString("ID_MSG_UPDATEFAILED",locale) + Resource.getString("ID_LABEL_COLON",locale) %>" + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#editAmount #closeBtn").hide();
									$("#editAmount #cancel").show();
									$("#editAmount #submitAmount").show();
								}
							}
						} 
					});
					
				});
				
				$("#editDiscount #closeBtn").click(function(){});
				
				
			});
			
			function updateData(consignmentNo, field, value){
				$(function() {
					$.ajax({
						type: 'POST',
						url: './consignment',        
						data: {
							actionType: 'inlineUpdate',
							name: consignmentNo,
							value: value,
							dataType: 'num',
							field: field
						},
						success: function(response) {
							
						} 
					});
				});
			};
			
			
			function checkDB(consignmentNo){
				
				//Calculate Total Consignment
				var userId = $( "#userid_"+consignmentNo ).text();
				$(function() {
					$.ajax({
						url: './consignment',        
						data: "actionType=counttotalconsignment&userId="+encodeURIComponent(userId.replace("DOTTED",".").replace("SPACE"," ").replace("ALLIANCE","@")),
						success: function(response) {
							if(eval(response)>0) {
								$('#countTotalConsignment_'+consignmentNo).html(response);
							}
						}
					});
				});
			
				//Calculate Internal Note
				$(function() {
					$.ajax({
						url: './log',        
						data: "actionType=countinternalnote&consignmentNo="+consignmentNo,
						success: function(response) {
							if(eval(response)>0) {
								$('#count_'+consignmentNo).html(response);
							}
						}
					});
				});
			};
			
			
			//fetch tab data with ajax
			$(function() {
				$( ".tabs" ).tabs({
					ajaxOptions: {
						error: function( xhr, status, index, anchor ) {
							$( anchor.hash ).html(
								"<%=Resource.getString("ID_MSG_QUERYFAILED",locale) %>" );
						}
					}
				});
			});
			
			function editDiscount(id){
				
				$(function(){
					$("#editDiscount #cancel").hide();
					$("#editDiscount #submitDiscount").hide();
					$("#editDiscount #closeBtn").hide();
					
					$("#editDiscount #loading").hide();
					
					$("#editDiscount #cancel").show();
					$("#editDiscount #submitDiscount").show();
					
					var amount = $("#amountModal_" + id).val();
					var discountReason = $("#discountReasonModal_" + id).val();

					$("#editDiscount #amount").val(amount);
					$("#editDiscount #discountReason").val(discountReason);
					$("#editDiscount #consignmentNo").val(id);
					
					$("#editDiscount").modal();
				});
				
			};
			
			function editAmount(id){
				
				$(function(){
					$("#editAmount #cancel").hide();
					$("#editAmount #submitAmount").hide();
					$("#editAmount #closeBtn").hide();
					
					$("#editAmount #loading").hide();
					
					$("#editAmount #cancel").show();
					$("#editAmount #submitAmount").show();
					
					var amount = $("#amountModal_" + id).val();

					$("#editAmount #amount").val(amount);
					$("#editAmount #consignmentNo").val(id);
					
					$("#editAmount").modal();
				});
				
			};
			
			
			function changeStatus(id){
				$("#success_"+id).hide();
				$("#noReason_"+id).hide();
				$("#error_"+id).hide();
				
				var status = $('#status_'+id).val();
				var payDeadline = $('#payDeadline_'+id).html();
				var reasonPending = $('#reasonPending_'+id).val();
				var reasonText = $('#reasonText_'+id).val();
				
				if(status == 2 && payDeadline == ("Empty")) { //awaiting payment
					alert("<%=Resource.getString("ID_MSG_PLSSETDEADLINE",locale)%>");
					return false;
				}
				
				if(status == 3 && reasonPending == ("") && reasonText == ("") ) { //check reasonText
					$("#error_"+id).show();
					$("#error_"+id).html("<%=Resource.getString("ID_MSG_PLSINPUTREASON",locale)%>");
					return false;
				}
				
				var str1 = "&status="+status+"&payDeadline="+payDeadline+"&reasonPending="+reasonPending+"&reasonText="+reasonText;
				
				$("#loadingBtn_"+id).show();
				$("#goBtn_"+id).hide();
				$(function() {
					$.ajax({
						type: 'POST',
						url: './consignment',        
						data: "actionType=changeStatus&consignmentNo="+id+str1,
						success: function(response) {
							$("#loadingBtn_"+id).hide();
							$("#goBtn_"+id).show();
							if(response != ""){
								if(response == "updateSuccess"){
									dynUpdatePage(id, status);
									$("#success_"+id).show();
									$("#success_"+id).html("<%=Resource.getString("ID_MSG_UPDATESUCCESS", locale)%>");
								} else if(response == "updateNemail"){
									dynUpdatePage(id, status);
									$("#success_"+id).show();
									$("#success_"+id).html("<%=Resource.getString("ID_MSG_UPDATEDNEMAIL", locale)%>");
								} else if(response == "emailFailed"){
									$("#error_"+id).show();
									$("#error_"+id).html("<%=Resource.getString("ID_MSG_SENDEMAILFAILED", locale)%>");
								} else if(response == "noData"){
									$("#error_"+id).show();
									$("#error_"+id).html("<%=Resource.getString("ID_MSG_NODATA", locale)%>");
								} else {
									$("#error_"+id).show();
									$("#error_"+id).html("<%=Resource.getString("ID_MSG_UPDATEFAILED",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%> "+response);
								}
							}
						}
					});
				});
				
			};
		
			$(function() {
				$( "#submitBtn" ).click(function() { $( "#searchRecord" ).submit(); return false; });
			});
			
			$(function() {
				$( "#filterBtn" ).click(function() { $( "#filterForm" ).submit(); return false; });
			});
			
			$(function() {
				$( "#showPendingBtn" ).click(function() {
					window.location.href="./pending";
				});
			});
			
			$(function() {
				$( "#showAllBtn" ).click(function() {
					window.location.href="./consignment";
				});
			});
			
			$(function() {
				$( "#calendarViewBtn" ).click(function() {
					window.location.href="./cpCalendar";
				});
			});
			
			
			
			function changeStatusCheck(obj){
				var code = obj.id.substring(7, obj.id.length);
				var status = obj.value;
				var reasonPending = document.getElementById("reasonPending_"+code).value;
				
				if(status == 1 || status == 2) {
					document.getElementById("sendEmailHint_"+code).innerHTML = "<%=Resource.getString("ID_LABEL_SYSWILLSENDEMAIL",locale)%>";
					document.getElementById("remarkNoti_"+code).style.display = "none";
					document.getElementById("otherReasons_"+code).style.display = "none";
				} else if(status == 3 && reasonPending != "") {
					document.getElementById("sendEmailHint_"+code).innerHTML = "";
					document.getElementById("remarkNoti_"+code).style.display = "block";
					document.getElementById("otherReasons_"+code).style.display = "none";
				} else if(status == 3 && reasonPending == "") {
					document.getElementById("sendEmailHint_"+code).innerHTML = "";
					document.getElementById("remarkNoti_"+code).style.display = "block";
					document.getElementById("otherReasons_"+code).style.display = "block";
				} else {
					document.getElementById("sendEmailHint_"+code).innerHTML = "";
					document.getElementById("remarkNoti_"+code).style.display = "none";
					document.getElementById("otherReasons_"+code).style.display = "none";
				}
			};
			
			function changeReasonCheck(obj){
				var code = obj.id.substring(14, obj.id.length);
				var reasonPending = obj.value;
				
				if(reasonPending == ""){
					document.getElementById("otherReasons_"+code).style.display = "block";
				} else {
					document.getElementById("otherReasons_"+code).style.display = "none";					
				}
			};
			
			function dynUpdatePage(consignmentNo, status) {
				//$('#stat_'+consignmentNo).html($('#status_'+consignmentNo).find("option:selected").text());
			};
			

		</script>

		
	</body>
</html>
