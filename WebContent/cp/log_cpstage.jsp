<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.logon.*,com.iposb.log.*,java.io.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%
	if(priv < 3){
        return;
	}
	
	String consignmentNo = request.getParameter("consignmentNo")==null ? "" : request.getParameter("consignmentNo").toString();
	
	ArrayList data = (ArrayList)request.getAttribute(LogController.OBJECTDATA);
	LogDataModel logData = new LogDataModel();
	int lastStage = 0;
	
		if(data != null && data.size() > 0){
			for(int i = 0; i < data.size(); i++){
				logData = (LogDataModel)data.get(i);
				
				if(logData.getStage()==1) {
					if(lastStage != 1) {
						out.println("<span class=\"orange\" style=\"font-size:18px;\">Stage 1 ("+Resource.getString("ID_LABEL_BTNPICKUP",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage1\"><i class=\"fa fa-spinner fa-pulse\"></i></span><br/>");
					}
					lastStage = 1;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==2) {
					if(lastStage != 2) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">Stage 2 ("+Resource.getString("ID_LABEL_BTNSTATION",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage2\"><i class=\"fa fa-spinner fa-pulse\"></i></span><br/>");
					}
					lastStage = 2;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==3) {
					if(lastStage != 3) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">Stage 3 ("+Resource.getString("ID_LABEL_BTNLOAD",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage3\"><i class=\"fa fa-spinner fa-pulse\"></i></span><br/>");
					}
					lastStage = 3;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> #" + logData.getSerial() + " - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==4) {
					if(lastStage != 4) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">Stage 4 ("+Resource.getString("ID_LABEL_BTNEXCHANGE",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage4\"><i class=\"fa fa-spinner fa-pulse\"></i></span><br/>");
					}
					lastStage = 4;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> #" + logData.getSerial() + " - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==5) {
					if(lastStage != 5) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">Stage 5 ("+Resource.getString("ID_LABEL_BTNUNLOAD",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage5\"><i class=\"fa fa-spinner fa-pulse\"></i></span><br/>");
					}
					lastStage = 5;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> #" + logData.getSerial() + " - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==6) {
					if(lastStage != 6) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">Stage 6 ("+Resource.getString("ID_LABEL_OUTOFDELIVERY",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage6\"><i class=\"fa fa-spinner fa-pulse\"></i></span><br/>");
					}
					lastStage = 6;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==7) {
					if(lastStage != 7) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">Stage 7 ("+Resource.getString("ID_LABEL_DELIVERED",locale)+")</span> &nbsp; <span id=\"conPics"+consignmentNo+"_Stage7\"><i class=\"fa fa-spinner fa-pulse\"></i></span> <span id=\"conDO_"+consignmentNo+"\" style=\"margin-left: 8px\"></span><br/>");
					}
					lastStage = 7;
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; <i class=\"ace-icon fa fa-cube orange\"></i> - by " + logData.getUserId() + "<br />");
				} else if(logData.getStage()==9) { //pending shipment
					if(lastStage != 9) {
						out.println("<br/><span class=\"orange\" style=\"font-size:18px;\">"+Resource.getString("ID_LABEL_STAGE9",locale)+"</span> <span id=\"conPics_Stage9\"></span><br/>");
					}
					lastStage = 9;
					
					String reasonTxt = "";
					String reasonPending = logData.getReasonPending();
					if(reasonPending.equals("MD")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONMD",locale);
					} else if(reasonPending.equals("NA")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONNA",locale);
					} else if(reasonPending.equals("NP")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONNP",locale);
					} else if(reasonPending.equals("PH")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONPH",locale);
					} else if(reasonPending.equals("RF")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONRF",locale);
					} else if(reasonPending.equals("ST")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONST",locale);
					} else if(reasonPending.equals("UL")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONUL",locale);
					} else if(reasonPending.equals("VB")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONVB",locale);
					} else if(reasonPending.equals("WA")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONWA",locale);
					} else if(reasonPending.equals("WR")) {
						reasonTxt = Resource.getString("ID_LABEL_PENDINGREASONWR",locale);
					} else if(reasonPending.equals("")) {
						reasonTxt = logData.getReasonText();
					} 
					
					out.println("<i class=\"icon-caret-right blue\"></i> " + logData.getCreateDT() + " &nbsp; - by " + logData.getUserId() + "<br />");
					out.println(" &nbsp; Reason: " + reasonTxt + "<br />");
				}

			}
		} else {
			out.println("<p align=\"center\"><span class='redBoldText'>" + Resource.getString("ID_LABEL_NORECORD",locale) + "</span></p>");
		}
%>

<script type="text/javascript">
	$(document).ready(function() {
	
		//check conPics files
		$.ajax({
			url: './resource',        
			data: 'actionType=getConPics&consignmentNo=<%=consignmentNo %>',
			dataType: 'json',
			success: function(response) {
			
			<% for (int i = 1; i <= 7; i++) { %>
					if (response.stage<%=i %> == "") {
						$('#conPics<%=consignmentNo %>_Stage<%=i %>').html("&nbsp;");
					} else {
						var iconLink = response.stage<%=i %>;
						$('#conPics<%=consignmentNo %>_Stage<%=i %>').html(iconLink);
					}
			<% } %>
			
			checkDO();
				
			}
		});
	
	});
	
	function checkDO(){
		$.ajax({
			url: './resource',        
			data: 'actionType=getConDO&consignmentNo=<%=consignmentNo %>',
			dataType: 'json',
			success: function(response) {
			
				if (response.DO == "") {
					$('#conDO_<%=consignmentNo %>').html("&nbsp;");
				} else {
					$('#conDO_<%=consignmentNo %>').html("D/O: "+response.DO);
				}
				
			}
		});
	}
	
</script>