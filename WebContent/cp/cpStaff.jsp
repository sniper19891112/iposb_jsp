<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.area.*,com.iposb.utils.*,java.util.HashMap,java.util.Iterator,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%		
	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel logonData = new LogonDataModel();
	String errMsg = "";

	ArrayList aData = (ArrayList)request.getAttribute("area");
	AreaDataModel areaData = new AreaDataModel();
	
	if(data != null && data.size() > 0){
		logonData = (LogonDataModel)data.get(0);
		errMsg = logonData.getErrmsg();
	}
	
	//String pageNo = request.getParameter("page")==null ? "1" : request.getParameter("page").toString();
	String searchuser = request.getParameter("searchuser")==null ? "" : new String(request.getParameter("searchuser").getBytes("ISO-8859-1"), "UTF-8");
	boolean isSearch = request.getParameter("actionType")==null ? false : request.getParameter("actionType").toString().equals("searchStaff") ? true : false;
	
	boolean addEdit = false;
	boolean list = true;

%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_STAFFMAINTAINANCE",locale)%></title>
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
							<% 
								if (isSearch) {
									out.print("<li><a href=\"./member\">"+Resource.getString("ID_LABEL_STAFFMAINTAINANCE",locale)+"</a></li>"); 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_SEARCH",locale)+ " \"" + searchuser +"\"</li>");
								} else { 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_STAFFMAINTAINANCE",locale)+"</li>");
								} 
							%>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<div class="row">
										
										<div class="col-xs-8">
											<!-- 
											<a class="btn btn-primary" id="addCreditnoteBtn">
												<i class="fa fa-plus"></i>
												<%=Resource.getString("ID_LABEL_ISSUECREDITNOTE",locale)%>
											</a>
											-->
										</div>
										
										<div class="col-xs-12 col-sm-4">
						           			<form name="searchStaff" id="searchStaff" action="./logon" method="get">
												<input type="hidden" id="actionType" name="actionType" value="searchStaff">
												
												<div class="input-group">
													<input class="form-control search-query" id="searchuser" name="searchuser" value="<%=searchuser %>" placeholder="Name, Email, Contact Number" type="text">
													<span class="input-group-btn">
														<button type="submit" class="btn btn-purple btn-sm">
															<%=Resource.getString("ID_LABEL_SEARCH",locale)%>
															<i class="icon-search icon-on-right bigger-110"></i>
														</button>
													</span>
												</div>
											
											</form>
										</div>
									
					           		</div>
					           		
					           		<div class="space-10"></div>
									
									<div class="row">
										<div class="col-xs-12">

											<div class="table-responsive">
												<table id="members-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ENAME",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CNAME",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_EMAIL",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_IC",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_AREA",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%></th>
															<th class="center"></th>
														</tr>
													</thead>

													<tbody>
													<%
														logonData = new LogonDataModel();
														
														if(data != null && data.size() > 0){
															for(int i = 0; i < data.size(); i++){
																logonData = (LogonDataModel)data.get(i);
													%>
														<tr>
															<td class="center"><%=logonData.getSid() %></td>
															<td class="center"><%=logonData.getEname() %></td>
															<td class="center"><%=logonData.getCname() %></td>
															<td class="center"><%=logonData.getEmail() %></td>
															<td class="center"><%=logonData.getPhone() %></td>
															<td class="center"><%=logonData.getIC() %></td>
															<td class="center"><%=logonData.getAreaName() %></td>
															<td class="center"><%=Resource.getString("ID_LABEL_PRIV"+logonData.getPrivilege(), locale)%></td>
															<td class="center">
																<div class="visible-md visible-lg hidden-sm hidden-xs">
																	<a onclick="editLogon('<%=logonData.getSid() %>')" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_EDIT",locale)%>">
																		<span class="green">
																			<i class="icon-edit bigger-120"></i>
																		</span>
																	</a>
																	<!-- 
																	<a id="userDetails" userId="<%=logonData.getUserId() %>" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_DETAILS",locale)%>">
																		<span class="blue">
																			<i class="icon-search bigger-120"></i>
																		</span>
																	</a>
																	-->
																</div>
																<input type="hidden" id="email<%=logonData.getSid() %>" name="email<%=logonData.getSid() %>" value="<%=logonData.getEmail() %>" />
																<input type="hidden" id="ename<%=logonData.getSid() %>" name="ename<%=logonData.getSid() %>" value="<%=logonData.getEname() %>" />
																<input type="hidden" id="cname<%=logonData.getSid() %>" name="cname<%=logonData.getSid() %>" value="<%=logonData.getCname() %>" />
																<input type="hidden" id="gender<%=logonData.getSid() %>" name="gender<%=logonData.getSid() %>" value="<%=logonData.getGender() %>" />
																<input type="hidden" id="phone<%=logonData.getSid() %>" name="phone<%=logonData.getSid() %>" value="<%=logonData.getPhone() %>" />
																<input type="hidden" id="dob<%=logonData.getSid() %>" name="dob<%=logonData.getSid() %>" value="<%=logonData.getDob() %>" />
																<input type="hidden" id="IC<%=logonData.getSid() %>" name="IC<%=logonData.getSid() %>" value="<%=logonData.getIC() %>" />
																<input type="hidden" id="aid<%=logonData.getSid() %>" name="aid<%=logonData.getSid() %>" value="<%=logonData.getAid() %>" />
																<input type="hidden" id="privilege<%=logonData.getSid() %>" name="privilege<%=logonData.getSid() %>" value="<%=logonData.getPrivilege() %>" />
																<input type="hidden" id="stage1<%=logonData.getSid() %>" name="stage1<%=logonData.getSid() %>" value="<%=logonData.getStage1() %>" />
																<input type="hidden" id="stage2<%=logonData.getSid() %>" name="stage2<%=logonData.getSid() %>" value="<%=logonData.getStage2() %>" />
																<input type="hidden" id="stage3<%=logonData.getSid() %>" name="stage3<%=logonData.getSid() %>" value="<%=logonData.getStage3() %>" />
																<input type="hidden" id="stage4<%=logonData.getSid() %>" name="stage4<%=logonData.getSid() %>" value="<%=logonData.getStage4() %>" />
																<input type="hidden" id="stage5<%=logonData.getSid() %>" name="stage5<%=logonData.getSid() %>" value="<%=logonData.getStage5() %>" />
																<input type="hidden" id="stage6<%=logonData.getSid() %>" name="stage6<%=logonData.getSid() %>" value="<%=logonData.getStage6() %>" />
																<input type="hidden" id="stage7<%=logonData.getSid() %>" name="stage7<%=logonData.getSid() %>" value="<%=logonData.getStage7() %>" />
																<input type="hidden" id="registerIP<%=logonData.getSid() %>" name="registerIP<%=logonData.getSid() %>" value="<%=logonData.getRegisterIP() %>" />
																<input type="hidden" id="lastIP<%=logonData.getSid() %>" name="lastIP<%=logonData.getSid() %>" value="<%=logonData.getLastIP() %>" />
																<input type="hidden" id="lastLoginDT<%=logonData.getSid() %>" name="lastLoginDT<%=logonData.getSid() %>" value="<%=logonData.getLastLoginDT() %>" />
																<input type="hidden" id="totalBooking<%=logonData.getSid() %>" name="totalBooking<%=logonData.getSid() %>" value="<%=logonData.getTotalBooking() %>" />
																<input type="hidden" id="createDT<%=logonData.getSid() %>" name="createDT<%=logonData.getSid() %>" value="<%=logonData.getCreateDT() %>" />
																<input type="hidden" id="modifyDT<%=logonData.getSid() %>" name="modifyDT<%=logonData.getSid() %>" value="<%=logonData.getModifyDT() %>" />
																
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
											//分頁
											int numPerPage = 20; //每頁顯示數量
											String pnoStr = request.getParameter("page");
											Integer pno = pnoStr == null ? 1 : Integer.parseInt(pnoStr);
											pager.setPageNo(pno.intValue());
											pager.setPerLen(numPerPage);
											int total = 0;
											int thisPage = 0;
											String p = request.getParameter("page") == null ? "1" : request.getParameter("page").equals("") ? "1" : request.getParameter("page").toString();
											
											//防止user亂輸入頁碼
											boolean isNum = LogonController.isNumeric(p);
											
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

										
											total = logonData.getTotal();
											pager.setData(total);//设置数据
											int []range = pager.getRange(numPerPage);//获得页码呈现的区间
											if(pager.getMaxPage()>1) {//要呈现的页面超过1页，需要分页呈现
												out.print("<ul class=\"pagination\">");
													
												//上10頁按鍵
												if(thisPage > 1){
													out.print("<li><a href=\"./cpStaff?page="+prev10+"\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁												
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./cpStaff?page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./cpStaff?page="+(next10)+"\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
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
		
		<!--=== Edit Member Modal Start ===-->
			<div class="modal fade" id="editStaff" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h2 class="modal-title" id=""><%=Resource.getString("ID_LABEL_STAFFMAINTAINANCE",locale)%></h2>
						</div>
						<div class="modal-body">
							<div class="center"><img id="modalAvatar" class="cpAvatarStyle" /></div>
							
							<form id="memberForm" name="memberForm" class="reg-page" style="width:90%;margin:0px auto!important;" method="post" action="./logon">
								<input type="hidden" id="actionType" name="actionType" value="ajaxUpdateStaff">
								<input type="hidden" id="formValues" name="formValues">
								<input type="hidden" id="email_Exist" name="email_Exist">
								<input type="hidden" id="email_Current" name="email_Current" value="">
								
								<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DETAILS",locale)%></h3>
									
								<div class="space-4"></div>
			                    <label><%=Resource.getString("ID_LABEL_EMAIL",locale)%> <span class="red">*</span></label>
								<input id="email" name="email" type="text" class="form-control" maxlength="100" value="" onblur="checkExist('email')"> 
								<span id="checkingSpan_email"></span>
								
								<div class="margin-bottom-20"></div>
			
			                    <hr>
								
								<label><%=Resource.getString("ID_LABEL_ENAME",locale)%> <span class="red">*</span></label>
								<input id="ename" name="ename" type="text" class="form-control margin-bottom-20" maxlength="50" value="" onkeyup="changeUpperCase()" placeholder="<%=Resource.getString("ID_LABEL_HINTCONFIRMYOURNAME",locale)%>" />
			
								<label><%=Resource.getString("ID_LABEL_CNAME",locale)%></label>
								<input id="cname" name="cname" type="text" class="form-control margin-bottom-20" maxlength="10" value="" />
								
								<label><%=Resource.getString("ID_LABEL_GENDER",locale)%> <span class="red">*</span></label>
								<select name="gender" id="gender" class="form-control margin-bottom-20">
									<option value="0"></option>
									<option value="1"><%=Resource.getString("ID_LABEL_MALE",locale)%></option>
									<option value="2"><%=Resource.getString("ID_LABEL_FEMALE",locale)%></option>
								</select>
								
								<label><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%> <span class="red">*</span></label>
								<input id="phone" name="phone" type="text" class="form-control margin-bottom-20" size="15" maxlength="15" value="" onBlur="trimZero()" />
								
								<label><%=Resource.getString("ID_LABEL_IC",locale)%> <span class="red">*</span></label>
								<input id="IC" name="IC" type="text" class="form-control margin-bottom-20" size="15" maxlength="15" value="" />
								
								<label><%=Resource.getString("ID_LABEL_AREA",locale)%> <span class="red">*</span></label>
								<select name="aid" id="aid" class="form-control margin-bottom-20">
									<option value="0"></option>
									<% 
		                                if(aData != null && !aData.isEmpty()){
											for(int i = 0; i < aData.size(); i ++){
												areaData = (AreaDataModel)aData.get(i);
	                                %>
										<option value="<%=areaData.getAid() %>" <% if(logonData.getAid()==areaData.getAid()){out.print("selected");} %>><%=areaData.getName_enUS() %></option>
									<% } } %>
								</select>
								
			                    <hr>
			                    <!--=== Privilege ===-->
			                    	<%if(priv == 99){ //only boss can change %>
				                    	<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%></h3>
										<div class="space-4"></div>
										<select name="privilege" id="privilege" class="form-control margin-bottom-20">
											<option value="-9"><%=Resource.getString("ID_LABEL_PRIV-9",locale)%></option>
											<option value="0"><%=Resource.getString("ID_LABEL_PRIV0",locale)%></option>
											<option value="5"><%=Resource.getString("ID_LABEL_PRIV5",locale)%></option>
											<option value="6"><%=Resource.getString("ID_LABEL_PRIV6",locale)%></option>
											<option value="7"><%=Resource.getString("ID_LABEL_PRIV7",locale)%></option>
											<option value="8"><%=Resource.getString("ID_LABEL_PRIV8",locale)%></option>
											<option value="9"><%=Resource.getString("ID_LABEL_PRIV9",locale)%></option>
											<option value="99"><%=Resource.getString("ID_LABEL_PRIV99",locale)%></option>
										</select>
										
										<div class="space-24"></div>
										
										<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_STAFFPERMISSION",locale)%></h3>
										
										<div class="row">
										
											<div class="col-xs-6">
							           			
												<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNPICKUP",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage1" name="stage1" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
								           		<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNSTATION",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage2" name="stage2" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
								           		<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNLOAD",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage3" name="stage3" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
								           		<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNEXCHANGE",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage4" name="stage4" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
											</div>
											
											<div class="col-xs-6">
							           			
												<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNUNLOAD",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage5" name="stage5" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
								           		<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNDISTRIBUTE",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage6" name="stage6" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
								           		<div class="row">
													<div class="col-xs-5">
									           			<%=Resource.getString("ID_LABEL_BTNDELIVER",locale)%>
													</div>
													<div class="col-xs-7">
									           			<label>
															<input id="stage7" name="stage7" value="on" class="ace ace-switch ace-switch-5" type="checkbox" />
															<span class="lbl"></span>
														</label>
													</div>
								           		</div>
								           		
											</div>
										
						           		</div>
									<%} %>
			                    <!--=== Privilege End ===-->
								
								
			                </form>
						</div>
						<div class="modal-footer">
							<span id="loading"></span>
							<button type="button" class="btn btn-default" id="cancel" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
							<button type="button" class="btn btn-info" id="updateMember"><%=Resource.getString("ID_LABEL_SUBMIT",locale) %></button>
							<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal" style="display:none"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
						</div>
					</div>
				</div>
			</div>

		<!--=== Edit Member Modal End ===-->
		
		<jsp:include page="cpModal.jsp" />

		<!-- inline scripts related to this page -->

<g:compress>		
		<script type="text/javascript">
			
			jQuery(function($) {
				
				$("#editStaff #updateMember").click(function(){
					
					var userId = $("#editStaff #userId").val();
					var email = $("#editStaff #email").val();
					var email_Current = $("#editStaff #email_Current").val();
					var ename = $("#editStaff #ename").val();
					var cname = $("#editStaff #cname").val();
					var gender = $("#editStaff #gender").val();
					var phone = $("#editStaff #phone").val();
					var IC = $("#editStaff #IC").val();
					var aid = $("#editStaff #aid").val();
					var privilege = $("#editStaff #privilege").val();
					var stage1 = "";
					var stage2 = "";
					var stage3 = "";
					var stage4 = "";
					var stage5 = "";
					var stage6 = "";
					var stage7 = "";
					if($("#editStaff #stage1").is(':checked')) { stage1 = "on";}
					if($("#editStaff #stage2").is(':checked')) { stage2 = "on";}
					if($("#editStaff #stage3").is(':checked')) { stage3 = "on";}
					if($("#editStaff #stage4").is(':checked')) { stage4 = "on";}
					if($("#editStaff #stage5").is(':checked')) { stage5 = "on";}
					if($("#editStaff #stage6").is(':checked')) { stage6 = "on";}
					if($("#editStaff #stage7").is(':checked')) { stage7 = "on";}

			   		$("#editStaff #loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#editStaff #cancel").hide();
					$("#editStaff #updateMember").hide();
					$.ajax({
						type: 'POST',
						url: './logon',
						data:{    
							lang: "<%=locale %>",
							actionType:"ajaxUpdateStaff",
							userId: userId,
							email: email,
							email_Current: email_Current,
							ename: ename,
							cname: cname,
							gender: gender,
							phone: phone,
							IC: IC,
							aid: aid,
							privilege: privilege,
							stage1: stage1,
							stage2: stage2,
							stage3: stage3,
							stage4: stage4,
							stage5: stage5,
							stage6: stage6,
							stage7: stage7
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "OK"){
									$("#editStaff #loading").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\"><%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%></span>&nbsp;&nbsp;&nbsp;");
									$("#editStaff #closeBtn").show();
									$("#editStaff #updateMember").show();
								} else {
									$("#editStaff #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\"><%=Resource.getString("ID_MSG_UPDATEFAILED",locale) + Resource.getString("ID_LABEL_COLON",locale) %>" + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#editStaff #closeBtn").hide();
									$("#editStaff #cancel").show();
									$("#editStaff #updateMember").show();
								}
							}
						} 
					});
				});
				
				
				$("#editStaff #closeBtn").click(function(){
					location.reload();					
				});
				
			});
			
			
			function editLogon(sid){
				
				$(function(){
				   	$("#modalAvatar").attr("src", "assets/img/noavatar.jpg");
				   	
					var userId = $("#userId" + sid).val();
					var email = $("#email" + sid).val();
					var ename = $("#ename" + sid).val();
					var cname = $("#cname" + sid).val();
					var gender = $("#gender" + sid).val();
					var phone = $("#phone" + sid).val();
					var IC = $("#IC" + sid).val();
					var aid = $("#aid" + sid).val();
					var privilege = $("#privilege" + sid).val();
					var stage1 = $("#stage1" + sid).val();
					var stage2 = $("#stage2" + sid).val();
					var stage3 = $("#stage3" + sid).val();
					var stage4 = $("#stage4" + sid).val();
					var stage5 = $("#stage5" + sid).val();
					var stage6 = $("#stage6" + sid).val();
					var stage7 = $("#stage7" + sid).val();
					var registerIP = $("#registerIP" + sid).val();
					var lastIP = $("#lastIP" + sid).val();
					var lastLoginDT = $("#lastLoginDT" + sid).val();
					var totalBooking = $("#totalBooking" + sid).val();
					var createDT = $("#createDT" + sid).val();
					var modifyDT = $("#modifyDT" + sid).val();

					var avatarDT = '';
					if(modifyDT.trim() != '') avatarDT = modifyDT.trim().replace(' ', '_');
					var avatarSrc = 'https://s3-ap-southeast-1.amazonaws.com/iposb/images/avatar/staff/'+sid+'/avatar.jpg?'+avatarDT;
					
					$("#editStaff #userId").val(userId);
					$("#editStaff #email").val(email);
					$("#editStaff #email_Current").val(email);
					$("#editStaff #ename").val(ename);
					$("#editStaff #cname").val(cname);
					$("#editStaff #gender option[value='" + gender + "']").prop("selected", true);
					$("#editStaff #phone").val(phone);
					$("#editStaff #IC").val(IC);
					$("#editStaff #aid").val(aid);
					$("#editStaff #privilege option[value='" + privilege + "']").prop("selected", true);
					
					//åæ¸é¤checked
					$("#editStaff #stage1").prop('checked', false);
					$("#editStaff #stage2").prop('checked', false);
					$("#editStaff #stage3").prop('checked', false);
					$("#editStaff #stage4").prop('checked', false);
					$("#editStaff #stage5").prop('checked', false);
					$("#editStaff #stage6").prop('checked', false);
					$("#editStaff #stage7").prop('checked', false);
					if(stage1==1){ $("#editStaff #stage1").prop('checked', true); }
					if(stage2==1){ $("#editStaff #stage2").prop('checked', true); }
					if(stage3==1){ $("#editStaff #stage3").prop('checked', true); }
					if(stage4==1){ $("#editStaff #stage4").prop('checked', true); }
					if(stage5==1){ $("#editStaff #stage5").prop('checked', true); }
					if(stage6==1){ $("#editStaff #stage6").prop('checked', true); }
					if(stage7==1){ $("#editStaff #stage7").prop('checked', true); }
					$("#editStaff #registerIP").html(registerIP);
					$("#editStaff #lastIP").html(lastIP);
					$("#editStaff #lastLoginDT").html(lastLoginDT);
					$("#editStaff #totalBooking").html(totalBooking);
					$("#editStaff #createDT").html(createDT);
					$("#editStaff #modifyDT").html(modifyDT);

					testImage(avatarSrc).then(
						function fulfilled(img) {
						   	$("#modalAvatar").attr("src", avatarSrc);
					    },function rejected() {
					    	$("#modalAvatar").attr("src", "assets/img/noavatar.jpg");
					    }
					);
					
					$("#editStaff").modal();
				});
			};
			
			
		</script>
</g:compress>	
		
		
	</body>
</html>
