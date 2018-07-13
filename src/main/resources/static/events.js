var bar_chart_data = {};

$(document).ready(function(){
    if(window.location.href.indexOf("single") > -1 ||
        window.location.href.indexOf("compare") > -1) {
        if (window.localStorage.getItem("token") == null){
            window.location.replace("http://cfpb-ada.herokuapp.com");
        }
    }

    var search_results = [];

    $('#typeAheadID').on('input', function() {
        $.ajax({
            url: "https://data.consumerfinance.gov/resource/jhzv-w97w.json",
            data: {"$select":"company",
                "$where": "company like '%" + $("#typeAheadID").val() + "%'",
                "$group": "company"
            },
            type: "GET",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-App-Token", "z7A8MAj2zPIpDZ7PaggWlaYDL")
            },
            success: function (data) {
                search_results = [];
                $.each(data, function(index, key) {
                    search_results.push(key.company);
                });

                $('#typeAheadID').typeahead({
                    source: search_results
                });
            }
        });
    });


    $('#search').click(function(){
        $.ajax({
            url: "https://data.consumerfinance.gov/resource/jhzv-w97w.json",
            data: {"company": $("#typeAheadID").val(),
                    "$limit": 10000},
            type: "GET",
            beforeSend: function(xhr){xhr.setRequestHeader("X-App-Token","z7A8MAj2zPIpDZ7PaggWlaYDL")},
            success: function(data){
                $("#complaintCount").html(data.length);
                $("#companyName").html($("#typeAheadID").val());

                var complaints = {};

                $.each(data, function(index, key) {
                    var issue = key.product.toLocaleLowerCase().trim();
                    if (issue in complaints){
                        complaints[issue] += 1;
                    } else {
                        complaints[issue] = 1;
                    }
                });

                var complaints_array = [];

                for(key in complaints){
                    complaints_array.push([key, complaints[key]]);
                }

                google.charts.load('current', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.charts.setOnLoadCallback(drawChart(data.length));

                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.
                function drawChart(count) {

                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Complaint');
                    data.addColumn('number', 'Count');

                    data.addRows(complaints_array);


                    var title = count + ' categorical complaints for: ' + $("#typeAheadID").val();
                    var options = {'title': title,
                        'width':700,
                        'height':700};

                    var chart = new google.visualization.PieChart(document.getElementById('chart'));

                    chart.draw(data, options);

                }
            }
        });

    });

    $('#compareSearch').click(function(){
        $.ajax({
            url: "https://data.consumerfinance.gov/resource/jhzv-w97w.json",
            data: {"company": $("#typeAheadID").val(),
                    "$limit": 10000},
            type: "GET",
            beforeSend: function(xhr){xhr.setRequestHeader("X-App-Token","z7A8MAj2zPIpDZ7PaggWlaYDL")},
            success: function(data){


                var complaints = {};

                $.each(data, function(index, key) {
                    var issue = key.product.toLocaleLowerCase().trim();
                    if (issue in complaints){
                        complaints[issue] += 1;
                    } else {
                        complaints[issue] = 1;
                    }
                });

                var company_name = $("#typeAheadID").val().trim();

                if(!(company_name in Object.keys(bar_chart_data))){
                    bar_chart_data[company_name] = data.length;
                }


                var complaints_array = [];

                for(key in complaints){
                    complaints_array.push([key, complaints[key]]);
                }

                google.charts.load('current', {'packages':['corechart']});

                // Set a callback to run when the Google Visualization API is loaded.
                google.charts.setOnLoadCallback(drawChart(data.length));

                google.charts.setOnLoadCallback(drawBarChart(data.length));


                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.
                function drawChart(count) {

                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Complaint');
                    data.addColumn('number', 'Count');

                    data.addRows(complaints_array);

                    // Set chart options
                    var title = count + ' categorical complaints for: ' + $("#typeAheadID").val();
                    var options = {'title': title,
                        'width':500,
                        'height':500};

                    var chart_id = "";
                    var id = 0;
                    // Instantiate and draw our chart, passing in some options.
                    if ($('#chart1').children().length === 0){
                        chart_id = 'chart1';
                        id = 1;
                    } else if ($('#chart2').children().length === 0){
                        chart_id = 'chart2';
                        id = 2;
                    }else if ($('#chart3').children().length === 0){
                        chart_id = 'chart3';
                        id = 3;
                    }else if ($('#chart4').children().length === 0){
                        chart_id = 'chart4';
                        id = 4;
                    }else {
                        chart_id = 'chart5';
                        id = 5;
                    }

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.PieChart(document.getElementById(chart_id));

                    chart.draw(data, options);
                    var removeID = "remove" + id;
                    $('#'+chart_id).append('<label display="none" id="label' +id+'" value="' + $("#typeAheadID").val().trim() +'"/>');
                    $('#'+chart_id).append('<input type="button" class="remove" id="' +removeID+'" value="Remove Chart"/>');

                    $(document).on("click", "input.remove", function(){
                        var chart_id = $(this).attr('id');
                        var id = chart_id.substr(chart_id.length -1);
                        var company_name = $('#label' + id).attr('value');
                        delete bar_chart_data[company_name];
                        drawBarChart();
                        $(this).parent().empty();
                    });



                    }

                function drawBarChart(count) {
                    if(Object.keys(bar_chart_data).length === 0) {
                        $('#barCharts').empty();
                    } else {
                        var bar_data = [
                            ['Company Name', 'Complaints']
                        ];

                        $.each(bar_chart_data, function(key, value){
                            bar_data.push([key, value]);
                        });

                        var data = google.visualization.arrayToDataTable(bar_data);

                        var options = {
                            title: 'Total Complaints',
                            chartArea: {width: '50%'},
                            hAxis: {
                                title: 'Total Complaints',
                                minValue: 0
                            },
                            vAxis: {
                                title: 'Company'
                            }
                        };

                        var chart = new google.visualization.BarChart(document.getElementById('barCharts'));

                        chart.draw(data, options);
                    }
                }
            }
        });

    });

});


