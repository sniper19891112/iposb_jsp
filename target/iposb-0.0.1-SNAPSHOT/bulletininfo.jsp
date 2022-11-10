<%@page import="com.iposb.i18n.*,com.iposb.bulletin.*,com.iposb.logon.*,com.iposb.utils.*,java.util.ArrayList"%>
<%@include file="include.jsp" %>

<%
	String title = "";
	String content = "";
	String lastUpdated = "";
	ArrayList data = (ArrayList)request.getAttribute(BulletinController.OBJECTDATA);
	BulletinDataModel bulletinData = new BulletinDataModel();
	if(data != null && data.size() > 0){				
		bulletinData = (BulletinDataModel)data.get(0);
		title = bulletinData.getTitle_enUS();
		content = bulletinData.getContent_enUS();
		
		if(locale.equals("zh_CN")){
			title = bulletinData.getTitle_zhCN();
			content = bulletinData.getContent_zhCN();
		}
		else if(locale.equals("zh_TW")){
			title = bulletinData.getTitle_zhTW();
			content = bulletinData.getContent_zhTW();
		}
		
		if(bulletinData.getModifyDT() != null && !bulletinData.getModifyDT().equals("")){
			lastUpdated = bulletinData.getModifyDT();
		} else {
			lastUpdated = bulletinData.getCreateDT();
		}
		
		if(lastUpdated.length() > 10){
			lastUpdated = lastUpdated.substring(0, 10);
		}
	}
	
	//is invisible?
	int visible = bulletinData.getIsShow();
	
	if(visible != 1){ //不公開
		if(priv < 6){ //不是管理層
			response.sendRedirect("./bulletin");
			return;
		}
	}
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_BULLETIN",locale)%> - <%=title %></title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=UtilsBusinessModel.removeHTML(content) %>" />
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
			<h2 class="white"><%=Resource.getString("ID_LABEL_BULLETIN",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_BULLETIN",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
			

    <!-- Inner Content -->
	 <div class="inner-page padd">
			
		<div class="container">

			<div class="row">
	
				<h1><%=title %></h1>
						
				<ul class="list-unstyled list-inline blog-info">
					<li><i class="fa fa-history" title="<%=Resource.getString("ID_LABEL_LASTUPDATE",locale)%>"></i> <%=bulletinData.getModifyDT() %></li>
				</ul>
				
				<div class="margin-bottom-50"></div>
				
				<p><%=content %></p>
				
				<div class="margin-bottom-50"></div>		
	
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->
    

	<%@ include file="footer.jsp" %>

	
</body>

</html>