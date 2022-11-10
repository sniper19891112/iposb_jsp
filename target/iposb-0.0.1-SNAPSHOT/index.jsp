<%@page import="com.iposb.i18n.*,com.iposb.utils.*,com.iposb.bulletin.*,java.util.*" contentType="text/html; charset=utf-8"%>
<%@include file="include.jsp" %>

<head>
    <title>iPosb :: A Whole New World Of Logistics</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    
    
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />
    <meta name="author" content="iPosb Logistic">

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Slider ===-->
    <%@include file="adv.jsp" %>
    <!--=== End Slider ===-->


    <!--=== Content Part ===-->
    <div class="inner-page padd">
    
    	<div class="single-item">
			<div class="container">

				<div class="single-item-content">
					<div class="row">
						<div class="col-md-3 col-sm-5">
							<!-- Product image -->
							<img class="img-responsive img-thumbnail" src="assets/img/service/global.jpg" alt="" />
						</div>
						<div class="col-md-5 col-sm-7">
							<!-- Heading -->
							<h3>Latest News</h3>

							<!-- Single item details -->
							<div class="item-details">
								<ul class="list-unstyled">
								
									<%
										ArrayList lastestBulletinData = BulletinBusinessModel.latestBulletin();
										BulletinDataModel bulletinData = new BulletinDataModel();
										if(lastestBulletinData != null && lastestBulletinData.size() > 0){
											for(int i = 0; i < lastestBulletinData.size(); i++) {		
												bulletinData = (BulletinDataModel)lastestBulletinData.get(i);
												int bid = bulletinData.getBid();
												String bulletinDT = bulletinData.getModifyDT().length() > 10 ? bulletinData.getModifyDT().substring(0, 10) : bulletinData.getModifyDT();
												String name = bulletinData.getTitle_enUS();
												String nameFull = name;
												name = name.length() > 30 ? name.substring(0, 30) + "..." : name;
												if(locale.equals("zh_CN")){
													name = bulletinData.getTitle_zhCN();
													nameFull = name;
													name = name.length() > 18 ? name.substring(0, 18) + "..." : name;
												} else if(locale.equals("zh_TW")){
													name = bulletinData.getTitle_zhTW();
													nameFull = name;
													name = name.length() > 18 ? name.substring(0, 18) + "..." : name;
												}
												
												out.print("<li>");
												out.print("["+bulletinDT+"] <a href=\"./bulletin-"+bid+"\">"+name+"</a> <span class=\"pull-right\"> <a href=\"./bulletin-"+bid+"\"><i class=\"fa fa-search-plus\"></i></a></span>");
												out.print("<div class=\"clearfix\"></div>");
												out.print("</li>");
											}
										}
									%>

								</ul>
							</div>

						</div>
						
						<div class="col-md-4 col-sm-7">
							<h3><%=Resource.getString("ID_LABEL_TRACE",locale)%></h3>
							<div class="table-responsive">

								<form role="form">
									<!-- Table -->
									<table class="table table-bordered">
										<tr>
											<td>
												<p style="text-align: left"><%=Resource.getString("ID_LABEL_CONSIGNMENTNUMBER",locale)%><%=Resource.getString("ID_LABEL_COLON",locale)%></p>

												<div class="input-group">
													<input type="text" class="form-control search-query text-uppercase" id="traceConsignmentNo" name="traceConsignmentNo" placeholder="" />
													
													<span class="input-group-btn">
														<a data-toggle="modal" href="#tracer">
															<button class="btn btn-orange" id="traceConsignment">
																<i class="fa fa-search fa-lg"></i> <%=Resource.getString("ID_LABEL_TRACE",locale)%>
															</button>
														</a>
													</span>
												</div>

											</td>
										</tr>
									</table>
								</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
        
    	<div class="dishes padd">
			<div class="container">
				<!-- Default Heading -->
				<div class="default-heading">
					<!-- Crown image -->
					<img class="img-responsive" src="assets/img/crown.png" alt="" />
					<!-- Heading -->
					<h2>Why Choose Us?</h2>
					<!-- Paragraph -->
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
					<!-- Border -->
					<div class="border"></div>
				</div>
				<div class="row">
					<div class="col-md-3 col-sm-6">	
						<div class="dishes-item-container">
							<!-- Image Frame -->
							<div class="img-frame">
								<!-- Image -->
								<img src="assets/img/service/service5.jpg" class="img-responsive" alt="" />
								<!-- Block for on hover effect to image -->
								<div class="img-frame-hover">
									<!-- Hover Icon -->
									<a href="#"><i class="fa fa-search-plus"></i></a>
								</div>
							</div>
							<!-- Dish Details -->
							<div class="dish-details">
								<!-- Heading -->
								<h3>Immediate Response</h3>
								<!-- Paragraph -->
								<p>At vero eos et accusal gusto for ides residuum lores.</p>
								<!-- Button -->
								<a href="#" class="btn btn-orange">Read more</a>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-6">
						<div class="dishes-item-container">
							<!-- Image Frame -->
							<div class="img-frame">
								<!-- Image -->
								<img src="assets/img/service/service6.jpg" class="img-responsive" alt="" />
								<!-- Block for on hover effect to image -->
								<div class="img-frame-hover">
									<!-- Hover Icon -->
									<a href="#"><i class="fa fa-search-plus"></i></a>
								</div>
							</div>
							<!-- Dish Details -->
							<div class="dish-details">
								<!-- Heading -->
								<h3>Same Day Delivery</h3>
								<!-- Paragraph -->
								<p>At vero eos et accusal gusto for ides residuum lores.</p>
								<!-- Button -->
								<a href="#" class="btn btn-orange">Read more</a>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-6">
						<div class="dishes-item-container">
							<!-- Image Frame -->
							<div class="img-frame">
								<!-- Image -->
								<img src="assets/img/service/service7.jpg" class="img-responsive" alt="" />
								<!-- Block for on hover effect to image -->
								<div class="img-frame-hover">
									<!-- Hover Icon -->
									<a href="#"><i class="fa fa-search-plus"></i></a>
								</div>
							</div>
							<!-- Dish Details -->
							<div class="dish-details">
								<!-- Heading -->
								<h3>Dedicated Service</h3>
								<!-- Paragraph -->
								<p>At vero eos et accusal gusto for ides residuum lores.</p>
								<!-- Button -->
								<a href="#" class="btn btn-orange">Read more</a>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-6">
						<div class="dishes-item-container">
							<!-- Image Frame -->
							<div class="img-frame">
								<!-- Image -->
								<img src="assets/img/service/service8.jpg" class="img-responsive" alt="" />
								<!-- Block for on hover effect to image -->
								<div class="img-frame-hover">
									<!-- Hover Icon -->
									<a href="#"><i class="fa fa-search-plus"></i></a>
								</div>
							</div>
							<!-- Dish Details -->
							<div class="dish-details">
								<!-- Heading -->
								<h3>Online Request</h3>
								<!-- Paragraph -->
								<p>At vero eos et accusal gusto for ides residuum lores.</p>
								<!-- Button -->
								<a href="#" class="btn btn-orange">Read more</a>
							</div>
						</div>
					</div>
				</div>
			</div>					
		</div>

    </div><!--/container-->		
    <!-- End Content Part -->
	
	<!-- Modal -->
	<div class="modal fade" id="tracer" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title"><%=Resource.getString("ID_LABEL_TRACE",locale)%></h4>
				</div>
				<div class="modal-body">
					<span id="tracerList"><div style="margin:30px; text-align:center"><i><%=Resource.getString("ID_LABEL_TRACING",locale)%></i> <i class="fa fa-circle-o-notch fa-spin fa-2x"></i></div></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CLOSE",locale)%></button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- Model End -->
	
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			   jQuery('.tp-banner').revolution(
				{
					delay:9000,
					startheight:500,
					
					hideThumbs:10,
					
					navigationType:"bullet",	
												
					hideArrowsOnMobile:"on",
					
					touchenabled:"on",
					onHoverStop:"on",
					
					navOffsetHorizontal:0,
					navOffsetVertical:20,
					
					stopAtSlide:-1,
					stopAfterLoops:-1,

					shadow:0,
					
					fullWidth:"on",
					fullScreen:"off"
				});
		});
		
		
	$("#traceConsignment").click(function(){
		
		var consignmentNo = $("#traceConsignmentNo").val();
		
		if(consignmentNo.length < 1) {
			return false;
		}
			
		$("#tracer #tracerList").html("<div style=\"margin:30px; text-align:center\"><i class=\"fa fa-circle-o-notch fa-spin fa-2x\"></i></div>");
		
		$.ajax({
			type: 'POST',
			url: './consignment',
			data:{    
				lang: "<%=locale %>",
				consignmentNo: consignmentNo,
				actionType:"trace"
	   		},  
	   		
			success: function(response) {
				if(response != ""){
					$("#tracer #tracerList").html(response);
				}
			} 
		});
			
	});
    
	</script>
	
	<%@include file="footer.jsp" %>

</body>
</html>	