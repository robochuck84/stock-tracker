var create_table_element = function(item) {
    var body = '<tr>'+
        '<td>' + item.id + '</td>' +
        '<td>' + item.name + '</td>' +
        '<td>' + item.currentPrice + '</td>' +
        '<td>' + item.lastUpdated.toString() + '</td>' +
        '</tr>';
    return body;
};

var load_all_stocks = function() {
    $.ajax({
        url: "http://localhost:8080/api/stocks",
        success: function(data) {
            console.log(data);
            var elements = $();
            data.forEach(function(item, index) {
                elements = elements.add(create_table_element(item));
            });
            $('#all-stocks').html(elements);
        }
    });
};

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/api/stocks",
        success: load_all_stocks
    });

    $("#get-id-submit").click(function() {
        var id = $("#get-id").val();
        $.ajax({
            url: "http://localhost:8080/api/stocks/" + id,
            success: function (data) {
                $('#all-stocks').html(create_table_element(data));
            },
            error: function (error) {
                $('#all-stocks').html('');
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
                load_all_stocks();
            },
            error: function(error) {
                alert(error.responseJSON.message);
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
                load_all_stocks();
            },
            error: function(error) {
                alert(error.responseJSON.message);
            }
        });
    });
});
