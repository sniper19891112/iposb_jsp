<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.area.*,com.iposb.partner.*,java.util.HashMap,java.util.Iterator,java.util.ArrayList,java.io.*,java.text.*"%>
<%@include file="../include.jsp" %>

<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%	
	if(priv!=3 && priv!=4){ //neither agent nor parther
		String url = "./partnerlogin";
		response.sendRedirect(url);
	    return;
	}

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
	
	String pagelink = "partnerCpConsignment";
	if(conType.equals("pending")) {
		pagelink = "pendingConsignment";
	}

	NumberFormat formatter = new DecimalFormat("#,###,##0.00");
	
	ArrayList <PartnerDataModel> partnerData = (ArrayList)request.getAttribute("partner");
	PartnerDataModel pData = new PartnerDataModel();
	
	String all_amount = "";
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
	String all_payStatus = "";
	String all_payDT = "";
	String all_pickupDriver = "";
	String all_pickupDT = "";
	String all_deliveryDriver = "";
	String all_deliveryDT = "";
	String all_terms = "";
	String all_abbreviation = "";
	String all_generalCargoNo = "";
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
							<li>
								<a href="./partnerCP"><%=Resource.getString("ID_LABEL_DASHBOARD",locale)%></a>
							</li>
							<li class="active">
								<%
									if (actionType.equals("searchPartnerConsignment")) {
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
													<form id="searchRecord" name="searchRecord" method="get" action="./partner">
														<input type="hidden" id="actionType" name="actionType" value="searchPartnerConsignment">
														<input type="text" class="text-uppercase" id="consignmentNo" name="consignmentNo" value="<%=searchCode %>" maxlength="50" style="width:138px">
														<button class="btn btn-sm btn-inverse" id="submitBtn"><%=Resource.getString("ID_LABEL_SEARCH",locale)%></button>
													</form>
												</span>
												<div class="infobox-content"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></div>
											</div>
										</div>

										
										<div class="infobox infobox-orange" style="width:300px">
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
										int status = 0;
										int payMethod = 0;
										int payStatus = -1;
										int cid = 0;
										int thisPartnerId = 0;
										boolean proofOnDelivery = false;
										
										if(data != null && data.size() > 0){
											for(int i = 0; i < data.size(); i++){
												consignmentData = (ConsignmentDataModel)data.get(i);
												consignmentNo = consignmentData.getConsignmentNo();
												generalCargoNo = consignmentData.getGeneralCargoNo() == null ? "" : consignmentData.getGeneralCargoNo();
												
												status = consignmentData.getStatus();
												payMethod = consignmentData.getPayMethod();
												payStatus = consignmentData.getPayStatus();
												statusTXT = ConsignmentBusinessModel.parseStatus(status, locale);
												cid = consignmentData.getCid();
												thisPartnerId = consignmentData.getPartnerId();
												
												all_amount += "#amount_"+consignmentNo+",";
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
												all_payStatus += "#payStatus_"+consignmentNo+",";
												all_payDT += "#payDT_"+consignmentNo+",";
												all_pickupDriver += "#pickupDriver_"+consignmentNo+",";
												all_pickupDT += "#pickupDT_"+consignmentNo+",";
												all_deliveryDriver += "#deliveryDriver_"+consignmentNo+",";
												all_deliveryDT += "#deliveryDT_"+consignmentNo+",";
												all_terms += "#terms_"+consignmentNo+",";
												all_abbreviation += "#partnerId_"+consignmentNo+",";
												all_generalCargoNo += "#generalCargoNo_"+consignmentNo+",";
												
												if(i==data.size()-1) { //last
													all_amount = all_amount.substring(0, all_amount.length()-1);
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
													all_payStatus = all_payStatus.substring(0, all_payStatus.length()-1);
													all_payDT = all_payDT.substring(0, all_payDT.length()-1);
													all_pickupDriver = all_pickupDriver.substring(0, all_pickupDriver.length()-1);
													all_pickupDT = all_pickupDT.substring(0, all_pickupDT.length()-1);
													all_deliveryDriver = all_deliveryDriver.substring(0, all_deliveryDriver.length()-1);
													all_deliveryDT = all_deliveryDT.substring(0, all_deliveryDT.length()-1);
													all_terms = all_terms.substring(0, all_terms.length()-1);
													all_abbreviation = all_abbreviation.substring(0, all_abbreviation.length()-1);
													all_generalCargoNo = all_generalCargoNo.substring(0, all_generalCargoNo.length()-1);
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
										<div class="col-sm-12 widget-container-span" style="padding-bottom:38px;">
											<div class="widget-box">
												<div class="widget-header <%=fontclass %>">
													
													<i class="fa fa-square"></i> <span style="margin-top:8px"><%=consignmentNo %><% if(generalCargoNo.trim().length() > 0) {out.print(" <small>("+generalCargoNo+")</small>");} %></span>

													<div class="widget-toolbar no-border">
														<ul class="nav nav-tabs" id="myTab">
															<li class="active">
																<a data-toggle="tab" href="#basic_<%=consignmentNo %>">
																	<i class="blue fa fa-info-circle"></i>
																	<%=Resource.getString("ID_LABEL_BASICINFO",locale)%>
																</a>
															</li>

															<li>
																<a data-toggle="tab" href="#detail_<%=consignmentNo %>" onclick="checkPics('<%=consignmentNo %>');">
																	<i class="blue fa fa-navicon"></i>
																	<%=Resource.getString("ID_LABEL_DETAILS",locale)%>
																</a>
															</li>

														</ul>
													</div>
												</div>
												<div class="widget-body">
													<div>
														<div class="tab-content">
															<div id="basic_<%=consignmentNo %>" class="tab-pane in active">
																<table class="table table-striped table-bordered" style="margin:0px">
																	<thead>
																		<tr>
																			<th class="center col-sm-1"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%></th>
																			<th class="center"><%=Resource.getString("ID_LABEL_FROMTO",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_SENDER",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_RECEIVER",locale)%></th>
																			<th class="center col-sm-2"><%=Resource.getString("ID_LABEL_STAGE",locale)%></th>
																		</tr>
																	</thead>

																	<tbody>
																		<tr>
																			<td class="center" <%=bgColor %>>
																				<%=dt %>
																			</td>																			
																			<td class="center" <%=bgColor %>>
																				<%=consignmentData.getConsignmentNo() %>
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
																						proofOnDelivery = true;
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
																						}
																						  
																						out.print(Resource.getString("ID_LABEL_STAGE9",locale) + " <i class=\"fa fa-exclamation-triangle red\" title=\""+reason+"\" rel=\"tooltip\"></i> "); //Pending Shipment
																					}
																				%>
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
																					<%=consignmentData.getSenderName().equals("") ? "&nbsp;" : consignmentData.getSenderName() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERIC",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getSenderIC().equals("") ? "&nbsp;" : consignmentData.getSenderIC() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERADDRESS",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getSenderAddress1() %><br/>
																					<%=consignmentData.getSenderAddress2() %><br/>
																					<%=consignmentData.getSenderAddress3() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERPOSTCODE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getSenderPostcode().equals("") ? "&nbsp;" : consignmentData.getSenderPostcode() %>
																				</div>
																			</div>																			
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERAREA",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getSenderArea() ==0 ? "&nbsp;" : AreaBusinessModel.parseAreaName(consignmentData.getSenderArea()) %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_ZONE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getSenderZonename().equals("") ? "&nbsp;" : consignmentData.getSenderZonename() %>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_SENDERPHONE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getSenderPhone().equals("") ? "&nbsp;" : consignmentData.getSenderPhone() %>
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
																					<%=consignmentData.getReceiverName().equals("") ? "&nbsp;" : consignmentData.getReceiverName() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERATTN",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiverAttn().equals("") ? "&nbsp;" : consignmentData.getReceiverAttn() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERADDRESS",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiverAddress1() %><br/>
																					<%=consignmentData.getReceiverAddress2() %><br/>
																					<%=consignmentData.getReceiverAddress3() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERPOSTCODE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiverPostcode().equals("") ? "&nbsp;" : consignmentData.getReceiverPostcode() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERAREA",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiverArea() == 0 ? "&nbsp;" : AreaBusinessModel.parseAreaName(consignmentData.getReceiverArea()) %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_ZONE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiverZonename().equals("") ? "&nbsp;" : consignmentData.getReceiverZonename() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIVERPHONE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiverPhone().equals("") ? "&nbsp;" : consignmentData.getReceiverPhone() %>
																				</div>
																			</div>
																			
																			
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
																					<%
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
																					<%=consignmentData.getHelps().trim().length() > 0 ? consignmentData.getHelps() : "&nbsp;" %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_CREATEDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getCreateDT() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PAYMETHOD",locale)%> </div>

																				<div class="profile-info-value">
																					<%=ConsignmentBusinessModel.parsePayMethod(payMethod, locale) %>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PAYSTATUS",locale)%> </div>

																				<div class="profile-info-value">
																					<%=ConsignmentBusinessModel.parsePayStatus(payStatus, locale) %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PAYDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getPayDT().equals("") ? "&nbsp;" : consignmentData.getPayDT() %>
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
																					<%=consignmentData.getPickupDriver().equals("") ? "&nbsp;" : consignmentData.getPickupDriver() %>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_PICKUPDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getPickupDT().equals("") ? "&nbsp;" : consignmentData.getPickupDT() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_DELIVERYDRIVER",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getDeliveryDriver().equals("") ? "&nbsp;" : consignmentData.getDeliveryDriver() %>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_DELIVERYDATE",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getDeliveryDT().equals("") ? "&nbsp;" : consignmentData.getDeliveryDT() %>
																				</div>
																			</div>																			
																			
																		</div>
																	</div>
																	
																	<!-- Fifth Column  -->
																	<div class="col-xs-12 col-sm-4" style="margin-top:30px">
																		<div class="profile-user-info profile-user-info-striped">
																		
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_AMOUNT",locale)%> (RM) </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getAmount() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_QUANTITY",locale)%> (PCS) </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getQuantity() %>
																				</div>
																			</div>

																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_WEIGHT",locale)%> (KG) </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getWeight() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_LENGTH",locale)%> (M) </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getLength() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_WIDTH",locale)%> (M) </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getWidth() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_HEIGHT",locale)%> (M) </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getHeight() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_FLIGHTNUM",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getFlightNum().equals("") ? "&nbsp;" : consignmentData.getFlightNum() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_AIRWAYBILLNUM",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getAirwaybillNum().equals("") ? "&nbsp;" : consignmentData.getAirwaybillNum() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_NATUREOFGOOD",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getNatureGood().equals("") ? "&nbsp;" : consignmentData.getNatureGood() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_RECEIPTNUM",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getReceiptNum().equals("") ? "&nbsp;" : consignmentData.getReceiptNum() %>
																				</div>
																			</div>
																			
																		</div>
																	</div>
																	
																	<!-- Sixth Column  -->
																	<div class="col-xs-12 col-sm-4" style="margin-top:30px">
																		<div class="profile-user-info profile-user-info-striped">
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_GENERALCARGOCNNUMBER",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getGeneralCargoNo().equals("") ? "&nbsp;" : consignmentData.getGeneralCargoNo() %>
																				</div>
																			</div>
																			
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_TERMS",locale)%> </div>

																				<div class="profile-info-value">
																					<%=consignmentData.getTerms().equals("") ? "&nbsp;" : consignmentData.getTerms() %>
																				</div>
																			</div>
																			
																			<% if(proofOnDelivery == true){ %>
																			<div class="profile-info-row">
																				<div class="profile-info-name"> <%=Resource.getString("ID_LABEL_POD",locale)%> </div>

																				<div class="profile-info-value" id="conPics_<%=consignmentNo %>"><i class="fa fa-spinner fa-pulse"></i></div>
																			</div>
																			<% } %>

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
									
									<% if(actionType.equals("fastSearch")) { %>
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
													out.print("<li><a href=\"./"+pagelink+"?page="+prev10+"\" rel=\"tooltip\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁													
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./"+pagelink+"?page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./"+pagelink+"?page="+(next10)+"\" rel=\"tooltip\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
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
		
		<%@include file="cpFooter.jsp" %>
		
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
				
			});

			$(function() {
				$( "#showPendingBtn" ).click(function() {
					window.location.href="./pendingConsignment";
				});
			});
			
			$(function() {
				$( "#showAllBtn" ).click(function() {
					window.location.href="./partnerCpConsignment";
				});
			});
			

			function checkPics(cn){
				$.ajax({
					url: './resource',        
					data: 'actionType=getConPics&consignmentNo='+cn,
					dataType: 'json',
					success: function(response) {
					
						if (response.stage7 === undefined) {
							$('#conPics_'+cn).html("&nbsp;");
						} else {
							$('#conPics_'+cn).html(response.stage7 +"&nbsp;");
						}
						
					}
				});
			}
						

		</script>

		
	</body>
</html>
