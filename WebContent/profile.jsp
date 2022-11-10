<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();
	String role = request.getParameter("role") == null ? "" : request.getParameter("role").toString();

	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(LogonController.OBJECTDATA);
	LogonDataModel logonData = new LogonDataModel();
	if(data != null && data.size() > 0){
		logonData = (LogonDataModel)data.get(0);
		errmsg = logonData.getErrmsg();
	}
	
	/* if(priv<0){
		String url = "./login?returnUrl=./profile";
		if(role.equals("staff")) {
			url = "./stafflogin?returnUrl=./staffprofile";
		}
		response.sendRedirect(url);
        return;
	} */
	
%>

<head>
    <title><%=Resource.getString("ID_LABEL_PROFILE",locale)%></title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->
			

    <div class="inner-page padd">
			
		<div class="container">

			<div class="row">
				
				<jsp:include page="mySidebar.jsp?tab=profile" />
				
				<div class="col-md-9">
				
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="accountStatus" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="privStatus"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
							
					<form id="registerForm" name="registerForm" method="post" action="./logon" class="sky-form">
						<input type="hidden" id="actionType" name="actionType">
						<input type="hidden" id="role" name="role" value="<%=role %>">
						<input type="hidden" id="useLocale" name="useLocale" value="<%=locale %>">
						<input type="hidden" id="email_Exist" name="email_Exist">
						<input type="hidden" id="email_Current" name="email_Current" value="<%=logonData.getEmail() %>">
						
						<fieldset>
						
							<% if (logonData.getAccNo() != "" && logonData.getPrivilege() == 2) {%>
							
							<div style="float:left">
								<H4><font size="4"><small>Account Number: </small></font><strong><%=logonData.getAccNo() %></strong></H4>
								<label class="label"><%=logonData.getCname() %></label>
							</div>
							
							<% } %>
							
							<br>
													
							<section>
								<label class="label"><%=Resource.getString("ID_LABEL_EMAIL",locale)%></label>
	                            <label for="email" class="input">
	                                <input type="text" id="email" name="email" value="<%=logonData.getEmail() %>" maxlength="120" <%if(priv != 0){out.print(" readonly disabled ");}%> >
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_ENAME",locale)%> <font color="red">*</font></label>
	                            <label for="ename" class="input">
	                                <input type="text" id="ename" name="ename" value="<%=logonData.getEname() %>" maxlength="50">
	                            </label>
	                        </section>
	                        
	                        <section>
	                        	<% if (logonData.getPrivilege() == 2) {%>
								<label class="label">Company Name</label>
								<% } else { %>
								<label class="label"><%=Resource.getString("ID_LABEL_CNAME",locale)%></label>
								<% } %>
	                            <label for="cname" class="input">
	                                <input type="text" id="cname" name="cname" value="<%=logonData.getCname() %>" maxlength="50">
	                            </label>
	                        </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_NATIONALITY",locale)%> <font color="red">*</font></label>
	                            <label for="nationality" class="input">
	                                <input type="hidden" id="nationality" name="nationality" value="<%=logonData.getNationality() %>" />
									<select name="cc" id="cc" onChange="setContryCode(this.form.cc)" class="form-control margin-bottom-20">
										<option value="" <% if(logonData.getNationality().equals("")) out.print("selected"); %>></option>
										<option value="BN-673" <% if(logonData.getNationality().equals("BN")) out.print("selected"); %>>Brunei</option>
										<option value="MY-60" <% if(logonData.getNationality().equals("MY")) out.print("selected"); %>>Malaysia</option>
										<option value="SG-65" <% if(logonData.getNationality().equals("SG")) out.print("selected"); %>>Singapore</option>
										<option value=""> ----------------------- </option>
										<option value="AF-93" <% if(logonData.getNationality().equals("AF")) out.print("selected"); %>>Afghanistan</option>
										<option value="AL-355" <% if(logonData.getNationality().equals("AL")) out.print("selected"); %>>Albania</option>
										<option value="DZ-213" <% if(logonData.getNationality().equals("DZ")) out.print("selected"); %>>Algeria</option>
										<option value="AS-1684" <% if(logonData.getNationality().equals("AS")) out.print("selected"); %>>American Samoa</option>
										<option value="AD-376" <% if(logonData.getNationality().equals("AD")) out.print("selected"); %>>Andorra</option>
										<option value="AO-244" <% if(logonData.getNationality().equals("AO")) out.print("selected"); %>>Angola</option>
										<option value="AI-1264" <% if(logonData.getNationality().equals("AI")) out.print("selected"); %>>Anguilla</option>
										<option value="AR-54" <% if(logonData.getNationality().equals("AR")) out.print("selected"); %>>Argentina</option>
										<option value="AM-374" <% if(logonData.getNationality().equals("AM")) out.print("selected"); %>>Armenia</option>
										<option value="AW-297" <% if(logonData.getNationality().equals("AW")) out.print("selected"); %>>Aruba</option>
										<option value="AU-61" <% if(logonData.getNationality().equals("AU")) out.print("selected"); %>>Australia</option>
										<option value="AT-43" <% if(logonData.getNationality().equals("AT")) out.print("selected"); %>>Austria</option>
										<option value="AZ-994" <% if(logonData.getNationality().equals("AZ")) out.print("selected"); %>>Azerbaijan</option>
										<option value="BS-1242" <% if(logonData.getNationality().equals("BS")) out.print("selected"); %>>Bahamas</option>
										<option value="BH-973" <% if(logonData.getNationality().equals("BH")) out.print("selected"); %>>Bahrain</option>
										<option value="BD-880" <% if(logonData.getNationality().equals("BD")) out.print("selected"); %>>Bangladesh</option>
										<option value="BB-1246" <% if(logonData.getNationality().equals("BB")) out.print("selected"); %>>Barbados</option>
										<option value="BY-375" <% if(logonData.getNationality().equals("BY")) out.print("selected"); %>>Belarus</option>
										<option value="BE-32" <% if(logonData.getNationality().equals("BE")) out.print("selected"); %>>Belgium</option>
										<option value="BZ-501" <% if(logonData.getNationality().equals("BZ")) out.print("selected"); %>>Belize</option>
										<option value="BJ-229" <% if(logonData.getNationality().equals("BJ")) out.print("selected"); %>>Benin</option>
										<option value="BT-975" <% if(logonData.getNationality().equals("BT")) out.print("selected"); %>>Bhutan</option>
										<option value="BO-591" <% if(logonData.getNationality().equals("BO")) out.print("selected"); %>>Bolivia</option>
										<option value="BA-387" <% if(logonData.getNationality().equals("BA")) out.print("selected"); %>>Bosnia and Herzegovina</option>
										<option value="BW-267" <% if(logonData.getNationality().equals("BW")) out.print("selected"); %>>Botswana</option>
										<option value="BR-55" <% if(logonData.getNationality().equals("BR")) out.print("selected"); %>>Brazil</option>
										<option value="BG-359" <% if(logonData.getNationality().equals("BG")) out.print("selected"); %>>Bulgaria</option>
										<option value="BF-226" <% if(logonData.getNationality().equals("BF")) out.print("selected"); %>>Burkina Faso</option>
										<option value="BI-257" <% if(logonData.getNationality().equals("BI")) out.print("selected"); %>>Burundi</option>
										<option value="KH-855" <% if(logonData.getNationality().equals("KH")) out.print("selected"); %>>Cambodia</option>
										<option value="CM-237" <% if(logonData.getNationality().equals("CM")) out.print("selected"); %>>Cameroon</option>
										<option value="CA-1" <% if(logonData.getNationality().equals("CA")) out.print("selected"); %>>Canada</option>
										<option value="CV-238" <% if(logonData.getNationality().equals("CV")) out.print("selected"); %>>Cape Verde</option>
										<option value="CF-236" <% if(logonData.getNationality().equals("CF")) out.print("selected"); %>>Central African Republic</option>
										<option value="TD-235" <% if(logonData.getNationality().equals("TD")) out.print("selected"); %>>Chad</option>
										<option value="CL-56" <% if(logonData.getNationality().equals("CL")) out.print("selected"); %>>Chile</option>
										<option value="CN-86" <% if(logonData.getNationality().equals("CN")) out.print("selected"); %>>China</option>
										<option value="CO-57" <% if(logonData.getNationality().equals("CO")) out.print("selected"); %>>Colombia</option>
										<option value="KM-269" <% if(logonData.getNationality().equals("KM")) out.print("selected"); %>>Comoros</option>
										<option value="CR-506" <% if(logonData.getNationality().equals("CR")) out.print("selected"); %>>Costa Rica</option>
										<option value="HR-385" <% if(logonData.getNationality().equals("HR")) out.print("selected"); %>>Croatia</option>
										<option value="CU-53" <% if(logonData.getNationality().equals("CU")) out.print("selected"); %>>Cuba</option>
										<option value="CY-357" <% if(logonData.getNationality().equals("CY")) out.print("selected"); %>>Cyprus</option>
										<option value="CZ-420" <% if(logonData.getNationality().equals("CZ")) out.print("selected"); %>>Czech Republic</option>
										<option value="CD-243" <% if(logonData.getNationality().equals("CD")) out.print("selected"); %>>Democratic Republic of Congo</option>
										<option value="DK-45" <% if(logonData.getNationality().equals("DK")) out.print("selected"); %>>Denmark</option>
										<option value="DJ-253" <% if(logonData.getNationality().equals("DJ")) out.print("selected"); %>>Djibouti</option>
										<option value="DM-1767" <% if(logonData.getNationality().equals("DM")) out.print("selected"); %>>Dominica</option>
										<option value="DO-1809" <% if(logonData.getNationality().equals("DO")) out.print("selected"); %>>Dominican Republic</option>
										<option value="EC-593" <% if(logonData.getNationality().equals("EC")) out.print("selected"); %>>Ecuador</option>
										<option value="EG-20" <% if(logonData.getNationality().equals("EG")) out.print("selected"); %>>Egypt</option>
										<option value="SV-503" <% if(logonData.getNationality().equals("SV")) out.print("selected"); %>>El Salvador</option>
										<option value="GQ-240" <% if(logonData.getNationality().equals("GQ")) out.print("selected"); %>>Equatorial Guinea</option>
										<option value="ER-291" <% if(logonData.getNationality().equals("ER")) out.print("selected"); %>>Eritrea</option>
										<option value="EE-372" <% if(logonData.getNationality().equals("EE")) out.print("selected"); %>>Estonia</option>
										<option value="ET-251" <% if(logonData.getNationality().equals("ET")) out.print("selected"); %>>Ethiopia</option>
										<option value="FI-358" <% if(logonData.getNationality().equals("FI")) out.print("selected"); %>>Finland</option>
										<option value="FR-33" <% if(logonData.getNationality().equals("FR")) out.print("selected"); %>>France</option>
										<option value="PF-689" <% if(logonData.getNationality().equals("PF")) out.print("selected"); %>>French Polynesia</option>
										<option value="GA-241" <% if(logonData.getNationality().equals("GA")) out.print("selected"); %>>Gabon</option>
										<option value="GM-220" <% if(logonData.getNationality().equals("GM")) out.print("selected"); %>>Gambia</option>
										<option value="GE-995" <% if(logonData.getNationality().equals("GE")) out.print("selected"); %>>Georgia</option>
										<option value="DE-49" <% if(logonData.getNationality().equals("DE")) out.print("selected"); %>>Germany</option>
										<option value="GH-233" <% if(logonData.getNationality().equals("GH")) out.print("selected"); %>>Ghana</option>
										<option value="GI-350" <% if(logonData.getNationality().equals("GI")) out.print("selected"); %>>Gibraltar</option>
										<option value="GR-30" <% if(logonData.getNationality().equals("GR")) out.print("selected"); %>>Greece</option>
										<option value="GL-299" <% if(logonData.getNationality().equals("GL")) out.print("selected"); %>>Greenland</option>
										<option value="GD-1473" <% if(logonData.getNationality().equals("GD")) out.print("selected"); %>>Grenada</option>
										<option value="GU-1671" <% if(logonData.getNationality().equals("GU")) out.print("selected"); %>>Guam</option>
										<option value="GT-502" <% if(logonData.getNationality().equals("GT")) out.print("selected"); %>>Guatemala</option>
										<option value="GN-224" <% if(logonData.getNationality().equals("GN")) out.print("selected"); %>>Guinea</option>
										<option value="GW-245" <% if(logonData.getNationality().equals("GW")) out.print("selected"); %>>Guinea-Bissau</option>
										<option value="GY-592" <% if(logonData.getNationality().equals("GY")) out.print("selected"); %>>Guyana</option>
										<option value="HT-509" <% if(logonData.getNationality().equals("HT")) out.print("selected"); %>>Haiti</option>
										<option value="HN-504" <% if(logonData.getNationality().equals("HN")) out.print("selected"); %>>Honduras</option>
										<option value="HK-852" <% if(logonData.getNationality().equals("HK")) out.print("selected"); %>>Hong Kong</option>
										<option value="HU-36" <% if(logonData.getNationality().equals("HU")) out.print("selected"); %>>Hungary</option>
										<option value="IS-354" <% if(logonData.getNationality().equals("IS")) out.print("selected"); %>>Iceland</option>
										<option value="IN-91" <% if(logonData.getNationality().equals("IN")) out.print("selected"); %>>India</option>
										<option value="ID-62" <% if(logonData.getNationality().equals("ID")) out.print("selected"); %>>Indonesia</option>
										<option value="IR-98" <% if(logonData.getNationality().equals("IR")) out.print("selected"); %>>Iran</option>
										<option value="IQ-964" <% if(logonData.getNationality().equals("IQ")) out.print("selected"); %>>Iraq</option>
										<option value="IE-353" <% if(logonData.getNationality().equals("IE")) out.print("selected"); %>>Ireland</option>
										<option value="IL-972" <% if(logonData.getNationality().equals("IL")) out.print("selected"); %>>Israel</option>
										<option value="IT-39" <% if(logonData.getNationality().equals("IT")) out.print("selected"); %>>Italy</option>
										<option value="CI-225" <% if(logonData.getNationality().equals("CI")) out.print("selected"); %>>Ivory Coast</option>
										<option value="JM-1876" <% if(logonData.getNationality().equals("JM")) out.print("selected"); %>>Jamaica</option>
										<option value="JP-81" <% if(logonData.getNationality().equals("JP")) out.print("selected"); %>>Japan</option>
										<option value="JO-962" <% if(logonData.getNationality().equals("JO")) out.print("selected"); %>>Jordan</option>
										<option value="KZ-7" <% if(logonData.getNationality().equals("KZ")) out.print("selected"); %>>Kazakhstan</option>
										<option value="KE-254" <% if(logonData.getNationality().equals("KE")) out.print("selected"); %>>Kenya</option>
										<option value="KI-686" <% if(logonData.getNationality().equals("KI")) out.print("selected"); %>>Kiribati</option>
										<option value="KW-965" <% if(logonData.getNationality().equals("KW")) out.print("selected"); %>>Kuwait</option>
										<option value="KG-996" <% if(logonData.getNationality().equals("KG")) out.print("selected"); %>>Kyrgyzstan</option>
										<option value="LA-856" <% if(logonData.getNationality().equals("LA")) out.print("selected"); %>>Laos</option>
										<option value="LV-371" <% if(logonData.getNationality().equals("LV")) out.print("selected"); %>>Latvia</option>
										<option value="LB-961" <% if(logonData.getNationality().equals("LB")) out.print("selected"); %>>Lebanon</option>
										<option value="LS-266" <% if(logonData.getNationality().equals("LS")) out.print("selected"); %>>Lesotho</option>
										<option value="LR-231" <% if(logonData.getNationality().equals("LR")) out.print("selected"); %>>Liberia</option>
										<option value="LY-218" <% if(logonData.getNationality().equals("LY")) out.print("selected"); %>>Libya</option>
										<option value="LI-423" <% if(logonData.getNationality().equals("LI")) out.print("selected"); %>>Liechtenstein</option>
										<option value="LT-370" <% if(logonData.getNationality().equals("LT")) out.print("selected"); %>>Lithuania</option>
										<option value="LU-352" <% if(logonData.getNationality().equals("LU")) out.print("selected"); %>>Luxembourg</option>
										<option value="MO-853" <% if(logonData.getNationality().equals("MO")) out.print("selected"); %>>Macau</option>
										<option value="MK-389" <% if(logonData.getNationality().equals("MK")) out.print("selected"); %>>Macedonia</option>
										<option value="MG-261" <% if(logonData.getNationality().equals("MG")) out.print("selected"); %>>Madagascar</option>
										<option value="MW-265" <% if(logonData.getNationality().equals("MW")) out.print("selected"); %>>Malawi</option>
										<option value="MV-960" <% if(logonData.getNationality().equals("MV")) out.print("selected"); %>>Maldives</option>
										<option value="ML-223" <% if(logonData.getNationality().equals("ML")) out.print("selected"); %>>Mali</option>
										<option value="MT-356" <% if(logonData.getNationality().equals("MT")) out.print("selected"); %>>Malta</option>
										<option value="MH-692" <% if(logonData.getNationality().equals("MH")) out.print("selected"); %>>Marshall Islands</option>
										<option value="MR-222" <% if(logonData.getNationality().equals("MR")) out.print("selected"); %>>Mauritania</option>
										<option value="MU-230" <% if(logonData.getNationality().equals("MU")) out.print("selected"); %>>Mauritius</option>
										<option value="YT-262" <% if(logonData.getNationality().equals("YT")) out.print("selected"); %>>Mayotte</option>
										<option value="MX-52" <% if(logonData.getNationality().equals("MX")) out.print("selected"); %>>Mexico</option>
										<option value="MD-373" <% if(logonData.getNationality().equals("MD")) out.print("selected"); %>>Moldova</option>
										<option value="MC-377" <% if(logonData.getNationality().equals("MC")) out.print("selected"); %>>Monaco</option>
										<option value="MN-976" <% if(logonData.getNationality().equals("MN")) out.print("selected"); %>>Mongolia</option>
										<option value="ME-382" <% if(logonData.getNationality().equals("ME")) out.print("selected"); %>>Montenegro</option>
										<option value="MS-1664" <% if(logonData.getNationality().equals("MS")) out.print("selected"); %>>Montserrat</option>
										<option value="MA-212" <% if(logonData.getNationality().equals("MA")) out.print("selected"); %>>Morocco</option>
										<option value="MM-976" <% if(logonData.getNationality().equals("MM")) out.print("selected"); %>>Myanmar</option>
										<option value="NA-264" <% if(logonData.getNationality().equals("NA")) out.print("selected"); %>>Namibia</option>
										<option value="NR-674" <% if(logonData.getNationality().equals("NR")) out.print("selected"); %>>Nauru</option>
										<option value="NP-977" <% if(logonData.getNationality().equals("NP")) out.print("selected"); %>>Nepal</option>
										<option value="NL-31" <% if(logonData.getNationality().equals("NL")) out.print("selected"); %>>Netherlands</option>
										<option value="AN-599" <% if(logonData.getNationality().equals("AN")) out.print("selected"); %>>Netherlands Antilles</option>
										<option value="NC-687" <% if(logonData.getNationality().equals("NC")) out.print("selected"); %>>New Caledonia</option>
										<option value="NZ-64" <% if(logonData.getNationality().equals("NZ")) out.print("selected"); %>>New Zealand</option>
										<option value="NI-505" <% if(logonData.getNationality().equals("NI")) out.print("selected"); %>>Nicaragua</option>
										<option value="NE-227" <% if(logonData.getNationality().equals("NE")) out.print("selected"); %>>Niger</option>
										<option value="NG-234" <% if(logonData.getNationality().equals("NG")) out.print("selected"); %>>Nigeria</option>
										<option value="NU-683" <% if(logonData.getNationality().equals("NU")) out.print("selected"); %>>Niue</option>
										<option value="KP-850" <% if(logonData.getNationality().equals("KP")) out.print("selected"); %>>North Korea</option>
										<option value="NO-47" <% if(logonData.getNationality().equals("NO")) out.print("selected"); %>>Norway</option>
										<option value="OM-968" <% if(logonData.getNationality().equals("OM")) out.print("selected"); %>>Oman</option>
										<option value="PK-92" <% if(logonData.getNationality().equals("PK")) out.print("selected"); %>>Pakistan</option>
										<option value="PW-680" <% if(logonData.getNationality().equals("PW")) out.print("selected"); %>>Palau</option>
										<option value="PA-507" <% if(logonData.getNationality().equals("PA")) out.print("selected"); %>>Panama</option>
										<option value="PG-675" <% if(logonData.getNationality().equals("PG")) out.print("selected"); %>>Papua New Guinea</option>
										<option value="PY-595" <% if(logonData.getNationality().equals("PY")) out.print("selected"); %>>Paraguay</option>
										<option value="PE-51" <% if(logonData.getNationality().equals("PE")) out.print("selected"); %>>Peru</option>
										<option value="PH-62" <% if(logonData.getNationality().equals("PH")) out.print("selected"); %>>Philippines</option>
										<option value="PN-870" <% if(logonData.getNationality().equals("PN")) out.print("selected"); %>>Pitcairn Islands</option>
										<option value="PL-48" <% if(logonData.getNationality().equals("PL")) out.print("selected"); %>>Poland</option>
										<option value="PT-351" <% if(logonData.getNationality().equals("PT")) out.print("selected"); %>>Portugal</option>
										<option value="PR-1" <% if(logonData.getNationality().equals("PR")) out.print("selected"); %>>Puerto Rico</option>
										<option value="QA-974" <% if(logonData.getNationality().equals("QA")) out.print("selected"); %>>Qatar</option>
										<option value="RO-40" <% if(logonData.getNationality().equals("RO")) out.print("selected"); %>>Romania</option>
										<option value="RU-7" <% if(logonData.getNationality().equals("RU")) out.print("selected"); %>>Russia</option>
										<option value="RW-250" <% if(logonData.getNationality().equals("RW")) out.print("selected"); %>>Rwanda</option>
										<option value="SH-290" <% if(logonData.getNationality().equals("SH")) out.print("selected"); %>>Saint Helena and Dependencies</option>
										<option value="KN-1869" <% if(logonData.getNationality().equals("KN")) out.print("selected"); %>>Saint Kitts and Nevis</option>
										<option value="LC-1758" <% if(logonData.getNationality().equals("LC")) out.print("selected"); %>>Saint Lucia</option>
										<option value="PM-508" <% if(logonData.getNationality().equals("PM")) out.print("selected"); %>>Saint Pierre and Miquelon</option>
										<option value="VC-1784" <% if(logonData.getNationality().equals("VC")) out.print("selected"); %>>Saint Vincent and the Grenadines</option>
										<option value="WS-685" <% if(logonData.getNationality().equals("WS")) out.print("selected"); %>>Samoa</option>
										<option value="SM-378" <% if(logonData.getNationality().equals("SM")) out.print("selected"); %>>San Marino</option>
										<option value="ST-239" <% if(logonData.getNationality().equals("ST")) out.print("selected"); %>>Sao Tome and Principe</option>
										<option value="SA-966" <% if(logonData.getNationality().equals("SA")) out.print("selected"); %>>Saudi Arabia</option>
										<option value="SN-221" <% if(logonData.getNationality().equals("SN")) out.print("selected"); %>>Senegal</option>
										<option value="RS-381" <% if(logonData.getNationality().equals("RS")) out.print("selected"); %>>Serbia</option>
										<option value="SC-248" <% if(logonData.getNationality().equals("SC")) out.print("selected"); %>>Seychelles</option>
										<option value="SL-232" <% if(logonData.getNationality().equals("SL")) out.print("selected"); %>>Sierra Leone</option>
										<option value="SK-421" <% if(logonData.getNationality().equals("SK")) out.print("selected"); %>>Slovakia</option>
										<option value="SI-386" <% if(logonData.getNationality().equals("SI")) out.print("selected"); %>>Slovenia</option>
										<option value="SB-677" <% if(logonData.getNationality().equals("SB")) out.print("selected"); %>>Solomon Islands</option>
										<option value="ZA-27" <% if(logonData.getNationality().equals("ZA")) out.print("selected"); %>>South Africa</option>
										<option value="KR-82" <% if(logonData.getNationality().equals("KR")) out.print("selected"); %>>South Korea</option>
										<option value="ES-34" <% if(logonData.getNationality().equals("ES")) out.print("selected"); %>>Spain</option>
										<option value="LK-94" <% if(logonData.getNationality().equals("LK")) out.print("selected"); %>>Sri Lanka</option>
										<option value="SD-249" <% if(logonData.getNationality().equals("SD")) out.print("selected"); %>>Sudan</option>
										<option value="SR-597" <% if(logonData.getNationality().equals("SR")) out.print("selected"); %>>Suriname</option>
										<option value="SZ-268" <% if(logonData.getNationality().equals("SZ")) out.print("selected"); %>>Swaziland</option>
										<option value="SE-46" <% if(logonData.getNationality().equals("SE")) out.print("selected"); %>>Sweden</option>
										<option value="CH-41" <% if(logonData.getNationality().equals("CH")) out.print("selected"); %>>Switzerland</option>
										<option value="SY-963" <% if(logonData.getNationality().equals("SY")) out.print("selected"); %>>Syria</option>
										<option value="TW-886" <% if(logonData.getNationality().equals("TW")) out.print("selected"); %>>Taiwan</option>
										<option value="TJ-992" <% if(logonData.getNationality().equals("TJ")) out.print("selected"); %>>Tajikistan</option>
										<option value="TZ-255" <% if(logonData.getNationality().equals("TZ")) out.print("selected"); %>>Tanzania</option>
										<option value="TH-66" <% if(logonData.getNationality().equals("TH")) out.print("selected"); %>>Thailand</option>
										<option value="TG-228" <% if(logonData.getNationality().equals("TG")) out.print("selected"); %>>Togo</option>
										<option value="TK-690" <% if(logonData.getNationality().equals("TK")) out.print("selected"); %>>Tokelau</option>
										<option value="TO-676" <% if(logonData.getNationality().equals("TO")) out.print("selected"); %>>Tonga</option>
										<option value="TN-216" <% if(logonData.getNationality().equals("TN")) out.print("selected"); %>>Tunisia</option>
										<option value="TR-90" <% if(logonData.getNationality().equals("TR")) out.print("selected"); %>>Turkey</option>
										<option value="TM-993" <% if(logonData.getNationality().equals("TM")) out.print("selected"); %>>Turkmenistan</option>
										<option value="TV-688" <% if(logonData.getNationality().equals("TV")) out.print("selected"); %>>Tuvalu</option>
										<option value="UG-256" <% if(logonData.getNationality().equals("UG")) out.print("selected"); %>>Uganda</option>
										<option value="UA-380" <% if(logonData.getNationality().equals("UA")) out.print("selected"); %>>Ukraine</option>
										<option value="AE-971" <% if(logonData.getNationality().equals("AE")) out.print("selected"); %>>United Arab Emirates</option>
										<option value="UK-44" <% if(logonData.getNationality().equals("UK")) out.print("selected"); %>>United Kingdom</option>
										<option value="US-1" <% if(logonData.getNationality().equals("US")) out.print("selected"); %>>United States</option>
										<option value="UY-598" <% if(logonData.getNationality().equals("UY")) out.print("selected"); %>>Uruguay</option>
										<option value="UZ-998" <% if(logonData.getNationality().equals("UZ")) out.print("selected"); %>>Uzbekistan</option>
										<option value="UV-678" <% if(logonData.getNationality().equals("UV")) out.print("selected"); %>>Vanuatu</option>
										<option value="VE-58" <% if(logonData.getNationality().equals("VE")) out.print("selected"); %>>Venezuela</option>
										<option value="VN-84" <% if(logonData.getNationality().equals("VN")) out.print("selected"); %>>Vietnam</option>
										<option value="WF-681" <% if(logonData.getNationality().equals("WF")) out.print("selected"); %>>Wallis and Futuna</option>
										<option value="YE-967" <% if(logonData.getNationality().equals("YE")) out.print("selected"); %>>Yemen</option>
										<option value="ZM-260" <% if(logonData.getNationality().equals("ZM")) out.print("selected"); %>>Zambia</option>
										<option value="ZW-263" <% if(logonData.getNationality().equals("ZW")) out.print("selected"); %>>Zimbabwe</option>
									</select>
	                            </label>
	                        </section>
	                        
	                        <section>
		                        <div class="col-sm-3 label">
		                            <label class="label"><%=Resource.getString("ID_LABEL_COUNTRYCODE",locale)%></label>
									<label for="countryCode" class="input"><input id="countryCode" name="countryCode" type="text" class="form-control" style="width:90%" value="<%=logonData.getCountryCode() %>" maxlength="4" /></label>
		                        </div>
		                        <div class="col-sm-9 label">
		                            <label class="label"><%=Resource.getString("ID_LABEL_CONTACTNUMBER",locale)%> <font color="red">*</font></label>
									<label for="phone" class="input"><input id="phone" name="phone" type="text" class="form-control" maxlength="30" value="<%=logonData.getPhone() %>" onBlur="trimZero()" /></label>
		                        </div>
		                    </section>
	                        
	                        <section>
								<label class="label"><%=Resource.getString("ID_LABEL_GENDER",locale)%> <font color="red">*</font></label>
	                            <label for="gender" class="input">
	                                <select class="form-control" id="gender" name="gender">
										<option value="1" <% if(logonData.getGender()==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_MALE",locale)%></option>
										<option value="2" <% if(logonData.getGender()==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_FEMALE",locale)%></option>
									</select>
	                            </label>
	                        </section>
	                        
	                        <section style="margin-bottom:80px">
		                        <label class="label"><%=Resource.getString("ID_LABEL_DOB",locale)%> </label>
		                        <% 
							        String dob = logonData.getDob();
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
								<input id="dob" name="dob" type="hidden" />
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
		                    </section>
		                    
		                    <section>
								<label class="label"><%=Resource.getString("ID_LABEL_IC",locale)%> <font color="red">*</font></label>
	                            <label for="IC" class="input">
	                                <input type="text" id="IC" name="IC" value="<%=logonData.getIC() %>" maxlength="15">
	                            </label>
	                        </section>
	                        
	                        <section>
	                        	<label class="label">Facebook ID</label>
	                        	<label for="fbid" class="input">
	                        		<input type="text" id="fbid" name ="fbid" value="<%= logonData.getFbid() %>">
	                        	</label>
	                        </section>
	                        
		                    <p>&nbsp;</p>
		                                            
	                        <hr>
	                        
	                        <section>
	                        	<label class="checkbox">
	                        		<input type="checkbox" id="newsletter" name="newsletter" <%if( logonData.getNewsLetter() == 1){out.print("checked");} %> %><i></i>Subscribe to Newsletter.
	                        	</label>
	                        </section>
	                        
	                    </fieldset>
	                    
						<footer>
						
							<p>&nbsp;</p>
							<button id="profileBtn" name="profileBtn" class="btn btn-orange" type="submit"><%=Resource.getString("ID_LABEL_UPDATE",locale) %></button>	
							<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
	                    </footer>
						
					</form>


				</div>
				
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	<script type="text/javascript" src="./assets/js/profilepic.js"></script>
	<script type="text/javascript" src="./assets/js/register.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
        	
			if("<%=logonData.getErrmsg() %>" != ""){
				if("<%=logonData.getErrmsg() %>" == "updateSuccess"){
					$( "#successAlert" ).show();
					document.getElementById("successMsg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATESUCCESS",locale)%>";
				} 
				else if("<%=logonData.getErrmsg() %>" == "updateFailed"){
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=Resource.getString("ID_MSG_UPDATEFAILED",locale)%>";
				}
				else if("<%=logonData.getErrmsg() %>" == "userIdEmailExist"){
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=Resource.getString("ID_MSG_USERIDEMAILEXIST",locale)%>";
				}
				else {
					$( "#errorAlert" ).show();
					document.getElementById("errorMsg").innerHTML = "<%=logonData.getErrmsg() %>";
				}
			}
			
			<% if(priv == 0){%>
				$( "#accountStatus" ).show();
				document.getElementById("privStatus").innerHTML = "Your account is waiting for verification. "+"<a href='./logon?actionType=sendVerifyEmail&userId=<%=userId %>'>Click here</a> to resend email.";
			<%} %>
		});
		
		$(function() {
			$( "#profileBtn" ).click(function() { submitCheck('update'); return false; });
		});
		
		function setContryCode(obj) {
			var idx  = obj.selectedIndex;
			var ccode = obj.options[idx].value;
			document.getElementById("nationality").value = ccode.substring(0, 2);
			document.getElementById("countryCode").value = ccode.substring(3, ccode.length);
		};

		function trimZero() {
			var phone = document.getElementById("phone").value;
			if(phone.length > 0) {
				document.getElementById("phone").value = phone.replace(/^(0+)/g, '');
			}
		};

	</script>
</g:compress>
	
</body>

</html>