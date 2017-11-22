$(function() {
	alert('Getting reports..');
	var baseUrl = document.location.origin;
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
	
});