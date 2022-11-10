<%@page import="com.iposb.i18n.*,com.iposb.partner.*,com.iposb.privilege.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%
	if(priv != 4){ //neither agent nor parther
		String url = "./partnerlogin";
		response.sendRedirect(url);
        return;
	}
	
	PartnerDataModel logonData = new PartnerDataModel();
	ArrayList data = (ArrayList)request.getAttribute(PartnerController.OBJECTDATA);	
	if(data != null && data.size() > 0){
		logonData = (PartnerDataModel)data.get(0);
	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_DASHBOARD",locale)%></title>
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

				<%@include file="partnerCpSidebar.jsp" %>

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
							<li class="active"><%=Resource.getString("ID_LABEL_DASHBOARD",locale)%></li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<!-- PAGE CONTENT BEGINS -->
							
							<div class="col-xs-12" id="cp">
									<div class="col-xs-12 center" style="margin: 10px">
										<font size="36px"><%=Resource.getString("ID_LABEL_WELCOMETODASHBOARD",locale)%></font>
									</div>
									
									
									<div class="col-xs-12 center"></div>
									
									<div class="col-xs-12 center">
										<form id="searchRecord" name="searchRecord" method="get" action="./partner">
											<div class="row">
												<div class="col-xs-4">
													
												</div>
												<div class="col-xs-4">
													<div class="input-group">
														<input type="hidden" id="actionType" name="actionType" value="">
														<input type="text" style="min-width:150px" class="form-control search-query text-uppercase" id="consignmentNo" name="consignmentNo" maxlength="50" placeholder="<%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%>" />
														
														<span class="input-group-btn">
															<button id="searchBtn" class="btn btn-purple btn-sm">
																<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
															</button>
														</span>
													</div>
												</div>
												<div class="col-xs-4">
													
												</div>
											</div>
										</form>
									</div>
									
									<div class="col-xs-4 center"></div>
									
							</div>
							
							
							<!-- PAGE CONTENT ENDS -->
							
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		<script type="text/javascript">
		
			$(document).ready(function() {

				$( "#consignmentNo" ).focus();
				
			});
			
			$("#searchBtn").click(function() { 
				var consignmentNo = $("#consignmentNo").val();
				if(trim(consignmentNo)=="") {
					alert("Please input Consignment Number");
					return false;
				} else {
					$("#actionType").val("searchPartnerConsignment");
					$("form#searchRecord").submit();	
				}
			});
			
		</script>
		
	</body>
</html>
