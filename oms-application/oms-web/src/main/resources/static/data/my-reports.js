$(function() {
	
	$.ajax({
		type : "GET",
		url : "reports",
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			
			$.each(result, function(i, data) {
				$("#po-bar-heading").text(data.name);
				
				$.ajax({
					type : "GET",
					url : "reports/"+data.id,
					headers : {
						'Accept' : 'application/json'
					},
					success : function(repoResult) {
						
						Morris.Bar({
					        element: 'po-bar-chart',
					        data: repoResult.data,
					        xkey: repoResult.xKey,
					        ykeys: repoResult.yKeys,
					        labels: repoResult.labels,
					        hideHover: 'auto',
					        resize: true
					    });
					}
				});

			});
		}
	});
	
});