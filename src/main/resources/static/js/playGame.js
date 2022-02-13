$( document ).ready(function() {
    if(localStorage.getItem("player") === "undefined") {
        $("#playerName").html("Please first create a game.");
    } else {
        $("#playerName").html(localStorage.getItem("player"));
    }
});

$( window ).on( "load", function() {
    if(localStorage.getItem("player") === "undefined") {
        $("#playerName").html("Please first create a game.");
    } else {
        $("#playerName").html(localStorage.getItem("player"));
    }
});


var handle200 = function (data, textStatus, jqXHR) {
    let board = data.boardDisplay;
    let player = data.turnPlayer;
    let game = data.gameIsOver;

    localStorage.setItem("player", player);
    if (game === true) {
        $("#playerName").html("Game is over.");
        localStorage.removeItem("id");
        localStorage.removeItem("player");
    } else {
        $("#playerName").html(player);
    }

    for(let i = 0 ; i<14 ; i++) {
        $("#"+i).html(board[i]);
    }
    console.log(response);
};

var handle400 = function (jqXHR, textStatus, errorThrown) {
    $(".content").css({ 'display' : '' });
    $(".message").removeClass('success');
    $(".message").addClass('negative');
    $("#response").html( jqXHR.responseText );
}


$(function () {

    $(".item").click(function(){
        var currentID = $(this).attr("id");
        console.log(currentID);
        $.ajax({
            type: "POST",
            url: "/games/real/make-turn/",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                pitId: localStorage.getItem("id"),
                board: localStorage.getItem(),
                selectedPit: currentID
            }),
            statusCode: {
                200: handle200,
                400: handle400
            }
        });
    });

    $(document.body).on('click', 'div', function (evt) {


    })
});

$(function(){
    $('.message .close')
        .on('click', function() {
            $(this)
                .closest('.message')
                .transition('fade')
            ;
        }); // jQuery methods go here...
});
