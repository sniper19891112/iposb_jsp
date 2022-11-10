<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,com.iposb.area.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel userData = new LogonDataModel();
	if(data != null && data.size() > 0){
		userData = (LogonDataModel)data.get(0);
	}

	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_PRICING",locale)%> - iPosb Logistic</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->
    
    <div class="banner padd">
		<div class="container">
			<!-- Image -->
			<img class="img-responsive" src="assets/img/crown-white.png" alt="" />
			<!-- Heading -->
			<h2 class="white"><%=Resource.getString("ID_LABEL_PRICING",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_PRICING",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
			

    <div class="inner-page padd">
			
		<!-- Contact Us Start -->
		
		<div class="contactus">
			<div class="container">
				<div class="row">
				
					<div class="col-md-6">
						<h2><%=Resource.getString("ID_LABEL_PRICECALCULATION",locale)%></h2>
						<small><%=Resource.getString("ID_LABEL_PRICECALCULATIONTXT",locale)%></small>
						<hr>
						
						<form class="form-horizontal col-md-10" role="form">
						
							<div class="form-group">
								<label class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_SENDERAREA",locale)%></label>
								<div class="col-md-8">
									<select class="form-control" id="senderArea" name="senderArea">
										<option value="0000000000000000"></option>
		                                <% 
			                                if(areaData != null && !areaData.isEmpty()){
												for(int i = 0; i < areaData.size(); i ++){
													aData = areaData.get(i);
													
													String aid = String.format("%06d", aData.getAid());//補足6碼
													if(aData.getIsMajor()==1){
														aid += "A000000";
													}else{
														aid += "B" + String.format("%06d", aData.getBelongArea());//補足6碼
													}
													aid += aData.getState();//州屬
		                                %>
											<option value="<%=aid %>"><%=aData.getName_enUS() %></option>
										<% } } %>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_RECEIVERAREA",locale)%></label>
								<div class="col-md-8">
									<select class="form-control" id="receiverArea" name="receiverArea">
										<option value="0"></option>
		                                <% 
			                                if(areaData != null && !areaData.isEmpty()){
												for(int i = 0; i < areaData.size(); i ++){
													aData = areaData.get(i);
													
													String aid = String.format("%06d", aData.getAid());//補足6碼
													if(aData.getIsMajor()==1){
														aid += "A000000";
													}else{
														aid += "B" + String.format("%06d", aData.getBelongArea());//補足6碼
													}
													aid += aData.getState();//州屬
		                                %>
											<option value="<%=aid %>"><%=aData.getName_enUS() %></option>
										<% } } %>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%></label>
								<div class="col-md-8">
									<select class="form-control" id="shipmentType" onChange="changeShipmentType()">
										<option value="1"><%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale)%></option>
										<option value="2"><%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%></option>
									</select>
								</div>
							</div>
							        
							<div id="quantityDiv" class="form-group" style="display:none">
								<label class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%> (PCS)</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="quantity" name="quantity" placeholder="" onKeyPress="return validatenumber(event);">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label"><span id="weightSpan"><%=Resource.getString("ID_LABEL_TOTALWEIGHT",locale)%></span> (KG)</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="weight" name="weight" placeholder="" onKeyPress="return validatenumber(event);">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_MEMBERTYPE",locale)%></label>
								<div class="col-md-8">
									<select class="form-control" id="memberType">
										<option value="normal"><%=Resource.getString("ID_LABEL_NORMALUSER",locale)%></option>
										<option value="credit"><%=Resource.getString("ID_LABEL_CREDITTERMUSER",locale)%></option>
									</select>
								</div>
							</div>
							   
							<div class="form-group">
								<div class="col-md-8 control-label pull-right">
									<button type="reset" class="btn btn-default btn-sm"><%=Resource.getString("ID_LABEL_RESET",locale)%></button>
									<button type="button" class="btn btn-orange btn-sm" id="calPrice"><%=Resource.getString("ID_LABEL_CALCULATE",locale)%></button>&nbsp;
								</div>
							</div>
							
							<div class="form-group">
								<div id="errorAlert1" class="alert alert-danger fade in alert-dismissable" style="display: none">
									<i class="fa fa-warning"></i> <span id="errorMsg1"></span>
								</div>
								
								<div id="successAlert1" class="alert alert-success fade in alert-dismissable" style="display: none">
									<span id="successMsg1"><%=Resource.getString("ID_LABEL_TOTALAMOUNT",locale)%> <span id="calResult"></span></span>
								</div>
							</div>
							
						</form>
						
					</div>
					
					<div class="col-md-6">
						<h2><%=Resource.getString("ID_LABEL_DIMCALCULATION",locale)%></h2>
						<small>&nbsp;</small>
						<hr>
						
						<form class="form-horizontal col-md-10" role="form">
						
							<div class="form-group">
								<label for="inputCountry" class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%></label>
								<div class="col-md-8">
									<select class="form-control" id="shipmentType">
										<option value="1"><%=Resource.getString("ID_LABEL_DOMESTICSHIPMENT",locale)%></option>
										<option value="2"><%=Resource.getString("ID_LABEL_INTERNATIONALSHIPMENT",locale)%></option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="inputName" class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_LENGTH",locale)%> (cm)</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="length" name="length" placeholder="" onKeyPress="return validatenumber(event);">
								</div>
							</div>            
							<div class="form-group">
								<label for="inputEmail1" class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_WIDTH",locale)%> (cm)</label>
								<div class="col-md-8">
									<input type="email" class="form-control" id="width" name="width" placeholder="" onKeyPress="return validatenumber(event);">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPhone" class="col-md-4 control-label"><%=Resource.getString("ID_LABEL_HEIGHT",locale)%> (cm)</label>
								<div class="col-md-8">
									<input type="text" class="form-control" id="height" name="height" placeholder="" onKeyPress="return validatenumber(event);">
								</div>
							</div>
							   
							<div class="form-group">
								<div class="col-md-8 control-label pull-right">
									<button type="reset" class="btn btn-default btn-sm"><%=Resource.getString("ID_LABEL_RESET",locale)%></button>
									<button type="button" class="btn btn-orange btn-sm" id="calVolumetric"><%=Resource.getString("ID_LABEL_CALCULATE",locale)%></button>&nbsp;
								</div>
							</div>
							
							<div class="form-group">
								<div id="errorAlert2" class="alert alert-danger fade in alert-dismissable" style="display: none">
									<i class="fa fa-warning"></i> <span id="errorMsg2"></span>
								</div>
								
								<div id="successAlert2" class="alert alert-success fade in alert-dismissable" style="display: none">
									<span id="successMsg2"><%=Resource.getString("ID_LABEL_DIMWEIGHTIS",locale)%> <font size="3"><b><span id="dimResult">0</span> KG</b></font></span>
								</div>
							</div>
							
						</form>
						
					</div>
					
					
				</div>
			</div>
		</div>
		
		<!-- Contact Us End -->
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			changeShipmentType();
		});
		
		
		$("#calPrice").click(function(){
			
			$("#errorAlert1").hide();
			$("#errorMsg1").html("");
			$("#successAlert1").hide();
			$("#calResult").html("");
			
			var senderArea = $("#senderArea").val();
			var receiverArea = $("#receiverArea").val();
			var shipmentType = $("#shipmentType").val();
			//var freightType = $("#freightType").val();
			var quantity = $("#quantity").val();
			var weight = $("#weight").val();
			var memberType = $("#memberType").val();
			
			if(quantity.length == 0) {
				quantity = "1";
				$("#quantity").val(quantity);
			}
			
			if(weight.length == 0) {
				weight = "0";
			}
			

			if(senderArea == 0) {
				$("#errorMsg1").html("Please choose sender area");
				$("#senderArea").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			if(receiverArea == 0) {
				$("#errorMsg1").html("Please choose receiver area");
				$("#receiverArea").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			/*
			if(freightType == 0) {
				$("#errorMsg1").html("Please choose freight type");
				$("#freightType").focus();
				$("#errorAlert1").show();
				return false;
			}
			*/
			
			if(eval(quantity) <= 0) {
				$("#errorMsg1").html("Please input quantity (PCS)");
				$("#quantity").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			if(eval(weight) <= 0) {
				$("#errorMsg1").html("Please input total weight (KG)");
				$("#weight").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			$("#calPrice").html("<i class='fa fa-circle-o-notch fa-spin'></i> Calculating...");
			
			$.ajax({
				type: 'POST',
				url: './pricing',
				data:{    
					actionType:"calculate",
					senderArea: senderArea,
					receiverArea: receiverArea,
					shipmentType: shipmentType,
					quantity: quantity,
					weight: weight,
					memberType: memberType
		   		},  
		   		
				success: function(response) {
					if(response != ""){
						
						var priceValue = response.split("|");
						var txt1 = "<font size=\"3\"><b>RM " + parseFloat(priceValue[0]).toFixed(2) + "</b></font>";
						var txt2 = "";
						if(priceValue[1] != priceValue[0]) {
							txt2 = "<span style=\"margin-left: 20px\"><font color=\"red\">Save <b>RM " + parseFloat(priceValue[1]-priceValue[0]).toFixed(2) + "</b></font></span>";	
						}
						
						$("#successAlert1").show();
						$("#calResult").html(txt1+txt2);
						$("#calPrice").text("<%=Resource.getString("ID_LABEL_CALCULATE",locale)%>");
					}
				} 
			});
			
		});
		
		
		$("#calVolumetric").click(function(){
			
			$("#errorAlert2").hide();
			$("#successAlert2").hide();
			$("#dimResult").html("");
			
			var shipType = $("#shipmentType").val();
			
			var volM = 0;
			var L = $("#length").val();
			var W = $("#width").val();
			var H = $("#height").val();
			
			if(L.length == 0) {
				L = "0";
			}
			if(W.length == 0) {
				W = "0";
			}
			if(H.length == 0) {
				H = "0";
			}
			
			if(eval(L) <= 0) {
				$("#errorMsg2").html("Please input length (cm)");
				$("#length").focus();
				$("#errorAlert2").show();
				return false;
			}
			if(eval(W) <= 0) {
				$("#errorMsg2").html("Please input width (cm)");
				$("#width").focus();
				$("#errorAlert2").show();
				return false;
			}
			if(eval(H) <= 0) {
				$("#errorMsg2").html("Please input height (cm)");
				$("#height").focus();
				$("#errorAlert2").show();
				return false;
			}
			
			if(shipType == 1) {
				volM = (L * W * H) / 6000;	
			} else {
				volM = (L * W * H) / 5000;	
			}
			
			$("#errorAlert2").hide();
			$("#successAlert2").show();
			$("#dimResult").html(Math.round(volM * 100) / 100);
		});
		
		function changeShipmentType() {
			var shipmentType = $("#shipmentType").val();
			if(shipmentType == 1) { //document
				$("#quantityDiv").hide();
				$("#quantity").val("");
				$("#weightSpan").html("<%=Resource.getString("ID_LABEL_WEIGHT",locale)%>");
			} else if (shipmentType == 2) { //parcel
				$("#quantityDiv").show();
				$("#weightSpan").html("<%=Resource.getString("ID_LABEL_TOTALWEIGHT",locale)%>");
			}
		}
		
		function validatenumber(event) {
			var key = window.event ? event.keyCode : event.which;

			if (key == 8 || key == 9 || key == 46) {
			    return true;
			}
			else if ( key < 48 || key > 57 ) {
			    return false;
			}
			else 
				return true;
		}

	</script>
</g:compress>	
	
</body>

</html>