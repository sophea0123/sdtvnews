$(document).ready(function () {

	//$('#tableReponse tbody').on('click', '.edit-category', function () {
	$(document).on('click', '.edit-category', function () {
		var categoryId = $(this).data('id'); // Get the category ID from data attribute
		$.ajax({
			url: '/category/edit/' + categoryId, // Adjust the URL to match your endpoint
			method: 'GET',
			success: function (data) {
				// Assuming data is of type CategorysResponse
				$('#edit_categorys input[name="id"]').val(data.id);
				$('#edit_categorys input[name="name"]').val(data.name);
				$('#edit_categorys input[name="indexShow"]').val(data.indexShow);
				$('#edit_categorys textarea[name="description"]').val(data.description);
				$('#edit_categorys input[name="status"]').val(data.status);
				// Show the modal after setting the values
				$('#edit_categorys').modal('show'); // This line will display the modal
			},
			error: function () {
				alert("Error fetching category data");
			}
		});
	});

	$('#name').on('input', function () {
		var name = $(this).val();
		// Make AJAX call to check for duplicate title
		$.ajax({
			url: '/category/checkNameDuplicate', // Adjust the path if necessary
			method: 'GET',
			data: { name : name },
			success: function (response) {
				if (response.isDuplicate) {
					$('#duplicateMessage').show(); // Show message if duplicate
					$('#btn-submit').prop('disabled', true);
				} else {
					$('#duplicateMessage').hide(); // Hide message if not duplicate
					$('#btn-submit').prop('disabled', false);
				}
			},
			error: function () {
				console.error("Error checking name duplicate status.");
			}
		});
	});

});