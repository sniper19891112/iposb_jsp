<%@page import="com.iposb.i18n.*,com.iposb.consignment.*,com.iposb.logon.*,com.iposb.log.*,java.io.*,java.util.ArrayList" contentType="text/html; charset=utf-8"%>
<%@include file="../include.jsp" %>
<%	
	if(priv < 3){
        return;
	}
	
	String consignmentNo = request.getParameter("consignmentNo") == null ? "" : request.getParameter("consignmentNo").toString();
	
	ArrayList data = (ArrayList)request.getAttribute(LogController.OBJECTDATA);
	LogDataModel logData = new LogDataModel();

		out.print("<span id='internoteSpan_"+consignmentNo+"'>");
		if(data != null && data.size() > 0){
			for(int i = 0; i < data.size(); i++){
				logData = (LogDataModel)data.get(i);
				if(logData.getIsDel()==1){ //deleted
					out.println("<div style='background-color:#E7FCFF'><font color=\"gray\"><strike>" + logData.getRemark().replaceAll("\n", "<br/> ") + "</strike></font> &nbsp; <i class=\"fa fa-trash\" style=\"cursor:pointer;\" title=\"un-delete\" rel=\"tooltip\" onclick=\"updateNote('"+logData.getNid()+"')\"></i></div>");
				} else {
					out.println("<div style='background-color:#E7FCFF'>" + logData.getRemark().replaceAll("\n", "<br/> ") + " &nbsp; <i class=\"fa fa-trash\" style=\"cursor:pointer;\" title=\"delete\" rel=\"tooltip\" onclick=\"updateNote('"+logData.getNid()+"')\"></i></div>");
				}
				out.println("<img src=\""+Resource.getString("ID_IMG_ARR_HISTORY",locale)+"\" alt=\"\" /><i><font size='1'>" + logData.getCreateDT() + " ");
				
				if(logData.getModifyDT() != null && logData.getModifyDT().length() > 0){
					out.println(" &nbsp; " + Resource.getString("ID_LABEL_LASTUPDATE",locale)+Resource.getString("ID_LABEL_COLON",locale)+ logData.getModifier());
				} else { //new keyin
					out.println(" &nbsp; " + Resource.getString("ID_LABEL_CREATEBY",locale)+Resource.getString("ID_LABEL_COLON",locale) + logData.getCreator());
				}

				out.println("</font></i><br /><br />");
			}
		} else {
			out.println("<p align=\"center\"><span class='redBoldText'>" + Resource.getString("ID_LABEL_NORECORD",locale) + "</span></p>");
		}
		out.print("</span>");
%>
<form id="newnote" name="newnote" action="./log" method="post">
	<input type="hidden" id="actionType" name="actionType" value="newnote">
	<textarea style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 68px;" id="note_<%=consignmentNo %>" name="note_<%=consignmentNo %>" class="autosize-transition form-control"></textarea>
	<div align="right"><input type="button" class="btn btn-primary" id="save_<%=consignmentNo %>" name="save_<%=consignmentNo %>" value="<%=Resource.getString("ID_LABEL_SAVE",locale)%>" onclick="saveNote()"></div>
</form>

<script language="javascript">
	
	function saveNote(){
		var remark = $('#note_<%=consignmentNo %>').val();
		$(function() {
			$.ajax({
				url: './log',
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				data: "actionType=savenote&&creator=<%=userId %>&locale=<%=locale %>&consignmentNo=<%=consignmentNo%>&remark="+encodeURIComponent(remark),
				success: function(response) {
					$('#internoteSpan_<%=consignmentNo %>').html(response);
					$('#note_<%=consignmentNo %>').val("");
				} 
			});
		});
	};
	
	function updateNote(nid){
		var remark = $('#note_<%=consignmentNo %>').val();
		$(function() {
			$.ajax({
				url: './log',        
				data: "actionType=updatenote&modifier=<%=userId %>&locale=<%=locale %>&consignmentNo=<%=consignmentNo%>&nid="+nid,
				success: function(response) {
					$('#internoteSpan_<%=consignmentNo %>').html(response);
				} 
			});
		});
	};
	
</script>