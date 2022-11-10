<%@page import="com.iposb.i18n.*,com.iposb.notification.*,com.iposb.utils.*,java.util.HashMap,java.util.Iterator,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%	
	ArrayList data = (ArrayList)request.getAttribute(NotificationController.OBJECTDATA);
	NotificationDataModel notificationData = new NotificationDataModel();
	String errMsg = "";
	
	if(data != null && data.size() > 0){
		notificationData = (NotificationDataModel)data.get(0);
	}
	
	String pageNo = request.getParameter("page")==null ? "1" : request.getParameter("page").toString();


%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_NOTIFICATION",locale)%></title>
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
							<li><a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li>
							<li class="active"><%=Resource.getString("ID_LABEL_NOTIFICATION",locale)%></li>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
		
									<div class="row">
										<div class="col-xs-12">

											<div class="table-responsive">
												<table id="members-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CONTENT",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_DATE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ISREAD",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_READBY",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_READDT",locale)%></th>
														</tr>
													</thead>

													<tbody>
													<%
														notificationData = new NotificationDataModel();
														
														if(data != null && data.size() > 0){
															for(int i = 0; i < data.size(); i++){
																notificationData = (NotificationDataModel)data.get(i);
													%>
														<tr>
															<td class="center"><%=notificationData.getNid() %></td>
															<td class="center">
																<%
																	String param = "";
																	if(notificationData.getNotifyType().equals("consignment")) {
															    		param = "./consignment?actionType=searchConsignment&consignmentNo="+notificationData.getParam();
															    	}else{
															    		param = notificationData.getParam();
															    	}
																%>
																<a href="<%=param %>" target="_blank"><%=notificationData.getContent() %></a>
															</td>
															<td class="center"><%=notificationData.getCreateDT() %></td>
															<td class="center">
																<% 
																	if (notificationData.getIsRead() == 1) {
																		out.print("<i class=\"fa fa-check green\"><i>");	
																	}
																%>
															
															</td>
															<td class="center"><%=notificationData.getReadBy() %></td>
															<td class="center"><%=notificationData.getReadDT() %></td>
															</td>
														</tr>
														
														
													<% } } %>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									
									<div align="center">
											
										<%
											//分頁
											int numPerPage = 30; //每頁顯示數量
											String pnoStr = request.getParameter("page");
											Integer pno = pnoStr == null ? 1 : Integer.parseInt(pnoStr);
											pager.setPageNo(pno.intValue());
											pager.setPerLen(numPerPage);
											int total = 0;
											int thisPage = 0;
											String p = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
											
											//防止user亂輸入頁碼
											boolean isNum = NotificationController.isNumeric(p);
											
											if(isNum){
												thisPage = Integer.parseInt(p);
											}
											if( !(thisPage > 0) || !(isNum) ){
												thisPage = 1;
											}
											
											
											int prev10 = 0;
											int next10 = 0;
											int this10start = 0;
											if( pno%2==0 || pno%2==1 ){//整數
											  prev10 = (int) (Math.floor( (pno-10)/10.1 ) * 10) + 1; //上10頁
											  next10 = (int) (Math.floor( (pno+10)/10.1 ) * 10) + 1;//下10頁
											  this10start = (int) (Math.floor( (pno)/10.1 ) * 10) + 1; //當天10頁的開始頁數 eg. 1, 21, 31, ...
											} else {//有小數
											  prev10 = (int) (Math.floor( (pno-10)/10 ) * 10) + 1; //上10頁
											  next10 = (int) (Math.floor( (pno+10)/10 ) * 10) + 1;//下10頁
											  this10start = (int) (Math.floor( (pno)/10 ) * 10) + 1; //當天10頁的開始頁數 eg. 1, 21, 31, ...
											}

										
											total = notificationData.getTotal();
											pager.setData(total);//设置数据
											int []range = pager.getRange(numPerPage);//获得页码呈现的区间
											if(pager.getMaxPage()>1) {//要呈现的页面超过1页，需要分页呈现
												out.print("<ul class=\"pagination\">");
													
												//上10頁按鍵
												if(thisPage > 1){
													out.print("<li><a href=\"./notification?page="+prev10+"\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁													
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./notification?page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./notification?page="+(next10)+"\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
												}
												
												out.print("</ul>");
										   }
										%>
										
								</div>
								
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
