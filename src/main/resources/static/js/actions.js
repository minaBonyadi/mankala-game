var handle201 = function(data, textStatus, jqXHR) {
    // test it if you are in doubt:
    console.log(data);
    console.log(textStatus);
    console.log(jqXHR);
    $(".content").css({ 'display' : '' });
    $(".message").removeClass('negative');
    $(".message").addClass('success');
    $("#response").html("player was registered successfully.");
};

var handle409 = function(jqXHR, textStatus, errorThrown) {
    $(".content").css({ 'display' : '' });
    $(".message").removeClass('success');
    $(".message").addClass('negative');
    $("#response").html(jqXHR.responseText);

}

function showAlert() {
    $.ajax({
        type: "POST",
        url: "/games/real/make-turn/",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            name: $("#first-name").val(),
        }),
        statusCode: {
            201: handle201,
            409: handle409
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
