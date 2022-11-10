<script type="text/javascript" src="<%=resPath %>/assets/plugins/jquery-ui-1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=resPath %>/assets/plugins/misc/jquery.form.compressed.js"></script>

<g:compress>
	<script type="text/javascript" src="../plugins/misc/iposb.js"></script>
	<script type="text/javascript" src="../plugins/misc/jquery.scannerdetection.js"></script>
	<script type="text/javascript" src="../plugins/ace/js/ace-extra.min.js"></script>
</g:compress>

<!--[if lt IE 9]>
	<script src="plugins/ace/js/html5shiv.js"></script>
	<script src="plugins/ace/js/respond.min.js"></script>
<![endif]-->



<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
	<i class="icon-double-angle-up icon-only bigger-110"></i>
</a>

<!--[if !IE]> -->

<script type="text/javascript">
	window.jQuery || document.write("<script src='plugins/ace/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='plugins/ace/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<g:compress>
	<script type="text/javascript">
		if("ontouchend" in document) document.write("<script src='plugins/ace/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="../plugins/ace/js/bootstrap.min.js"></script>
	<script src="../plugins/ace/js/typeahead-bs2.min.js"></script>

	<script src="../plugins/ace/js/ace-elements.min.js"></script>
	<script src="../plugins/ace/js/ace.min.js"></script>
</g:compress>