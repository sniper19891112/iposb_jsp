<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.pricing.*,com.iposb.area.*,java.util.ArrayList,java.text.DecimalFormat"%>
<%@include file="../include.jsp" %>

<%	
	String errmsg = "";
	String isShow = "";
	int firstRange = 0;
	int secondRange = 0;
	int thirdRange = 0;
	int forthRange = 0;
	
	ArrayList data = (ArrayList)request.getAttribute(PricingController.OBJECTDATA);
	PricingDataModel pricingData = new PricingDataModel();
	if(data != null && data.size() > 0){
		pricingData = (PricingDataModel)data.get(0);
		errmsg = pricingData.getErrmsg();
		firstRange = pricingData.getFirstRange();
		secondRange = pricingData.getSecondRange();
		thirdRange = pricingData.getThirdRange();
		forthRange = pricingData.getForthRange();
		
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
	if(actionType != null && (actionType.equals("cpPricing_normal_parcel_insert") || actionType.equals("cpPricing_normal_parcel_update"))){
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
		<title><%=Resource.getString("ID_LABEL_NORMALUSER",locale)%> (<%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%>) <%=Resource.getString("ID_LABEL_PRICINGMAINTAINANCE",locale)%></title>
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
									out.print("<li class=\"active\">"+Resource.getString("ID_LABEL_NORMALUSER",locale)+ " (" + Resource.getString("ID_LABEL_LANDDDTRUCK",locale) + ") " +Resource.getString("ID_LABEL_PRICINGMAINTAINANCE",locale)+"</li>"); 
							   } else {
									out.print("<li><a href=\"./cpPricing_normal_parcel\">"+Resource.getString("ID_LABEL_NORMALUSER",locale)+ " (" + Resource.getString("ID_LABEL_LANDDDTRUCK",locale) + ") " +Resource.getString("ID_LABEL_PRICINGMAINTAINANCE",locale)+"</a></li>"); 
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
														
														<h3 class="header smaller lighter blue"><%=Resource.getString("ID_LABEL_PRICERANGE",locale)%></h3>
														
														<div class="space-24"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="firstRange"> <%=Resource.getString("ID_LABEL_FIRSTQUANTITY",locale)%> (PCS) </label>
															<div class="col-sm-9">
																<div style="float:left; margin-right:10px">
																	<input type="text" value="1" size="3" disabled />
																</div>
																<%=Resource.getString("ID_LABEL_TO",locale)%>
																<input type="text" class="input-mini" id="firstRange" name="firstRange" style="width:80px; margin-left:10px" value="<%=pricingData.getFirstRange() %>" />
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="firstPrice"> <%=Resource.getString("ID_LABEL_FIRSTQUANTITYCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="firstPrice" name="firstPrice" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getFirstPrice() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
														
														<div class="space-24"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="secondRange"> <%=Resource.getString("ID_LABEL_SECONDQUANTITY",locale)%> (PCS) </label>
															<div class="col-sm-9">
																<div style="float:left; margin-right:10px">
																	<input type="text" value="1" size="3" id="secondStart" disabled />
																</div>
																<%=Resource.getString("ID_LABEL_TO",locale)%>
																<input type="text" class="input-mini" id="secondRange" name="secondRange" style="width:80px; margin-left:10px" value="<%=pricingData.getSecondRange() %>" />
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="secondPrice"> <%=Resource.getString("ID_LABEL_SECONDQUANTITYCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="secondPrice" name="secondPrice" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getSecondPrice() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
																												
														<div class="space-24"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="thirdRange"> <%=Resource.getString("ID_LABEL_THIRDQUANTITY",locale)%> (PCS) </label>
															<div class="col-sm-9">
																<div style="float:left; margin-right:10px">
																	<input type="text" value="1" size="3" id="thirdStart" disabled />
																</div>
																<%=Resource.getString("ID_LABEL_TO",locale)%>
																<input type="text" class="input-mini" id="thirdRange" name="thirdRange" style="width:80px; margin-left:10px" value="<%=pricingData.getThirdRange() %>" />
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="thirdPrice"> <%=Resource.getString("ID_LABEL_THIRDQUANTITYCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="thirdPrice" name="thirdPrice" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getThirdPrice() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
																												
														<div class="space-24"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="forthRange"> <%=Resource.getString("ID_LABEL_FORTHQUANTITY",locale)%> (PCS) </label>
															<div class="col-sm-9">
																<div style="float:left; margin-right:10px">
																	<input type="text" value="1" size="3" id="forthStart" disabled />
																</div>
																<%=Resource.getString("ID_LABEL_TO",locale)%>
																<input type="text" class="input-mini" id="forthRange" name="forthRange" style="width:80px; margin-left:10px" value="<%=pricingData.getForthRange() %>" />
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="forthPrice"> <%=Resource.getString("ID_LABEL_FORTHQUANTITYCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="forthPrice" name="forthPrice" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getForthPrice() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
															</div>
														</div>
																												
														<div class="space-24"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="fifthRange"> <%=Resource.getString("ID_LABEL_FIFTHQUANTITY",locale)%> (PCS) </label>
															<div class="col-sm-9">
																<div style="float:left; margin-right:10px">
																	<input type="text" value="1" size="3" id="fifthStart" disabled /> and above
																</div>
															</div>
														</div>
														
														<div class="space-4"></div>
														
														<div class="form-group">
															<label class="col-sm-3 control-label no-padding-right" for="fifthPrice"> <%=Resource.getString("ID_LABEL_FIFTHQUANTITYCHARGE",locale)%> (RM) </label>
															<div class="col-sm-9">
																<input type="text" id="fifthPrice" name="fifthPrice" placeholder="" class="col-xs-10 col-sm-5" value="<%=pricingData.getFifthPrice() %>" maxlength="8"  onKeyPress="return validatenumber(event);" />&nbsp;<span class="red">*</span>
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
															<th class="center"><%=Resource.getString("ID_LABEL_FIRSTRANGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_SECONDRANGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_THIRDRANGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_FORTHRANGE",locale)%></th>
															<th class="center"><%=Resource.getString("ID_LABEL_FIFTHRANGE",locale)%></th>
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
															<td align="center">1 - <%=pricingData.getFirstRange() %> pcs <br/> RM <%=FORMAT.format(pricingData.getFirstPrice()) %></td>
															<td align="center"><%=pricingData.getFirstRange()+1 %> - <%=pricingData.getSecondRange() %> pcs <br/> RM <%=FORMAT.format(pricingData.getSecondPrice()) %></td>
															<td align="center"><%=pricingData.getSecondRange()+1 %> - <%=pricingData.getThirdRange() %> pcs <br/> RM <%=FORMAT.format(pricingData.getThirdPrice()) %></td>
															<td align="center"><%=pricingData.getThirdRange()+1 %> - <%=pricingData.getForthRange() %> pcs <br/> RM <%=FORMAT.format(pricingData.getForthPrice()) %></td>
															<td align="center"><%=pricingData.getForthRange()+1 %> pcs and above <br/> RM <%=FORMAT.format(pricingData.getFifthPrice()) %></td>
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
																<a href="pricing?actionType=cpPricing_normal_parcel_edit&pid=<%=pricingData.getPid() %>"><i class=" green icon-pencil bigger-130"></i></a>
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
				
				<% if(addEdit){ %>
				
					$("#secondStart").val(eval(<%=firstRange %>) + 1);
					$("#thirdStart").val(eval(<%=secondRange %>) + 1);
					$("#forthStart").val(eval(<%=thirdRange %>) + 1);
					$("#fifthStart").val(eval(<%=forthRange %>) + 1);
				
				<% } %>

			});
			
			
			$('#firstRange').ace_spinner({value:1,min:0,max:999,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
			.on('change', function(){
				$("#secondStart").val(eval(this.value) + 1);
			});
			
			$('#secondRange').ace_spinner({value:1,min:0,max:999,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
			.on('change', function(){
				$("#thirdStart").val(eval(this.value) + 1);
			});
			
			$('#thirdRange').ace_spinner({value:1,min:0,max:999,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
			.on('change', function(){
				$("#forthStart").val(eval(this.value) + 1);
			});
			
			$('#forthRange').ace_spinner({value:1,min:0,max:999,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
			.on('change', function(){
				$("#fifthStart").val(eval(this.value) + 1);
			});
			
			$(function() {
				$( "#addPricingBtn" ).button({
					icons: {
						primary: "ui-icon-plusthick"
					}
				});
				$( "#addPricingBtn" ).click(function() { location.href='./pricing?actionType=cpPricing_normal_parcel_new'; return false; });
			});
			
			$(function() {
				$( "#backPricingBtn" ).button({
					icons: {
						primary: "ui-icon-circle-close"
					}
				});
				$( "#backPricingBtn" ).click(function() { location.href='./pricing?actionType=cpPricing_normal_parcel'; return false; });
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
			      null, null, null, null, null, null, null, null, null, null, null, { "bSortable": false }
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
