var DBevents = "";
var eventInArray = "";
var todayY = "";
var todayM = "";
var todayD = "";
var showY = "";
var showM = "";
var showD = "";

var dow = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];

function valButton(btn) {
	var cnt = -1;for (var i=btn.length-1; i > -1; i--) {if (btn[i].checked) {cnt = i; i = -1;}}if (cnt > -1) return btn[cnt].value;else return null;
}

function monthLength(month, year) {
	var dd = new Date(year, month, 0);
	return dd.getDate();
}

function setCell(f, month, day, col, station) {
	
	showD = day;
	if(day.toString().length==1){
		showD = "0"+day.toString();
	}

	var c = [];
	var t = '<td';
	if (f==0) c.push('previous');
	if (col==0 || col==6) c.push('weekend');
	if ((month == eval(todayM)-1)&&(day == eval(todayD))) {
		c.push('today');
	}
	if (f==9) c.push('next');
	if (c.length>0) t+=' class="'+c.join(' ')+'"';
	
	if(f!=0 && f!=9) { //not previous or next month
		t += '><a href="javascript:void(0)" style="margin-left: 2px; color: #000; text-decoration:none; cursor:default"><div class="date" style="background-color:#FFF7D7; margin-top:-19px; height:25px">'+day+'<a href="./consignment?actionType=searchConsignmentByDay&DT='+showY+'-'+showM+'-'+showD+'&station='+station+'" target="_blank" style="float:right; cursor:pointer; margin-right:5px"><i class="fa fa-align-justify fa-2x light-grey" title="View List Details"></i></a><\/div><\/a>'
	} else {
		t += '><a href="javascript:void(0)" style="margin-left: 2px; color: #000; text-decoration:none; cursor:default"><div class="date" style="background-color:#FFF7D7; margin-top:-19px; height:25px">'+day+'<\/div><\/a>'
	}
	
	var ev = "";
	var content = "";

	if(f!=0 && f!=9) { //not previous or next month
		for (i = 0; i < eventInArray.length; i++){
			ev = eventInArray[i]; //fetch data one by one
			var DAY = ev.substring(0, 2);
			var STATUS = ev.substring(3, 4);
			var CN = ev.substring(5, 14);
			var PartnerCN = ev.substring(15, ev.length);
			
			if(STATUS == "3") { //pending
				bgcolor = "#FE6C61";
			} else {
				bgcolor = "#8CBF40";
			}
			
			if(DAY == day){ //first 2 letters is equal to today
				content += "<a href='./consignment?actionType=searchConsignment&consignmentNo="+ CN +"' title='"+CN+PartnerCN+"' target='_blank' style='text-decoration:none; color: #FFFFFF; font-size:12px'><div class='ellipsis' style='background-color:"+bgcolor+"; height:22px;'>&nbsp;" + CN + PartnerCN + "</div></a>";
			}
		}
		
		t += '<div class="day"><span id="bankslip_'+showY+"-"+showM+"-"+showD+'">'+content+'<\/div><\/td>\n';
	}

	return t;
}
function setCal(fm, eventString, showYear, showMonth) {
	
	var station = document.getElementById("station").value;
	
	DBevents = eventString;
	eventInArray = DBevents.split(";"); //to form an array list.
	todayY = fm.todayYear.value;
	todayM = fm.todayMonth.value;
	todayD = fm.todayDay.value;
	
	showY = showYear;
	showM = showMonth;

	var m = parseInt(fm.month.value,10) - 1;
	var y = parseInt(fm.year.value,10);
	if (y < 1901 || y > 2100) {
		alert('year must be after 1900 and before 2101'); return false;
	}
	
	var c = new Date();
	c.setDate(1);
	c.setMonth(m);
	c.setFullYear(y);
	var x = parseInt(valButton(fm.day),10);
	var s = (c.getDay()-x)%7; 
	if (s<0) s+=7;
	var dm = monthLength(m,y);
	var h = '<table id="month">\n<thead>\n<tr>\n';
	for (var i=0;i<7;i++) {
		h+= '<th';
		if ((i+x)%7==0 || (i+x)%7==6) h+= ' class="weekend"';
		h+= '>'+dow[(i+x)%7]+'<\/th>\n';
	}

	h += '<\/tr>\n<\/thead>\n<tbody>\n<tr>\n';

	for (var i=s;i>0;i--) {
		h += setCell(0, m, dm-i+1, (s-i+x)%7, station); //f=0: previous month
	}
	dm = monthLength(m+1,y);
	
	for(var i=1; i <= dm; i++) {
		if((s%7)==0) {h += '<\/tr><tr>\n'; s = 0;}
		h += setCell(1, m, i, (s+x)%7, station);
		s++;
	}
	var j=1;
	
	for (var i=s;i<7;i++) {
		h += setCell(9, m, j, (i+x)%7, station); //f=9: next month
		j++;
	}
	
	h += '<\/tr>\n<\/tbody>\n<\/table>';
	document.getElementById('generateCalHere').innerHTML = h;
}
