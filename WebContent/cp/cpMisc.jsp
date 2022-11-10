<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.logon.*,java.io.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<%
	if(priv < 3){
        return;
	}
	
	double totalPayment = 0;
	String paymentNM = "";
	
	ArrayList data = (ArrayList)request.getAttribute(ConsignmentController.OBJECTDATA);
	ConsignmentDataModel consignmentData = new ConsignmentDataModel();
	
	int cid = 0;
	if(data != null && data.size() > 0){
		consignmentData = (ConsignmentDataModel)data.get(0);
		
		cid = consignmentData.getCid();
		totalPayment = consignmentData.getAmount() * 1.03;
		paymentNM = consignmentData.getConsignmentNo();						

	} else {
		out.println("<p align=\"center\"><span class='redBoldText'>" + Resource.getString("ID_LABEL_NORECORD",locale) + "</span></p>");
	}
%>	
	
<% if(data != null && data.size() > 0){ %>

<g:compress>
	<script language="javascript">
	
		function updateThisPickupTime() {
			var pickupTime = $.trim($("#thisPickupTime_<%=consignmentData.getConsignmentNo() %>").val());

			var r = confirm("<%=Resource.getString("ID_MSG_AREYOUCONFIRM",locale)%>");
			if(r) {
				$( "#updateResult1_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
				$.ajax({
					url: './consignment',        
					data: "actionType=inlineUpdate&field=pickupTime&value="+pickupTime+"&name=<%=consignmentData.getConsignmentNo()%>&dataType=txt",
					success: function(response) {
						if(response == "OK") {
						
							$.ajax({
								url: '/consignment',        
								data: "actionType=changePickupTime&consignmentCode=<%=consignmentData.getConsignmentNo()%>",
								success: function(response) {
									if(response == "OK") {
										$( "#updateResult1_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"green\"><%=Resource.getString("ID_LABEL_EMAILED",locale)%></font>");
									} else {
										$( "#updateResult1_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"green\"><%=Resource.getString("ID_LABEL_NOTEMAILED",locale)%></font>");
									}
								} 
							});

						} else {
							$( "#updateResult1_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_CHANGEDNOTSAVED",locale)%></font>");
						}
					} 
				});
			}
		}
		
		function updateThisUserId() {
			var updateId = $.trim($("#thisUserId_<%=consignmentData.getConsignmentNo() %>").val());
			var r = confirm("<%=Resource.getString("ID_MSG_AREYOUCONFIRM",locale)%>");
			if(r) {
				$( "#updateResult2_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
				$.ajax({
					url: './consignment',        
					data: "actionType=inlineUpdate&field=userId&value="+updateId+"&name=<%=consignmentData.getConsignmentNo()%>&dataType=txt",
					success: function(response) {
						if(response == "OK") {
							$( "#updateResult2_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"green\"><%=Resource.getString("ID_LABEL_CHANGEDSAVED",locale)%></font>");
						} else {
							$( "#updateResult2_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_CHANGEDNOTSAVED",locale)%></font>");
						}
					} 
				});
			}
		}
		
		function genPaymentLink() {
			$( "#paymentLink_<%=consignmentData.getConsignmentNo() %>" ).html("...");
			var amount = $.trim($("#thisAmount_<%=consignmentData.getConsignmentNo() %>").val());
			var charge = $.trim($("#thisCharge_<%=consignmentData.getConsignmentNo() %>").val());
			var totalAmount = (eval(amount) * eval(charge)/100) + eval(amount);
			var vcode = getNewVCode(totalAmount);
		}
		
		function getNewVCode(totalAmount) {
			$.post("/logon",
			{
				actionType: "md5",
				txt: totalAmount+"iposb<%=consignmentData.getConsignmentNo()%>19abbf40579a780fd64ea8d1f93d26b3"
			},
			
			function(data, status){
				$( "#paymentLink_<%=consignmentData.getConsignmentNo() %>" ).html("<a href=\"https://www.onlinepayment.com.my/NBepay/pay/iposb/?amount="+totalAmount+"&orderid=<%=consignmentData.getConsignmentNo() %>&bill_name=<%=consignmentData.getSenderName().replace(" ", "+") %>&bill_email=<%=consignmentData.getEmail() %>&bill_mobile=<%=consignmentData.getSenderPhone().replace("+", "%2B") %>&bill_desc=Payment+for+<%=paymentNM.replace("+", "%2B").replace(" ", "+").replace("&", "%26") %>&cur=myr&country=MY&vcode="+data+"\" target=\"_blank\"><%=Resource.getString("ID_LABEL_PAYMENTAMOUNT",locale) %> MYR "+totalAmount+"</a>");
			});
		}
		
		
		function generateCN(consignmentNo){
			$( "#updateResult6_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$('#cn_'+consignmentNo).html("");
			$(function() {
				$.ajax({
					url: './pdf',        
					data: "actionType=consignmentNote&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response == "generated success") {
							$('#cn_'+consignmentNo).html("<a href='https://s3-ap-southeast-1.amazonaws.com/iposb/pdf/"+consignmentNo+"-IPOSB.pdf' target='_blank'><img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a>");
							$( "#updateResult6_<%=consignmentData.getConsignmentNo() %>" ).html("<a style=\"color:green\" href='https://s3-ap-southeast-1.amazonaws.com/iposb/pdf/"+consignmentNo+"-IPOSB.pdf' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%></a>");
						} else {
							$( "#updateResult6_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_GENERATEDPDFFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		
		function deleteCN(consignmentNo){
			$( "#updateResult6_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$('#cn_'+consignmentNo).html("");
			$(function() {
				$.ajax({
					url: './pdf',        
					data: "actionType=deleteConsignmentNote&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response == "deleted success") {
							$('#cn_'+consignmentNo).html("");
							$( "#updateResult6_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"green\"><%=Resource.getString("ID_LABEL_PDFDELETED",locale)%></font>");
						} else {
							$( "#updateResult6_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_PDFDELETEDFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		
		function generateCNSticker(consignmentNo){
			$( "#updateResult7_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$('#sticker_'+consignmentNo).html("");
			$(function() {
				$.ajax({
					url: './pdf',        
					data: "actionType=consignmentNoteSticker&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response == "generated success") {
							$('#sticker_'+consignmentNo).html("<a href='https://s3-ap-southeast-1.amazonaws.com/iposb/sticker/"+consignmentNo+"-sticker.pdf' target='_blank'><img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a>");
							$( "#updateResult7_<%=consignmentData.getConsignmentNo() %>" ).html("<a style=\"color:green\" href='https://s3-ap-southeast-1.amazonaws.com/iposb/sticker/"+consignmentNo+"-sticker.pdf' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%></a>");
						} else {
							$( "#updateResult7_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_GENERATEDPDFFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		
		function generatePartnerSticker(generalCargoNo){
			$( "#updateResult8_<%=consignmentData.getGeneralCargoNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$('#stickerPartner_'+generalCargoNo).html("");
			$(function() {
				$.ajax({
					url: './pdf',        
					data: "actionType=partnerSticker&generalCargoNo="+generalCargoNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						$('#stickerPartner_'+generalCargoNo).html("<a href='https://s3-ap-southeast-1.amazonaws.com/iposb/sticker/"+response+"' target='_blank'><img src='<%=resPath %>/assets/L10N/common/img/pdf.gif' border='0' /></a>");
						$( "#updateResult8_<%=consignmentData.getGeneralCargoNo() %>" ).html("<a style=\"color:green\" href='https://s3-ap-southeast-1.amazonaws.com/iposb/sticker/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%></a>");
					} 
				});
			});
		};
		
		
		function generateFile(consignmentNo){
			$( "#updateResult4_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$(function() {
				$.ajax({
					url: './consignment',        
					data: "actionType=generatePDF&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response == "generated success") {
							$('#pdf_'+consignmentNo).html("<a href='https://s3-ap-southeast-1.amazonaws.com/iposb/pdf/"+consignmentNo+"-IPOSB.pdf' target='_blank'><img src='<%=resPath %>/assets/L10N/common/assets/img/pdf.gif' border='0' /></a>");
							$( "#updateResult4_<%=consignmentData.getConsignmentNo() %>" ).html("<a style=\"color:green\" href='https://s3-ap-southeast-1.amazonaws.com/iposb/pdf/"+consignmentNo+"-IPOSB.pdf' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%></a>");
						} else {
							$( "#updateResult4_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_GENERATEDPDFFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		
		function deleteFile(consignmentNo){
			$( "#updateResult4_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$(function() {
				$.ajax({
					url: './consignment',        
					data: "actionType=deletePDF&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response == "deleted success") {
							$('#pdf_'+consignmentNo).html("&nbsp;");
							$( "#updateResult4_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"green\"><%=Resource.getString("ID_LABEL_PDFDELETED",locale)%></font>");
						} else {
							$( "#updateResult4_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_PDFDELETEDFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		
		
		function generateInvoiceFile(consignmentNo){
			
			var guestName = $.trim($("#thisSenderName_<%=consignmentData.getConsignmentNo() %>").val());
			var contactNo = $.trim($("#thisContactNo_<%=consignmentData.getConsignmentNo() %>").val());
			
			$( "#updateResult5_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$(function() {
				$.ajax({
					url: './invoice',
					data: "actionType=generateInvoice&guestName="+guestName+"&contactNo="+encodeURIComponent(contactNo)+"&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response.length == 8) { //有invoice no
							$('#invoice_'+consignmentNo).html("<a href='https://s3-ap-southeast-1.amazonaws.com/iposb/invoice/"+response+"-"+consignmentNo+"-IPOSB.pdf' target='_blank'><img src='<%=resPath %>/assets/L10N/common/assets/img/pdf.gif' border='0' /></a>");
							$( "#updateResult5_<%=consignmentData.getConsignmentNo() %>" ).html("<a style=\"color:green\" href='https://s3-ap-southeast-1.amazonaws.com/iposb/invoice/"+response+"-"+consignmentNo+"-IPOSB.pdf' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDPDF",locale)%></a>");
						} else {
							$( "#updateResult5_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_GENERATEDPDFFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		

		function deleteInvoiceFile(consignmentNo){
			
			$( "#updateResult5_<%=consignmentData.getConsignmentNo() %>" ).html("&nbsp;&nbsp;&nbsp;<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>");
			$(function() {
				$.ajax({
					url: './invoice',        
					data: "actionType=deleteInvoice&consignmentNo="+consignmentNo,
					success: function(response) {
						$(document).ajaxStop($.unblockUI); // unblock when ajax activity stops
						if(response == "deleted success") {
							$('#invoice_'+consignmentNo).html("&nbsp;");
							$( "#updateResult5_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"green\"><%=Resource.getString("ID_LABEL_PDFDELETED",locale)%></font>");
						} else {
							$( "#updateResult5_<%=consignmentData.getConsignmentNo() %>" ).html("<font color=\"red\"><%=Resource.getString("ID_LABEL_PDFDELETEDFAILED",locale)%></font>");
						}
					} 
				});
			});
		};
		
	</script>
</g:compress>

	
	<b><%=Resource.getString("ID_LABEL_CHANGESENDER",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		<%=Resource.getString("ID_LABEL_USERID",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %><input type="text" id="thisUserId_<%=consignmentData.getConsignmentNo() %>" name="thisUserId_<%=consignmentData.getConsignmentNo() %>" value="<%=consignmentData.getUserId() %>" style="width: 188px" maxlength="120">
		<input type="button" class="btn btn-primary" style="height:auto;" name="updateUserId" id="updateUserId" value="<%=Resource.getString("ID_LABEL_CHANGE",locale) %>" onClick="updateThisUserId()" /><span id="updateResult2_<%=consignmentData.getConsignmentNo() %>"></span>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	
	<!-- 
	
	<b><%=Resource.getString("ID_LABEL_GENERATEPAYMENTLINK",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		MYR <input type="text" id="thisAmount_<%=consignmentData.getConsignmentNo() %>" name="thisAmount_<%=consignmentData.getConsignmentNo() %>" value="<%=consignmentData.getAmount() %>" style="width: 68px"> + <input type="text" id="thisCharge_<%=consignmentData.getConsignmentNo() %>" name="thisCharge_<%=consignmentData.getConsignmentNo() %>" value="3" style="width: 23px">%
		<input type="button" class="btn btn-primary" style="height:auto;" name="generatePaymentLink" id="generatePaymentLink" value="<%=Resource.getString("ID_LABEL_GENERATE",locale) %>" onClick="genPaymentLink()" />
		<div id="paymentLink_<%=consignmentData.getConsignmentNo() %>">&nbsp;</div>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	
	 -->
	
	<b><%=Resource.getString("ID_LABEL_GENERATECONSIGNMENTNOTE",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		<input type="button" class="btn btn-primary" style="height:auto;" id="generateCNBtn" name="generateCNBtn" value="<%=Resource.getString("ID_LABEL_GENERATEPDF",locale)%>" onclick="generateCN('<%=consignmentData.getConsignmentNo() %>')">									
		<input type="button" class="btn btn-primary" style="height:auto;" id="deleteCNBtn" name="deleteCNBtn" value="<%=Resource.getString("ID_LABEL_DELETEPDF",locale)%>" onclick="deleteCN('<%=consignmentData.getConsignmentNo() %>')">
		<span id="updateResult6_<%=consignmentData.getConsignmentNo() %>"></span>
		<span id="cn_<%=consignmentData.getConsignmentNo() %>"></span>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	
	<b><%=Resource.getString("ID_LABEL_GENERATECONSIGNMENTNOTESTICKER",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		<input type="button" class="btn btn-primary" style="height:auto;" id="generateCNStickerBtn" name="generateCNStickerBtn" value="<%=Resource.getString("ID_LABEL_GENERATEPDF",locale)%>" onclick="generateCNSticker('<%=consignmentData.getConsignmentNo() %>')">									
		<span id="updateResult7_<%=consignmentData.getConsignmentNo() %>"></span>
		<span id="sticker_<%=consignmentData.getConsignmentNo() %>"></span>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	
	<%
		if(consignmentData.getGeneralCargoNo().trim().length() > 0) { //如果是 general cargo 的物件，才可以 generate 這種 sticker
	%>
	<b><%=Resource.getString("ID_LABEL_GENERATEPARTNERCONSIGNMENTSTICKER",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		<input type="button" class="btn btn-primary" style="height:auto;" id="generatePartnerStickerBtn" name="generatePartnerStickerBtn" value="<%=Resource.getString("ID_LABEL_GENERATEPDF",locale)%>" onclick="generatePartnerSticker('<%=consignmentData.getGeneralCargoNo() %>')">									
		<span id="updateResult8_<%=consignmentData.getGeneralCargoNo() %>"></span>
		<span id="stickerPartner_<%=consignmentData.getGeneralCargoNo() %>"></span>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	
	<% } %>
	
	<!-- 
	<b><%=Resource.getString("ID_LABEL_GENERATERECEIPT",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		<input type="button" class="btn btn-primary" style="height:auto;" id="generateBtn" name="generateBtn" value="<%=Resource.getString("ID_LABEL_GENERATEPDF",locale)%>" onclick="generateFile('<%=consignmentData.getConsignmentNo() %>')">									
		<input type="button" class="btn btn-primary" style="height:auto;" id="deleteBtn" name="deleteBtn" value="<%=Resource.getString("ID_LABEL_DELETEPDF",locale)%>" onclick="deleteFile('<%=consignmentData.getConsignmentNo() %>')">
		<span id="updateResult4_<%=consignmentData.getConsignmentNo() %>"></span>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	
	 
	
	<b><%=Resource.getString("ID_LABEL_GENERATETAXINVOICE",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %></b><br />
	<div style="border-style:dashed; border-width:1px; padding:10px">
		<%=Resource.getString("ID_LABEL_BILLTO",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %><input type="text" id="thisSenderName_<%=consignmentData.getConsignmentNo() %>" name="thisSenderName_<%=consignmentData.getConsignmentNo() %>" value="<%=consignmentData.getSenderName() %>" style="width: 128px" maxlength="50">
		<%=Resource.getString("ID_LABEL_SENDERPHONE",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %><input type="text" id="thisContactNo_<%=consignmentData.getConsignmentNo() %>" name="thisContactNo_<%=consignmentData.getConsignmentNo() %>" value="<%=consignmentData.getSenderPhone() %>" style="width: 128px" maxlength="25"><br />
		<input type="button" class="btn btn-primary" style="height:auto;" id="generateInvoiceBtn_<%=consignmentData.getConsignmentNo() %>" name="generateInvoiceBtn" value="<%=Resource.getString("ID_LABEL_GENERATEPDF",locale)%>" onclick="generateInvoiceFile('<%=consignmentData.getConsignmentNo() %>')">									
		<input type="button" class="btn btn-primary disabled" style="height:auto;" id="deleteInvoiceBtn_<%=consignmentData.getConsignmentNo() %>" name="deleteInvoiceBtn" value="<%=Resource.getString("ID_LABEL_DELETEPDF",locale)%>" onclick="deleteInvoiceFile('<%=consignmentData.getConsignmentNo() %>')">
		<span id="updateResult5_<%=consignmentData.getConsignmentNo() %>"></span>
	</div>
	
	<div class="space-10" style="clear:both;"></div>
	-->

<% } %>
