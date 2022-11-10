<%@page import="com.iposb.service.*, java.util.ArrayList"%>
<%@include file="include.jsp" %>
<%	
	String urlStr = request.getRequestURL() + (request.getQueryString()==null? "?" : "?"+request.getQueryString()+"&");
	String fixPath = urlStr.replace("aboutus.jsp", "aboutus")
			.replace("contact.jsp", "contact")
			.replace("login.jsp", "login")
			.replace("member.jsp", "logon")
			.replace("myBooking.jsp", "my")
			.replace("my.jsp", "my")
			.replace("cpMember.jsp", "logon")
			.replace("faq.jsp", "faq")
			.replace("faq.jsp", "faq").replace("cpFaq.jsp", "faq")
			.replace("enquiry.jsp", "enquiry")
			.replace("eventinfo.jsp", "events")
			.replace("bulletininfo.jsp", "bulletins")
			.replace("cp.jsp", "cp")
			.replace("cpAccount.jsp", "account")
			.replace("cpAccountMaintain.jsp", "accountMaintain")
			.replace("cpBookingCalendar.jsp", "cpBookingCalendar")
			.replace("log.jsp", "log").replace("status.jsp", "status")
			.replace("log_search.jsp", "sysLog")
			.replace("myBookingChange.jsp", "changeBooking")
			.replace("cpBulletins.jsp", "cpBulletins")
			.replace("cpAdv.jsp", "cpAdv")
			.replace("price.jsp", "price");
	
	String url = fixPath.replace("?lang=en_US", "?").replace("&lang=en_US", "").replace("?lang=zh_CN", "?").replace("&lang=zh_CN", "").replace("?lang=zh_TW", "?").replace("&lang=zh_TW", "");

	String tab = request.getParameter("tab") == null ? "" : request.getParameter("tab");
	String tabIndex = "";
	String tabHotel = "";
	String tabTour = "";
	String tabCar = "";
	String tabCharter = "";
	String tabMice = "";
	if(tab.equals("index")){
		tabIndex = "active";
	} else if(tab.equals("hotel")){
		tabHotel = "active";
	} else if(tab.equals("tour")){
		tabTour = "active";
	} else if(tab.equals("car")){
		tabCar = "active";
	} else if(tab.equals("charter")){
		tabCharter = "active";
	} else if(tab.equals("mice")){
		tabMice = "active";
	}
	
	ArrayList<ServiceDataModel> dataForHeader = ServiceBusinessModel.viewServiceForHeader();
	ServiceDataModel serviceDataForHeader = new ServiceDataModel();
	if(dataForHeader != null && dataForHeader.size() > 0){
		serviceDataForHeader = (ServiceDataModel)dataForHeader.get(0);
	}
%>
<!-- Page Wrapper -->
		<div class="wrapper">
		
			<!-- Header Start -->
			
			<div class="header">
				<div class="container">
					<!-- Header top area content -->
					<div class="header-top">
						<div class="row">
							<div class="col-md-4 col-sm-4">
								<!-- Header top left content contact -->
								<div class="header-contact">
									<!-- Contact number -->
									<span><i class="fa fa-phone orange"></i> Call Us: 089-271 863</span>
								</div>
							</div>
							<div class="col-md-2 col-sm-4">

							</div>
							
							<!-- 
							<div class="col-md-4 col-sm-4">
								<div class=" header-search">
									<form class="form" role="form">
										<div class="input-group">
										  <input type="text" class="form-control" placeholder="Search...">
										  <span class="input-group-btn">
											<button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
										  </span>
										</div>
									</form>
								</div>
							</div>
							-->
							
							<div class="col-md-6 col-sm-4">
								<div class="topbar">
									<ul class="loginbar pull-right">
										<% if(priv >= 0) { %>
											<span>Hi <b><%=userName %></b><%=Resource.getString("ID_LABEL_COLON",locale)%></span> <br/>
										<% } %>
										
					                    <li>
					                    	<% if(priv == -9) { //還沒 login %>
												<li><i class="fa fa-pencil-square-o orange"></i> <a href="./register"><%=Resource.getString("ID_LABEL_REGISTER",locale)%></a></li> 
												<li class="topbar-devider"></li> 
												<li><i class="fa fa-sign-in orange"></i> <a href="./login"><%=Resource.getString("ID_LABEL_LOGIN",locale)%></a></li>   
											<% } else if(priv >= 5) { //公司內部 %>
												<li><i class="fa fa-tachometer orange"></i> <a href="./cp"><%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-user orange"></i> <a href="./staffprofile"><%=Resource.getString("ID_LABEL_MYPROFILE",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-sign-out orange"></i> <a href="./logout"><%=Resource.getString("ID_LABEL_LOGOUT",locale)%></a></li>  
											<% } else if(priv == 4) { //partner %>
												<li><i class="fa fa-check-square-o orange"></i> <a href="./partnerCP"><%=Resource.getString("ID_LABEL_DASHBOARD",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-user orange"></i> <a href="./companyprofile"><%=Resource.getString("ID_LABEL_COMPANYPROFILE",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-sign-out orange"></i> <a href="./partnerlogout"><%=Resource.getString("ID_LABEL_LOGOUT",locale)%></a></li>
											<% } else if(priv == 3) { //agent %>
												<li><i class="fa fa-check-square-o orange"></i> <a href="./agentCP"><%=Resource.getString("ID_LABEL_DASHBOARD",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-user orange"></i> <a href="./companyprofile"><%=Resource.getString("ID_LABEL_COMPANYPROFILE",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-sign-out orange"></i> <a href="./partnerlogout"><%=Resource.getString("ID_LABEL_LOGOUT",locale)%></a></li>
											<% } else { %>
												<li><i class="fa fa-check-square-o orange"></i> <a href="./my"><%=Resource.getString("ID_LABEL_MYCONSIGNMENT",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-user orange"></i> <a href="./profile"><%=Resource.getString("ID_LABEL_MYPROFILE",locale)%></a></li> 
												<li class="topbar-devider"></li>
												<li><i class="fa fa-sign-out orange"></i> <a href="./logout"><%=Resource.getString("ID_LABEL_LOGOUT",locale)%></a></li>  
											<%} %>
									</ul>
								</div>
							</div>
							
							
						</div>
					</div>
					<div class="row">
						<div class="col-md-4 col-sm-5">
							<!-- Link -->
							<a href="./">
								<!-- Logo area -->
								<div class="logo">
									<img class="img-responsive" src="./assets/img/logo.png" width="295" height="117" alt="iPosb Logistic" title="iPosb Logistic" />
								</div>
							</a>
						</div>
						<div class="col-md-8 col-sm-7">
							<!-- Navigation -->
							<nav class="navbar navbar-default navbar-right" role="navigation">
								<div class="container-fluid">
									<!-- Brand and toggle get grouped for better mobile display -->
									<div class="navbar-header">
										<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
											<span class="sr-only">Toggle navigation</span>
											<span class="icon-bar"></span>
											<span class="icon-bar"></span>
											<span class="icon-bar"></span>
										</button>
									</div>

									<!-- Collect the nav links, forms, and other content for toggling -->
									<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
										<ul class="nav navbar-nav">
											<li><a href="./"><img src="./assets/img/nav-menu/nav1.jpg" class="img-responsive" alt="" /> <%=Resource.getString("ID_LABEL_HOME",locale)%> </a></li>
											<li class="dropdown hidden-xs">
												<a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="./assets/img/nav-menu/nav2.jpg" class="img-responsive" alt="" /> <%=Resource.getString("ID_LABEL_OURSERVICES",locale)%> <b class="caret"></b></a>
												<ul class="dropdown-menu dropdown-md">
													<li>
														<div class="row">
															<div class="col-md-4 col-sm-6">
																<!-- Menu Item -->
																<div class="menu-item">
																	<!-- Heading -->
																	<h3><%=serviceDataForHeader.getTitle1()%></h3>
																	<!-- Image -->
																	<a href="./service#door"><img src="./assets/img/service/service1.jpg" class="img-responsive" alt="<%=serviceDataForHeader.getTitle1()%>" /></a>
																	<!-- Paragraph -->
																	<p><%=serviceDataForHeader.getIntro1()%></p>
																	<!-- Button -->
																	<a href="./service#door" class="btn btn-orange btn-xs"><%=Resource.getString("ID_LABEL_VIEWDETAILS",locale)%></a>
																</div>
															</div>
															<div class="col-md-4 col-sm-6">
																<!-- Menu Item -->
																<div class="menu-item">
																	<!-- Heading -->
																	<h3><%=serviceDataForHeader.getTitle2()%></h3>
																	<!-- Image -->
																	<a href="./service#station"><img src="./assets/img/service/service2.jpg" class="img-responsive" alt="<%=serviceDataForHeader.getTitle2()%>" /></a>
																	<!-- Paragraph -->
																	<p><%=serviceDataForHeader.getIntro2()%></p>
																	<!-- Button -->
																	<a href="./service#station" class="btn btn-orange btn-xs"><%=Resource.getString("ID_LABEL_VIEWDETAILS",locale)%></a>
																</div>
															</div>
															<div class="col-md-4">
																<!-- Menu Item -->
																<div class="menu-item">
																	<!-- Heading -->
																	<h3><%=serviceDataForHeader.getTitle3()%></h3>
																	<!-- Image -->
																	<a href="./service#online"><img src="./assets/img/service/service3.jpg" class="img-responsive" alt="<%=serviceDataForHeader.getTitle3()%>" /></a>
																	<!-- Paragraph -->
																	<p><%=serviceDataForHeader.getIntro3()%></p>
																	<!-- Button -->
																	<a href="./service#online" class="btn btn-orange btn-xs"><%=Resource.getString("ID_LABEL_VIEWDETAILS",locale)%></a>
																</div>
															</div>
														</div>
													</li>
												</ul>
											</li>
											<li class="dropdown visible-xs">
												<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <%=Resource.getString("ID_LABEL_OURSERVICES",locale)%> <b class="caret"></b></a>
												<ul class="dropdown-menu">
													<li><a href="./service#door"><%=Resource.getString("ID_LABEL_DOORTODOOR",locale)%></a></li>
													<li><a href="./service#station"><%=Resource.getString("ID_LABEL_STATIONTOSTATION",locale)%></a></li>
													<li><a href="./service#online"><%=Resource.getString("ID_LABEL_ONLINEREQUEST",locale)%></a></li>
												</ul>
											</li>
											<li><a href="./price"><img src="./assets/img/nav-menu/nav3.jpg" class="img-responsive" alt="" /> <%=Resource.getString("ID_LABEL_PRICELIST",locale)%> </a></li>
											<li class="dropdown">
												<a href="#" class="dropdown-toggle" data-toggle="dropdown"><img src="./assets/img/nav-menu/nav4.jpg" class="img-responsive" alt="" /> <%=Resource.getString("ID_LABEL_GETLOWRATES",locale)%> <b class="caret"></b></a>
												<ul class="dropdown-menu">
													<li><a href="./howitworks"><%=Resource.getString("ID_LABEL_HOWITWORKS",locale)%></a></a></li>
													<li><a href="#"><%=Resource.getString("ID_LABEL_QUOTENOW",locale)%></a></li>
												</ul>
											</li>
											<li><a href="./contact"><img src="./assets/img/nav-menu/nav5.jpg" class="img-responsive" alt="" /> <%=Resource.getString("ID_LABEL_CONTACTUS",locale)%> </a></li>
										</ul>
									</div><!-- /.navbar-collapse -->
								</div><!-- /.container-fluid -->
							</nav>
						</div>
					</div>
				</div> <!-- / .container -->
			</div>
			
			<!-- Header End -->