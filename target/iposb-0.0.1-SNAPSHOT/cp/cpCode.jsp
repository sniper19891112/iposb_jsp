<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,com.iposb.code.*,java.util.HashMap,java.util.ArrayList"%>
<%@include file="../include.jsp" %>
<jsp:useBean id="pager" class="com.iposb.page.Paging" scope="session"></jsp:useBean>

<%
	String errmsg = "";
	
	ArrayList data = (ArrayList)request.getAttribute(CodeController.OBJECTDATA);
	CodeDataModel codeData = new CodeDataModel();
	if(data != null && data.size() > 0){
		codeData = (CodeDataModel)data.get(0);
		errmsg = codeData.getErrmsg();
				
	}
	
	//String pageNo = request.getParameter("page")==null ? "1" : request.getParameter("page").toString();
	String searchcode = request.getParameter("searchcode")==null ? "" : new String(request.getParameter("searchcode").getBytes("ISO-8859-1"), "UTF-8");
	boolean isSearch = request.getParameter("actionType")==null ? false : request.getParameter("actionType").toString().equals("searchpromo") ? true : false;
	
	//shown & not shown
	boolean addEdit = false;
	boolean list = true;
//	String actionType = request.getParameter("actionType");
//	if(actionType != null && (actionType.equals("dbcode_insert") || actionType.equals("dbcode_update"))){
//		addEdit = true;
//		list = false;
//	} else {
//		addEdit = false;
//		list = true;
//	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>Promotional Code</title>
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
								<a href="/"><%=Resource.getString("ID_LABEL_HOME",locale)%></a>
							</li>
							<li><a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li>
							<% if(list){ 
									out.print("<li class=\"active\">"+"Promotional Code"+"</li>");
							   } else {
									out.print("<li><a href=\"./cpCode\">"+"Promotional Code"+"</a></li>"); 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_EDIT",locale)+"</li>"); 
							   } 
							%>
							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
								<div class="row">
									<div class="col-xs-12 col-sm-8">
										<a onclick="editCode()" class="btn btn-primary" id="addCodeBtn">
											<i class="fa fa-plus"></i>
											Add New Code
										</a>
									</div>
									
									
									<div class="col-xs-12 col-sm-4">
						           		<form name="searchPromo" id="searchPromo" action="./code" method="get">
											<input type="hidden" id="actionType" name="actionType" value="searchpromo">
												
											<div class="input-group">
												<input class="form-control search-query" id="searchcode" name="searchcode" maxlength="10" value="<%=searchcode %>" placeholder="Code" type="text">
												<span class="input-group-btn">
													<button type="submit" class="btn btn-purple btn-sm">
														<%=Resource.getString("ID_LABEL_SEARCH",locale)%>
														<i class="icon-search icon-on-right bigger-110"></i>
													</button>
												</span>
											</div>
										</form>
									</div>
									
									<div class="space-4"></div>
								
				           		</div>
				           		
								<div class="row">
									<div class="col-xs-12">
									
										<div class="table-responsive">
											<table id="code-table" class="table table-striped table-bordered table-hover">
												<thead>
													<tr>
														<th class="center col-xs-1">No.</th>
														<th class="center">Code</th>
														<th class="center">Discount</th>
														<th class="center">Discount type</th>
														<th class="center">Allow Times</th>
														<th class="center">Used</th>
														<th class="center">Period (Start)</th>
														<th class="center">Period (End)</th>
														<th class="center">is Allow</th>
														<th class="center"></th>
													</tr>
												</thead>

												<tbody>
													<%
														if(data != null && data.size() > 0){
															String isAllow = "";
															String DiscountType = "";
															for(int i = 0; i < data.size(); i++){
																codeData = (CodeDataModel)data.get(i);
																if(codeData.getIsAllow() == 1){
																	isAllow = "Yes";
																} else {
																	isAllow = "No";
																}
																
																if(codeData.getDiscountType() == 0){
																	DiscountType = "MYR";
																} else {
																	DiscountType = "%";
																}
													%>
													<tr>
														<td align="center"><%=codeData.getCid() %>.</td>
														<td align="center"><%=codeData.getCode() %></td>
														<td align="center"><%=codeData.getDiscount() %></td>
														<td align="center"><%=DiscountType %></td>
														<td align="center"><%=codeData.getAllowTimes()%></td>
														<td align="center"><%=codeData.getUsed()%></td>
														<td align="center"><%=codeData.getPeriodStart()%></td>
														<td align="center"><%=codeData.getPeriodEnd()%></td>
														<td align="center"><%=isAllow %></td>
														<td class="center">
															<div class="visible-md visible-lg hidden-sm hidden-xs">
																<a onclick="editCode('<%=codeData.getCode() %>')" style="cursor:pointer;" class="tooltip-success" data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_EDIT",locale)%>">
																	<span class="green">
																		<i class="icon-edit bigger-120"></i>
																	</span>
																</a>
															</div>
															<input type="hidden" id="code<%=codeData.getCode() %>" name="code<%=codeData.getCode() %>" value="<%=codeData.getCode() %>" />
															<input type="hidden" id="discount<%=codeData.getCode() %>" name="discount<%=codeData.getCode() %>" value="<%=codeData.getDiscount() %>" />
															<input type="hidden" id="allowTimes<%=codeData.getCode() %>" name="allowTimes<%=codeData.getCode() %>" value="<%=codeData.getAllowTimes() %>" />
															<input type="hidden" id="used<%=codeData.getCode() %>" name="used<%=codeData.getCode() %>" value="<%=codeData.getUsed() %>" />
															<input type="hidden" id="periodStart<%=codeData.getCode() %>" name="periodStart<%=codeData.getCode() %>" value="<%=codeData.getPeriodStart() %>" />
															<input type="hidden" id="periodEnd<%=codeData.getCode() %>" name="periodEnd<%=codeData.getCode() %>" value="<%=codeData.getPeriodEnd() %>" />
															<input type="hidden" id="createDT<%=codeData.getCode() %>" name="createDT<%=codeData.getCode() %>" value="<%=codeData.getCreateDT() %>" />
															<input type="hidden" id="lastUsedDT<%=codeData.getCode() %>" name="lastUsedDT<%=codeData.getCode() %>" value="<%=codeData.getLastUsedDT() %>" />
															<input type="hidden" id="usedToBook<%=codeData.getCode() %>" name="usedToBook<%=codeData.getCode() %>" value="<%=codeData.getUsedToBook() %>" />
															<input type="hidden" id="remark<%=codeData.getCode() %>" name="remark<%=codeData.getCode() %>" value="<%=codeData.getRemark() %>" />
															<input type="hidden" id="isAllow<%=codeData.getCode() %>" name="isAllow<%=codeData.getCode() %>" value="<%=codeData.getIsAllow() %>" />
															<input type="hidden" id="discountType<%=codeData.getCode() %>" name="discountType<%=codeData.getCode() %>" value="<%=codeData.getDiscountType() %>" />
														</td>
													</tr>
													<% }} %>
												</tbody>
											</table>
				
										</div><!-- /.table-responsive -->
									</div><!-- /span -->
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
											boolean isNum = CodeController.isNumeric(p);
											
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

										
											total = codeData.getTotal();
											pager.setData(total);//设置数据
											int []range = pager.getRange(numPerPage);//获得页码呈现的区间
											if(pager.getMaxPage()>1) {//要呈现的页面超过1页，需要分页呈现
												out.print("<ul class=\"pagination\">");
													
												//上10頁按鍵
												if(thisPage > 1){
													out.print("<li><a href=\"./cpCode?page="+prev10+"\" title=\""+Resource.getString("ID_LABEL_PREV10PAGE",locale)+"\"><i class=\"icon-double-angle-left\"></i></a></li>");
												} else { //本頁
													out.print("<li class=\"disabled\"><a><i class=\"icon-double-angle-left\"></i></a></li>");
												}
												
												//中間10頁												
												for(int i=this10start; i<this10start+10; i++){
													if(i<=pager.getMaxPage()){
														if(thisPage==i){//當頁
															out.print("<li class=\"active\"><a>"+i+"</a></li>"); //當頁則不顯示連結
														} else {
															out.print("<li><a href=\"./cpCode?page="+i+"\">"+i+"</a></li>");
														}
													}
												}
												
												//下10頁按鍵
												if(thisPage < pager.getMaxPage()){
													out.print("<li><a href=\"./cpCode?page="+(next10)+"\" title=\""+Resource.getString("ID_LABEL_NEXT10PAGE",locale)+"\"><i class=\"icon-double-angle-right\"></i></a></li>");
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
			<div class="modal fade" id="editCode" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
							<h2 class="modal-title" id="">Promotional Code Details</h2>
						</div>
						<div class="modal-body">
							<div class="row">
								<form id="codeForm" name="codeForm" class="reg-page" style="width:90%;margin:0px auto!important;" method="post" action="./code">
									<input type="hidden" id="actionType" name="actionType" value="dbcodes_update">						
						<!--			<input type="hidden" id="formValues" name="formValues">
						-->			
									<div class="space-4"></div>
									
									<div class="form-group col-xs-12">
					                    <label>Code <span class="red">*</span></label><br/>
					                    <div class="input-group">
											<input id="code" name="code" type="text" class="form-control" maxlength="10" value="" readonly disabled />
											<span class="input-group-btn">
												<button type="button" class="btn btn-info btn-sm" id="randomCode" ><i class="fa fa-random"></i> Generate Random Code</button>
											</span>
										</div>
									</div>
									
									<div class="margin-bottom-20"></div>
				
				                    <hr>
									
									<div class="space-4"></div>
									
									<div class="form-group">
										<div class="col-xs-8">
											<label>Discount <span class="red">*</span></label>
											<div>
												<input id="discount" name="discount" class="form-control" type="number" pattern="[0-9]*" maxlength="10" value="" />
											</div>
										</div>
										
										<div class="col-xs-4">
											<label>Discount Type</label>
											<div>
												<select name="discountType" id="discountType" class="form-control">
													<option value="0">MYR</option>
													<option value="1">%</option>
												</select>
											</div>
										</div>
									</div>
									
									<div class="form-group col-xs-12">
										<div class="space-8"></div>
										<label>Allow Times</label>
										<input id="allowTimes" name="allowTimes" type="number" pattern="[0-9]*" class="form-control margin-bottom-20" maxlength="3" value="1" />
									</div>
									
									<div class="space-4"></div>
									
									<div class="form-group col-xs-12">
										<label>Used</label>
										<input id="used" name="used" type="text" class="form-control margin-bottom-20" maxlength="3" value="" readonly disabled />
									</div>
									
									<div class="space-4"></div>
									
									<div class="form-group">
										<div class="col-xs-6">
											<label>Period (Start) <span class="red">*</span></label>
											<div class="input-group">
												<input class="form-control date-picker margin-bottom-20" id="periodStart" name="periodStart" data-date-format="yyyy-mm-dd" type="text" value="">
												<span class="input-group-addon">
													<i class="fa fa-calendar bigger-110"></i>
												</span>
											</div>
										</div>
										
										<div class="col-xs-6">
											<label>Period (End) <span class="red">*</span></label>
											<div class="input-group">
												<input class="form-control date-picker margin-bottom-20" id="periodEnd" name="periodEnd" data-date-format="yyyy-mm-dd" type="text" value="">
												<span class="input-group-addon">
													<i class="fa fa-calendar bigger-110"></i>
												</span>
											</div>
										</div>
									</div>
									
									<div class="form-group col-xs-12">
										<div class="space-8"></div>
										<label>Used to Book</label>
										<textarea id="usedToBook" name="usedToBook" class="form-control margin-bottom-20" rows="2" cols="50"  maxlength="100"  style="resize: none" readonly disabled></textarea>
									</div>
									
									<div class="space-4"></div>
									
									<div class="form-group col-xs-12">
										<label>Remark</label>
										<textarea id="remark" name="remark"  class="form-control margin-bottom-20" rows="2" cols="50" style="resize: none" maxlength="100"></textarea>
									</div>
									
									<div class="space-4"></div>
									
									<div class="form-group col-xs-12">
										<label>Is Allow</label>
										<input id="isAllow" name="isAllow" class="ace ace-switch ace-switch-5" type="checkbox" />
										<span class="lbl"></span>
									</div>
									
									
									<div class="space-24"></div>
									
				                </form>
			                </div>
						</div>
						<div class="modal-footer">
							<span id="loading"></span>
							<button type="button" class="btn btn-default" id="cancel" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
							<button type="button" class="btn btn-info" id="addCode" style="display:none"><%=Resource.getString("ID_LABEL_SUBMIT",locale) %></button>
							<button type="button" class="btn btn-info" id="updateCode" style="display:none"><%=Resource.getString("ID_LABEL_SUBMIT",locale) %></button>
							<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal" style="display:none"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
						</div>
					</div>
				</div>
			</div>

		<!--=== Edit Code Modal End ===-->
		
		<jsp:include page="cpModal.jsp" />

		<!-- inline scripts related to this page -->
		
		

		<!-- page specific plugin scripts -->
		<script src="./assets/js/jquery.dataTables.min.js"></script>
		<script src="./assets/js/jquery.dataTables.bootstrap.js"></script>
<g:compress>
		<script type="text/javascript">
		
			$( document ).ready(function() {
				
				$("#editCode #addCode").click(function(){
					
					var code = $("#editCode #code").val();
					var discount = $("#editCode #discount").val();
					var allowTimes = $("#editCode #allowTimes").val();
					var periodStart = $("#editCode #periodStart").val();
					var periodEnd = $("#editCode #periodEnd").val();
					var remark = $("#editCode #remark").val();
					var isAllow = "";
					if($("#editCode #isAllow").is(':checked')){
						isAllow = "on";
					};
					var discountType = $("#editCode #discountType").val();
					
					if(code == ''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input code </span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(discount == ''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Discount </span>&nbsp;&nbsp;&nbsp;");
						return;
					} else if(discount < 0){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Discount cannot be negative </span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(allowTimes == ''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Allow Times </span>&nbsp;&nbsp;&nbsp;");
						return;
					} else if(allowTimes < 0){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Allow Times cannot be negative </span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(periodStart==''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Period(Start)</span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(periodEnd==''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Period(End)</span>&nbsp;&nbsp;&nbsp;");
						return;
					}
					
			   		$("#editCode #loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#editCode #cancel").hide();
					$("#editCode #addCode").hide();
					$.ajax({
						type: 'POST',
						url: './code',
						data:{    
							lang: "<%=locale %>",
							actionType:"dbcodes_insert",
							code: code,
							discount: discount,
							allowTimes: allowTimes,
							periodStart: periodStart,
							periodEnd: periodEnd,
							remark: remark,
							isAllow: isAllow,
							discountType: discountType
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "insertSuccess"){
									$("#editCode #loading").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\">Add Success</span>&nbsp;&nbsp;&nbsp;");
									$("#editCode #closeBtn").show();
								} else {
									$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: " + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#editCode #closeBtn").hide();
									$("#editCode #cancel").show();
									$("#editCode #addCode").show();
								}
							}
						} 
					});
				});
				
				$("#editCode #updateCode").click(function(){
					
					var code = $("#editCode #code").val();
					var discount = $("#editCode #discount").val();
					var allowTimes = $("#editCode #allowTimes").val();
					var periodStart = $("#editCode #periodStart").val();
					var periodEnd = $("#editCode #periodEnd").val();
					var remark = $("#editCode #remark").val();
					var isAllow = "";
					if($("#editCode #isAllow").is(':checked')){
						isAllow = "on";
					};
					var discountType = $("#editCode #discountType").val();
					
					if(code == ''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input code </span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(discount == ''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Discount </span>&nbsp;&nbsp;&nbsp;");
						return;
					} else if(discount < 0){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Discount cannot be negative </span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(allowTimes == ''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Allow Times </span>&nbsp;&nbsp;&nbsp;");
						return;
					} else if(allowTimes < 0){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Allow Times cannot be negative </span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(periodStart==''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Period(Start)</span>&nbsp;&nbsp;&nbsp;");
						return;
					};
					if(periodEnd==''){
						$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\">Add Failed: Please input Period(End)</span>&nbsp;&nbsp;&nbsp;");
						return;
					}
					
			   		$("#editCode #loading").html("<i class='fa fa-circle-o-notch fa-spin btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					$("#editCode #cancel").hide();
					$("#editCode #updateCode").hide();
					$.ajax({
						type: 'POST',
						url: './code',
						data:{    
							lang: "<%=locale %>",
							actionType:"dbcodes_update",
							code: code,
							discount: discount,
							allowTimes: allowTimes,
							periodStart: periodStart,
							periodEnd: periodEnd,
							remark: remark,
							isAllow: isAllow,
							discountType: discountType
				   		},  
				   		
						success: function(response) {
							if(response != ""){
								
								if(response == "updateSuccess"){
									$("#editCode #loading").html("<span style='color:#fff;background-color:#72C02C;padding:8px;' class=\"text-highlights text-highlights-green\"><%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%></span>&nbsp;&nbsp;&nbsp;");
									$("#editCode #closeBtn").show();
								} else {
									$("#editCode #loading").html("<span style='color:#fff;background-color:red;padding:8px;' class=\"text-highlights text-highlights-red\"><%=Resource.getString("ID_MSG_UPDATEFAILED",locale) + Resource.getString("ID_LABEL_COLON",locale) %>" + response + "</span>&nbsp;&nbsp;&nbsp;");
									$("#editCode #closeBtn").hide();
									$("#editCode #cancel").show();
									$("#editCode #updateCode").show();
								}
							}
						} 
					});
				});
				
				$("#editCode #closeBtn").click(function(){
					location.reload();					
				});
				
				<%
					if(errmsg.length() > 0){
						if(errmsg.equals("insertSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_INSERTSUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("updateSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATESUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("noData")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_NODATA",locale) + "</p></div>\");");
						} else if(errmsg.equals("exist")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_EXIST",locale) + "</p></div>\");");
						} else if(errmsg.equals("noConnection")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i> " + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>

			});
			
			function editCode(tmpCode){
				
				$(function(){
					if(tmpCode == '' || tmpCode == null){
						$("#editCode #loading").html("");
						$("#editCode #randomCode").removeClass("disabled");
						
						$("#updateCode").hide();
						$("#addCode").show();
						
						$("#editCode #code").prop("disabled", false);
						$("#editCode #code").prop("readonly", false);
						
						$("#editCode #code").val('');
						$("#editCode #discount").val('');
						$("#editCode #allowTimes").val(1);
						$("#editCode #used").val('');
						$("#editCode #periodStart").val('');
						$("#editCode #periodEnd").val('');
						$("#editCode #usedToBook").val('');
						$("#editCode #remark").val('');
						$("#editCode #isAllow").prop("checked", false);
						$("#editCode #discountType option[value=0]").prop("selected", true);
						
						$("#editCode").modal();
					} else {
						$("#editCode #loading").html("");
						$("#editCode #randomCode").addClass("disabled");

						
						$("#addCode").hide();
						$("#updateCode").show();
						
						$("#editCode #code").prop("disabled", true);
						$("#editCode #code").prop("readonly", true);
						
						var code = $("#code" + tmpCode).val();
						var discount = $("#discount" + tmpCode).val();
						var allowTimes = $("#allowTimes" + tmpCode).val();
						var used = $("#used" + tmpCode).val();
						var periodStart = $("#periodStart" + tmpCode).val();
						var periodEnd = $("#periodEnd" + tmpCode).val();
						var usedToBook = $("#usedToBook" + tmpCode).val();
						var remark = $("#remark" + tmpCode).val();
						var isAllow = $("#isAllow" + tmpCode).val();
						var discountType = $("#discountType" + tmpCode).val();
						
						$("#editCode #code").val(code);
						$("#editCode #discount").val(discount);
						$("#editCode #allowTimes").val(allowTimes);
						$("#editCode #used").val(used);
						$("#editCode #periodStart").val(periodStart);
						$("#editCode #periodEnd").val(periodEnd);
						$("#editCode #usedToBook").val(usedToBook);
						$("#editCode #remark").val(remark);
						
						if(isAllow == 1){
							$("#editCode #isAllow").prop("checked", true);
						} else {
							$("#editCode #isAllow").prop("checked", false);
						}
						
						$("#editCode #discountType option[value=" + discountType + "]").prop("selected", true);
						
						
						$("#editCode").modal();
					}
				});
				
			};
			

			$("#editCode #randomCode").click(function(){
				var code = "";
			    var possible = "1234567890qazwsxedcrfvtgbyhnujmikolp";

			    for(var i=0; i<10; i++){
			        code += possible.charAt(Math.floor(Math.random() * possible.length));
			    }
			    
				$('#editCode #code').val(code);		
			});
			
			//datepicker plugin
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true,
				dateFormat: 'yy-mm-dd'
			})
			//show datepicker when clicking on the icon
			.next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
			
		</script>
</g:compress>
		<link rel="stylesheet" href="plugins/ace/css/datepicker.css" />
	</body>
</html>
