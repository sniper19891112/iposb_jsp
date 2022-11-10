<%@page import="com.iposb.i18n.*,com.iposb.logon.*,java.io.*,java.util.ArrayList,com.iposb.privilege.*"%>
<%@include file="../include.jsp" %>
<%	
	String logDate = request.getParameter("date") == null ? "" : request.getParameter("date").toString();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="https://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title><%=Resource.getString("ID_LABEL_SERVERLOG",locale)%></title>
	<meta name="description" content="" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<%@include file="cpMeta.jsp" %>
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
						<li class="active"><a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li>
						<li class="active"><%=Resource.getString("ID_LABEL_SERVERLOG",locale)%></li>
					</ul><!-- .breadcrumb -->

				</div>

				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->

								<div>
								
									<h4>General System Log:</h4>
									
									<textarea readonly style="width:100%" rows="30" wrap="off">
									<%
										try {
											//java.net.URL path =new java.net.URL(resPath+"/log/log.txt");
											java.net.URL path =config.getServletContext().getResource("/log/log.txt");
											BufferedReader input = new BufferedReader(new InputStreamReader(path.openStream(),"UTF-8"));
						
											String line = "";
											while ((line = input.readLine()) != null) {
												out.println(line);
											}
											input.close();
											
										} catch(IOException e){}
									%>
									</textarea>
								</div>
								<br />

							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div><!-- /.page-content -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container-inner -->
	</div><!-- /.main-container -->
	
	<%@include file="cpFooter.jsp" %>
	
</body>

</html>