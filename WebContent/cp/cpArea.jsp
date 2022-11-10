<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.area.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%
	String errmsg = "";
	String isShow = "";
	String isMajor = "";
	
	String showBelongArea = "block";
	
	ArrayList data = (ArrayList)request.getAttribute(AreaController.OBJECTDATA);
	AreaDataModel areaData = new AreaDataModel();
	if(data != null && data.size() > 0){
		areaData = (AreaDataModel)data.get(0);
		errmsg = areaData.getErrmsg();
		
		if(areaData.getIsShow()==1){ //是否顯示 0: 不顯示
			isShow = "checked";
		}
		
		if(areaData.getIsMajor()==1){ //是否顯示 0: 不顯示
			isMajor = "checked";
			showBelongArea = "none";
		}
		
		
	}
	
	//for Belong to Area column
	AreaDataModel majorCityData = new AreaDataModel();
	
	//Area List
	ArrayList<AreaDataModel> area = new ArrayList<AreaDataModel>();
	AreaDataModel aData = new AreaDataModel();
    area = AreaBusinessModel.areaList();
	
	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("dbarea_insert") || actionType.equals("dbarea_update"))){
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
		<title><%=Resource.getString("ID_LABEL_AREAMAINTAINANCE",locale)%></title>
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
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_AREAMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./area\">"+Resource.getString("ID_LABEL_AREAMAINTAINANCE",locale)+"</a></li>"); 
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
											
											<form id="areaForm" name="areaForm" method="post" action="./area" class="form-horizontal">
												<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
												<input type="hidden" id="creator" name="creator" value="<%=userId %>">
												<input type="hidden" id="modifier" name="modifier" value="<%=userId %>">
												<input type="hidden" id="aid" name="aid" value="<%=areaData.getAid() %>">
												
												<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="contentText">
												  <tr>
													<td height="120">
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_AREATITLE",locale)%></h3>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="name_enUS"> <%=Resource.getString("ID_LABEL_NAMEEN",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="name_enUS" name="name_enUS" placeholder="" class="col-xs-10 col-sm-5" value="<%=areaData.getName_enUS() %>" maxlength="100" />&nbsp;<font color="red">*</font>
															</div>
														</div>
														
														<!-- 
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="name_zhCN"> <%=Resource.getString("ID_LABEL_NAMESC",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="name_zhCN" name="name_zhCN" placeholder="" class="col-xs-10 col-sm-5" value="<%=areaData.getName_zhCN() %>" maxlength="100" />&nbsp;<font color="red">*</font>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="name_zhTW"> <%=Resource.getString("ID_LABEL_NAMETC",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="name_zhTW" name="name_zhTW" placeholder="" class="col-xs-10 col-sm-5" value="<%=areaData.getName_zhTW() %>" maxlength="100" />&nbsp;<font color="red">*</font>
															</div>
														</div>

														<br />
														-->
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="code"> <%=Resource.getString("ID_LABEL_CODE",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="code" name="code" placeholder="" class="col-xs-10 col-sm-5" value="<%=areaData.getCode() %>" maxlength="6" />&nbsp;<font color="red">*</font>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="isMajor"> <%=Resource.getString("ID_LABEL_MAJORCITY",locale)%> </label>
															<div class="col-sm-9">
																<div class="col-xs-3">
																	<label>
																		<input id="isMajor" name="isMajor" onClick="isMajorCity()" value="on" class="ace ace-switch ace-switch-5" type="checkbox" <%=isMajor %> />
																		<span class="lbl"></span>
																	</label>
																</div>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div id="majorCity" class="form-group" style="display: <%=showBelongArea %>">
															<label class="col-sm-3 control-label no-padding-right" for="belongArea"> <%=Resource.getString("ID_LABEL_BELONGTO",locale)%> </label>
															<div class="col-sm-9">
																<select id="belongArea" name="belongArea">
																	<option value="0"></option>
																	<% 
										                                if(area != null && !area.isEmpty()){
																			for(int i = 0; i < area.size(); i ++){
																				aData = area.get(i);
									                                %>
																		<option value="<%=aData.getAid() %>" <% if(aData.getAid() == areaData.getBelongArea()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
																	<%  } } %>
																</select>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="state"> <%=Resource.getString("ID_LABEL_STATE",locale)%> </label>
															<div class="col-sm-9">
																<select id="state" name="state">
																	<option value="" <% if(areaData.getState().equals("")){out.print("selected");} %>></option>
																	<option value="JHR" <% if(areaData.getState().equals("JHR")){out.print("selected");} %>>Johor</option>
																	<option value="KDH" <% if(areaData.getState().equals("KDH")){out.print("selected");} %>>Kedah</option>
																	<option value="KTN" <% if(areaData.getState().equals("KTN")){out.print("selected");} %>>Kelantan</option>
																	<option value="KUL" <% if(areaData.getState().equals("KUL")){out.print("selected");} %>>Kuala Lumpur</option>
																	<option value="MLK" <% if(areaData.getState().equals("MLK")){out.print("selected");} %>>Melaka</option>
																	<option value="NSN" <% if(areaData.getState().equals("NSN")){out.print("selected");} %>>Negeri Sembilan</option>
																	<option value="PHG" <% if(areaData.getState().equals("PHG")){out.print("selected");} %>>Pahang</option>
																	<option value="PRK" <% if(areaData.getState().equals("PRK")){out.print("selected");} %>>Perak</option>
																	<option value="PLS" <% if(areaData.getState().equals("PLS")){out.print("selected");} %>>Perlis</option>
																	<option value="PNG" <% if(areaData.getState().equals("PNG")){out.print("selected");} %>>Pulau Pinang</option>
																	<option value="SBH" <% if(areaData.getState().equals("SBH")){out.print("selected");} %>>Sabah</option>
																	<option value="SRW" <% if(areaData.getState().equals("SRW")){out.print("selected");} %>>Sarawak</option>
																	<option value="SGR" <% if(areaData.getState().equals("SGR")){out.print("selected");} %>>Selangor</option>
																	<option value="TRG" <% if(areaData.getState().equals("TRG")){out.print("selected");} %>>Terengganu</option>
																</select>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="space-24"></div>
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_OTHERSETTING",locale)%></h3>
														
														<div class="space-4"></div>
																
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="isShow"> <%=Resource.getString("ID_LABEL_VALID",locale)%> </label>
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
											<%=Resource.getString("ID_LABEL_ADDNEWAREA",locale)%>
										</a>
									</p>
											
									<div class="row">
										<div class="col-xs-12">
										
											<div class="table-responsive">
												<table id="area-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_AREATITLE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CODE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_BELONGTO",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_STATE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_VALID",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_EDIT",locale)%></th>
														</tr>
													</thead>

													<tbody>
														<%
															String areaName = "";
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																
																areaData = (AreaDataModel)data.get(i);
																areaName = areaData.getName_enUS();
																if(locale.equals("zh_CN")){
																	areaName = areaData.getName_zhCN();
																} else if(locale.equals("zh_TW")){
																	areaName = areaData.getName_zhTW();
																}
														%>
														<tr>
															<td align="center"><%=areaData.getAid() %>.</td>
															<td align="center"><%=areaName %></td>
															<td align="center"><%=areaData.getCode() %></td>
															
															<td align="center">
																<%
																	if(areaData.getBelongArea() != 0){
																		for(int j = 0; j < area.size(); j++){
																			majorCityData = (AreaDataModel)area.get(j);
																			if(areaData.getBelongArea() == majorCityData.getAid()){
																				out.print(majorCityData.getName_enUS());
																			}
																		}
																	} else {
																		out.print("");
																	}
																%>
															</td>
															
															<td align="center"><%=areaData.getState() %></td>
															
															<td align="center">
																<% if(areaData.getIsShow()==1) {%>
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
																<a href="./area?actionType=dbarea_edit&aid=<%=areaData.getAid() %>"><i class=" green icon-pencil bigger-130"></i></a>
																<input type="hidden" id="name_<%=areaData.getAid() %>" name="name_<%=areaData.getAid() %>" value="<%=areaData.getName_enUS() %>" />
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
				$( "#addAreaBtn" ).click(function() { location.href='./area?actionType=dbarea_new'; return false; });
			});
			
			$(function() {
				$( "#backAreaBtn" ).button({
					icons: {
						primary: "ui-icon-circle-close"
					}
				});
				$( "#backAreaBtn" ).click(function() { location.href='./area'; return false; });
			});
			
			function submitCheck() {
				if($("#name_enUS").val() == "" || $("#code").val() == ""){
					$( "#result" ).html("<div class='alert alert-danger'><button type='button' class='close' data-dismiss='alert'><i class='icon-remove'></i></button><p><i class='icon-remove'></i> <%=Resource.getString("ID_MSG_PLSINPUTNAMECODE",locale)%></p></div>");
					return false;
				} else {
					if ($('#isMajor').is(':checked')) {
						$('#belongArea').val(0);
					} else if (!$('#isMajor').is(':checked')) {
						if ($('#belongArea').val() == 0) {
							$( "#result" ).html("<div class='alert alert-danger'><button type='button' class='close' data-dismiss='alert'><i class='icon-remove'></i></button><p><i class='icon-remove'></i> <%=Resource.getString("ID_MSG_SELECTAREA",locale)%></p></div>");
							return false;
						}
					};
					
					document.getElementById("backAreaBtn").style.display = "none";
					$( "#submitBtn" ).prop( "disabled", true );
					$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
					document.areaForm.submit();
					
				};
			};
			
			jQuery(function($) {
				var oTable1 = $('#area-table').dataTable( {
				"aoColumns": [
			      null, null, null, null, null, null, { "bSortable": false }
				] } );
				
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table');
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			});
			
			function isMajorCity(){
				
				if($('#isMajor').is(':checked')){
					$('#majorCity').hide();
				} else {
					$('#majorCity').show();
				}
			};
			
		</script>
</g:compress>

	</body>
</html>
