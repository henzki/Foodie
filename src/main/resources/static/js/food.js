   $(document).ready(function() {
    $('#loader').hide();
    $("#submit").on("click", function() {
    	$("#submit").prop("disabled", true);
    	var name = $("#name").val(); 
        var time = $("#time").val();
        var link = $("#link").val();
        var file = $("#image").val();
        var category = $("#category").val();
        var cuisine = $("#cuisine").val();
        var form = $("#form").serialize();
    	var data = new FormData($("#form")[0]);
    	data.append('name', name);
    	data.append('time', time);
    	data.append('link', link); 
    	data.append('category', category); 
    	data.append('cuisine', cuisine);
    	
        $('#loader').show();
        if (name === "" || file === "" || time === "" || link === "" || category === "" || cuisine === "") {
        	$("#submit").prop("disabled", false);
            $('#loader').hide();
            $("#name").css("border-color", "red");
            $("#image").css("border-color", "red");
            $("#time").css("border-color", "red");
            $("#link").css("border-color", "red");
            $("#category").css("border-color", "red");
            $("#cuisine").css("border-color", "red");
        } else {
            $("#name").css("border-color", "");
            $("#image").css("border-color", "");
            $("#time").css("border-color", "");
            $("#link").css("border-color", "");
            $("#category").css("border-color", "");
            $("#cuisine").css("border-color", "");
                    $.ajax({
                        type: 'POST',
                        enctype: 'multipart/form-data',
                        data: data,
                        url: "/saveFood", 
                        processData: false,
                        contentType: false,
                        cache: false,
                        success: function(data, statusText, xhr) {
                        console.log(xhr.status);
                        if(xhr.status == "200") {
                        		$('#loader').hide(); 
                        		$("#form")[0].reset();
                        		$('#success').css('display','block');
                            $("#error").text("");
                            $("#success").html("Food Inserted Succsessfully.");
                            $('#success').delay(2000).fadeOut('slow');
                         }	   
                        },
                        error: function(e) {
                        	$('#loader').hide();
                        	$('#error').css('display','block');
                            $("#error").html("Oops! something went wrong.");
                            $('#error').delay(5000).fadeOut('slow');
                            location.reload();
                        }
                    });
        }
            });
        });