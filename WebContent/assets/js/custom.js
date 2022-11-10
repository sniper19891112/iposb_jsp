/* *************************************** */ 
/* Cart Button Drop Down */
/* *************************************** */  

$(document).ready(function() {
	$('.btn-cart-md .cart-link').click(function(e){
		e.preventDefault();
		var $dd_menu = $('.btn-cart-md .cart-dropdown')
		if ($dd_menu.hasClass('open')) {
			$dd_menu.fadeOut();
			$dd_menu.removeClass('open');
		} else {
			$dd_menu.fadeIn();
			$dd_menu.addClass('open');
		}
	});
});

/* *************************************** */ 
/* Tool Tip JS */
/* *************************************** */  

// v Causes error: TypeError: $(...).tooltip is not a function
// $('.my-tooltip').tooltip(); 

/* *************************************** */ 
/* Scroll to Top */
/* *************************************** */  
		
$(document).ready(function() {
	$(".totop").hide();
	
	$(window).scroll(function(){
	if ($(this).scrollTop() > 300) {
		$('.totop').fadeIn();
	} else {
		$('.totop').fadeOut();
	}
	});
	$(".totop a").click(function(e) {
		e.preventDefault();
		$("html, body").animate({ scrollTop: 0 }, "slow");
		return false;
	});
		
});
/* *************************************** */
/* Test image */
/* *************************************** */  

function testImage(url) {
    // Define the promise
    const imgPromise = new Promise(function imgPromise(resolve, reject) {
        const imgElement = new Image(); // Create the image
        
        // When image is loaded, resolve the promise
        imgElement.addEventListener('load', function imgOnLoad() {
            resolve(this);
        });
        
        // When there's an error during load, reject the promise
        imgElement.addEventListener('error', function imgOnError() {
            reject();
        });

        // Assign URL
        imgElement.src = url;
    });
    return imgPromise;
}
/* *************************************** */