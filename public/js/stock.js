$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/api/stocks",
        success: function(data) {
            console.log(data);
            var elements = $();
            data.forEach(function(item, index) {
                var body = '<tr>'+
                    '<td>' + item.id + '</td>' +
                    '<td>' + item.name + '</td>' +
                    '<td>' + item.currentPrice + '</td>' +
                    '</tr>';
                
                elements = elements.add(body);
            });
            $('#all-stocks').html(elements);
        }
    });

    $("#get-id-submit").click(function() {
        var id = $("#get-id").val();
        $.ajax({
            url: "http://localhost:8080/api/stocks/" + id,
            success: function (data) {
                var body = '<div class="well">' +
                    '<div>Name: ' + data.name + '</div>' +
                    '<div>Current Price: ' + data.currentPrice + '</div>' +
                    '</div>';
                $('.individual-stocks').html(body);
            },
            error: function (error) {
                $('.individual-stocks').html('No stock with that ID');
            }
        });
    })

    $("#update-submit").click(function() {
        var id = $("#update-id").val();
        var price = $("#update-price").val();
        $.ajax({
            url: "http://localhost:8080/api/stocks/" + id,
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify({"id": id, "currentPrice": price}),
            success: function (data) {
                $("#update-message").html("Success!");
            },
            error: function(error) {
                console.log(error);
                $("#error").html(error.message);
            }
        });
    });

    $("#create-submit").click(function() {
        var name = $("#create-name").val();
        var price = $("#create-price").val();
        $.ajax({
            url: "http://localhost:8080/api/stocks",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({"name": name, "currentPrice": price}),
            success: function (data) {
                console.log(data);
                $("#create-message").html("Successfully create stock " + name + " for " + price + ", ID is " + data);
            },
            error: function(error) {
            }
        });
    });
});
