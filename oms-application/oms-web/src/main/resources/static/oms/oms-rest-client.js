

//load categories 
function loadCategories(categoryEleId) {
	$.ajax({
		type : "GET",
		url : "categories",
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
		}
	});
};

//load sub categories based on category id
$("#Category-id").change(function() {
	var categoryId = $("#Category-id").val();
	$.ajax({
		type : "GET",
		url : "categories/"+ categoryId + "/subcategories",
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
		}
	});
});


//load sub categories based on category id
$("#SubCategory-id").change(function() {
	var categoryId = $('#Category-id').val();
	var subCategoryId = $('#SubCategory-id').val();
	$.ajax({
		type : "GET",
		url : "categories/"+ categoryId + "/subcategories/"+subCategoryId+"/products" ,
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
		}
	});
});
