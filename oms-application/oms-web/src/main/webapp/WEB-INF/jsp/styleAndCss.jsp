<title>OMS Solutions</title>

<!-- Bootstrap Core CSS -->
<link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="${pageContext.request.contextPath}/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${pageContext.request.contextPath}/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="${pageContext.request.contextPath}/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- jQuery -->
	<script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="${pageContext.request.contextPath}/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="${pageContext.request.contextPath}/dist/js/sb-admin-2.js"></script>

	<!--Project custom JavaScript -->
	<script src="${pageContext.request.contextPath}/oms/oms-rest-client.js"></script>

	<!-- Datepicker related JavaScript -->
	<script src="${pageContext.request.contextPath}/datepicker/js/bootstrap-datepicker.js"></script>

	<script type="text/javascript">
		$(function() {
			$('#datetimepicker1 input').datepicker({
				format : "yyyy/mm/dd",
				clearBtn : true,
				autoclose : true,
				todayHighlight : true
			});
		});
	</script>
	


    <!-- Morris Charts JavaScript -->
    <script src="${pageContext.request.contextPath}/vendor/raphael/raphael.min.js"></script>
    <script src="${pageContext.request.contextPath}/vendor/morrisjs/morris.min.js"></script>
    <script src="${pageContext.request.contextPath}/data/morris-data.js"></script>

