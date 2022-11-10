<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.area.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%
	String errmsg = "";
	String isShow = "";
	
	ArrayList data = (ArrayList)request.getAttribute(AreaController.OBJECTDATA);
	AreaDataModel zoneData = new AreaDataModel();
	if(data != null && data.size() > 0){
		zoneData = (AreaDataModel)data.get(0);
		errmsg = zoneData.getErrmsg();
		
		if(zoneData.getIsShow()==1){ //是否顯示 0: 不顯示
			isShow = "checked";
		}
		
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();

	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("dbzone_insert") || actionType.equals("dbzone_update"))){
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
		<title><%=Resource.getString("ID_LABEL_ZONEMAINTAINANCE",locale)%></title>
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
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_ZONEMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./zone\">"+Resource.getString("ID_LABEL_ZONEMAINTAINANCE",locale)+"</a></li>"); 
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_EDIT",locale)+"</li>"); 
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
											
											<form id="zoneForm" name="zoneForm" method="post" action="./area" class="form-horizontal">
												<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
												<input type="hidden" id="zid" name="zid" value="<%=zoneData.getZid() %>">
												
												<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="contentText">
												  <tr>
													<td height="120">
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_ZONETITLE",locale)%></h3>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="name_enUS"> <%=Resource.getString("ID_LABEL_NAMEEN",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="name_enUS" name="name_enUS" placeholder="" class="col-xs-10 col-sm-5" value="<%=zoneData.getName_enUS() %>" maxlength="100" />&nbsp;<font color="red">*</font>
															</div>
														</div>
														
														<%--
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="name_zhCN"> <%=Resource.getString("ID_LABEL_NAMESC",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="name_zhCN" name="name_zhCN" placeholder="" class="col-xs-10 col-sm-5" value="<%=zoneData.getName_zhCN() %>" maxlength="100" />&nbsp;<font color="red">*</font>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="name_zhTW"> <%=Resource.getString("ID_LABEL_NAMETC",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="name_zhTW" name="name_zhTW" placeholder="" class="col-xs-10 col-sm-5" value="<%=zoneData.getName_zhTW() %>" maxlength="100" />&nbsp;<font color="red">*</font>
															</div>
														</div>

														<br />
														
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="code"> <%=Resource.getString("ID_LABEL_CODE",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="code" name="code" placeholder="" class="col-xs-10 col-sm-5" value="<%=zoneData.getCode() %>" maxlength="6" />&nbsp;<font color="red">*</font>
															</div>
														</div>
														
														<div class="space-4"></div>
														--%>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="aid"> <%=Resource.getString("ID_LABEL_BELONGTO",locale)%> </label>
															<div class="col-sm-4">
																<select class="form-control" id="aid" name="aid">
									                                <% 
										                                if(areaData != null && !areaData.isEmpty()){
																			for(int i = 0; i < areaData.size(); i ++){
																				aData = areaData.get(i);
									                                %>
																		<option value="<%=aData.getAid() %>" <% if(zoneData.getAid()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
																	<% } } %>
																</select>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="cutoff"> Cut off </label>
															<div class="col-sm-9">
																<select id="cutoff" name="cutoff">
																	<option value="" <% if(zoneData.getCutoff().equals("")){out.print("selected");} %>></option>
									                                <% 
																		for(int i = 0; i < 24; i++){
									                                %>
																		<option value="<%= i %>" <% if(zoneData.getCutoff().equals(String.valueOf(i))){out.print("selected");} %>><%= i + ":00" %></option>
																	<% } %>
																</select>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="space-24"></div>
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_OTHERSETTING",locale)%></h3>
														
														<div class="space-4"></div>
																
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="isShow"> <%=Resource.getString("ID_LABEL_VALID",locale)%> </label>
															<div class="col-sm-9">
																<div class="col-xs-3">
																	<label>
																		<input id="isShow" name="isShow" value="on" class="ace ace-switch ace-switch-5" type="checkbox" <%=isShow %> />
																		<span class="lbl"></span>
																	</label>
																</div>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														
													</td>
												  </tr>
												</table>
											</form>
										</div>
										
										<p>
											<div class="center">
												<a class="btn" id="backAreaBtn">
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
									
									<p>
										<a class="btn btn-primary" id="addAreaBtn">
											<i class="fa fa-plus"></i>
											<%=Resource.getString("ID_LABEL_ADDNEWZONE",locale)%>
										</a>
									</p>
											
									<div class="row">
										<div class="col-xs-12">
										
											<div class="table-responsive">
												<table id="zone-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CODE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ZONETITLE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_BELONGTO",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_VALID",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_EDIT",locale)%></th>
														</tr>
													</thead>

													<tbody>
														<%
															String zoneName = "";
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																
																zoneData = (AreaDataModel)data.get(i);
																zoneName = zoneData.getName_enUS();
																if(locale.equals("zh_CN")){
																	zoneName = zoneData.getName_zhCN();
																} else if(locale.equals("zh_TW")){
																	zoneName = zoneData.getName_zhTW();
																}
														%>
														<tr>
															<td align="center"><%=zoneData.getZid() %>.</td>
															<td align="center"><%=zoneData.getAreaCode() %><%=zoneData.getSerial() %></td>
															<td><%=zoneName %></td>
															<td align="center"><%=zoneData.getAreaName() %></td>
															<td align="center">
																<% if(zoneData.getIsShow()==1) {%>
																	<span class="fa-stack fa-lg">
																	  <i class="fa fa-eye fa-stack-1x"></i>
																	</span>
																<% } else { %>
																	<span class="fa-stack fa-lg">
																	  <i class="fa fa-eye fa-stack-1x"></i>
																	  <i class="fa fa-ban fa-stack-2x text-danger"></i>
																	</span>
																<% } %>
															</td>
															<td align="center">
																<a href="./area?actionType=dbzone_edit&zid=<%=zoneData.getZid() %>"><i class=" green icon-pencil bigger-130"></i></a>
																<input type="hidden" id="name_<%=zoneData.getZid() %>" name="name_<%=zoneData.getZid() %>" value="<%=zoneData.getName_enUS() %>" />
															</td>
														</tr>
														<% }} %>
													</tbody>
												</table>
					
											</div><!-- /.table-responsive -->
										</div><!-- /span -->
									</div>
									<% } %>
								
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
				
			</div><!-- /.main-container-inner -->
			
			

		</div><!-- /.main-container -->
		
		<%@include file="cpFooter.jsp" %>
		
		

		<!-- page specific plugin scripts -->
	
		<script src="./assets/js/jquery.dataTables.min.js"></script>
		<script src="./assets/js/jquery.dataTables.bootstrap.js"></script>
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
			
			
			function changeCategory(){
				var cg = document.getElementById("category").value;
				if (cg == "txt") {
					document.getElementById("cat_gen").style.display = "block";
					document.getElementById("cat_url").style.display = "none";
				} else if (cg == "url") {
					document.getElementById("cat_gen").style.display = "none";
					document.getElementById("cat_url").style.display = "block";
				}
			};
			
			$(function() {
				$( "#addAreaBtn" ).button({
					icons: {
						primary: "ui-icon-plusthick"
					}
				});
				$( "#addAreaBtn" ).click(function() { location.href='./area?actionType=dbzone_new'; return false; });
			});
			
			$(function() {
				$( "#backAreaBtn" ).button({
					icons: {
						primary: "ui-icon-circle-close"
					}
				});
				$( "#backAreaBtn" ).click(function() { location.href='./zone'; return false; });
			});
			
			function submitCheck() {
				document.getElementById("backAreaBtn").style.display = "none";
				$( "#submitBtn" ).prop( "disabled", true );
				$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
				document.zoneForm.submit();
			};
			
			jQuery(function($) {
				var oTable1 = $('#zone-table').dataTable( {
				"aoColumns": [
			      null, null,null, null, { "bSortable": false }, { "bSortable": false }
				] } );
				
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			});
			
		</script>
</g:compress>

	</body>
</html>
