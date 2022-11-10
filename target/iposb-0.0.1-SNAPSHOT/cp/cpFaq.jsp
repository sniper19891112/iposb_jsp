<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.faq.*,java.util.ArrayList"%>
<%@include file="../include.jsp" %>

<%	
	String errmsg = "";
	String isShow = "";
	
	ArrayList data = (ArrayList)request.getAttribute(FaqController.OBJECTDATA);
	FaqDataModel faqData = new FaqDataModel();
	if(data != null && data.size() > 0){
		faqData = (FaqDataModel)data.get(0);
		errmsg = faqData.getErrmsg();
		
		if(faqData.getIsShow()==1){ //是否顯示 0: 不顯示
			isShow = "checked";
		}
	}

	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("cpfaq_insert") || actionType.equals("cpfaq_update"))){
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
		<title><%=Resource.getString("ID_LABEL_FAQMAINTAINANCE",locale)%></title>
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
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_FAQMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./cpFaq\">"+Resource.getString("ID_LABEL_FAQMAINTAINANCE",locale)+"</a></li>"); 
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
											
											<form id="cpFaqForm" name="cpFaqForm" method="post" action="./faq" class="form-horizontal">
											<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
											<input type="hidden" id="creator" name="creator" value="<%=userId %>">
											<input type="hidden" id="modifier" name="modifier" value="<%=userId %>">
											<input type="hidden" id="fid" name="fid" value="<%=faqData.getFid() %>">
											<input type="hidden" id="language" name="language">
											<input type="hidden" id="content_enUS" name="content_enUS">
											
											<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="contentText">
											  <tr>
												<td height="120">
													<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_TITLE",locale)%></h3>
													
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="title_enUS"> <%=Resource.getString("ID_LABEL_NAMEEN",locale)%> </label>
														<div class="col-sm-9">
															<input type="text" id="title_enUS" name="title_enUS" placeholder="" class="col-xs-10 col-sm-5" value="<%=faqData.getTitle_enUS() %>" maxlength="100" />&nbsp;*
														</div>
													</div>

													<div class="space-24"></div>
													<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_CONTENT",locale)%></h3>
									
													<%
														String content_enUS = "";
														if (faqData.getContent_enUS() != null){
															content_enUS = faqData.getContent_enUS().toString();
														}
													%>
													
													<div class="space-24"></div>
													
													<label> <%=Resource.getString("ID_LABEL_DESCEN",locale)%> </label>
													<textarea class="ckeditor" id="content_enUSDIV" name="content_enUSDIV" cols="50" rows="6"><%=content_enUS %></textarea>
																										
													<div class="space-24"></div>
													<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_OTHERSETTING",locale)%></h3>
													
													<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" for="category"> <%=Resource.getString("ID_LABEL_CATEGORY",locale)%> </label>
														<div class="col-sm-9">
															<select id="category" name="category">
																<option value="general" <% if(faqData.getCategory().equals("general")) out.print("selected"); %>><%=Resource.getString("ID_LABEL_GENERAL",locale)%></option>
															</select>
														</div>
													</div>
													
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

												</td>
											  </tr>
											</table>
											
										</div>
										
										<p>
											<div class="center">
												<a class="btn" id="backFaqBtn">
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
										<a class="btn btn-primary" id="addFaqBtn">
											<i class="fa fa-plus"></i>
											<%=Resource.getString("ID_LABEL_ADDNEWFAQ",locale)%>
										</a>
									</p>
											
									<div class="row">
										<div class="col-xs-12">
										
											<div class="table-responsive">
												<table id="faq-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center col-xs-1"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_TITLE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_CATEGORY",locale)%></th>
															<th class="center"></th>
															<th class="center"><%=Resource.getString("ID_LABEL_EDIT",locale)%></th>
														</tr>
													</thead>

													<tbody>
														<%
															String faqTitle = "";
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																
																faqData = (FaqDataModel)data.get(i);
																faqTitle = faqData.getTitle_enUS();
																//if(locale.equals("zh_CN")){
																//	faqTitle = faqData.getTitle_zhCN();
																//} else if(locale.equals("zh_TW")){
																//	faqTitle = faqData.getTitle_zhTW();
																//}
														%>
														<tr>
															<td align="center"><%=faqData.getFid() %>.</td>
															<td><%=faqTitle%></td>
															<td align="center"><%=faqData.getCategory() %></td>
															<td align="center">
																<% if(faqData.getIsShow()==1) {%>
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
															<td align="center"><a href="faq?actionType=cpfaq_edit&fid=<%=faqData.getFid() %>"><%=Resource.getString("ID_LABEL_EDIT",locale)%></a></td>
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
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NODATA",locale) + "</p></div>\");");
						} else if(errmsg.equals("exist")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_EXIST",locale) + "</p></div>\");");
						} else if(errmsg.equals("noConnection")){
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_NOCONNECTION",locale) + "</p></div>\");");
						} else {
							out.println("$( \"#result\" ).html(\"<div class=\'alert alert-danger\'><button type=\'button\' class=\'close\' data-dismiss=\'alert\'><i class=\'icon-remove\'></i></button><p><i class=\'icon-remove\'></i>" + Resource.getString("ID_MSG_UPDATEFAILED",locale) + "</p></div>\");");
						}
					}
				%>

			});
			
			
			$(function() {
				$( "#addFaqBtn" ).button({
					icons: {
						primary: "ui-icon-plusthick"
					}
				});
				$( "#addFaqBtn" ).click(function() { location.href='./faq?actionType=cpfaq_new'; return false; });
			});
			
			$(function() {
				$( "#backFaqBtn" ).button({
					icons: {
						primary: "ui-icon-circle-close"
					}
				});
				$( "#backFaqBtn" ).click(function() { location.href='./faq?actionType=cpfaq'; return false; });
			});
			
			function submitCheck() {
				document.getElementById("content_enUS").value = replaceApostrophe(CKEDITOR.instances.content_enUSDIV.getData());
				//document.getElementById("content_zhCN").value = replaceApostrophe(CKEDITOR.instances.content_zhCNDIV.getData());
				//document.getElementById("content_zhTW").value = replaceApostrophe(CKEDITOR.instances.content_zhTWDIV.getData());
				document.getElementById("backFaqBtn").style.display = "none";
				$( "#submitBtn" ).button( 'option', 'disabled', true );
				$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
				document.cpFaqForm.submit();
			}
			
			function submitForm() {
				document.getElementById("content_enUS").value = replaceApostrophe(document.getElementById("content_enUSDIV").innerHTML);
				//document.getElementById("content_zhCN").value = replaceApostrophe(document.getElementById("content_zhCNDIV").innerHTML);
				//document.getElementById("content_zhTW").value = replaceApostrophe(document.getElementById("content_zhTWDIV").innerHTML);
				
				document.getElementById("cancelBtn").style.display = "none";
				document.getElementById("submitBtn").disabled = true;
				document.getElementById("submitBtn").value = "<%=Resource.getString("ID_LABEL_PROCESSING",locale) %>";
				document.cpFaqForm.submit();
			};
			
			jQuery(function($) {
				var oTable1 = $('#faq-table').dataTable( {
				"aoColumns": [
			      null, null,null, null,
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
			})
		</script>
</g:compress>

	</body>
</html>
