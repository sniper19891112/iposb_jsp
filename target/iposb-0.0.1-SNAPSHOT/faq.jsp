<%@page import="com.iposb.i18n.*,com.iposb.faq.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	ArrayList data = (ArrayList)request.getAttribute(FaqController.OBJECTDATA);
	FaqDataModel faqData = new FaqDataModel();
	if(data != null && data.size() > 0){				
		faqData = (FaqDataModel)data.get(0);
	}
%>

<head>
    <title><%=Resource.getString("ID_LABEL_FAQ",locale)%> - iPosb Logistic</title>

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
			<h2 class="white"><%=Resource.getString("ID_LABEL_FAQ",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=Resource.getString("ID_LABEL_FAQ",locale)%></li>
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
						<div class="panel-group acc-v1" id="accordion-1">
			
						<%
							String title = "";
							String content = "";
							if(data != null && data.size() > 0){
								
								out.print("<div class=\"headline\"><h2>" + Resource.getString("ID_LABEL_GENERAL",locale) + " " + Resource.getString("ID_LABEL_FAQ",locale) + "</h2></div>");
								for(int i = 0; i < data.size(); i++){
									faqData = (FaqDataModel)data.get(i);
									title = faqData.getTitle_enUS();
									content = faqData.getContent_enUS();
									if(locale.equals("zh_CN")){
										title = faqData.getTitle_zhCN();
										content = faqData.getContent_zhCN();
									}
									else if(locale.equals("zh_TW")){
										title = faqData.getTitle_zhTW();
										content = faqData.getContent_zhTW();
									}
									if(faqData.getCategory().equals("general")) {
						%>
					
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-1" href="#collapse-<%=i+1%>">
										<%=title %>
									</a>
								</h4>
							</div>
							<div id="collapse-<%=i+1%>" class="panel-collapse collapse">
								<div class="panel-body">
									<%=content %>
								</div>
							</div>
						</div>
						
						<%
									} //end  category
								} // end for-loop
							}
						%>
						
					</div>
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