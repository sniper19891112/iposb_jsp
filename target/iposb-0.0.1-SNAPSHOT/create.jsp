<%@page import="com.iposb.i18n.*,com.iposb.logon.*,com.iposb.area.*,com.iposb.consignment.*,com.iposb.point.*,com.iposb.my.*,com.iposb.utils.*,java.util.HashMap,java.util.ArrayList,javax.servlet.http.HttpServletRequest"%>
<%@include file="include.jsp" %>

<%
	String logonType = request.getParameter("logonType") == null ? "" : request.getParameter("logonType").toString();
	String returnUrl = request.getParameter("returnUrl") == null ? "" : request.getParameter("returnUrl").toString();
	String result = request.getParameter("result") == null ? "" : request.getParameter("result").toString();
	String source = request.getParameter("source") == null ? "" : request.getParameter("source").toString();

	HashMap draftData = (HashMap)request.getAttribute(ConsignmentController.OBJECTDATA2);
	
	ArrayList <AreaDataModel> areaData = (ArrayList)request.getAttribute("area");
	AreaDataModel aData = new AreaDataModel();
	
	ArrayList <MyDataModel> creditaccountData = (ArrayList)request.getAttribute("creditaccount");
	MyDataModel creditData = new MyDataModel();
		
	if(priv < 1){
		String url = "./login?returnUrl=./my";
		response.sendRedirect(url);
        return;
	}
	
	String pageTitle = Resource.getString("ID_LABEL_CREATECONSIGNMENT",locale);
	String tab = "mySidebar.jsp?tab=my";
	String senderName = "";
	String senderAddress1 = "";
	String senderAddress2 = "";
	String senderAddress3 = "";
	String senderPostcode = "";
	int senderZone = 0;
	String senderPhone = "";
	int senderArea = 0;
	String senderCountry = "";
	String senderIC = "";
	String receiverName = "";
	String receiverAttn = "";
	String receiverAddress1 = "";
	String receiverAddress2 = "";
	String receiverAddress3 = "";
	String receiverPostcode = "";
	int receiverZone = 0;
	String receiverPhone = "";
	int receiverArea = 0;
	String receiverCountry = "";
	String helps = "";
	String tickItem = "";
	int shipmentType = 0;
	double weight = 0;
	int quantity = 1;
	int payMethod = 0;
	int creditArea = 0;
	String deliveryDT = "";
	String useLocale = "";
	String verify = "";
	String errmsg = "";
	
	if(source.equals("draft")) {
		pageTitle = Resource.getString("ID_LABEL_EDITDRAFT",locale);
		tab = "mySidebar.jsp?tab=draft";
		senderName = draftData.get("senderName") == null ? "" : draftData.get("senderName").toString();
		senderAddress1 = draftData.get("senderAddress1") == null ? "" : draftData.get("senderAddress1").toString();
		senderAddress2 = draftData.get("senderAddress2") == null ? "" : draftData.get("senderAddress2").toString();
		senderAddress3 = draftData.get("senderAddress3") == null ? "" : draftData.get("senderAddress3").toString();
		senderPostcode = draftData.get("senderPostcode") == null ? "" : draftData.get("senderPostcode").toString();
		senderZone = Integer.parseInt(draftData.get("senderZone") == null ? "0" : draftData.get("senderZone").toString());
		senderPhone = draftData.get("senderPhone") == null ? "" : draftData.get("senderPhone").toString();
		senderArea = Integer.parseInt(draftData.get("senderArea") == null ? "0" : draftData.get("senderArea").toString());
		senderCountry = draftData.get("senderCountry") == null ? "" : draftData.get("senderCountry").toString();
		receiverAttn = draftData.get("receiverAttn") == null ? "" : draftData.get("receiverAttn").toString();
		senderIC = draftData.get("senderIC") == null ? "" : draftData.get("senderIC").toString();
		receiverName = draftData.get("receiverName") == null ? "" : draftData.get("receiverName").toString();
		receiverAddress1 = draftData.get("receiverAddress1") == null ? "" : draftData.get("receiverAddress1").toString();
		receiverAddress2 = draftData.get("receiverAddress2") == null ? "" : draftData.get("receiverAddress2").toString();
		receiverAddress3 = draftData.get("receiverAddress3") == null ? "" : draftData.get("receiverAddress3").toString();
		receiverPostcode = draftData.get("receiverPostcode") == null ? "" : draftData.get("receiverPostcode").toString();
		receiverZone = Integer.parseInt(draftData.get("receiverZone") == null ? "0" : draftData.get("receiverZone").toString());
		receiverPhone = draftData.get("receiverPhone") == null ? "" : draftData.get("receiverPhone").toString();
		receiverArea = Integer.parseInt(draftData.get("receiverArea") == null ? "0" : draftData.get("receiverArea").toString());
		receiverCountry = draftData.get("receiverCountry") == null ? "" : draftData.get("receiverCountry").toString();
		helps = draftData.get("helps") == null ? "" : draftData.get("helps").toString();
		tickItem = draftData.get("tickItem") == null ? "" : draftData.get("tickItem").toString();
		shipmentType = Integer.parseInt(draftData.get("shipmentType") == null ? "0" : draftData.get("shipmentType").toString());
		weight = Double.parseDouble(draftData.get("weight") == null ? "0" : draftData.get("weight").toString());
		quantity = Integer.parseInt(draftData.get("quantity") == null ? "0" : draftData.get("quantity").toString());
		payMethod = Integer.parseInt(draftData.get("payMethod") == null ? "0" : draftData.get("payMethod").toString());
		creditArea = Integer.parseInt(draftData.get("creditArea") == null ? "0" : draftData.get("creditArea").toString());
		deliveryDT = draftData.get("deliveryDT") == null ? "" : draftData.get("deliveryDT").toString();
		useLocale = draftData.get("useLocale") == null ? "" : draftData.get("useLocale").toString();
		verify = draftData.get("verify") == null ? "" : draftData.get("verify").toString();
		errmsg = draftData.get("errmsg") == null ? "" : draftData.get("errmsg").toString();
	}
%>

<head>
    <title><%=pageTitle %> - iPosb Logistic</title>

    <!-- Meta -->
    <%@include file="meta.jsp" %>
    <meta name="description" content="<%=Resource.getString("ID_LABEL_SEO_DESC_HOMEPAGE",locale)%>" />
	<meta name="keyword" content="<%=Resource.getString("ID_LABEL_METAKEYWORD",locale)%>" />

	<style>
		.radio-toolbar input[type="radio"] {
		  opacity: 0;
		  margin: 20px;
		  width: 0;
		}
		
		.radio-toolbar label {
		    display: inline-block;
		    background-color: #f5f5f5;
		    padding: 10px 20px;
		    font-family: sans-serif, Arial;
		    font-size: 16px;
		    border: 1px solid #9a9a9a;
		    border-radius: 4px;
		    cursor: pointer;
		}
		
		.radio-toolbar input[type="radio"]:checked + label {
		    background-color: rgb(0 166 80);
		    border-color: #3c763d;
		    color: white;
		}
		

	</style>
</head>	

<body>

	<jsp:include page="header.jsp" />
	
    <!--=== Content Part ===-->
			

    <div class="inner-page padd">
			
			<div class="container">

				<div class="row">
						
					<jsp:include page="<%=tab %>" />
					
					<div class="col-md-9">

						<h3><%=pageTitle %></h3>
						
						<div id="errorAlert" class="alert alert-danger fade in alert-dismissable" style="display: none">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<span id="errorMsg"></span>
						</div>
						
						<div id="successAlert" class="alert alert-success fade in alert-dismissable" style="display: none">
							<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
							<span id="successMsg"></span>
						</div>
						
						<form id="createForm" name="createForm" method="post" action="./consignment" class="sky-form">
							<input type="hidden" id="actionType" name="actionType">
							<input type="hidden" id="tickItem" name="tickItem" value="<%=tickItem %>">
							<input type="hidden" id="useLocale" name="useLocale" value="<%=locale %>">
							<input type="hidden" id="verify" name="verify" value="<%=verify %>">
							<input type="hidden" id="formValues" name="formValues">
							
							<fieldset>
									<label class="label"><%=Resource.getString("ID_LABEL_SHIPMENTMETHOD",locale)%> <font color="red">*</font></label>
									<div class="radio-toolbar text-center">
		                                <input type="radio" value="1" id="shipmentMethod" name="methodGroup"> <label for="1"><i class="fa fa-truck fa-lg"></i> <%=Resource.getString("ID_LABEL_SHIPMENTMETHOD_LAND",locale)%></label>
    									<input type="radio" value="2" id="shipmentMethod" name="methodGroup"> <label for="2"><i class="fa fa-ship fa-lg"></i> <%=Resource.getString("ID_LABEL_SHIPMENTMETHOD_SHIP",locale)%></label>
    									<input type="radio" value="3" id="shipmentMethod" name="methodGroup"> <label for="3"><i class="fa fa-plane fa-lg"></i> <%=Resource.getString("ID_LABEL_SHIPMENTMETHOD_FLIGHT",locale)%></label>
    								</div>
		                        </section>
				                        
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_SHIPMENTTYPE",locale)%> <font color="red">*</font></label>
		                            <label for="shipmentType" class="input">
		                                <select class="form-control" id="shipmentType" name="shipmentType" onChange="changeShipmentType()">
											<option value="1" <% if(shipmentType==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_AIRFREIGHTCOURIER",locale)%></option>
											<option value="2" <% if(shipmentType==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_LANDDDTRUCK",locale)%></option>
											<option value="3" <% if(shipmentType==3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_CHARTERTRANSPORT",locale)%></option>
										</select>
		                            </label>
		                        </section>
							
								<section id="quantitySection">
									<label class="label"><%=Resource.getString("ID_LABEL_QUANTITY",locale)%> <font color="red">*</font></label>
		                            <label for="quantity" class="input">
		                    			<input class="form-control" type="text" id="quantity" name="quantity" value="<%=quantity %>" maxlength="6" onKeyPress="return validatenumber(event);">
		                            </label>
		                        </section>
							
								<section id="weightSection">
									<label class="label"><span id="weightSpan"><%=Resource.getString("ID_LABEL_WEIGHT",locale)%></span> (KG) <font color="red">*</font></label>
		                            <label for="weight" class="input">
		                    			<input class="form-control" type="text" id="weight" name="weight" value="<%=weight %>" maxlength="10" onKeyPress="return validatenumber(event);">
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_PAYMETHOD",locale)%> <font color="red">*</font></label>
		                            <label for="payMethod" class="input">
		                                <select class="form-control" id="payMethod" name="payMethod">
		                                	<% if(priv==2) { //credit term user %>
			                                	<option value="4" <% if(payMethod==4){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale)%></option>
											<% } else { %>
												<option value="1" <% if(payMethod==1){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCASH",locale)%></option>
												<option value="2" <% if(payMethod==2){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYONLINE",locale)%></option>
												<option value="3" <% if(payMethod==3){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYBANKIN",locale)%></option>
												<option value="4" <% if(payMethod==4){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCREDIT",locale)%></option>
												<option value="5" <% if(payMethod==5){out.print("selected");} %>><%=Resource.getString("ID_LABEL_PAYBYCOD",locale)%></option>
											<% } %>
										</select>
		                            </label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_ITEMREMARK",locale)%> </label>
		                            <label class="checkbox"><input name="tick1" id="tick1" value="tick1" type="checkbox"><i></i><%=Resource.getString("ID_LABEL_PERISHABLE",locale)%></label>
		                            <label class="checkbox"><input name="tick2" id="tick2" value="tick2" type="checkbox"><i></i><%=Resource.getString("ID_LABEL_FOOD",locale)%></label>
		                            <label class="checkbox"><input name="tick3" id="tick3" value="tick3" type="checkbox"><i></i><%=Resource.getString("ID_LABEL_FRAGILE",locale)%></label>
		                            <label class="checkbox"><input name="tick4" id="tick4" value="tick4" type="checkbox"><i></i><%=Resource.getString("ID_LABEL_EQUIPMENT",locale)%></label>
		                        </section>
		                        
		                        <section>
									<label class="label"><%=Resource.getString("ID_LABEL_SPECIALHELP",locale)%> </label>
		                            <label for="helps" class="input">
		                                <input type="text" id="helps" name="helps" value="<%=helps %>" maxlength="50">
		                            </label>
		                        </section>
								
								<section>
									<label class="label">
										<%=Resource.getString("ID_LABEL_DELIVERYDATE",locale)%> 
										<!-- <font color="red">*</font> -->
									</label>
									<div class="input-group date">
										<input class="form-control date-picker" id="deliveryDT" name="deliveryDT" maxlength="10" type="text" value="<%=deliveryDT %>" />
										<span class="input-group-addon">
											<i class="fa fa-calendar bigger-110"></i>
										</span>
									</div>
		                        </section>
		                        
		                        <p>&nbsp;</p>
		                        
		                        <hr>
		                        
		                        <div class="row">
						
									<div class="col-md-6">
									
										<section>
				                        	<div align="right">
												<a data-toggle="modal" href="#addressbook"><button id="loadSenderAddressBook" class="btn btn-primary"><i class="fa fa-heart"></i> <%=Resource.getString("ID_LABEL_SELECTSENDERFROMADDRESSBOOK",locale)%></button></a>
											</div>
				                        </section>
										
										<section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERNAME",locale)%> <font color="red">*</font></label>
				                            <label for="senderName" class="input">
				                                <input type="text" id="senderName" name="senderName" value="<%=senderName %>">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERIC",locale)%> </label>
				                            <label for="senderIC" class="input">
				                                <input type="text" id="senderIC" name="senderIC" value="<%=senderIC %>">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERADDRESS",locale)%> <font color="red">*</font></label>
				                            <label for="senderAddress1" class="input">
				                                <input type="text" id="senderAddress1" name="senderAddress1" value="<%=senderAddress1 %>" maxlength="40">
				                            </label>
				                            <label for="senderAddress2" class="input">
				                                <input type="text" id="senderAddress2" name="senderAddress2" value="<%=senderAddress2 %>" maxlength="40">
				                            </label>
				                            <label for="senderAddress3" class="input">
				                                <input type="text" id="senderAddress3" name="senderAddress3" value="<%=senderAddress3 %>" maxlength="40">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERPOSTCODE",locale)%> <font color="red">*</font></label>
				                            <label for="senderPostcode" class="input">
				                                <input type="text" id="senderPostcode" name="senderPostcode" maxlength="10" value="<%=senderPostcode %>">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERAREA",locale)%> <font color="red">*</font></label>
				                            <label for="senderArea" class="input">
				                                <select class="form-control" id="senderArea" name="senderArea" onChange="loadZone('sender');">
					                                <% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
																
																String aid = String.format("%06d", aData.getAid());//補足6碼
																if(aData.getIsMajor()==1){
																	aid += "A000000";
																}else{
																	aid += "B" + String.format("%06d", aData.getBelongArea());//補足6碼
																}
																aid += aData.getState();//州屬
					                                %>
														<option value="<%=aData.getAid() %>" addcode="<%=aid %>" <% if(senderArea==(i+1)){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<%  } } %>
												</select>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_COLLECTPOINT",locale)%> <i><small>(<%=Resource.getString("ID_LABEL_FORFASTERPICKUP",locale)%>)</small></i></label>
				                            <label for="senderZone" class="input">
				                            	<input type="hidden" id="senderZoneTmp" name="senderZoneTmp">
				                                <select class="form-control" id="senderZone" name="senderZone" onChange="cutoffCheck('checkCutoff')"><option value="0" cutoff=""></option></select>
				                            </label>
				                        </section>
				                        
				                        <section>
				                        	<label class="label">Country</label>
				                        	<label for="senderCountry" class="input">
												<select name="senderCountry" id="senderCountry" class="form-control margin-bottom-20">
													<option value="" <% if(senderCountry==("")){out.print("selected");} %>></option>
													<option value="BN" <% if(senderCountry==("BN")){out.print("selected");} %>>Brunei</option>
													<option value="MY" <% if(senderCountry==("MY")){out.print("selected");} %>>Malaysia</option>
													<option value="SG" <% if(senderCountry==("SG")){out.print("selected");} %>>Singapore</option>
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
											<label class="label"><%=Resource.getString("ID_LABEL_SENDERPHONE",locale)%> <font color="red">*</font></label>
				                            <label for="senderPhone" class="input">
				                                <input type="text" id="senderPhone" name="senderPhone" value="<%=senderPhone %>">
				                            </label>
				                        </section>
				                        
									</div>
									
									<div class="col-md-6">
									
										<section>
				                        	<div align="right">
												<a data-toggle="modal" href="#addressbook"><button id="loadReceiverAddressBook" class="btn btn-primary"><i class="fa fa-heart"></i> <%=Resource.getString("ID_LABEL_SELECTRECEIVERFROMADDRESSBOOK",locale)%></button></a>
											</div>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERNAME",locale)%> <font color="red">*</font></label>
				                            <label for="receiverName" class="input">
				                                <input type="text" id="receiverName" name="receiverName" value="<%=receiverName %>">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERATTN",locale)%> <font color="red">*</font></label>
				                            <label for="receiverAttn" class="input">
				                                <input type="text" id="receiverAttn" name="receiverAttn" value="<%=receiverAttn %>">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERADDRESS",locale)%> <font color="red">*</font></label>
				                            <label for="receiverAddress1" class="input">
				                                <input type="text" id="receiverAddress1" name="receiverAddress1" value="<%=receiverAddress1 %>" maxlength="40">
				                            </label>
				                            <label for="receiverAddress2" class="input">
				                                <input type="text" id="receiverAddress2" name="receiverAddress2" value="<%=receiverAddress2 %>" maxlength="40">
				                            </label>
				                            <label for="receiverAddress3" class="input">
				                                <input type="text" id="receiverAddress3" name="receiverAddress3" value="<%=receiverAddress3 %>" maxlength="40">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERPOSTCODE",locale)%> <font color="red">*</font></label>
				                            <label for="receiverPostcode" class="input">
				                                <input type="text" id="receiverPostcode" name="receiverPostcode" maxlength="10" value="<%=receiverPostcode %>">
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERAREA",locale)%> <font color="red">*</font></label>
				                            <label for="receiverArea" class="input">
				                                <select class="form-control" id="receiverArea" name="receiverArea" onChange="loadZone('receiver');">
					                                <% 
						                                if(areaData != null && !areaData.isEmpty()){
															for(int i = 0; i < areaData.size(); i ++){
																aData = areaData.get(i);
																
																String aid = String.format("%06d", aData.getAid());//補足6碼
																if(aData.getIsMajor()==1){
																	aid += "A000000";
																}else{
																	aid += "B" + String.format("%06d", aData.getBelongArea());//補足6碼
																}
																aid += aData.getState();//州屬
					                                %>
														<option value="<%=aData.getAid() %>" addcode="<%=aid %>" <% if(receiverArea==(i+1)){out.print("selected");} %>><%=aData.getName_enUS() %></option>
													<% } } %>
												</select>
				                            </label>
				                        </section>
				                        
				                        <section>
											<label class="label"><%=Resource.getString("ID_LABEL_DROPOFFPOINT",locale)%>  <i><small>(<%=Resource.getString("ID_LABEL_FORFASTERDELIVERY",locale)%>)</small></i></label>
				                            <label for="receiverZone" class="input">
				                            	<input type="hidden" id="receiverZoneTmp" name="receiverZoneTmp">
				                                <select class="form-control" id="receiverZone" name="receiverZone"><option value="0"></option></select>
				                            </label>
				                        </section>
				                        
				                        <section>
				                        	<label class="label">Country</label>
				                        	<label for="receiverCountry" class="input">
												<select name="receiverCountry" id="receiverCountry" class="form-control margin-bottom-20">
													<option value="" <% if(receiverCountry==("")){out.print("selected");} %>></option>
													<option value="BN" <% if(receiverCountry==("BN")){out.print("selected");} %>>Brunei</option>
													<option value="MY" <% if(receiverCountry==("MY")){out.print("selected");} %>>Malaysia</option>
													<option value="SG" <% if(receiverCountry==("SG")){out.print("selected");} %>>Singapore</option>
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
											<label class="label"><%=Resource.getString("ID_LABEL_RECEIVERPHONE",locale)%> <font color="red">*</font></label>
				                            <label for="receiverPhone" class="input">
				                                <input type="text" id="receiverPhone" name="receiverPhone" value="<%=receiverPhone %>">
				                            </label>
				                        </section>
				                        
									</div>
									
								</div>
								
								<hr>
								
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_FREIGHTTYPE",locale)%> <font color="red">*</font></label>
		                            <label class="input">
		                                <span id="freight">
											<select class="form-control">
												<option value="0"></option>
											</select>
										</span>
		                            </label>
		                        </section>
								
								<section>
									<label class="label"><%=Resource.getString("ID_LABEL_PROMOCODE",locale)%></label>
		                            <label for="promoCode" class="input">
		                                <input type="text" id="promoCode" name="promoCode" autocomplete="off" >
		                            </label>
	                        	</section>
		                        
		                        <hr>
								
								<section>
									<label class="checkbox">
										<input type="checkbox" id="acceptTerms" name="acceptTerms"><i></i> <%=Resource.getString("ID_LABEL_AGREEMENT",locale)%></a>
									</label>
		                        </section>


		                    </fieldset>

							<footer>
							
								<p>&nbsp;</p>
								
								<div id="cutoffWarning" class="alert alert-danger fade in alert-dismissable" style="display: none">
									<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								</div>
								<%-- 
								<div id="errorAlert1" class="alert alert-danger fade in alert-dismissable" style="display: none">
									<i class="fa fa-warning"></i> <span id="errorMsg1"></span>
								</div>
								
								<div id="successAlert1" class="alert alert-success fade in alert-dismissable" style="display: none">
									<span id="successMsg1"><%=Resource.getString("ID_LABEL_TOTALAMOUNT",locale)%> <span id="calResult"></span></span>
								</div>
								
								<button type="button" class="btn btn-orange" id="calPrice"><%=Resource.getString("ID_LABEL_CALCULATE",locale)%></button>&nbsp;
								 --%>
								<p>&nbsp;</p>
								
								<% if(source.equals("draft")) { %>
									<button id="updateDraft" name="updateDraft" class="btn btn-default" type="button" onClick="draft('updatedraft')"><%=Resource.getString("ID_LABEL_SAVEASDRAFT",locale)%></button>&nbsp;
								<% } else { %>
									<button id="saveDraft" name="saveDraft" class="btn btn-default" type="button" onClick="draft('savedraft')"><%=Resource.getString("ID_LABEL_SAVEASDRAFT",locale)%></button>&nbsp;
								<% } %>
								
								<button id="createConsignment" name="createConsignment" class="btn btn-orange" onClick="cutoffCheck('addPoint')" type="submit"><%=Resource.getString("ID_LABEL_CONFIRMCREATE",locale)%></button>	
								<span id="loading" style="display:none"> &nbsp; <i class="fa fa-circle-o-notch fa-spin"></i></span>
								
		                    </footer>
							
						</form>

					</div>
			</div>
		</div>
		
	</div><!-- / Inner Page Content End -->	
			
    <!--=== End Content Part ===-->
    
    <!-- Modal -->
	<div class="modal fade" id="addressbook" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title"><%=Resource.getString("ID_LABEL_ADDRESSBOOK",locale)%></h4>
				</div>
				<div class="modal-body">
					<span id="addressbookList"><div style="margin:30px; text-align:center"><i class="fa fa-circle-o-notch fa-spin fa-2x"></i></div></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><%=Resource.getString("ID_LABEL_CLOSE",locale)%></button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	<!-- Model End -->


	<%@ include file="footer.jsp" %>
	
	<script type="text/javascript" src="./plugins/sky-forms/version-2.0.1/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="./plugins/sky-forms/version-2.0.1/js/jquery.maskedinput.min.js"></script>
	
	<!-- Order Form -->
	<script type="text/javascript" src="./plugins/sky-forms/version-2.0.1/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="./plugins/sky-forms/version-2.0.1/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="./plugins/sky-forms/version-2.0.1/js/jquery.form.min.js"></script>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			if("<%=result %>" == "createFailed"){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_CREATEFAILED",locale)%>: <%=errmsg %>");
			} else if( ("<%=result %>" == "savedraftFailed")||("<%=result %>" == "updatedraftFailed") ){
				$( "#errorAlert" ).show();
				$( "#errorMsg" ).html("<%=Resource.getString("ID_MSG_SAVEFAILED",locale)%>: <%=errmsg %>");
			}
			
			<%
				String tickvalue = tickItem;
				if(!tickvalue.equals("") && tickvalue != null){
					String tickSplit[] = tickvalue.split("\\,");
					for(int i = 0; i < tickSplit.length; i++){
						String tmp = tickSplit[i];
						out.print("$(\"#"+tmp+"\").prop('checked', true);");
					}
				}
			%>
			
			loadZone("sender");
			loadZone("receiver");
			cutoffCheck();
			
			OrderForm.initOrderForm();
		});
		
		
		function draft(action) {
			$( "#actionType" ).val(action);
			
			var tickvalue = "";
			for(var i = 1; i <= 4; i ++){
				if($("#tick" + i).is(':checked')){
					tickvalue = tickvalue + $("#tick" + i).val() + ",";
				}
			};
			$("#tickItem").val(tickvalue.substring(0, tickvalue.length-1));	
			
			document.createForm.submit();
		};
		
		function insertAddressDetail(fid, bookType) {
			if(bookType=="sender") {
				$("#senderName").val($("#senderName_"+fid).val());
				$("#senderAddress1").val($("#senderAddress1_"+fid).val());
				$("#senderAddress2").val($("#senderAddress2_"+fid).val());
				$("#senderAddress3").val($("#senderAddress3_"+fid).val());
				$("#senderPostcode").val($("#senderPostcode_"+fid).val());
				$("#senderPhone").val($("#senderPhone_"+fid).val());
				$("#senderZoneTmp").val($("#senderZone_"+fid).val());
				$("#senderArea").val($("#senderArea_"+fid).val());
				$("#senderCountry").val($("#senderCountry_"+fid).val());
				loadZone("sender");
			} else {
				$("#receiverName").val($("#receiverName_"+fid).val());
				$("#receiverAttn").val($("#receiverAttn_"+fid).val());
				$("#receiverAddress1").val($("#receiverAddress1_"+fid).val());
				$("#receiverAddress2").val($("#receiverAddress2_"+fid).val());
				$("#receiverAddress3").val($("#receiverAddress3_"+fid).val());
				$("#receiverPostcode").val($("#receiverPostcode_"+fid).val());
				$("#receiverPhone").val($("#receiverPhone_"+fid).val());
				$("#receiverZoneTmp").val($("#receiverZone_"+fid).val());
				$("#receiverArea").val($("#receiverArea_"+fid).val());
				$("#receiverCountry").val($("#receiverCountry_"+fid).val());
				loadZone("receiver");
			}
		};
		
		$("#loadSenderAddressBook").click(function(){
			
			$("#addressbook #addressbookList").html("<div style=\"margin:30px; text-align:center\"><i class=\"fa fa-circle-o-notch fa-spin fa-2x\"></i></div>");
			
			$.ajax({
				type: 'POST',
				url: './consignment',
				data:{    
					lang: "<%=locale %>",
					bookType: "sender",
					actionType:"ajaxGetAddressBook"
		   		},  
		   		
				success: function(response) {
					if(response != ""){
						$("#addressbook #addressbookList").html(response);
					}
				} 
			});
			
		});
		
		$("#loadReceiverAddressBook").click(function(){
			
			$("#addressbook #addressbookList").html("<div style=\"margin:30px; text-align:center\"><i class=\"fa fa-circle-o-notch fa-spin fa-2x\"></i></div>");
			
			$.ajax({
				type: 'POST',
				url: './consignment',
				data:{    
					lang: "<%=locale %>",
					bookType: "receiver",
					actionType:"ajaxGetAddressBook"
		   		},  
		   		
				success: function(response) {
					if(response != ""){
						$("#addressbook #addressbookList").html(response);
					}
				} 
			});
			
		});
		
		function validatenumber(event) {
			var key = window.event ? event.keyCode : event.which;

			if (key == 8 || key == 9 || key == 46) {
			    return true;
			}
			else if ( key < 48 || key > 57 ) {
			    return false;
			}
			else 
				return true;
		};
		
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

		
		function loadFreightType(){

			$("#freight").html("<select class=\"form-control\"><option value=\"0\"></option></select>");
			var senderfreight = $('#senderArea option:selected').attr("senderfreight");
			var receiverfreight = $('#receiverArea option:selected').attr("receiverfreight");

			if(senderfreight == 1 && receiverfreight == 1) { //Inter Sabah
				$("#freight").html("<select class=\"form-control\" id=\"freightType\" name=\"freightType\"><option value=\"1\"><%=Resource.getString("ID_LABEL_EXPRESSTXT",locale)%></option><option value=\"2\"><%=Resource.getString("ID_LABEL_SEMIEXPRESSTXT",locale)%></option></select>");
			} else if(senderfreight == 1 && receiverfreight == 2) { //Sabah to West Malaysia
				$("#freight").html("<select class=\"form-control\" id=\"freightType\" name=\"freightType\"><option value=\"3\"><%=Resource.getString("ID_LABEL_COURIERTXT",locale)%></option><option value=\"4\"><%=Resource.getString("ID_LABEL_CARGOTXT",locale)%></option></select>");
			} else if(senderfreight == 2 && receiverfreight == 1) { //West Malaysia to Sabah
				$("#freight").html("<select class=\"form-control\" id=\"freightType\" name=\"freightType\"><option value=\"3\"><%=Resource.getString("ID_LABEL_COURIERTXT",locale)%></option><option value=\"4\"><%=Resource.getString("ID_LABEL_CARGOTXT",locale)%></option></select>");
			} else if(senderfreight == 2 && receiverfreight == 2) { //Inter West Malaysia
				$("#freight").html("<select class=\"form-control\" id=\"freightType\" name=\"freightType\"><option value=\"1\"><%=Resource.getString("ID_LABEL_EXPRESSTXT",locale)%></option><option value=\"2\"><%=Resource.getString("ID_LABEL_SEMIEXPRESSTXT",locale)%></option></select>");
			};
			
		}
		
		function cutoffCheck(action) {
			
			var cutoff = $('#senderZone option:selected').attr("cutoff");
			var zone = $('#senderZone option:selected').text();
        	var dt = new Date();
        	var dtNow = dt.getHours();
        	
        	if(action == 'checkCutoff'){
        		$("#cutoffWarning").hide();
        		
	        	if(cutoff != "" && dtNow >= cutoff){
	        		$("#cutoffWarning").show();
					$("#cutoffWarning").html('Gentle remind: The cutoff time for zone "'+ zone +'" is '+ cutoff +':00, your consignment will be pickup by tomorrow.');
	        	};
	        	
        	}else if(action == 'addPoint'){
        		if(cutoff != "" && dtNow < cutoff){
        			
        			$.ajax({
    					type: 'POST',
    					url: './point',
    					data:{
    						actionType:"consignmentAddPoint",
    						userId: '<%=userId %>'
    			   		}
    				});
        			
	        	};
        	};
		};
		
		
		var OrderForm = function () {
			return {
				initOrderForm: function () {

					// Validation
					$("#createForm").validate({
						rules:
						{
							senderName:
							{
								required: true
							},
							senderAddress1:
							{
								required: true
							},
							senderPostcode:
							{
								required: true
							},
							senderPhone:
							{
								required: true
							},
							receiverName:
							{
								required: true
							},
							receiverAttn:
							{
								required: true
							},
							receiverAddress1:
							{
								required: true
							},
							receiverPostcode:
							{
								required: true
							},
							receiverPhone:
							{
								required: true
							},
							acceptTerms:
							{
								required: true
							}
						},

						messages:
						{
							senderName:
							{
								required: "<%=Resource.getString("ID_MSG_SENDERNAMEISREQUIRED",locale)%>"
							},
							senderAddress1:
							{
								required: "<%=Resource.getString("ID_MSG_SENDERADDRESSISREQUIRED",locale)%>"
							},
							senderPostcode:
							{
								required: "<%=Resource.getString("ID_MSG_SENDERPOSTCODEISREQUIRED",locale)%>"
							},
							senderPhone:
							{
								required: "<%=Resource.getString("ID_MSG_SENDERPHONEISREQUIRED",locale)%>"
							},
							receiverName:
							{
								required: "<%=Resource.getString("ID_MSG_RECEIVERNAMEISREQUIRED",locale)%>"
							},
							receiverAttn:
							{
								required: "<%=Resource.getString("ID_MSG_RECEIVERATTNISREQUIRED",locale)%>"
							},
							receiverAddress1:
							{
								required: "<%=Resource.getString("ID_MSG_RECEIVERADDRESSISREQUIRED",locale)%>"
							},
							receiverPostcode:
							{
								required: "<%=Resource.getString("ID_MSG_RECEIVERPOSTCODEISREQUIRED",locale)%>"
							},
							receiverPhone:
							{
								required: "<%=Resource.getString("ID_MSG_RECEIVERPHONEISREQUIRED",locale)%>"
							},
							acceptTerms:
							{
								required: "<%=Resource.getString("ID_MSG_YOUMUSTAGREE",locale)%>"
							}
						},

						// Do not change code below
						errorPlacement: function(error, element){
							error.insertAfter(element.parent());
						},
						
						submitHandler: function(form) {
							
							var tickvalue = "";
							for(var i = 1; i <= 4; i ++){
								if($("#tick" + i).is(':checked')){
									tickvalue = tickvalue + $("#tick" + i).val() + ",";
								}
							};
							
							
							$("#tickItem").val(tickvalue.substring(0, tickvalue.length-1));				

							$( "#actionType" ).val("createConsignment");
							$( "#saveDraft" ).attr('disabled','disabled');
							$( "#createConsignment" ).attr('disabled','disabled');
							$( "#loading" ).show();
							form.submit();
						}

					});
					
					
				}
			};
		}();
		
		function changeShipmentType() {
			var shipmentType = $("#shipmentType").val();
			if(shipmentType == 1) { //document
				//$("#quantitySection").hide();
				//$("#quantity").val("1");
				$("#weightSpan").html("<%=Resource.getString("ID_LABEL_WEIGHT",locale)%>");
			} else if (shipmentType == 2) { //parcel
				$("#quantitySection").show();
				$("#weightSpan").html("<%=Resource.getString("ID_LABEL_TOTALWEIGHT",locale)%>");
			}
		}
		

		$("#calPrice").click(function(){
			
			$("#errorAlert1").hide();
			$("#errorMsg1").html("");
			$("#successAlert1").hide();
			$("#calResult").html("");
			
			var senderArea = $("#senderArea option:selected").attr("addcode");
			var receiverArea = $("#receiverArea option:selected").attr("addcode");
			
			var shipmentType = $("#shipmentType").val();
			//var freightType = $("#freightType").val();
			var quantity = $("#quantity").val();
			var weight = $("#weight").val();
			var memberType = ($("#payMethod").val() == 4 ? "credit" : "normal");
			
			if(quantity.length == 0) {
				quantity = "1";
				$("#quantity").val(quantity);
			}
			
			if(weight.length == 0) {
				weight = "0";
			}
			

			if(senderArea == 0) {
				$("#errorMsg1").html("Please choose sender area");
				//$("#senderArea").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			if(receiverArea == 0) {
				$("#errorMsg1").html("Please choose receiver area");
				//$("#receiverArea").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			/*
			if(freightType == 0) {
				$("#errorMsg1").html("Please choose freight type");
				$("#freightType").focus();
				$("#errorAlert1").show();
				return false;
			}
			*/
			
			if(eval(quantity) <= 0) {
				$("#errorMsg1").html("Please input quantity (PCS)");
				//$("#quantity").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			if(eval(weight) <= 0) {
				$("#errorMsg1").html("Please input total weight (KG)");
				//$("#weight").focus();
				$("#errorAlert1").show();
				return false;
			}
			
			$("#calPrice").html("<i class='fa fa-circle-o-notch fa-spin'></i> Calculating...");
			
			$.ajax({
				type: 'POST',
				url: './pricing',
				data:{    
					actionType:"calculate",
					senderArea: senderArea,
					receiverArea: receiverArea,
					shipmentType: shipmentType,
					quantity: quantity,
					weight: weight,
					memberType: memberType
		   		},  
		   		
				success: function(response) {
					if(response != ""){
						
						var priceValue = response.split("|");
						var txt1 = "<font size=\"3\"><b>RM " + parseFloat(priceValue[0]).toFixed(2) + "</b></font>";
						var txt2 = "";
						if(priceValue[1] != priceValue[0]) {
							txt2 = "<span style=\"margin-left: 20px\"><font color=\"red\">Save <b>RM " + parseFloat(priceValue[1]-priceValue[0]).toFixed(2) + "</b></font></span>";	
						}
						
						$("#successAlert1").show();
						$("#calResult").html(txt1+txt2);
						$("#calPrice").text("<%=Resource.getString("ID_LABEL_CALCULATE",locale)%>");
					}
				} 
			});
		});
		
		//datepicker plugin
		$('.date-picker').datepicker({"dateFormat": "yy-mm-dd"});
		
	</script>


<!--[if lt IE 9]>
    <script src="./plugins/sky-forms/version-2.0.1/js/sky-forms-ie8.js"></script>
<![endif]-->

<!--[if lt IE 10]>
    <script src="./plugins/sky-forms/version-2.0.1/js/jquery.placeholder.min.js"></script>
<![endif]-->
	
</body>

</html>