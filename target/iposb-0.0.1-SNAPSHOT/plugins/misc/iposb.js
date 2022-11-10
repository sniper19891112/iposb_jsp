function randomString(lang) {
	var chars = "3456789ABCDEFGHJKLMNPQRTUVWXY";
	var string_length = 5;
	var randomstring = '';
	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	
	var lastdigit = "E";
	if(lang=="zh_CN") {
		lastdigit = "C";
	} else if(lang=="zh_TW") {
		lastdigit = "T";
	}
	
	document.getElementById("bookingCode").value = randomstring+lastdigit;
};

function randomVerifyString() {
	var chars = "0123456789abcdefghijkmnpqrstuvwxyz";
	var string_length = 10;
	var randomstring = '';
	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	document.getElementById("verify").value = randomstring;
};

function showHintText(target, txt){
	target.text(txt).addClass( "ui-state-highlight" );
	target.removeAttr( "style" ).hide().fadeIn();
	setTimeout(function() {
		target.hide()
	}, 3000);
};


function trim(stringToTrim){
	return stringToTrim.replace(/^\s+|\s+$/g,"");
};

function IsNumeric(sText){
	var ValidChars = "0123456789";
	var IsNumber=true;
	var Char;

	for (i = 0; i < sText.length && IsNumber == true; i++){ 
		Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1){
		IsNumber = false;
      }
	}
	return IsNumber;
};

function replaceApostrophe(strOrg){
	var index = 0;
	var strFind = "'";
	var strReplace = "&rsquo;";
	while(strOrg.indexOf(strFind,index) != -1){
		strOrg = strOrg.replace(strFind,strReplace);
		index = strOrg.indexOf(strFind,index);
	}
	return strOrg;
};

function handleAjaxSpecialMark(strOrg){
	var index = 0;
	var strFind = "&";
	var strReplace = "_AND_";
	while(strOrg.indexOf(strFind,index) != -1){
		strOrg = strOrg.replace(strFind,strReplace);
		index = strOrg.indexOf(strFind,index);
	}
	return strOrg;
};

function numericCheck(obj){
	var num = obj.value;
	var oid = obj.id;
	if(IsNumeric(num)==false){
		document.getElementById(oid).value = 0;
	}
	if(trim(num).length == 0){
		document.getElementById(oid).value = 0;
	}
}

function getInternetExplorerVersion(){
	var rv = -1; // Return value assumes failure.
	if (navigator.appName == 'Microsoft Internet Explorer') {
		var ua = navigator.userAgent;
		var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		if (re.exec(ua) != null)
		rv = parseFloat( RegExp.$1 );
	}
	return rv;
}
function checkVersion()	{
	var msg = "";
	var ver = getInternetExplorerVersion();

	if ( ver > -1 ) {
		if ( ver < 8.0 ) 
		msg = "old";
	}
	return msg;
}


function checkExist(obj){

	var current = $("#"+obj+"_Current").val();
	var newInput = $("#"+obj).val();
	$("#checkingSpan_"+obj).html("");
	
	if(trim(current).length > 0) { //profile
		if (current == newInput) {
			//do nothing
		} else {
			startCheck(obj);
		}
	} else { //register
		startCheck(obj);
	}
	
};

function startCheck(obj) {

	var objvalue = $("#"+obj).val();
		
	if(obj=="email") {
		validRegExp = /^[^@]+@[^@]+.[a-z]{2,}$/i;
		if(trim(objvalue).search(validRegExp) == -1){
			$("#checkingSpan_"+obj).html("<span style='padding: 0 1px; font-style: normal; font-size: 11px; line-height: 15px; color: #ee9393;'>Invalid format.</span>");
			return false;
		}
	}
	
	if(obj=="userId") {
		if(trim(obj).length < 3){
			return false;
		}
	}
	
	$("#checkingSpan_"+obj).html("<i class='fa fa-circle-o-notch fa-spin'></i>");
	
	$.ajax({
		type: 'POST',
		url: './logon?actionType=isExist&' + obj + '=' + objvalue + '&timeStamp=' + new Date().getTime(),
		
		success: function(response) {
			
			$("#checkingSpan_"+obj).html("");
			$("#"+obj+"_Exist").val("");
			
			if(response == "Y"){
				$("#checkingSpan_"+obj).html("<span style='padding: 0 1px; font-style: normal; font-size: 11px; line-height: 15px; color: #ee9393;'>It has been taken</span>");
				$("#"+obj+"_Exist").val("Y");
			} else if(response == "N") {
				$("#checkingSpan_"+obj).html("<i class='fa fa-check-circle green'></i>")
				$("#"+obj+"_Exist").val("N");
			} else {
				$("#checkingSpan_"+obj).html("");
			}
			
		} 
	});

}


$(function() {
	$( "#bookBtn" ).click(function() { submitCheck(); return false; });
});

$(function() {
	$( "#cancelBtn" ).click(function() { history.back(); return false; });
});

$(function() {
	$( "#backBtn" ).click(function() { history.back(); return false; });
});

$(function() {
	$( "#submitBtn" ).click(function() { submitCheck(); return false; });
});



function showPromoColumn() {
	$( "#hintTxt" ).hide();
	$( "#promoCode" ).show();
}


/*
	TOOLTIP
	http://osvaldas.info/elegant-css-and-jquery-tooltip-responsive-mobile-friendly
*/

$( function()
{
	var targets = $( '[rel~=tooltip]' ),
		target	= false,
		tooltip = false,
		title	= false;

	targets.bind( 'mouseenter', function()
	{
		target	= $( this );
		tip		= target.attr( 'title' );
		tooltip	= $( '<div id="tooltip"></div>' );

		if( !tip || tip == '' )
			return false;

		target.removeAttr( 'title' );
		tooltip.css( 'opacity', 0 )
			   .html( tip )
			   .appendTo( 'body' );

		var init_tooltip = function()
		{
			if( $( window ).width() < tooltip.outerWidth() * 1.5 )
				tooltip.css( 'max-width', $( window ).width() / 2 );
			else
				tooltip.css( 'max-width', 340 );

			var pos_left = target.offset().left + ( target.outerWidth() / 2 ) - ( tooltip.outerWidth() / 2 ),
				pos_top	 = target.offset().top - tooltip.outerHeight() - 20;

			if( pos_left < 0 )
			{
				pos_left = target.offset().left + target.outerWidth() / 2 - 20;
				tooltip.addClass( 'left' );
			}
			else
				tooltip.removeClass( 'left' );

			if( pos_left + tooltip.outerWidth() > $( window ).width() )
			{
				pos_left = target.offset().left - tooltip.outerWidth() + target.outerWidth() / 2 + 20;
				tooltip.addClass( 'right' );
			}
			else
				tooltip.removeClass( 'right' );

			if( pos_top < 0 )
			{
				var pos_top	 = target.offset().top + target.outerHeight();
				tooltip.addClass( 'top' );
			}
			else
				tooltip.removeClass( 'top' );

			tooltip.css( { left: pos_left, top: pos_top } )
				   .animate( { top: '+=10', opacity: 1 }, 50 );
		};

		init_tooltip();
		$( window ).resize( init_tooltip );

		var remove_tooltip = function()
		{
			tooltip.animate( { top: '-=10', opacity: 0 }, 50, function()
			{
				$( this ).remove();
			});

			target.attr( 'title', tip );
		};

		target.bind( 'mouseleave', remove_tooltip );
		tooltip.bind( 'click', remove_tooltip );
	});
});