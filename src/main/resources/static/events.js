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

                var complaints = {};

                $.each(data, function(index, key) {
                    var issue = key.product.toLocaleLowerCase().trim();
                    if (issue in complaints){
                        complaints[issue] += 1;
                    } else {
                        complaints[issue] = 1;
                    }
                });

                var complaints_array = []

                for(key in complaints){
                    complaints_array.push([key, complaints[key]]);
                }

                google.charts.load('current', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.charts.setOnLoadCallback(drawChart);

                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.
                function drawChart() {

                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Complaint');
                    data.addColumn('number', 'Count');

                    data.addRows(complaints_array);

                    // Set chart options
                    var companyName
                    var options = {'title':'Categorical Complaints for: ' + $("#name").val(),
                        'width':700,
                        'height':700};

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.PieChart(document.getElementById('chart'));
                    chart.draw(data, options);
                }
            }
        });

    });
});

