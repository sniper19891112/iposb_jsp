<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,com.iposb.area.*,java.util.HashMap,java.util.Iterator,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%		
	
	//Area List
	ArrayList<AreaDataModel> areaData = new ArrayList<AreaDataModel>();
	areaData = AreaBusinessModel.areaList();
	AreaDataModel aData = new AreaDataModel();

	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel logonData = new LogonDataModel();
	String errMsg = "";
	
	if(data != null && data.size() > 0){
		logonData = (LogonDataModel)data.get(0);
		errMsg = logonData.getErrmsg();
	}
	
	//String pageNo = request.getParameter("page")==null ? "1" : request.getParameter("page").toString();
	String searchuser = request.getParameter("searchuser")==null ? "" : new String(request.getParameter("searchuser").getBytes("ISO-8859-1"), "UTF-8");
	boolean isSearch = request.getParameter("actionType")==null ? false : request.getParameter("actionType").toString().equals("searchmember") ? true : false;
	
	int privFilter = Integer.parseInt(request.getParameter("privFilter")==null ? "-1" : request.getParameter("privFilter"));
	
	boolean addEdit = false;
	boolean list = true;

%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_MEMBERMAINTAINANCE",locale)%></title>
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
									out.print("<li><a href=\"./member\">"+Resource.getString("ID_LABEL_MEMBERMAINTAINANCE",locale)+"</a></li>"); 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_SEARCH",locale)+ " \"" + searchuser +"\"</li>");
								} else { 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_MEMBERMAINTAINANCE",locale)+"</li>");
								} 
							%>
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<div class="row">
										
										<div class="col-xs-5">
											<!-- 
											<a class="btn btn-primary" id="addCreditnoteBtn">
												<i class="fa fa-plus"></i>
												<%=Resource.getString("ID_LABEL_ISSUECREDITNOTE",locale)%>
											</a>
											-->
										</div>
										
										
										<div class="col-xs-7 col-sm-4">
							           		<form name="searchMember" id="searchMember" action="./logon" method="get">
												<input type="hidden" id="actionType" name="actionType" value="searchmember">
													
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
										
										
										
										<div>
											<form id="privFilterForm" name="privFilterForm" action="./logon" method="get">
												<span>
													<input type="hidden" id="actionType" name="actionType" value="filterPriv">
													<select id="privFilter" name="privFilter">
														<option value=""></option>
														<option value="-9" <%if(privFilter == -9){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PRIV0",locale) %></option>
														<option value="0" <%if(privFilter == 0){out.print("selected");} %>>Waiting for Verification</option>
														<option value="1" <%if(privFilter == 1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PRIV1",locale) %></option>
														<option value="2" <%if(privFilter == 2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PRIV2",locale) %></option>
													</select>
													
													<button class="btn btn-sm btn-inverse" id="filterBtn"><%=Resource.getString("ID_LABEL_FILTER",locale)%></button>
												</span>
											</form>
										</div>
										
										
										<div></div>
									
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
															<th class="center"><%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%></th>
															<th class="center">Account Number</th>
															<th class="center"><%=Resource.getString("ID_LABEL_LOGINTIMES",locale)%></th>
															<th class="center"></th>
															<th class="center"></th>
														</tr>
													</thead>
													
													<%
														logonData = new LogonDataModel();
														
														if(data != null && data.size() > 0){
															for(int i = 0; i < data.size(); i++){
																logonData = (LogonDataModel)data.get(i);
													%>
													<tbody>
														<tr>
															<td class="center"><%=logonData.getMid() %></td>
															<td class="center"><%=logonData.getEname() %></td>
															<td class="center"><%=logonData.getCname() %></td>
															<td class="center"><%=logonData.getEmail() %></td>
															<td class="center"><%=logonData.getPhone() %></td>
															<td class="center"><%=Resource.getString("ID_LABEL_PRIV"+logonData.getPrivilege(), locale)%></td>
															<td class="center"><% if(logonData.getPrivilege() == 2){out.print(logonData.getAccNo());} %></td>
															<td class="center"><%=logonData.getLoginTimes() %></td>
															<td class="center">
																<div class="visible-md visible-lg hidden-sm hidden-xs">
																	<a href="memberreport?userId=<%=logonData.getUserId() %>" target="_blank" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="View Report">
																		<span class="blue">
																			<i class="fa fa-bar-chart"></i>
																		</span>
																	</a>
																</div>
															</td>
															<td class="center">
																<div class="visible-md visible-lg hidden-sm hidden-xs">
																	<a onclick="editLogon('<%=logonData.getMid() %>')" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_EDIT",locale)%>">
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
																<input type="hidden" id="userId<%=logonData.getMid() %>" name="userId<%=logonData.getMid() %>" value="<%=logonData.getUserId() %>" />
																<input type="hidden" id="email<%=logonData.getMid() %>" name="email<%=logonData.getMid() %>" value="<%=logonData.getEmail() %>" />
																<input type="hidden" id="ename<%=logonData.getMid() %>" name="ename<%=logonData.getMid() %>" value="<%=logonData.getEname() %>" />
																<input type="hidden" id="cname<%=logonData.getMid() %>" name="cname<%=logonData.getMid() %>" value="<%=logonData.getCname() %>" />
																<input type="hidden" id="gender<%=logonData.getMid() %>" name="gender<%=logonData.getMid() %>" value="<%=logonData.getGender() %>" />
																<input type="hidden" id="nationality<%=logonData.getMid() %>" name="nationality<%=logonData.getMid() %>" value="<%=logonData.getNationality() %>" />
																<input type="hidden" id="phone<%=logonData.getMid() %>" name="phone<%=logonData.getMid() %>" value="<%=logonData.getPhone() %>" />
																<input type="hidden" id="dob<%=logonData.getMid() %>" name="dob<%=logonData.getMid() %>" value="<%=logonData.getDob() %>" />
																<input type="hidden" id="privilege<%=logonData.getMid() %>" name="privilege<%=logonData.getMid() %>" value="<%=logonData.getPrivilege() %>" />
																<input type="hidden" id="area<%=logonData.getMid() %>" name="area<%=logonData.getMid() %>" value="<%=logonData.getAccNo() %>" />
																<input type="hidden" id="accNo<%=logonData.getMid() %>" name="accNo<%=logonData.getMid() %>" value="<%=logonData.getAccNo() %>" />
																<input type="hidden" id="discPack<%=logonData.getMid() %>" name="discPack<%=logonData.getMid() %>" value="<%=logonData.getDiscPack() %>" />
																<input type="hidden" id="registerIP<%=logonData.getMid() %>" name="registerIP<%=logonData.getMid() %>" value="<%=logonData.getRegisterIP() %>" />
																<input type="hidden" id="lastIP<%=logonData.getMid() %>" name="lastIP<%=logonData.getMid() %>" value="<%=logonData.getLastIP() %>" />
																<input type="hidden" id="lastLoginDT<%=logonData.getMid() %>" name="lastLoginDT<%=logonData.getMid() %>" value="<%=logonData.getLastLoginDT() %>" />
																<input type="hidden" id="totalBooking<%=logonData.getMid() %>" name="totalBooking<%=logonData.getMid() %>" value="<%=logonData.getTotalBooking() %>" />
																<input type="hidden" id="createDT<%=logonData.getMid() %>" name="createDT<%=logonData.getMid() %>" value="<%=logonData.getCreateDT() %>" />
																<input type="hidden" id="modifyDT<%=logonData.getMid() %>" name="modifyDT<%=logonData.getMid() %>" value="<%=logonData.getModifyDT() %>" />
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
											int numPerPage = 20; //每頁顯示數量
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
													out.print("<li><a href=\"./cpmember?page="+prev10+"\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁												
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./cpmember?page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./cpmember?page="+(next10)+"\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
												}
												
												out.print("</ul>");
										   }
										%>
										
								</div>
								
								<div align="center">
									<% if( ((priv == 99)||(priv == 9)) && ((data != null && data.size() > 0)) ){ //有資料，然後是admin或account才能產生 %>
										<span>
											<button class="btn btn-lg btn-success" id="exportMember">
												<i class="ace-icon fa fa-file-excel-o"></i>
												Export Member List
											</button>
											<span id="loadingInvoice"></span>
										</span>
									<% } %>
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
			<div class="modal fade" id="editMember" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h2 class="modal-title" id=""><%=Resource.getString("ID_LABEL_MEMBERMAINTAINANCE",locale)%></h2>
						</div>
						<div class="modal-body">
							<div class="center"><img id="modalAvatar" class="cpAvatarStyle" /></div>
							
							<form id="memberForm" name="memberForm" class="reg-page" style="width:90%;margin:0px auto!important;" method="post" action="./logon">
								<input type="hidden" id="actionType" name="actionType" value="ajaxUpdateMember">						
								<input type="hidden" id="formValues" name="formValues">
								<input type="hidden" id="email_Exist" name="email_Exist">
								<input type="hidden" id="email_Current" name="email_Current" value="">
								<input type="hidden" id="userId" name="userId">
								
								<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DETAILS",locale)%></h3>
									
								<div class="space-4"></div>
			                    <label><%=Resource.getString("ID_LABEL_EMAIL",locale)%> <span class="red">*</span></label>
								<input id="email" name="email" type="text" class="form-control" maxlength="100" value="" onblur="checkExist('email')" readonly> 
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
								
								<label><%=Resource.getString("ID_LABEL_DOB",locale)%> <span class="red">*</span></label>
								<input id="dob" name="dob" type="text" class="form-control margin-bottom-20" size="15" maxlength="10" value="" />								
								
								<label><%=Resource.getString("ID_LABEL_NATIONALITY",locale)%> <span class="red">*</span></label>
								<select name="nationality" id="nationality" class="form-control margin-bottom-20">
									<option value="" <% if(logonData.getNationality().equals("")) out.print("selected"); %>></option>
									<option value="BN" <% if(logonData.getNationality().equals("BN")) out.print("selected"); %>>Brunei</option>
									<option value="MY" <% if(logonData.getNationality().equals("MY")) out.print("selected"); %>>Malaysia</option>
									<option value="SG" <% if(logonData.getNationality().equals("SG")) out.print("selected"); %>>Singapore</option>
									<option value=""> ----------------------- </option>
									<option value="AF" <% if(logonData.getNationality().equals("AF")) out.print("selected"); %>>Afghanistan</option>
									<option value="AL" <% if(logonData.getNationality().equals("AL")) out.print("selected"); %>>Albania</option>
									<option value="DZ" <% if(logonData.getNationality().equals("DZ")) out.print("selected"); %>>Algeria</option>
									<option value="AS" <% if(logonData.getNationality().equals("AS")) out.print("selected"); %>>American Samoa</option>
									<option value="AD" <% if(logonData.getNationality().equals("AD")) out.print("selected"); %>>Andorra</option>
									<option value="AO" <% if(logonData.getNationality().equals("AO")) out.print("selected"); %>>Angola</option>
									<option value="AI" <% if(logonData.getNationality().equals("AI")) out.print("selected"); %>>Anguilla</option>
									<option value="AR" <% if(logonData.getNationality().equals("AR")) out.print("selected"); %>>Argentina</option>
									<option value="AM" <% if(logonData.getNationality().equals("AM")) out.print("selected"); %>>Armenia</option>
									<option value="AW" <% if(logonData.getNationality().equals("AW")) out.print("selected"); %>>Aruba</option>
									<option value="AU" <% if(logonData.getNationality().equals("AU")) out.print("selected"); %>>Australia</option>
									<option value="AT" <% if(logonData.getNationality().equals("AT")) out.print("selected"); %>>Austria</option>
									<option value="AZ" <% if(logonData.getNationality().equals("AZ")) out.print("selected"); %>>Azerbaijan</option>
									<option value="BS" <% if(logonData.getNationality().equals("BS")) out.print("selected"); %>>Bahamas</option>
									<option value="BH" <% if(logonData.getNationality().equals("BH")) out.print("selected"); %>>Bahrain</option>
									<option value="BD" <% if(logonData.getNationality().equals("BD")) out.print("selected"); %>>Bangladesh</option>
									<option value="BB" <% if(logonData.getNationality().equals("BB")) out.print("selected"); %>>Barbados</option>
									<option value="BY" <% if(logonData.getNationality().equals("BY")) out.print("selected"); %>>Belarus</option>
									<option value="BE" <% if(logonData.getNationality().equals("BE")) out.print("selected"); %>>Belgium</option>
									<option value="BZ" <% if(logonData.getNationality().equals("BZ")) out.print("selected"); %>>Belize</option>
									<option value="BJ" <% if(logonData.getNationality().equals("BJ")) out.print("selected"); %>>Benin</option>
									<option value="BT" <% if(logonData.getNationality().equals("BT")) out.print("selected"); %>>Bhutan</option>
									<option value="BO" <% if(logonData.getNationality().equals("BO")) out.print("selected"); %>>Bolivia</option>
									<option value="BA" <% if(logonData.getNationality().equals("BA")) out.print("selected"); %>>Bosnia and Herzegovina</option>
									<option value="BW" <% if(logonData.getNationality().equals("BW")) out.print("selected"); %>>Botswana</option>
									<option value="BR" <% if(logonData.getNationality().equals("BR")) out.print("selected"); %>>Brazil</option>
									<option value="BG" <% if(logonData.getNationality().equals("BG")) out.print("selected"); %>>Bulgaria</option>
									<option value="BF" <% if(logonData.getNationality().equals("BF")) out.print("selected"); %>>Burkina Faso</option>
									<option value="BI" <% if(logonData.getNationality().equals("BI")) out.print("selected"); %>>Burundi</option>
									<option value="KH" <% if(logonData.getNationality().equals("KH")) out.print("selected"); %>>Cambodia</option>
									<option value="CM" <% if(logonData.getNationality().equals("CM")) out.print("selected"); %>>Cameroon</option>
									<option value="CA" <% if(logonData.getNationality().equals("CA")) out.print("selected"); %>>Canada</option>
									<option value="CV" <% if(logonData.getNationality().equals("CV")) out.print("selected"); %>>Cape Verde</option>
									<option value="CF" <% if(logonData.getNationality().equals("CF")) out.print("selected"); %>>Central African Republic</option>
									<option value="TD" <% if(logonData.getNationality().equals("TD")) out.print("selected"); %>>Chad</option>
									<option value="CL" <% if(logonData.getNationality().equals("CL")) out.print("selected"); %>>Chile</option>
									<option value="CN" <% if(logonData.getNationality().equals("CN")) out.print("selected"); %>>China</option>
									<option value="CO" <% if(logonData.getNationality().equals("CO")) out.print("selected"); %>>Colombia</option>
									<option value="KM" <% if(logonData.getNationality().equals("KM")) out.print("selected"); %>>Comoros</option>
									<option value="CR" <% if(logonData.getNationality().equals("CR")) out.print("selected"); %>>Costa Rica</option>
									<option value="HR" <% if(logonData.getNationality().equals("HR")) out.print("selected"); %>>Croatia</option>
									<option value="CU" <% if(logonData.getNationality().equals("CU")) out.print("selected"); %>>Cuba</option>
									<option value="CY" <% if(logonData.getNationality().equals("CY")) out.print("selected"); %>>Cyprus</option>
									<option value="CZ" <% if(logonData.getNationality().equals("CZ")) out.print("selected"); %>>Czech Republic</option>
									<option value="CD" <% if(logonData.getNationality().equals("CD")) out.print("selected"); %>>Democratic Republic of Congo</option>
									<option value="DK" <% if(logonData.getNationality().equals("DK")) out.print("selected"); %>>Denmark</option>
									<option value="DJ" <% if(logonData.getNationality().equals("DJ")) out.print("selected"); %>>Djibouti</option>
									<option value="DM" <% if(logonData.getNationality().equals("DM")) out.print("selected"); %>>Dominica</option>
									<option value="DO" <% if(logonData.getNationality().equals("DO")) out.print("selected"); %>>Dominican Republic</option>
									<option value="EC" <% if(logonData.getNationality().equals("EC")) out.print("selected"); %>>Ecuador</option>
									<option value="EG" <% if(logonData.getNationality().equals("EG")) out.print("selected"); %>>Egypt</option>
									<option value="SV" <% if(logonData.getNationality().equals("SV")) out.print("selected"); %>>El Salvador</option>
									<option value="GQ" <% if(logonData.getNationality().equals("GQ")) out.print("selected"); %>>Equatorial Guinea</option>
									<option value="ER" <% if(logonData.getNationality().equals("ER")) out.print("selected"); %>>Eritrea</option>
									<option value="EE" <% if(logonData.getNationality().equals("EE")) out.print("selected"); %>>Estonia</option>
									<option value="ET" <% if(logonData.getNationality().equals("ET")) out.print("selected"); %>>Ethiopia</option>
									<option value="FI" <% if(logonData.getNationality().equals("FI")) out.print("selected"); %>>Finland</option>
									<option value="FR" <% if(logonData.getNationality().equals("FR")) out.print("selected"); %>>France</option>
									<option value="PF" <% if(logonData.getNationality().equals("PF")) out.print("selected"); %>>French Polynesia</option>
									<option value="GA" <% if(logonData.getNationality().equals("GA")) out.print("selected"); %>>Gabon</option>
									<option value="GM" <% if(logonData.getNationality().equals("GM")) out.print("selected"); %>>Gambia</option>
									<option value="GE" <% if(logonData.getNationality().equals("GE")) out.print("selected"); %>>Georgia</option>
									<option value="DE" <% if(logonData.getNationality().equals("DE")) out.print("selected"); %>>Germany</option>
									<option value="GH" <% if(logonData.getNationality().equals("GH")) out.print("selected"); %>>Ghana</option>
									<option value="GI" <% if(logonData.getNationality().equals("GI")) out.print("selected"); %>>Gibraltar</option>
									<option value="GR" <% if(logonData.getNationality().equals("GR")) out.print("selected"); %>>Greece</option>
									<option value="GL" <% if(logonData.getNationality().equals("GL")) out.print("selected"); %>>Greenland</option>
									<option value="GD" <% if(logonData.getNationality().equals("GD")) out.print("selected"); %>>Grenada</option>
									<option value="GU" <% if(logonData.getNationality().equals("GU")) out.print("selected"); %>>Guam</option>
									<option value="GT" <% if(logonData.getNationality().equals("GT")) out.print("selected"); %>>Guatemala</option>
									<option value="GN" <% if(logonData.getNationality().equals("GN")) out.print("selected"); %>>Guinea</option>
									<option value="GW" <% if(logonData.getNationality().equals("GW")) out.print("selected"); %>>Guinea-Bissau</option>
									<option value="GY" <% if(logonData.getNationality().equals("GY")) out.print("selected"); %>>Guyana</option>
									<option value="HT" <% if(logonData.getNationality().equals("HT")) out.print("selected"); %>>Haiti</option>
									<option value="HN" <% if(logonData.getNationality().equals("HN")) out.print("selected"); %>>Honduras</option>
									<option value="HK" <% if(logonData.getNationality().equals("HK")) out.print("selected"); %>>Hong Kong</option>
									<option value="HU" <% if(logonData.getNationality().equals("HU")) out.print("selected"); %>>Hungary</option>
									<option value="IS" <% if(logonData.getNationality().equals("IS")) out.print("selected"); %>>Iceland</option>
									<option value="IN" <% if(logonData.getNationality().equals("IN")) out.print("selected"); %>>India</option>
									<option value="ID" <% if(logonData.getNationality().equals("ID")) out.print("selected"); %>>Indonesia</option>
									<option value="IR" <% if(logonData.getNationality().equals("IR")) out.print("selected"); %>>Iran</option>
									<option value="IQ" <% if(logonData.getNationality().equals("IQ")) out.print("selected"); %>>Iraq</option>
									<option value="IE" <% if(logonData.getNationality().equals("IE")) out.print("selected"); %>>Ireland</option>
									<option value="IL" <% if(logonData.getNationality().equals("IL")) out.print("selected"); %>>Israel</option>
									<option value="IT" <% if(logonData.getNationality().equals("IT")) out.print("selected"); %>>Italy</option>
									<option value="CI" <% if(logonData.getNationality().equals("CI")) out.print("selected"); %>>Ivory Coast</option>
									<option value="JM" <% if(logonData.getNationality().equals("JM")) out.print("selected"); %>>Jamaica</option>
									<option value="JP" <% if(logonData.getNationality().equals("JP")) out.print("selected"); %>>Japan</option>
									<option value="JO" <% if(logonData.getNationality().equals("JO")) out.print("selected"); %>>Jordan</option>
									<option value="KZ" <% if(logonData.getNationality().equals("KZ")) out.print("selected"); %>>Kazakhstan</option>
									<option value="KE" <% if(logonData.getNationality().equals("KE")) out.print("selected"); %>>Kenya</option>
									<option value="KI" <% if(logonData.getNationality().equals("KI")) out.print("selected"); %>>Kiribati</option>
									<option value="KW" <% if(logonData.getNationality().equals("KW")) out.print("selected"); %>>Kuwait</option>
									<option value="KG" <% if(logonData.getNationality().equals("KG")) out.print("selected"); %>>Kyrgyzstan</option>
									<option value="LA" <% if(logonData.getNationality().equals("LA")) out.print("selected"); %>>Laos</option>
									<option value="LV" <% if(logonData.getNationality().equals("LV")) out.print("selected"); %>>Latvia</option>
									<option value="LB" <% if(logonData.getNationality().equals("LB")) out.print("selected"); %>>Lebanon</option>
									<option value="LS" <% if(logonData.getNationality().equals("LS")) out.print("selected"); %>>Lesotho</option>
									<option value="LR" <% if(logonData.getNationality().equals("LR")) out.print("selected"); %>>Liberia</option>
									<option value="LY" <% if(logonData.getNationality().equals("LY")) out.print("selected"); %>>Libya</option>
									<option value="LI" <% if(logonData.getNationality().equals("LI")) out.print("selected"); %>>Liechtenstein</option>
									<option value="LT" <% if(logonData.getNationality().equals("LT")) out.print("selected"); %>>Lithuania</option>
									<option value="LU" <% if(logonData.getNationality().equals("LU")) out.print("selected"); %>>Luxembourg</option>
									<option value="MO" <% if(logonData.getNationality().equals("MO")) out.print("selected"); %>>Macau</option>
									<option value="MK" <% if(logonData.getNationality().equals("MK")) out.print("selected"); %>>Macedonia</option>
									<option value="MG" <% if(logonData.getNationality().equals("MG")) out.print("selected"); %>>Madagascar</option>
									<option value="MW" <% if(logonData.getNationality().equals("MW")) out.print("selected"); %>>Malawi</option>
									<option value="MV" <% if(logonData.getNationality().equals("MV")) out.print("selected"); %>>Maldives</option>
									<option value="ML" <% if(logonData.getNationality().equals("ML")) out.print("selected"); %>>Mali</option>
									<option value="MT" <% if(logonData.getNationality().equals("MT")) out.print("selected"); %>>Malta</option>
									<option value="MH" <% if(logonData.getNationality().equals("MH")) out.print("selected"); %>>Marshall Islands</option>
									<option value="MR" <% if(logonData.getNationality().equals("MR")) out.print("selected"); %>>Mauritania</option>
									<option value="MU" <% if(logonData.getNationality().equals("MU")) out.print("selected"); %>>Mauritius</option>
									<option value="YT" <% if(logonData.getNationality().equals("YT")) out.print("selected"); %>>Mayotte</option>
									<option value="MX" <% if(logonData.getNationality().equals("MX")) out.print("selected"); %>>Mexico</option>
									<option value="MD" <% if(logonData.getNationality().equals("MD")) out.print("selected"); %>>Moldova</option>
									<option value="MC" <% if(logonData.getNationality().equals("MC")) out.print("selected"); %>>Monaco</option>
									<option value="MN" <% if(logonData.getNationality().equals("MN")) out.print("selected"); %>>Mongolia</option>
									<option value="ME" <% if(logonData.getNationality().equals("ME")) out.print("selected"); %>>Montenegro</option>
									<option value="MS" <% if(logonData.getNationality().equals("MS")) out.print("selected"); %>>Montserrat</option>
									<option value="MA" <% if(logonData.getNationality().equals("MA")) out.print("selected"); %>>Morocco</option>
									<option value="MM" <% if(logonData.getNationality().equals("MM")) out.print("selected"); %>>Myanmar</option>
									<option value="NA" <% if(logonData.getNationality().equals("NA")) out.print("selected"); %>>Namibia</option>
									<option value="NR" <% if(logonData.getNationality().equals("NR")) out.print("selected"); %>>Nauru</option>
									<option value="NP" <% if(logonData.getNationality().equals("NP")) out.print("selected"); %>>Nepal</option>
									<option value="NL" <% if(logonData.getNationality().equals("NL")) out.print("selected"); %>>Netherlands</option>
									<option value="AN" <% if(logonData.getNationality().equals("AN")) out.print("selected"); %>>Netherlands Antilles</option>
									<option value="NC" <% if(logonData.getNationality().equals("NC")) out.print("selected"); %>>New Caledonia</option>
									<option value="NZ" <% if(logonData.getNationality().equals("NZ")) out.print("selected"); %>>New Zealand</option>
									<option value="NI" <% if(logonData.getNationality().equals("NI")) out.print("selected"); %>>Nicaragua</option>
									<option value="NE" <% if(logonData.getNationality().equals("NE")) out.print("selected"); %>>Niger</option>
									<option value="NG" <% if(logonData.getNationality().equals("NG")) out.print("selected"); %>>Nigeria</option>
									<option value="NU" <% if(logonData.getNationality().equals("NU")) out.print("selected"); %>>Niue</option>
									<option value="KP" <% if(logonData.getNationality().equals("KP")) out.print("selected"); %>>North Korea</option>
									<option value="NO" <% if(logonData.getNationality().equals("NO")) out.print("selected"); %>>Norway</option>
									<option value="OM" <% if(logonData.getNationality().equals("OM")) out.print("selected"); %>>Oman</option>
									<option value="PK" <% if(logonData.getNationality().equals("PK")) out.print("selected"); %>>Pakistan</option>
									<option value="PW" <% if(logonData.getNationality().equals("PW")) out.print("selected"); %>>Palau</option>
									<option value="PA" <% if(logonData.getNationality().equals("PA")) out.print("selected"); %>>Panama</option>
									<option value="PG" <% if(logonData.getNationality().equals("PG")) out.print("selected"); %>>Papua New Guinea</option>
									<option value="PY" <% if(logonData.getNationality().equals("PY")) out.print("selected"); %>>Paraguay</option>
									<option value="PE" <% if(logonData.getNationality().equals("PE")) out.print("selected"); %>>Peru</option>
									<option value="PH" <% if(logonData.getNationality().equals("PH")) out.print("selected"); %>>Philippines</option>
									<option value="PN" <% if(logonData.getNationality().equals("PN")) out.print("selected"); %>>Pitcairn Islands</option>
									<option value="PL" <% if(logonData.getNationality().equals("PL")) out.print("selected"); %>>Poland</option>
									<option value="PT" <% if(logonData.getNationality().equals("PT")) out.print("selected"); %>>Portugal</option>
									<option value="PR" <% if(logonData.getNationality().equals("PR")) out.print("selected"); %>>Puerto Rico</option>
									<option value="QA" <% if(logonData.getNationality().equals("QA")) out.print("selected"); %>>Qatar</option>
									<option value="RO" <% if(logonData.getNationality().equals("RO")) out.print("selected"); %>>Romania</option>
									<option value="RU" <% if(logonData.getNationality().equals("RU")) out.print("selected"); %>>Russia</option>
									<option value="RW" <% if(logonData.getNationality().equals("RW")) out.print("selected"); %>>Rwanda</option>
									<option value="SH" <% if(logonData.getNationality().equals("SH")) out.print("selected"); %>>Saint Helena and Dependencies</option>
									<option value="KN" <% if(logonData.getNationality().equals("KN")) out.print("selected"); %>>Saint Kitts and Nevis</option>
									<option value="LC" <% if(logonData.getNationality().equals("LC")) out.print("selected"); %>>Saint Lucia</option>
									<option value="PM" <% if(logonData.getNationality().equals("PM")) out.print("selected"); %>>Saint Pierre and Miquelon</option>
									<option value="VC" <% if(logonData.getNationality().equals("VC")) out.print("selected"); %>>Saint Vincent and the Grenadines</option>
									<option value="WS" <% if(logonData.getNationality().equals("WS")) out.print("selected"); %>>Samoa</option>
									<option value="SM" <% if(logonData.getNationality().equals("SM")) out.print("selected"); %>>San Marino</option>
									<option value="ST" <% if(logonData.getNationality().equals("ST")) out.print("selected"); %>>Sao Tome and Principe</option>
									<option value="SA" <% if(logonData.getNationality().equals("SA")) out.print("selected"); %>>Saudi Arabia</option>
									<option value="SN" <% if(logonData.getNationality().equals("SN")) out.print("selected"); %>>Senegal</option>
									<option value="RS" <% if(logonData.getNationality().equals("RS")) out.print("selected"); %>>Serbia</option>
									<option value="SC" <% if(logonData.getNationality().equals("SC")) out.print("selected"); %>>Seychelles</option>
									<option value="SL" <% if(logonData.getNationality().equals("SL")) out.print("selected"); %>>Sierra Leone</option>
									<option value="SK" <% if(logonData.getNationality().equals("SK")) out.print("selected"); %>>Slovakia</option>
									<option value="SI" <% if(logonData.getNationality().equals("SI")) out.print("selected"); %>>Slovenia</option>
									<option value="SB" <% if(logonData.getNationality().equals("SB")) out.print("selected"); %>>Solomon Islands</option>
									<option value="ZA" <% if(logonData.getNationality().equals("ZA")) out.print("selected"); %>>South Africa</option>
									<option value="KR" <% if(logonData.getNationality().equals("KR")) out.print("selected"); %>>South Korea</option>
									<option value="ES" <% if(logonData.getNationality().equals("ES")) out.print("selected"); %>>Spain</option>
									<option value="LK" <% if(logonData.getNationality().equals("LK")) out.print("selected"); %>>Sri Lanka</option>
									<option value="SD" <% if(logonData.getNationality().equals("SD")) out.print("selected"); %>>Sudan</option>
									<option value="SR" <% if(logonData.getNationality().equals("SR")) out.print("selected"); %>>Suriname</option>
									<option value="SZ" <% if(logonData.getNationality().equals("SZ")) out.print("selected"); %>>Swaziland</option>
									<option value="SE" <% if(logonData.getNationality().equals("SE")) out.print("selected"); %>>Sweden</option>
									<option value="CH" <% if(logonData.getNationality().equals("CH")) out.print("selected"); %>>Switzerland</option>
									<option value="SY" <% if(logonData.getNationality().equals("SY")) out.print("selected"); %>>Syria</option>
									<option value="TW" <% if(logonData.getNationality().equals("TW")) out.print("selected"); %>>Taiwan</option>
									<option value="TJ" <% if(logonData.getNationality().equals("TJ")) out.print("selected"); %>>Tajikistan</option>
									<option value="TZ" <% if(logonData.getNationality().equals("TZ")) out.print("selected"); %>>Tanzania</option>
									<option value="TH" <% if(logonData.getNationality().equals("TH")) out.print("selected"); %>>Thailand</option>
									<option value="TG" <% if(logonData.getNationality().equals("TG")) out.print("selected"); %>>Togo</option>
									<option value="TK" <% if(logonData.getNationality().equals("TK")) out.print("selected"); %>>Tokelau</option>
									<option value="TO" <% if(logonData.getNationality().equals("TO")) out.print("selected"); %>>Tonga</option>
									<option value="TN" <% if(logonData.getNationality().equals("TN")) out.print("selected"); %>>Tunisia</option>
									<option value="TR" <% if(logonData.getNationality().equals("TR")) out.print("selected"); %>>Turkey</option>
									<option value="TM" <% if(logonData.getNationality().equals("TM")) out.print("selected"); %>>Turkmenistan</option>
									<option value="TV" <% if(logonData.getNationality().equals("TV")) out.print("selected"); %>>Tuvalu</option>
									<option value="UG" <% if(logonData.getNationality().equals("UG")) out.print("selected"); %>>Uganda</option>
									<option value="UA" <% if(logonData.getNationality().equals("UA")) out.print("selected"); %>>Ukraine</option>
									<option value="AE" <% if(logonData.getNationality().equals("AE")) out.print("selected"); %>>United Arab Emirates</option>
									<option value="UK" <% if(logonData.getNationality().equals("UK")) out.print("selected"); %>>United Kingdom</option>
									<option value="US" <% if(logonData.getNationality().equals("US")) out.print("selected"); %>>United States</option>
									<option value="UY" <% if(logonData.getNationality().equals("UY")) out.print("selected"); %>>Uruguay</option>
									<option value="UZ" <% if(logonData.getNationality().equals("UZ")) out.print("selected"); %>>Uzbekistan</option>
									<option value="UV" <% if(logonData.getNationality().equals("UV")) out.print("selected"); %>>Vanuatu</option>
									<option value="VE" <% if(logonData.getNationality().equals("VE")) out.print("selected"); %>>Venezuela</option>
									<option value="VN" <% if(logonData.getNationality().equals("VN")) out.print("selected"); %>>Vietnam</option>
									<option value="WF" <% if(logonData.getNationality().equals("WF")) out.print("selected"); %>>Wallis and Futuna</option>
									<option value="YE" <% if(logonData.getNationality().equals("YE")) out.print("selected"); %>>Yemen</option>
									<option value="ZM" <% if(logonData.getNationality().equals("ZM")) out.print("selected"); %>>Zambia</option>
									<option value="ZW" <% if(logonData.getNationality().equals("ZW")) out.print("selected"); %>>Zimbabwe</option>
								</select>
								
								<div class="space-24"></div>
								
								<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%></h3>
								<div class="space-4"></div>
								<select name="privilege" id="privilege" class="form-control margin-bottom-20" onchange="changePrivilegeCheck(this.form.privilege)">
									<option value="-9"><%=Resource.getString("ID_LABEL_PRIV-9",locale)%></option>
									<option value="0"><%=Resource.getString("ID_LABEL_PRIV0",locale)%></option>
									<option value="1"><%=Resource.getString("ID_LABEL_PRIV1",locale)%></option>
									<option value="2"><%=Resource.getString("ID_LABEL_PRIV2",locale)%></option>
								</select>
															
								<span id="creditTermArea">
									<label> <%=Resource.getString("ID_LABEL_AREA",locale)%> </label>
									<select id="area" name="area" class="form-control margin-bottom-20">
										<option value=""></option>
										<% 
			                                if(areaData != null && !areaData.isEmpty()){
												for(int i = 0; i < areaData.size(); i ++){
													aData = areaData.get(i);
		                                %>
											<option value="<%=aData.getCode() %>"><%=aData.getName_enUS() %></option>
										<% } } %>
									</select>
									
									<span id="accountNumber" >
										<label><%=Resource.getString("ID_LABEL_ACCOUNTNUMBER",locale)%></label>
										<input id="accNo" name="accNo" class="form-control margin-bottom-20" size="15" value="" readonly disabled/>
									</span>
									
									<label><%=Resource.getString("ID_LABEL_PACKAGE",locale)%> </label>
									<select name="discPack" id="discPack" class="form-control margin-bottom-20">
										<option value=""></option>
										<option value="A"><%=Resource.getString("ID_LABEL_PACKAGE",locale)%> A</option>
										<option value="B"><%=Resource.getString("ID_LABEL_PACKAGE",locale)%> B</option>
										<option value="C"><%=Resource.getString("ID_LABEL_PACKAGE",locale)%> C</option>
										<option value="D"><%=Resource.getString("ID_LABEL_PACKAGE",locale)%> D</option>
									</select>
								
								</span>
								
								<div class="space-24"></div>
								
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
				
				$("#editMember #updateMember").click(function(){
					
					var userId = $("#editMember #userId").val();
					var email = $("#editMember #email").val();
					var email_Current = $("#editMember #email_Current").val();
					var ename = $("#editMember #ename").val();
					var cname = $("#editMember #cname").val();
					var gender = $("#editMember #gender").val();
					var phone = $("#editMember #phone").val();
					var dob = $("#editMember #dob").val();
					var nationality = $("#editMember #nationality").val();
					var privilege = $("#editMember #privilege").val();
					var area = $("#editMember #area").val();
					var accNo = $("#editMember #accNo").val();
					var discPack = $("#editMember #discPack").val();
			   		$("#editMember #loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#editMember #cancel").hide();
					$("#editMember #updateMember").hide();
					$.ajax({
						type: 'POST',
						url: './logon',
						data:{    
							lang: "<%=locale %>",
							actionType:"ajaxUpdateMember",
							userId: userId,
							email: email,
							email_Current: email_Current,
							ename: ename,
							cname: cname,
							gender: gender,
							phone: phone,
							dob: dob,
							nationality: nationality,
							privilege: privilege,
							area: area,
							accNo: accNo,
							discPack: discPack
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "OK"){
									$("#editMember #loading").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\"><%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%></span>&nbsp;&nbsp;&nbsp;");
									$("#editMember #closeBtn").show();
									$("#editMember #updateMember").show();
								} else {
									$("#editMember #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\"><%=Resource.getString("ID_MSG_UPDATEFAILED",locale) + Resource.getString("ID_LABEL_COLON",locale) %>" + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#editMember #closeBtn").hide();
									$("#editMember #cancel").show();
									$("#editMember #updateMember").show();
								}
							}
						} 
					});
				});
				
				
				$("#editMember #closeBtn").click(function(){
					location.reload();					
				});
				
			});
			
			$(function() {
				$( "#filterBtn" ).click(function() { $( "#privFilterForm" ).submit(); return false; });
			});
			
			$(function() {
				$( "#exportMember" ).click(function() {
					
					var privFilter = $( "#privFilter" ).val();

					$( "#loadingInvoice" ).html("<br/><br/><%=Resource.getString("ID_LABEL_GENERATING",locale)%> <i class=\"ace-icon fa fa-circle-o-notch fa-spin icon-on-right bigger-110\"></i><br/><br/><br/>");

					$(function() {
						$.ajax({
							url: './logon', 
							data: "actionType=exportMemberList&privFilter="+privFilter,
							success: function(response) {

								if(response.indexOf(".xlsx")!=-1) {
									$( "#loadingInvoice" ).html("<br/><br/><a href='https://s3-ap-southeast-1.amazonaws.com/iposb/memberlist/"+response+"' target='_blank'><%=Resource.getString("ID_LABEL_GENERATEDEXCEL",locale)%> <img src='<%=resPath %>/assets/L10N/common/img/excel.png' border='0' /></a><br/><br/><br/>");
								} else if(response=="NoConnection") {
									$( "#loadingInvoice" ).html("<br/><%=Resource.getString("ID_LABEL_NOCONNECTION",locale)%>");
								} else if(response=="noData") {
									$( "#loadingInvoice" ).html("<br/><%=Resource.getString("ID_MSG_NODATA",locale)%>");
								} else {
									$( "#loadingInvoice" ).html("<br/>"+response);
								}
								
							}
						});
					});
					
				});
			});
			
			function editLogon(mid){
				
				$(function(){
					var userId = $("#userId" + mid).val();
					var email = $("#email" + mid).val();
					var ename = $("#ename" + mid).val();
					var cname = $("#cname" + mid).val();
					var gender = $("#gender" + mid).val();
					var phone = $("#phone" + mid).val();
					var dob = $("#dob" + mid).val();
					var nationality = $("#nationality" + mid).val();
					var privilege = $("#privilege" + mid).val();
					
					if ( $("#area" + mid).val().substring(0,4) == "KNBT" ) {
						var area = $("#area" + mid).val().substring(0,4);
					} else {
						var area = $("#area" + mid).val().substring(0,3);
					}
					
					var accNo = $("#accNo" + mid).val();
					var discPack = $("#discPack" + mid).val();
					var registerIP = $("#registerIP" + mid).val();
					var lastIP = $("#lastIP" + mid).val();
					var lastLoginDT = $("#lastLoginDT" + mid).val();
					var totalBooking = $("#totalBooking" + mid).val();
					var createDT = $("#createDT" + mid).val();
					var modifyDT = $("#modifyDT" + mid).val();
					
					var avatarDT = '';
					if(modifyDT.trim() != '') avatarDT = modifyDT.trim().replace(' ', '_');
					var avatarSrc = 'https://s3-ap-southeast-1.amazonaws.com/iposb/images/avatar/member/'+mid+'/avatar.jpg?'+avatarDT;
					
					$("#editMember #userId").val(userId);
					$("#editMember #email").val(email);
					$("#editMember #email_Current").val(email);
					$("#editMember #ename").val(ename);
					$("#editMember #cname").val(cname);
					$("#editMember #gender option[value='" + gender + "']").prop("selected", true);
					$("#editMember #phone").val(phone);
					$("#editMember #dob").val(dob);
					$("#editMember #nationality").val(nationality);
					$("#editMember #privilege").val(privilege);
					
					if ( $("#accNo" + mid).val() != "" ) {
						$("#editMember #area").val(area).prop('disabled', true);
						$("#editMember #area").val(area).prop('readonly', true);
					} else {
						$("#editMember #area").val(area).prop('disabled', false);
						$("#editMember #area").val(area).prop('readonly', false);
					}
					
					$("#editMember #accNo").val(accNo);
					$("#editMember #discPack").val(discPack);
					$("#editMember #registerIP").html(registerIP);
					$("#editMember #lastIP").html(lastIP);
					$("#editMember #lastLoginDT").html(lastLoginDT);
					$("#editMember #totalBooking").html(totalBooking);
					$("#editMember #createDT").html(createDT);
					$("#editMember #modifyDT").html(modifyDT);
					
					testImage(avatarSrc).then(
						function fulfilled(img) {
						   	$("#modalAvatar").attr("src", avatarSrc);
					    },function rejected() {
					    	$("#modalAvatar").attr("src", "assets/img/noavatar.jpg");
					    }
					);
				   	
					$("#editMember").modal();
				});
				
			};
			
			function changePrivilegeCheck(obj){
				
				var privilege = obj.value;
				
				if(privilege == 2){
					$("#creditTermArea").show();
				} else {
					$("#creditTermArea").hide();					
				}
			};
			
			
			
		</script>
</g:compress>	
		
		
	</body>
</html>
