<%@page import="com.iposb.i18n.*,com.iposb.howitworks.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String content = "";
	ArrayList data = (ArrayList)request.getAttribute(HowitworksController.OBJECTDATA);
	HowitworksDataModel howitworksData = new HowitworksDataModel();
	if(data != null && data.size() > 0){
		howitworksData = (HowitworksDataModel)data.get(0);
		content = howitworksData.getContent_enUS();
		
		if(locale.equals("zh_CN")) {
			content = howitworksData.getContent_zhCN();
		} else if(locale.equals("zh_TW")) {
			content = howitworksData.getContent_zhTW();
		}
	}
%>

<head>
    <title><%=Resource.getString("ID_LABEL_HOWITWORKS",locale)%> - iPosb Logistic</title>

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
			<h2 class="white"><%=Resource.getString("ID_LABEL_HOWITWORKS",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_HOWITWORKS",locale)%></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
			

    <!-- Inner Content -->
	<div class="inner-page padd">
	
		<!-- General Info Start -->
		
		<div class="general">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-sm-8">
						<%=content %>
					</div>
					<div class="col-md-4 col-sm-4">
						<!-- General Sidebar image -->
						<div class="general-img">
							<img class="img-responsive img-thumbnail" src="assets/img/service/service1.jpg" alt="" />
							<!-- Hot tag -->
							<span class="hot-tag br-green">New</span>
						</div>
						<!-- General Sidebar image -->
						<div class="general-img">
							<img class="img-responsive img-thumbnail" src="assets/img/service/service2.jpg" alt="" />
							<!-- Hot tag -->
							<span class="hot-tag br-red">Hot</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- General Info End -->
	
		
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