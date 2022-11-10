jQuery(document).ready(function() {

	var avatarPath = $("#avatarPath").val(); // staff/member
	var avatarId = $("#avatarId").val(); // sid/mid
	var avatarDT = $("#avatarDT").val();// To get new uploaded img instead of cached one
	var avatarSrc = 'https://s3-ap-southeast-1.amazonaws.com/iposb/images/avatar/'+avatarPath+'/'+avatarId+'/avatar.jpg?'+ avatarDT;
	
	testImage(avatarSrc).then(
		function fulfilled() {
			//do nothing
	    },function rejected() {
	    	avatarSrc = "assets/img/noavatar.jpg";
	    }
	);
	
	//upload avatar
	var options =
	{
		thumbBox: '.thumbBox',
		spinner: '.spinner',
		imgSrc: avatarSrc
		//imgSrc: avatarSrc
	}
	var cropper = $('.imageBox').cropbox(options);
	$('#uploadAvatar').on('change', function(){
		var reader = new FileReader();
		reader.onload = function(e) {
			options.imgSrc = e.target.result;
			cropper = $('.imageBox').cropbox(options);
		}
		reader.readAsDataURL(this.files[0]);
		//this.files = []; // causes weird error
	})
	
	$('#btnZoomIn').on('click', function(){
		cropper.zoomIn();
	})
	$('#btnZoomOut').on('click', function(){
		cropper.zoomOut();
	})
	
	$('#btnCrop').on('click', function(){
		
		var blob = cropper.getBlob();
		var fd = new FormData();
		fd.append('file', blob);

		var img = cropper.getDataURL();
		//$("#myAvatar").attr("src", img);
		
		$("#avatarDIV").show();
		$("#editAvatar").hide();
		
		$.ajax({
			type: 'POST',
			url: './upload?actionUpload=avatar&path='+avatarPath+'&id='+avatarId,
			data: fd,
			cache: false,
			contentType: false,
			processData: false,
			
			beforeSend: function() {
				$('#avatarResult').html('<p><i style="color:#FF6600" class="fa fa-spinner fa-spin" aria-hidden="true"></i> Uploading...</p>');
				$('#avatarResult').fadeIn();
		    },
			success: function(data){
				if(data !== undefined && data !== null){
					if(data.trim() == "uploadSuccess"){ // Success
						let msg = 'Upload Success';
						$('#avatarResult').html('<p><i style="color:#00941a" class="fa fa-check" aria-hidden="true"></i> '+msg+'</p>');
					}else if(data.trim() == "uploadFailed"){ // Failed
						let msg = 'Upload failed, please refresh and try again.';
						$('#avatarResult').html('<p><i style="color:#f71111" class="fa fa-times" aria-hidden="true"></i> '+msg+'</p>');
					}
				}
			},
			error: function (error) {
				$('#avatarResult').html('<p><i style="color:#f71111" class="fa fa-times" aria-hidden="true"></i> Upload failed: '+String(error)+' </p>');
			},
			complete: function() {
				this.files = [];
				
				/*setTimeout(function(){
					$('#avatarResult').fadeOut(1000);
					setTimeout(function(){ $('#avatarResult').html('') }, 1000);
				}, 5000);*/
		    },
		    
		});

	})
});

$(function() {
	$( ".avatar" ).click(function() { 
		$("#uploadAvatar").get(0).click();
		$("#avatarDIV").hide();
		$("#editAvatar").show();
	});
});