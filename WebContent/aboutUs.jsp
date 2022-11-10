<%@page import="com.iposb.i18n.*,com.iposb.about.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String content = "";
	ArrayList data = (ArrayList)request.getAttribute(AboutController.OBJECTDATA);
	AboutDataModel aboutData = new AboutDataModel();
	if(data != null && data.size() > 0){
		aboutData = (AboutDataModel)data.get(0);
		content = aboutData.getContent_enUS();
		
		if(locale.equals("zh_CN")) {
			content = aboutData.getContent_zhCN();
		} else if(locale.equals("zh_TW")) {
			content = aboutData.getContent_zhTW();
		}
	}

%>

<head>
    <title><%=Resource.getString("ID_LABEL_ABOUTUS",locale)%> - iPosb Logistic</title>

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
			<h2 class="white"><%=Resource.getString("ID_LABEL_ABOUTUS",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_ABOUTUS",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
			

    <%=content %>
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {

		});

	</script>
</g:compress>	
	
</body>

</html>