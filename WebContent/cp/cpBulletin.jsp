<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.bulletin.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	
	String errmsg = "";
	String catGen = "block"; //預設顯示general的textarea
	String catUrl = "none"; //預設不顯示url的textbox
	String isShow = "";
	
	ArrayList data = (ArrayList)request.getAttribute(BulletinController.OBJECTDATA);
	BulletinDataModel bulletinData = new BulletinDataModel();
	if(data != null && data.size() > 0){
		bulletinData = (BulletinDataModel)data.get(0);
		errmsg = bulletinData.getErrmsg();
		
		if(bulletinData.getIsShow()==1){ //是否顯示 0: 不顯示
			isShow = "checked";
		}
		
		if(bulletinData.getCategory().equals("url")){
			catGen = "none";
			catUrl = "block";
		}
	}
	
	String orderBy = request.getParameter("orderBy") == null ? "" : request.getParameter("orderBy").toString();

	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("cpbulletin_insert") || actionType.equals("cpbulletin_update"))){
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
		<title><%=Resource.getString("ID_LABEL_BULLETINMAINTAINANCE",locale)%></title>
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
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_BULLETINMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./cpBulletin\">"+Resource.getString("ID_LABEL_BULLETINMAINTAINANCE",locale)+"</a></li>"); 
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
											
											<form id="cpBulletinForm" name="cpBulletinForm" method="post" action="./bulletin" class="form-horizontal">
												<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
												<input type="hidden" id="creator" name="creator" value="<%=userId %>">
												<input type="hidden" id="modifier" name="modifier" value="<%=userId %>">
												<input type="hidden" id="bid" name="bid" value="<%=bulletinData.getBid() %>">
												<input type="hidden" id="language" name="language">
												<input type="hidden" id="content_enUS" name="content_enUS">
												<input type="hidden" id="content_zhCN" name="content_zhCN">
												<input type="hidden" id="content_zhTW" name="content_zhTW">
												
												<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="contentText">
												  <tr>
													<td height="120">
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_BULLETINTITLE",locale)%></h3>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="title_enUS"> <%=Resource.getString("ID_LABEL_NAMEEN",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="title_enUS" name="title_enUS" placeholder="" class="col-xs-10 col-sm-5" value="<%=bulletinData.getTitle_enUS() %>" maxlength="100" />&nbsp;*
															</div>
														</div>
														
														<!-- 
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="title_zhCN"> <%=Resource.getString("ID_LABEL_NAMESC",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="title_zhCN" name="title_zhCN" placeholder="" class="col-xs-10 col-sm-5" value="<%=bulletinData.getTitle_zhCN() %>" maxlength="100" />&nbsp;*
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="title_zhTW"> <%=Resource.getString("ID_LABEL_NAMETC",locale)%> </label>
															<div class="col-sm-9">
																<input type="text" id="title_zhTW" name="title_zhTW" placeholder="" class="col-xs-10 col-sm-5" value="<%=bulletinData.getTitle_zhTW() %>" maxlength="100" />&nbsp;*
															</div>
														</div>
														
														-->

														<br />
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="category"> <%=Resource.getString("ID_LABEL_CATEGORY",locale)%> </label>
															<div class="col-sm-9">
																<select id="category" name="category" onChange="changeCategory()">
																	<option value="txt" <% if(bulletinData.getCategory().equals("txt")) out.print("selected"); %>>Bulletin Text</option>
																	<option value="url" <% if(bulletinData.getCategory().equals("url")) out.print("selected"); %>>URL Link</option>
																</select>
															</div>
														</div>
														
														<div class="space-24"></div>
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_BULLETINCONTENT",locale)%></h3>
														
														<span id="cat_url" name="cat_url" style="display:<%=catUrl %>">
															<p>URL<%=Resource.getString("ID_LABEL_COLON",locale)%><input type="text" id="url" name="url" value="<%=bulletinData.getUrl() %>" size="100" maxlength="200" /></p>
														</span>
														
														<%
															String content_enUS = "";
															String content_zhCN = "";
															String content_zhTW = "";
															if (bulletinData.getContent_enUS() != null){
																content_enUS = bulletinData.getContent_enUS().toString();
															}
															if (bulletinData.getContent_zhCN() != null){
																content_zhCN = bulletinData.getContent_zhCN().toString();
															}
															if (bulletinData.getContent_zhTW() != null){
																content_zhTW = bulletinData.getContent_zhTW().toString();
															}
														%>
														
														<span id="cat_gen" name="cat_gen" style="display:<%=catGen %>">
														
															<div class="space-24"></div>
															
															<label> <%=Resource.getString("ID_LABEL_CONTENT",locale)%><%=Resource.getString("ID_LABEL_BRACKETEN",locale)%> </label>
															<textarea class="ckeditor" id="content_enUSDIV" name="content_enUSDIV" cols="50" rows="6"><%=content_enUS %></textarea>
															
															<div class="space-24"></div>
															
															<!-- 
															
															<label> <%=Resource.getString("ID_LABEL_CONTENT",locale)%><%=Resource.getString("ID_LABEL_BRACKETSC",locale)%> </label>
															<textarea class="ckeditor" id="content_zhCNDIV" name="content_zhCNDIV" cols="50" rows="6"><%=content_zhCN %></textarea>

															<div class="space-24"></div>
															
															<label> <%=Resource.getString("ID_LABEL_CONTENT",locale)%><%=Resource.getString("ID_LABEL_BRACKETTC",locale)%> </label>
															<textarea class="ckeditor" id="content_zhTWDIV" name="content_zhTWDIV" cols="50" rows="6"><%=content_zhTW %></textarea>
															
															-->
														
														</span>
														
														<div class="space-24"></div>
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_OTHERSETTING",locale)%></h3>
														
														<div class="space-4"></div>
																
														<div class="form-group">
															<label class="col-sm-2 control-label no-padding-right" for="isShow"> <%=Resource.getString("ID_LABEL_PUBLISH",locale)%> </label>
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
												<a class="btn" id="backBulletinBtn">
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
										<a class="btn btn-primary" id="addBulletinBtn">
											<i class="fa fa-plus"></i>
											<%=Resource.getString("ID_LABEL_ADDNEWBULLETIN",locale)%>
										</a>
									</p>
											
									<div class="row">
										<div class="col-xs-12">
										
											<div class="table-responsive">
												<table id="bulletin-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_BULLETINTITLE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_LASTUPDATE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_VIEW",locale)%></th>
															<th class="center"></th>
															<th class="center"><%=Resource.getString("ID_LABEL_EDIT",locale)%></th>
														</tr>
													</thead>

													<tbody>
														<%
															String bulletinTitle = "";
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																
																bulletinData = (BulletinDataModel)data.get(i);
																bulletinTitle = bulletinData.getTitle_enUS();
																if(locale.equals("zh_CN")){
																	bulletinTitle = bulletinData.getTitle_zhCN();
																} else if(locale.equals("zh_TW")){
																	bulletinTitle = bulletinData.getTitle_zhTW();
																}
														%>
														<tr>
															<td align="center"><%=bulletinData.getBid() %>.</td>
															<td><%=bulletinTitle %></td>
															<td align="center"><%=bulletinData.getModifyDT() %></td>
															<td align="center"><%=bulletinData.getView() %></td>
															<td align="center">
																<% if(bulletinData.getIsShow()==1) {%>
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
																<a href="bulletin?actionType=cpbulletin_edit&bid=<%=bulletinData.getBid() %>"><i class=" green icon-pencil bigger-130"></i></a>
																<!--  / 
																<i onclick="callImageModal('<%=bulletinData.getBid() %>')" class="purple icon-cloud-upload bigger-130" style="cursor:pointer;" id="imageModalIcon"></i>
																-->
																<input type="hidden" id="name_<%=bulletinData.getBid() %>" name="name_<%=bulletinData.getBid() %>" value="<%=bulletinData.getTitle_enUS() %>" />
															</td>
														</tr>
														<% }} %>
													</tbody>
												</table>
					
											</div><!-- /.table-responsive -->
										</div><!-- /span -->
									</div>
									<!--=== Modal Start ===-->
										<div class="modal fade" id="uploadImageModal" tabindex="-1" role="dialog" aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
														<h2 class="modal-title" id=""><%=Resource.getString("ID_LABEL_UPLOADIMAGE",locale)%></h2>
														<p><%=Resource.getString("ID_LABEL_BULLETINTITLE",locale) %><%=Resource.getString("ID_LABEL_COLON",locale) %> <span id="name"></span></p>
													</div>
													<div class="modal-body">
														<form id="imageForm" method="post" enctype="multipart/form-data" action="./upload" class="form-horizontal">
															<input type="hidden" id="bid" name="bid" value="">
															<input type="hidden" id="name" name="name" value="">
															<input type="hidden" id="actionUpload" name="actionUpload" value="bulletins">
															<input type="file" id="image" name="image" value="" style="width:0;top:-999;position: absolute;left: -999px;"/>
															
															
															<!--=== Image lang Start ===-->
																<div class="space-4"></div>
																		
																<div class="form-group">
																	<label class="col-sm-4 control-label no-padding-right" for="imageLang"> <%=Resource.getString("ID_LABEL_LANGUAGE",locale)%> </label>
																	<div class="col-sm-7">
																		<div class="col-xs-3">
																			<label>
																				<select id="imageLang" name="imageLang">
																					<option value="en_US" ><%=Resource.getString("ID_LABEL_ENGLISH",locale)%></option>
																					<option value="zh_CN" ><%=Resource.getString("ID_LABEL_SCHINESE",locale)%></option>
																					<option value="zh_TW" ><%=Resource.getString("ID_LABEL_TCHINESE",locale)%></option>
																				</select>
																			</label>
																		</div>
																	</div>
																</div>
															<!--=== Image lang End ===-->
															<div class="space-10"></div>
														</form>
														<!--=== image size remind Start ===-->
														<!--	<div class="space-4"></div>
																	
															<div class="form-group">
																<label class="col-sm-4 control-label no-padding-right" for="imageLang"></label>
																<div class="col-sm-7">
																	<b><%=Resource.getString("ID_LABEL_IMAGESIZELARGER", "1170 x 500",locale)%></b>
																</div>
															</div>-->
														<!--=== image size remind End ===-->
													</div>
													<div class="modal-footer">
														<div id="uploadSuccess" class="success" style="display:none;padding:10px;background-color:#87B87F;"></div>
														<div id="uploadError"  class="error" style="display:none;padding:10px;background-color:#D15B47;"></div>
														<div id="loading"></div>
														<div class="space-4"></div>
														<button type="button" class="btn btn-info" id="uploadImageBtn"><%=Resource.getString("ID_LABEL_UPLOADIMAGE",locale) %></button>
														<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
													</div>
												</div>
											</div>
										</div>
										
										
									<!--=== Modal End ===-->
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
		
		<script src="plugins/ckeditor/ckeditor.js"></script>

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
				$( "#addBulletinBtn" ).button({
					icons: {
						primary: "ui-icon-plusthick"
					}
				});
				$( "#addBulletinBtn" ).click(function() { location.href='./bulletin?actionType=cpbulletin_new'; return false; });
			});
			
			$(function() {
				$( "#backBulletinBtn" ).button({
					icons: {
						primary: "ui-icon-circle-close"
					}
				});
				$( "#backBulletinBtn" ).click(function() { location.href='./bulletin?actionType=cpbulletin'; return false; });
			});
			
			function submitCheck() {
				document.getElementById("content_enUS").value = replaceApostrophe(CKEDITOR.instances.content_enUSDIV.getData());
				//document.getElementById("content_zhCN").value = replaceApostrophe(CKEDITOR.instances.content_zhCNDIV.getData());
				//document.getElementById("content_zhTW").value = replaceApostrophe(CKEDITOR.instances.content_zhTWDIV.getData());
				document.getElementById("backBulletinBtn").style.display = "none";
				$( "#submitBtn" ).button( 'option', 'disabled', true );
				$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
				document.cpBulletinForm.submit();
			};
			
			jQuery(function($) {
				var oTable1 = $('#bulletin-table').dataTable( {
				"aoColumns": [
			      null, null,null, null, null,
				  { "bSortable": false }
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
			
			function callImageModal(bid){
				$(function(){
					$("#uploadSuccess").hide();
					$("#uploadError").hide();
					var name = $("#name_" + bid).val();
					if(bid.length > 0 && name.length > 0){
						$("#uploadImageModal input[id=bid]").val(bid);
						$("#uploadImageModal input[id=name]").val(name);
						$("#uploadImageModal span[id=name]").html(name);
						$("#uploadImageModal").modal();
					}else{
						return;
					}
				});
			};
			$(function(){
				
				
				
				var lang = "";
				var path = "";
				var name = "";
				
				$("#uploadImageModal #uploadImageBtn").click(function(){
					lang = $("#uploadImageModal #imageLang option:selected").val();
					path = $("#uploadImageModal input[id=bid]").val();
					name = $("#uploadImageModal input[id=name]").val();
					$("#image").get(0).click();
				});
				
				
				
				
				$("#image").change(function(){

					$("#imageForm").attr("action", "./upload?actionUpload=bulletins&path=" + path + "&name=" + name + "&lang=" + lang + "");
					$("#uploadSuccess").hide();
					$("#uploadError").hide();
					$("#uploadImageBtn").hide();
					$("#closeBtn").hide();
					$("#uploadImageModal #loading").show();
					$("#uploadImageModal #loading").html("<i class='fa fa-circle-o-notch fa-pulse btn-lg'></i>&nbsp;&nbsp;&nbsp;");
					
					$("#imageForm").ajaxForm({
							
						 success: function(data, status){ 
					     	var start = data.indexOf(">");
				            if(start != -1) {
				        	  var end = data.indexOf("<", start + 1);
				        	  if(end != -1) {
				        	    data = data.substring(start + 1, end);
				        	   }
				            }
				            
				            var type = ["jpeg", "png", "jpg", "gif"];
				            var isTrue = false;
					        for(var i = 0; i < type.length; i ++){
					        	if($.trim(data.substring(data.indexOf(".")+1, data.length)).indexOf(type[i]) > -1){
					        		isTrue = true;
					        	};
					        }
					        
							if(isTrue){
								$("#uploadImageModal #loading").hide();
								$("#uploadImageModal #loading").html("");
								$("#uploadImageBtn").show();
								$("#closeBtn").show();
								$("#uploadSuccess").show();
								$("#uploadSuccess").html("<%=Resource.getString("ID_LABEL_UPLOADSUCCESS",locale)%>");
							}else{
								$("#uploadImageModal #loading").hide();
								$("#uploadImageModal #loading").html("");
								$("#uploadImageBtn").show();
								$("#closeBtn").show();
								$("#uploadError").show();
								$("#uploadError").html("<%=Resource.getString("ID_LABEL_UPLOADERROR",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%>" + data);
							}
							
					     }
						 
					}).submit();
				});
			});
		</script>
</g:compress>

	</body>
</html>
