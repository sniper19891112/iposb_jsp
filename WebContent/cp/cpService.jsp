<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.service.*,com.iposb.privilege.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	
	if(userId.equals("")){
		String url = "./";
		response.sendRedirect(url);
        return;
	}
	
	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(ServiceController.OBJECTDATA);
	ServiceDataModel serviceData = new ServiceDataModel();
	if(data != null && data.size() > 0){
		serviceData = (ServiceDataModel)data.get(0);
		errmsg = serviceData.getErrmsg();
	}

	String actionType = request.getParameter("actionType") == null ? "" : request.getParameter("actionType").toString();
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title> Services Maintenance </title>
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
							<li class="active"> Services Maintenance </li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<!-- PAGE CONTENT BEGINS -->
							
							<div id="result"></div>
													
							<form id="serviceForm" name="serviceForm" class="sky-form" method="post" action="./service">
								<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
								
								<label><strong>Title 1:</strong></label>
								<br/>
								<input type="text" style="width:100%;" id="title1"  name="title1" maxlength="20" value="<%=serviceData.getTitle1() %>"></input>
								
								<br/><br/><br/>
								<label><strong>Intro 1:</strong></label>
								<br/>
								<input type="text" style="width:100%;" id="intro1"  name="intro1" maxlength="100" value="<%=serviceData.getIntro1() %>"></input>
								
								<br/><br/><br/>
								<label><strong>Content 1:</strong></label>
								<br/>
								<textarea class="ckeditor" id="content1"  name="content1" cols="50" rows="6"><%=serviceData.getContent1() %></textarea>
								<br/><br/>
								
								<hr>
								
								<br/><br/>
								<label><strong>Title 2:</strong></label>
								<br/>
								<input type="text" style="width:100%;" id="title2"  name="title2" maxlength="20" value="<%=serviceData.getTitle2() %>"></input>
								
								<br/><br/><br/>
								<label><strong>Intro 2:</strong></label>
								<br/>
								<input type="text" style="width:100%;" id="intro2"  name="intro2" maxlength="100" value="<%=serviceData.getIntro2() %>"></input>
								
								<br/><br/><br/>
								<label><strong>Content 2:</strong></label>
								<br/>
								<textarea class="ckeditor" id="content2"  name="content2" cols="50" rows="6"><%=serviceData.getContent2() %></textarea>
								<br/><br/>
								
								<hr>
								
								<br/><br/>
								<label><strong>Title 3:</strong></label>
								<br/>
								<input type="text" style="width:100%;" id="title3"  name="title3" maxlength="20" value="<%=serviceData.getTitle3() %>"></input>
								
								<br/><br/><br/>
								<label><strong>Intro 3:</strong></label>
								<br/>
								<input type="text" style="width:100%;" id="intro3"  name="intro3" maxlength="100" value="<%=serviceData.getIntro3() %>"></input>
								
								<br/><br/><br/>
								<label><strong>Content 3:</strong></label>
								<br/>
								<textarea class="ckeditor" id="content3"  name="content3" cols="50" rows="6"><%=serviceData.getContent3() %></textarea>
								
								
								<br/><br/>
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
				$("#serviceForm").submit();
			});

		</script>
		
	</body>
</html>
