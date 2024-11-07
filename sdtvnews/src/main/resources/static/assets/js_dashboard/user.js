$(document).ready(function () {

    $('#tableReponse tbody').on('click', '.edit-user', function () {
        var userId = $(this).data('id'); // Get the category ID from data attribute
        $.ajax({
            url: '/user/edit/' + userId, // Adjust the URL to match your endpoint
            method: 'GET',
            success: function (data) {
                // Assuming data is of type CategorysResponse
                $('#edit_users input[name="id"]').val(data.id);
                $('#edit_users input[name="firstName"]').val(data.firstName);
                $('#edit_users input[name="lastName"]').val(data.lastName);
                $('#edit_users input[name="userName"]').val(data.userName);
                $('#edit_users input[name="passWord"]').val(data.passWord);
                $('#edit_users select[name="roleId"]').val(data.roleId).change();
                $('#edit_users input[name="status"]').val(data.status);

                // Show the modal after setting the values
                $('#edit_users').modal('show'); // This line will display the modal

            },
            error: function () {
                alert("Error fetching user data");
            }
        });
    });
    $('#togglePassword').on('click', function () {
        // Toggle the type attribute
        const passwordInput = $('#passWord');
        const eyeIcon = $('#eyeIcon');
        const type = passwordInput.attr('type') === 'password' ? 'text' : 'password';
        passwordInput.attr('type', type);

        // Toggle the eye icon
        eyeIcon.toggleClass('fa-eye fa-eye-slash');
    });

    $('#EdittogglePassword').on('click', function () {
        // Toggle the type attribute
        const passwordInput = $('#EditpassWord');
        const eyeIcon = $('#EditeyeIcon');
        const type = passwordInput.attr('type') === 'password' ? 'text' : 'password';
        passwordInput.attr('type', type);

        // Toggle the eye icon
        eyeIcon.toggleClass('fa-eye fa-eye-slash');
    });

    $('#userName').on('input', function () {
    		var username = $(this).val();

    		// Make AJAX call to check for duplicate title
    		$.ajax({
    			url: '/user/checkNameDuplicate', // Adjust the path if necessary
    			method: 'GET',
    			data: { username : username },
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