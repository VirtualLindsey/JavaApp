$(document).ready(function(){
    $('#search').click(function(){
        $.ajax({
            url: "https://data.consumerfinance.gov/resource/jhzv-w97w.json",
            data: {"company": $("#name").val()},
            type: "GET",
            beforeSend: function(xhr){xhr.setRequestHeader("X-App-Token","z7A8MAj2zPIpDZ7PaggWlaYDL")},
            success: function(data){
                $("#complaintCount").html(data.length);
                $("#companyName").html($("#name").val());
            }
        });

    });
});

