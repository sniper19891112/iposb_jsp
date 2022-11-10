<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.privilege.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	
	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(PrivilegeController.OBJECTDATA);
	PrivilegeDataModel privilegeData = new PrivilegeDataModel();
	if(data != null && data.size() > 0){
		privilegeData = (PrivilegeDataModel)data.get(0);
		errmsg = privilegeData.getErrmsg();
	}

	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("cpprivilege_insert") || actionType.equals("cpprivilege_update"))){
		addEdit = true;
		list = false;
	} else {
		addEdit = false;
		list = true;
	}
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_PRIVILEGEMAINTAINANCE",locale)%></title>
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
							<% if(list){ 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_PRIVILEGEMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./cpPrivilege\">"+Resource.getString("ID_LABEL_PRIVILEGEMAINTAINANCE",locale)+"</a></li>"); 
									if(actionType.equals("cpprivilege_insert")) {
										out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_ADDNEWITEM",locale)+"</li>"); 
									} else {
										out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_EDIT",locale)+"</li>"); 
									}
									
							   } 
							%>							
							
						</ul><!-- .breadcrumb -->

					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
									<%
										if(addEdit){
									%>
										<div>
											<div id="result"></div>
											
											<form id="cpPrivilegeForm" name="cpPrivilegeForm" method="post" action="./privilege" class="form-horizontal">
											<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
											<input type="hidden" id="creator" name="creator" value="<%=userId %>">
											<input type="hidden" id="modifier" name="modifier" value="<%=userId %>">
											<input type="hidden" id="iid" name="iid" value="<%=privilegeData.getIid() %>">
											<input type="hidden" id="language" name="language">

											<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="contentText">
											  <tr>
												<td height="120">
												
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="name_enUS"> <%=Resource.getString("ID_LABEL_NAMEEN",locale)%> </label>
														<div class="col-sm-9">
															<input type="text" id="name_enUS" name="name_enUS" placeholder="" class="col-xs-10 col-sm-5" value="<%=privilegeData.getName_enUS() %>" maxlength="100" />&nbsp;<font class="red">*</font>
														</div>
													</div>
													
													<div class="space-4"></div>
													<!-- 
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="name_zhCN"> <%=Resource.getString("ID_LABEL_NAMESC",locale)%> </label>
														<div class="col-sm-9">
															<input type="text" id="name_zhCN" name="name_zhCN" placeholder="" class="col-xs-10 col-sm-5" value="<%=privilegeData.getName_zhCN() %>" maxlength="100" />&nbsp;*
														</div>
													</div>
													
													<div class="space-4"></div>
													
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="name_zhTW"> <%=Resource.getString("ID_LABEL_NAMETC",locale)%> </label>
														<div class="col-sm-9">
															<input type="text" id="name_zhTW" name="name_zhTW" placeholder="" class="col-xs-10 col-sm-5" value="<%=privilegeData.getName_zhTW() %>" maxlength="100" />&nbsp;*
														</div>
													</div>
													
													<div class="space-4"></div>
													-->
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="icon"> <%=Resource.getString("ID_LABEL_ICON",locale)%> </label>
														<div class="col-sm-9">
															<input type="text" id="icon" name="icon" placeholder="" class="col-xs-10 col-sm-5" value="<%=privilegeData.getIcon() %>" maxlength="100" />&nbsp;<font class="red">*</font>
														</div>
													</div>
													
													<div class="space-4"></div>
													
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="link"> <%=Resource.getString("ID_LABEL_LINK",locale)%> </label>
														<div class="col-sm-9">
															<input type="text" id="link" name="link" placeholder="" class="col-xs-10 col-sm-5" value="<%=privilegeData.getLink() %>" maxlength="100" />&nbsp;<font class="red">*</font>
														</div>
													</div>
													
													<div class="space-4"></div>
													
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="privilege"> <%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%> </label>
														<div class="col-sm-9">
															
															<div class="checkbox">
																<label>
																	<input class="ace" type="checkbox" id="priv5" name="priv5" <% if (privilegeData.getPriv5()==1){out.print("checked");} %> />
																	<span class="lbl"> <%=Resource.getString("ID_LABEL_PRIV5",locale) %></span>
																</label>
															</div>
															
															<div class="checkbox">
																<label>
																	<input class="ace" type="checkbox" id="priv6" name="priv6" <% if (privilegeData.getPriv6()==1){out.print("checked");} %> />
																	<span class="lbl"> <%=Resource.getString("ID_LABEL_PRIV6",locale) %></span>
																</label>
															</div>
															
															<div class="checkbox">
																<label>
																	<input class="ace" type="checkbox" id="priv7" name="priv7" <% if (privilegeData.getPriv7()==1){out.print("checked");} %> />
																	<span class="lbl"> <%=Resource.getString("ID_LABEL_PRIV7",locale) %></span>
																</label>
															</div>
															
															<div class="checkbox">
																<label>
																	<input class="ace" type="checkbox" id="priv8" name="priv8" <% if (privilegeData.getPriv8()==1){out.print("checked");} %> />
																	<span class="lbl"> <%=Resource.getString("ID_LABEL_PRIV8",locale) %></span>
																</label>
															</div>
															
															<div class="checkbox">
																<label>
																	<input class="ace" type="checkbox" id="priv9" name="priv9" <% if (privilegeData.getPriv9()==1){out.print("checked");} %> />
																	<span class="lbl"> <%=Resource.getString("ID_LABEL_PRIV9",locale) %></span>
																</label>
															</div>
															
															<div class="checkbox">
																<label>
																	<input class="ace" type="checkbox" id="priv99" name="priv99" <% if (privilegeData.getPriv99()==1){out.print("checked");} %> />
																	<span class="lbl"> <%=Resource.getString("ID_LABEL_PRIV99",locale) %></span>
																</label>
															</div>
												
														</div>
													</div>
																
												</td>
											  </tr>
											</table>
											
											</form>
										</div>
										
										<p>
											<div class="center">
												<a class="btn" id="backPrivilegeBtn">
													<i class="icon-arrow-left"></i>
													<%=Resource.getString("ID_LABEL_BACK",locale)%>
												</a>
												<a class="btn btn-primary" id="submitBtn">
													<i class="icon-save"></i>
													<%=Resource.getString("ID_LABEL_SAVE",locale)%>
												</a>
											</div>
										</p>
									<% }
										else if(list){
									%>	
									<!--  
									<p>
										<a class="btn btn-primary" id="addPrivilegeBtn">
											<i class="fa fa-plus"></i>
											<%=Resource.getString("ID_LABEL_ADDNEWITEM",locale)%>
										</a>
									</p>
									-->
									<div id="result"></div>
									<div class="row">
										<div class="col-xs-12">
											<div class="table-responsive">
												<table id="privilege-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center"><%=Resource.getString("ID_LABEL_ITEMNAME",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ICON",locale)%></th>
															<th class="center">IID</th>
															<th class="center"><span data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>5"><%=Resource.getString("ID_LABEL_PRIV5",locale)%></span></th>
															<th class="center"><span data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>6"><%=Resource.getString("ID_LABEL_PRIV6",locale)%></span></th>
															<th class="center"><span data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>7"><%=Resource.getString("ID_LABEL_PRIV7",locale)%></span></th>
															<th class="center"><span data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>8"><%=Resource.getString("ID_LABEL_PRIV8",locale)%></span></th>
															<th class="center"><span data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>9"><%=Resource.getString("ID_LABEL_PRIV9",locale)%></span></th>
															<th class="center"><span data-rel="tooltip" title="<%=Resource.getString("ID_LABEL_PRIVILEGE",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>99"><%=Resource.getString("ID_LABEL_PRIV99",locale)%></span></th>
															<th class="center"></th>
														</tr>
													</thead>

													<tbody>
														<%
															String privilegeTitle = "";
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																
																privilegeData = (PrivilegeDataModel)data.get(i);
																privilegeTitle = privilegeData.getName_enUS();
																if(locale.equals("zh_CN")){
																	privilegeTitle = privilegeData.getName_zhCN();
																} else if(locale.equals("zh_TW")){
																	privilegeTitle = privilegeData.getName_zhTW();
																}
														%>
														<tr>
															<td><%=privilegeTitle %></td>
															<td class="center"><i class="<%=privilegeData.getIcon() %>"></i></td>
															<td class="center"><%=privilegeData.getIid() %></td>
															<td class="center"><%if (privilegeData.getPriv5()==1){out.print("<i class=\"icon-star orange2\"></i>");} else {out.print("<i class=\"icon-star-empty light-grey\"></i>");} %></td>
															<td class="center"><%if (privilegeData.getPriv6()==1){out.print("<i class=\"icon-star orange2\"></i>");} else {out.print("<i class=\"icon-star-empty light-grey\"></i>");} %></td>
															<td class="center"><%if (privilegeData.getPriv7()==1){out.print("<i class=\"icon-star orange2\"></i>");} else {out.print("<i class=\"icon-star-empty light-grey\"></i>");} %></td>
															<td class="center"><%if (privilegeData.getPriv8()==1){out.print("<i class=\"icon-star orange2\"></i>");} else {out.print("<i class=\"icon-star-empty light-grey\"></i>");} %></td>
															<td class="center"><%if (privilegeData.getPriv9()==1){out.print("<i class=\"icon-star orange2\"></i>");} else {out.print("<i class=\"icon-star-empty light-grey\"></i>");} %></td>
															<td class="center"><%if (privilegeData.getPriv99()==1){out.print("<i class=\"icon-star orange2\"></i>");} else {out.print("<i class=\"icon-star-empty light-grey\"></i>");} %></td>
															<td class="center">
																<div class="visible-md visible-lg hidden-sm hidden-xs">
																	<a class="blue" href="privilege?actionType=cpprivilege_edit&iid=<%=privilegeData.getIid() %>" class="btn btn-primary no-radius">
																		<i class="icon-edit"></i>
																		<%=Resource.getString("ID_LABEL_EDIT",locale)%>
																	</a>
																</div>
															</td>
														</tr>
														
														<input type="hidden" id="tid_<%=i %>" name="tid_<%=i %>" value="<%=privilegeData.getIid() %>" />
														<input type="hidden" id="name_<%=i %>" name="name_<%=i %>" value="<%=privilegeTitle %>" />
														<% }} %>
													</tbody>
												</table>
											</div><!-- /.table-responsive -->
										</div><!-- /span -->
									</div>
									
									
									<!-- Modal Start -->
										<div class="modal fade" id="reorderModal" tabindex="-1" role="dialog" aria-hidden="true">
											<div class="modal-dialog modal-lg">
												<div class="modal-content">
													<div class="modal-header">
														
														<h2><%=Resource.getString("ID_LABEL_REORDER",locale) %></h2>
													</div>
													<div class="modal-body">
														<div class="dd" id="nestable">
															<ol class="dd-list">
																
															</ol>
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default" id="thinkAgainBtn" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CANCEL",locale) %></button>
														<button type="button" class="btn btn-info" id="updateReorder"><%=Resource.getString("ID_LABEL_CONFIRMCHANGE",locale) %></button>
														<span id="loading_"></span>
														<button type="button" id="reorderCloseBtn" class="btn btn-default pull-right" data-dismiss="modal" style="display:none"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
													</div>
												</div>
											</div>
										</div>
										<a id="reorderBtn" style="cursor:pointer;float:right;padding-top:20px;"><i class="fa fa-sort"></i> <%=Resource.getString("ID_LABEL_REORDER",locale) %></a>
										
									<!-- Modal End -->
									
									<% } %>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		<a href="./cpPrivilege" id="refreshBtn"></a>
		
		

<g:compress>
		<script type="text/javascript">
		
			$( document ).ready(function() {
				<%
					if(errmsg.length() > 0){
						if(errmsg.equals("insertSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_INSERTSUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("updateSuccess")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-block alert-success\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATESUCCESS",locale) + "</p></div>\");");
						} else if(errmsg.equals("noData")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_NODATA",locale) + "</p></div>\");");
						} else if(errmsg.equals("exist")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_EXIST",locale) + "</p></div>\");");
						} else if(errmsg.equals("noConnection")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-ok\'></i> " + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>
			});
			
			$(function() {
				$( "#addPrivilegeBtn" ).click(function() { location.href='./privilege?actionType=cpprivilege_new'; return false; });
			});
			
			$(function() {
				$( "#backPrivilegeBtn" ).click(function() { location.href='./cpPrivilege'; return false; });
			});
			
			
			function submitCheck() {
				$( "#backPrivilegeBtn" ).hide();
				$( "#submitBtn" ).button( 'option', 'disabled', true );
				$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
				document.cpPrivilegeForm.submit();
			};
			
			jQuery(function($) {			
				
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
				
				$('[data-rel=tooltip]').tooltip();
			});
			
			$(function(){
				
				$("#reorderBtn").click(function(){
					var li = "";
					for(var i = 0; i < <%=data == null ? 0 : data.size() %>; i++){
						var tid = $("#tid_" + i).val();
						var name = $("#name_" + i).val();
						li += "<li class='dd-item' id='" + tid + "'><div class='dd-handle'>" + tid + ". " + name + "</div></li>\n";
					};
					
					$(".dd-list").html(li);
					$("#reorderModal").modal();
				});
				$('.dd-list').sortable({});
				$('.dd-handle a').on('mousedown', function(e){
					e.stopPropagation();
				});
				
				
				$("#updateReorder").click(function(){
					
					$( "#thinkAgainBtn" ).hide();
					$( "#updateReorder" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
					var reorder = "";
					var row = 1;
					$(".dd-list").children().each(function(){
						reorder += row + "-" + $(this).attr("id") + ",";
						row ++;
					});
					//alert(reorder);
					//===Update Reorder Start===
					$.post("./privilege",
					{
						actionType: "ajaxUpdateReorder",
						reorder:reorder
					},
					function(data,status){
						$( "#thinkAgainBtn" ).show();
						$( "#updateReorder" ).button().text("<%=Resource.getString("ID_LABEL_CONFIRMCHANGE",locale) %>");
						
						if((data == "OK")) {
							$("#reorderCloseBtn").get(0).click();
							$( "#result" ).html("<div class='alert alert-block alert-success'><button type='button' class='close' data-dismiss='alert'><i class='icon-remove'></i></button><p><i class='icon-ok'></i><%=Resource.getString("ID_MSG_UPDATESUCCESS",locale) %></p></div>");
							$("#refreshBtn").get(0).click();
						}else{
							$( "#result" ).html("<div class='alert alert-block alert-success'><button type='button' class='close' data-dismiss='alert'><i class='icon-remove'></i></button><p><i class='icon-ok'></i><%=Resource.getString("ID_MSG_UPDATEFAILED",locale) %></p></div>");
						};
						
					});
					//===Update Reorder End===
				});

			});
		</script>
</g:compress>

	</body>
</html>
