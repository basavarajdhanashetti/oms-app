<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<jsp:include page="styleAndCss.jsp"></jsp:include>

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<jsp:include page="navigation.jsp"></jsp:include>


		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Welcome
						${sessionScope.Session_UserDetails.userName}</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-tasks fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge" id="idTaskCount">0</div>
									<div>New Tasks!</div>
								</div>
							</div>
						</div>
						<a href="${pageContext.request.contextPath}/process/tasks">
							<div class="panel-footer">
								<span class="pull-left">View Details</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-table fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div class="huge" id="idPOCount">0</div>
									<div>Purchase Orders!</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">View Details</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>

			</div>
			<!-- /.row -->
			 <div class="row" id="charts-panel">
			 </div>
			<!-- <div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> Area Chart Example
							
						</div>
						/.panel-heading
						<div class="panel-body">
							<div id="morris-area-chart"></div>
						</div>
						/.panel-body
					</div>
				</div>
				/.col-lg-8
				<div class="col-lg-6">
					/.panel
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> Bar Chart Example
						</div>
						/.panel-heading
						<div class="panel-body">
							<div class="row">
								/.col-lg-4 (nested)
								<div class="col-lg-12">
									<div id="morris-bar-chart"></div>
								</div>
								/.col-lg-8 (nested)
							</div>
							/.row
						</div>
						/.panel-body
					</div>
					/.panel
				</div>
				/.col-lg-8
				<div class="col-lg-6">
					/.panel
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> <span id="po-bar-heading"/>
						</div>
						<div class="panel-body">
							<div id="po-bar-chart"></div>
						</div>
						/.panel-body
					</div>
					/.panel
					/.panel .chat-panel
				</div>
				/.col-lg-8
				<div class="col-lg-6">
					/.panel
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-bar-chart-o fa-fw"></i> Donut Chart Example
						</div>
						<div class="panel-body">
							<div id="morris-donut-chart"></div>
							<a href="#" class="btn btn-default btn-block">View Details</a>
						</div>
						/.panel-body
					</div>
					/.panel
					/.panel .chat-panel
				</div>
				/.col-lg-4
			</div> -->
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->


</body>

<script type="text/javascript">

$(function() {
	
	var baseUrl = document.location.origin;
	
	//Dashboard building
	
	//Charts for User
	$.ajax({
		type : "GET",
		url : baseUrl+"/api/reports",
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			
			$.each(result, function(i, data) {
				
				$("#charts-panel").append("<div class=\"col-lg-6\"> "+ 
					"<div class=\"panel panel-default\">"+
						"<div class=\"panel-heading\">"+
							"<i class=\"fa fa-bar-chart-o fa-fw\"></i> <span id=\"morris-chart-heading-"+data.id+"\"> "+
						"</div>"+
						"<div class=\"panel-body\">"+
							"<div id=\"morris-chart-id-"+data.id+"\"></div>"+
						"</div>"+						
					"</div>"+
				"</div>");
				
				$("#morris-chart-heading-"+data.id).text(data.name);
				
				$.ajax({
					type : "GET",
					url : baseUrl+"/api/reports/"+data.id,
					headers : {
						'Accept' : 'application/json'
					},
					success : function(repoResult) {
						
						if(data.chartType == 'BarChart'){
							Morris.Bar({
						        element: 'morris-chart-id-'+data.id,
						        data: repoResult.data,
						        xkey: repoResult.xKey,
						        ykeys: repoResult.yKeys,
						        labels: repoResult.labels,
						        hideHover: 'false',
						        resize: true
						    });
						}else if(data.chartType == 'AreaChart'){
							Morris.Area({
						        element: 'morris-chart-id-'+data.id,
						        data: repoResult.data,
						        xkey: repoResult.xKey,
						        ykeys: repoResult.yKeys,
						        labels: repoResult.labels,
						        hideHover: 'false',
						        resize: true
						    });
						}else if(data.chartType == 'AreaChart'){
							 Morris.Donut({
							        element: 'morris-chart-id-'+data.id,
							        data: repoResult.labelValues,
							        resize: true
							    });
						}
						
					},
					error: function(err){
						alert('failed to read data'+ JSON.stringify(err));
					}
				});

			});
		}
	});
	
	
	//populate Task Count
	$.ajax({
		type : "GET",
		url : baseUrl+"/api/dashboard/tasks",
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			$("#idTaskCount").text(result.length);
		}
	});
	
	//populate PO Count
	$.ajax({
		type : "GET",
		url : baseUrl+"/api/dashboard/purchaseorders",
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			$("#idPOCount").text(result.length);
		}
	});
	
});

</script>

</html>
