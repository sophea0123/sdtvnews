$(document).ready(function () {

	$('#tableReponse tbody').on('click', '.edit-category', function () {
		var categoryId = $(this).data('id'); // Get the category ID from data attribute
		$.ajax({
			url: '/category/edit/' + categoryId, // Adjust the URL to match your endpoint
			method: 'GET',
			success: function (data) {
				// Assuming data is of type CategorysResponse
				$('#edit_categorys input[name="id"]').val(data.id);
				$('#edit_categorys input[name="name"]').val(data.name);
				$('#edit_categorys textarea[name="description"]').val(data.description);
				$('#edit_categorys select[name="indexShow"]').val(data.indexShow);
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

//	// Fetch categories when the page loads
//    fetchCategoryList();
//
//    // Function to fetch categories from the back-end and render them
//    function fetchCategoryList() {
//        $.ajax({
//            url: '/category/list',
//            method: 'GET',
//            success: function (categories) {
//                $('#category-list ul').empty(); // Clear the list before appending
//
//                // Loop through the categories and append them to the list
//                categories.forEach(function (category, index) {
//                    var $listItem = $('<li>').data('id', category.id).append(
//                        $('<span>').text(category.name),
//                        $('<button>').addClass('move-up').text('Up'),
//                        $('<button>').addClass('move-down').text('Down')
//                    );
//                    $('#category-list ul').append($listItem);
//                });
//
//                // Bind events after the list is rendered
//                bindMoveCategoryButtons();
//            },
//            error: function () {
//                alert('Error fetching categories');
//            }
//        });
//    }
//
//    // Function to handle category movement
//    function bindMoveCategoryButtons() {
//        $('.move-up').on('click', function () {
//            var $category = $(this).closest('li');
//            var categoryId = $category.data('id');
//            var index = $category.indexShow();
//            var direction = 'up';
//
//            moveCategory(categoryId, index, direction);
//        });
//
//        $('.move-down').on('click', function () {
//            var $category = $(this).closest('li');
//            var categoryId = $category.data('id');
//            var index = $category.indexShow();
//            var direction = 'down';
//
//            moveCategory(categoryId, index, direction);
//        });
//    }
//
//    // Function to move the category
//    function moveCategory(categoryId, index, direction) {
//        $.ajax({
//            url: '/category/move',
//            method: 'POST',
//            contentType: 'application/json',
//            data: JSON.stringify({
//                categoryId: categoryId,
//                index: index,
//                direction: direction
//            }),
//
//            success: function () {
//                fetchCategoryList();  // Re-fetch the category list to reflect changes
//                alert('Category moved ' + direction);
//            },
//            error: function () {
//                alert('Error moving category');
//            }
//        });
//    }

});