

//load categories 
function loadCategories(categoryEleId) {
	var baseUrl = document.location.origin;
	$.ajax({
		type : "GET",
		url : baseUrl+"/api/categories",
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			$(categoryEleId).find("option").remove();
			var newOption = $('<option>');
			newOption.attr('value', '0').text('--Select--');
			$(categoryEleId).append(newOption);
			$.each(result, function(i, data) {
				var newOption = $('<option>');
				newOption.attr('value', data.id).text(data.name);
				$(categoryEleId).append(newOption);

			});
		}, error: function(xhr){
            alert("An error occured: " + xhr.status + " " + xhr.statusText);
        }
	});
};

//load sub categories based on category id
$("#Category-id").change(function() {
	var categoryId = $("#Category-id").val();
	var baseUrl = document.location.origin;
	$.ajax({
		type : "GET",
		url : baseUrl+"/api/categories/"+ categoryId + "/subcategories",
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			$('#SubCategory-id').find("option").remove();
			var newOption = $('<option>');
			newOption.attr('value', '0').text('--Select--');
			$('#SubCategory-id').append(newOption);
			$.each(result, function(i, data) {
				var newOption = $('<option>');
				newOption.attr('value', data.id).text(data.name);
				$('#SubCategory-id').append(newOption);

			});
		}, error: function(xhr){
            alert("An error occured: " + xhr.status + " " + xhr.statusText);
        }
	});
});


//load sub categories based on category id
$("#SubCategory-id").change(function() {
	var categoryId = $('#Category-id').val();
	var subCategoryId = $('#SubCategory-id').val();
	var baseUrl = document.location.origin;
	$.ajax({
		type : "GET",
		url : baseUrl+"/api/categories/"+ categoryId + "/subcategories/"+subCategoryId+"/products" ,
		headers : {
			'Accept' : 'application/json'
		},
		success : function(result) {
			$('#Product-id').find("option").remove();
			var newOption = $('<option>');
			newOption.attr('value', '').text('--Select--');
			$('#Product-id').append(newOption);
			$.each(result, function(i, data) {
				var newOption = $('<option>');
				newOption.attr('value', data.id).text(data.name);
				$('#Product-id').append(newOption);

			});
		}, error: function(xhr){
            alert("An error occured: " + xhr.status + " " + xhr.statusText);
        }
	});
});
