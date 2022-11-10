<%@include file="../include.jsp" %>
<%
	String bookType = request.getParameter("bookType") == null ? "" : request.getParameter("bookType").toString();
	String actionType = "ajaxSearchMember";
	
	if(bookType.equals("partner")) {
		actionType = "ajaxSearchPartner";
%>

	<div class="modal fade" id="model" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h2 class="modal-title" id=""><%=Resource.getString("ID_LABEL_MYPROFILE",locale)%>&nbsp;<span id="memberUserId"></span></h2>
				</div>
				<div class="modal-body">
						
					<div class="space-4"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_EMAIL",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="email"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_ENAME",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="ename"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_ABBREVIATION",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="cname"></span>
						</div>
					</div>
					
					<div class="space-10 areaDiv" style="clear:both;"></div>
					
					<div class="areaDiv" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_AREA",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="area"></span>
						</div>
					</div>
					
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_WEBSITE",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="website"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_OFFICIALEMAIL",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="officialEmail"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_CONTACTNAME",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="contactPerson"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="phone"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_ADDRESS",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="address"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_COMPANYLICENSE",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="companyLicense"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_GST",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="gst"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_REGISTERDT",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="createDT"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_REGISTERIP",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="registerIP"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_LASTIP",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="lastIP"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_LASTLOGINDT",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="lastLoginDT"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-4 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_LOGINTIMES",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-8 control-label" style="text-align:left;">
							<span id="loginTimes"></span>
						</div>
					</div>
								
						
				</div>
				<div id="loading" style="text-align: center;"></div>
				<div class="modal-footer">
					<div id="errmsg" class="alert alert-danger fade in alert-dismissable" style="display:none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<strong><%=Resource.getString("ID_MSG_ERRORMSG",locale)%></strong> <span id="membermsg"></span>
					</div> 
					<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
				</div>
			</div>
		</div>
	</div>
	
<% } else { %>

	<div class="modal fade" id="model" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h2 class="modal-title" id=""><%=Resource.getString("ID_LABEL_ABOUT",locale)%>&nbsp;<span id="memberUserId"></span></h2>
				</div>
				<div class="modal-body">
						
					<div class="space-4"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_EMAIL",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="email"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_ENAME",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="ename"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_CNAME",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="cname"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_GENDER",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="gender"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="phone"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_REGISTERDT",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="createDT"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_REGISTERIP",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="registerIP"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_LASTIP",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="lastIP"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_LASTLOGINDT",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="lastLoginDT"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_LOGINTIMES",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<span id="loginTimes"></span>
						</div>
					</div>
					
					<div class="space-10" style="clear:both;"></div>
					<div class="" style="font-size:15px;">
						<label class="col-sm-3 control-label" style="text-align:right;"> <%=Resource.getString("ID_LABEL_TOTALCONSIGNMENT",locale) + Resource.getString("ID_LABEL_COLON",locale)%></label>
						<div class="col-sm-9 control-label" style="text-align:left;">
							<a id="totalBooking" href="" target="_blank"><span id="totalBookingB"></span></a>
						</div>
					</div>					
						
				</div>
				<div id="loading" style="text-align: center;"></div>
				<div class="modal-footer">
					<div id="errmsg" class="alert alert-danger fade in alert-dismissable" style="display:none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<strong><%=Resource.getString("ID_MSG_ERRORMSG",locale)%></strong> <span id="membermsg"></span>
					</div> 
					<button type="button" id="closeBtn" class="btn btn-default pull-right" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CLOSE",locale) %></button>
				</div>
			</div>
		</div>
	</div>
<% } %>

	<script type="text/javascript">
		$(function(){
			$("a[id='userDetails']").click(function(){
				var userId = $(this).attr("userId");
				
				var pid = $(this).attr("pid");
				var mid = $(this).attr("mid");
				$("#model #loading").show();
				$("#model .modal-body").hide();
				$("#model #loading").html("<br /><br /><i class='fa fa-circle-o-notch fa-spin fa-2x'></i><br /><br />");
				$("#model").modal();
				
				$.ajax({
					type: 'POST',
					url: './logon',
					dataType: "json",
					data:{    
						lang: "<%=locale %>",
						actionType: "<%=actionType %>",
						mid: mid,
						pid: pid
					},  
					
					success: function(response) {
						
						
					<% if(bookType.equals("partner")) { %>
							
						if(response.error == "success"){
							var email = response.email;
							var ename = response.ename;
							var cname = response.cname;
							var website = response.website;
							var officialEmail = response.officialEmail;
							var contactPerson = response.contactPerson;
							var phone = response.phone;
							var address = response.address;
							var companyLicense = response.companyLicense;
							var gst = response.gst;
							var createDT = response.createDT;
							var registerIP = response.registerIP;
							var lastIP = response.lastIP;
							var lastLoginDT = response.lastLoginDT;
							var loginTimes = response.loginTimes;
							var totalBooking = response.totalBooking;
							
							var areaName = $("#areaName_"+pid).html();
							if(trim(areaName) == "") $("#model .areaDiv").hide();
							else if(trim(areaName) != "") $("#model .areaDiv").show();
							
							$("#model #email").html(email);
							$("#model #ename").html(ename);
							$("#model #cname").html(cname);
							$("#model #area").html(areaName);
							$("#model #website").html(website);
							$("#model #officialEmail").html(officialEmail);
							$("#model #contactPerson").html(contactPerson);
							$("#model #phone").html(phone);
							$("#model #address").html(address);
							$("#model #companyLicense").html(companyLicense);
							$("#model #gst").html(gst);
							$("#model #createDT").html(createDT);
							$("#model #registerIP").html(registerIP);
							$("#model #lastIP").html(lastIP);
							$("#model #lastLoginDT").html(lastLoginDT);
							$("#model #loginTimes").html(loginTimes);
							$("#model #totalBooking").html(totalBooking);
							$("#model #membermsg").html("");
							$("#model #errmsg").hide();
							$("#model #loading").hide();
							$("#model .modal-body").show();
							//$("#model a[id='totalBooking']").attr("href", "booking?actionType=adminCheckPartner&check=" + userId + "");
							//$("#model #totalBookingB").html(totalBooking + " <%=Resource.getString("ID_LABEL_XBOOKING", locale) %>");
						} else if( (response.error == "noUserId") || (response.error == "noData") ){
							$("#model #email").html("--");
							$("#model #ename").html("--");
							$("#model #cname").html("--");
							$("#model #website").html("--");
							$("#model #officialEmail").html("--");
							$("#model #contactPerson").html("--");
							$("#model #phone").html("--");
							$("#model #address").html("--");
							$("#model #companyLicense").html("--");
							$("#model #gst").html("--");
							$("#model #createDT").html("--");
							$("#model #registerIP").html("--");
							$("#model #lastIP").html("--");
							$("#model #lastLoginDT").html("--");
							$("#model #loginTimes").html("--");
							//$("#model #totalBooking").html("--");
							$("#model #membermsg").html("<%=Resource.getString("ID_MSG_NODATA",locale)%>");
							$("#model #errmsg").show();
							$("#model #loading").hide();
							$("#model .modal-body").show();
						}
					
					<% } else { %>
							
						if(response.error == "success"){
							var email = response.email;
							var ename = response.ename;
							var cname = response.cname;
							var nationality = response.nationality;
							var gender = response.gender;
							var ageRange = response.ageRange;
							var phone = response.phone;
							var createDT = response.createDT;
							var registerIP = response.registerIP;
							var lastIP = response.lastIP;
							var lastLoginDT = response.lastLoginDT;
							var loginTimes = response.loginTimes;
							var totalBooking = response.totalBooking;
							
							$("#model #email").html(email);
							$("#model #ename").html(ename);
							$("#model #cname").html(cname);
							$("#model #nationality").attr("src", "<%=resPath %>/assets/L10N/common/assets/img/flag/" + nationality + ".gif");
							$("#model #nationality").attr("alt", nationality);
							$("#model #nationality").attr("title", nationality);
							$("#model #gender").html(gender);
							$("#model #ageRange").html(ageRange);
							$("#model #phone").html(phone);
							$("#model #createDT").html(createDT);
							$("#model #registerIP").html(registerIP);
							$("#model #lastIP").html(lastIP);
							$("#model #lastLoginDT").html(lastLoginDT);
							$("#model #loginTimes").html(loginTimes);
							$("#model #totalBooking").html(totalBooking);
							$("#model #membermsg").html("");
							$("#model #errmsg").hide();
							$("#model #loading").hide();
							$("#model .modal-body").show();
							$("#model a[id='totalBooking']").attr("href", "booking?actionType=adminCheckUser&check=" + userId + "");
							$("#model #totalBookingB").html(totalBooking + " <%=Resource.getString("ID_LABEL_XBOOKING", locale) %>");
						} else if( (response.error == "noUserId") || (response.error == "noData") ){
							$("#model #userId").html("--");
							$("#model #email").html("--");
							$("#model #ename").html("--");
							$("#model #cname").html("--");
							$("#model #nationality").html("--");
							$("#model #gender").html("--");
							$("#model #ageRange").html("--");
							$("#model #phone").html("--");
							$("#model #createDT").html("--");
							$("#model #registerIP").html("--");
							$("#model #lastIP").html("--");
							$("#model #lastLoginDT").html("--");
							$("#model #loginTimes").html("--");
							$("#model #totalBooking").html("--");
							$("#model #membermsg").html("<%=Resource.getString("ID_MSG_NODATA",locale)%>");
							$("#model #errmsg").show();
							$("#model #loading").hide();
							$("#model .modal-body").show();
						}
						
					<% } %>
					} 
				});
			});
		});
	</script>
