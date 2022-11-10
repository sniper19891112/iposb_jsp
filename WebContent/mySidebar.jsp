<%@include file="include.jsp" %>
<% 
	String tab = request.getParameter("tab") == null ? "" : request.getParameter("tab").toString();

	boolean isStaff = false;
	if(priv >= 5) { //staff
		isStaff = true;
	}
%>
		<div class="single-item single-item-content">
			<div class="col-md-3">
				
				<div id="profilePicDiv">
					<div id="avatarDIV" data-content="<%=Resource.getString("ID_LABEL_CHANGEAVATAR",locale)%>" class="avatar" > 
					    <div class="imageBox">
					    	<%-- 
							<img id="myAvatar" class="img-responsive profile-img" src="https://s3-ap-southeast-1.amazonaws.com/iposb/images/avatar/<%=avatarPath %>/<%=id %>/avatar.jpg?<%=avatarDT %>" onerror="this.src='assets/img/noavatar.jpg'" alt="">
							<img id="myAvatar" class="img-responsive profile-img" alt="">
							--%>	
							<input type="hidden" id="avatarPath" value="<%=avatarPath %>">
							<input type="hidden" id="avatarId" value="<%=id %>">
							<input type="hidden" id="avatarDT" value="<%=avatarDT %>">
					    </div>
					</div>
					
					<div id="editAvatar" style="display:none">
					    <div class="imageBox">
					        <div class="thumbBox"></div>
					        <div class="spinner" style="display: none"><%=Resource.getString("ID_LABEL_LOADING",locale)%></div>
					    </div>
					    
					    <div class="action">
					        <input type="file" id="uploadAvatar" name="uploadAvatar" accept='image/*' style="float:left; width:0px;visibility:hidden">
							<button class="btn rounded btn-default" type="button" id="btnZoomOut"><i class="fa fa-search-minus"></i></button>
							<button class="btn rounded btn-primary" type="button" id="btnZoomIn"><i class="fa fa-search-plus"></i></button>
					        <button class="btn rounded btn-success" type="button" id="btnCrop"><i class="fa fa-save"></i> <%=Resource.getString("ID_LABEL_SAVE",locale)%></button>
					    </div>
					</div>
					
					<div id="avatarResult" class="text-center" style="display:none;"></div>
					
				</div>
				
					
				<% if(!isStaff) { %>
					
	                <ul class="list-group sidebar-nav-v1" id="sidebar-nav">
	                    <li class="list-group-item <% if(tab.trim().equals("my")) { out.print("active");} %>"><a href="./my"><i class="fa fa-cubes fa-lg blue"></i> <%=Resource.getString("ID_LABEL_MANAGEMYCONSIGNMENT",locale)%></a></li>
	                    <li class="list-group-item <% if(tab.trim().equals("draft")) { out.print("active");} %>"><a href="./draft"><i class="fa fa-edit fa-lg blue"></i> <%=Resource.getString("ID_LABEL_CONSIGNMENTDRAFT",locale)%></a></li>
						<li class="list-group-item <% if(tab.trim().equals("addressbook")) { out.print("active");} %>"><a href="./addressbook"><i class="fa fa-book fa-lg blue"></i> <%=Resource.getString("ID_LABEL_ADDRESSBOOK",locale)%></a></li>
						<% if (priv==2) { %>
							<li class="list-group-item <% if(tab.trim().equals("creditaccount")) { out.print("active");} %>"><a href="./creditaccount"><i class="fa fa-sitemap fa-lg blue"></i> <%=Resource.getString("ID_LABEL_CREDITACCOUNT",locale)%></a></li>
						<% } %>
	                </ul>
                <% } %>
                
                <ul class="list-group sidebar-nav-v1" id="sidebar-nav">
                	<% if(isStaff) { %>
                		<li class="list-group-item <% if(tab.trim().equals("profile")) { out.print("active");} %>"><a href="./staffprofile"><i class="fa fa-user fa-lg blue"></i> <%=Resource.getString("ID_LABEL_PROFILE",locale)%></a></li>
						<li class="list-group-item <% if(tab.trim().equals("password")) { out.print("active");} %>"><a href="./password?role=staff"><i class="fa fa-lock fa-lg blue"></i> <%=Resource.getString("ID_LABEL_CHANGEPASSWORD",locale)%></a></li>
                	<% } else { %>
	                    <li class="list-group-item <% if(tab.trim().equals("profile")) { out.print("active");} %>"><a href="./profile"><i class="fa fa-user fa-lg blue"></i> <%=Resource.getString("ID_LABEL_PROFILE",locale)%></a></li>
						<li class="list-group-item <% if(tab.trim().equals("password")) { out.print("active");} %>"><a href="./password"><i class="fa fa-lock fa-lg blue"></i> <%=Resource.getString("ID_LABEL_CHANGEPASSWORD",locale)%></a></li>
					<% } %>
                </ul>
            </div>
        </div>