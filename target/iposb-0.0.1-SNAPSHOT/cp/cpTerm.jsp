<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.term.*,com.iposb.privilege.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	
	if(userId.equals("")){
		String url = "./";
		response.sendRedirect(url);
        return;
	}
	
	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(TermController.OBJECTDATA);
	TermDataModel termData = new TermDataModel();
	if(data != null && data.size() > 0){
		termData = (TermDataModel)data.get(0);
		errmsg = termData.getErrmsg();
	}

	String actionType = request.getParameter("actionType") == null ? "" : request.getParameter("actionType").toString();
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_TERMSMAINTENANCE",locale)%></title>
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		<%@include file="cpMeta.jsp" %>
		
		<script src="//cdn.ckeditor.com/4.4.7/full/ckeditor.js"></script>
		
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
							<li class="active"><%=Resource.getString("ID_LABEL_TERMSMAINTENANCE",locale)%></li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<!-- PAGE CONTENT BEGINS -->
							
							<div id="result"></div>
													
							<form id="termForm" name="termForm" class="sky-form" method="post" action="./terms">
								<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
								
								<%=Resource.getString("ID_LABEL_CONTENTEN",locale)%> <%=Resource.getString("ID_LABEL_COLON",locale)%>
								<textarea class="ckeditor" id="content_enUS"  name="content_enUS" cols="50" rows="6"><%=termData.getContent_enUS() %></textarea>
								<br /><br />
								<!-- 
								<%=Resource.getString("ID_LABEL_CONTENTSC",locale)%> <%=Resource.getString("ID_LABEL_COLON",locale)%>
								<textarea class="ckeditor" id="content_zhCN" name="content_zhCN" cols="50" rows="6"><%=termData.getContent_zhCN() %></textarea>
								<br /><br />
								
								<%=Resource.getString("ID_LABEL_CONTENTTW",locale)%> <%=Resource.getString("ID_LABEL_COLON",locale)%>
								<textarea class="ckeditor" id="content_zhTW" name="content_zhTW" cols="50" rows="6"><%=termData.getContent_zhTW() %></textarea>
								<br /><br />
								-->
								<div class="row text-center">
										<button class="btn btn-lg rounded btn-primary" id="submitBtn" ><i class="fa fa-save"></i> <%=Resource.getString("ID_LABEL_SAVE",locale)%></button>
								</div>
								<div class="margin-bottom-20"></div>
							</form>
							
							<!-- PAGE CONTENT ENDS -->
							
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		<script type="text/javascript">
		
			$(document).ready(function() {

				<%
					if(errmsg.length() > 0){
						if(errmsg.equals("updateSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATESUCCESS",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>
				
			});
			
			$("#submitBtn").click(function(){
				$("#termForm").submit();
			});

		</script>
		
	</body>
</html>
