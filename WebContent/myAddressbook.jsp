<%@page import="com.iposb.i18n.*,com.iposb.my.*,com.iposb.area.*,com.iposb.utils.*,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	//String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();

	String errmsg = "";
	ArrayList data = (ArrayList)request.getAttribute(MyController.OBJECTDATA);
	MyDataModel myData = new MyDataModel();
	if(data != null && data.size() > 0){
		myData = (MyDataModel)data.get(0);
		errmsg = myData.getErrmsg();
	}
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	if(priv < 1){
		String url = "./login?returnUrl=./addressbook";
		response.sendRedirect(url);
        return;
	}
	
	//shown & not shown
	boolean addEdit = false;
	boolean list = false;
	String actionType = request.getParameter("actionType");
	if(actionType != null && (actionType.equals("insertaddressbook") || actionType.equals("updateaddressbook"))){
		addEdit = true;
		list = false;
	} else {
		addEdit = false;
		list = true;
	}
%>

<head>
    <title><%=Resource.getString("ID_LABEL_ADDRESSBOOK",locale)%> - iPosb Logistic</title>

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
				
				<jsp:include page="mySidebar.jsp?tab=addressbook" />
				
				<div class="col-md-9">
				
					<% 
						String btnText = "";
						if(actionType.equals("insertaddressbook")){
							out.println("<h3>"+Resource.getString("ID_LABEL_ADDADDRESSBOOK",locale)+"</h3>"); 
							btnText = Resource.getString("ID_LABEL_CONFIRMCREATE",locale);
						} else if(actionType.equals("updateaddressbook")){
							out.println("<h3>"+Resource.getString("ID_LABEL_EDITADDRESSBOOK",locale)+"</h3>");
							btnText = Resource.getString("ID_LABEL_UPDATE",locale);
						} else {
							out.println("<h3>"+Resource.getString("ID_LABEL_ADDRESSBOOK",locale)+"</h3>");
						}
					%>
					
					<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="errorMsg"></span>
					</div>
					
					<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<span id="successMsg"></span>
					</div>
					
					<%
						if(addEdit){
					%>
					
						<form id="createForm" name="createForm" method="post" action="./my" class="sky-form">
							<input type="hidden" id="actionType" name="actionType" value="<%=actionType %>">
							<input type="hidden" id="verify" name="verify" value="<%=myData.getVerify() %>">
							<input type="hidden" id="useLocale" name="useLocale" value="<%=locale %>">
							
							<fieldset>
		                        
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_NAME",locale)%> </label>
		                            <label for="receiverName" class="input">
		                                <input type="text" id="receiverName" name="receiverName" value="<%=myData.getReceiverName() %>" maxlength="255">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERATTN",locale)%> </label>
		                            <label for="receiverAttn" class="input">
		                                <input type="text" id="receiverAttn" name="receiverAttn" value="<%=myData.getReceiverAttn() %>" maxlength="255">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_ADDRESS",locale)%> </label>
		                            <label for="receiverAddress1" class="input">
		                                <input type="text" id="receiverAddress1" name="receiverAddress1" value="<%=myData.getReceiverAddress1() %>" maxlength="40">
		                            </label>
		                            <label for="receiverAddress2" class="input">
		                                <input type="text" id="receiverAddress2" name="receiverAddress2" value="<%=myData.getReceiverAddress2() %>" maxlength="40">
		                            </label>
		                            <label for="receiverAddress3" class="input">
		                                <input type="text" id="receiverAddress3" name="receiverAddress3" value="<%=myData.getReceiverAddress3() %>" maxlength="40">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_POSTCODE",locale)%> </label>
		                            <label for="receiverPostcode" class="input">
		                                <input type="text" id="receiverPostcode" name="receiverPostcode" value="<%=myData.getReceiverPostcode() %>" maxlength="10">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_AREA",locale)%> </label>
		                            <label for="receiverArea" class="input">
		                                <select class="form-control" id="receiverArea" name="receiverArea" onChange="loadZone('receiver')">
			                                <% 
				                                if(areaData != null && !areaData.isEmpty()){
													for(int i = 0; i < areaData.size(); i ++){
														aData = areaData.get(i);
			                                %>
												<option value="<%=aData.getAid() %>" <% if(myData.getReceiverArea()==aData.getAid()){out.print("selected");} %>><%=aData.getName_enUS() %></option>
											<% } } %>
										</select>
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_ZONE",locale)%></label>
		                            <label for="receiverZone" class="input">
		                            	<input type="hidden" id="receiverZoneTmp" name="receiverZoneTmp">
		                                <select class="form-control" id="receiverZone" name="receiverZone"></select>
		                            </label>
		                        </section>
		                        
		                        <section>
		                        	<label class="label">Country</label>
		                        	<label for="receiverCountry" class="input">
										<select name="receiverCountry" id="receiverCountry" class="form-control margin-bottom-20">
											<option value="" <% if(myData.getReceiverCountry().equals("")){out.print("selected");} %>></option>
											<option value="BN" <% if(myData.getReceiverCountry().equals("BN")){out.print("selected");} %>>Brunei</option>
											<option value="MY" <% if(myData.getReceiverCountry().equals("MY")){out.print("selected");} %>>Malaysia</option>
											<option value="SG" <% if(myData.getReceiverCountry().equals("SG")){out.print("selected");} %>>Singapore</option>
											<option value=""> ----------------------- </option>
											<option value="AF">Afghanistan</option>
											<option value="AL">Albania</option>
											<option value="DZ">Algeria</option>
											<option value="AS">American Samoa</option>
											<option value="AD">Andorra</option>
											<option value="AO">Angola</option>
											<option value="AI">Anguilla</option>
											<option value="AR">Argentina</option>
											<option value="AM">Armenia</option>
											<option value="AW">Aruba</option>
											<option value="AU">Australia</option>
											<option value="AT">Austria</option>
											<option value="AZ">Azerbaijan</option>
											<option value="BS">Bahamas</option>
											<option value="BH">Bahrain</option>
											<option value="BD">Bangladesh</option>
											<option value="BB">Barbados</option>
											<option value="BY">Belarus</option>
											<option value="BE">Belgium</option>
											<option value="BZ">Belize</option>
											<option value="BJ">Benin</option>
											<option value="BT">Bhutan</option>
											<option value="BO">Bolivia</option>
											<option value="BA">Bosnia and Herzegovina</option>
											<option value="BW">Botswana</option>
											<option value="BR">Brazil</option>
											<option value="BG">Bulgaria</option>
											<option value="BF">Burkina Faso</option>
											<option value="BI">Burundi</option>
											<option value="KH">Cambodia</option>
											<option value="CM">Cameroon</option>
											<option value="CA">Canada</option>
											<option value="CV">Cape Verde</option>
											<option value="CF">Central African Republic</option>
											<option value="TD">Chad</option>
											<option value="CL">Chile</option>
											<option value="CN">China</option>
											<option value="CO">Colombia</option>
											<option value="KM">Comoros</option>
											<option value="CR">Costa Rica</option>
											<option value="HR">Croatia</option>
											<option value="CU">Cuba</option>
											<option value="CY">Cyprus</option>
											<option value="CZ">Czech Republic</option>
											<option value="CD">Democratic Republic of Congo</option>
											<option value="DK">Denmark</option>
											<option value="DJ">Djibouti</option>
											<option value="DM">Dominica</option>
											<option value="DO">Dominican Republic</option>
											<option value="EC">Ecuador</option>
											<option value="EG">Egypt</option>
											<option value="SV">El Salvador</option>
											<option value="GQ">Equatorial Guinea</option>
											<option value="ER">Eritrea</option>
											<option value="EE">Estonia</option>
											<option value="ET">Ethiopia</option>
											<option value="FI">Finland</option>
											<option value="FR">France</option>
											<option value="PF">French Polynesia</option>
											<option value="GA">Gabon</option>
											<option value="GM">Gambia</option>
											<option value="GE">Georgia</option>
											<option value="DE">Germany</option>
											<option value="GH">Ghana</option>
											<option value="GI">Gibraltar</option>
											<option value="GR">Greece</option>
											<option value="GL">Greenland</option>
											<option value="GD">Grenada</option>
											<option value="GU">Guam</option>
											<option value="GT">Guatemala</option>
											<option value="GN">Guinea</option>
											<option value="GW">Guinea-Bissau</option>
											<option value="GY">Guyana</option>
											<option value="HT">Haiti</option>
											<option value="HN">Honduras</option>
											<option value="HK">Hong Kong</option>
											<option value="HU">Hungary</option>
											<option value="IS">Iceland</option>
											<option value="IN">India</option>
											<option value="ID">Indonesia</option>
											<option value="IR">Iran</option>
											<option value="IQ">Iraq</option>
											<option value="IE">Ireland</option>
											<option value="IL">Israel</option>
											<option value="IT">Italy</option>
											<option value="CI">Ivory Coast</option>
											<option value="JM">Jamaica</option>
											<option value="JP">Japan</option>
											<option value="JO">Jordan</option>
											<option value="KZ">Kazakhstan</option>
											<option value="KE">Kenya</option>
											<option value="KI">Kiribati</option>
											<option value="KW">Kuwait</option>
											<option value="KG">Kyrgyzstan</option>
											<option value="LA">Laos</option>
											<option value="LV">Latvia</option>
											<option value="LB">Lebanon</option>
											<option value="LS">Lesotho</option>
											<option value="LR">Liberia</option>
											<option value="LY">Libya</option>
											<option value="LI">Liechtenstein</option>
											<option value="LT">Lithuania</option>
											<option value="LU">Luxembourg</option>
											<option value="MO">Macau</option>
											<option value="MK">Macedonia</option>
											<option value="MG">Madagascar</option>
											<option value="MW">Malawi</option>
											<option value="MV">Maldives</option>
											<option value="ML">Mali</option>
											<option value="MT">Malta</option>
											<option value="MH">Marshall Islands</option>
											<option value="MR">Mauritania</option>
											<option value="MU">Mauritius</option>
											<option value="YT">Mayotte</option>
											<option value="MX">Mexico</option>
											<option value="MD">Moldova</option>
											<option value="MC">Monaco</option>
											<option value="MN">Mongolia</option>
											<option value="ME">Montenegro</option>
											<option value="MS">Montserrat</option>
											<option value="MA">Morocco</option>
											<option value="MZ">Mozambique</option>
											<option value="MM">Myanmar</option>
											<option value="NA">Namibia</option>
											<option value="NR">Nauru</option>
											<option value="NP">Nepal</option>
											<option value="NL">Netherlands</option>
											<option value="AN">Netherlands Antilles</option>
											<option value="NC">New Caledonia</option>
											<option value="NZ">New Zealand</option>
											<option value="NI">Nicaragua</option>
											<option value="NE">Niger</option>
											<option value="NG">Nigeria</option>
											<option value="NU">Niue</option>
											<option value="KP">North Korea</option>
											<option value="NO">Norway</option>
											<option value="OM">Oman</option>
											<option value="PK">Pakistan</option>
											<option value="PW">Palau</option>
											<option value="PA">Panama</option>
											<option value="PG">Papua New Guinea</option>
											<option value="PY">Paraguay</option>
											<option value="PE">Peru</option>
											<option value="PH">Philippines</option>
											<option value="PN">Pitcairn Islands</option>
											<option value="PL">Poland</option>
											<option value="PT">Portugal</option>
											<option value="PR">Puerto Rico</option>
											<option value="QA">Qatar</option>
											<option value="RO">Romania</option>
											<option value="RU">Russia</option>
											<option value="RW">Rwanda</option>
											<option value="SH">Saint Helena and Dependencies</option>
											<option value="KN">Saint Kitts and Nevis</option>
											<option value="LC">Saint Lucia</option>
											<option value="PM">Saint Pierre and Miquelon</option>
											<option value="VC">Saint Vincent and the Grenadines</option>
											<option value="WS">Samoa</option>
											<option value="SM">San Marino</option>
											<option value="ST">Sao Tome and Principe</option>
											<option value="SA">Saudi Arabia</option>
											<option value="SN">Senegal</option>
											<option value="RS">Serbia</option>
											<option value="SC">Seychelles</option>
											<option value="SL">Sierra Leone</option>
											<option value="SK">Slovakia</option>
											<option value="SI">Slovenia</option>
											<option value="SB">Solomon Islands</option>
											<option value="ZA">South Africa</option>
											<option value="KR">South Korea</option>
											<option value="ES">Spain</option>
											<option value="LK">Sri Lanka</option>
											<option value="SD">Sudan</option>
											<option value="SR">Suriname</option>
											<option value="SZ">Swaziland</option>
											<option value="SE">Sweden</option>
											<option value="CH">Switzerland</option>
											<option value="SY">Syria</option>
											<option value="TW">Taiwan</option>
											<option value="TJ">Tajikistan</option>
											<option value="TZ">Tanzania</option>
											<option value="TH">Thailand</option>
											<option value="TG">Togo</option>
											<option value="TK">Tokelau</option>
											<option value="TO">Tonga</option>
											<option value="TN">Tunisia</option>
											<option value="TR">Turkey</option>
											<option value="TM">Turkmenistan</option>
											<option value="TV">Tuvalu</option>
											<option value="UG">Uganda</option>
											<option value="UA">Ukraine</option>
											<option value="AE">United Arab Emirates</option>
											<option value="UK">United Kingdom</option>
											<option value="US">United States</option>
											<option value="UY">Uruguay</option>
											<option value="UZ">Uzbekistan</option>
											<option value="UV">Vanuatu</option>
											<option value="VE">Venezuela</option>
											<option value="VN">Vietnam</option>
											<option value="WF">Wallis and Futuna</option>
											<option value="YE">Yemen</option>
											<option value="ZM">Zambia</option>
											<option value="ZW">Zimbabwe</option>
										</select>
		                        	</label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_PHONE",locale)%> </label>
		                            <label for="receiverPhone" class="input">
		                                <input type="text" id="receiverPhone" name="receiverPhone" value="<%=myData.getReceiverPhone() %>" maxlength="50">
		                            </label>
		                        </section>

		                    </fieldset>

							<footer>
							
								<p>&nbsp;</p>
								<button id="favBtn" name="favBtn" class="btn btn-orange" type="button"><%=btnText %></button>	
								<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
		                    </footer>
							
						</form>
						
						
					<% } else if(list){ %>
					
						<div class="row">
							<div class="col-md-6 col-sm-6"></div>
							<div class="col-md-6 col-sm-6">
								<div class="pull-right"><a href="./addAddressbook"><button id="addAddressbook" class="btn btn-primary" type="button"><%=Resource.getString("ID_LABEL_ADDADDRESSBOOK",locale)%></button></a></div>
							</div>
						</div>
					
						<div class="single-item single-item-content">
					
							<div class="item-details">
								<ul class="list-unstyled">
								
									<% 
										if(data != null && data.size() > 0){
											for(int i = 0; i < data.size(); i++) {
												myData = (MyDataModel)data.get(i);
									%>
										<li>
											<%=(i+1) %>. <a href="./editAddressbook-<%=myData.getVerify() %>"><%=myData.getReceiverName() %></a> <span class="pull-right"> <a href="./editAddressbook-<%=myData.getVerify() %>"><button id="edit" class="btn btn-warning" type="button"><%=Resource.getString("ID_LABEL_EDIT",locale)%></button></a></span>
											<div class="clearfix"></div>
										</li>
										
									<% } } else { %>
									
										<li>no record found<div class="clearfix"></div></li>
										
									<% }  %>
								</ul>
							</div>
						
						</div>
					
					<% } %>
					
					


				</div>
				
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->

	<%@ include file="footer.jsp" %>
	
<g:compress>
	
	<script type="text/javascript" src="./assets/js/profilepic.js"></script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			if("<%=result %>" == "updateaddressbookSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_SAVESUCCESS",locale)%>");
			} else if("<%=result %>" == "createaddressbookSuccess"){
				$( "#successAlert" ).show();
				$( "#successMsg" ).html("<%=Resource.getString("ID_MSG_CREATESUCCESS",locale)%>");
			} else if ("<%=result %>" == "updateaddressbookFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_SAVEFAILED",locale)%>");
			} else if ("<%=result %>" == "createaddressbookFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_CREATEFAILED",locale)%>");
			}
			
			$("#receiverZoneTmp").val(<%=myData.getReceiverZone() %>);
			loadZone('receiver');
			
		});
		
		function loadZone(obj) {
			
			var areaId = "";
			
			if(obj=="sender") {
				$( "#senderZone" ).prop( "disabled", true );
				$( "#senderZone" ).empty();
				areaId = $( "#senderArea" ).val();
			} else if(obj=="receiver") {
				$( "#receiverZone" ).prop( "disabled", true );
				$( "#receiverZone" ).empty();
				areaId = $( "#receiverArea" ).val();
			}
			
			$.ajax({
				type: 'POST',
				url: './area',
				data:{    
					lang: "<%=locale %>",
					actionType:"ajaxGetZone",
					aid: areaId
		   		},  
		   		
				success: function(response) {
					
					if(obj=="sender") {
						
						if(response != ""){
							$( "#senderZone" ).append(response);
						}
						$( "#senderZone" ).prop( "disabled", false );
						$( "#senderZone" ).val($( "#senderZoneTmp" ).val());
						
					} else if(obj=="receiver") {

						if(response != ""){
							$( "#receiverZone" ).append(response);
						}
						$( "#receiverZone" ).prop( "disabled", false );
						$( "#receiverZone" ).val($( "#receiverZoneTmp" ).val());
						
					}
					
				} 
			});
			
		};
		
		
		$("#favBtn").click(function(){
			$( "#favBtn" ).attr('disabled','disabled');
			$( "#loading" ).show();
			$("#createForm").submit();
		});

	</script>
</g:compress>	
	
</body>

</html>