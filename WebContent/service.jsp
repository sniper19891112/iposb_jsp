<%@page import="com.iposb.i18n.*,com.iposb.utils.*,com.iposb.service.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	
	String title1 = "";
	String intro1 = "";
	String content1 = "";
	String title2 = "";
	String intro2 = "";
	String content2 = "";
	String title3 = "";
	String intro3 = "";
	String content3 = "";
	
	ArrayList data = (ArrayList)request.getAttribute(ServiceController.OBJECTDATA);
	ServiceDataModel serviceData = new ServiceDataModel();
	if(data != null && data.size() > 0){
		serviceData = (ServiceDataModel)data.get(0);
		title1 = serviceData.getTitle1();
		intro1 = serviceData.getIntro1();
		content1 = serviceData.getContent1();
		title2 = serviceData.getTitle2();
		intro2 = serviceData.getIntro2();
		content2 = serviceData.getContent2();
		title3 = serviceData.getTitle3();
		intro3 = serviceData.getIntro3();
		content3 = serviceData.getContent3();
	}

%>

<head>
    <title><%=Resource.getString("ID_LABEL_OURSERVICES",locale)%> - iPosb Logistic</title>

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
			<h2 class="white"><%=Resource.getString("ID_LABEL_OURSERVICES",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_OURSERVICES",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
			

    <div class="inner-page padd">
			
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<a name="door"></a>
					<h2><%=title1 %></h2>
					<p><%=intro1 %></p>
					<hr>
					
					<div style="text-align: justify; margin-bottom: 100px">
						<%=content1 %>	
					</div>
					
					
					<a name="station"></a>
					<h2><%=title2 %></h2>
					<p><%=intro2 %></p>
					<hr>
					
					<div style="text-align: justify; margin-bottom: 100px">
					<%=content2 %>	
					</div>
					
					
					<a name="online"></a>
					<h2><%=title3 %></h2>
					<p><%=intro3 %></p>
					<hr>
					
					<div style="text-align: justify; margin-bottom: 20px">
					<%=content3 %>	
					</div>

					
				</div>

			</div>
		</div>
		
		
	</div><!-- / Inner Page Content End -->	
			
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