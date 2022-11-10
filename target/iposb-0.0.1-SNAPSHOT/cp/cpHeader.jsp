<%@page import="com.iposb.i18n.*, com.iposb.utils.*,com.iposb.logon.*" contentType="text/html; charset=utf-8"%>

<%

	String urlStr = request.getRequestURL() + (request.getQueryString()==null? "?" : "?"+request.getQueryString()+"&");
	String fixPath = urlStr
			.replace("aboutus.jsp", "aboutus")
			.replace("contact.jsp", "contact")
			.replace("login.jsp", "login")
			.replace("member.jsp", "logon")
			.replace("myBooking.jsp", "my")
			.replace("my.jsp", "my")
			.replace("cpMember.jsp", "logon")
			.replace("faq.jsp", "faq")
			.replace("faq.jsp", "faq")
			.replace("cpFaq.jsp", "faq")
			.replace("enquiry.jsp", "enquiry")
			.replace("eventinfo.jsp", "events")
			.replace("bulletininfo.jsp", "bulletins")
			.replace("cp.jsp", "cp")
			.replace("cpAccount.jsp", "account")
			.replace("cpAccountMaintain.jsp", "accountMaintain")
			.replace("cpBookingCalendar.jsp", "cpBookingCalendar")
			.replace("log.jsp", "log")
			.replace("status.jsp", "status")
			.replace("log_search.jsp", "sysLog")
			.replace("myBookingChange.jsp", "changeBooking")
			.replace("cpBulletins.jsp", "cpBulletins");
	String url = fixPath.replace("?lang=en_US", "?").replace("&lang=en_US", "").replace("?lang=zh_CN", "?").replace("&lang=zh_CN", "").replace("?lang=zh_TW", "?").replace("&lang=zh_TW", "");

	String server = "";
	String ssl = "";
		
%>

<div class="navbar navbar-default" id="navbar">
	<script type="text/javascript">
		try{ace.settings.check('navbar' , 'fixed')}catch(e){}
	</script>

	<div class="navbar-container" id="navbar-container">
		<div class="navbar-header pull-left">
			<a href="./cp" class="navbar-brand">
				<small>
					<i class="fa fa-tachometer"></i>
					<%=Resource.getString("ID_LABEL_CONTROLPANEL",locale)%>
				</small>
			</a><!-- /.brand -->
		</div><!-- /.navbar-header -->

		<div class="navbar-header pull-right" role="navigation">
			<ul class="nav ace-nav">
			
				<li class="purple">
					<a data-toggle="dropdown" class="dropdown-toggle" id="showNotification" href="#">
						<i class="icon-bell-alt"></i>
						<span class="badge badge-important"><span id="notificationCount1">0</span></span>
					</a>

					<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
						<li class="dropdown-header">
							<i class="fa fa-warning"></i>
							<span id="notificationCount2">0</span> <%=Resource.getString("ID_LABEL_NOTIFICATIONS", locale)%>
						</li>
					</ul>
				</li>
			
				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<!-- <img class="nav-user-photo" src="https://s3-ap-southeast-1.amazonaws.com/iposb/images/avatar/<%=avatarPath %>/<%=id %>/avatar.jpg?<%=avatarDT %>" onerror="<%=resPath %>/assets/avatars/user.png"/> -->
						<img class="nav-user-photo" src="<%=resPath %>/assets/avatars/user.png"/>
						<span class="user-info">
							<small>Hi,</small>
							<%=userName.equals("")? userId : userName %>
						</span>

						<i class="icon-caret-down"></i>
					</a>

					<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
						<li>
							<a href="staffprofile">
								<i class="fa fa-user fa-lg"></i>
								<%=Resource.getString("ID_LABEL_MYPROFILE", locale)%>
							</a>
						</li>

						<li class="divider"></li>
						
						<li>
							<a href="logout">
								<i class="fa fa-sign-out fa-lg"></i>
								<%=Resource.getString("ID_LABEL_LOGOUT", locale)%>
							</a>
						</li>
					</ul>
				</li>
			</ul><!-- /.ace-nav -->
		</div><!-- /.navbar-header -->
	</div><!-- /.container -->
</div>

<g:compress>
<script>

	$(function() {
		$( "#showNotification" ).click(function() {
			
			var nowClosed = $( ".purple" ).hasClass( "open" );//open notification window?  true: close  false: open  

			if(!nowClosed) {
				$('.purple ul li:not(:first)').remove();
				$(".purple ul").append("<li id=\"notificationLoading\" class=\"center\"><i class=\"fa fa-circle-o-notch fa-spin\"></i></li>");
				
				$.ajax({
					type: 'POST',
					url: './notification',
					data:{    
						lang: "<%=locale %>",
						actionType:"checkUnread"
			   		},  
			   		
					success: function(response) {
						$('#notificationLoading').remove();
						$(".purple ul").append(response);
					} 
				});
			}
		});
	});
	
	var openNotification = function(event1, event2) {
		var nid = event1;
		var url = event2;
		
		$.ajax({
			type: 'POST',
			url: './notification',
			data:{
				nid: nid,
				actionType:"markRead"
	   		},  
	   		
			success: function(response) {
				window.location.replace(url);
			} 
		});

	};
	
	
    var source = new EventSource("./notifier");

	source.onmessage = function(event) {
        if (eval(event.data) > 0) {
			$( "i.icon-bell-alt" ).toggleClass( "icon-animated-bell" );
			$("#notificationCount1").html(event.data);
			$("#notificationCount2").html(event.data);
		}
    };
</script>
</g:compress>