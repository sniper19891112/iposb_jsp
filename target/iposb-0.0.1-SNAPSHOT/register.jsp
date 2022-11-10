<%@page import="com.iposb.i18n.*, com.iposb.logon.*, com.iposb.utils.*, java.util.ArrayList, javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String showLang = "en";
	if(locale.equals("zh_CN")) {
		showLang = "zh-CN";
	}

	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel userData = new LogonDataModel();
	if(data != null && data.size() > 0){
		userData = (LogonDataModel)data.get(0);
	}
	
	String pagetitle = Resource.getString("ID_LABEL_REGISTERMEMBERACCOUNT",locale);
	String alreadySignup = Resource.getString("ID_LABEL_ALREADYSIGNUP",locale);
	if(role.equals("staff")) {
		pagetitle = Resource.getString("ID_LABEL_REGISTERSTAFFACCOUNT",locale);
		alreadySignup = "";
	}
	
	if(!userId.trim().equals("")){ //already login
		String url = "./my";
		if(role.equals("staff")) {
			url = "./cp";
		}
		response.sendRedirect(url);
        return;
	}
	
%>

<head>
    <title><%=pagetitle %></title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />
</head>	

<body>

	<jsp:include page="header.jsp" />
	
	<div class="banner padd">
		<div class="container">
			<!-- Image -->
			<img class="img-responsive" src="assets/img/crown-white.png" alt="" />
			<!-- Heading -->
			<h2 class="white"><%=Resource.getString("ID_LABEL_REGISTER",locale)%></h2>
			<ol class="breadcrumb">
				<li><a href="./"><%=Resource.getString("ID_LABEL_HOME",locale)%></a></li>
				<li class="active"><%=pagetitle %></li>
			</ol>
			<div class="clearfix"></div>
		</div>
	</div>
	
    <!--=== Content Part ===-->

    <div class="inner-page padd">	
    	<div class="row">
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">	
					
				<div class="booking-form">
					<h3><%=pagetitle %></h3>
					<p><%=alreadySignup %></p>
					<hr>
					
					<div id="errmsg" class="alert alert-danger fade in alert-dismissable" style="display:none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<strong><%=Resource.getString("ID_MSG_ERRORMSG",locale)%></strong> <span id="msg"></span>
					</div>

					<form id="registerForm" name="registerForm" method="post" action="./logon" role="form">
						<input type="hidden" id="actionType" name="actionType" value="register">
						<input type="hidden" id="formValues" name="formValues">
						<input type="hidden" id="captchatoken" name="captchatoken">
						<input type="hidden" id="role" name="role" value="<%=role %>">
						<input type="hidden" id="email_Exist" name="email_Exist">
						<input type="hidden" id="email_Current" name="email_Current" value="<%=userData.getEmail_Current() %>">
					
						<label><%=Resource.getString("ID_LABEL_EMAIL",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="email" id="email" name="email" value="<%=userData.getEmail() %>" maxlength="120" onblur="checkExist('email')" />
							<span id="checkingSpan_email"></span>
						</div>

						<label><%=Resource.getString("ID_LABEL_PASSWORD",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="password" id="passwd" name="passwd" maxlength="12" />
						</div>
						
						<hr>

						<label><%=Resource.getString("ID_LABEL_ENAME",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<input class="form-control" type="text" id="ename" name="ename" value="<%=userData.getEname() %>" maxlength="50" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_CNAME",locale)%></label>
						<div class="form-group">
							<input class="form-control" type="text" id="cname" name="cname" value="<%=userData.getCname() %>" maxlength="15" />
						</div>
						
						<label><%=Resource.getString("ID_LABEL_NATIONALITY",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<%
								String cCode = "";
								if(userData.getNationality().equals("")) {
									cCode = IPTool.countryLookup(request, "code");
								}
							%>
							<input type="hidden" id="nationality" name="nationality" value="<%=userData.getNationality() %>" />
							<select name="cc" id="cc" onChange="setContryCode(this.form.cc)" class="form-control margin-bottom-20">
								<option value="" <% if(userData.getNationality().equals("") || cCode.equals("")) out.print("selected"); %>></option>
								<option value="BN-673" <% if(userData.getNationality().equals("BN") || cCode.equals("BN")) out.print("selected"); %>>Brunei</option>
								<option value="MY-60" <% if(userData.getNationality().equals("MY") || cCode.equals("MY")) out.print("selected"); %>>Malaysia</option>
								<option value="SG-65" <% if(userData.getNationality().equals("SG") || cCode.equals("SG")) out.print("selected"); %>>Singapore</option>
								<option value=""> ----------------------- </option>
								<option value="AF-93" <% if(userData.getNationality().equals("AF") || cCode.equals("AF")) out.print("selected"); %>>Afghanistan</option>
								<option value="AL-355" <% if(userData.getNationality().equals("AL") || cCode.equals("AL")) out.print("selected"); %>>Albania</option>
								<option value="DZ-213" <% if(userData.getNationality().equals("DZ") || cCode.equals("DZ")) out.print("selected"); %>>Algeria</option>
								<option value="AS-1684" <% if(userData.getNationality().equals("AS") || cCode.equals("AS")) out.print("selected"); %>>American Samoa</option>
								<option value="AD-376" <% if(userData.getNationality().equals("AD") || cCode.equals("AD")) out.print("selected"); %>>Andorra</option>
								<option value="AO-244" <% if(userData.getNationality().equals("AO") || cCode.equals("AO")) out.print("selected"); %>>Angola</option>
								<option value="AI-1264" <% if(userData.getNationality().equals("AI") || cCode.equals("AI")) out.print("selected"); %>>Anguilla</option>
								<option value="AR-54" <% if(userData.getNationality().equals("AR") || cCode.equals("AR")) out.print("selected"); %>>Argentina</option>
								<option value="AM-374" <% if(userData.getNationality().equals("AM") || cCode.equals("AM")) out.print("selected"); %>>Armenia</option>
								<option value="AW-297" <% if(userData.getNationality().equals("AW") || cCode.equals("AW")) out.print("selected"); %>>Aruba</option>
								<option value="AU-61" <% if(userData.getNationality().equals("AU") || cCode.equals("AU")) out.print("selected"); %>>Australia</option>
								<option value="AT-43" <% if(userData.getNationality().equals("AT") || cCode.equals("AT")) out.print("selected"); %>>Austria</option>
								<option value="AZ-994" <% if(userData.getNationality().equals("AZ") || cCode.equals("AZ")) out.print("selected"); %>>Azerbaijan</option>
								<option value="BS-1242" <% if(userData.getNationality().equals("BS") || cCode.equals("BS")) out.print("selected"); %>>Bahamas</option>
								<option value="BH-973" <% if(userData.getNationality().equals("BH") || cCode.equals("BH")) out.print("selected"); %>>Bahrain</option>
								<option value="BD-880" <% if(userData.getNationality().equals("BD") || cCode.equals("BD")) out.print("selected"); %>>Bangladesh</option>
								<option value="BB-1246" <% if(userData.getNationality().equals("BB") || cCode.equals("BB")) out.print("selected"); %>>Barbados</option>
								<option value="BY-375" <% if(userData.getNationality().equals("BY") || cCode.equals("BY")) out.print("selected"); %>>Belarus</option>
								<option value="BE-32" <% if(userData.getNationality().equals("BE") || cCode.equals("BE")) out.print("selected"); %>>Belgium</option>
								<option value="BZ-501" <% if(userData.getNationality().equals("BZ") || cCode.equals("BZ")) out.print("selected"); %>>Belize</option>
								<option value="BJ-229" <% if(userData.getNationality().equals("BJ") || cCode.equals("BJ")) out.print("selected"); %>>Benin</option>
								<option value="BT-975" <% if(userData.getNationality().equals("BT") || cCode.equals("BT")) out.print("selected"); %>>Bhutan</option>
								<option value="BO-591" <% if(userData.getNationality().equals("BO") || cCode.equals("BO")) out.print("selected"); %>>Bolivia</option>
								<option value="BA-387" <% if(userData.getNationality().equals("BA") || cCode.equals("BA")) out.print("selected"); %>>Bosnia and Herzegovina</option>
								<option value="BW-267" <% if(userData.getNationality().equals("BW") || cCode.equals("BW")) out.print("selected"); %>>Botswana</option>
								<option value="BR-55" <% if(userData.getNationality().equals("BR") || cCode.equals("BR")) out.print("selected"); %>>Brazil</option>
								<option value="BG-359" <% if(userData.getNationality().equals("BG") || cCode.equals("BG")) out.print("selected"); %>>Bulgaria</option>
								<option value="BF-226" <% if(userData.getNationality().equals("BF") || cCode.equals("BF")) out.print("selected"); %>>Burkina Faso</option>
								<option value="BI-257" <% if(userData.getNationality().equals("BI") || cCode.equals("BI")) out.print("selected"); %>>Burundi</option>
								<option value="KH-855" <% if(userData.getNationality().equals("KH") || cCode.equals("KH")) out.print("selected"); %>>Cambodia</option>
								<option value="CM-237" <% if(userData.getNationality().equals("CM") || cCode.equals("CM")) out.print("selected"); %>>Cameroon</option>
								<option value="CA-1" <% if(userData.getNationality().equals("CA") || cCode.equals("CA")) out.print("selected"); %>>Canada</option>
								<option value="CV-238" <% if(userData.getNationality().equals("CV") || cCode.equals("CV")) out.print("selected"); %>>Cape Verde</option>
								<option value="CF-236" <% if(userData.getNationality().equals("CF") || cCode.equals("CF")) out.print("selected"); %>>Central African Republic</option>
								<option value="TD-235" <% if(userData.getNationality().equals("TD") || cCode.equals("TD")) out.print("selected"); %>>Chad</option>
								<option value="CL-56" <% if(userData.getNationality().equals("CL") || cCode.equals("CL")) out.print("selected"); %>>Chile</option>
								<option value="CN-86" <% if(userData.getNationality().equals("CN") || cCode.equals("CN")) out.print("selected"); %>>China</option>
								<option value="CO-57" <% if(userData.getNationality().equals("CO") || cCode.equals("CO")) out.print("selected"); %>>Colombia</option>
								<option value="KM-269" <% if(userData.getNationality().equals("KM") || cCode.equals("ARKM")) out.print("selected"); %>>Comoros</option>
								<option value="CR-506" <% if(userData.getNationality().equals("CR") || cCode.equals("CR")) out.print("selected"); %>>Costa Rica</option>
								<option value="HR-385" <% if(userData.getNationality().equals("HR") || cCode.equals("HR")) out.print("selected"); %>>Croatia</option>
								<option value="CU-53" <% if(userData.getNationality().equals("CU") || cCode.equals("CU")) out.print("selected"); %>>Cuba</option>
								<option value="CY-357" <% if(userData.getNationality().equals("CY") || cCode.equals("CY")) out.print("selected"); %>>Cyprus</option>
								<option value="CZ-420" <% if(userData.getNationality().equals("CZ") || cCode.equals("CZ")) out.print("selected"); %>>Czech Republic</option>
								<option value="CD-243" <% if(userData.getNationality().equals("CD") || cCode.equals("CD")) out.print("selected"); %>>Democratic Republic of Congo</option>
								<option value="DK-45" <% if(userData.getNationality().equals("DK") || cCode.equals("DK")) out.print("selected"); %>>Denmark</option>
								<option value="DJ-253" <% if(userData.getNationality().equals("DJ") || cCode.equals("DJ")) out.print("selected"); %>>Djibouti</option>
								<option value="DM-1767" <% if(userData.getNationality().equals("DM") || cCode.equals("DM")) out.print("selected"); %>>Dominica</option>
								<option value="DO-1809" <% if(userData.getNationality().equals("DO") || cCode.equals("DO")) out.print("selected"); %>>Dominican Republic</option>
								<option value="EC-593" <% if(userData.getNationality().equals("EC") || cCode.equals("EC")) out.print("selected"); %>>Ecuador</option>
								<option value="EG-20" <% if(userData.getNationality().equals("EG") || cCode.equals("EG")) out.print("selected"); %>>Egypt</option>
								<option value="SV-503" <% if(userData.getNationality().equals("SV") || cCode.equals("SV")) out.print("selected"); %>>El Salvador</option>
								<option value="GQ-240" <% if(userData.getNationality().equals("GQ") || cCode.equals("GQ")) out.print("selected"); %>>Equatorial Guinea</option>
								<option value="ER-291" <% if(userData.getNationality().equals("ER") || cCode.equals("ER")) out.print("selected"); %>>Eritrea</option>
								<option value="EE-372" <% if(userData.getNationality().equals("EE") || cCode.equals("EE")) out.print("selected"); %>>Estonia</option>
								<option value="ET-251" <% if(userData.getNationality().equals("ET") || cCode.equals("ET")) out.print("selected"); %>>Ethiopia</option>
								<option value="FI-358" <% if(userData.getNationality().equals("FI") || cCode.equals("FI")) out.print("selected"); %>>Finland</option>
								<option value="FR-33" <% if(userData.getNationality().equals("FR") || cCode.equals("FR")) out.print("selected"); %>>France</option>
								<option value="PF-689" <% if(userData.getNationality().equals("PF") || cCode.equals("PF")) out.print("selected"); %>>French Polynesia</option>
								<option value="GA-241" <% if(userData.getNationality().equals("GA") || cCode.equals("GA")) out.print("selected"); %>>Gabon</option>
								<option value="GM-220" <% if(userData.getNationality().equals("GM") || cCode.equals("GM")) out.print("selected"); %>>Gambia</option>
								<option value="GE-995" <% if(userData.getNationality().equals("GE") || cCode.equals("GE")) out.print("selected"); %>>Georgia</option>
								<option value="DE-49" <% if(userData.getNationality().equals("DE") || cCode.equals("DE")) out.print("selected"); %>>Germany</option>
								<option value="GH-233" <% if(userData.getNationality().equals("GH") || cCode.equals("GH")) out.print("selected"); %>>Ghana</option>
								<option value="GI-350" <% if(userData.getNationality().equals("GI") || cCode.equals("GI")) out.print("selected"); %>>Gibraltar</option>
								<option value="GR-30" <% if(userData.getNationality().equals("GR") || cCode.equals("GR")) out.print("selected"); %>>Greece</option>
								<option value="GL-299" <% if(userData.getNationality().equals("GL") || cCode.equals("GL")) out.print("selected"); %>>Greenland</option>
								<option value="GD-1473" <% if(userData.getNationality().equals("GD") || cCode.equals("GD")) out.print("selected"); %>>Grenada</option>
								<option value="GU-1671" <% if(userData.getNationality().equals("GU") || cCode.equals("GU")) out.print("selected"); %>>Guam</option>
								<option value="GT-502" <% if(userData.getNationality().equals("GT") || cCode.equals("GT")) out.print("selected"); %>>Guatemala</option>
								<option value="GN-224" <% if(userData.getNationality().equals("GN") || cCode.equals("GN")) out.print("selected"); %>>Guinea</option>
								<option value="GW-245" <% if(userData.getNationality().equals("GW") || cCode.equals("GW")) out.print("selected"); %>>Guinea-Bissau</option>
								<option value="GY-592" <% if(userData.getNationality().equals("GY") || cCode.equals("GY")) out.print("selected"); %>>Guyana</option>
								<option value="HT-509" <% if(userData.getNationality().equals("HT") || cCode.equals("HT")) out.print("selected"); %>>Haiti</option>
								<option value="HN-504" <% if(userData.getNationality().equals("HN") || cCode.equals("HN")) out.print("selected"); %>>Honduras</option>
								<option value="HK-852" <% if(userData.getNationality().equals("HK") || cCode.equals("HK")) out.print("selected"); %>>Hong Kong</option>
								<option value="HU-36" <% if(userData.getNationality().equals("HU") || cCode.equals("HU")) out.print("selected"); %>>Hungary</option>
								<option value="IS-354" <% if(userData.getNationality().equals("IS") || cCode.equals("IS")) out.print("selected"); %>>Iceland</option>
								<option value="IN-91" <% if(userData.getNationality().equals("IN") || cCode.equals("IN")) out.print("selected"); %>>India</option>
								<option value="ID-62" <% if(userData.getNationality().equals("ID") || cCode.equals("ID")) out.print("selected"); %>>Indonesia</option>
								<option value="IR-98" <% if(userData.getNationality().equals("IR") || cCode.equals("IR")) out.print("selected"); %>>Iran</option>
								<option value="IQ-964" <% if(userData.getNationality().equals("IQ") || cCode.equals("IQ")) out.print("selected"); %>>Iraq</option>
								<option value="IE-353" <% if(userData.getNationality().equals("IE") || cCode.equals("IE")) out.print("selected"); %>>Ireland</option>
								<option value="IL-972" <% if(userData.getNationality().equals("IL") || cCode.equals("IL")) out.print("selected"); %>>Israel</option>
								<option value="IT-39" <% if(userData.getNationality().equals("IT") || cCode.equals("IT")) out.print("selected"); %>>Italy</option>
								<option value="CI-225" <% if(userData.getNationality().equals("CI") || cCode.equals("CI")) out.print("selected"); %>>Ivory Coast</option>
								<option value="JM-1876" <% if(userData.getNationality().equals("JM") || cCode.equals("JM")) out.print("selected"); %>>Jamaica</option>
								<option value="JP-81" <% if(userData.getNationality().equals("JP") || cCode.equals("JP")) out.print("selected"); %>>Japan</option>
								<option value="JO-962" <% if(userData.getNationality().equals("JO") || cCode.equals("JO")) out.print("selected"); %>>Jordan</option>
								<option value="KZ-7" <% if(userData.getNationality().equals("KZ") || cCode.equals("KZ")) out.print("selected"); %>>Kazakhstan</option>
								<option value="KE-254" <% if(userData.getNationality().equals("KE") || cCode.equals("KE")) out.print("selected"); %>>Kenya</option>
								<option value="KI-686" <% if(userData.getNationality().equals("KI") || cCode.equals("KI")) out.print("selected"); %>>Kiribati</option>
								<option value="KW-965" <% if(userData.getNationality().equals("KW") || cCode.equals("KW")) out.print("selected"); %>>Kuwait</option>
								<option value="KG-996" <% if(userData.getNationality().equals("KG") || cCode.equals("KG")) out.print("selected"); %>>Kyrgyzstan</option>
								<option value="LA-856" <% if(userData.getNationality().equals("LA") || cCode.equals("LA")) out.print("selected"); %>>Laos</option>
								<option value="LV-371" <% if(userData.getNationality().equals("LV") || cCode.equals("LV")) out.print("selected"); %>>Latvia</option>
								<option value="LB-961" <% if(userData.getNationality().equals("LB") || cCode.equals("LB")) out.print("selected"); %>>Lebanon</option>
								<option value="LS-266" <% if(userData.getNationality().equals("LS") || cCode.equals("LS")) out.print("selected"); %>>Lesotho</option>
								<option value="LR-231" <% if(userData.getNationality().equals("LR") || cCode.equals("LR")) out.print("selected"); %>>Liberia</option>
								<option value="LY-218" <% if(userData.getNationality().equals("LY") || cCode.equals("LY")) out.print("selected"); %>>Libya</option>
								<option value="LI-423" <% if(userData.getNationality().equals("LI") || cCode.equals("LI")) out.print("selected"); %>>Liechtenstein</option>
								<option value="LT-370" <% if(userData.getNationality().equals("LT") || cCode.equals("LT")) out.print("selected"); %>>Lithuania</option>
								<option value="LU-352" <% if(userData.getNationality().equals("LU") || cCode.equals("LU")) out.print("selected"); %>>Luxembourg</option>
								<option value="MO-853" <% if(userData.getNationality().equals("MO") || cCode.equals("MO")) out.print("selected"); %>>Macau</option>
								<option value="MK-389" <% if(userData.getNationality().equals("MK") || cCode.equals("MK")) out.print("selected"); %>>Macedonia</option>
								<option value="MG-261" <% if(userData.getNationality().equals("MG") || cCode.equals("MG")) out.print("selected"); %>>Madagascar</option>
								<option value="MW-265" <% if(userData.getNationality().equals("MW") || cCode.equals("MW")) out.print("selected"); %>>Malawi</option>
								<option value="MV-960" <% if(userData.getNationality().equals("MV") || cCode.equals("MV")) out.print("selected"); %>>Maldives</option>
								<option value="ML-223" <% if(userData.getNationality().equals("ML") || cCode.equals("ML")) out.print("selected"); %>>Mali</option>
								<option value="MT-356" <% if(userData.getNationality().equals("MT") || cCode.equals("MT")) out.print("selected"); %>>Malta</option>
								<option value="MH-692" <% if(userData.getNationality().equals("MH") || cCode.equals("MH")) out.print("selected"); %>>Marshall Islands</option>
								<option value="MR-222" <% if(userData.getNationality().equals("MR") || cCode.equals("MR")) out.print("selected"); %>>Mauritania</option>
								<option value="MU-230" <% if(userData.getNationality().equals("MU") || cCode.equals("MU")) out.print("selected"); %>>Mauritius</option>
								<option value="YT-262" <% if(userData.getNationality().equals("YT") || cCode.equals("YT")) out.print("selected"); %>>Mayotte</option>
								<option value="MX-52" <% if(userData.getNationality().equals("MX") || cCode.equals("MX")) out.print("selected"); %>>Mexico</option>
								<option value="MD-373" <% if(userData.getNationality().equals("MD") || cCode.equals("MD")) out.print("selected"); %>>Moldova</option>
								<option value="MC-377" <% if(userData.getNationality().equals("MC") || cCode.equals("MC")) out.print("selected"); %>>Monaco</option>
								<option value="MN-976" <% if(userData.getNationality().equals("MN") || cCode.equals("MN")) out.print("selected"); %>>Mongolia</option>
								<option value="ME-382" <% if(userData.getNationality().equals("ME") || cCode.equals("ME")) out.print("selected"); %>>Montenegro</option>
								<option value="MS-1664" <% if(userData.getNationality().equals("MS") || cCode.equals("MS")) out.print("selected"); %>>Montserrat</option>
								<option value="MA-212" <% if(userData.getNationality().equals("MA") || cCode.equals("MA")) out.print("selected"); %>>Morocco</option>
								<option value="MZ-258" <% if(userData.getNationality().equals("MZ") || cCode.equals("MZ")) out.print("selected"); %>>Mozambique</option>
								<option value="MM-976" <% if(userData.getNationality().equals("MM") || cCode.equals("MM")) out.print("selected"); %>>Myanmar</option>
								<option value="NA-264" <% if(userData.getNationality().equals("NA") || cCode.equals("NA")) out.print("selected"); %>>Namibia</option>
								<option value="NR-674" <% if(userData.getNationality().equals("NR") || cCode.equals("NR")) out.print("selected"); %>>Nauru</option>
								<option value="NP-977" <% if(userData.getNationality().equals("NP") || cCode.equals("NP")) out.print("selected"); %>>Nepal</option>
								<option value="NL-31" <% if(userData.getNationality().equals("NL") || cCode.equals("NL")) out.print("selected"); %>>Netherlands</option>
								<option value="AN-599" <% if(userData.getNationality().equals("AN") || cCode.equals("AN")) out.print("selected"); %>>Netherlands Antilles</option>
								<option value="NC-687" <% if(userData.getNationality().equals("NC") || cCode.equals("NC")) out.print("selected"); %>>New Caledonia</option>
								<option value="NZ-64" <% if(userData.getNationality().equals("NZ") || cCode.equals("NZ")) out.print("selected"); %>>New Zealand</option>
								<option value="NI-505" <% if(userData.getNationality().equals("NI") || cCode.equals("NI")) out.print("selected"); %>>Nicaragua</option>
								<option value="NE-227" <% if(userData.getNationality().equals("NE") || cCode.equals("NE")) out.print("selected"); %>>Niger</option>
								<option value="NG-234" <% if(userData.getNationality().equals("NG") || cCode.equals("NG")) out.print("selected"); %>>Nigeria</option>
								<option value="NU-683" <% if(userData.getNationality().equals("NU") || cCode.equals("NU")) out.print("selected"); %>>Niue</option>
								<option value="KP-850" <% if(userData.getNationality().equals("KP") || cCode.equals("KP")) out.print("selected"); %>>North Korea</option>
								<option value="NO-47" <% if(userData.getNationality().equals("NO") || cCode.equals("NO")) out.print("selected"); %>>Norway</option>
								<option value="OM-968" <% if(userData.getNationality().equals("OM") || cCode.equals("OM")) out.print("selected"); %>>Oman</option>
								<option value="PK-92" <% if(userData.getNationality().equals("PK") || cCode.equals("PK")) out.print("selected"); %>>Pakistan</option>
								<option value="PW-680" <% if(userData.getNationality().equals("PW") || cCode.equals("PW")) out.print("selected"); %>>Palau</option>
								<option value="PA-507" <% if(userData.getNationality().equals("PA") || cCode.equals("PA")) out.print("selected"); %>>Panama</option>
								<option value="PG-675" <% if(userData.getNationality().equals("PG") || cCode.equals("PG")) out.print("selected"); %>>Papua New Guinea</option>
								<option value="PY-595" <% if(userData.getNationality().equals("PY") || cCode.equals("PY")) out.print("selected"); %>>Paraguay</option>
								<option value="PE-51" <% if(userData.getNationality().equals("PE") || cCode.equals("PE")) out.print("selected"); %>>Peru</option>
								<option value="PH-62" <% if(userData.getNationality().equals("PH") || cCode.equals("PH")) out.print("selected"); %>>Philippines</option>
								<option value="PN-870" <% if(userData.getNationality().equals("PN") || cCode.equals("PN")) out.print("selected"); %>>Pitcairn Islands</option>
								<option value="PL-48" <% if(userData.getNationality().equals("PL") || cCode.equals("PL")) out.print("selected"); %>>Poland</option>
								<option value="PT-351" <% if(userData.getNationality().equals("PT") || cCode.equals("PT")) out.print("selected"); %>>Portugal</option>
								<option value="PR-1" <% if(userData.getNationality().equals("PR") || cCode.equals("PR")) out.print("selected"); %>>Puerto Rico</option>
								<option value="QA-974" <% if(userData.getNationality().equals("QA") || cCode.equals("QA")) out.print("selected"); %>>Qatar</option>
								<option value="RO-40" <% if(userData.getNationality().equals("RO") || cCode.equals("RO")) out.print("selected"); %>>Romania</option>
								<option value="RU-7" <% if(userData.getNationality().equals("RU") || cCode.equals("RU")) out.print("selected"); %>>Russia</option>
								<option value="RW-250" <% if(userData.getNationality().equals("RW") || cCode.equals("RW")) out.print("selected"); %>>Rwanda</option>
								<option value="SH-290" <% if(userData.getNationality().equals("SH") || cCode.equals("SH")) out.print("selected"); %>>Saint Helena and Dependencies</option>
								<option value="KN-1869" <% if(userData.getNationality().equals("KN") || cCode.equals("KN")) out.print("selected"); %>>Saint Kitts and Nevis</option>
								<option value="LC-1758" <% if(userData.getNationality().equals("LC") || cCode.equals("LC")) out.print("selected"); %>>Saint Lucia</option>
								<option value="PM-508" <% if(userData.getNationality().equals("PM") || cCode.equals("PM")) out.print("selected"); %>>Saint Pierre and Miquelon</option>
								<option value="VC-1784" <% if(userData.getNationality().equals("VC") || cCode.equals("VC")) out.print("selected"); %>>Saint Vincent and the Grenadines</option>
								<option value="WS-685" <% if(userData.getNationality().equals("WS") || cCode.equals("WS")) out.print("selected"); %>>Samoa</option>
								<option value="SM-378" <% if(userData.getNationality().equals("SM") || cCode.equals("SM")) out.print("selected"); %>>San Marino</option>
								<option value="ST-239" <% if(userData.getNationality().equals("ST") || cCode.equals("ST")) out.print("selected"); %>>Sao Tome and Principe</option>
								<option value="SA-966" <% if(userData.getNationality().equals("SA") || cCode.equals("SA")) out.print("selected"); %>>Saudi Arabia</option>
								<option value="SN-221" <% if(userData.getNationality().equals("SN") || cCode.equals("SN")) out.print("selected"); %>>Senegal</option>
								<option value="RS-381" <% if(userData.getNationality().equals("RS") || cCode.equals("RS")) out.print("selected"); %>>Serbia</option>
								<option value="SC-248" <% if(userData.getNationality().equals("SC") || cCode.equals("SC")) out.print("selected"); %>>Seychelles</option>
								<option value="SL-232" <% if(userData.getNationality().equals("SL") || cCode.equals("SL")) out.print("selected"); %>>Sierra Leone</option>
								<option value="SK-421" <% if(userData.getNationality().equals("SK") || cCode.equals("SK")) out.print("selected"); %>>Slovakia</option>
								<option value="SI-386" <% if(userData.getNationality().equals("SI") || cCode.equals("SI")) out.print("selected"); %>>Slovenia</option>
								<option value="SB-677" <% if(userData.getNationality().equals("SB") || cCode.equals("SB")) out.print("selected"); %>>Solomon Islands</option>
								<option value="ZA-27" <% if(userData.getNationality().equals("ZA") || cCode.equals("ZA")) out.print("selected"); %>>South Africa</option>
								<option value="KR-82" <% if(userData.getNationality().equals("KR") || cCode.equals("KR")) out.print("selected"); %>>South Korea</option>
								<option value="ES-34" <% if(userData.getNationality().equals("ES") || cCode.equals("ES")) out.print("selected"); %>>Spain</option>
								<option value="LK-94" <% if(userData.getNationality().equals("LK") || cCode.equals("LK")) out.print("selected"); %>>Sri Lanka</option>
								<option value="SD-249" <% if(userData.getNationality().equals("SD") || cCode.equals("SD")) out.print("selected"); %>>Sudan</option>
								<option value="SR-597" <% if(userData.getNationality().equals("SR") || cCode.equals("SR")) out.print("selected"); %>>Suriname</option>
								<option value="SZ-268" <% if(userData.getNationality().equals("SZ") || cCode.equals("SZ")) out.print("selected"); %>>Swaziland</option>
								<option value="SE-46" <% if(userData.getNationality().equals("SE") || cCode.equals("SE")) out.print("selected"); %>>Sweden</option>
								<option value="CH-41" <% if(userData.getNationality().equals("CH") || cCode.equals("CH")) out.print("selected"); %>>Switzerland</option>
								<option value="SY-963" <% if(userData.getNationality().equals("SY") || cCode.equals("SY")) out.print("selected"); %>>Syria</option>
								<option value="TW-886" <% if(userData.getNationality().equals("TW") || cCode.equals("TW")) out.print("selected"); %>>Taiwan</option>
								<option value="TJ-992" <% if(userData.getNationality().equals("TJ") || cCode.equals("TJ")) out.print("selected"); %>>Tajikistan</option>
								<option value="TZ-255" <% if(userData.getNationality().equals("TZ") || cCode.equals("TZ")) out.print("selected"); %>>Tanzania</option>
								<option value="TH-66" <% if(userData.getNationality().equals("TH") || cCode.equals("TH")) out.print("selected"); %>>Thailand</option>
								<option value="TG-228" <% if(userData.getNationality().equals("TG") || cCode.equals("TG")) out.print("selected"); %>>Togo</option>
								<option value="TK-690" <% if(userData.getNationality().equals("TK") || cCode.equals("TK")) out.print("selected"); %>>Tokelau</option>
								<option value="TO-676" <% if(userData.getNationality().equals("TO") || cCode.equals("TO")) out.print("selected"); %>>Tonga</option>
								<option value="TN-216" <% if(userData.getNationality().equals("TN") || cCode.equals("TN")) out.print("selected"); %>>Tunisia</option>
								<option value="TR-90" <% if(userData.getNationality().equals("TR") || cCode.equals("TR")) out.print("selected"); %>>Turkey</option>
								<option value="TM-993" <% if(userData.getNationality().equals("TM") || cCode.equals("TM")) out.print("selected"); %>>Turkmenistan</option>
								<option value="TV-688" <% if(userData.getNationality().equals("TV") || cCode.equals("TV")) out.print("selected"); %>>Tuvalu</option>
								<option value="UG-256" <% if(userData.getNationality().equals("UG") || cCode.equals("UG")) out.print("selected"); %>>Uganda</option>
								<option value="UA-380" <% if(userData.getNationality().equals("UA") || cCode.equals("UA")) out.print("selected"); %>>Ukraine</option>
								<option value="AE-971" <% if(userData.getNationality().equals("AE") || cCode.equals("AE")) out.print("selected"); %>>United Arab Emirates</option>
								<option value="UK-44" <% if(userData.getNationality().equals("UK") || cCode.equals("UK")) out.print("selected"); %>>United Kingdom</option>
								<option value="US-1" <% if(userData.getNationality().equals("US") || cCode.equals("US")) out.print("selected"); %>>United States</option>
								<option value="UY-598" <% if(userData.getNationality().equals("UY") || cCode.equals("UY")) out.print("selected"); %>>Uruguay</option>
								<option value="UZ-998" <% if(userData.getNationality().equals("UZ") || cCode.equals("UZ")) out.print("selected"); %>>Uzbekistan</option>
								<option value="UV-678" <% if(userData.getNationality().equals("UV") || cCode.equals("UV")) out.print("selected"); %>>Vanuatu</option>
								<option value="VE-58" <% if(userData.getNationality().equals("VE") || cCode.equals("VE")) out.print("selected"); %>>Venezuela</option>
								<option value="VN-84" <% if(userData.getNationality().equals("VN") || cCode.equals("VN")) out.print("selected"); %>>Vietnam</option>
								<option value="WF-681" <% if(userData.getNationality().equals("WF") || cCode.equals("WF")) out.print("selected"); %>>Wallis and Futuna</option>
								<option value="YE-967" <% if(userData.getNationality().equals("YE") || cCode.equals("YE")) out.print("selected"); %>>Yemen</option>
								<option value="ZM-260" <% if(userData.getNationality().equals("ZM") || cCode.equals("ZM")) out.print("selected"); %>>Zambia</option>
								<option value="ZW-263" <% if(userData.getNationality().equals("ZW") || cCode.equals("ZW")) out.print("selected"); %>>Zimbabwe</option>
							</select>
						</div>
						
						<div class="row form-group">
	                        <div class="col-sm-3">
	                            <label><%=Resource.getString("ID_LABEL_COUNTRYCODE",locale)%></label>
								<input id="countryCode" name="countryCode" type="text" class="form-control" value="<%=userData.getCountryCode() %>" maxlength="4" /> 
	                        </div>
	                        <div class="col-sm-9">
	                            <label><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%> <font color="red">*</font></label>
								<input id="phone" name="phone" type="text" class="form-control" maxlength="30" value="<%=userData.getPhone() %>" onBlur="trimZero()" />
	                        </div>
	                    </div>

						<label><%=Resource.getString("ID_LABEL_GENDER",locale)%> <font color="red">*</font></label>
						<div class="form-group">
							<select class="form-control" id="gender" name="gender">
								<option value="0" <% if(userData.getGender()==0) out.print("selected"); %>></option>
								<option value="1" <% if(userData.getGender()==1) out.print("selected"); %>><%=Resource.getString("ID_LABEL_MALE",locale)%></option>
								<option value="2" <% if(userData.getGender()==2) out.print("selected"); %>><%=Resource.getString("ID_LABEL_FEMALE",locale)%></option>
							</select>
						</div>
						
						<label><%=Resource.getString("ID_LABEL_DOB",locale)%> </label>
						<input id="dob" name="dob" type="hidden" />
						<% 
					        String dob = userData.getDob();
	                   		String dobYear = "0";
	                   		String dobMonth = "0";
	                   		String dobDay = "0";
	                   		String selected = "";
	                   		if(dob.length() == 10){
	                   			dobYear = dob.substring(0, 4);
	                   			dobMonth = dob.substring(5, 7);
	                   			dobDay = dob.substring(8, 10);
	                   		}
				        
				        %>
						<div class="form-group">
							<div class="col-sm-4">
								<select id="year" name="year" class="form-control">
			                    	<option value=""> - <%=Resource.getString("ID_LABEL_YEAR",locale)%> - </option>
			                    	<%
			                    		int year = 1940;
			                    	
			                    		for(int y = 0; y < 76; y++){
			                    			if(Integer.parseInt(dobYear) == year){
			                    				selected = "selected";
			                    			}
			                    			out.println("<option " + selected + " value=\"" + year + "\">" + year + "</option>");
			                    			year++;
			                    			selected = "";
			                    		}
			                    	%>
			                    </select>
	                        </div>
	                        <div class="col-sm-4">
	                            <select id="month" name="month" class="form-control">
			                    	<option <%if(dobMonth.equals("00")){out.println("selected");} %> value="00"> - <%=Resource.getString("ID_LABEL_MONTH",locale)%> - </option>
									<option <%if(dobMonth.equals("01")){out.println("selected");} %> value="01"><%=Resource.getString("ID_LABEL_JAN",locale)%></option>
									<option <%if(dobMonth.equals("02")){out.println("selected");} %> value="02"><%=Resource.getString("ID_LABEL_FEB",locale)%></option>
									<option <%if(dobMonth.equals("03")){out.println("selected");} %> value="03"><%=Resource.getString("ID_LABEL_MAR",locale)%></option>
									<option <%if(dobMonth.equals("04")){out.println("selected");} %> value="04"><%=Resource.getString("ID_LABEL_APR",locale)%></option>
									<option <%if(dobMonth.equals("05")){out.println("selected");} %> value="05"><%=Resource.getString("ID_LABEL_MAY",locale)%></option>
									<option <%if(dobMonth.equals("06")){out.println("selected");} %> value="06"><%=Resource.getString("ID_LABEL_JUN",locale)%></option>
									<option <%if(dobMonth.equals("07")){out.println("selected");} %> value="07"><%=Resource.getString("ID_LABEL_JUL",locale)%></option>
									<option <%if(dobMonth.equals("08")){out.println("selected");} %> value="08"><%=Resource.getString("ID_LABEL_AUG",locale)%></option>
									<option <%if(dobMonth.equals("09")){out.println("selected");} %> value="09"><%=Resource.getString("ID_LABEL_SEP",locale)%></option>
									<option <%if(dobMonth.equals("10")){out.println("selected");} %> value="10"><%=Resource.getString("ID_LABEL_OCT",locale)%></option>
									<option <%if(dobMonth.equals("11")){out.println("selected");} %> value="11"><%=Resource.getString("ID_LABEL_NOV",locale)%></option>
									<option <%if(dobMonth.equals("12")){out.println("selected");} %> value="12"><%=Resource.getString("ID_LABEL_DEC",locale)%></option>
			                    </select>
	                        </div>
	                        <div class="col-sm-4">
	                            <select id="day" name="day" class="form-control">
			                    	<option value=""> - <%=Resource.getString("ID_LABEL_DAY",locale)%> - </option>
			                    	<%
			                    		int day = 1;
			                    	
				                    	for(int y = 0; y < 31; y++){
			                    			if(Integer.parseInt(dobDay) == day){
			                    				selected = "selected";
			                    			}
			                    			if(day <= 9){
			                    				out.println("<option " + selected + " value=\"0" + day + "\">0" + day + "</option>");
			                    			}else{
				                    			out.println("<option " + selected + " value=\"" + day + "\">" + day + "</option>");
			                    			}
			                    			day++;
			                    			selected = "";
			                    		}
			                    	%>
			                    </select>
	                        </div>
						</div>
						
						<p>&nbsp;</p>
						
						<label><%=Resource.getString("ID_LABEL_IC",locale)%></label>
						<div class="form-group">
							<input class="form-control" type="text" id="IC" name="IC" value="<%=userData.getIC() %>" maxlength="15" />
						</div>
						
						<p>&nbsp;</p>
						<hr>
		                    
						<label></label>
						<div class="form-group">
							<input type="checkbox" id="agree" name="agree"> <%=Resource.getString("ID_LABEL_READANDAGREED",locale)%>
						</div>
						
						<hr>
						
						<div id="validateInput" class="alert alert-danger fade in alert-dismissable" style="display:none"></div>

						<button id="registerBtn" class="btn btn-orange" onclick="return false"><%=Resource.getString("ID_LABEL_REGISTER",locale)%></button>&nbsp;
						<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
						<p>&nbsp;</p>
						
					</form>
				</div>
        
            </div>
        </div><!--/row-->
    </div><!--/container-->	
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
	<script src="https://www.google.com/recaptcha/api.js?render=6Le5xeIZAAAAAFwZ4n2_rtC84IRfjDAYtUVftS5G"></script>
	
<g:compress>

	<script type="text/javascript" src="./assets/js/register.js"></script>
	<script type="text/javascript">
	
		jQuery(document).ready(function() {
			
			if("<%=userData.getErrmsg() %>" != ""){
				document.getElementById("errmsg").style.display = "block";
			}
			
			if("<%=userData.getErrmsg() %>" != ""){
				if("<%=userData.getErrmsg() %>" == "secureCode"){
					document.getElementById("msg").innerHTML = "<%=Resource.getString("ID_MSG_INVALIDSECURECODE",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "gRecaptcha"){
					document.getElementById("msg").innerHTML = "Please tick the reCaptcha";
				}
				else if("<%=userData.getErrmsg() %>" == "queryFailed"){
					document.getElementById("msg").innerHTML = "<%=Resource.getString("ID_MSG_QUERYFAILED",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "updateFailed"){
					document.getElementById("msg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATEFAILED",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "wrongPassword"){
					document.getElementById("msg").innerHTML = "<%=Resource.getString("ID_MSG_OLDPASSWORDNOTMATCH",locale)%>";
				}
				else if("<%=userData.getErrmsg() %>" == "secureCodeNull"){
					document.getElementById("msg").innerHTML = "<%=Resource.getString("ID_MSG_SYSTEMFAILURE",locale)%>";
				} else if("<%=userData.getErrmsg() %>" == "userIdEmailExist"){
					document.getElementById("msg").innerHTML = "<%=Resource.getString("ID_MSG_USERIDEMAILEXIST",locale)%>";
				}
				else {
					document.getElementById("msg").innerHTML = "<%=userData.getErrmsg() %>";
				}
			}
			
			
			if("<%=result %>" == "loginFailed"){
				document.getElementById("hint").innerHTML = "<font color='red'><%=Resource.getString("ID_MSG_LOGINFAILED",locale)%></font>";
				return false;
			}
			
			setContryCode(document.getElementById("cc"));
			
		});
		
		$(function() {
			$( "#registerBtn" ).click(function() {
		        grecaptcha.ready(function() {
		          grecaptcha.execute('6Le5xeIZAAAAAFwZ4n2_rtC84IRfjDAYtUVftS5G', {action: 'submit'}).then(function(token) {
		        	  	$('#captchatoken').val(token);
						submitCheck('register'); 
						return false; 
		          });
		        });
			});
		});
		
	</script>
	
</g:compress>
	
	
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-88105313-1', 'auto');
	  ga('send', 'pageview');
	
	</script>
</body>

</html>