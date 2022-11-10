<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.logon.*,com.iposb.log.*,java.io.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<%

	if(priv < 3){
        return;
	}
	
	ArrayList data = (ArrayList)request.getAttribute(LogController.OBJECTDATA);
	LogDataModel logData = new LogDataModel();

		if(data != null && data.size() > 0){
			for(int i = 0; i < data.size(); i++){
				logData = (LogDataModel)data.get(i);
				
				String statusTxt = ConsignmentBusinessModel.parseStatus(logData.getStatus(), locale);
				
				out.println("<i class=\"fa fa-caret-right grey\"></i> " + logData.getModifyDT() + " - by " + logData.getModifier() + "<br />");
				if(statusTxt.length() > 0) {
					out.println("<ul><li>");
					if(logData.getStage()==4) { //join consignment or upload excel
						out.println("[" + statusTxt + "] " + logData.getRemark());
					} else {
						out.println("[" + statusTxt + "] " + logData.getRemark());
					}
					out.println("</li></ul>");
				} else {
					out.println("<ul>");
					out.println("<li>"+logData.getRemark() + "</li>");
					out.println("</ul>");
				}
			}
		} else {
			out.println("<p align=\"center\"><span class='redBoldText'>" + Resource.getString("ID_LABEL_NORECORD",locale) + "</span></p>");
		}
%>