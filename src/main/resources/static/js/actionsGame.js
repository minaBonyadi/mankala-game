var handle201 = function(data, textStatus, jqXHR) {
    // test it if you are in doubt:
    console.log(data);
    console.log(textStatus);
    console.log(jqXHR);
    $(".content").css({ 'display' : '' });
    $(".message").removeClass('negative');
    $(".message").addClass('success');
    $("#response").html("Game was registered with id: " + data.responseText + " successfully.");
    localStorage.setItem("id", data.responseText);
    localStorage.setItem("player", $("#player1-name").val());
};

var handle404 = function(jqXHR, textStatus, errorThrown) {
    $(".content").css({ 'display' : '' });
    $(".message").removeClass('success');
    $(".message").addClass('negative');
    $("#response").html("player with name: " + jqXHR.responseText + "not found.");

}

function showAlert() {
    $.ajax({
        type: "POST",
        url: "/games/real/make-turn/",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            player1Name: $("#player1-name").val(),
        }),
        statusCode: {
            201: handle201,
            404: handle404
        }
    });
}

$(function(){
    $('.message .close')
        .on('click', function() {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        }); // jQuery methods go here...
});
