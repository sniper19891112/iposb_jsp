<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.pricing.*,com.iposb.area.*,java.util.ArrayList,java.text.DecimalFormat"%>
<%@include file="../include.jsp" %>

<%	
	String errmsg = "";
	String isShow = "";
	
	ArrayList data = (ArrayList)request.getAttribute(PricingController.OBJECTDATA);
	PricingDataModel pricingData = new PricingDataModel();
	if(data != null && data.size() > 0){
		pricingData = (PricingDataModel)data.get(0);
		errmsg = pricingData.getErrmsg();
		
		if(pricingData.getIsShow()==1){ //是否顯示 0: 不顯示
			isShow = "checked";
		}
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	String orderBy = request.getParameter("orderBy") == null ? "" : request.getParameter("orderBy").toString();

	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("cpPricing_credit_parcel_insert") || actionType.equals("cpPricing_credit_parcel_update"))){
		addEdit = true;
		list = false;
	} else {
		addEdit = false;
		list = true;
	}
	
	DecimalFormat FORMAT = new DecimalFormat("#,###,##0.00");
	
%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title><%=Resource.getString("ID_LABEL_CREDITTERMUSER",locale)%> (<%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%>) <%=Resource.getString("ID_LABEL_PRICINGMAINTAINANCE",locale)%></title>
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
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_CREDITTERMUSER",locale)+ " (" + Resource.getString("ID_LABEL_LANDDDTRUCK",locale) + ") " +Resource.getString("ID_LABEL_PRICINGMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./cpPricing_credit_parcel\">"+Resource.getString("ID_LABEL_CREDITTERMUSER",locale)+ " (" + Resource.getString("ID_LABEL_LANDDDTRUCK",locale) + ") " +Resource.getString("ID_LABEL_PRICINGMAINTAINANCE",locale)+"</a></li>"); 
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
											
											<form id="cpPricingForm" name="cpPricingForm" method="post" action="./pricing" class="form-horizontal">
												<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
												<input type="hidden" id="pid" name="pid" value="<%=pricingData.getPid() %>">
												
												<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0" class="contentText">
												  <tr>
													<td height="120">
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_PRICINGSETTING",locale)%></h3>
																												
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="fromArea"> <%=Resource.getString("ID_LABEL_TRANSFERBETWEEN",locale)%> </label>
															<div class="col-sm-3">
																<select class="form-control" id="fromArea" name="fromArea">
																	<% 
										                                if(areaData != null && !areaData.isEmpty()){
																			for(int i = 0; i < areaData.size(); i ++){
																				aData = areaData.get(i);
																				
																				if(aData.getIsMajor() == 1) { //只顯示 Major Town
									                                %>
																		<option value="<%=aData.getAid() %>" <% if(pricingData.getFromArea()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
																	<% } } } %>
																</select>
																
																<span style="padding-left:50px"><i class="fa fa-arrows-v"></i></span>
																
																<select class="form-control" id="toArea" name="toArea">
																	<% 
										                                if(areaData != null && !areaData.isEmpty()){
																			for(int i = 0; i < areaData.size(); i ++){
																				aData = areaData.get(i);
									                                %>
																		<option value="<%=aData.getAid() %>" <% if(pricingData.getToArea()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
																	<% } } %>
																</select>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="weightEach"> <%=Resource.getString("ID_LABEL_WEIGHTEACH",locale)%> (KG) </label>
															<div class="col-sm-9">
																<input type="text" id="weightEach" name="weightEach" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getWeightEach() %>" maxlength="5"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="firstPrice"> <%=Resource.getString("ID_LABEL_FIRSTWEIGHTCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="firstPrice" name="firstPrice" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getFirstPrice() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="additionCharge"> <%=Resource.getString("ID_LABEL_ADDITIONWEIGHTCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="additionCharge" name="additionCharge" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getAdditionCharge() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="handling"> <%=Resource.getString("ID_LABEL_HANDLINGCHARGE",locale)%> (%) </label>
															<div class="col-sm-9">
																<input type="text" id="handling" name="handling" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getHandling() %>" maxlength="3"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="fuel"> <%=Resource.getString("ID_LABEL_FUELCHARGE",locale)%> (%) </label>
															<div class="col-sm-9">
																<input type="text" id="fuel" name="fuel" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getFuel() %>" maxlength="3"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DISCOUNTFORA",locale)%></h3>
																												
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountFirstPriceA"> <%=Resource.getString("ID_LABEL_FIRSTWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountFirstPriceA" name="discountFirstPriceA" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountFirstPriceA() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountAdditionPriceA"> <%=Resource.getString("ID_LABEL_ADDITIONWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountAdditionPriceA" name="discountAdditionPriceA" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountAdditionPriceA() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DISCOUNTFORB",locale)%></h3>
																												
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountFirstPriceB"> <%=Resource.getString("ID_LABEL_FIRSTWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountFirstPriceB" name="discountFirstPriceB" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountFirstPriceB() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountAdditionPriceB"> <%=Resource.getString("ID_LABEL_ADDITIONWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountAdditionPriceB" name="discountAdditionPriceB" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountAdditionPriceB() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DISCOUNTFORC",locale)%></h3>
																												
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountFirstPriceC"> <%=Resource.getString("ID_LABEL_FIRSTWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountFirstPriceC" name="discountFirstPriceC" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountFirstPriceC() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountAdditionPriceC"> <%=Resource.getString("ID_LABEL_ADDITIONWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountAdditionPriceC" name="discountAdditionPriceC" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountAdditionPriceC() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_DISCOUNTFORD",locale)%></h3>
																												
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountFirstPriceD"> <%=Resource.getString("ID_LABEL_FIRSTWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountFirstPriceD" name="discountFirstPriceD" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountFirstPriceD() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="discountAdditionPriceD"> <%=Resource.getString("ID_LABEL_ADDITIONWEIGHTDISCOUNT",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="discountAdditionPriceD" name="discountAdditionPriceD" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getDiscountAdditionPriceD() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_OTHERSETTING",locale)%></h3>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="isShow"> <%=Resource.getString("ID_LABEL_PUBLISH",locale)%> </label>
															<div class="col-sm-9">
																<div class="col-xs-3">
																	<label>
																		<input id="isShow" name="isShow" value="on" class="ace ace-switch ace-switch-5" type="checkbox" <%=isShow %> />
																		<span class="lbl"></span>
																	</label>
																</div>
															</div>
														</div>
														
														<div class="space-24"></div>
														
													</td>
												  </tr>
												</table>
											</form>
										</div>
										
										<p>
											<div class="center">
												<a class="btn" id="backPricingBtn">
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
										<a class="btn btn-primary" id="addPricingBtn">
											<i class="fa fa-plus"></i>
											<%=Resource.getString("ID_LABEL_ADDNEWPRICING",locale)%>
										</a>
									</p>
											
									<div class="row">
										<div class="col-xs-12">
										
											<div class="table-responsive">
												<table id="pricing-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center"><%=Resource.getString("ID_LABEL_NUM",locale)%></th>
															<th class="center col-xs-2"><%=Resource.getString("ID_LABEL_TRANSFERBETWEEN",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_WEIGHT",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_FIRSTWEIGHTCHARGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_ADDITIONWEIGHTCHARGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_HANDLING",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_FUEL",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_VALID",locale)%></th>
															<th class="center"></th>
														</tr>
													</thead>

													<tbody>
														<%
															if(data != null && data.size() > 0){
																for(int i = 0; i < data.size(); i++){
																
																pricingData = (PricingDataModel)data.get(i);
														%>
														<tr>
															<td align="center"><%=pricingData.getPid() %>.</td>
															<td align="center"><%=AreaBusinessModel.parse2AreaCode(pricingData.getFromArea(), pricingData.getToArea()) %></td>
															<td align="center"><%=FORMAT.format(pricingData.getWeightEach()) %> KG</td>
															<td align="center">RM <%=FORMAT.format(pricingData.getFirstPrice()) %></td>
															<td align="center">RM <%=FORMAT.format(pricingData.getAdditionCharge()) %></td>
															<td align="center"><%=pricingData.getHandling() %> %</td>
															<td align="center"><%=pricingData.getFuel() %> %</td>
															<td align="center">
																<% if(pricingData.getIsShow()==1) {%>
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
																<a href="pricing?actionType=cpPricing_credit_parcel_edit&pid=<%=pricingData.getPid() %>"><i class=" green icon-pencil bigger-130"></i></a>
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
		<script src="./assets/js/chosen.jquery.min.js"></script>
		<script src="./assets/js/fuelux.spinner.min.js"></script>
		
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

			
			$(function() {
				$( "#addPricingBtn" ).button({
					icons: {
						primary: "ui-icon-plusthick"
					}
				});
				$( "#addPricingBtn" ).click(function() { location.href='./pricing?actionType=cpPricing_credit_parcel_new'; return false; });
			});
			
			$(function() {
				$( "#backPricingBtn" ).button({
					icons: {
						primary: "ui-icon-circle-close"
					}
				});
				$( "#backPricingBtn" ).click(function() { location.href='./pricing?actionType=cpPricing_credit_parcel'; return false; });
			});
			
			function submitCheck() {
				document.getElementById("backPricingBtn").style.display = "none";
				$( "#submitBtn" ).button( 'option', 'disabled', true );
				$( "#submitBtn" ).button().text("<%=Resource.getString("ID_LABEL_PROCESSING",locale)%>");
				document.cpPricingForm.submit();
			};
			
			
			function validatenumber(event) {
				var key = window.event ? event.keyCode : event.which;

				if (key == 8 || key == 9 || key == 46) {
				    return true;
				}
				else if ( key > 57 || key < 48 ) {
				    return false;
				}
				else 
					return true;
			};
			
			jQuery(function($) {
				var oTable1 = $('#pricing-table').dataTable( {
				"aoColumns": [
			      null, null, null, null, null, null, null, null, { "bSortable": false }
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
