<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.area.*,com.iposb.my.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%
	//String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();
	String searchConsignmentNo = request.getParameter("searchConsignmentNo") == null ? "" : request.getParameter("searchConsignmentNo").toString();
		
	boolean viewDetails = false;
	if(!searchConsignmentNo.equals("")) {
		viewDetails = true;
	}

	ArrayList data = (ArrayList)request.getAttribute(ConsignmentController.OBJECTDATA);
	ConsignmentDataModel consignmentData = new ConsignmentDataModel();
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	ArrayList <MyDataModel> creditaccountData = (ArrayList)request.getAttribute("creditaccount");
	MyDataModel creditData = new MyDataModel();
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_MANAGEMYCONSIGNMENT",locale)%> - iPosb Logistic</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />
	<style>
		.radio-toolbar input[type="radio"] {
		  opacity: 0;
		  margin: 20px;
		  width: 0;
		}
		
		.radio-toolbar label {
		    display: inline-block;
		    background-color: #f5f5f5;
		    padding: 10px 20px;
		    font-family: sans-serif, Arial;
		    font-size: 16px;
		    border: 1px solid #9a9a9a;
		    border-radius: 4px;
		    cursor: pointer;
		}
		
		.radio-toolbar input[type="radio"]:checked + label {
		    background-color: rgb(0 166 80);
		    border-color: #3c763d;
		    color: white;
		}
		

	</style>

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->
			

    <div class="inner-page padd">
			
		<div class="container">

			<div class="row">
				
				<jsp:include page="mySidebar.jsp?tab=my" />
				
				<div class="col-md-9">

					<h3><%=Resource.getString("ID_LABEL_MANAGEMYCONSIGNMENT",locale)%></h3>
					
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
					
					<%
						if(viewDetails) {
							
							if(data != null && data.size() > 0){
								consignmentData = (ConsignmentDataModel)data.get(0);
					%>
					
						<div class="text-right">
							<small><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></small><font size="5"> <%=searchConsignmentNo %></font><br />
							<small><%=Resource.getString("ID_LABEL_STATUS",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></small><font size="3"> <%=ConsignmentBusinessModel.parseStatus(consignmentData.getStatus(), locale) %></font>
							<% if(consignmentData.getStatus()==6) { %>
									<br/><small><%=Resource.getString("ID_LABEL_REASON",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%><font color="red"><%=consignmentData.getRemarkNotify() %></font></small>
							<% } %>
							<p>&nbsp;</p>
							<a href="https://static.iposb.com/pdf/<%=searchConsignmentNo %>-IPOSB.pdf" target="_blank"><input class="btn btn-orange" type="button" value="<%=Resource.getString("ID_LABEL_DOWNLOADCONSIGNMENTNOTE",locale)%>" /></a>
						</div>
						
						<p>&nbsp;</p>
						
						<form id="createForm" name="createForm" method="post" action="./consignment" class="sky-form">
						
							
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_SHIPMENTMETHOD",locale)%> </label>
									<div class="radio-toolbar text-center">
		                                <input type="radio" value="1" id="shipmentMethod" name="methodGroup" <% if(consignmentData.getShipmentMethod()==1){out.print("selected");} %>> <label for="1"><i class="fa fa-truck fa-lg"></i> <%=Resource.getString("ID_LABEL_SHIPMENTMETHOD_LAND",locale)%></label>
    									<input type="radio" value="2" id="shipmentMethod" name="methodGroup" <% if(consignmentData.getShipmentMethod()==2){out.print("selected");} %>> <label for="2"><i class="fa fa-ship fa-lg"></i> <%=Resource.getString("ID_LABEL_SHIPMENTMETHOD_SHIP",locale)%></label>
    									<input type="radio" value="3" id="shipmentMethod" name="methodGroup" <% if(consignmentData.getShipmentMethod()==3){out.print("selected");} %>> <label for="3"><i class="fa fa-plane fa-lg"></i> <%=Resource.getString("ID_LABEL_SHIPMENTMETHOD_FLIGHT",locale)%></label>
    								</div>
		                        </section>
															
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%> </label>
		                            <label for="shipmentType" class="input">
		                                <select class="form-control" id="shipmentType" name="shipmentType" readonly disabled>
											<option value="1" <% if(consignmentData.getShipmentType()==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale)%></option>
											<option value="2" <% if(consignmentData.getShipmentType()==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%></option>
											<option value="3" <% if(consignmentData.getShipmentType()==3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_CHARTERTRANSPORT",locale)%></option>
										</select>
		                            </label>
		                        </section>
							
								<section id="quantitySection">
									<label class="label"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%> </label>
		                            <label for="quantity" class="input">
		                                <input class="form-control" type="text" id="quantity" name="quantity" value="<%=consignmentData.getQuantity() %>" maxlength="6" readonly disabled>
		                            </label>
		                        </section>
							
								<section id="weightSection">
									<label class="label"><span id="weightSpan"><%=Resource.getString("ID_LABEL_WEIGHT",locale)%></span> (KG)</label>
		                            <label for="weight" class="input">
		                                <input class="form-control" type="text" id="weight" name="weight" value="<%=consignmentData.getWeight() %>" maxlength="10" readonly disabled>
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_PAYMETHOD",locale)%> </label>
		                            <label for="payMethod" class="input">
		                                <select class="form-control" id="payMethod" name="payMethod" readonly disabled>
		                                	<option value="0" <% if(consignmentData.getPayMethod()==0){out.print("selected");} %>><%=Resource.getString("ID_LABEL_UNKNOWN",locale)%></option>
		                                	<option value="1" <% if(consignmentData.getPayMethod()==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCASH",locale)%></option>
											<option value="2" <% if(consignmentData.getPayMethod()==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYONLINE",locale)%></option>
											<option value="3" <% if(consignmentData.getPayMethod()==3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYBANKIN",locale)%></option>
											<option value="4" <% if(consignmentData.getPayMethod()==4){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale)%></option>
											<option value="5" <% if(consignmentData.getPayMethod()==5){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCOD",locale)%></option>
										</select>
		                            </label>
		                        </section>
		                        
		                        <% if(priv==2){ //credit term user %>
									<section>
										<label class="label"><%=Resource.getString("ID_LABEL_CREDITACCOUNT",locale)%></label>
			                            <label for="creditArea" class="input">
			                                <select class="form-control" id="creditArea" name="creditArea" readonly disabled>
				                                <% 
					                                if(creditaccountData != null && !creditaccountData.isEmpty()){
														for(int p = 0; p < creditaccountData.size(); p ++){
															creditData = creditaccountData.get(p);
				                                %>
													<option value="<%=creditData.getAid() %>" <% if(consignmentData.getCreditArea()==p){out.print("selected");} %>><%=creditData.getContactName() %></option>
												<% } } %>
											</select>
			                            </label>
			                        </section>
		                        <% } %>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_ITEMREMARK",locale)%> </label>
		                            <label class="checkbox"><input name="tick1" id="tick1" value="tick1" type="checkbox" readonly disabled><i></i><%=Resource.getString("ID_LABEL_PERISHABLE",locale)%></label>
		                            <label class="checkbox"><input name="tick2" id="tick2" value="tick2" type="checkbox" readonly disabled><i></i><%=Resource.getString("ID_LABEL_FOOD",locale)%></label>
		                            <label class="checkbox"><input name="tick3" id="tick3" value="tick3" type="checkbox" readonly disabled><i></i><%=Resource.getString("ID_LABEL_FRAGILE",locale)%></label>
		                            <label class="checkbox"><input name="tick4" id="tick4" value="tick4" type="checkbox" readonly disabled><i></i><%=Resource.getString("ID_LABEL_EQUIPMENT",locale)%></label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_SPECIALHELP",locale)%> </label>
		                            <label for="helps" class="input">
		                                <input type="text" id="helps" name="helps" value="<%=consignmentData.getHelps() %>" maxlength="50" readonly disabled>
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_DELIVERYDATE",locale)%> </label>
		                            <label for="deliveryDT" class="input">
		                                <input type="text" id="deliveryDT" name="deliveryDT" value="<%=consignmentData.getDeliveryDT() %>" maxlength="20" readonly disabled>
		                            </label>
		                        </section>
		                        
		                        <p>&nbsp;</p>
		                        
		                        <hr>
		                        
		                        <div class="row">
						
									<div class="col-md-6">

										<section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%> </label>
				                            <label for="senderName" class="input">
				                                <input type="text" id="senderName" name="senderName" value="<%=consignmentData.getSenderName() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERIC",locale)%> </label>
				                            <label for="senderIC" class="input">
				                                <input type="text" id="senderIC" name="senderIC" value="<%=consignmentData.getSenderIC() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERADDRESS",locale)%> </label>
				                            <label for="senderAddress1" class="input">
				                                <input type="text" id="senderAddress1" name="senderAddress1" value="<%=consignmentData.getSenderAddress1() %>" readonly disabled>
				                            </label>
				                            <label for="senderAddress2" class="input">
				                                <input type="text" id="senderAddress2" name="senderAddress2" value="<%=consignmentData.getSenderAddress2() %>" readonly disabled>
				                            </label>
				                            <label for="senderAddress3" class="input">
				                                <input type="text" id="senderAddress3" name="senderAddress3" value="<%=consignmentData.getSenderAddress3() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERPOSTCODE",locale)%> </label>
				                            <label for="senderPostcode" class="input">
				                                <input type="text" id="senderPostcode" name="senderPostcode" maxlength="10" value="<%=consignmentData.getSenderPostcode() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERAREA",locale)%> </label>
				                            <label for="senderArea" class="input">
				                                <select class="form-control" id="senderArea" name="senderArea" readonly disabled>
					                                <% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(consignmentData.getSenderArea()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_COLLECTPOINT",locale)%> </label>
				                            <label for="senderZone" class="input">
				                                <input type="hidden" id="senderZoneTmp" name="senderZoneTmp">
				                                <select class="form-control" id="senderZone" name="senderZone"></select>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERPHONE",locale)%> </label>
				                            <label for="senderPhone" class="input">
				                                <input type="text" id="senderPhone" name="senderPhone" value="<%=consignmentData.getSenderPhone() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
									</div>
									
									<div class="col-md-6">

				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERNAME",locale)%> </label>
				                            <label for="receiverName" class="input">
				                                <input type="text" id="receiverName" name="receiverName" value="<%=consignmentData.getReceiverName() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERATTN",locale)%> </label>
				                            <label for="receiverAttn" class="input">
				                                <input type="text" id="receiverAttn" name="receiverAttn" value="<%=consignmentData.getReceiverAttn() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERADDRESS",locale)%> </label>
				                            <label for="receiverAddress1" class="input">
				                                <input type="text" id="receiverAddress1" name="receiverAddress1" value="<%=consignmentData.getReceiverAddress1() %>" readonly disabled>
				                            </label>
				                            <label for="receiverAddress2" class="input">
				                                <input type="text" id="receiverAddress2" name="receiverAddress2" value="<%=consignmentData.getReceiverAddress2() %>" readonly disabled>
				                            </label>
				                            <label for="receiverAddress3" class="input">
				                                <input type="text" id="receiverAddress3" name="receiverAddress3" value="<%=consignmentData.getReceiverAddress3() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERPOSTCODE",locale)%> </label>
				                            <label for="receiverPostcode" class="input">
				                                <input type="text" id="receiverPostcode" name="receiverPostcode" maxlength="10" value="<%=consignmentData.getReceiverPostcode() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERAREA",locale)%> </label>
				                            <label for="receiverArea" class="input">
				                                <select class="form-control" id="receiverArea" name="receiverArea" readonly disabled>
					                                <% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
					                                %>
														<option value="<%=aData.getAid() %>" <% if(consignmentData.getReceiverArea()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_DROPOFFPOINT",locale)%> </label>
				                            <label for="receiverZone" class="input">
				                                <input type="hidden" id="receiverZoneTmp" name="receiverZoneTmp">
				                                <select class="form-control" id="receiverZone" name="receiverZone"></select>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERPHONE",locale)%> </label>
				                            <label for="receiverPhone" class="input">
				                                <input type="text" id="receiverPhone" name="receiverPhone" value="<%=consignmentData.getReceiverPhone() %>" readonly disabled>
				                            </label>
				                        </section>
				                        
									</div>
									
								</div>
	
		                    </fieldset>
	                    
	                    </form>
	                    
	                    <p>&nbsp;</p>
	                    
	                    <h3><small><%=Resource.getString("ID_LABEL_FINALWEIGHT",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></small><%=consignmentData.getWeight() %> KG</h3>
	                    <h3><small><%=Resource.getString("ID_LABEL_FINALPRICE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></small>RM <%=consignmentData.getAmount() %></h3>
	                    
	                    <div class="text-center">
							<input class="btn btn-primary" type="button" value="<%=Resource.getString("ID_LABEL_BACK",locale)%>" onclick="history.go(-1);" />
						</div>
	                    
					<% } } else { %>
					
						<div class="row">
							<div class="col-md-6 col-sm-6">							
							</div>
							<div class="col-md-6 col-sm-6">
								<div class="pull-right"><a href="./create"><button id="createConsignment" class="btn btn-primary" type="button"><%=Resource.getString("ID_LABEL_CREATECONSIGNMENT",locale)%></button></a></div>
							</div>
						</div>
						
						
						<div class="single-item single-item-content">
						
							<div class="item-details">
								<ul class="list-unstyled">
								
									<% 
										if(data != null && data.size() > 0){
											for(int i = 0; i < data.size(); i++){
												consignmentData = (ConsignmentDataModel)data.get(i);
									%>
									
										<li>
											
												<div>
												    <div style="float: left; width: 150px;">
												    	<font size="3"><a href="./myConsignment-<%=consignmentData.getConsignmentNo() %>"><%=consignmentData.getConsignmentNo() %></a></font>
												    	<% 
												    		String statusTXT = ConsignmentBusinessModel.parseStatus(consignmentData.getStatus(), locale);
												    	%>
												    	<p><%=statusTXT %></p>
												    </div>
												    <div style="float: left;">
														<font size="3"><%=consignmentData.getSenderAreaname() %> &nbsp; <i class="fa fa-caret-right blue"></i> <%=consignmentData.getReceiverAreaname() %></font>
														<p><i class="fa fa-calendar-o blue"></i><%=consignmentData.getCreateDT() %> &nbsp; <i class="fa fa-user blue"></i><%=consignmentData.getReceiverName() %></p>
													</div>
													<div style="float: right;">
														<a href="./myConsignment-<%=consignmentData.getConsignmentNo() %>"><button id="edit" class="btn btn-orange" type="button"><%=Resource.getString("ID_LABEL_VIEW",locale)%></button></a>
														<a href="https://static.iposb.com/pdf/<%=consignmentData.getConsignmentNo() %>-IPOSB.pdf" target="_blank"><button class="btn btn-" type="button" title="<%=Resource.getString("ID_LABEL_CONSIGNMENTNOTE",locale)%>"><i class="fa fa-file-pdf-o fa-lg red"></i></button></a>
													</div>
												</div>
												
											<div class="clearfix"></div>
										</li>
										
									<% } } else { %>
									
										<li><%=Resource.getString("ID_LABEL_NOCONSIGNMENTFOUND",locale)%><div class="clearfix"></div></li>
										
									<% } %>
	
								</ul>
							</div>
						
						</div>
						
						
						<%
							//分頁
							int numPerPage = 10; //每頁顯示數量
							String pnoStr = request.getParameter("pageNo");
							Integer pno = pnoStr == null ? 0 : Integer.parseInt(pnoStr);
							pager.setPageNo(pno.intValue());
							pager.setPerLen(numPerPage);
							int total = 0;
							int thisPage = 0;
							String p = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
							
							//防止user亂輸入頁碼
							boolean isNum = MyController.isNumeric(p);
							
							if(isNum){
								thisPage = Integer.parseInt(p);
							}
							if( !(thisPage > 0) || !(isNum) ){
								thisPage = 1;
							}
							
						
							total = consignmentData.getTotal();
							pager.setData(total);//設置數據
						
							int []range = pager.getRange(numPerPage);//獲得頁碼呈現的區間
							if(pager.getMaxPage()>1) {//要呈現的頁面超過1頁，需要分頁呈現
								out.print("<div class=\"text-center\"><ul class=\"pagination\">");
								if(thisPage > 1){
									out.print("<li><a href='my-page-"+(thisPage-1)+"'>&laquo;</a></li>");
								}
								for(int i=0; i<range.length; i++){
									if(thisPage==(i+1)){
										out.print("<li class=\"active\"><a>"+(i+1)+"</a></li>"); //當頁則不顯示連結
									} else {
										out.print("<li><a href='my-page-"+(i+1)+"'>"+(i+1)+"</a></li>");
									}
								}
								if(thisPage < pager.getMaxPage()){
									out.print("<li><a href='my-page-"+(thisPage+1)+"'>&raquo;</a></li>");
								}
								out.print("</ul></div>");
						   }
						%>
					
					<% } %>

				</div>
				
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript" src="./assets/js/profilepic.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			if("<%=result %>" == "createSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_CREATESUCCESS",locale)%>");
			} else if("<%=result %>" == "createFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_NOCONNECTION",locale)%>");
			}
			
			<%
				String tickvalue = consignmentData.getTickItem(); 
				if(!tickvalue.equals("") && tickvalue != null){
					String tickSplit[] = tickvalue.split("\\,");
					for(int i = 0; i < tickSplit.length; i++){
						String tmp = tickSplit[i];
						out.print("$(\"#"+tmp+"\").prop('checked', true);");
					}
				}
			%>
			
			$("#senderZoneTmp").val(<%=consignmentData.getSenderZone() %>);
			$("#receiverZoneTmp").val(<%=consignmentData.getReceiverZone() %>);
			
			loadZone("sender");
			loadZone("receiver");
			
			changeShipmentType();
		});
		
		function changeShipmentType() {
			var shipmentType = $("#shipmentType").val();
			if(shipmentType == 1) { //document
				$("#weightSpan").html("<%=Resource.getString("ID_LABEL_WEIGHT",locale)%>");
			} else if (shipmentType == 2) { //parcel
				$("#weightSpan").html("<%=Resource.getString("ID_LABEL_TOTALWEIGHT",locale)%>");
			}
		}
		
		$(function() {
			$( "#loginBtn" ).click(function() { document.loginPageForm.submit(); return false; });
		});
		
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
						$( "#senderZone" ).val($( "#senderZoneTmp" ).val());
						
					} else if(obj=="receiver") {

						if(response != ""){
							$( "#receiverZone" ).append(response);
						}
						$( "#receiverZone" ).val($( "#receiverZoneTmp" ).val());
						
					}
					
				} 
			});
			
		};
		
	</script>
</g:compress>	
	
</body>

</html>